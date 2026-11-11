import { useDispatch } from 'react-redux';
import CashPaymentDetailsModal from './CashPaymentDetailsModal';
import { checkoutPayment, removePayment, updateOrderFromBackendAction } from '../../actions/orders';
import CardPaymentDetailsModal from './CardPaymentDetailsModal';
import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { round } from '../../../../utils/numbers';
import {
  PAYMENT_STATUS_FAILED,
  PAYMENT_STATUS_NEW,
  PAYMENT_STATUS_PENDING,
  PAYMENT_STATUS_UNKNOWN,
} from '../../constants/paymentStatus';
import { PAYMENT_METHOD_CARD, PAYMENT_METHOD_CASH } from '../../constants/paymentMethods';
import { checkPaymentAPI, getOpenOrders, releasePaymentAPI } from '../../api/orders';
import { toastError } from '../../../../utils/toast';

const UNKNOWN_PAYMENT_POLL_MILLIS = 2000;

/**
 * Shows the order's next payment that still needs the cashier.
 * <p>
 * A card payment whose result is unknown: on opening, SumUp is asked once for its current result; while it stays
 * unknown, the order is polled every 2 s; from the payment's {@code releasableAt} the cashier may check / release it,
 * confirming that the card was not charged.
 */
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
  const unknownCardPayment_uuid =
    payment?.paymentMethod === PAYMENT_METHOD_CARD && payment?.status === PAYMENT_STATUS_UNKNOWN ? payment.uuid : null;

  const [isSubmitting, setSubmitting] = useState(false);
  // the status the cashier was asked to confirm "not charged" for; null while no confirmation is asked
  const [releaseConfirmationStatus, setReleaseConfirmationStatus] = useState(null);
  const [releaseMessage, setReleaseMessage] = useState(null);
  const [isCheckFailed, setCheckFailed] = useState(false);
  const [now, setNow] = useState(Date.now());

  // the confirmation stays only while the payment is still in the status it was asked for
  const isReleaseConfirmationShown =
    releaseConfirmationStatus != null && !!unknownCardPayment_uuid && payment.status === releaseConfirmationStatus;

  //
  // Opening an unknown card payment: forget the previous payment's release state and ask SumUp once for its result.
  useEffect(() => {
    setReleaseConfirmationStatus(null);
    setReleaseMessage(null);
    setCheckFailed(false);
    setNow(Date.now());
    if (!unknownCardPayment_uuid) {
      return;
    }

    let isStopped = false;
    checkPaymentAPI({ posTerminalId, order_uuid, payment_uuid: unknownCardPayment_uuid })
      .then((order) => {
        if (!isStopped) {
          dispatch(updateOrderFromBackendAction({ order }));
        }
      })
      .catch((axiosError) => console.warn('Failed checking card payment', { unknownCardPayment_uuid, axiosError }));

    return () => {
      isStopped = true;
    };
  }, [unknownCardPayment_uuid]);

  //
  // Poll the order while the card payment's result is unknown and keep "now" current for the release button.
  // The next request is scheduled only after the previous one finished, so polls never overlap; a response arriving
  // after the poll stopped is ignored.
  useEffect(() => {
    if (!unknownCardPayment_uuid) {
      return;
    }

    let isStopped = false;
    let timeoutId = null;
    const pollOnce = () => {
      getOpenOrders({ posTerminalId, ids: [order_uuid] })
        .then((result) => {
          const order = result?.list?.find((order) => order.uuid === order_uuid);
          if (!isStopped && order) {
            dispatch(updateOrderFromBackendAction({ order }));
          }
        })
        .catch((axiosError) => console.warn('Failed polling order', { order_uuid, axiosError }))
        .finally(() => {
          if (!isStopped) {
            setNow(Date.now());
            timeoutId = setTimeout(pollOnce, UNKNOWN_PAYMENT_POLL_MILLIS);
          }
        });
    };
    timeoutId = setTimeout(pollOnce, UNKNOWN_PAYMENT_POLL_MILLIS);

    return () => {
      isStopped = true;
      clearTimeout(timeoutId);
    };
  }, [posTerminalId, order_uuid, unknownCardPayment_uuid]);

  //
  // The payment left the status the confirmation was asked for (e.g. it was approved meanwhile): drop the
  // confirmation, the modal shows the new state instead.
  useEffect(() => {
    if (releaseConfirmationStatus != null && !isReleaseConfirmationShown) {
      setReleaseConfirmationStatus(null);
    }
  }, [releaseConfirmationStatus, isReleaseConfirmationShown]);

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

  const applyReleaseResponse = (response) => {
    dispatch(updateOrderFromBackendAction({ order: response.order }));
    // remember the status the confirmation is asked for: the confirm request must refer to exactly that one
    const releasedPayment = response.order?.payments?.find((p) => p.uuid === payment.uuid);
    setReleaseConfirmationStatus(response.confirmationRequired ? releasedPayment?.status ?? null : null);
    setReleaseMessage(response.refusedMessage ?? null);
    setCheckFailed(!!response.checkFailed);
  };

  const callRelease = ({ confirmNotCharged, expectedStatus } = {}) => {
    if (isSubmitting) return;

    setSubmitting(true);
    releasePaymentAPI({ posTerminalId, order_uuid, payment_uuid: payment.uuid, confirmNotCharged, expectedStatus })
      .then(applyReleaseResponse)
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setSubmitting(false));
  };

  const handleRelease = () => callRelease();
  const handleConfirmRelease = () => {
    if (!isReleaseConfirmationShown) return;
    // released only if the payment is still in the status the cashier confirmed
    callRelease({ confirmNotCharged: true, expectedStatus: releaseConfirmationStatus });
  };

  if (payment.paymentMethod === PAYMENT_METHOD_CASH) {
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
  } else if (payment.paymentMethod === PAYMENT_METHOD_CARD) {
    const orderOpenAmount = computeOrderOpenAmount({
      orderTotalAmount,
      currencyPrecision,
      payments,
      exclude_payment_uuid: payment.uuid,
    });
    const isReleaseEnabled =
      !!unknownCardPayment_uuid && !!payment.releasableAt && now >= Date.parse(payment.releasableAt);

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
        isReleaseEnabled={isReleaseEnabled}
        onRelease={handleRelease}
        isReleaseConfirmationShown={isReleaseConfirmationShown}
        onConfirmRelease={handleConfirmRelease}
        onCancelReleaseConfirmation={() => setReleaseConfirmationStatus(null)}
        releaseMessage={releaseMessage}
        isCheckFailed={isCheckFailed}
        isSubmitting={isSubmitting}
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
      payment.paymentMethod === PAYMENT_METHOD_CARD &&
      (payment.status === PAYMENT_STATUS_NEW ||
        payment.status === PAYMENT_STATUS_PENDING ||
        payment.status === PAYMENT_STATUS_UNKNOWN ||
        payment.status === PAYMENT_STATUS_FAILED)
    ) {
      return payment;
    }
  }

  // Lastly, the cash payments
  for (const payment of payments) {
    if (
      payment.paymentMethod === PAYMENT_METHOD_CASH &&
      (payment.status === PAYMENT_STATUS_NEW || payment.status === PAYMENT_STATUS_PENDING)
    ) {
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
