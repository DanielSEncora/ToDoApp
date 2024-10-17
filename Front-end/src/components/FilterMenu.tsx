import React from "react";
import Search from "./Search";
import "../App.css";
//import "../index.css";

const filterMenu = () => {
  return (
    <>
      <div className="black-border-mp">
        <div>
          <div>Name</div>
          <td>
            <input type="search" name="check" />
          </td>
          <div>Priority</div>
          <td>
            <select name="priority">
              <option value="all">All</option>
              <option value="high">High</option>
              <option value="medium">Medium</option>
              <option value="low">Low</option>
            </select>
          </td>
          <div>State</div>
          <td>
            <select name="state">
              <option value="All">All</option>
              <option value="Done">Done</option>
              <option value="Undone">Undone</option>
            </select>
          </td>
          <Search></Search>
        </div>
      </div>
    </>
  );
};

export default filterMenu;
