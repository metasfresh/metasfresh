import { CLOSE_MODAL, CLOSE_PANEL, SHOW_MODAL, SHOW_PANEL } from '../actionTypes';

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
  }

  return applicationState;
}
