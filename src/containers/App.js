import axios from 'axios';
import counterpart from 'counterpart';
import React, { Component } from 'react';
import { Provider } from 'react-redux';
import { browserHistory } from 'react-router';
import { push, syncHistoryWithStore } from 'react-router-redux';

import '../assets/css/styles.css';
import {
  addNotification,
  languageSuccess,
  logoutSuccess,
  setProcessSaved,
} from '../actions/AppActions';
import { getAvailableLang } from '../api';
import { noConnection } from '../actions/WindowActions';
import { addPlugins } from '../actions/PluginActions';
import PluginsRegistry from '../services/PluginsRegistry';
import { generateHotkeys, ShortcutProvider } from '../components/keyshortcuts';
import CustomRouter from './CustomRouter';
import Translation from '../components/Translation';
import NotificationHandler from '../components/notifications/NotificationHandler';
import { LOCAL_LANG } from '../constants/Constants';
import Auth from '../services/Auth';
import blacklist from '../shortcuts/blacklist';
import keymap from '../shortcuts/keymap';
import configureStore from '../store/configureStore';

const hotkeys = generateHotkeys({ keymap, blacklist });
const store = configureStore(browserHistory);
const history = syncHistoryWithStore(browserHistory, store);
const APP_PLUGINS = PLUGINS ? PLUGINS : [];

export default class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      pluginsLoading: !!APP_PLUGINS.length,
    };

    this.auth = new Auth();
    this.pluginsRegistry = new PluginsRegistry(this);
    window.META_HOST_APP = this;

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
        const errorPrototype = Object.getPrototypeOf(error);

        // This is a canceled request error
        if (errorPrototype && errorPrototype.__CANCEL__) {
          return Promise.reject(error);
        }

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
            const message = data.message ? data.message : '';

            // eslint-disable-next-line no-console
            data.message && console.error(data.message);

            // Chart disabled notifications
            if (
              error.response.request.responseURL.includes('silentError=true')
            ) {
              return;
            }

            store.dispatch(
              addNotification(
                'Error: ' + message.split(' ', 4).join(' ') + '...',
                data.message,
                5000,
                'error',
                errorTitle
              )
            );
          }
        }

        //reset password errors
        if (error.response.request.responseURL.includes('resetPassword')) {
          return Promise.reject(error.response);
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

    if (APP_PLUGINS.length) {
      const plugins = APP_PLUGINS.map(plugin => {
        const waitForChunk = () =>
          import(`@plugins/${plugin}/index.js`)
            .then(module => module)
            .catch(() => {
              // eslint-disable-next-line no-console
              console.error(`Error loading plugin ${plugin}`);
            });

        return new Promise(resolve =>
          waitForChunk().then(file => {
            this.pluginsRegistry.addEntry(plugin, file);
            resolve({ name: plugin, file });
          })
        );
      });

      Promise.all(plugins).then(res => {
        const plugins = res.reduce((prev, current) => prev.concat(current), []);

        if (plugins.length) {
          store.dispatch(addPlugins(plugins));
        }

        plugins.forEach(({ file }) => {
          if (file.reducers && file.reducers.name) {
            store.attachReducers({
              plugins: {
                [`${file.reducers.name}`]: file.reducers.reducer,
              },
            });
          }
        });

        this.setState({
          pluginsLoading: false,
        });
      });
    }
  }

  getRegistry() {
    return this.pluginsRegistry;
  }

  render() {
    if (APP_PLUGINS.length && this.state.pluginsLoading) {
      return null;
    }

    return (
      <Provider store={store}>
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <Translation>
            <NotificationHandler>
              <CustomRouter store={store} history={history} auth={this.auth} />
            </NotificationHandler>
          </Translation>
        </ShortcutProvider>
      </Provider>
    );
  }
}
