import React, { useEffect, useRef } from 'react';
import cx from 'classnames';
import { setSelectedOrderLineAction, useCurrentOrderOrNew } from '../../actions';
import CurrentOrderActions from './CurrentOrderActions';
import PropTypes from 'prop-types';
import { formatQtyToHumanReadableStr } from '../../../../utils/qtys';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { useDispatch } from 'react-redux';
import { usePOSConfiguration } from '../../api/pos_configuration';

const CurrentOrder = () => {
  const dispatch = useDispatch();
  const config = usePOSConfiguration();
  const { /*isCurrentOrderLoading,*/ currentOrder } = useCurrentOrderOrNew();

  const pricePrecision = config?.pricePrecision ?? 2;
  const currencyPrecision = config?.currencyPrecision ?? 2;
  const lines = currentOrder?.lines ?? [];
  const totalAmtStr = formatAmountToHumanReadableStr({
    amount: currentOrder?.totalAmt,
    currency: currentOrder?.currencySymbol,
    precision: currencyPrecision,
  });
  const taxAmtStr = formatAmountToHumanReadableStr({
    amount: currentOrder?.taxAmt,
    currency: currentOrder?.currencySymbol,
    precision: currencyPrecision,
  });

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
            pricePrecision={pricePrecision}
            currencyPrecision={currencyPrecision}
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
        <div className="summary-line totalAmt">Total: {totalAmtStr}</div>
        <div className="summary-line taxAmt">Taxes: {taxAmtStr}</div>
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

const OrderLine = ({
  /*uuid,*/ productName,
  qty,
  uom,
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
  const priceStr =
    formatAmountToHumanReadableStr({ amount: price, currency: currencySymbol, precision: pricePrecision }) + '/' + uom;
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
  pricePrecision: PropTypes.number,
  currencyPrecision: PropTypes.number,
  selected: PropTypes.bool.isRequired,
  onClick: PropTypes.func.isRequired,
};

//
//
//
//
//

export default CurrentOrder;
