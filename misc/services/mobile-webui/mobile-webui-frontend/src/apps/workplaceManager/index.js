import messages_en from './i18n/en.json';
import messages_de from './i18n/de.json';
import { appLocation, routes } from './routes';
import { APPLICATION_ID } from './constants';
import { isWorkplaceQRCode } from '../../utils/qrCode/workplace';
import * as api from '../../api/workplace';

export const applicationDescriptor = {
  applicationId: APPLICATION_ID,
  routes,
  messages: {
    en: messages_en,
    de: messages_de,
  },
  startApplication: ({ history }) => {
    history.push(appLocation());
  },
  startApplicationByQRCode: async ({ qrCode, callerApplicationId, history }) => {
    if (!isWorkplaceQRCode(qrCode)) {
      return false;
    }

    await api.getWorkplaceByQRCode(qrCode); // just to make sure the workplace QR code is valid

    history.push(appLocation({ qrCode, callerApplicationId }));
  },
  //reduxReducer: () => {},
};
