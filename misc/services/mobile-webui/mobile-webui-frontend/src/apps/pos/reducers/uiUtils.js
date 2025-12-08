import { determineApplicationState } from './commonUtils';

export const getModalFromState = ({ applicationState, globalState }) => {
  const applicationStateEff = determineApplicationState({ applicationState, globalState });
  return applicationStateEff.modal;
};
