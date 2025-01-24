import React, { useState, useCallback } from 'react'
import '../App.css'

type FilterProps = {
  onFilterChange: (text: string, priority: string, done: string) => void
}

const FilterMenu: React.FC<FilterProps> = ({ onFilterChange }) => {
  const [text, setText] = useState<string>('')
  const [priority, setPriority] = useState<string>('')
  const [done, setDone] = useState<string>('')

  const debouncedFilterChange = useCallback(debounce(onFilterChange, 300), [])

  const handleTextChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setText(e.target.value)
    debouncedFilterChange(e.target.value, priority, done)
  }

  const handlePriorityChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setPriority(e.target.value)
    debouncedFilterChange(text, e.target.value, done)
  }

  const handleDoneChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setDone(e.target.value)
    debouncedFilterChange(text, priority, e.target.value)
  }

  return (
    <div className="black-border-mp">
      <div>
        <label htmlFor="name">Name</label>
        <input
          id="name"
          type="text"
          onChange={handleTextChange}
          value={text}
          placeholder="Name"
        />
      </div>
      <div>
        <label htmlFor="priority">Priority</label>
        <select
          id="priority"
          name="priority"
          value={priority}
          onChange={handlePriorityChange}
        >
          <option value="">All</option>
          <option value="HIGH">High</option>
          <option value="MEDIUM">Medium</option>
          <option value="LOW">Low</option>
        </select>
      </div>
      <div>
        <label htmlFor="state">State</label>
        <select
          id="state"
          name="state"
          onChange={handleDoneChange}
          value={done}
        >
          <option value="">All</option>
          <option value="done">Done</option>
          <option value="undone">Undone</option>
        </select>
      </div>
    </div>
  )
}

// Utility function to debounce calls
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
