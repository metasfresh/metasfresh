import React from 'react';
import PropTypes from 'prop-types';
import { usePOSTerminal } from '../actions/posTerminal';
import { useCurrentOrder } from '../actions/orders';
import {
  ORDER_STATUS_COMPLETED,
  ORDER_STATUS_DRAFTED,
  ORDER_STATUS_VOIDED,
  ORDER_STATUS_WAITING_PAYMENT,
} from '../constants/orderStatus';
import POSOrderPanel from './order_panel/POSOrderPanel';
import POSPaymentPanel from './payment_panel/POSPaymentPanel';
import OrderSummary from './order_summary/OrderSummary';

export const POSContent = ({ disabled }) => {
  const posTerminal = usePOSTerminal();
  const currentOrder = useCurrentOrder({ posTerminalId: posTerminal.id });

  switch (currentOrder?.status ?? '--') {
    case ORDER_STATUS_DRAFTED:
    case ORDER_STATUS_VOIDED:
      return <POSOrderPanel disabled={disabled} />;
    case ORDER_STATUS_WAITING_PAYMENT:
      return <POSPaymentPanel disabled={disabled} />;
    case ORDER_STATUS_COMPLETED:
      return <OrderSummary disabled={disabled} />;
    default:
      return <></>;
  }
};
POSContent.propTypes = {
  disabled: PropTypes.bool,
};
