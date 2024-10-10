import React from 'react';
import cx from 'classnames';
import { useDispatch } from 'react-redux';
import './POSPaymentPanel.scss';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { round } from '../../../../utils/numbers';
import PaymentLine from './PaymentLine';
import PaymentMethodButton from './PaymentMethodButton';
import { usePOSTerminal } from '../../actions/posTerminal';
import {
  addPayment,
  changeOrderStatusToComplete,
  changeOrderStatusToDraft,
  checkoutPayment,
  refundPayment,
  removePayment,
  useCurrentOrder,
} from '../../actions/orders';
import PropTypes from 'prop-types';
import CashPaymentDetailsModal from './CashPaymentDetailsModal';

const POSPaymentPanel = ({ disabled }) => {
  const dispatch = useDispatch();
  const posTerminal = usePOSTerminal();
  const currentOrder = useCurrentOrder({ posTerminalId: posTerminal.id });

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
  const isAllowAddPayment = !disabled && openAmt > 0;
  const isAllowValidate = !disabled && openAmt === 0;

  const payments = currentOrder?.payments ?? [];

  let pendingPayment = null;
  let pendingPaymentModal = null;
  for (const payment of payments) {
    console.log('payment', { payment });
    if (payment.status === 'PENDING') {
      if (payment.paymentMethod === 'CASH') {
        pendingPayment = payment;
        pendingPaymentModal = (
          <CashPaymentDetailsModal
            currency={currentOrder.currencySymbol}
            precision={currencyPrecision}
            payAmount={payment.amount}
            tenderedAmount={payment.cashTenderedAmount}
            onOK={({ cashTenderedAmount }) => {
              console.log('got', { cashTenderedAmount });
              dispatch(
                checkoutPayment({
                  posTerminalId: posTerminal.id,
                  order_uuid: currentOrder.uuid,
                  payment_uuid: payment.uuid,
                  cashTenderedAmount,
                })
              );
            }}
          />
        );
        break;
      }
    }
  }

  console.log('***', { pendingPayment, pendingPaymentModal, payments });

  //
  //
  //

  const onAddPaymentClick = ({ paymentMethod }) => {
    if (!isAllowAddPayment) return;
    dispatch(addPayment({ posTerminalId: posTerminal.id, order_uuid, paymentMethod, amount: openAmt }));
  };

  const onPaymentCheckout = ({ uuid }) => {
    if (disabled) return;
    dispatch(checkoutPayment({ posTerminalId: posTerminal.id, order_uuid, payment_uuid: uuid }));
  };

  const onPaymentDelete = ({ uuid }) => {
    if (disabled) return;
    dispatch(removePayment({ posTerminalId: posTerminal.id, order_uuid, payment_uuid: uuid }));
  };

  const onPaymentRefund = ({ uuid }) => {
    if (disabled) return;
    dispatch(refundPayment({ posTerminalId: posTerminal.id, order_uuid, payment_uuid: uuid }));
  };

  const onBackClick = () => {
    if (disabled) return;
    dispatch(changeOrderStatusToDraft({ posTerminalId: posTerminal.id, order_uuid }));
  };

  const onValidateClick = () => {
    if (!isAllowValidate) return;
    dispatch(changeOrderStatusToComplete({ posTerminalId: posTerminal.id, order_uuid }));
  };

  //
  //
  //

  return (
    <div className="pos-content pos-payment-panel">
      {pendingPaymentModal}
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
            disabled={disabled}
            paymentMethod={payment.paymentMethod}
            amount={payment.amount}
            currency={currency}
            currencyPrecision={currencyPrecision}
            status={payment.status}
            statusDetails={payment.statusDetails}
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
POSPaymentPanel.propTypes = {
  disabled: PropTypes.bool,
};

export default POSPaymentPanel;
