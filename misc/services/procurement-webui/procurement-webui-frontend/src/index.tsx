import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'mobx-react';
import { observable } from 'mobx';

import './static/index.scss';

import App from './App';
import * as serviceWorkerRegistration from './serviceWorkerRegistration';
import { getMessages } from './api';
import reportWebVitals from './reportWebVitals';
import { RootStoreContext, store } from './models/Store';

const history = {
  snapshots: observable.array([], { deep: false }),
  actions: observable.array([], { deep: false }),
  patches: observable.array([], { deep: false }),
};

// if there is no DEV_SERVER env set up it will use the Mirage mockups - this can be later removed
process.env.DEV_SERVER === 'undefined' && console.log('Please setup the DEV_SERVER env variable');

// get the messages first for i18n
getMessages().then(async (response) => {
  if (response.status === 200 && response.data) {
    const { language, messages } = response.data;
    store.i18n.changeLang(language);
    store.i18n.changeMessages(messages);
  }

  await store.app.getUserSession();

  ReactDOM.render(
    <Provider store={store} history={history}>
      <RootStoreContext.Provider value={store}>
        <App />
      </RootStoreContext.Provider>
    </Provider>,
    document.getElementById('root')
  );
});

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://cra.link/PWA
serviceWorkerRegistration.register();

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
