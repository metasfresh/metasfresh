import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { ProvideAuth } from './hooks/useAuth';
import ApplicationRoot from './containers/applicationRoot/ApplicationRoot';
import { store } from './store/store';
import { load } from 'redux-localstorage-simple';
import { setupCounterpart } from './utils/translations';
import { setupOfflineModeDetector } from './services/offlineModeDetector';
import { setupServiceWorker } from './services/serviceWorker/serviceWorkerRegistration';

import './assets/index.scss';
import '@fortawesome/fontawesome-free/js/all.min';
import { ErrorBoundary } from 'react-error-boundary';
import { logErrorToBackend } from './api/applications';
import ErrorScreen from './components/ErrorScreen';

setupCounterpart();

export const globalStore = store(load());

ReactDOM.render(
  <React.StrictMode>
    <Provider store={globalStore}>
      <ErrorBoundary FallbackComponent={ErrorScreen} onError={logErrorToBackend}>
        <ProvideAuth>
          <ApplicationRoot />
        </ProvideAuth>
      </ErrorBoundary>
    </Provider>
  </React.StrictMode>,
  document.getElementById('root')
);

setupOfflineModeDetector({ globalStore });
setupServiceWorker();

// TODO debug
window.metasfresh_debug = true;
