// src/pages/Home/Home.tsx
import React from 'react'
import useToDo from '../hooks/useToDo'
import '../App.css'
import ToDoTable from '../components/ToDoTable'
import FilterMenu from '../components/FilterMenu'
import Metrics from '../components/Metrics'
import Modal from '../components/Modal'

const Home: React.FC = () => {
  const { toDos, loading, error, fetchToDos, fetchFilteredData, addToDo } =
    useToDo()

  return (
    <>
      <FilterMenu onFilterChange={fetchFilteredData} />
      <Modal onAdd={fetchToDos} />
      {loading && <p>Loading...</p>}
      {error && <p>Error: {error}</p>}
      <ToDoTable toDos={toDos} refreshData={fetchToDos} />
      <Metrics toDos={toDos} />
    </>
  )
}

export default Home
