import {
  POS_TERMINAL_CLOSING,
  POS_TERMINAL_CLOSING_CANCEL,
  POS_TERMINAL_LOAD_DONE,
  POS_TERMINAL_LOAD_START,
} from '../actionTypes';

export function posTerminalReducer(applicationState, action) {
  switch (action.type) {
    case POS_TERMINAL_LOAD_START: {
      return updatePOSTerminalToState({
        applicationState,
        props: { isLoading: true },
      });
    }
    case POS_TERMINAL_LOAD_DONE: {
      const { posTerminal } = action.payload;
      return setPOSTerminalToState({
        applicationState,
        newTerminal: posTerminal,
      });
    }
    case POS_TERMINAL_CLOSING: {
      return updatePOSTerminalToState({
        applicationState,
        props: (terminal) => ({
          isCashJournalClosing: !!terminal.cashJournalOpen, // closing makes sense only if the journal is already open
        }),
      });
    }
    case POS_TERMINAL_CLOSING_CANCEL: {
      return updatePOSTerminalToState({
        applicationState,
        props: {
          isCashJournalClosing: false,
        },
      });
    }
  }

  return applicationState;
}

//
//
//
//
//
//
//
//
//

const updatePOSTerminalToState = ({ applicationState, props }) => {
  const currentTerminal = applicationState.terminal ?? {};

  let newProps;
  if (typeof props === 'function') {
    newProps = props(currentTerminal);
  } else {
    newProps = props;
  }

  return {
    ...applicationState,
    terminal: {
      ...currentTerminal,
      ...newProps,
    },
  };
};

const setPOSTerminalToState = ({ applicationState, newTerminal }) => {
  // preserve closing flag.
  // also, closing makes sense only if the journal is already open.
  const currentTerminal = applicationState?.terminal ?? {};
  const isCashJournalClosing =
    currentTerminal.isCashJournalClosing && newTerminal?.cashJournalOpen && newTerminal.id === currentTerminal.id;

  const newTerminalEffective = {
    ...newTerminal,
    isCashJournalClosing,
    isLoading: false,
    isLoaded: true,
  };
  delete newTerminalEffective.openOrders;

  return {
    ...applicationState,
    terminal: newTerminalEffective,
  };
};
