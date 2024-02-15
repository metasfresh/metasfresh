import messages_en from './i18n/en.json';
import messages_de from './i18n/de.json';
import { scanAnythingReducer } from './reducers';
import { scanAnythingLocation, scanAnythingRoutes } from './routes';
import { push } from 'connected-react-router';

export const applicationDescriptor = {
  applicationId: 'scanAnything',
  routes: scanAnythingRoutes,
  messages: {
    en: messages_en,
    de: messages_de,
  },
  startApplication: () => {
    return (dispatch) => {
      dispatch(push(scanAnythingLocation()));
    };
  },
  reduxReducer: scanAnythingReducer,
};
