import { useContext, createContext } from 'react';
import { types, getEnv, destroy, Instance, onSnapshot } from 'mobx-state-tree';
import { Todo } from './Todo';
import { Day } from './Day';

export const Store = types
  .model('Store', {
    todos: types.array(Todo),
    day: Day,
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

let initialState = Store.create(
  {
    todos: [
      {
        text: 'Get coffee',
        id: 0,
      },
    ],
    day: {
      currentDay: 'today',
    },
  },
  {
    fetch: fetcher,
    alert: (m) => console.log(m), // Noop for demo: window.alert(m)
  }
);

const data = localStorage.getItem('initialState');
if (data) {
  const json = JSON.parse(data);
  if (Store.is(json)) {
    initialState = Store.create(json);
  }
}

export const store = initialState;
/**
 * Create a localStorage entry from the snapshot
 */
onSnapshot(store, (snapshot) => {
  console.log('Snapshot: ', snapshot);
  localStorage.setItem('initialState', JSON.stringify(snapshot));
});

/**
 * we can use also useMst through the app to reach the data by using the context
 */
export type RootInstance = Instance<typeof Store>;
const RootStoreContext = createContext<null | RootInstance>(null);

export const Provider = RootStoreContext.Provider;
export function useMst() {
  const store = useContext(RootStoreContext);
  if (store === null) {
    throw new Error('Please add a context provider');
  }
  return store;
}
