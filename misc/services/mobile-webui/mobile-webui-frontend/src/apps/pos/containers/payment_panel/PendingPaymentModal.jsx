import { useDispatch } from 'react-redux';
import CashPaymentDetailsModal from './CashPaymentDetailsModal';
import { checkoutPayment, removePayment } from '../../actions/orders';
import CardPaymentDetailsModal from './CardPaymentDetailsModal';
import React from 'react';
import PropTypes from 'prop-types';

const PendingPaymentModal = ({ posTerminalId, order_uuid, payments, currency, currencyPrecision }) => {
  const dispatch = useDispatch();

  for (const payment of payments) {
    if (payment.status === 'PENDING') {
      if (payment.paymentMethod === 'CASH') {
        return (
          <CashPaymentDetailsModal
            currency={currency}
            precision={currencyPrecision}
            payAmount={payment.amount}
            tenderedAmount={payment.cashTenderedAmount}
            onOK={({ cashTenderedAmount }) => {
              dispatch(
                checkoutPayment({
                  posTerminalId,
                  order_uuid,
                  payment_uuid: payment.uuid,
                  cashTenderedAmount,
                })
              );
            }}
            isAllowCancel={payment.allowDelete}
            onCancel={() => {
              dispatch(removePayment({ posTerminalId, order_uuid, payment_uuid: payment.uuid }));
            }}
          />
        );
      } else if (payment.paymentMethod === 'CARD') {
        return (
          <CardPaymentDetailsModal
            currency={currency}
            precision={currencyPrecision}
            payAmount={payment.amount}
            cardReaderName={payment.cardReaderName}
          />
        );
      }
    }
  }

  //
  //
  // NO modal
  return null;
};

PendingPaymentModal.propTypes = {
  posTerminalId: PropTypes.number.isRequired,
  order_uuid: PropTypes.string.isRequired,
  payments: PropTypes.array.isRequired,
  currency: PropTypes.string.isRequired,
  currencyPrecision: PropTypes.number.isRequired,
};

export default PendingPaymentModal;
