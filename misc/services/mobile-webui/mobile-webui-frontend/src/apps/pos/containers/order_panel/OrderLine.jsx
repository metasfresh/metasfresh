import React, { useEffect, useRef } from 'react';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { formatQtyToHumanReadableStr } from '../../../../utils/qtys';
import cx from 'classnames';
import PropTypes from 'prop-types';
import { trl } from '../../../../utils/translations';

const _ = (key) => trl(`pos.currentOrder.line.${key}`);

export const OrderLine = ({
  disabled,
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
  isNew,
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
  const description = `${qtyStr}${catchWeightStr ? ', ' + catchWeightStr : ''} ${_('at')} ${priceStr}`;

  useEffect(() => {
    if (selected && elementRef?.current?.scrollIntoView) {
      elementRef.current.scrollIntoView({ behaviour: 'smooth', block: 'end', inline: 'end' });
    }
  }, [selected]);

  const fireOnClick = () => {
    if (!disabled && onClick) {
      onClick();
    }
  };

  return (
    <div
      className={cx('line', { 'line-selected': selected, 'line-new': isNew })}
      ref={elementRef}
      onClick={fireOnClick}
    >
      <div className="main">
        <div className="productName">{productName}</div>
        <div className="amount">{amountStr}</div>
      </div>
      <div className="description">{description}</div>
    </div>
  );
};

OrderLine.propTypes = {
  disabled: PropTypes.bool,
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
  isNew: PropTypes.bool,
  onClick: PropTypes.func.isRequired,
};

export default OrderLine;
