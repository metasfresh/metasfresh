import React from 'react';
import { useDispatch } from 'react-redux';
import {
  addNewOrderAction,
  changeOrderStatusToVoid,
  changeOrderStatusToWaitingPayment,
  useCurrentOrder,
} from '../../actions/orders';

const CurrentOrderActions = () => {
  const dispatch = useDispatch();
  const currentOrder = useCurrentOrder();

  const isNewOrderAllowed = !currentOrder.isLoading && currentOrder.lines?.length > 0;
  const isVoidAllowed = !currentOrder.isLoading && currentOrder.lines?.length > 0;
  const isPayAllowed = !currentOrder.isLoading && currentOrder.lines?.length > 0;
  const isDeleteCurrentLineAllowed = !!currentOrder.selectedLineUUID;

  const onNewOrderClick = () => {
    dispatch(addNewOrderAction());
  };
  const onVoidCurrentOrderClick = () => {
    if (!isVoidAllowed) return;
    dispatch(changeOrderStatusToVoid({ order_uuid: currentOrder?.uuid }));
  };
  const onDeleteCurrentLine = () => {
    if (!isDeleteCurrentLineAllowed) return;
    currentOrder.removeOrderLine(currentOrder.selectedLineUUID);
  };
  const onPayClick = () => {
    if (!isPayAllowed) return;
    dispatch(changeOrderStatusToWaitingPayment({ order_uuid: currentOrder?.uuid }));
  };

  return (
    <div className="current-order-actions">
      <div className="other-actions-container">
        <button className="button" onClick={onNewOrderClick} disabled={!isNewOrderAllowed}>
          New
        </button>
        <button className="button" onClick={onVoidCurrentOrderClick} disabled={!isVoidAllowed}>
          Void
        </button>
        <button className="button" onClick={onDeleteCurrentLine} disabled={!isDeleteCurrentLineAllowed}>
          Delete line
        </button>
      </div>
      <button className="button is-large pay-action" onClick={onPayClick} disabled={!isPayAllowed}>
        <i className="fas fa-regular fa-money-bill-1"></i>
        <span>Pay</span>
      </button>
    </div>
  );
};

export default CurrentOrderActions;
