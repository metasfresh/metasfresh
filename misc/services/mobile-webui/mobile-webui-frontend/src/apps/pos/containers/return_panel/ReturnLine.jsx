import React from 'react';
import PropTypes from 'prop-types';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { formatQtyToHumanReadableStr } from '../../../../utils/qtys';
import { trl } from '../../../../utils/translations';

const _ = (key) => trl(`pos.return.line.${key}`);

/** One returned-product line of the return cart — qty × the till's own current price for that product. */
export const ReturnLine = ({
  productName,
  qty,
  uom,
  price,
  currencySymbol,
  pricePrecision,
  currencyPrecision,
  onClick,
}) => {
  const amount = qty * price;
  const qtyStr = formatQtyToHumanReadableStr({ qty, uom });
  const priceStr =
    formatAmountToHumanReadableStr({ amount: price, currency: currencySymbol, precision: pricePrecision }) + '/' + uom;
  const amountStr = formatAmountToHumanReadableStr({ amount, currency: currencySymbol, precision: currencyPrecision });

  return (
    <div className="line" data-testid="pos-return-line" onClick={onClick}>
      <div className="main">
        <div className="productName" data-testid="pos-return-line-product-name">
          {productName}
        </div>
        <div className="amount" data-testid="pos-return-line-amount">
          {amountStr}
        </div>
      </div>
      <div className="description" data-testid="pos-return-line-description">
        {qtyStr} {_('at')} {priceStr}
      </div>
    </div>
  );
};

ReturnLine.propTypes = {
  productName: PropTypes.string.isRequired,
  qty: PropTypes.number.isRequired,
  uom: PropTypes.string.isRequired,
  price: PropTypes.number.isRequired,
  currencySymbol: PropTypes.string,
  pricePrecision: PropTypes.number,
  currencyPrecision: PropTypes.number,
  onClick: PropTypes.func.isRequired,
};

export default ReturnLine;
