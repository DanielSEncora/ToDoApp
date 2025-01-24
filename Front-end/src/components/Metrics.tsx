import React, { useEffect } from 'react'
import { ToDo } from '../types'

const calculateAverageTime = (toDos: ToDo[]) => {
  const finishedTasks = toDos.filter(
    (todo) => todo.done && todo.doneDate && todo.creationDate,
  )
  const totalTimeInMillis = finishedTasks.reduce((total, todo) => {
    const creationDate = new Date(todo.creationDate).getTime()
    const doneDate = todo.doneDate ? new Date(todo.doneDate).getTime() : 0
    return total + (doneDate - creationDate)
  }, 0)

  const averageTimeInMillis =
    finishedTasks.length > 0 ? totalTimeInMillis / finishedTasks.length : 0

  return formatTime(averageTimeInMillis)
}

const calculateAverageTimeByPriority = (toDos: ToDo[], priority: string) => {
  const finishedTasks = toDos.filter(
    (todo) =>
      todo.done &&
      todo.priority === priority &&
      todo.doneDate &&
      todo.creationDate,
  )
  const totalTimeInMillis = finishedTasks.reduce((total, todo) => {
    const creationDate = new Date(todo.creationDate).getTime()
    const doneDate = todo.doneDate ? new Date(todo.doneDate).getTime() : 0
    return total + (doneDate - creationDate)
  }, 0)

  const averageTimeInMillis =
    finishedTasks.length > 0 ? totalTimeInMillis / finishedTasks.length : 0

  return formatTime(averageTimeInMillis)
}

const formatTime = (timeInMillis: number) => {
  const totalSeconds = Math.floor(timeInMillis / 1000)
  const hours = Math.floor(totalSeconds / 3600)
  const minutes = Math.floor((totalSeconds % 3600) / 60)
  const seconds = totalSeconds % 60

  return `${String(hours).padStart(2, '0')}:${String(minutes).padStart(
    2,
    '0',
  )}:${String(seconds).padStart(2, '0')}`
}

interface MetricsProps {
  toDos: ToDo[]
}

const Metrics: React.FC<MetricsProps> = ({ toDos }) => {
  useEffect(() => {
    // This effect will run whenever toDos changes
  }, [toDos])

  return (
    <div className="p-6 bg-white rounded-lg shadow-md max-w-4xl mx-auto mt-8 text-center">
      <div className="mb-4">
        <p className="text-gray-700 font-semibold text-lg">
          Average time to finish tasks:
        </p>
        <p className="text-blue-500 font-bold text-2xl">
          {calculateAverageTime(toDos)}
        </p>
      </div>
      <div className="mb-4">
        <p className="text-gray-700 font-semibold text-lg">
          Average time to finish tasks by priority:
        </p>
        <div className="flex justify-center space-x-8 mt-2">
          <div className="bg-green-100 p-4 rounded-lg shadow-md">
            <p className="text-green-500 font-semibold">Low</p>
            <p className="text-gray-700 font-bold">
              {calculateAverageTimeByPriority(toDos, 'LOW')}
            </p>
          </div>
          <div className="bg-yellow-100 p-4 rounded-lg shadow-md">
            <p className="text-yellow-500 font-semibold">Medium</p>
            <p className="text-gray-700 font-bold">
              {calculateAverageTimeByPriority(toDos, 'MEDIUM')}
            </p>
          </div>
          <div className="bg-red-100 p-4 rounded-lg shadow-md">
            <p className="text-red-500 font-semibold">High</p>
            <p className="text-gray-700 font-bold">
              {calculateAverageTimeByPriority(toDos, 'HIGH')}
            </p>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Metrics
