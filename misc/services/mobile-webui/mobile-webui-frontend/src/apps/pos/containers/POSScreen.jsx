import React from 'react';
import './POSScreen.scss';
import Header from './Header';
import POSOrderPanel from './order_panel/POSOrderPanel';
import POSPaymentPanel from './payment_panel/POSPaymentPanel';
import POSCashJournalOpenPanel from './cash_journal/POSCashJournalOpenPanel';
import POSCashJournalClosingPanel from './cash_journal/POSCashJournalClosingPanel';
import { useOrdersWebsocket } from '../api/orders';
import { useDispatch, useSelector } from 'react-redux';
import { updateOrderFromBackend, useCurrentOrder } from '../actions/orders';
import POSTerminalSelectPanel from './select_terminal/POSTerminalSelectPanel';
import { usePOSTerminal } from '../actions/posTerminal';
import { getPOSApplicationState } from '../actions/common';
import { MODAL_POSTerminalSelect } from '../actions/ui';

const POSScreen = () => {
  const dispatch = useDispatch();
  const posTerminal = usePOSTerminal({ refresh: true });
  const posTerminalId = posTerminal.id;

  useOrdersWebsocket({
    posTerminalId,
    onWebsocketMessage: (message) => {
      dispatch(updateOrderFromBackend({ posTerminalId, order_uuid: message.posOrderId }));
    },
  });

  return (
    <div className="pos-screen">
      <Header />
      <POSContent />
    </div>
  );
};

const POSContent = () => {
  const posTerminal = usePOSTerminal();
  const modal = useSelector((globalState) => getPOSApplicationState(globalState)?.modal);
  const currentOrder = useCurrentOrder({ posTerminalId: posTerminal.id });

  if (!posTerminal.id) {
    return <POSTerminalSelectPanel />;
  }

  if (modal) {
    if (modal === MODAL_POSTerminalSelect) {
      return <POSTerminalSelectPanel />;
    }
  }

  const journalStatus = getCashJournalStatus(posTerminal);
  if (journalStatus === 'closed') {
    return <POSCashJournalOpenPanel />;
  } else if (journalStatus === 'closing') {
    return <POSCashJournalClosingPanel />;
  } else if (journalStatus === 'open') {
    const orderStatus = journalStatus === 'open' ? currentOrder.status ?? 'DR' : '--';
    if (orderStatus === 'DR' || orderStatus === 'VO') {
      return <POSOrderPanel />;
    } else if (orderStatus === 'WP' || orderStatus === 'CO') {
      return <POSPaymentPanel />;
    } else {
      console.warn('No view to be rendered for orderStatus=' + orderStatus);
      return <></>;
    }
  }
};

const getCashJournalStatus = (posTerminal) => {
  if (posTerminal?.cashJournalOpen) {
    return posTerminal?.isCashJournalClosing ? 'closing' : 'open';
  } else {
    return 'closed';
  }
};

export default POSScreen;
