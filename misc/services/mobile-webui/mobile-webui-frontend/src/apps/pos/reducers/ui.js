import { CLOSE_MODAL, CLOSE_PANEL, SET_CASH_TENDERED_DRAFT, SHOW_MODAL, SHOW_PANEL } from '../actionTypes';

export function uiReducer(applicationState, action) {
  switch (action.type) {
    case SHOW_MODAL: {
      const { modal } = action.payload;
      if (applicationState.modal === modal) {
        return applicationState;
      }
      return {
        ...applicationState,
        modal,
      };
    }
    case CLOSE_MODAL: {
      const { ifModal } = action.payload;
      if (ifModal && ifModal !== applicationState.modal) {
        return applicationState;
      }
      return {
        ...applicationState,
        modal: null,
      };
    }
    case SHOW_PANEL: {
      const { panel } = action.payload;
      if (applicationState.panel === panel) {
        return applicationState;
      }
      return {
        ...applicationState,
        panel,
      };
    }
    case CLOSE_PANEL: {
      if (!applicationState.panel) {
        return applicationState;
      }
      return {
        ...applicationState,
        panel: null,
      };
    }
    case SET_CASH_TENDERED_DRAFT: {
      const { payment_uuid, tenderedEditingAmount } = action.payload;
      const current = applicationState.cashTenderedDraft;
      if (current?.payment_uuid === payment_uuid && current?.tenderedEditingAmount === tenderedEditingAmount) {
        return applicationState;
      }
      // only one cash payment dialog is open at a time, so one draft is enough
      return {
        ...applicationState,
        cashTenderedDraft: { payment_uuid, tenderedEditingAmount },
      };
    }
  }

  return applicationState;
}
