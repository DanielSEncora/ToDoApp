/**
 * ToDoTable Component
 *
 * The ToDoTable component is responsible for displaying a table of ToDo items.
 * It provides functionalities for sorting, pagination, updating, and deleting ToDos.
 *
 * @param {ToDo[]} toDos - The list of ToDo items to display.
 * @param {() => void} refreshData - Function to refresh the data.
 * @param {(id: number, updatedToDo: Partial<ToDo>) => Promise<void>} updateToDo - Function to update a ToDo item.
 * @param {(id: number) => Promise<void>} deleteToDo - Function to delete a ToDo item.
 * @returns {JSX.Element} The rendered ToDoTable component.
 */

import React, { useState, useMemo } from 'react'
import EditModal from './EditModal'
import { ToDo } from '../types'

interface ToDoTableProps {
  toDos: ToDo[]
  refreshData: () => void
  updateToDo: (id: number, updatedToDo: Partial<ToDo>) => Promise<void>
  deleteToDo: (id: number) => Promise<void>
}

const ToDoTable: React.FC<ToDoTableProps> = ({
  toDos,
  refreshData,
  updateToDo,
  deleteToDo,
}) => {
  const [sortColumn, setSortColumn] = useState<keyof ToDo | null>(null)
  const [sortDirection, setSortDirection] = useState<'asc' | 'desc'>('asc')
  const [currentPage, setCurrentPage] = useState<number>(1)
  const itemsPerPage = 10

  /**
   * Helper function to check if a property is defined on an object.
   *
   * @param {T} obj - The object to check.
   * @param {K} key - The key to check on the object.
   * @returns {boolean} - Whether the key is defined on the object.
   */
  const isDefined = <T, K extends keyof T>(
    obj: T,
    key: K,
  ): obj is T & Required<Pick<T, K>> => {
    return obj[key] !== undefined
  }

  /**
   * Memoized function to sort the ToDo items based on the selected column and direction.
   */
  const sortedToDos = useMemo(() => {
    if (!sortColumn) return toDos

    return [...toDos].sort((a, b) => {
      if (sortColumn === 'dueDate') {
        const dateA = new Date(a[sortColumn] ?? 0) // Default to epoch time if undefined
        const dateB = new Date(b[sortColumn] ?? 0)
        return sortDirection === 'asc'
          ? dateA.getTime() - dateB.getTime()
          : dateB.getTime() - dateA.getTime()
      }

      if (sortColumn === 'priority') {
        const priorityOrder: { [key: string]: number } = {
          LOW: 1,
          MEDIUM: 2,
          HIGH: 3,
        }
        return sortDirection === 'asc'
          ? priorityOrder[a[sortColumn] ?? ''] -
              priorityOrder[b[sortColumn] ?? '']
          : priorityOrder[b[sortColumn] ?? ''] -
              priorityOrder[a[sortColumn] ?? '']
      }

      // Default to string comparison
      const valueA = a[sortColumn] ?? ''
      const valueB = b[sortColumn] ?? ''
      return sortDirection === 'asc'
        ? valueA < valueB
          ? -1
          : 1
        : valueA > valueB
        ? -1
        : 1
    })
  }, [toDos, sortColumn, sortDirection])

  /**
   * Memoized function to get the current items based on pagination.
   */
  const currentItems = useMemo(() => {
    const indexOfLastItem = currentPage * itemsPerPage
    const indexOfFirstItem = indexOfLastItem - itemsPerPage
    return sortedToDos.slice(indexOfFirstItem, indexOfLastItem)
  }, [sortedToDos, currentPage, itemsPerPage])

  /**
   * Function to handle sorting of columns.
   *
   * @param {keyof ToDo} column - The column to sort by.
   */
  const handleSort = (column: keyof ToDo) => {
    const direction =
      sortColumn === column && sortDirection === 'asc' ? 'desc' : 'asc'
    setSortColumn(column)
    setSortDirection(direction)
  }

  const totalPages = Math.ceil(sortedToDos.length / itemsPerPage)

  /**
   * Function to handle updating the done status of a ToDo item.
   *
   * @param {number} id - The ID of the ToDo item.
   * @param {boolean} done - The new done status.
   */
  const handleUpdateDone = async (id: number, done: boolean) => {
    const updatedToDo = toDos.find((todo) => todo.id === id)
    if (updatedToDo) {
      const updatedFields: Partial<ToDo> = {
        ...updatedToDo,
        done,
        doneDate: done ? new Date().toISOString() : '', // Set doneDate to current time in UTC
        dueDate: updatedToDo.dueDate, // Ensure dueDate is included
        creationDate: updatedToDo.creationDate, // Ensure creationDate is included
      }

      console.log('Updating ToDo with payload:', updatedFields) // Log payload before sending

      await updateToDo(id, updatedFields)
      refreshData()
    }
  }

  /**
   * Function to handle deleting a ToDo item.
   *
   * @param {number} id - The ID of the ToDo item.
   */
  const handleDelete = async (id: number) => {
    await deleteToDo(id)
    refreshData()
  }

  /**
   * Function to parse a date string into a more readable format.
   *
   * @param {string} dateString - The date string to parse.
   * @returns {string} - The parsed date string.
   */
  const parseDate = (dateString: string): string => {
    const date = new Date(dateString)
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0')
    return `${year}/${month}/${day}`
  }

  return (
    <div className="p-4 bg-white rounded-lg shadow-md max-w-4xl mx-auto mt-8">
      <table className="min-w-full bg-white border border-gray-300">
        <thead className="bg-gray-200">
          <tr>
            <th className="p-2 border border-gray-300 w-10 text-center">
              <input type="checkbox" name="check" aria-label="Select all" />
            </th>
            <th
              className="p-2 border border-gray-300 cursor-pointer text-center"
              onClick={() => handleSort('text')}
              aria-sort={
                sortColumn === 'text'
                  ? sortDirection === 'asc'
                    ? 'ascending'
                    : 'descending'
                  : 'none'
              }
            >
              Name
            </th>
            <th
              className="p-2 border border-gray-300 cursor-pointer text-center"
              onClick={() => handleSort('priority')}
              aria-sort={
                sortColumn === 'priority'
                  ? sortDirection === 'asc'
                    ? 'ascending'
                    : 'descending'
                  : 'none'
              }
            >
              Priority
            </th>
            <th
              className="p-2 border border-gray-300 cursor-pointer text-center"
              onClick={() => handleSort('dueDate')}
              aria-sort={
                sortColumn === 'dueDate'
                  ? sortDirection === 'asc'
                    ? 'ascending'
                    : 'descending'
                  : 'none'
              }
            >
              Due Date
            </th>
            <th className="p-2 border border-gray-300 text-center">Actions</th>
          </tr>
        </thead>
        <tbody>
          {Array.isArray(currentItems) && currentItems.length > 0 ? (
            currentItems.map((todo) => (
              <tr key={todo.id} className="hover:bg-gray-100">
                <td className="p-2 border border-gray-300 text-center">
                  <input
                    type="checkbox"
                    name="check"
                    checked={todo.done}
                    onChange={(e) =>
                      handleUpdateDone(todo.id!, e.target.checked)
                    }
                    aria-label={`Mark ${todo.text} as ${
                      todo.done ? 'undone' : 'done'
                    }`}
                  />
                </td>
                <td
                  className={`p-2 border border-gray-300 text-center ${
                    todo.done ? 'line-through text-gray-500' : ''
                  }`}
                >
                  {todo.text}
                </td>
                <td
                  className={`p-2 border border-gray-300 text-center ${
                    todo.done ? 'line-through text-gray-500' : ''
                  }`}
                >
                  {todo.priority}
                </td>
                <td
                  className={`p-2 border border-gray-300 text-center ${
                    todo.done ? 'line-through text-gray-500' : ''
                  }`}
                >
                  {parseDate(todo.dueDate)}
                </td>
                <td className="p-2 border border-gray-300 text-center">
                  {isDefined(todo, 'id') && (
                    <div className="flex justify-center space-x-2">
                      <EditModal todoId={todo.id} onEdit={refreshData} />
                      <button
                        className="bg-red-500 text-white px-4 py-1 rounded hover:bg-red-700"
                        onClick={() => handleDelete(todo.id)}
                        aria-label={`Delete ${todo.text}`}
                      >
                        Delete
                      </button>
                    </div>
                  )}
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={5} className="p-2 text-center">
                No todos available
              </td>
            </tr>
          )}
        </tbody>
      </table>
      <div className="flex justify-center mt-4">
        {Array.from({ length: totalPages }, (_, index) => (
          <button
            key={index}
            className={`px-3 py-1 mx-1 border border-gray-300 ${
              currentPage === index + 1 ? 'bg-blue-500 text-white' : ''
            }`}
            onClick={() => setCurrentPage(index + 1)}
            aria-label={`Go to page ${index + 1}`}
          >
            {index + 1}
          </button>
        ))}
      </div>
    </div>
  )
}

export default ToDoTable
