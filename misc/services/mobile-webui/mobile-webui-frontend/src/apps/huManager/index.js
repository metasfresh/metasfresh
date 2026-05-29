import { clearLoadedData, handlingUnitLoaded } from './actions';

import messages_en from './i18n/en.json';
import messages_de from './i18n/de.json';
import { huManagerReducer } from './reducers';
import { huManagerLocation, huManagerRoutes } from './routes';
import * as api from './api';
import { APPLICATION_ID } from './constants';
import { isHUQRCode } from '../../utils/qrCode/hu';

export const applicationDescriptor = {
  applicationId: APPLICATION_ID,
  routes: huManagerRoutes,
  messages: {
    en: messages_en,
    de: messages_de,
  },
  startApplication: ({ dispatch, history }) => {
    dispatch(clearLoadedData());
    history.push(huManagerLocation());
  },
  startApplicationByQRCode: async ({ qrCode, dispatch, history }) => {
    let handlingUnitInfo;
    try {
      handlingUnitInfo = await api.getHUByQRCode(qrCode);
    } catch (error) {
      if (isHUQRCode(qrCode)) {
        throw error;
      } else {
        console.log(
          'Failed resolving scanned code but because we are not sure it shall be an HU, we are just returning false',
          { qrCode, error }
        );
        return false;
      }
    }

    dispatch(handlingUnitLoaded({ handlingUnitInfo }));
    history.push(huManagerLocation());
  },
  reduxReducer: huManagerReducer,
};
