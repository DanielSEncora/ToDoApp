import { useEffect, useState } from "react";
import "./App.css";
import ToDoTable from "./components/ToDoTable";
import FilterMenu from "./components/FilterMenu";
import Metrics from "./components/Metrics";
import Modal from "./components/Modal";

/*
  React siempre está refrescando el componente, cada vez que 
  le cambiamos el valor a todo, mi componente refreshea
  */
function App() {
  const [toDo, setToDo] = useState<any[]>([]);

  const fetchData = () => {
    fetch("http://localhost:9090/todos", {
      method: "GET",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((json) => setToDo(json))
      .catch((error) => console.error("Error fetching data:", error));
  };

  const fetchFilteredData = (text: string, priority: string, done: string) => {
    fetch(`http://localhost:9090/todos/filter/${text}-${priority}-${done}`, {
      method: "GET",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((json) => setToDo(json))
      .catch((error) => console.error("Error fetching data:", error));
  };

  //Agarra la data al correr app y se la asigna a ToDo
  //Si el valor de alguna de las variables dentro de los corchetes []
  // cambia, useEffect vuelve a correr
  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      <FilterMenu onFilterChange={fetchFilteredData} />
      <Modal onAdd={fetchData} />
      <ToDoTable toDos={toDo} refreshData={fetchData} />
      <Metrics />
    </>
  );
}

export default App;
