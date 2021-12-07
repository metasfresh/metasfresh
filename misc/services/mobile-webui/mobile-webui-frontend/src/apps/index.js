import * as inventoryDisposalApp from './inventoryDisposal/index';

const registeredApplications = {};

const registerApplication = ({ applicationId, routes, messages, startApplication }) => {
  registeredApplications[applicationId] = {
    applicationId,
    routes,
    messages,
    startApplication,
  };

  console.log(`Registered application ${applicationId}`);
  //console.log('=>registeredApplications', registeredApplications);
};

export const getApplicationStartFunction = (applicationId) => {
  return registeredApplications[applicationId]?.startApplication;
};

export const getApplicationRoutes = () => {
  return Object.values(registeredApplications).reduce((result, applicationDescriptor) => {
    return Array.isArray(applicationDescriptor.routes) ? result.concat(applicationDescriptor.routes) : result;
  }, []);
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

//
// SETUP
//

registerApplication(inventoryDisposalApp.applicationDescriptor);
