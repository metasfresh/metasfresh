import axios from 'axios';
import counterpart from 'counterpart';
import React, { useEffect } from 'react';
import { useDispatch, useSelector, useStore } from 'react-redux';

import '../assets/css/styles.scss';
import {
  initCurrentActiveLocale,
  setCurrentActiveLocale,
} from '../utils/locale';
import {
  addNotification,
  setProcessSaved,
  initHotkeys,
  initKeymap,
  setLanguages,
} from '../actions/AppActions';
import { getAvailableLang } from '../api/login';
import { connectionError } from '../actions/AppActions';
// import PluginsRegistry from '../services/PluginsRegistry';
import { useAuth } from '../hooks/useAuth';
import useConstructor from '../hooks/useConstructor';
import history from '../services/History';
import Routes from '../routes';
import { NO_CONNECTION_ERROR } from '../constants/Constants';
import { generateHotkeys, ShortcutProvider } from '../components/keyshortcuts';
import Translation from '../components/Translation';
import NotificationHandler from '../components/notifications/NotificationHandler';
import blacklist from '../shortcuts/blacklist';
import keymap from '../shortcuts/keymap';
import { getDocSummaryDataFromState } from '../reducers/windowHandlerUtils';

const hotkeys = generateHotkeys({ keymap, blacklist });

// const APP_PLUGINS = PLUGINS ? PLUGINS : [];

const computeTitleFromState = (state) => {
  if (state?.appHandler?.isLogged) {
    const breadcrumb = state?.menuHandler?.breadcrumb;
    if (breadcrumb?.length > 0) {
      const lastElement = breadcrumb.slice(-1)[0];
      const caption = lastElement?.caption;
      if (caption) {
        let title = caption;
        const docSummary = getDocSummaryDataFromState(state)?.value;
        if (docSummary) {
          title += ' / ' + docSummary;
        }
        return title;
      }
    }

    return 'metasfresh';
  } else {
    return 'Login';
  }
};

/**
 * @file Functional component.
 * @module App
 * Main application component providing navigation, shortcuts, translations, and notifications
 * plus setting some global values and handling global errors.
 */
const App = () => {
  // const [pluginsLoading, setPluginsLoading] = useState(!!APP_PLUGINS.length);
  const auth = useAuth();
  const dispatch = useDispatch();
  const store = useStore();
  const language = useSelector((state) => state.appHandler.me.language);

  useConstructor(() => {
    // this.pluginsRegistry = new PluginsRegistry(this);
    // const pluginsRegistry = new PluginsRegistry(this);
    // window.META_HOST_APP = this;

    axios.defaults.withCredentials = true;
    axios.defaults.headers.common['Content-Type'] = 'application/json';

    initCurrentActiveLocale();

    axios.interceptors.response.use(
      function (response) {
        return response;
      },
      function (error) {
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
          dispatch(connectionError({ errorType: NO_CONNECTION_ERROR }));
        }

        /*
         * Authorization error
         */
        if (error.response.status == 401) {
          if (
            !location.pathname.includes('login') &&
            !auth.authRequestPending()
          ) {
            dispatch(setProcessSaved());

            auth.setRedirectRoute(location.pathname);

            // we got not authenticated error, but locally still have the authenticated flag truthy
            // (ie user logged out in another window, or session timed out)
            if (auth.isLoggedIn || store.getState().appHandler.isLogged) {
              auth.logout().finally(() => {
                history.push('/login');
              });
            } else {
              history.push('/login');
            }
          }
        } else if (
          error.response.status === 500 &&
          error.response.data.path.includes('/authenticate')
        ) {
          /*
           * User already logged in on the backend side or wrong
           * login token
           */

          // if user types in incorrect token, there's no way for us to tell if he's
          // already authenticated or not. So it's safest to reset the login process
          if (error.response.data.message.includes('Invalid token')) {
            return auth
              .logout()
              .then(() => {
                history.push('/login');
              })
              .catch((err) => {
                console.error('App.checkAuthentication error: ', err);
              });
          }

          //if not logged in
          if (!auth.isLoggedIn && !store.getState().appHandler.isLogged) {
            return auth.checkAuthentication().then((authenticated) => {
              if (authenticated) {
                history.push(location.pathname);
              }
            });
          }
        } else if (error.response.status == 502) {
          return; // silent error for 502 bad gateway (otherwise we will get a bunch of notif from the retries)
        } else if (error.response.status == 503) {
          dispatch(connectionError({ errorType: NO_CONNECTION_ERROR }));
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
            console.log(`Got error: ${message}`, { error });

            // Chart disabled notifications
            if (
              error.response.request.responseURL.includes('silentError=true')
            ) {
              return;
            }

            if (data.userFriendlyError) {
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

      if (!language) {
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

    // const getRegistry = () => {
    //   return pluginsRegistry;
    // }
  }, []);

  // if (APP_PLUGINS.length && pluginsLoading) {
  //   return null;
  // }

  const title = useSelector((state) => computeTitleFromState(state));
  useEffect(() => {
    document.title = title;
  }, [title]);

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
