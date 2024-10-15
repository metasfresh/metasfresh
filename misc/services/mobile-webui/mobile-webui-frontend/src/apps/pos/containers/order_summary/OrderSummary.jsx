import React, { useEffect, useState } from 'react';
import { usePOSTerminal } from '../../actions/posTerminal';
import { changeOrderStatusToClosed, getReceiptPdf, useCurrentOrder } from '../../actions/orders';
import { useDispatch } from 'react-redux';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { PAYMENT_STATUS_SUCCESSFUL } from '../../constants/paymentStatus';
import { PAYMENT_METHOD_CARD, PAYMENT_METHOD_CASH } from '../../constants/paymentMethods';
import { getPaymentSummaryLine } from '../../utils/payments';
import cx from 'classnames';
import printJS from 'print-js';
import './OrderSummary.scss';
import Spinner from '../../../../components/Spinner';
import { trl } from '../../../../utils/translations';

const _ = (key) => trl(`pos.summary.${key}`);

const OrderSummary = () => {
  const dispatch = useDispatch();
  const { id: posTerminalId, currencyPrecision: precision } = usePOSTerminal();
  const { uuid: order_uuid, currencySymbol: currency, totalAmt, payments } = useCurrentOrder({ posTerminalId });
  const [receiptPdfData, setReceiptPdfData] = useState();

  const {
    totalAmtStr,
    cashTenderedAmount,
    cashTenderedAmountStr,
    cashGiveBackAmount,
    cashGiveBackAmountStr,
    cardPayments,
  } = computePaymentDetails({ totalAmt, currency, precision, payments });

  useEffect(() => {
    getReceiptPdf({ order_uuid }).then(setReceiptPdfData);
  }, [order_uuid]);

  const onPrint = () => {
    if (!receiptPdfData) return;
    printJS({ printable: receiptPdfData, type: 'pdf', base64: true });
  };

  const onClose = () => {
    dispatch(changeOrderStatusToClosed({ posTerminalId, order_uuid }));
  };

  return (
    <div className="modal is-active order-summary-modal">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">{_('title')}</p>
        </header>
        <section className="modal-card-body">
          <div className="detail-center">
            <div className="detail-line">
              <div className="detail-caption">{_('totalAmt')}</div>
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
                  <div className="detail-caption">{_('tenderedAmt')}</div>
                  <div className="detail-value">{cashTenderedAmountStr}</div>
                </div>
                <div className="detail-line">
                  <div className="detail-caption">{_('changeBackAmt')}</div>
                  <div className={cx('detail-value', { 'has-text-danger': cashGiveBackAmount !== 0 })}>
                    {cashGiveBackAmountStr}
                  </div>
                </div>
              </>
            )}
          </div>
          <div className="detail-right">
            {!receiptPdfData && <Spinner />}
            {receiptPdfData && <object className="pdf" data={`data:application/pdf;base64,${receiptPdfData}`} />}
          </div>
        </section>
        <footer className="modal-card-foot">
          <div className="buttons">
            <button className="button is-large" disabled={!receiptPdfData} onClick={onPrint}>
              {_('actions.print')}
            </button>
            <button className="button is-large" onClick={onClose}>
              {_('actions.close')}
            </button>
          </div>
        </footer>
      </div>
    </div>
  );
};

export default OrderSummary;

//
//
//
//
//
//

const computePaymentDetails = ({ totalAmt, currency, precision, payments }) => {
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

  const cashTenderedAmountStr = formatAmountToHumanReadableStr({ amount: cashTenderedAmount, currency, precision });
  const cashGiveBackAmountStr = formatAmountToHumanReadableStr({ amount: cashGiveBackAmount, currency, precision });

  return {
    totalAmtStr,
    cashTenderedAmount,
    cashTenderedAmountStr,
    cashGiveBackAmount,
    cashGiveBackAmountStr,
    cardPayments,
  };
};
