import React, { useState } from 'react';
import PropTypes from 'prop-types';
import './PaymentDetailsModal.scss';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import Spinner from '../../../../components/Spinner';
import { NumericKeyboard, recomputeAmount, toEditingAmount } from '../NumericKeyboard';
import cx from 'classnames';
import { PAYMENT_STATUS_FAILED, PAYMENT_STATUS_NEW, PAYMENT_STATUS_PENDING } from '../../constants/paymentStatus';
import { trl } from '../../../../utils/translations';

const _ = (key) => trl(`pos.payment.modal.card.${key}`);

const CardPaymentDetailsModal = ({
  currency,
  precision,
  orderOpenAmount,
  payAmount: payAmountParam,
  cardReaderName,
  status,
  onDelete,
  onCheckout,
}) => {
  const [editingPayAmount, setEditingPayAmount] = useState(() => toEditingAmount({ value: payAmountParam, precision }));

  const payAmount = editingPayAmount.value;
  const isPayAmountValid = payAmount > 0 && payAmount <= orderOpenAmount;

  const orderOpenAmountStr = formatAmountToHumanReadableStr({ amount: orderOpenAmount, currency, precision });
  const payAmountStr = formatAmountToHumanReadableStr({ amount: payAmount, currency, precision });

  const isAllowEditing = status === PAYMENT_STATUS_NEW || status === PAYMENT_STATUS_FAILED;
  const isPending = status === PAYMENT_STATUS_PENDING;
  const isAllowCancel = isAllowEditing;
  const isAllowCheckout = isAllowEditing && isPayAmountValid;

  const handleOpenAmountClick = () => {
    setEditingPayAmount(() => toEditingAmount({ value: payAmountParam, precision }));
  };
  const handleNumKeyPressed = (key) => {
    if (!isAllowEditing) return;
    setEditingPayAmount((editingPayAmount) => recomputeAmount(editingPayAmount, key));
  };

  const handleCheckoutClicked = () => {
    if (!isAllowCheckout) return;
    onCheckout({ cardPayAmount: payAmount });
  };

  const handleCancelClicked = () => {
    if (!isAllowCancel) return;
    onDelete();
  };

  return (
    <div className="modal is-active payment-details-modal">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">{_('title')}</p>
        </header>
        <section className="modal-card-body">
          <div className="detail-line" onClick={handleOpenAmountClick}>
            <div className="detail-caption">{_('orderOpenAmt')}</div>
            <div className="detail-value">{orderOpenAmountStr}</div>
          </div>
          <div className="detail-line">
            <div className="detail-caption">{_('payAmt')}</div>
            <div className={cx('detail-value', { 'has-text-danger': !isPayAmountValid })}>{payAmountStr}</div>
          </div>
          {cardReaderName && (
            <div className="detail-line">
              <div className="detail-caption">{_('cardReader')}</div>
              <div className="detail-value">{cardReaderName}</div>
            </div>
          )}
          {isAllowEditing && (
            <div className="numpad-container">
              <NumericKeyboard onKey={handleNumKeyPressed} />
            </div>
          )}
          {isPending && (
            <div className="detail-line-spinner">
              <Spinner />
            </div>
          )}
        </section>
        <footer className="modal-card-foot">
          <div className="buttons">
            {isAllowCheckout && (
              <button className="button is-large" disabled={!isAllowCheckout} onClick={handleCheckoutClicked}>
                {_('actions.checkout')}
              </button>
            )}
            {isAllowCancel && (
              <button className="button is-large" disabled={!isAllowCancel} onClick={handleCancelClicked}>
                {_('actions.cancel')}
              </button>
            )}
          </div>
        </footer>
      </div>
    </div>
  );
};
CardPaymentDetailsModal.propTypes = {
  currency: PropTypes.string.isRequired,
  precision: PropTypes.number.isRequired,
  orderOpenAmount: PropTypes.number.isRequired,
  payAmount: PropTypes.number.isRequired,
  cardReaderName: PropTypes.string,
  status: PropTypes.string.isRequired,
  onDelete: PropTypes.func.isRequired,
  onCheckout: PropTypes.func.isRequired,
};

export default CardPaymentDetailsModal;
