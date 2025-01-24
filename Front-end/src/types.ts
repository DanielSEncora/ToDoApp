// src/types.ts
export interface ToDo {
  id?: number;
  text: string;
  priority: 'LOW' | 'MEDIUM' | 'HIGH';
  dueDate: string;
  creationDate: string; // ISO format
  doneDate?: string; // ISO format, optional
  done: boolean;
}