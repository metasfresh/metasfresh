import { types, getEnv, destroy } from 'mobx-state-tree';
import { Todo } from './Todo';

const Store = types
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

const fetcher = (url) => window.fetch(url).then((response) => response.json());

export const store = Store.create(
  {
    todos: [
      {
        text: 'Get coffee',
        id: 0,
      },
    ],
  },
  {
    fetch: fetcher,
    alert: (m) => console.log(m), // Noop for demo: window.alert(m)
  }
);
