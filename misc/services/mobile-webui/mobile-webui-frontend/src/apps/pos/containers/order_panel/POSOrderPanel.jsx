import React from 'react';
import CurrentOrder from './CurrentOrder';
import Products from './Products';
import './POSOrderPanel.scss';
import PropTypes from 'prop-types';

const POSOrderPanel = ({ disabled }) => {
  return (
    <div className="pos-content pos-order-panel">
      <CurrentOrder disabled={disabled} />
      <Products disabled={disabled} />
    </div>
  );
};

POSOrderPanel.propTypes = {
  disabled: PropTypes.bool,
};

export default POSOrderPanel;
