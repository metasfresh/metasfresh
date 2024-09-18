import React from 'react';
import CurrentOrder from './CurrentOrder';
import Products from './Products';
import './POSOrderPanel.scss';

const POSOrderPanel = () => {
  return (
    <div className="pos-content pos-order-panel">
      <CurrentOrder />
      <Products />
    </div>
  );
};

export default POSOrderPanel;
