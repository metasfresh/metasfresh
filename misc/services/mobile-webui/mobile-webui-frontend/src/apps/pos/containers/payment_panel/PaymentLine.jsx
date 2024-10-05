import { getPaymentMethodCaption, getPaymentMethodIcon } from '../../utils/paymentMethods';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import React from 'react';
import PropTypes from 'prop-types';

const PaymentLine = ({
  uuid,
  paymentMethod,
  amount,
  currency,
  currencyPrecision,
  status,
  allowCheckout,
  onCheckout,
  allowDelete,
  onDelete,
}) => {
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
        <i className={iconClassName} /> {caption} ({status})
      </div>
      <div className="payment-line-value">{amountStr}</div>
      <div className="payment-line-actions">
        {allowCheckout && onCheckout && (
          <div className="payment-line-action-item" onClick={() => onCheckout({ uuid })}>
            <i className="fa-solid fa-repeat" />
          </div>
        )}
        {allowDelete && onDelete && (
          <div className="payment-line-action-item" onClick={() => onDelete({ uuid })}>
            <i className="fa-solid fa-trash-can" />
          </div>
        )}
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
  allowCheckout: PropTypes.bool,
  onCheckout: PropTypes.func.isRequired,
  allowDelete: PropTypes.bool,
  onDelete: PropTypes.func.isRequired,
};

export default PaymentLine;
