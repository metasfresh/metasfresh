import { getApplicationState } from '../../index';
import { APPLICATION_ID } from '../constants';

export const getPOSApplicationState = (globalState) => getApplicationState(globalState, APPLICATION_ID);

export const determineApplicationState = ({ globalState, applicationState }) => {
  let applicationStateEff = applicationState;
  if (applicationState == null && globalState != null) {
    applicationStateEff = getPOSApplicationState(globalState);
  }
  if (applicationStateEff == null) {
    throw 'Cannot determine applicationState';
  }

  return applicationStateEff;
};
