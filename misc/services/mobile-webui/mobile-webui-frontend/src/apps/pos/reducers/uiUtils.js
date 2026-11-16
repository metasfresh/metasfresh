import { determineApplicationState } from './commonUtils';

export const getModalFromState = ({ applicationState, globalState }) => {
  const applicationStateEff = determineApplicationState({ applicationState, globalState });
  return applicationStateEff.modal;
};

export const getPanelFromState = ({ applicationState, globalState }) => {
  const applicationStateEff = determineApplicationState({ applicationState, globalState });
  return applicationStateEff.panel;
};

export const getCashTenderedDraftFromState = ({ applicationState, globalState, payment_uuid }) => {
  const applicationStateEff = determineApplicationState({ applicationState, globalState });
  const draft = applicationStateEff.cashTenderedDraft;
  return payment_uuid && draft?.payment_uuid === payment_uuid ? draft.tenderedEditingAmount : undefined;
};
