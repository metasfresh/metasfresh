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
import Cookies from 'js-cookie';
import { assignWorkplace } from '../../../api/workplace';

const COOKIE_posTerminalId = 'posTerminalId';

export const usePOSTerminal = ({ refresh } = { refresh: false }) => {
  const dispatch = useDispatch();

  const cookiePOSTerminalId = Cookies.get(COOKIE_posTerminalId);
  const posTerminal = useSelector(getPOSTerminalFromGlobalState);

  useEffect(() => {
    // just refresh the cookie
    if (cookiePOSTerminalId) {
      Cookies.set(COOKIE_posTerminalId, cookiePOSTerminalId, { expires: 365 });
    }

    if (cookiePOSTerminalId && !posTerminal.isLoading) {
      if (refresh) {
        dispatch(updatePOSTerminalFromBackend({ posTerminalId: cookiePOSTerminalId }));
      }
    }
  }, [refresh, cookiePOSTerminalId]);

  //const isLoading = posTerminal.isLoading || !posTerminalId || !posTerminalId !== posTerminal.id;

  return {
    ...posTerminal,
    changeStatusToClosing: () => dispatch(posTerminalClosingAction()),
    cancelClosing: () => dispatch(posTerminalClosingCancelAction()),
    openJournal: ({ cashBeginningBalance, openingNote }) => {
      dispatch(openJournal({ posTerminalId: cookiePOSTerminalId, cashBeginningBalance, openingNote }));
    },
    closeJournal: ({ cashClosingBalance, closingNote }) => {
      dispatch(closeJournal({ posTerminalId: cookiePOSTerminalId, cashClosingBalance, closingNote }));
    },
    setPOSTerminalId: (newPOSTerminalId) => {
      Cookies.set(COOKIE_posTerminalId, newPOSTerminalId, { expires: 365 });
      if (newPOSTerminalId !== posTerminal.id) {
        dispatch(updatePOSTerminalFromBackend({ posTerminalId: newPOSTerminalId }));
      }
    },
  };
};
const getPOSTerminalFromGlobalState = (globalState) => {
  const applicationState = getPOSApplicationState(globalState);
  return applicationState?.terminal ?? {};
};
const updatePOSTerminalFromBackend = ({ posTerminalId }) => {
  return (dispatch) => {
    dispatch(posTerminalLoadStartAction({ posTerminalId }));
    posTerminalAPI.getPOSTerminalById(posTerminalId).then((posTerminal) => {
      dispatch(posTerminalLoadDoneAction({ posTerminal }));
      if (posTerminal.workplaceId) {
        assignWorkplace(posTerminal.workplaceId);
      }
    });
  };
};
const posTerminalLoadStartAction = ({ posTerminalId }) => {
  return {
    type: POS_TERMINAL_LOAD_START,
    payload: { posTerminalId },
  };
};
const posTerminalLoadDoneAction = ({ posTerminal }) => {
  return {
    type: POS_TERMINAL_LOAD_DONE,
    payload: { posTerminal },
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
const openJournal = ({ posTerminalId, cashBeginningBalance, openingNote }) => {
  return (dispatch) => {
    return posJournalAPI
      .openJournal({ posTerminalId, cashBeginningBalance, openingNote })
      .then((terminal) => dispatch(posTerminalLoadDoneAction({ terminal })));
  };
};
const closeJournal = ({ posTerminalId, cashClosingBalance, closingNote }) => {
  return (dispatch) => {
    return posJournalAPI
      .closeJournal({ posTerminalId, cashClosingBalance, closingNote })
      .then((terminal) => dispatch(posTerminalLoadDoneAction({ terminal })));
  };
};
