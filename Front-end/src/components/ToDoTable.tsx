import React, { useEffect, useState } from "react";
import EditModal from "./EditModal";

interface ToDo {
  id: number;
  text: string;
  priority: string;
  dueDate: string;
  done: boolean;
}

interface ToDoTableProps {
  toDos: ToDo[];
  refreshData: () => void;
}

const ToDoTable: React.FC<ToDoTableProps> = ({ toDos, refreshData }) => {
  const [sortedToDos, setSortedToDos] = useState<ToDo[]>(toDos);
  const [sortColumn, setSortColumn] = useState<keyof ToDo | null>(null);
  const [sortDirection, setSortDirection] = useState<"asc" | "desc">("asc");
  const [currentPage, setCurrentPage] = useState<number>(1);
  const itemsPerPage = 6; // Number of items per page

  useEffect(() => {
    setSortedToDos(toDos);
  }, [toDos, refreshData]);

  const deleteAction = (id: number) => {
    fetch(`http://localhost:9090/todos/${id}`, {
      method: "DELETE",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (!response.ok) throw new Error("Failed to delete todo");
        refreshData(); // Refresh data after deletion
      })
      .catch((error) => console.error("Error deleting todo:", error));
  };

  const parseDate = (dateString: string): Date => {
    console.log("before: " + dateString);
    const [year, month, day] = dateString.split("/").map(Number);
    console.log("after: " + new Date(year, month, -1, day));
    return new Date(year, month - 1, day);
  };

  const handleSort = (column: keyof ToDo) => {
    const direction =
      sortColumn === column && sortDirection === "asc" ? "desc" : "asc";
    setSortColumn(column);
    setSortDirection(direction);

    const sortedToDos = [...toDos].sort((a, b) => {
      if (column === "dueDate") {
        // Parse dates directly in the sorting logic

        const dateA = parseDate(a[column]); // Assuming a[column] is in 'yyyy-MM-dd' format
        const dateB = parseDate(b[column]);

        return direction === "asc"
          ? dateA.getTime() - dateB.getTime()
          : dateB.getTime() - dateA.getTime();
      }

      if (column === "priority") {
        const priorityOrder: { [key: string]: number } = {
          LOW: 1,
          MEDIUM: 2,
          HIGH: 3,
        };
        return direction === "asc"
          ? priorityOrder[a[column]] - priorityOrder[b[column]]
          : priorityOrder[b[column]] - priorityOrder[a[column]];
      }

      if (a[column] < b[column]) return direction === "asc" ? -1 : 1;
      if (a[column] > b[column]) return direction === "asc" ? 1 : -1;
      return 0;
    });

    setSortedToDos(sortedToDos); // Update the sorted state
  };

  const totalPages = Math.ceil(sortedToDos.length / itemsPerPage);

  // Get current items
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = sortedToDos.slice(indexOfFirstItem, indexOfLastItem);

  return (
    <div className="black-border-mp">
      <table>
        <thead className="table-headers">
          <tr>
            <th>
              <input type="checkbox" name="check" />
            </th>
            <th onClick={() => handleSort("text")}>Name</th>
            <th onClick={() => handleSort("priority")}>Priority</th>
            <th onClick={() => handleSort("dueDate")}>Due Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody className="table-group-divider">
          {Array.isArray(currentItems) && currentItems.length > 0 ? (
            currentItems.map((todo) => (
              <tr key={todo.id}>
                <td>
                  <input
                    type="checkbox"
                    name="check"
                    defaultChecked={todo.done}
                  />
                </td>
                <td>{todo.text}</td>
                <td>{todo.priority}</td>
                <td>{todo.dueDate}</td>
                <td>
                  <EditModal todoId={todo.id} />/
                  <button
                    className="action-buttons"
                    onClick={() => deleteAction(todo.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={5}>No todos available</td>
            </tr>
          )}
        </tbody>
      </table>
      <div className="pagination black-border-mp">
        {Array.from({ length: totalPages }, (_, index) => (
          <button
            key={index}
            className="action-buttons"
            onClick={() => setCurrentPage(index + 1)}
            style={{
              fontWeight: currentPage === index + 1 ? "bold" : "normal",
            }}
          >
            {index + 1}
          </button>
        ))}
      </div>
    </div>
  );
};

export default ToDoTable;
