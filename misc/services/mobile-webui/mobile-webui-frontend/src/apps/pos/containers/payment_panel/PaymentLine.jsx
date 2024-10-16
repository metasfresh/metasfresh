import { getPaymentMethodIcon } from '../../constants/paymentMethods';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import React from 'react';
import PropTypes from 'prop-types';
import { getPaymentSummaryLine } from '../../utils/payments';

const PaymentLine = ({
  uuid,
  disabled,
  paymentMethod,
  amount,
  currency,
  currencyPrecision,
  status,
  statusDetails,
  //
  allowCheckout: allowCheckoutParam,
  onCheckout,
  allowDelete,
  onDelete,
  allowRefund,
  onRefund,
}) => {
  const icon = getPaymentMethodIcon({ paymentMethod });
  const iconClassName = `fa-regular ${icon}`;
  const caption = getPaymentSummaryLine({ paymentMethod, status, statusDetails });
  const amountStr = formatAmountToHumanReadableStr({
    amount: amount,
    currency: currency,
    precision: currencyPrecision,
  });

  const allowCheckout = !disabled && allowCheckoutParam;
  const allowDeleteOrRefund = !disabled && ((allowDelete && onDelete) || (allowRefund && onRefund));
  const fireDeleteOrRefund = () => {
    if (allowDelete && onDelete) {
      onDelete({ uuid });
    } else if (allowRefund && onRefund) {
      onRefund({ uuid });
    }
  };

  return (
    <div className="payment-line">
      <div className="payment-line-label">
        <i className={iconClassName} /> {caption}
      </div>
      <div className="payment-line-value">{amountStr}</div>
      <div className="payment-line-actions">
        {allowCheckout && onCheckout && (
          <div className="payment-line-action-item" onClick={() => onCheckout({ uuid })}>
            <i className="fa-solid fa-repeat" />
          </div>
        )}
        {allowDeleteOrRefund && (
          <div className="payment-line-action-item" onClick={fireDeleteOrRefund}>
            <i className="fa-solid fa-trash-can" />
          </div>
        )}
      </div>
    </div>
  );
};

PaymentLine.propTypes = {
  uuid: PropTypes.string.isRequired,
  disabled: PropTypes.bool,
  paymentMethod: PropTypes.string.isRequired,
  amount: PropTypes.number.isRequired,
  currency: PropTypes.string.isRequired,
  currencyPrecision: PropTypes.number.isRequired,
  status: PropTypes.string,
  statusDetails: PropTypes.string,
  allowCheckout: PropTypes.bool,
  onCheckout: PropTypes.func.isRequired,
  allowDelete: PropTypes.bool,
  onDelete: PropTypes.func.isRequired,
  allowRefund: PropTypes.bool,
  onRefund: PropTypes.func.isRequired,
};

export default PaymentLine;
