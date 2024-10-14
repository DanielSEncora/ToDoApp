import { useEffect, useState } from "react";
import "./App.css";
import ToDoTable from "./components/toDoTable";
import FilterMenu from "./components/filterMenu";
import NewToDo from "./components/newToDo";
import Metrics from "./components/Metrics";

/*
  React siempre está refrescando el componente, cada vez que 
  le cambiamos el valor a todo, mi componente refreshea
  */
function App() {
  const [toDo, setToDo] = useState<string>("");
  let id: number = 3;

  const fetchData = (id: number) => {
    fetch("http://localhost:9090/toDo?id=" + id, {
      method: "GET",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    }).then((response) =>
      response.json().then((json) => setToDo(JSON.stringify(json)))
    );
  };

  //Agarra la data al correr app y se la asigna a ToDo
  //Si el valor de alguna de las variables dentro de los corchetes []
  // cambia, useEffect vuelve a correr
  useEffect(() => {
    fetchData(id);
  }, []);

  return (
    <div className="p-40 bg-gray-200">
      To Do: {toDo}
      <FilterMenu />
      <NewToDo />
      <ToDoTable />
      <Metrics />
    </div>
  );
}

export default App;
