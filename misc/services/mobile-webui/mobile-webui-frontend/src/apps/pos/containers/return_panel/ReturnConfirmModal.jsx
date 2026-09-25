import React, { useState } from 'react';
import PropTypes from 'prop-types';
import useEscapeKey from '../../../../hooks/useEscapeKey';
import { formatAmountToHumanReadableStr } from '../../../../utils/money';
import { toastError } from '../../../../utils/toast';
import { trl } from '../../../../utils/translations';
import { createReturn } from '../../api/returns';

const _ = (key, args) => trl(`pos.return.confirmModal.${key}`, args);

/**
 * Confirms the return and pays the credit memo out in cash. On success shows the credit-memo number and
 * refunded amount. On a server error the cart stays intact (the modal itself stays open, re-enabled) so the
 * cashier can retry — the retry POSTs the SAME {@code externalId}, so it never creates a second return.
 */
const ReturnConfirmModal = ({
  posTerminalId,
  externalId,
  lines,
  totalAmt,
  currencySymbol,
  currencyPrecision,
  onConfirmed,
  onCancel,
}) => {
  const [isSubmitting, setSubmitting] = useState(false);
  const [result, setResult] = useState(null);

  useEscapeKey(() => {
    if (!isSubmitting && !result) onCancel();
  });

  const onOK = () => {
    if (isSubmitting) return;

    setSubmitting(true);
    createReturn({ posTerminalId, externalId, lines })
      .then((response) => setResult(response))
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setSubmitting(false));
  };

  const totalAmtStr = formatAmountToHumanReadableStr({
    amount: totalAmt,
    currency: currencySymbol,
    precision: currencyPrecision,
  });

  if (result) {
    const refundAmtStr = formatAmountToHumanReadableStr({
      amount: result.refundAmount,
      currency: currencySymbol,
      precision: currencyPrecision,
    });

    return (
      <div className="modal is-active pos-return-confirm-modal" data-testid="pos-return-confirm-modal">
        <div className="modal-background"></div>
        <div className="modal-card">
          <section className="modal-card-body">
            <div className="pos-return-success" data-testid="pos-return-success">
              {_('success', { docNo: result.creditMemoDocumentNo, amount: refundAmtStr })}
            </div>
          </section>
          <footer className="modal-card-foot">
            <div className="buttons">
              <button
                className="button is-large"
                data-testid="pos-return-confirm-close-button"
                onClick={() => onConfirmed(result)}
              >
                {_('actions.close')}
              </button>
            </div>
          </footer>
        </div>
      </div>
    );
  }

  return (
    <div className="modal is-active pos-return-confirm-modal" data-testid="pos-return-confirm-modal">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">{_('title')}</p>
          <button className="delete" aria-label="close" disabled={isSubmitting} onClick={onCancel}></button>
        </header>
        <section className="modal-card-body">
          <div className="amount-line">
            <div className="caption">{_('totalAmt')}</div>
            <div className="amount" data-testid="pos-return-confirm-amount">
              {totalAmtStr}
            </div>
          </div>
        </section>
        <footer className="modal-card-foot">
          <div className="buttons">
            <button
              className="button is-large is-success"
              data-testid="pos-return-confirm-ok-button"
              disabled={isSubmitting}
              onClick={onOK}
            >
              {_('payCash')}
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

ReturnConfirmModal.propTypes = {
  posTerminalId: PropTypes.string,
  externalId: PropTypes.string.isRequired,
  lines: PropTypes.arrayOf(
    PropTypes.shape({
      productId: PropTypes.number.isRequired,
      qty: PropTypes.number.isRequired,
    })
  ).isRequired,
  totalAmt: PropTypes.number.isRequired,
  currencySymbol: PropTypes.string,
  currencyPrecision: PropTypes.number,
  onConfirmed: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
};

export default ReturnConfirmModal;
