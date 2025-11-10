import * as huManagerApp from './huManager';
import * as scanAnythingApp from './scanAnything';
import * as workplaceManagerApp from './workplaceManager';
import * as workstationManagerApp from './workstationManager';
import * as pickingApp from './picking';
import * as posApp from './pos';
import * as huConsolidationApp from './huConsolidation';
import * as inventoryApp from './inventory';
import { toastError } from '../utils/toast';
import { appLaunchersLocation } from '../routes/launchers';

const registeredApplications = {};

const registerApplication = ({
  applicationId,
  routes,
  messages,
  isFullScreen,
  startApplication,
  startApplicationByQRCode,
  reduxReducer,
  onWFActivityCompleted,
}) => {
  const applicationInfo = {
    applicationId,
    routes,
    messages,
    isFullScreen,
    startApplication,
    startApplicationByQRCode,
    reduxReducer,
    onWFActivityCompleted,
  };
  registeredApplications[applicationId] = applicationInfo;

  console.log(`Registered application ${applicationId}`, { applicationInfo });
  // console.log('=>registeredApplications', registeredApplications);
};

export const isApplicationFullScreen = (applicationId) => {
  return !!registeredApplications?.[applicationId]?.isFullScreen;
};

const getApplicationStartFunction = (applicationId) => {
  return registeredApplications[applicationId]?.startApplication;
};

export const getApplicationRoutes = () => {
  const result = [];

  Object.values(registeredApplications).forEach((applicationDescriptor) => {
    if (Array.isArray(applicationDescriptor.routes)) {
      applicationDescriptor.routes.forEach((route) => {
        result.push({
          applicationId: applicationDescriptor.applicationId,
          ...route,
        });
      });
    }
  });

  return result;
};

export const getApplicationMessages = () => {
  return Object.values(registeredApplications).reduce((result, applicationDescriptor) => {
    if (applicationDescriptor.messages) {
      Object.keys(applicationDescriptor.messages).forEach((locale) => {
        if (!result[locale]) {
          result[locale] = {};
        }

        result[locale][applicationDescriptor.applicationId] = applicationDescriptor.messages[locale];
      });
    }

    return result;
  }, {});
};

export const getApplicationReduxReducers = () => {
  return Object.values(registeredApplications).reduce((result, applicationDescriptor) => {
    if (applicationDescriptor.reduxReducer) {
      result[computeApplicationStateKey(applicationDescriptor.applicationId)] = applicationDescriptor.reduxReducer;
    }
    return result;
  }, {});
};

export const getApplicationState = (globalState, applicationId) => {
  return globalState?.[computeApplicationStateKey(applicationId)] ?? {};
};

const computeApplicationStateKey = (applicationId) => 'applications/' + applicationId;

export const fireWFActivityCompleted = ({ applicationId, defaultAction, ...params }) => {
  const onWFActivityCompleted = registeredApplications[applicationId]?.onWFActivityCompleted;
  return (dispatch, getState) => {
    if (onWFActivityCompleted) {
      onWFActivityCompleted({ applicationId, defaultAction, ...params, dispatch, getState });
    } else {
      defaultAction?.();
    }
  };
};

export const startApplicationById = ({ applicationId, dispatch, history }) => {
  const startApplicationFunc = getApplicationStartFunction(applicationId);
  if (startApplicationFunc) {
    startApplicationFunc({ dispatch, history });
  } else {
    history.push(appLaunchersLocation({ applicationId }));
  }
};

export const startApplicationByScannedCode = async ({
  scannedBarcode,
  callerApplicationId,
  onlyApplicationIds,
  dispatch,
  history,
}) => {
  for (const applicationDescriptor of Object.values(registeredApplications)) {
    const startApplicationByQRCodeFn = applicationDescriptor.startApplicationByQRCode;
    if (!startApplicationByQRCodeFn) {
      continue;
    }

    if (onlyApplicationIds && !onlyApplicationIds.includes(applicationDescriptor.applicationId)) {
      console.log(
        `Skipping ${applicationDescriptor.applicationId} because it is not in the list of allowed applications`
      );
      continue;
    }

    try {
      console.log(`Trying to start ${applicationDescriptor.applicationId}`);
      const result = await startApplicationByQRCodeFn({
        qrCode: scannedBarcode,
        callerApplicationId,
        dispatch,
        history,
      });
      console.log(`Got result for ${applicationDescriptor.applicationId}: ${result}`);
      if (result === undefined || result === true) {
        console.log('Start OK');
        return;
      }
    } catch (error) {
      toastError({ axiosError: error, fallbackMessageKey: 'error.qrCode.invalid', context: { scannedBarcode } });
      return;
    }
  }

  // No application can handle given scannedBarcode => ERROR
  toastError({ messageKey: 'error.qrCode.invalid', context: { scannedBarcode } });
};

//
// SETUP
//

registerApplication(huManagerApp.applicationDescriptor);
registerApplication(scanAnythingApp.applicationDescriptor);
registerApplication(workplaceManagerApp.applicationDescriptor);
registerApplication(workstationManagerApp.applicationDescriptor);
registerApplication(pickingApp.applicationDescriptor);
registerApplication(posApp.applicationDescriptor);
registerApplication(huConsolidationApp.applicationDescriptor);
registerApplication(inventoryApp.applicationDescriptor);
