export const PAYMENT_METHOD_CASH = 'CASH';
export const PAYMENT_METHOD_CARD = 'CARD';

export const getPaymentMethodCaption = ({ paymentMethod }) => {
  if (paymentMethod === PAYMENT_METHOD_CASH) {
    return 'Cash';
  } else if (paymentMethod === PAYMENT_METHOD_CARD) {
    return 'Card';
  }
};

export const getPaymentMethodIcon = ({ paymentMethod }) => {
  if (paymentMethod === PAYMENT_METHOD_CASH) {
    return 'fa-money-bill-1';
  } else if (paymentMethod === PAYMENT_METHOD_CARD) {
    return 'fa-credit-card';
  }
};
