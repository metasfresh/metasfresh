import { push } from 'connected-react-router';

import { clearLoadedData, handlingUnitLoaded } from './actions';

import messages_en from './i18n/en.json';
import messages_de from './i18n/de.json';
import { huManagerReducer } from './reducers';
import { huManagerLocation, huManagerRoutes } from './routes';
import * as api from './api';
import { toastError } from '../../utils/toast';
import { APPLICATION_ID } from './constants';

export const applicationDescriptor = {
  applicationId: APPLICATION_ID,
  routes: huManagerRoutes,
  messages: {
    en: messages_en,
    de: messages_de,
  },
  startApplication: () => {
    return (dispatch) => {
      dispatch(clearLoadedData());
      dispatch(push(huManagerLocation()));
    };
  },
  startApplicationByQRCode: ({ qrCode }) => {
    return (dispatch) => {
      api
        .getHUByQRCode(qrCode)
        .then((handlingUnitInfo) => {
          dispatch(handlingUnitLoaded({ handlingUnitInfo }));
          dispatch(push(huManagerLocation()));
        })
        .catch((axiosError) => toastError({ axiosError }));
    };
  },
  reduxReducer: huManagerReducer,
};
