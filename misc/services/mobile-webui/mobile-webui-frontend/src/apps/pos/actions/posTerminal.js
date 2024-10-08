import {
  POS_TERMINAL_CLOSING,
  POS_TERMINAL_CLOSING_CANCEL,
  POS_TERMINAL_LOAD_DONE,
  POS_TERMINAL_LOAD_START,
} from '../actionTypes';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import * as posTerminalAPI from '../api/posTerminal';
import * as posJournalAPI from '../api/posJournal';
import { getPOSApplicationState } from './common';

export const usePOSTerminal = ({ refresh } = { refresh: false }) => {
  const dispatch = useDispatch();
  const posTerminal = useSelector(getPOSTerminalFromGlobalState);

  useEffect(() => {
    if (refresh) {
      updatePOSTerminalFromBackend({ dispatch });
    }
  }, []);

  if (!posTerminal) return { isLoading: true };

  return {
    ...posTerminal,
    updateFromBackend: () => updatePOSTerminalFromBackend({ dispatch }),
    changeStatusToClosing: () => dispatch(posTerminalClosingAction()),
    cancelClosing: () => dispatch(posTerminalClosingCancelAction()),
    openJournal: ({ cashBeginningBalance, openingNote }) => {
      dispatch(openJournal({ cashBeginningBalance, openingNote }));
    },
    closeJournal: ({ cashClosingBalance, closingNote }) => {
      dispatch(closeJournal({ cashClosingBalance, closingNote }));
    },
  };
};
const getPOSTerminalFromGlobalState = (globalState) => {
  const applicationState = getPOSApplicationState(globalState);
  return applicationState?.terminal;
};
const updatePOSTerminalFromBackend = ({ dispatch }) => {
  dispatch(posTerminalLoadStartAction());
  posTerminalAPI.getPOSTerminal().then((terminal) => dispatch(posTerminalLoadDoneAction({ terminal })));
};
const posTerminalLoadStartAction = () => {
  return {
    type: POS_TERMINAL_LOAD_START,
  };
};
const posTerminalLoadDoneAction = ({ terminal }) => {
  return {
    type: POS_TERMINAL_LOAD_DONE,
    payload: { terminal },
  };
};
const posTerminalClosingAction = () => {
  return {
    type: POS_TERMINAL_CLOSING,
  };
};
const posTerminalClosingCancelAction = () => {
  return {
    type: POS_TERMINAL_CLOSING_CANCEL,
  };
};
const openJournal = ({ cashBeginningBalance, openingNote }) => {
  return (dispatch) => {
    return posJournalAPI
      .openJournal({ cashBeginningBalance, openingNote })
      .then((terminal) => dispatch(posTerminalLoadDoneAction({ terminal })));
  };
};
const closeJournal = ({ cashClosingBalance, closingNote }) => {
  return (dispatch) => {
    return posJournalAPI
      .closeJournal({ cashClosingBalance, closingNote })
      .then((terminal) => dispatch(posTerminalLoadDoneAction({ terminal })));
  };
};
