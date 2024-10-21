import React, { useState } from "react";

import "../App.css";

interface ModalProps {
  onAdd: () => void;
}

const Modal: React.FC<ModalProps> = ({ onAdd }) => {
  const [modal, setModal] = useState<boolean>(false);
  const [newToDoText, setNewToDoText] = useState<string>("");
  const [priority, setPriority] = useState<string>("HIGH");
  const [dueDate, setDueDate] = useState<string>("");

  const toggleModal = () => {
    setModal((prev) => !prev);
  };

  if (modal) {
    document.body.classList.add("active-modal");
  } else {
    document.body.classList.remove("active-modal");
  }

  const parseDate = (dateString: string): string => {
    const [year, month, day] = dateString.split("-").map(Number);
    // Create a new Date object
    const date = new Date(year, month - 1, day);

    // Format the date as dd/MM/yyyy
    const formattedDate = `${String(date.getDate()).padStart(2, "0")}/${String(
      date.getMonth() + 1
    ).padStart(2, "0")}/${date.getFullYear()}`;

    return formattedDate;
  };

  const addToDo = () => {
    const newToDo = {
      text: newToDoText,
      priority: priority,
      dueDate: dueDate,
    };
    fetch("http://localhost:9090/todos", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newToDo),
    })
      .then((response) => response.json())
      .then(() => {
        onAdd(); // Call the onAdd prop to refresh the ToDoTable
        toggleModal();
      })
      .catch((error) => console.error("Error fetching data:", error));
  };

  return (
    <>
      <button onClick={toggleModal} className="btn-modal">
        + New To Do
      </button>

      {modal && (
        <div className={`modal ${modal ? "active" : ""}`}>
          <div onClick={toggleModal} className="overlay"></div>
          <div className="modal-content">
            <h2>Create To Do</h2>
            <div>To Do Name:</div>
            <input
              type="text"
              name="text"
              onChange={(e) => setNewToDoText(e.target.value)}
            />
            <div>Priority:</div>
            <select
              name="priority"
              onChange={(e) => setPriority(e.target.value)}
            >
              <option value="HIGH">High</option>
              <option value="MEDIUM">Medium</option>
              <option value="LOW">Low</option>
            </select>
            <div>Due Date:</div>
            <input
              type="date"
              name="dueDate"
              onChange={(e) => setDueDate(String(parseDate(e.target.value)))}
            />
            <button className="close-modal" onClick={toggleModal}>
              X
            </button>
            <input type="submit" value="Add to do" onClick={addToDo} />
          </div>
        </div>
      )}
    </>
  );
};

export default Modal;
