import React from 'react';
import { useDispatch } from 'react-redux';
import {
  addNewOrderAction,
  changeOrderStatusToVoid,
  changeOrderStatusToWaitingPayment,
  useCurrentOrder,
} from '../../actions/orders';
import { usePOSTerminal } from '../../actions/posTerminal';
import PropTypes from 'prop-types';
import { trl } from '../../../../utils/translations';

const _ = (key) => trl(`pos.currentOrder.actions.${key}`);

const CurrentOrderActions = ({ disabled: disabledParam }) => {
  const dispatch = useDispatch();
  const posTerminal = usePOSTerminal();
  const posTerminalId = posTerminal.id;
  const currentOrder = useCurrentOrder({ posTerminalId });

  const disabled = disabledParam || currentOrder.isLoading;
  const hasLines = currentOrder.lines?.length > 0;
  const isNewOrderAllowed = !disabled && hasLines;
  const isVoidAllowed = !disabled && currentOrder.allowVoid && hasLines;
  const isPayAllowed = !disabled && currentOrder.allowWaitPayment;
  const isDeleteCurrentLineAllowed = !disabled && !!currentOrder.selectedLineUUID;

  const onNewOrderClick = () => {
    if (!isNewOrderAllowed) return;
    dispatch(addNewOrderAction({ posTerminalId }));
  };
  const onVoidCurrentOrderClick = () => {
    if (!isVoidAllowed) return;
    dispatch(changeOrderStatusToVoid({ posTerminalId, order_uuid: currentOrder?.uuid }));
  };
  const onDeleteCurrentLine = () => {
    if (!isDeleteCurrentLineAllowed) return;
    currentOrder.removeOrderLine(currentOrder.selectedLineUUID);
  };
  const onPayClick = () => {
    if (!isPayAllowed) return;
    dispatch(changeOrderStatusToWaitingPayment({ posTerminalId, order_uuid: currentOrder?.uuid }));
  };

  return (
    <div className="current-order-actions">
      <div className="other-actions-container">
        <button className="button" onClick={onNewOrderClick} disabled={!isNewOrderAllowed}>
          {_('new')}
        </button>
        <button className="button" onClick={onVoidCurrentOrderClick} disabled={!isVoidAllowed}>
          {_('void')}
        </button>
        <button className="button" onClick={onDeleteCurrentLine} disabled={!isDeleteCurrentLineAllowed}>
          {_('deleteLine')}
        </button>
      </div>
      <button className="button is-large pay-action" onClick={onPayClick} disabled={!isPayAllowed}>
        <i className="fas fa-regular fa-money-bill-1"></i>
        <span>{_('pay')}</span>
      </button>
    </div>
  );
};

CurrentOrderActions.propTypes = {
  disabled: PropTypes.bool,
};

export default CurrentOrderActions;
