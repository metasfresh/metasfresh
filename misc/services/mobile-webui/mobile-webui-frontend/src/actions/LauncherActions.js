import { POPULATE_LAUNCHERS_COMPLETE, POPULATE_LAUNCHERS_START } from '../constants/LaunchersActionTypes';

export const populateLaunchersStart = ({ applicationId, filterByQRCode }) => {
  return {
    type: POPULATE_LAUNCHERS_START,
    payload: { applicationId, filterByQRCode, timestamp: Date.now() },
  };
};

export const populateLaunchersComplete = ({ applicationId, applicationLaunchers }) => {
  //console.trace('populateLaunchersComplete', { applicationId, applicationLaunchers });
  return {
    type: POPULATE_LAUNCHERS_COMPLETE,
    payload: { applicationId, applicationLaunchers },
  };
};
