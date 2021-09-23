import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';

import App from './App';
import * as serviceWorkerRegistration from './serviceWorkerRegistration';
import reportWebVitals from './reportWebVitals';
import { store, history } from './store/store';
import { networkStatusOffline, networkStatusOnline } from './actions/NetworkActions';

import './index.css';
import './assets/index.scss';

export const globalStore = store();

ReactDOM.render(
  <React.StrictMode>
    <Provider store={globalStore}>
      <App />
    </Provider>
  </React.StrictMode>,
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


window.addEventListener('offline', () => {
  globalStore.dispatch(networkStatusOffline())
});

window.addEventListener('online', () => {
  globalStore.dispatch(networkStatusOnline())
});

// const checkOnlineStatus = async () => {
//   try {
//     const online = await fetch('/favicon.ico');
//     return online.status >= 200 && online.status < 300; 
//   } catch (err) {
//     return false; // we are offline
//   }
// };

// special case when page is refreshed after offline is set
// window.addEventListener('load', async ()  => {
//   const onlineStatus = await checkOnlineStatus();
//   if (onlineStatus) {
//     globalStore.dispatch(networkStatusOnline())
//   } else {
//     globalStore.dispatch(networkStatusOffline())
//   }
// });