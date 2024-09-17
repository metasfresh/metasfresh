import React from 'react';
import { useCurrentOrderOrNew } from '../actions';
import CurrentOrderActions from './CurrentOrderActions';
import PropTypes from 'prop-types';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';
import { formatAmountToHumanReadableStr } from '../../../utils/money';

const CurrentOrder = () => {
  const { /*isCurrentOrderLoading,*/ currentOrder } = useCurrentOrderOrNew();

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
            <OrderLine
              key={line.uuid}
              uuid={line.uuid}
              productName={line.productName}
              qty={line.qty}
              uom={line.uomSymbol}
              currencySymbol={line.currencySymbol}
              price={line.price}
              amount={line.amount}
            />
          ))}
        </tbody>
      </table>
      <div className="current-order-summary">
        <div className="totalAmt">
          Total:&nbsp;
          {formatAmountToHumanReadableStr({ amount: currentOrder?.totalAmt, currency: currentOrder?.currencySymbol })}
        </div>
      </div>
      <CurrentOrderActions />
    </div>
  );
};

const OrderLine = ({ /*uuid,*/ productName, qty, uom, currencySymbol /*, price*/, amount }) => {
  return (
    <tr>
      <td>{productName}</td>
      <td>{formatQtyToHumanReadableStr({ qty, uom })}</td>
      <td>{amount != null ? formatAmountToHumanReadableStr({ amount: amount, currency: currencySymbol }) : ''}</td>
    </tr>
  );
};

OrderLine.propTypes = {
  productName: PropTypes.string.isRequired,
  qty: PropTypes.number.isRequired,
  uom: PropTypes.string.isRequired,
  currencySymbol: PropTypes.string,
  amount: PropTypes.number,
};

export default CurrentOrder;
