import { getPaymentMethodCaption, getPaymentMethodIcon } from '../../utils/paymentMethods';
import cx from 'classnames';
import React from 'react';
import PropTypes from 'prop-types';

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

export default PaymentMethodButton;
