import { useEffect, useState } from 'react';
import './App.css'

/*
  React siempre está refrescando el componente, cada vez que 
  le cambiamos el valor a todo, mi componente refreshea
  */
function App() {
const[toDo, setToDo] = useState <string>('');

//let toDo: String = '';

const fetchData = () => {
  fetch('http://localhost:9090/toDoTest',
  {method: 'GET', headers: { 
      Accept: 'application/json',
      'Content-Type': 'application/json',
      }
    })
  .then(response => response.json()
  .then(json => setToDo((JSON.stringify(json)))))
}


//Agarra la data al correr app y se la asigna a ToDo
//Si el valor de alguna de las variables dentro de los corchetes []
// cambia, useEffect vuelve a correr 
useEffect(() => {
  fetchData()
},[])


  return (
    <>To Do: {toDo}</>
  )
}

export default App
