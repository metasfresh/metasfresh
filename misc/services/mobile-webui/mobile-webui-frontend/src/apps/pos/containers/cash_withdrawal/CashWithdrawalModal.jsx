import React, { useState } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';
import { useDispatch } from 'react-redux';
import './CashWithdrawalModal.scss';
import { usePOSTerminal } from '../../actions/posTerminal';
import { closeModalAction, MODAL_CashWithdrawal } from '../../actions/ui';
import { withdrawCash } from '../../api/posJournal';
import { NumericKeyboard, recomputeAmount, toEditingAmount } from '../NumericKeyboard';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { toastError } from '../../../../utils/toast';
import { trl } from '../../../../utils/translations';
import useEscapeKey from '../../../../hooks/useEscapeKey';
import CashWithdrawalSlip from './CashWithdrawalSlip';

const _ = (key) => trl(`pos.cashWithdrawal.${key}`);

/**
 * Takes cash out of the till for an expense: the cashier picks the expense category and keys the amount; on confirm
 * the withdrawal is booked and its receipt slip is shown (and printed) for the recipient to sign.
 */
const CashWithdrawalModal = ({ categories }) => {
  const dispatch = useDispatch();
  const posTerminal = usePOSTerminal();
  const posTerminalId = posTerminal.id;
  const currency = posTerminal.currencySymbol;
  const precision = posTerminal.currencyPrecision ?? 2;

  const [chargeId, setChargeId] = useState(null);
  const [editingAmount, setEditingAmount] = useState(() => toEditingAmount({ value: 0, precision }));
  const [isSubmitting, setSubmitting] = useState(false);
  const [withdrawal, setWithdrawal] = useState(null);

  const amount = editingAmount.value;
  const isValid = !!chargeId && amount > 0;

  const closeModal = () => dispatch(closeModalAction({ ifModal: MODAL_CashWithdrawal }));
  const onCancel = () => {
    if (isSubmitting || withdrawal) return;
    closeModal();
  };
  useEscapeKey(onCancel);

  const onNumKeyPressed = (key) => setEditingAmount((editingAmount) => recomputeAmount(editingAmount, key));

  const onOK = () => {
    if (!isValid || isSubmitting) return;

    setSubmitting(true);
    withdrawCash({ posTerminalId, chargeId, amount })
      .then((result) => {
        setWithdrawal(result);
        posTerminal.reload();
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setSubmitting(false));
  };

  if (withdrawal) {
    return (
      <CashWithdrawalSlip
        withdrawal={withdrawal}
        currency={withdrawal.journal?.currencySymbol ?? currency}
        precision={withdrawal.journal?.currencyPrecision ?? precision}
        onClose={closeModal}
      />
    );
  }

  return (
    <div className="modal is-active pos-cash-withdrawal-modal" data-testid="pos-cash-withdrawal-modal">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">{_('title')}</p>
          <button className="delete" aria-label="close" onClick={onCancel}></button>
        </header>
        <section className="modal-card-body">
          <div className="caption">{_('category')}</div>
          <div className="categories">
            {categories.map((category) => {
              const isSelected = category.chargeId === chargeId;
              return (
                <button
                  key={category.chargeId}
                  className={cx('button is-large is-fullwidth', { 'is-info': isSelected })}
                  data-testid="pos-cash-withdrawal-category-button"
                  data-category-name={category.name}
                  data-selected={isSelected}
                  onClick={() => setChargeId(category.chargeId)}
                >
                  {category.name}
                </button>
              );
            })}
          </div>
          <div className="amount-line">
            <div className="caption">{_('amount')}</div>
            <div className="amount" data-testid="pos-cash-withdrawal-amount">
              {formatAmountToHumanReadableStr({ amount, currency, precision })}
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
              data-testid="pos-cash-withdrawal-ok-button"
              disabled={!isValid || isSubmitting}
              onClick={onOK}
            >
              {_('actions.ok')}
            </button>
            <button className="button is-large" disabled={isSubmitting} onClick={onCancel}>
              {_('actions.cancel')}
            </button>
          </div>
        </footer>
      </div>
    </div>
  );
};

CashWithdrawalModal.propTypes = {
  categories: PropTypes.arrayOf(
    PropTypes.shape({
      chargeId: PropTypes.number.isRequired,
      name: PropTypes.string.isRequired,
    })
  ).isRequired,
};

export default CashWithdrawalModal;
