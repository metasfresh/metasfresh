import axios from 'axios';
import counterpart from 'counterpart';
import React, { Component } from 'react';
import { Provider } from 'react-redux';
import { browserHistory, Router } from 'react-router';
import { push, syncHistoryWithStore } from 'react-router-redux';

import {
  addNotification,
  getAvailableLang,
  languageSuccess,
  logoutSuccess,
  setProcessSaved,
} from '../actions/AppActions';
import { noConnection } from '../actions/WindowActions';
import '../assets/css/styles.css';
import { generateHotkeys, ShortcutProvider } from '../components/shortcuts';
import Translation from '../components/Translation';
import NotificationHandler from '../components/notifications/NotificationHandler';
import { LOCAL_LANG } from '../constants/Constants';
import { getRoutes } from '../routes.js';
import Auth from '../services/Auth';
import blacklist from '../shortcuts/blacklist';
import keymap from '../shortcuts/keymap';
import configureStore from '../store/configureStore';

const hotkeys = generateHotkeys({ keymap, blacklist });
const store = configureStore(browserHistory);
const history = syncHistoryWithStore(browserHistory, store);

export default class App extends Component {
  constructor() {
    super();

    this.auth = new Auth();

    axios.defaults.withCredentials = true;
    axios.defaults.headers.common['Content-Type'] = 'application/json';

    const cachedLang = localStorage.getItem(LOCAL_LANG);
    if (cachedLang) {
      languageSuccess(cachedLang);
    }

    axios.interceptors.response.use(
      function(response) {
        return response;
      },
      function(error) {
        if (!error.response) {
          store.dispatch(noConnection(true));
        }

        /*
             * Authorization error
             */
        if (error.response.status == 401) {
          store.dispatch(setProcessSaved());
          logoutSuccess(this.auth);
          store.dispatch(push('/login?redirect=true'));
        } else if (error.response.status == 503) {
          store.dispatch(noConnection(true));
        } else if (error.response.status != 404) {
          if (localStorage.isLogged) {
            const errorMessenger = code => {
              switch (code) {
                case 500:
                  return 'Server error';
                case 400:
                  return 'Client error';
              }
            };
            const { data, status } = error.response;

            const errorTitle = errorMessenger(status);

            // eslint-disable-next-line no-console
            console.error(data.message);

            // Chart disabled notifications
            if (
              error.response.request.responseURL.includes('silentError=true')
            ) {
              return;
            }

            store.dispatch(
              addNotification(
                'Error: ' + data.message.split(' ', 4).join(' ') + '...',
                data.message,
                5000,
                'error',
                errorTitle
              )
            );
          }
        }

        if (error.response.request.responseURL.includes('showError=true')) {
          const { data } = error.response;

          store.dispatch(
            addNotification(
              'Error: ' + data.message.split(' ', 4).join(' ') + '...',
              data.message,
              5000,
              'error',
              ''
            )
          );
        } else {
          return Promise.reject(error);
        }
      }.bind(this)
    );

    getAvailableLang().then(response => {
      const { defaultValue, values } = response.data;
      const valuesFlatten = values.map(item => Object.keys(item)[0]);
      const lang =
        valuesFlatten.indexOf(navigator.language) > -1
          ? navigator.language
          : defaultValue;

      languageSuccess(lang);
    });

    counterpart.setMissingEntryGenerator(() => '');
  }

  render() {
    return (
      <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
        <Provider store={store}>
          <Translation>
            <NotificationHandler>
              <Router history={history} routes={getRoutes(store, this.auth)} />
            </NotificationHandler>
          </Translation>
        </Provider>
      </ShortcutProvider>
    );
  }
}
