import { determineApplicationState, getPOSApplicationState } from './commonUtils';
import { useSelector } from 'react-redux';

export const getCurrentOrderFromGlobalState = (globalState) => {
  const applicationState = getPOSApplicationState(globalState);
  const current_uuid = getCurrentOrderUUID({ applicationState });
  if (!current_uuid) {
    return null;
  }

  return getOrderByUUID({ applicationState, order_uuid: current_uuid });
};

export const useOpenOrdersArray = () => {
  return useSelector((globalState) => getOpenOrdersArrayFromGlobalState(globalState));
};
const getOpenOrdersArrayFromGlobalState = (globalState) => {
  const applicationState = getPOSApplicationState(globalState);
  const orders = applicationState?.orders?.byUUID ?? {};
  return Object.values(orders);
};

export const getOrderByUUID = ({ globalState, applicationState, order_uuid }) => {
  const applicationStateEff = determineApplicationState({ globalState, applicationState });
  return applicationStateEff?.orders?.byUUID?.[order_uuid];
};

export const getCurrentOrderUUID = ({ globalState, applicationState }) => {
  const applicationStateEff = determineApplicationState({ globalState, applicationState });
  return applicationStateEff?.orders?.current_uuid;
};
