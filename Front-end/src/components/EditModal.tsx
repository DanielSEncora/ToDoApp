import React, { useEffect, useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "../App.css";

const EditModal: React.FC<{ todoId: number; onEdit: () => void }> = ({
  todoId,
  onEdit,
}) => {
  interface ToDo {
    id: number;
    text: string;
    priority: string;
    dueDate: string;
    done: boolean;
    creationDate: string;
  }

  const [modal, setModal] = useState<boolean>(false);
  const [toDo, setToDo] = useState<ToDo | null>(null);
  const [dueDate, setDueDate] = useState<Date | null>(null);

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
      setDueDate(new Date(data.dueDate));
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    fetchData(todoId);
  }, [todoId]);

  const toggleModal = () => {
    setModal((prev) => !prev);
  };

  if (modal) {
    document.body.classList.add("active-modal");
  } else {
    document.body.classList.remove("active-modal");
  }

  const handleEdit = async () => {
    if (toDo) {
      const updatedToDo = {
        ...toDo,
        dueDate: dueDate ? formatDate(dueDate) : toDo.dueDate,
        creationDate: toDo.creationDate, // Ensure creationDate is sent as Date
      };

      console.log("Updated ToDo:", updatedToDo);

      try {
        await fetch(`http://localhost:9090/todos/${todoId}`, {
          method: "PUT",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
          },
          body: JSON.stringify(updatedToDo),
        });
        onEdit(); // Refresh the list after editing
        toggleModal();
      } catch (error) {
        console.error("Error updating todo:", error);
      }
    }
  };

  const formatDate = (date: Date): string => {
    const day: string = String(date.getDate()).padStart(2, "0");
    const month: string = String(date.getMonth() + 1).padStart(2, "0"); // Months are 0-based
    const year: number = date.getFullYear();
    const hours: string = String(date.getHours()).padStart(2, "0");
    const minutes: string = String(date.getMinutes()).padStart(2, "0");
    const seconds: string = String(date.getSeconds()).padStart(2, "0");

    return `${day}/${month}/${year} ${hours}:${minutes}:${seconds}`; // Format as dd/MM/yyyy HH:mm:ss
  };

  return (
    <>
      <button onClick={toggleModal} className="action-buttons">
        Edit
      </button>

      {modal && (
        <div className={`modal ${modal ? "active" : ""}`}>
          <div onClick={toggleModal} className="overlay"></div>
          <div className="modal-content">
            <h2>Edit To Do</h2>
            <div>To Do Name:</div>
            <input
              type="text"
              name="text"
              defaultValue={toDo?.text}
              onChange={(e) =>
                setToDo((prev) =>
                  prev ? { ...prev, text: e.target.value } : null
                )
              }
            />
            <div>Priority:</div>
            <select
              name="priority"
              defaultValue={toDo?.priority}
              onChange={(e) =>
                setToDo((prev) =>
                  prev ? { ...prev, priority: e.target.value } : null
                )
              }
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
            />
            <button className="close-modal" onClick={toggleModal}>
              X
            </button>
            <input type="submit" value="Save Changes" onClick={handleEdit} />
          </div>
        </div>
      )}
    </>
  );
};

export default EditModal;
