import React, { useState } from "react";
import "./Modal.css";

const Modal: React.FC = () => {
  const [modal, setModal] = useState<boolean>(false);

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
      <button onClick={toggleModal} className="btn-modal">
        + New To Do
      </button>

      {modal && (
        <div className={`modal ${modal ? "active" : ""}`}>
          <div onClick={toggleModal} className="overlay"></div>
          <div className="modal-content">
            <h2>Create To Do</h2>
            <div>To Do Name:</div>
            <input type="text" name="check" />
            <div>Priority:</div>
            <select name="priority">
              <option value="high">High</option>
              <option value="medium">Medium</option>
              <option value="low">Low</option>
            </select>
            <div>Due Date:</div>
            <input type="date" name="check" />
            <button className="close-modal" onClick={toggleModal}>
              X
            </button>
            <input type="submit" name="check" />
          </div>
        </div>
      )}
    </>
  );
};

export default Modal;
