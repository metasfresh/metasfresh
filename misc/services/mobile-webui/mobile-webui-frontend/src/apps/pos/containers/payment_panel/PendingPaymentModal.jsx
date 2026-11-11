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
import { usePollWhile } from '../card_release/usePollWhile';
import { useCardRelease } from '../card_release/useCardRelease';

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
  const [isCheckFailed, setCheckFailed] = useState(false);
  const [now, setNow] = useState(Date.now());

  //
  // Poll the order while the card payment's result is unknown and keep "now" current for the release button.
  const { markChanged: markOrderChanged } = usePollWhile({
    isActive: !!unknownCardPayment_uuid,
    pollKey: `${posTerminalId}/${order_uuid}/${unknownCardPayment_uuid}`,
    intervalMillis: UNKNOWN_PAYMENT_POLL_MILLIS,
    poll: () => getOpenOrders({ posTerminalId, ids: [order_uuid] }),
    onResult: (result) => {
      const order = result?.list?.find((order) => order.uuid === order_uuid);
      if (order) {
        dispatch(updateOrderFromBackendAction({ order }));
      }
    },
    onTick: () => setNow(Date.now()),
  });

  /** Applies an order from a check / release response; an older poll answer arriving later is ignored */
  const applyOrder = (order) => {
    markOrderChanged();
    dispatch(updateOrderFromBackendAction({ order }));
  };

  const cardRelease = useCardRelease({
    status: payment?.status,
    isReleasable: !!unknownCardPayment_uuid,
    isSubmitting,
    setSubmitting,
    release: ({ confirmNotCharged, expectedStatus }) =>
      releasePaymentAPI({
        posTerminalId,
        order_uuid,
        payment_uuid: unknownCardPayment_uuid,
        confirmNotCharged,
        expectedStatus,
      }).then((response) => {
        applyOrder(response.order);
        setCheckFailed(!!response.checkFailed);
        return {
          status: response.order?.payments?.find((p) => p.uuid === unknownCardPayment_uuid)?.status ?? null,
          confirmationRequired: response.confirmationRequired,
          refusedMessage: response.refusedMessage,
        };
      }),
  });

  //
  // Opening an unknown card payment: forget the previous payment's release state and ask SumUp once for its result.
  useEffect(() => {
    cardRelease.reset();
    setCheckFailed(false);
    setSubmitting(false);
    setNow(Date.now());
    if (!unknownCardPayment_uuid) {
      return;
    }

    let isStopped = false;
    setSubmitting(true);
    checkPaymentAPI({ posTerminalId, order_uuid, payment_uuid: unknownCardPayment_uuid })
      .then((response) => {
        if (!isStopped) {
          applyOrder(response.order);
          setCheckFailed(!!response.checkFailed);
        }
      })
      .catch((axiosError) => {
        if (!isStopped) {
          toastError({ axiosError });
        }
      })
      .finally(() => {
        if (!isStopped) {
          setSubmitting(false);
        }
      });

    return () => {
      isStopped = true;
    };
  }, [dispatch, posTerminalId, order_uuid, unknownCardPayment_uuid]);

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
        onRelease={cardRelease.onRelease}
        isReleaseConfirmationShown={cardRelease.isConfirmationShown}
        onConfirmRelease={cardRelease.onConfirmRelease}
        onCancelReleaseConfirmation={cardRelease.onCancelConfirmation}
        releaseMessage={cardRelease.releaseMessage}
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
