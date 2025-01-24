import React from 'react'
import { ToDo } from '../types'

interface MetricsProps {
  toDos: ToDo[]
}

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

const Metrics: React.FC<MetricsProps> = ({ toDos }) => {
  return (
    <div className="black-border-mp">
      <div>Average time to finish tasks: {calculateAverageTime(toDos)}</div>
      <div>Average time to finish tasks by priority:</div>
      <div>Low: {calculateAverageTimeByPriority(toDos, 'LOW')}</div>
      <div>Medium: {calculateAverageTimeByPriority(toDos, 'MEDIUM')}</div>
      <div>High: {calculateAverageTimeByPriority(toDos, 'HIGH')}</div>
    </div>
  )
}

export default Metrics
