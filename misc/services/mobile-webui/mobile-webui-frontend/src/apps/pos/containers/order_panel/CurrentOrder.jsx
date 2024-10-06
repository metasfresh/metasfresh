import React from 'react';
import CurrentOrderActions from './CurrentOrderActions';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { useDispatch } from 'react-redux';
import './CurrentOrder.scss';
import { OrderLine } from './OrderLine';
import { usePOSTerminal } from '../../actions/posTerminal';
import { setSelectedOrderLineAction, useCurrentOrderOrNew } from '../../actions/orders';

const CurrentOrder = () => {
  const dispatch = useDispatch();
  const posTerminal = usePOSTerminal();
  const { /*isCurrentOrderLoading,*/ currentOrder } = useCurrentOrderOrNew();

  const pricePrecision = posTerminal?.pricePrecision ?? 2;
  const currencyPrecision = posTerminal?.currencyPrecision ?? 2;
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
            catchWeight={line.catchWeight}
            catchWeightUom={line.catchWeightUomSymbol}
            price={line.price}
            currencySymbol={line.currencySymbol}
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

export default CurrentOrder;
