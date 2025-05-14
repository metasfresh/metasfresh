import { CLOSE_MODAL, SHOW_MODAL } from '../actionTypes';

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
  }

  return applicationState;
}
