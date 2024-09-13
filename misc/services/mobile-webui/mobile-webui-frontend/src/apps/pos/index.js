import { APPLICATION_ID } from './constants';
import { posLocation, posRoutes } from './routes';
import messages_en from './i18n/en.json';
import messages_de from './i18n/de.json';
import { posReducer } from './reducers';
import { push } from 'connected-react-router';

export const applicationDescriptor = {
  applicationId: APPLICATION_ID,
  routes: posRoutes,
  messages: {
    en: messages_en,
    de: messages_de,
  },
  startApplication: () => {
    return (dispatch) => {
      dispatch(push(posLocation()));
    };
  },
  reduxReducer: posReducer,
};
