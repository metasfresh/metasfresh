import * as huManagerApp from './huManager';
import * as scanAnythingApp from './scanAnything';
import * as workplaceManagerApp from './workplaceManager';
import * as workstationManagerApp from './workstationManager';
import * as pickingApp from './picking';

const registeredApplications = {};

const registerApplication = ({
  applicationId,
  routes,
  messages,
  startApplication,
  startApplicationByQRCode,
  reduxReducer,
  onWFActivityCompleted,
}) => {
  registeredApplications[applicationId] = {
    applicationId,
    routes,
    messages,
    startApplication,
    startApplicationByQRCode,
    reduxReducer,
    onWFActivityCompleted,
  };

  console.log(`Registered application ${applicationId}`);
  //console.log('=>registeredApplications', registeredApplications);
};

export const getApplicationStartFunction = (applicationId) => {
  return registeredApplications[applicationId]?.startApplication;
};
export const getApplicationStartByQRCodeFunction = (applicationId) => {
  return registeredApplications[applicationId]?.startApplicationByQRCode;
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
      result['applications/' + applicationDescriptor.applicationId] = applicationDescriptor.reduxReducer;
    }
    return result;
  }, {});
};

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

//
// SETUP
//

registerApplication(huManagerApp.applicationDescriptor);
registerApplication(scanAnythingApp.applicationDescriptor);
registerApplication(workplaceManagerApp.applicationDescriptor);
registerApplication(workstationManagerApp.applicationDescriptor);
registerApplication(pickingApp.applicationDescriptor);
