import React, { useState } from "react";
import Search from "./Search";
import "../App.css";

interface ToDo {
  id: number;
  text: string;
  priority: string;
  dueDate: string;
  done: boolean;
}

type FilterProps = {
  onFilterChange: (text: string, priority: string, done: string) => void;
};

const FilterMenu: React.FC<FilterProps> = ({ onFilterChange }) => {
  const [text, setText] = useState<string>("");
  const [priority, setPriority] = useState<string>("");
  const [done, setDone] = useState<string>("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault(); // Prevent the default form submission
    onFilterChange(text, priority, done); // Call the callback with the form values
    setText(""); // Clear the input fields
    setPriority("");
    setDone("");
  };

  return (
    <form onSubmit={handleSubmit} className="black-border-mp">
      <div>
        <div>Name</div>
        <td>
          <input
            type="text"
            onChange={(e) => setText(e.target.value)}
            value={text}
            placeholder="Name"
          />
        </td>
        <div>Priority</div>
        <td>
          <select
            name="priority"
            value={priority}
            onChange={(e) => setPriority(e.target.value)}
          >
            <option value="">All</option>
            <option value="HIGH">High</option>
            <option value="MEDIUM">Medium</option>
            <option value="LOW">Low</option>
          </select>
        </td>
        <div>State</div>
        <td>
          <select
            name="state"
            onChange={(e) => setDone(e.target.value)}
            value={done}
          >
            <option value="">All</option>
            <option value="done">Done</option>
            <option value="undone">Undone</option>
          </select>
        </td>
        <button type="submit">Search</button>
      </div>
    </form>
  );
};

export default FilterMenu;
