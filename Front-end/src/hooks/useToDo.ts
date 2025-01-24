import { useState, useEffect, useCallback } from 'react';
import { ToDo } from '../types';

const useToDo = () => {
  const [toDos, setToDos] = useState<ToDo[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetchToDos();
  }, []);

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