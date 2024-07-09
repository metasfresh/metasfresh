import messages_en from './i18n/en.json';
import messages_de from './i18n/de.json';
import { push } from 'connected-react-router';
import { appLocation, routes } from './routes';
import { APPLICATION_ID } from './constants';

export const applicationDescriptor = {
  applicationId: APPLICATION_ID,
  routes,
  messages: {
    en: messages_en,
    de: messages_de,
  },
  startApplication: () => {
    return (dispatch) => {
      dispatch(push(appLocation()));
    };
  },
  startApplicationByQRCode: ({ qrCode, parent }) => {
    return (dispatch) => {
      dispatch(push(appLocation({ qrCode, parent })));
    };
  },
  //reduxReducer: () => {},
};
