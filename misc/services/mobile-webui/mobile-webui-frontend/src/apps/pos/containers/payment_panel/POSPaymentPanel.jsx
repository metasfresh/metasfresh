import React from 'react';
import cx from 'classnames';
import {
  addPayment,
  changeOrderStatusToComplete,
  changeOrderStatusToDraft,
  checkoutPayment,
  refundPayment,
  removePayment,
  useCurrentOrder,
  usePOSTerminal,
} from '../../actions';
import { useDispatch } from 'react-redux';
import './POSPaymentPanel.scss';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { round } from '../../../../utils/numbers';
import PaymentLine from './PaymentLine';
import PaymentMethodButton from './PaymentMethodButton';

const POSPaymentPanel = () => {
  const dispatch = useDispatch();
  const posTerminal = usePOSTerminal();
  const currentOrder = useCurrentOrder();

  const order_uuid = currentOrder.uuid;

  const currencyPrecision = posTerminal?.currencyPrecision ?? 2;
  const currency = currentOrder?.currencySymbol;

  const totalAmt = currentOrder?.totalAmt ?? 0;
  const totalAmtStr = formatAmountToHumanReadableStr({
    amount: totalAmt,
    currency: currency,
    precision: currencyPrecision,
  });
  const paidAmt = currentOrder?.paidAmt ?? 0;
  const openAmt = round(totalAmt - paidAmt, currencyPrecision);
  const openAmtStr = formatAmountToHumanReadableStr({
    amount: openAmt,
    currency: currency,
    precision: currencyPrecision,
  });

  const payments = currentOrder?.payments ?? [];

  const isAllowAddPayment = openAmt > 0;
  const isAllowValidate = openAmt === 0;

  const onAddPaymentClick = ({ paymentMethod }) => {
    if (!isAllowAddPayment) return;

    dispatch(addPayment({ order_uuid, paymentMethod, amount: openAmt }));
  };

  const onPaymentCheckout = ({ uuid }) => {
    dispatch(checkoutPayment({ order_uuid, payment_uuid: uuid }));
  };

  const onPaymentDelete = ({ uuid }) => {
    dispatch(removePayment({ order_uuid, payment_uuid: uuid }));
  };

  const onPaymentRefund = ({ uuid }) => {
    dispatch(refundPayment({ order_uuid, payment_uuid: uuid }));
  };

  const onBackClick = () => {
    dispatch(changeOrderStatusToDraft({ order_uuid }));
  };

  const onValidateClick = () => {
    if (!isAllowValidate) return;
    dispatch(changeOrderStatusToComplete({ order_uuid }));
  };

  return (
    <div className="pos-content pos-payment-panel">
      <div className="payment-summary">
        <div className="payment-summary-line totalAmt">
          <div className="payment-summary-label">Total</div>
          <div className="payment-summary-value">{totalAmtStr}</div>
          <div className="payment-summary-actions" />
        </div>
        <div className="payment-summary-line openAmt">
          <div className="payment-summary-label">Remaining</div>
          <div className="payment-summary-value">{openAmtStr}</div>
          <div className="payment-summary-actions" />
        </div>
      </div>
      <div className="payment-lines-container">
        {payments.map((payment) => (
          <PaymentLine
            key={payment.uuid}
            uuid={payment.uuid}
            paymentMethod={payment.paymentMethod}
            amount={payment.amount}
            currency={currency}
            currencyPrecision={currencyPrecision}
            status={payment.status}
            allowCheckout={payment.allowCheckout}
            onCheckout={onPaymentCheckout}
            allowDelete={payment.allowDelete}
            onDelete={onPaymentDelete}
            allowRefund={payment.allowRefund}
            onRefund={onPaymentRefund}
          />
        ))}
      </div>
      <div className="payment-methods-container">
        {posTerminal.availablePaymentMethods?.map((paymentMethod) => (
          <PaymentMethodButton
            key={paymentMethod}
            paymentMethod={paymentMethod}
            disabled={!isAllowAddPayment}
            onClick={onAddPaymentClick}
          />
        ))}
      </div>
      <div className="payment-bottom">
        <button className="button is-large back" onClick={onBackClick}>
          <span className="icon is-medium">
            <i className="fa-solid fa-chevron-left" />
          </span>
          <span>Back</span>
        </button>
        <button
          className={cx('button is-large validate', { 'is-disabled': !isAllowValidate })}
          disabled={!isAllowValidate}
          onClick={onValidateClick}
        >
          <span className="icon is-medium">
            <i className="fa-solid fa-chevron-right" />
          </span>
          <span>Validate</span>
        </button>
      </div>
    </div>
  );
};

export default POSPaymentPanel;
