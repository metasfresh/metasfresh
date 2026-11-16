import React, { useEffect, useState } from 'react';
import cx from 'classnames';
import PropTypes from 'prop-types';
import './PaymentDetailsModal.scss';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { NumericKeyboard, recomputeAmount, toEditingAmount } from '../NumericKeyboard';
import { trl } from '../../../../utils/translations';

const _ = (key) => trl(`pos.payment.modal.cash.${key}`);

const CashPaymentDetailsModal = ({
  currency,
  precision,
  payAmount,
  tenderedAmount: tenderedAmountParam,
  tenderedEditingAmountDraft,
  onTenderedEditingAmountChanged,
  isAllowCancel,
  onOK,
  onCancel,
}) => {
  // Start from the draft, if any: this dialog can be re-mounted while the cashier is typing (see PendingPaymentModal)
  const [tenderedEditingAmount, setTenderedEditingAmount] = useState(
    () => tenderedEditingAmountDraft ?? toEditingAmount({ value: tenderedAmountParam ?? 0, precision })
  );
  // report only actual changes: the callback is a new function on every render, so it is deliberately not a dependency
  useEffect(() => {
    onTenderedEditingAmountChanged?.(tenderedEditingAmount);
  }, [tenderedEditingAmount]);

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
    <div className="modal is-active payment-details-modal" data-testid="pos-cash-payment-modal">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">{_('title')}</p>
        </header>
        <section className="modal-card-body">
          <div className="detail-line">
            <div className="detail-caption">{_('payAmt')}</div>
            <div className="detail-value">{payAmountStr}</div>
          </div>
          <div className="detail-line">
            <div className="detail-caption">{_('tenderedAmt')}</div>
            <div className={cx('detail-value', { 'has-text-danger': !isTenderedAmountValid })}>{tenderedAmountStr}</div>
          </div>
          <div className="detail-line">
            <div className="detail-caption">{_('changeBackAmt')}</div>
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
            <button
              className="button is-large"
              data-testid="pos-cash-payment-ok-button"
              disabled={!isValid}
              onClick={fireOK}
            >
              {_('actions.ok')}
            </button>
            {isAllowCancel && (
              <button className="button is-large" onClick={fireCancel}>
                {_('actions.cancel')}
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
  tenderedEditingAmountDraft: PropTypes.object,
  onTenderedEditingAmountChanged: PropTypes.func,
  changeBackAmount: PropTypes.number,
  isAllowCancel: PropTypes.bool.isRequired,
  onOK: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
};

export default CashPaymentDetailsModal;
