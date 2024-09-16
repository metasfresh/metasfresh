import React from 'react';
import CurrentOrder from './CurrentOrder';
import './POSScreen.scss';
import Products from './Products';

const POSScreen = () => {
  return (
    <div className="pos-screen">
      <CurrentOrder />
      <Products />
    </div>
  );
};

export default POSScreen;
