import React, { useState } from 'react'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'
import useToDo from '../hooks/useToDo'
import '../App.css'
import { ToDo } from '../types'

interface ModalProps {
  onAdd: () => void
}

const Modal: React.FC<ModalProps> = ({ onAdd }) => {
  const { addToDo } = useToDo()
  const [modal, setModal] = useState<boolean>(false)
  const [newToDoText, setNewToDoText] = useState<string>('')
  const [priority, setPriority] = useState<ToDo['priority']>('HIGH') // Ensure priority is typed correctly
  const [dueDate, setDueDate] = useState<Date | null>(null)
  const [error, setError] = useState<string | null>(null)

  const toggleModal = () => {
    setModal((prev) => !prev)
  }

  if (modal) {
    document.body.classList.add('active-modal')
  } else {
    document.body.classList.remove('active-modal')
  }

  const formatDate = (date: Date): string => {
    const pad = (num: number) => String(num).padStart(2, '0')
    const day = pad(date.getDate())
    const month = pad(date.getMonth() + 1) // Months are zero-indexed
    const year = date.getFullYear()
    const hours = pad(date.getHours())
    const minutes = pad(date.getMinutes())

    // Adjust format based on your backend requirements
    return `${day}/${month}/${year} ${hours}:${minutes}:00`
  }

  const handleAddToDo = async () => {
    if (!newToDoText || !dueDate) {
      setError('Please fill out all fields.')
      return
    }

    const newToDo: Omit<ToDo, 'id'> = {
      text: newToDoText,
      priority: priority,
      dueDate: formatDate(dueDate),
      creationDate: new Date().toISOString(),
      done: false,
    }

    try {
      await addToDo(newToDo)
      onAdd() // Call the onAdd prop to refresh the ToDoTable
      toggleModal()
      setError(null) // Clear any existing errors
    } catch (error) {
      console.error('Error adding new ToDo:', error)
      setError('Failed to add new ToDo. Please try again.')
    }
  }

  return (
    <>
      <button
        onClick={toggleModal}
        className="fixed bottom-8 right-8 bg-blue-500 text-white w-16 h-16 flex items-center justify-center rounded-full shadow-lg hover:bg-blue-600 transition duration-300 text-3xl"
      >
        +
      </button>

      {modal && (
        <div className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-50 z-50">
          <div className="bg-white rounded-lg shadow-lg p-6 w-full max-w-md mx-auto">
            <h2
              className="text-2xl font-bold mb-4 text-gray-700"
              id="modal-title"
            >
              Create To Do
            </h2>
            <div className="mb-4">
              <label
                htmlFor="todo-text"
                className="block text-gray-700 font-semibold mb-2"
              >
                To Do Name:
              </label>
              <input
                id="todo-text"
                type="text"
                name="text"
                maxLength={120}
                value={newToDoText}
                onChange={(e) => setNewToDoText(e.target.value)}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                aria-required="true"
              />
            </div>
            <div className="mb-4">
              <label
                htmlFor="todo-priority"
                className="block text-gray-700 font-semibold mb-2"
              >
                Priority:
              </label>
              <select
                id="todo-priority"
                name="priority"
                value={priority}
                onChange={(e) =>
                  setPriority(e.target.value as ToDo['priority'])
                } // Ensure type is correctly set
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                aria-required="true"
              >
                <option value="HIGH">High</option>
                <option value="MEDIUM">Medium</option>
                <option value="LOW">Low</option>
              </select>
            </div>
            <div className="mb-4">
              <label
                htmlFor="todo-due-date"
                className="block text-gray-700 font-semibold mb-2"
              >
                Due Date:
              </label>
              <DatePicker
                id="todo-due-date"
                selected={dueDate}
                onChange={(date) => setDueDate(date)} // Set selected date
                showTimeSelect
                dateFormat="Pp" // Custom format for date and time
                timeFormat="HH:mm:ss"
                timeIntervals={1} // Show every minute
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                aria-required="true"
              />
            </div>
            {error && (
              <div
                className="bg-red-100 text-red-700 p-2 rounded mb-4"
                role="alert"
              >
                {error}
              </div>
            )}
            <div className="flex justify-between">
              <button
                className="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600 transition duration-300"
                onClick={toggleModal}
                aria-label="Close modal"
              >
                X
              </button>
              <button
                onClick={handleAddToDo}
                className="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 transition duration-300"
              >
                Add to do
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  )
}

export default Modal
