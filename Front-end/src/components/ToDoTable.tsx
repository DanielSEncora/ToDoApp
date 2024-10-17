import React, { useEffect, useState } from "react";

interface ToDo {
  id: number;
  text: string;
  priority: string;
  dueDate: string;
}

const ToDoTable: React.FC = () => {
  const [toDos, setToDos] = useState<ToDo[]>([]);

  const deleteAction = () => {
    console.log("hi");
    fetch("http://localhost:9090/todos/1", {
      method: "DELETE",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => setToDos(data))
      .catch((error) => console.error("Error fetching data:", error));
  };

  const editAction = () => {};
  const pagination = () => {};

  const fetchData = () => {
    fetch("http://localhost:9090/todos", {
      method: "GET",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => setToDos(data))
      .catch((error) => console.error("Error fetching data:", error));
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div className="black-border-mp">
      <table>
        <thead>
          <tr>
            <th>
              <input type="checkbox" name="check" />
            </th>
            <th>Name</th>
            <th>Priority</th>
            <th>Due Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody className="table-group-divider">
          {toDos.map((todo) => (
            <tr key={todo.id}>
              <td>
                <input type="checkbox" name="check" />
              </td>
              <td>{todo.text}</td>
              <td>{todo.priority}</td>
              <td>{todo.dueDate}</td>
              <td>
                <button className="action-buttons" onClick={editAction}>
                  Edit
                </button>
                /
                <button className="action-buttons" onClick={deleteAction}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="pagination black-border-mp">
        <button className="action-buttons" onClick={pagination}>
          1,2,3,4
        </button>
      </div>
    </div>
  );
};

export default ToDoTable;
