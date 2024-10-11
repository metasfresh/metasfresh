import React, { useState } from 'react';
import cx from 'classnames';
import PropTypes from 'prop-types';
import './PaymentDetailsModal.scss';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { NumericKeyboard, recomputeAmount, toEditingAmount } from '../NumericKeyboard';

const CashPaymentDetailsModal = ({
  currency,
  precision,
  payAmount,
  tenderedAmount: tenderedAmountParam,
  isAllowCancel,
  onOK,
  onCancel,
}) => {
  const [tenderedEditingAmount, setTenderedEditingAmount] = useState(() =>
    toEditingAmount({ value: tenderedAmountParam, precision })
  );

  const tenderedAmount = tenderedEditingAmount.value;
  const changeBackAmount = tenderedAmount - payAmount;
  const isChangeBackAmountValid = changeBackAmount >= 0;
  const isTenderedAmountValid = tenderedAmount > 0 && tenderedAmount >= payAmount;
  const isValid = isTenderedAmountValid && isChangeBackAmountValid;

  const onNumKeyPressed = (key) => {
    setTenderedEditingAmount((tenderedEditingAmount) => recomputeAmount(tenderedEditingAmount, key));
  };
  const fireOK = () => {
    if (!isValid) return;
    onOK({ cashTenderedAmount: tenderedAmount });
  };
  const fireCancel = () => {
    if (!isAllowCancel) return;
    onCancel();
  };

  const payAmountStr = formatAmountToHumanReadableStr({ amount: payAmount, currency, precision });
  const tenderedAmountStr = formatAmountToHumanReadableStr({ amount: tenderedAmount, currency, precision });
  const changeBackAmountStr = formatAmountToHumanReadableStr({ amount: changeBackAmount, currency, precision });

  return (
    <div className="modal is-active payment-details-modal">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">Cash payment</p>
        </header>
        <section className="modal-card-body">
          <div className="detail-line">
            <div className="detail-caption">Amount to pay</div>
            <div className="detail-value">{payAmountStr}</div>
          </div>
          <div className="detail-line">
            <div className="detail-caption">Tendered Amount</div>
            <div className={cx('detail-value', { 'has-text-danger': !isTenderedAmountValid })}>{tenderedAmountStr}</div>
          </div>
          <div className="detail-line">
            <div className="detail-caption">Change</div>
            <div className={cx('detail-value', { 'has-text-danger': !isChangeBackAmountValid })}>
              {changeBackAmountStr}
            </div>
          </div>
          <div className="numpad-container">
            <NumericKeyboard onKey={onNumKeyPressed} />
          </div>
        </section>
        <footer className="modal-card-foot">
          <div className="buttons">
            <button className="button is-large" disabled={!isValid} onClick={fireOK}>
              OK
            </button>
            {isAllowCancel && (
              <button className="button is-large" onClick={fireCancel}>
                Cancel
              </button>
            )}
          </div>
        </footer>
      </div>
    </div>
  );
};
CashPaymentDetailsModal.propTypes = {
  currency: PropTypes.string.isRequired,
  precision: PropTypes.number.isRequired,
  payAmount: PropTypes.number.isRequired,
  tenderedAmount: PropTypes.number,
  changeBackAmount: PropTypes.number,
  isAllowCancel: PropTypes.bool.isRequired,
  onOK: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
};

export default CashPaymentDetailsModal;
