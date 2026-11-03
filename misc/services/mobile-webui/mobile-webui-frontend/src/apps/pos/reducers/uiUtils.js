import { determineApplicationState } from './commonUtils';

export const getModalFromState = ({ applicationState, globalState }) => {
  const applicationStateEff = determineApplicationState({ applicationState, globalState });
  return applicationStateEff.modal;
};

export const getPanelFromState = ({ applicationState, globalState }) => {
  const applicationStateEff = determineApplicationState({ applicationState, globalState });
  return applicationStateEff.panel;
};
