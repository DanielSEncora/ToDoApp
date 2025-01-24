/**
 * useToDo Hook
 * 
 * The useToDo hook provides functionality to manage ToDo items, including fetching, 
 * adding, updating, and deleting ToDos. It also handles loading and error states.
 * 
 * @returns {object} - An object containing the ToDo items, loading state, error state, 
 * and functions to manage ToDo items.
 */

import { useState, useEffect, useCallback } from 'react';
import { ToDo } from '../types';

const useToDo = () => {
  const [toDos, setToDos] = useState<ToDo[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  // Fetch all ToDos when the component mounts
  useEffect(() => {
    fetchToDos();
  }, []);

  /**
   * Fetches all ToDo items.
   * 
   * @returns {Promise<ToDo[]>} - A promise that resolves to an array of ToDo items.
   */
  const fetchToDos = async (): Promise<ToDo[]> => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch('http://localhost:9090/todos', {
        method: 'GET',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
      });
      if (!response.ok) {
        throw new Error('Failed to fetch to-dos');
      }
      const data: ToDo[] = await response.json();
      setToDos(data);
      return data; // Return the fetched data
    } catch (error) {
      if (error instanceof Error) {
        setError(error.message);
      } else {
        setError('An unknown error occurred');
      }
      return []; // Return an empty array on error
    } finally {
      setLoading(false);
    }
  };

  /**
   * Fetches a single ToDo item by ID.
   * 
   * @param {number} id - The ID of the ToDo item to fetch.
   * @returns {Promise<ToDo>} - A promise that resolves to the fetched ToDo item.
   */
  const fetchToDo = useCallback(async (id: number): Promise<ToDo> => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch(`http://localhost:9090/todos/${id}`, {
        method: 'GET',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
      });
      if (!response.ok) {
        throw new Error('Failed to fetch to-do');
      }
      return await response.json();
    } catch (error) {
      if (error instanceof Error) {
        setError(error.message);
      } else {
        setError('An unknown error occurred');
      }
      throw error; // Rethrow the error so that the caller can handle it
    } finally {
      setLoading(false);
    }
  }, []);

  /**
   * Fetches filtered ToDo items based on text, priority, and done status.
   * 
   * @param {string} text - The text to filter by.
   * @param {string} priority - The priority to filter by.
   * @param {string} done - The done status to filter by.
   */
  const fetchFilteredData = async (text: string, priority: string, done: string) => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch(`http://localhost:9090/todos/filter/${text}-${priority}-${done}`, {
        method: 'GET',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
      });
      if (!response.ok) {
        throw new Error('Failed to fetch filtered to-dos');
      }
      const data = await response.json();
      setToDos(data);
    } catch (error) {
      if (error instanceof Error) {
        setError(error.message);
      } else {
        setError('An unknown error occurred');
      }
    } finally {
      setLoading(false);
    }
  };

  /**
   * Adds a new ToDo item.
   * 
   * @param {Omit<ToDo, 'id'>} newToDo - The new ToDo item to add.
   */
  const addToDo = async (newToDo: Omit<ToDo, 'id'>) => {
    const response = await fetch('http://localhost:9090/todos', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(newToDo),
    });

    if (!response.ok) {
      throw new Error('Failed to add to-do');
    }

    const data: ToDo = await response.json();
    setToDos((prevToDos) => [...prevToDos, data]);
  };

  /**
   * Updates an existing ToDo item by ID.
   * 
   * @param {number} id - The ID of the ToDo item to update.
   * @param {Partial<ToDo>} updatedToDo - The updated ToDo item data.
   */
  const updateToDo = async (id: number, updatedToDo: Partial<ToDo>) => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch(`http://localhost:9090/todos/${id}`, {
        method: 'PUT',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedToDo),
      });
      if (!response.ok) {
        throw new Error('Failed to update to-do');
      }
      const data = await response.json();
      setToDos((prevToDos) =>
        prevToDos.map((todo) => (todo.id === id ? data : todo))
      );
    } catch (error) {
      if (error instanceof Error) {
        setError(error.message);
      } else {
        setError('An unknown error occurred');
      }
    } finally {
      setLoading(false);
    }
  };

  /**
   * Deletes a ToDo item by ID.
   * 
   * @param {number} id - The ID of the ToDo item to delete.
   */
  const deleteToDo = async (id: number) => {
    setLoading(true);
    setError(null);
    try {
      const response = await fetch(`http://localhost:9090/todos/${id}`, {
        method: 'DELETE',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
      });
      if (!response.ok) {
        throw new Error('Failed to delete to-do');
      }
      setToDos((prevToDos) => prevToDos.filter((todo) => todo.id !== id));
    } catch (error) {
      if (error instanceof Error) {
        setError(error.message);
      } else {
        setError('An unknown error occurred');
      }
    } finally {
      setLoading(false);
    }
  };

  return {
    toDos,
    loading,
    error,
    fetchToDos,
    fetchToDo,
    fetchFilteredData,
    addToDo,
    updateToDo,
    deleteToDo,
  };
};

export default useToDo;