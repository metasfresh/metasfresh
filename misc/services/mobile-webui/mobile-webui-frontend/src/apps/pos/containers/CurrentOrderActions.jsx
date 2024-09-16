import React from 'react';
import { addNewOrderAction, removeOrderAction, useCurrentOrder } from '../actions';
import { useDispatch } from 'react-redux';

const CurrentOrderActions = () => {
  const dispatch = useDispatch();
  const currentOrder = useCurrentOrder();

  const onNewOrderClick = () => {
    dispatch(addNewOrderAction());
  };
  const onCancelCurrentOrderClick = () => {
    const order_uuid = currentOrder?.uuid;
    if (!order_uuid) {
      return;
    }
    dispatch(removeOrderAction({ order_uuid }));
  };

  const onPayClick = () => {
    console.log('PAY!');
  };

  return (
    <div className="current-order-actions">
      <button onClick={onNewOrderClick} disabled={!currentOrder}>
        New
      </button>
      <button onClick={onCancelCurrentOrderClick} disabled={!currentOrder}>
        Cancel
      </button>
      <button onClick={onPayClick} disabled={!currentOrder}>
        Pay
      </button>
    </div>
  );
};

export default CurrentOrderActions;
