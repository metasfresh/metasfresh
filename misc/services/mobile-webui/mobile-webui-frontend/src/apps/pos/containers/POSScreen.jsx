import React from 'react';
import './POSScreen.scss';
import Header from './Header';
import POSOrderPanel from './order_panel/POSOrderPanel';
import POSPaymentPanel from './payment_panel/POSPaymentPanel';
import POSCashJournalOpenPanel from './cash_journal/POSCashJournalOpenPanel';
import POSCashJournalClosingPanel from './cash_journal/POSCashJournalClosingPanel';
import { useOrdersWebsocket } from '../api/orders';
import { useDispatch } from 'react-redux';
import { usePOSTerminal } from '../actions/posTerminal';
import { updateOrderFromBackend, useCurrentOrder } from '../actions/orders';

const POSScreen = () => {
  const dispatch = useDispatch();
  const posTerminal = usePOSTerminal({ refresh: true });
  const currentOrder = useCurrentOrder();

  const journalStatus = getCashJouralStatus(posTerminal);
  const orderStatus = journalStatus === 'open' ? currentOrder.status ?? 'DR' : '--';

  useOrdersWebsocket({
    terminalId: posTerminal.id,
    onWebsocketMessage: (message) => {
      dispatch(updateOrderFromBackend({ order_uuid: message.posOrderId }));
    },
  });

  return (
    <div className="pos-screen">
      <Header />
      {journalStatus === 'closed' && <POSCashJournalOpenPanel />}
      {journalStatus === 'closing' && <POSCashJournalClosingPanel />}
      {orderStatus === 'DR' && <POSOrderPanel />}
      {orderStatus === 'WP' && <POSPaymentPanel />}
    </div>
  );
};

const getCashJouralStatus = (posTerminal) => {
  if (posTerminal?.cashJournalOpen) {
    return posTerminal?.isCashJournalClosing ? 'closing' : 'open';
  } else {
    return 'closed';
  }
};

export default POSScreen;
