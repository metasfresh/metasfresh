import axios from 'axios';
import counterpart from 'counterpart';
import React, { Component, useState } from 'react';
// import { Provider } from 'react-redux';
import { useDispatch } from 'react-redux';
// import { createBrowserHistory } from 'history';
// import qhistory from 'qhistory';
// import { stringify, parse } from 'qs';

import '../assets/css/styles.css';
import {
  initCurrentActiveLocale,
  setCurrentActiveLocale,
} from '../utils/locale';
import {
  addNotification,
  logoutSuccess,
  setProcessSaved,
  initHotkeys,
  initKeymap,
  setLanguages,
} from '../actions/AppActions';
import { getAvailableLang } from '../api';
import { noConnection } from '../actions/WindowActions';
import PluginsRegistry from '../services/PluginsRegistry';
import { useAuth } from '../hooks/useAuth';
import Routes from '../routes.js';

import { generateHotkeys, ShortcutProvider } from '../components/keyshortcuts';
import Translation from '../components/Translation';
import NotificationHandler from '../components/notifications/NotificationHandler';
import blacklist from '../shortcuts/blacklist';
import keymap from '../shortcuts/keymap';
import configureStore from '../store/configureStore';

const hotkeys = generateHotkeys({ keymap, blacklist });

const store = configureStore();
const APP_PLUGINS = PLUGINS ? PLUGINS : [];

if (window.Cypress) {
  window.store = store;
}

const App = () => {
// export default class App extends Component {
  // constructor(props) {
  //   super(props);

    // this.state = {
    //   pluginsLoading: !!APP_PLUGINS.length,
    // };
    const [pluginsLoading, setPluginsLoading] = useState(!!APP_PLUGINS.length);
    const auth = useAuth();
    const dispatch = useDispatch();

    // this.auth = new Auth();
    // this.pluginsRegistry = new PluginsRegistry(this);
    // const pluginsRegistry = new PluginsRegistry(this);
    // window.META_HOST_APP = this;

    axios.defaults.withCredentials = true;
    axios.defaults.headers.common['Content-Type'] = 'application/json';

    initCurrentActiveLocale();

    axios.interceptors.response.use(
      function(response) {
        return response;
      },
      function(error) {
        const errorPrototype = Object.getPrototypeOf(error);

        // This is a canceled request error
        if (
          !error ||
          !error.response ||
          !error.response.status ||
          (errorPrototype && errorPrototype.__CANCEL__)
        ) {
          return Promise.reject(error);
        }

        if (!error || !error.response || !error.response.status) {
          dispatch(noConnection(true));
        }

        /*
         * Authorization error
         */
        if (error.response.status == 401) {
          dispatch(setProcessSaved());
          auth.logoutSuccess();
          console.log('dodaj redirect nie wiadomo po co')
          // store.dispatch(push('/login?redirect=true'));
        } else if (error.response.status == 503) {
          dispatch(noConnection(true));
        } else if (error.response.status != 404) {
          if (auth.isLoggedIn) {
            const errorMessenger = (code) => {
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

            dispatch(
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

          dispatch(
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
      }
    );

    getAvailableLang().then((response) => {
      const { defaultValue, values } = response.data;
      const valuesFlatten = values.map((item) => Object.keys(item)[0]);
      if (!store.getState().appHandler.me.language) {
        dispatch(setLanguages(values));
      }
      const lang =
        valuesFlatten.indexOf(navigator.language) > -1
          ? navigator.language
          : defaultValue;

      setCurrentActiveLocale(lang);
    });

    counterpart.setMissingEntryGenerator(() => '');

    dispatch(initKeymap(keymap));
    dispatch(initHotkeys(hotkeys));

    /**
     * this is the part of the application that activates the plugins from the plugins array found in - plugins.js
     * Currently we aren't using any. Due to this we deactivated the code below. (March, 2021). If this is going to
     * change in the future pls kindly activate this part and change the way the imports are made.
     */
    // if (APP_PLUGINS.length) {
    //   const plugins = APP_PLUGINS.map((plugin) => {
    //     const waitForChunk = () =>
    //       import(`@plugins/${plugin}/index.js`)
    //         .then((module) => module)
    //         .catch(() => {
    //           // eslint-disable-next-line no-console
    //           console.error(`Error loading plugin ${plugin}`);
    //         });

    //     return new Promise((resolve) =>
    //       waitForChunk().then((file) => {
    //         this.pluginsRegistry.addEntry(plugin, file);
    //         resolve({ name: plugin, file });
    //       })
    //     );
    //   });

    //   Promise.all(plugins).then((res) => {
    //     const plugins = res.reduce((prev, current) => prev.concat(current), []);

    //     if (plugins.length) {
    //       store.dispatch(addPlugins(plugins));
    //     }

    //     plugins.forEach(({ file }) => {
    //       if (file.reducers && file.reducers.name) {
    //         store.attachReducers({
    //           plugins: {
    //             [`${file.reducers.name}`]: file.reducers.reducer,
    //           },
    //         });
    //       }
    //     });

    //     this.setState({
    //       pluginsLoading: false,
    //     });
    //   });
    // }
  // }

  // const getRegistry = () => {
  //   return pluginsRegistry;
  // }

  // render() {
    if (APP_PLUGINS.length && pluginsLoading) {
      return null;
    }

    // return (
    //   <Provider store={store}>
    //     <ShortcutProvider>
    //       <Translation>
    //         <NotificationHandler>
    //           <ConnectedRouter history={history}>
    //             {getRoutes(
    //               store,
    //               this.auth,
    //               store.getState().pluginsHandler.files
    //             )}
    //           </ConnectedRouter>
    //         </NotificationHandler>
    //       </Translation>
    //     </ShortcutProvider>
    //   </Provider>
    // );
    // return (
    //   <Provider store={store}>
    //     <ShortcutProvider>
    //       <Translation>
    //         <NotificationHandler>
    //           <ConnectedRouter history={history} noInitialPop>
    //             <Routes dispatch={store.dispatch} auth={this.auth} />
    //           </ConnectedRouter>
    //         </NotificationHandler>
    //       </Translation>
    //     </ShortcutProvider>
    //   </Provider>
    // );
    // return (
    //   <Provider store={store}>
    //     <ProvideAuth>
    //       <ShortcutProvider>
    //         <Translation>
    //           <NotificationHandler>
    //             <Routes />
    //           </NotificationHandler>
    //         </Translation>
    //       </ShortcutProvider>
    //     </ProvideAuth>
    //   </Provider>
    // );
  return (
    <ShortcutProvider>
      <Translation>
        <NotificationHandler>
          <Routes />
        </NotificationHandler>
      </Translation>
    </ShortcutProvider>
  );
};

export default App;
