import { types, getEnv, destroy } from 'mobx-state-tree';
import { Todo } from './Todo';

export const Store = types
  .model('Store', {
    todos: types.array(Todo),
  })
  .views((self) => ({
    get fetch() {
      return getEnv(self).fetch;
    },
    get alert() {
      return getEnv(self).alert;
    },
  }))
  .actions((self) => ({
    addTodo(text) {
      const id = self.todos.reduce((maxId, todo) => Math.max(todo.id, maxId), -1) + 1;
      self.todos.unshift({
        id,
        text,
      });
    },
    removeTodo(todo) {
      destroy(todo);
    },
  }));
