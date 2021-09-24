import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorkerRegistration from './serviceWorkerRegistration';
import reportWebVitals from './reportWebVitals';
import { Provider } from 'react-redux';
import { ConnectedRouter } from 'connected-react-router';
import { Route, Switch } from 'react-router';
import { store, history } from './store/store';
import './assets/index.scss';
import { networkStatusOffline, networkStatusOnline } from './actions/NetworkActions';

export const globalStore = store();

ReactDOM.render(
  <React.StrictMode>
    <Provider store={globalStore}>
      <ConnectedRouter history={history}>
        <>
          <Switch>
            <Route exact path="/" render={() => <App />} />
            <Route
              exact
              path="/test"
              render={() => (
                <>
                  <h1>test</h1>
                </>
              )}
            />
            <Route render={() => <div>Not found</div>} />
          </Switch>
        </>
      </ConnectedRouter>
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

const broadcast = new BroadcastChannel('network-status-channel');

/**
 * Listen to the response broadcasted by the service worker when the network status changes
 * This is needed for the special case when user refreshes the page while being off
 */
broadcast.onmessage = (event) => {
  event.data.payload === 'offline' && globalStore.dispatch(networkStatusOffline());
};

window.addEventListener('offline', () => {
  globalStore.dispatch(networkStatusOffline());
});

window.addEventListener('online', () => {
  globalStore.dispatch(networkStatusOnline());
});

window.addEventListener('beforeinstallprompt', (e) => {
  let installEvent = e;
  console.log('Install event triggered:', installEvent);
  // e.preventDefault(); - this is going to disable the prompt if uncommented !
  // See if the app is already installed, in that case, do nothing
  if (window.matchMedia && window.matchMedia('(display-mode: standalone)').matches) {
    // Already installed
    return false;
  }
});
