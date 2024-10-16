import React from "react";

const toDoTable = () => {
  return (
    <>
      <div className="black-border-mp">
        <table>
          <thead>
            <tr>
              <th>Checkbox</th>
              <th>Name</th>
              <th>Priority</th>
              <th> Due Date</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody className="table-group-divider">
            <tr>
              <td>
                <input type="checkbox" name="check" />
              </td>
              <td>Todo #1</td>
              <td>High</td>
              <td>10/11/2024</td>
              <td>Edit/Delete</td>
            </tr>
            <tr>
              <td>
                <input type="checkbox" name="check" />
              </td>
              <td>Todo #2</td>
              <td>Medium</td>
              <td>10/11/2024</td>
              <td>Edit/Delete</td>
            </tr>
            <tr>
              <td>
                <input type="checkbox" name="check" />
              </td>
              <td>Todo #3</td>
              <td>Low</td>
              <td>10/11/2024</td>
              <td>Edit/Delete</td>
            </tr>
          </tbody>
        </table>
        <div className="">1 2 3 .. 10</div>
      </div>
    </>
  );
};

export default toDoTable;
