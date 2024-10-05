import { getPaymentMethodCaption, getPaymentMethodIcon } from '../../utils/paymentMethods';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import React from 'react';
import PropTypes from 'prop-types';

const PaymentLine = ({ uuid, paymentMethod, amount, currency, currencyPrecision, status, onDelete }) => {
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
        {status}
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
  status: PropTypes.string,
  onDelete: PropTypes.func.isRequired,
};

export default PaymentLine;
