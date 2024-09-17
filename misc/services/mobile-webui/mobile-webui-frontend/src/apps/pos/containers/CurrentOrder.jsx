import React, { useEffect, useRef } from 'react';
import cx from 'classnames';
import { setSelectedOrderLineAction, useCurrentOrderOrNew } from '../actions';
import CurrentOrderActions from './CurrentOrderActions';
import PropTypes from 'prop-types';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';
import { formatAmountToHumanReadableStr } from '../../../utils/money';
import { useDispatch } from 'react-redux';

const CurrentOrder = () => {
  const dispatch = useDispatch();
  const { /*isCurrentOrderLoading,*/ currentOrder } = useCurrentOrderOrNew();

  const lines = currentOrder?.lines ?? [];

  return (
    <div className="current-order">
      <div className="lines-container">
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
            selected={line.uuid === currentOrder.selectedLineUUID}
            onClick={() => {
              dispatch(
                setSelectedOrderLineAction({
                  order_uuid: currentOrder.uuid,
                  selectedLineUUID: line.uuid,
                })
              );
            }}
          />
        ))}
      </div>
      <div className="summary">
        <div className="totalAmt">
          Total:&nbsp;
          {formatAmountToHumanReadableStr({ amount: currentOrder?.totalAmt, currency: currentOrder?.currencySymbol })}
        </div>
      </div>
      <CurrentOrderActions />
    </div>
  );
};

//
//
//
//
//

const OrderLine = ({ /*uuid,*/ productName, qty, uom, currencySymbol, price, amount, selected, onClick }) => {
  const elementRef = useRef();
  const amountStr = amount != null ? formatAmountToHumanReadableStr({ amount: amount, currency: currencySymbol }) : '';
  const qtyStr = formatQtyToHumanReadableStr({ qty, uom });
  const priceStr = formatAmountToHumanReadableStr({ amount: price, currency: currencySymbol }) + '/' + uom;
  const description = `${qtyStr} at ${priceStr}`;

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
  currencySymbol: PropTypes.string,
  price: PropTypes.number,
  amount: PropTypes.number,
  selected: PropTypes.bool.isRequired,
  onClick: PropTypes.func.isRequired,
};

//
//
//
//
//

export default CurrentOrder;
