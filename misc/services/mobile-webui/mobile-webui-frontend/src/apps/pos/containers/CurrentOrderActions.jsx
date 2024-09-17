import React from 'react';
import { addNewOrderAction, useCurrentOrder, voidOrder } from '../actions';
import { useDispatch } from 'react-redux';

const CurrentOrderActions = () => {
  const dispatch = useDispatch();
  const { isCurrentOrderLoading, currentOrder } = useCurrentOrder();

  const isNewOrderAllowed = !isCurrentOrderLoading && (!currentOrder || currentOrder?.lines?.length > 0);
  const isVoidAllowed = !isCurrentOrderLoading && currentOrder && currentOrder.lines?.length > 0;
  const isPayAllowed = !isCurrentOrderLoading && currentOrder && currentOrder.lines?.length > 0;

  const onNewOrderClick = () => {
    dispatch(addNewOrderAction());
  };
  const onVoidCurrentOrderClick = () => {
    if (!isVoidAllowed) return;
    dispatch(voidOrder({ order_uuid: currentOrder?.uuid }));
  };

  const onPayClick = () => {
    console.log('PAY!');
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
