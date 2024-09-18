import React from 'react';
import CurrentOrder from './CurrentOrder';
import Products from './Products';
const POSOrderPanel = () => {
  return (
    <div className="pos-order-panel">
      <CurrentOrder />
      <Products />
    </div>
  );
};

export default POSOrderPanel;
