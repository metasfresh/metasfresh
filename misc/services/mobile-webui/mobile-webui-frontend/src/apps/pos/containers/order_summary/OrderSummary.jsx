import React from 'react';
import '../payment_panel/PaymentDetailsModal.scss';
import { usePOSTerminal } from '../../actions/posTerminal';
import { changeOrderStatusToClosed, useCurrentOrder } from '../../actions/orders';
import { useDispatch } from 'react-redux';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { PAYMENT_STATUS_SUCCESSFUL } from '../../constants/paymentStatus';
import { PAYMENT_METHOD_CARD, PAYMENT_METHOD_CASH } from '../../constants/paymentMethods';
import { getPaymentSummaryLine } from '../../utils/payments';
import cx from 'classnames';

const OrderSummary = () => {
  const dispatch = useDispatch();
  const { id: posTerminalId, currencyPrecision: precision } = usePOSTerminal();
  const { uuid: order_uuid, currencySymbol: currency, totalAmt, payments } = useCurrentOrder({ posTerminalId });

  const totalAmtStr = formatAmountToHumanReadableStr({ amount: totalAmt, currency, precision });

  const cardPayments = [];
  let cashTenderedAmount = 0;
  let cashGiveBackAmount = 0;
  for (const payment of payments) {
    if (payment.status !== PAYMENT_STATUS_SUCCESSFUL) {
      continue;
    }

    if (payment.paymentMethod === PAYMENT_METHOD_CARD) {
      cardPayments.push({
        uuid: payment.uuid,
        summary: getPaymentSummaryLine({ paymentMethod: payment.paymentMethod, statusDetails: payment.statusDetails }),
        amountStr: formatAmountToHumanReadableStr({ amount: payment.amount, currency, precision }),
      });
    } else if (payment.paymentMethod === PAYMENT_METHOD_CASH) {
      cashTenderedAmount += payment.cashTenderedAmount;
      cashGiveBackAmount += payment.cashGiveBackAmount;
    }
  }

  const onClose = () => {
    dispatch(changeOrderStatusToClosed({ posTerminalId, order_uuid }));
  };

  return (
    <div className="modal is-active payment-details-modal">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">Summary</p>
        </header>
        <section className="modal-card-body">
          <div className="detail-line">
            <div className="detail-caption">Total</div>
            <div className="detail-value">{totalAmtStr}</div>
          </div>
          {cardPayments.map((payment) => (
            <div key={payment.uuid} className="detail-line">
              <div className="detail-caption">{payment.summary}</div>
              <div className="detail-value">{payment.amountStr}</div>
            </div>
          ))}
          {(cashTenderedAmount !== 0 || cashGiveBackAmount !== 0) && (
            <>
              <div className="detail-line">
                <div className="detail-caption">Cash Tendered Amount</div>
                <div className="detail-value">
                  {formatAmountToHumanReadableStr({ amount: cashTenderedAmount, currency, precision })}
                </div>
              </div>
              <div className="detail-line">
                <div className="detail-caption">Change</div>
                <div className={cx('detail-value', { 'has-text-danger': cashGiveBackAmount !== 0 })}>
                  {formatAmountToHumanReadableStr({ amount: cashGiveBackAmount, currency, precision })}
                </div>
              </div>
            </>
          )}
        </section>
        <footer className="modal-card-foot">
          <div className="buttons">
            <button className="button is-large" onClick={onClose}>
              Close
            </button>
          </div>
        </footer>
      </div>
    </div>
  );
};

export default OrderSummary;
