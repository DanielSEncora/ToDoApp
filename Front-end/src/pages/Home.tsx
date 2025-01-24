/**
 * Home Component
 *
 * The Home component serves as the main container for the ToDo application.
 * It utilizes the `useToDo` custom hook to manage and fetch ToDo data, and
 * renders various sub-components to provide a comprehensive ToDo management interface.
 *
 * @returns {JSX.Element} The rendered Home component.
 */

import React from 'react'
import useToDo from '../hooks/useToDo'
import '../App.css'
import ToDoTable from '../components/ToDoTable'
import FilterMenu from '../components/FilterMenu'
import Metrics from '../components/Metrics'
import Modal from '../components/Modal'

const Home: React.FC = () => {
  // Destructure the ToDo-related state and functions from the useToDo hook
  const {
    toDos,
    loading,
    error,
    fetchToDos,
    fetchFilteredData,
    updateToDo,
    deleteToDo,
  } = useToDo()

  return (
    <div className="p-4 bg-gray-100 min-h-screen">
      <div className="max-w-7xl mx-auto">
        {/* FilterMenu component for filtering ToDos */}
        <FilterMenu onFilterChange={fetchFilteredData} />

        {/* Modal component for adding new ToDos */}
        <div className="mt-8">
          <Modal onAdd={fetchToDos} />
        </div>

        {/* Display loading state */}
        {loading && <p className="text-center text-gray-500">Loading...</p>}

        {/* Display error state */}
        {error && <p className="text-center text-red-500">Error: {error}</p>}

        {/* ToDoTable component for displaying the list of ToDos */}
        <div className="mt-8">
          <ToDoTable
            toDos={toDos}
            refreshData={fetchToDos}
            updateToDo={updateToDo}
            deleteToDo={deleteToDo}
          />
        </div>

        {/* Metrics component for displaying ToDo metrics */}
        <div className="mt-8">
          <Metrics toDos={toDos} />
        </div>
      </div>
    </div>
  )
}

export default Home
