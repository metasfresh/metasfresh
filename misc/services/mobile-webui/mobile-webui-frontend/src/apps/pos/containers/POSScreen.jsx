import React from 'react';
import './POSScreen.scss';
import Header from './Header';
import POSCashJournalOpenModal from './cash_journal/POSCashJournalOpenModal';
import POSCashJournalClosingModal from './cash_journal/POSCashJournalClosingModal';
import { useOrdersWebsocket } from '../api/orders';
import { useDispatch, useSelector } from 'react-redux';
import { updateOrderFromBackendAction } from '../actions/orders';
import POSTerminalSelectModal from './select_terminal/POSTerminalSelectModal';
import { usePOSTerminal } from '../actions/posTerminal';
import { MODAL_POSTerminalSelect } from '../actions/ui';
import { getModalFromState } from '../reducers/uiUtils';
import { POSContent } from './POSContent';

const POSScreen = () => {
  const dispatch = useDispatch();
  const posTerminal = usePOSTerminal({ refresh: true });
  const posTerminalId = posTerminal.id;

  useOrdersWebsocket({
    posTerminalId,
    onWebsocketMessage: (message) => {
      dispatch(updateOrderFromBackendAction({ order: message.posOrder }));
    },
  });

  const modal = useModal();

  return (
    <div className="pos-screen">
      <Header />
      {modal}
      <POSContent disabled={!!modal} />
    </div>
  );
};

//
//
//
//
//
//
//
//
//

const getCashJournalStatus = (posTerminal) => {
  if (posTerminal?.cashJournalOpen) {
    return posTerminal?.isCashJournalClosing ? 'closing' : 'open';
  } else {
    return 'closed';
  }
};

const useModal = () => {
  const posTerminal = usePOSTerminal();
  const modal = useSelector((globalState) => getModalFromState({ globalState }));

  if (!posTerminal.id) {
    return <POSTerminalSelectModal allowCancel={false} />;
  }

  if (modal) {
    if (modal === MODAL_POSTerminalSelect) {
      return <POSTerminalSelectModal allowCancel={true} />;
    }
  }

  const journalStatus = getCashJournalStatus(posTerminal);
  if (journalStatus === 'closed') {
    return <POSCashJournalOpenModal />;
  } else if (journalStatus === 'closing') {
    return <POSCashJournalClosingModal />;
  }

  return null;
};

export default POSScreen;
