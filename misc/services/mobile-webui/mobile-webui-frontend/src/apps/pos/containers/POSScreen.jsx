import React from 'react';
import PropTypes from 'prop-types';
import './POSScreen.scss';
import Header from './Header';
import POSOrderPanel from './order_panel/POSOrderPanel';
import POSPaymentPanel from './payment_panel/POSPaymentPanel';
import POSCashJournalOpenModal from './cash_journal/POSCashJournalOpenModal';
import POSCashJournalClosingModal from './cash_journal/POSCashJournalClosingModal';
import { useOrdersWebsocket } from '../api/orders';
import { useDispatch, useSelector } from 'react-redux';
import { updateOrderFromBackend, useCurrentOrder } from '../actions/orders';
import POSTerminalSelectModal from './select_terminal/POSTerminalSelectModal';
import { usePOSTerminal } from '../actions/posTerminal';
import { MODAL_POSTerminalSelect } from '../actions/ui';
import { getModalFromState } from '../reducers/uiUtils';

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

  const modal = useModal();

  return (
    <div className="pos-screen">
      <Header />
      {modal}
      <POSContent disabled={!!modal} />
    </div>
  );
};

const POSContent = ({ disabled }) => {
  const posTerminal = usePOSTerminal();
  const currentOrder = useCurrentOrder({ posTerminalId: posTerminal.id });

  const orderStatus = currentOrder?.status ?? '--';
  if (orderStatus === 'DR' || orderStatus === 'VO') {
    return <POSOrderPanel disabled={disabled} />;
  } else if (orderStatus === 'WP' || orderStatus === 'CO') {
    return <POSPaymentPanel disabled={disabled} />;
  } else {
    //console.warn('No view to be rendered for orderStatus=' + orderStatus);
    return <></>;
  }
};
POSContent.propTypes = {
  disabled: PropTypes.bool,
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
    return <POSTerminalSelectModal />;
  }

  if (modal) {
    if (modal === MODAL_POSTerminalSelect) {
      return <POSTerminalSelectModal />;
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
