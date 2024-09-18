import React from 'react';
import cx from 'classnames';
import { addPayment, changeOrderStatusToDraft, removePayment, useCurrentOrder } from '../../actions';
import { useDispatch } from 'react-redux';
import './POSPaymentPanel.scss';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { usePOSConfiguration } from '../../api/pos_configuration';
import PropTypes from 'prop-types';
import { getPaymentMethodCaption, getPaymentMethodIcon, PAYMENT_METHODS } from '../../utils/paymentMethods';

const POSPaymentPanel = () => {
  const dispatch = useDispatch();
  const config = usePOSConfiguration();
  const { currentOrder } = useCurrentOrder();

  const currencyPrecision = config?.currencyPrecision ?? 2;
  const currency = currentOrder?.currencySymbol;

  const totalAmt = currentOrder?.totalAmt ?? 0;
  const totalAmtStr = formatAmountToHumanReadableStr({
    amount: totalAmt,
    currency: currency,
    precision: currencyPrecision,
  });
  const paidAmt = currentOrder?.paidAmt ?? 0;
  const openAmt = totalAmt - paidAmt;
  const openAmtStr = formatAmountToHumanReadableStr({
    amount: openAmt,
    currency: currency,
    precision: currencyPrecision,
  });

  const payments = currentOrder?.payments ?? [];

  const isAllowAddPayment = openAmt > 0;

  const onAddPaymentClick = ({ paymentMethod }) => {
    if (!isAllowAddPayment) return;

    dispatch(
      addPayment({
        order_uuid: currentOrder.uuid,
        paymentMethod,
        amount: openAmt,
      })
    );
  };

  const onPaymentDelete = ({ uuid }) => {
    dispatch(
      removePayment({
        order_uuid: currentOrder.uuid,
        payment_uuid: uuid,
      })
    );
  };

  const onBackClick = () => {
    dispatch(changeOrderStatusToDraft({ order_uuid: currentOrder?.uuid }));
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
            onDelete={onPaymentDelete}
          />
        ))}
      </div>
      <div className="payment-methods-container">
        {PAYMENT_METHODS.map((paymentMethod) => (
          <PaymentMethodButton
            key={paymentMethod}
            paymentMethod={paymentMethod}
            disabled={!isAllowAddPayment}
            onClick={onAddPaymentClick}
          />
        ))}
      </div>
      <div className="payment-bottom">
        <button onClick={onBackClick}>Back</button>
      </div>
    </div>
  );
};

//
//
//

const PaymentLine = ({ uuid, paymentMethod, amount, currency, currencyPrecision, onDelete }) => {
  const icon = getPaymentMethodIcon({ paymentMethod });
  const iconClassName = `fa-regular ${icon}`;
  const caption = getPaymentMethodCaption({ paymentMethod });
  const amountStr = formatAmountToHumanReadableStr({
    amount: amount,
    currency: currency,
    precision: currencyPrecision,
  });

  return (
    <div className="payment-line">
      <div className="payment-line-label">
        <i className={iconClassName} /> {caption}
      </div>
      <div className="payment-line-value">{amountStr}</div>
      <div className="payment-line-actions">
        <div className="payment-line-action-item" onClick={() => onDelete({ uuid })}>
          <i className="fa-regular fa-trash-can" />
        </div>
      </div>
    </div>
  );
};
PaymentLine.propTypes = {
  uuid: PropTypes.string.isRequired,
  paymentMethod: PropTypes.string.isRequired,
  amount: PropTypes.number.isRequired,
  currency: PropTypes.string.isRequired,
  currencyPrecision: PropTypes.number.isRequired,
  onDelete: PropTypes.func.isRequired,
};

//
//
//

const PaymentMethodButton = ({ paymentMethod, disabled, onClick }) => {
  const icon = getPaymentMethodIcon({ paymentMethod });
  const caption = getPaymentMethodCaption({ paymentMethod });

  return (
    <div
      className={cx('payment-method', { 'is-disabled': disabled })}
      onClick={() => {
        onClick({ paymentMethod });
      }}
    >
      <i className={cx('payment-method-icon fa-regular', icon)} />
      <span className="payment-method-caption">{caption}</span>
    </div>
  );
};
PaymentMethodButton.propTypes = {
  paymentMethod: PropTypes.string.isRequired,
  disabled: PropTypes.bool,
  onClick: PropTypes.func.isRequired,
};

//
//
//

export default POSPaymentPanel;
