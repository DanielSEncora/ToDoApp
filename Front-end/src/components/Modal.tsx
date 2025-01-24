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
      <button onClick={toggleModal} className="btn-modal">
        + New To Do
      </button>

      {modal && (
        <div className={`modal ${modal ? 'active' : ''}`}>
          <div onClick={toggleModal} className="overlay"></div>
          <div
            className="modal-content"
            role="dialog"
            aria-modal="true"
            aria-labelledby="modal-title"
          >
            <h2 id="modal-title">Create To Do</h2>
            <div>
              <label htmlFor="todo-text">To Do Name:</label>
              <input
                id="todo-text"
                type="text"
                name="text"
                maxLength={120}
                value={newToDoText}
                onChange={(e) => setNewToDoText(e.target.value)}
                aria-required="true"
              />
            </div>
            <div>
              <label htmlFor="todo-priority">Priority:</label>
              <select
                id="todo-priority"
                name="priority"
                value={priority}
                onChange={(e) =>
                  setPriority(e.target.value as ToDo['priority'])
                } // Ensure type is correctly set
                aria-required="true"
              >
                <option value="HIGH">High</option>
                <option value="MEDIUM">Medium</option>
                <option value="LOW">Low</option>
              </select>
            </div>
            <div>
              <label htmlFor="todo-due-date">Due Date:</label>
              <DatePicker
                id="todo-due-date"
                selected={dueDate}
                onChange={(date) => setDueDate(date)} // Set selected date
                showTimeSelect
                dateFormat="Pp" // Custom format for date and time
                timeFormat="HH:mm:ss"
                timeIntervals={1} // Show every minute
                aria-required="true"
              />
            </div>
            {error && (
              <div className="error-message" role="alert">
                {error}
              </div>
            )}
            <button
              className="close-modal"
              onClick={toggleModal}
              aria-label="Close modal"
            >
              X
            </button>
            <input type="button" value="Add to do" onClick={handleAddToDo} />
          </div>
        </div>
      )}
    </>
  )
}

export default Modal
