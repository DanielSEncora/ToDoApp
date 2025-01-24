/**
 * EditModal Component
 *
 * The EditModal component provides a user interface for editing an existing ToDo item.
 * It includes form fields for the ToDo name, priority, and due date, and handles form
 * validation and submission.
 *
 * @param {number} todoId - The ID of the ToDo item to edit.
 * @param {() => void} onEdit - A callback function to be called when the ToDo item is successfully edited.
 * @returns {JSX.Element} The rendered EditModal component.
 */

import React, { useEffect, useState, useCallback } from 'react'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'
import '../App.css'
import { ToDo } from '../types' // Import ToDo type
import useToDo from '../hooks/useToDo' // Import the custom hook

const EditModal: React.FC<{ todoId: number; onEdit: () => void }> = ({
  todoId,
  onEdit,
}) => {
  const [modal, setModal] = useState<boolean>(false)
  const [toDo, setToDo] = useState<ToDo | null>(null)
  const [dueDate, setDueDate] = useState<Date | null>(null)

  const { fetchToDo, updateToDo } = useToDo() // Use the custom hook

  /**
   * Fetches the ToDo item data.
   */
  const fetchData = useCallback(async () => {
    try {
      const data = await fetchToDo(todoId)
      setToDo(data)
      setDueDate(new Date(data.dueDate))
    } catch (error) {
      console.error('Error fetching data:', error)
    }
  }, [fetchToDo, todoId])

  useEffect(() => {
    fetchData()
  }, [fetchData])

  /**
   * Toggles the visibility of the modal.
   */
  const toggleModal = () => {
    setModal((prev) => !prev)
  }

  if (modal) {
    document.body.classList.add('active-modal')
  } else {
    document.body.classList.remove('active-modal')
  }

  /**
   * Handles the editing of the ToDo item.
   */
  const handleEdit = async () => {
    if (toDo) {
      const updatedToDo: ToDo = {
        ...toDo,
        dueDate: dueDate ? dueDate.toISOString() : toDo.dueDate,
        creationDate: toDo.creationDate, // Ensure creationDate is sent as string
      }

      console.log('Updated ToDo:', updatedToDo)

      try {
        await updateToDo(todoId, updatedToDo)
        onEdit() // Refresh the list after editing
        toggleModal()
      } catch (error) {
        console.error('Error updating todo:', error)
      }
    }
  }

  return (
    <>
      <button
        onClick={toggleModal}
        className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700"
      >
        Edit
      </button>

      {modal && (
        <div className={`modal ${modal ? 'active' : ''}`}>
          <div
            onClick={toggleModal}
            className="fixed inset-0 bg-gray-600 bg-opacity-50"
          ></div>
          <div className="fixed inset-0 flex items-center justify-center px-4">
            <div className="bg-white rounded-lg p-6 shadow-lg w-full max-w-md">
              <h2 className="text-2xl font-bold mb-4">Edit To Do</h2>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">To Do Name:</label>
                <input
                  type="text"
                  name="text"
                  maxLength={120}
                  value={toDo?.text || ''}
                  onChange={(e) =>
                    setToDo((prev) =>
                      prev ? { ...prev, text: e.target.value } : null,
                    )
                  }
                  className="w-full px-3 py-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Priority:</label>
                <select
                  name="priority"
                  value={toDo?.priority || ''}
                  onChange={(e) =>
                    setToDo((prev) =>
                      prev
                        ? {
                            ...prev,
                            priority: e.target.value as
                              | 'LOW'
                              | 'MEDIUM'
                              | 'HIGH',
                          }
                        : null,
                    )
                  }
                  className="w-full px-3 py-2 border border-gray-300 rounded"
                >
                  <option value="HIGH">High</option>
                  <option value="MEDIUM">Medium</option>
                  <option value="LOW">Low</option>
                </select>
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Due Date:</label>
                <DatePicker
                  selected={dueDate}
                  onChange={(date) => setDueDate(date)} // Set selected date
                  showTimeSelect
                  dateFormat="Pp" // Custom format for date and time
                  className="w-full px-3 py-2 border border-gray-300 rounded"
                />
              </div>
              <button
                className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-700"
                onClick={toggleModal}
              >
                X
              </button>
              <input
                type="submit"
                value="Save Changes"
                onClick={handleEdit}
                className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-700 ml-2"
              />
            </div>
          </div>
        </div>
      )}
    </>
  )
}

export default EditModal
