import React from 'react';
import { changeOrderStatusToDraft, useCurrentOrder } from '../../actions';
import { useDispatch } from 'react-redux';

const POSPaymentPanel = () => {
  const dispatch = useDispatch();
  const { currentOrder } = useCurrentOrder();

  const onBackClick = () => {
    dispatch(changeOrderStatusToDraft({ order_uuid: currentOrder?.uuid }));
  };

  return (
    <div className="pos-payment-panel">
      Payment panel
      <button onClick={onBackClick}>Back</button>
    </div>
  );
};

export default POSPaymentPanel;
