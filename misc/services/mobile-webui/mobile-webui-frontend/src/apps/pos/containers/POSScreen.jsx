import React from 'react';
import './POSScreen.scss';
import Header from './Header';
import POSOrderPanel from './order_panel/POSOrderPanel';

const POSScreen = () => {
  return (
    <div className="pos-screen">
      <Header />
      <POSOrderPanel />
    </div>
  );
};

export default POSScreen;
