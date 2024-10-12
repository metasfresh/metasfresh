import React, { useEffect, useState } from 'react';
import '../payment_panel/PaymentDetailsModal.scss';
import { usePOSTerminal } from '../../actions/posTerminal';
import { changeOrderStatusToClosed, useCurrentOrder } from '../../actions/orders';
import { useDispatch } from 'react-redux';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { PAYMENT_STATUS_SUCCESSFUL } from '../../constants/paymentStatus';
import { PAYMENT_METHOD_CARD, PAYMENT_METHOD_CASH } from '../../constants/paymentMethods';
import { getPaymentSummaryLine } from '../../utils/payments';
import cx from 'classnames';
import { getReceiptPdf } from '../../api/orders';

const OrderSummary = () => {
  const dispatch = useDispatch();
  const { id: posTerminalId, currencyPrecision: precision } = usePOSTerminal();
  const { uuid: order_uuid, currencySymbol: currency, totalAmt, payments } = useCurrentOrder({ posTerminalId });
  const [receiptPdfData, setReceiptPdfData] = useState();

  useEffect(() => {
    getReceiptPdf({ order_uuid }).then((data) => {
      setReceiptPdfData(data);
      console.log('loaded pdf', { data });
    });
  }, [order_uuid]);
  console.log('OrderSummary', { receiptPdfData });

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

  const onPrint = () => {
    //getReceiptPdf({ order_uuid });

    // window.print();

    // let printWindow = window.open(receiptPdfData);
    // printWindow.print();
    // window.alert('printed');

    console.log('', { receiptPdfData });

    // // const blob = b64toBlob(receiptPdfData, 'application/pdf');
    // console.log('', { blob });
    //
    // const blobUrl = URL.createObjectURL(blob);
    // console.log('', { blobUrl });
  };

  // const b64toBlob = (b64Data, contentType = '', sliceSize = 512) => {
  //   const byteCharacters = atob(b64Data);
  //   const byteArrays = [];
  //
  //   for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
  //     const slice = byteCharacters.slice(offset, offset + sliceSize);
  //
  //     const byteNumbers = new Array(slice.length);
  //     for (let i = 0; i < slice.length; i++) {
  //       byteNumbers[i] = slice.charCodeAt(i);
  //     }
  //
  //     const byteArray = new Uint8Array(byteNumbers);
  //     byteArrays.push(byteArray);
  //   }
  //
  //   const blob = new Blob(byteArrays, { type: contentType });
  //   return blob;
  // };

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
          <object data={receiptPdfData} width="400px" height="400px" />
        </section>
        <footer className="modal-card-foot">
          <div className="buttons">
            <button className="button is-large" onClick={onPrint}>
              Print
            </button>
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
