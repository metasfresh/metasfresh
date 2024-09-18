import React from 'react';
import './POSScreen.scss';
import { useCurrentOrder } from '../actions';
import Header from './Header';
import POSOrderPanel from './order_panel/POSOrderPanel';
import POSPaymentPanel from './payment_panel/POSPaymentPanel';

const POSScreen = () => {
  const { currentOrder } = useCurrentOrder();

  const status = currentOrder?.status ?? 'DR';

  return (
    <div className="pos-screen">
      <Header />
      {status === 'DR' && <POSOrderPanel />}
      {status === 'WP' && <POSPaymentPanel />}
    </div>
  );
};

export default POSScreen;
