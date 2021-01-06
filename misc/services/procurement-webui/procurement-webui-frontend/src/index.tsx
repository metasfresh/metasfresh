import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from "mobx-react";
import { observable } from "mobx";
import { types, getEnv, getRoot, destroy } from "mobx-state-tree";

import './static/index.scss';
import App from './App';
import * as serviceWorkerRegistration from './serviceWorkerRegistration';
import reportWebVitals from './reportWebVitals';

const Todo = types
  .model({
      text: types.string,
      id: types.identifierNumber
  })
  .actions((self) => ({
    remove() {
      // @ts-ignore: Not sure how to fix this yet
      getRoot(self).removeTodo(self)
    },
    edit(text) {
      self.text = text
    },
  }))

const Store = types
  .model("Store", {
    todos: types.array(Todo)
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
      const id = self.todos.reduce((maxId, todo) => Math.max(todo.id, maxId), -1) + 1
      self.todos.unshift({
        id,
        text
      })
    },
    removeTodo(todo) {
      destroy(todo)
    }
  }));

const fetcher = (url) => window.fetch(url).then((response) => response.json());
const store = Store.create({
  todos: [
    {
      text: "Get coffee",
      id: 0,
    }
  ]},
  {
    fetch: fetcher,
    alert: (m) => console.log(m), // Noop for demo: window.alert(m)
  }
);

const history = {
  snapshots: observable.array([], { deep: false }),
  actions: observable.array([], { deep: false }),
  patches: observable.array([], { deep: false }),
};

ReactDOM.render(
  <Provider store={store} history={history}>
    <App />
  </Provider>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://cra.link/PWA
serviceWorkerRegistration.register();

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
