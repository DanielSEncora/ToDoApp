/**
 * FilterMenu Component
 *
 * The FilterMenu component provides a user interface for filtering ToDo items.
 * It includes input fields for task name, priority, and state (done/undone).
 * The component uses debouncing to optimize the filtering process.
 *
 * @param {(text: string, priority: string, done: string) => void} onFilterChange - A callback function to be called when the filter criteria change.
 * @returns {JSX.Element} The rendered FilterMenu component.
 */

import React, { useState, useCallback } from 'react'
import '../App.css'

type FilterProps = {
  onFilterChange: (text: string, priority: string, done: string) => void
}

const FilterMenu: React.FC<FilterProps> = ({ onFilterChange }) => {
  const [text, setText] = useState<string>('')
  const [priority, setPriority] = useState<string>('')
  const [done, setDone] = useState<string>('')

  // Debounce the onFilterChange function to limit the number of times it is called
  const debouncedFilterChange = useCallback(debounce(onFilterChange, 300), [])

  /**
   * Handles changes to the text input field.
   *
   * @param {React.ChangeEvent<HTMLInputElement>} e - The change event.
   */
  const handleTextChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setText(e.target.value)
    debouncedFilterChange(e.target.value, priority, done)
  }

  /**
   * Handles changes to the priority select field.
   *
   * @param {React.ChangeEvent<HTMLSelectElement>} e - The change event.
   */
  const handlePriorityChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setPriority(e.target.value)
    debouncedFilterChange(text, e.target.value, done)
  }

  /**
   * Handles changes to the state (done/undone) select field.
   *
   * @param {React.ChangeEvent<HTMLSelectElement>} e - The change event.
   */
  const handleDoneChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setDone(e.target.value)
    debouncedFilterChange(text, priority, e.target.value)
  }

  return (
    <div className="p-4 bg-white rounded-lg shadow-md max-w-4xl mx-auto mt-8">
      <h2 className="text-2xl font-bold text-center mb-4 text-gray-700">
        Search for a task!
      </h2>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div className="flex flex-col items-center">
          <label
            htmlFor="name"
            className="block text-gray-700 font-semibold mb-2"
          >
            Name
          </label>
          <input
            id="name"
            type="text"
            onChange={handleTextChange}
            value={text}
            placeholder="Enter task name"
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
        <div className="flex flex-col items-center">
          <label
            htmlFor="priority"
            className="block text-gray-700 font-semibold mb-2"
          >
            Priority
          </label>
          <select
            id="priority"
            name="priority"
            value={priority}
            onChange={handlePriorityChange}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            <option value="">All</option>
            <option value="HIGH">High</option>
            <option value="MEDIUM">Medium</option>
            <option value="LOW">Low</option>
          </select>
        </div>
        <div className="flex flex-col items-center">
          <label
            htmlFor="state"
            className="block text-gray-700 font-semibold mb-2"
          >
            State
          </label>
          <select
            id="state"
            name="state"
            onChange={handleDoneChange}
            value={done}
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            <option value="">All</option>
            <option value="done">Done</option>
            <option value="undone">Undone</option>
          </select>
        </div>
      </div>
    </div>
  )
}

/**
 * Utility function to debounce calls.
 *
 * @param {Function} func - The function to debounce.
 * @param {number} wait - The delay in milliseconds.
 * @returns {Function} - The debounced function.
 */
function debounce(func: Function, wait: number) {
  let timeout: NodeJS.Timeout
  return function executedFunction(...args: any[]) {
    const later = () => {
      clearTimeout(timeout)
      func(...args)
    }
    clearTimeout(timeout)
    timeout = setTimeout(later, wait)
  }
}

export default FilterMenu
