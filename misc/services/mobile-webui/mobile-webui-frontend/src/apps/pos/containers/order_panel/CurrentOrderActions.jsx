import React from 'react';
import {
  addNewOrderAction,
  changeOrderStatusToVoid,
  changeOrderStatusToWaitingPayment,
  useCurrentOrder,
} from '../../actions';
import { useDispatch } from 'react-redux';

const CurrentOrderActions = () => {
  const dispatch = useDispatch();
  const currentOrder = useCurrentOrder();

  const isNewOrderAllowed = !currentOrder.isLoading && currentOrder.lines?.length > 0;
  const isVoidAllowed = !currentOrder.isLoading && currentOrder.lines?.length > 0;
  const isPayAllowed = !currentOrder.isLoading && currentOrder.lines?.length > 0;

  const onNewOrderClick = () => {
    dispatch(addNewOrderAction());
  };
  const onVoidCurrentOrderClick = () => {
    if (!isVoidAllowed) return;
    dispatch(changeOrderStatusToVoid({ order_uuid: currentOrder?.uuid }));
  };

  const onPayClick = () => {
    if (!isPayAllowed) return;
    dispatch(changeOrderStatusToWaitingPayment({ order_uuid: currentOrder?.uuid }));
  };

  return (
    <div className="current-order-actions">
      <div className="other-actions-container">
        <button onClick={onNewOrderClick} disabled={!isNewOrderAllowed}>
          New
        </button>
        <button onClick={onVoidCurrentOrderClick} disabled={!isVoidAllowed}>
          Void
        </button>
      </div>
      <button className="pay-action" onClick={onPayClick} disabled={!isPayAllowed}>
        &gt; Pay
      </button>
    </div>
  );
};

export default CurrentOrderActions;
