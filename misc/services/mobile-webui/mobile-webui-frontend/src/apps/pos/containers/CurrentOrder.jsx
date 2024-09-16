import React from 'react';
import { useCurrentOrderOrNew } from '../actions';
import CurrentOrderActions from './CurrentOrderActions';
import PropTypes from 'prop-types';

const CurrentOrder = () => {
  const { isCurrentOrderLoading, currentOrder } = useCurrentOrderOrNew();
  console.log('CurrentOrder', { isCurrentOrderLoading, currentOrder });

  const lines = currentOrder?.lines ?? [];

  return (
    <div className="current-order">
      <table className="current-order-table">
        <thead>
          <tr>
            <th>Product</th>
            <th>Qty</th>
            <th>Amt</th>
          </tr>
        </thead>
        <tbody>
          {lines.map((line) => (
            <OrderLine key={line.uuid} uuid={line.uuid} productName={line.productName} />
          ))}
        </tbody>
      </table>
      <CurrentOrderActions />
    </div>
  );
};

const OrderLine = ({ /*uuid,*/ productName }) => {
  return (
    <tr>
      <td>{productName}</td>
      <td>?</td>
      <td>?</td>
    </tr>
  );
};

OrderLine.propTypes = {
  productName: PropTypes.string.isRequired,
};

export default CurrentOrder;
