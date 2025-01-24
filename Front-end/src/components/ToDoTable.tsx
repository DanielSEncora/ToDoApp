import React, { useState, useMemo } from 'react'
import EditModal from './EditModal'
import { ToDo } from '../types'
import useToDo from '../hooks/useToDo'

interface ToDoTableProps {
  toDos: ToDo[]
  refreshData: () => void
}

const ToDoTable: React.FC<ToDoTableProps> = ({ toDos, refreshData }) => {
  const { updateToDo, deleteToDo } = useToDo()
  const [sortColumn, setSortColumn] = useState<keyof ToDo | null>(null)
  const [sortDirection, setSortDirection] = useState<'asc' | 'desc'>('asc')
  const [currentPage, setCurrentPage] = useState<number>(1)
  const itemsPerPage = 10

  const isDefined = <T, K extends keyof T>(
    obj: T,
    key: K,
  ): obj is T & Required<Pick<T, K>> => {
    return obj[key] !== undefined
  }

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

  const currentItems = useMemo(() => {
    const indexOfLastItem = currentPage * itemsPerPage
    const indexOfFirstItem = indexOfLastItem - itemsPerPage
    return sortedToDos.slice(indexOfFirstItem, indexOfLastItem)
  }, [sortedToDos, currentPage, itemsPerPage])

  const handleSort = (column: keyof ToDo) => {
    const direction =
      sortColumn === column && sortDirection === 'asc' ? 'desc' : 'asc'
    setSortColumn(column)
    setSortDirection(direction)
  }

  const totalPages = Math.ceil(sortedToDos.length / itemsPerPage)

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

  const formatDate = (date: Date): string => {
    const day: string = String(date.getDate()).padStart(2, '0')
    const month: string = String(date.getMonth() + 1).padStart(2, '0') // Months are 0-based
    const year: number = date.getFullYear()
    const hours: string = String(date.getHours()).padStart(2, '0')
    const minutes: string = String(date.getMinutes()).padStart(2, '0')
    const seconds: string = String(date.getSeconds()).padStart(2, '0')

    return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}.000+00:00` // Format to match dueDate and creationDate
  }

  const handleDelete = (id: number) => {
    deleteToDo(id)
    refreshData()
  }

  const parseDate = (dateString: string): string => {
    const date = new Date(dateString)
    const year = date.getFullYear()
    const month = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0')
    return `${year}/${month}/${day}`
  }

  return (
    <div className="black-border-mp">
      <table>
        <thead className="table-headers">
          <tr>
            <th>
              <input type="checkbox" name="check" aria-label="Select all" />
            </th>
            <th
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
            <th>Actions</th>
          </tr>
        </thead>
        <tbody className="table-group-divider">
          {Array.isArray(currentItems) && currentItems.length > 0 ? (
            currentItems.map((todo) => (
              <tr key={todo.id}>
                <td>
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
                <td>{todo.text}</td>
                <td>{todo.priority}</td>
                <td>{parseDate(todo.dueDate)}</td>
                <td>
                  {isDefined(todo, 'id') && (
                    <>
                      <EditModal todoId={todo.id} onEdit={refreshData} />
                      <button
                        className="action-buttons"
                        onClick={() => handleDelete(todo.id)}
                        aria-label={`Delete ${todo.text}`}
                      >
                        Delete
                      </button>
                    </>
                  )}
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={5}>No todos available</td>
            </tr>
          )}
        </tbody>
      </table>
      <div className="pagination black-border-mp">
        {Array.from({ length: totalPages }, (_, index) => (
          <button
            key={index}
            className="action-buttons pagination"
            onClick={() => setCurrentPage(index + 1)}
            style={{
              fontWeight: currentPage === index + 1 ? 'bold' : 'normal',
            }}
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
