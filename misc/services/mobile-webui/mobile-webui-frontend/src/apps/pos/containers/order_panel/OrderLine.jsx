import React, { useEffect, useRef } from 'react';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { formatQtyToHumanReadableStr } from '../../../../utils/qtys';
import cx from 'classnames';
import PropTypes from 'prop-types';

export const OrderLine = ({
  /*uuid,*/ productName,
  qty,
  uom,
  catchWeight,
  catchWeightUom,
  currencySymbol,
  price,
  amount,
  pricePrecision,
  currencyPrecision,
  selected,
  onClick,
}) => {
  const elementRef = useRef();
  const amountStr =
    amount != null
      ? formatAmountToHumanReadableStr({ amount: amount, currency: currencySymbol, precision: currencyPrecision })
      : '';
  const qtyStr = formatQtyToHumanReadableStr({ qty, uom });
  const catchWeightStr =
    catchWeightUom != null ? formatQtyToHumanReadableStr({ qty: catchWeight, uom: catchWeightUom }) : null;
  const priceUom = catchWeightUom ? catchWeightUom : uom;
  const priceStr =
    formatAmountToHumanReadableStr({ amount: price, currency: currencySymbol, precision: pricePrecision }) +
    '/' +
    priceUom;
  const description = `${qtyStr}${catchWeightStr ? ', ' + catchWeightStr : ''} at ${priceStr}`;

  useEffect(() => {
    if (selected && elementRef?.current?.scrollIntoView) {
      elementRef.current.scrollIntoView({ behaviour: 'smooth', block: 'end', inline: 'end' });
    }
  }, [selected]);

  return (
    <div className={cx('line', { 'line-selected': selected })} ref={elementRef} onClick={onClick}>
      <div className="main">
        <div className="productName">{productName}</div>
        <div className="amount">{amountStr}</div>
      </div>
      <div className="description">{description}</div>
    </div>
  );
};

OrderLine.propTypes = {
  productName: PropTypes.string.isRequired,
  qty: PropTypes.number.isRequired,
  uom: PropTypes.string.isRequired,
  catchWeight: PropTypes.number,
  catchWeightUom: PropTypes.string,
  currencySymbol: PropTypes.string,
  price: PropTypes.number,
  amount: PropTypes.number,
  pricePrecision: PropTypes.number,
  currencyPrecision: PropTypes.number,
  selected: PropTypes.bool.isRequired,
  onClick: PropTypes.func.isRequired,
};

export default OrderLine;
