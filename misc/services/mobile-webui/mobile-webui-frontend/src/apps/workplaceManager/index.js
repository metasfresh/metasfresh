import messages_en from './i18n/en.json';
import messages_de from './i18n/de.json';
import { push } from 'connected-react-router';
import { workplaceManagerLocation, workplaceManagerRoutes } from './routes';

export const applicationDescriptor = {
  applicationId: 'workplaceManager',
  routes: workplaceManagerRoutes,
  messages: {
    en: messages_en,
    de: messages_de,
  },
  startApplication: () => {
    return (dispatch) => {
      dispatch(push(workplaceManagerLocation()));
    };
  },
  // startApplicationByQRCode: ({ qrCode }) => {
  // },
  //reduxReducer: () => {},
};
