import React from 'react'
import useToDo from '../hooks/useToDo'
import '../App.css'
import ToDoTable from '../components/ToDoTable'
import FilterMenu from '../components/FilterMenu'
import Metrics from '../components/Metrics'
import Modal from '../components/Modal'

const Home: React.FC = () => {
  const {
    toDos,
    loading,
    error,
    fetchToDos,
    fetchFilteredData,
    addToDo,
    updateToDo,
    deleteToDo,
  } = useToDo()

  return (
    <div className="p-4 bg-gray-100 min-h-screen">
      <div className="max-w-7xl mx-auto">
        <FilterMenu onFilterChange={fetchFilteredData} />
        <div className="mt-8">
          <Modal onAdd={fetchToDos} />
        </div>
        {loading && <p className="text-center text-gray-500">Loading...</p>}
        {error && <p className="text-center text-red-500">Error: {error}</p>}
        <div className="mt-8">
          <ToDoTable
            toDos={toDos}
            refreshData={fetchToDos}
            updateToDo={updateToDo}
            deleteToDo={deleteToDo}
          />
        </div>
        <div className="mt-8">
          <Metrics toDos={toDos} />
        </div>
      </div>
    </div>
  )
}

export default Home
