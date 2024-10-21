import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

import "../App.css";

interface ModalProps {
  onAdd: () => void;
}

const Modal: React.FC<ModalProps> = ({ onAdd }) => {
  const [modal, setModal] = useState<boolean>(false);
  const [newToDoText, setNewToDoText] = useState<string>("");
  const [priority, setPriority] = useState<string>("HIGH");
  const [dueDate, setDueDate] = useState<Date | null>(null);

  const toggleModal = () => {
    setModal((prev) => !prev);
  };

  if (modal) {
    document.body.classList.add("active-modal");
  } else {
    document.body.classList.remove("active-modal");
  }

  const formatDate = (date: Date): string => {
    const pad = (num: number) => String(num).padStart(2, "0");
    const day = pad(date.getDate());
    const month = pad(date.getMonth() + 1); // Months are zero-indexed
    const year = date.getFullYear();
    const hours = pad(date.getHours());
    const minutes = pad(date.getMinutes());

    // Adjust format based on your backend requirements
    return `${day}/${month}/${year} ${hours}:${minutes}:00`;
  };

  const addToDo = () => {
    const newToDo = {
      text: newToDoText,
      priority: priority,
      dueDate: dueDate ? formatDate(dueDate) : null,
    };
    console.log(newToDo);
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
            <DatePicker
              selected={dueDate}
              onChange={(date) => setDueDate(date)} // Set selected date
              showTimeSelect
              dateFormat="Pp" // Custom format for date and time
              timeFormat="HH:mm:ss"
              timeIntervals={1} // Show every minute
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
