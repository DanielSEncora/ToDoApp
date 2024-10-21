import React, { useEffect, useState } from "react";
import "../App.css";

const EditModal: React.FC<{ todoId: number }> = ({ todoId }) => {
  interface ToDo {
    id: number;
    text: string;
    priority: string;
    dueDate: string;
    done: boolean;
  }

  const [modal, setModal] = useState<boolean>(false);
  const [toDo, setToDo] = useState<ToDo | null>(null);

  const fetchData = async (id: number) => {
    try {
      const response = await fetch(`http://localhost:9090/todos/${id}`, {
        method: "GET", // Use GET method to fetch
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      });
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      const data = await response.json();
      setToDo(data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    fetchData(todoId);
  }, []);

  const toggleModal = () => {
    setModal((prev) => !prev);
  };

  if (modal) {
    document.body.classList.add("active-modal");
  } else {
    document.body.classList.remove("active-modal");
  }

  return (
    <>
      <button onClick={toggleModal} className="action-buttons">
        Edit
      </button>

      {modal && (
        <div className={`modal ${modal ? "active" : ""}`}>
          <div onClick={toggleModal} className="overlay"></div>
          <div className="modal-content">
            <h2>Edit To Do </h2>
            <div>To Do Name:</div>
            <input type="text" name="check" defaultValue={toDo?.text} />
            <div>Priority:</div>
            <select name="priority" defaultValue={toDo?.priority}>
              <option value="HIGH">High</option>
              <option value="MEDIUM">Medium</option>
              <option value="LOW">Low</option>
            </select>
            <div>Due Date:</div>
            <input type="date" name="check" value={toDo?.dueDate} />
            <input type="submit" name="check" />
          </div>
          <button className="close-modal" onClick={toggleModal}>
            X
          </button>
        </div>
      )}
    </>
  );
};

export default EditModal;
