import { useDispatch } from 'react-redux';
import CashPaymentDetailsModal from './CashPaymentDetailsModal';
import { checkoutPayment, removePayment } from '../../actions/orders';
import CardPaymentDetailsModal from './CardPaymentDetailsModal';
import React from 'react';
import PropTypes from 'prop-types';
import { round } from '../../../../utils/numbers';

const PendingPaymentModal = ({
  posTerminalId,
  order_uuid,
  orderTotalAmount,
  payments,
  currency,
  currencyPrecision,
}) => {
  const dispatch = useDispatch();

  const payment = findNextPendingPayment({ payments });
  if (!payment) return null;

  const handleCheckout = ({ cardPayAmount, cashTenderedAmount } = {}) => {
    dispatch(
      checkoutPayment({
        posTerminalId,
        order_uuid,
        payment_uuid: payment.uuid,
        cardPayAmount,
        cashTenderedAmount,
      })
    );
  };

  const handleDelete = () => {
    dispatch(removePayment({ posTerminalId, order_uuid, payment_uuid: payment.uuid }));
  };

  if (payment.paymentMethod === 'CASH') {
    return (
      <CashPaymentDetailsModal
        currency={currency}
        precision={currencyPrecision}
        payAmount={payment.amount}
        tenderedAmount={payment.cashTenderedAmount}
        onOK={handleCheckout}
        isAllowCancel={payment.allowDelete}
        onCancel={handleDelete}
      />
    );
  } else if (payment.paymentMethod === 'CARD') {
    const orderOpenAmount = computeOrderOpenAmount({
      orderTotalAmount,
      currencyPrecision,
      payments,
      exclude_payment_uuid: payment.uuid,
    });

    return (
      <CardPaymentDetailsModal
        currency={currency}
        precision={currencyPrecision}
        orderOpenAmount={orderOpenAmount}
        payAmount={payment.amount}
        cardReaderName={payment.cardReaderName}
        status={payment.status}
        onCheckout={handleCheckout}
        onDelete={handleDelete}
      />
    );
  } else {
    console.log('Payment method is not handled', { payment, payments });
    return null;
  }
};

PendingPaymentModal.propTypes = {
  posTerminalId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
  order_uuid: PropTypes.string.isRequired,
  orderTotalAmount: PropTypes.number.isRequired,
  payments: PropTypes.array.isRequired,
  currency: PropTypes.string.isRequired,
  currencyPrecision: PropTypes.number.isRequired,
};

export default PendingPaymentModal;

//
//
//
//
//
//

const findNextPendingPayment = ({ payments }) => {
  if (!payments?.length) return null;

  // First, take care of card payments
  for (const payment of payments) {
    if (
      payment.paymentMethod === 'CARD' &&
      (payment.status === 'NEW' || payment.status === 'PENDING' || payment.status === 'FAILED')
    ) {
      return payment;
    }
  }

  // Lastly, the cash payments
  for (const payment of payments) {
    if (payment.paymentMethod === 'CASH' && payment.status === 'PENDING') {
      return payment;
    }
  }

  return null;
};

const computeOrderOpenAmount = ({ orderTotalAmount, currencyPrecision, payments, exclude_payment_uuid }) => {
  const paidAmt = computeOrderPaidAmount({ payments, exclude_payment_uuid });
  return round(orderTotalAmount - paidAmt, currencyPrecision);
};

const computeOrderPaidAmount = ({ payments, exclude_payment_uuid }) => {
  if (!payments?.length) return 0;

  return payments
    .filter((payment) => !exclude_payment_uuid || payment.uuid !== exclude_payment_uuid)
    .map((payment) => payment.amount)
    .reduce((acc, amount) => acc + amount, 0);
};
