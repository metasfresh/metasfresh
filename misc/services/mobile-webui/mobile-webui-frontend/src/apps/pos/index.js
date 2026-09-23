import { APPLICATION_ID } from './constants';
import { posLocation, posRoutes } from './routes';
import messages_en from './i18n/en.json';
import messages_de from './i18n/de.json';
import { posReducer } from './reducers';

export const applicationDescriptor = {
  applicationId: APPLICATION_ID,
  routes: posRoutes,
  messages: {
    en: messages_en,
    de: messages_de,
  },
  isFullScreen: true,
  // startApplicationById (apps/index.js) calls this synchronously as startApplication({ dispatch, history })
  // and does not dispatch/await its return value - so it must navigate directly (matching huManager's
  // startApplication), never return a thunk for the caller to dispatch. The previous shape
  // (`() => (dispatch) => dispatch(push(...))`) returned an un-dispatched thunk and silently did nothing:
  // tapping "POS" on the applications list never navigated anywhere.
  startApplication: ({ history }) => {
    history.push(posLocation());
  },
  reduxReducer: posReducer,
};
