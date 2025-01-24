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

  const toggleModal = () => {
    setModal((prev) => !prev)
  }

  if (modal) {
    document.body.classList.add('active-modal')
  } else {
    document.body.classList.remove('active-modal')
  }

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

  const formatDate = (date: Date): string => {
    const day: string = String(date.getDate()).padStart(2, '0')
    const month: string = String(date.getMonth() + 1).padStart(2, '0') // Months are 0-based
    const year: number = date.getFullYear()
    const hours: string = String(date.getHours()).padStart(2, '0')
    const minutes: string = String(date.getMinutes()).padStart(2, '0')
    const seconds: string = String(date.getSeconds()).padStart(2, '0')

    return `${day}/${month}/${year} ${hours}:${minutes}:${seconds}` // Format as dd/MM/yyyy HH:mm:ss
  }

  return (
    <>
      <button onClick={toggleModal} className="action-buttons">
        Edit
      </button>

      {modal && (
        <div className={`modal ${modal ? 'active' : ''}`}>
          <div onClick={toggleModal} className="overlay"></div>
          <div className="modal-content">
            <h2>Edit To Do</h2>
            <div>To Do Name:</div>
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
            />
            <div>Priority:</div>
            <select
              name="priority"
              value={toDo?.priority || ''}
              onChange={(e) =>
                setToDo((prev) =>
                  prev
                    ? {
                        ...prev,
                        priority: e.target.value as 'LOW' | 'MEDIUM' | 'HIGH',
                      }
                    : null,
                )
              }
            >
              <option value="HIGH">High</option>
              <option value="MEDIUM">Medium</option>
              <option value="LOW">Low</option>
            </select>
            <div>Due Date:</div>
            <DatePicker
              selected={dueDate}
              onChange={(date) => setDueDate(date)} // Set selected date
              showTimeSelect
              dateFormat="Pp" // Custom format for date and time
            />
            <button className="close-modal" onClick={toggleModal}>
              X
            </button>
            <input type="submit" value="Save Changes" onClick={handleEdit} />
          </div>
        </div>
      )}
    </>
  )
}

export default EditModal
