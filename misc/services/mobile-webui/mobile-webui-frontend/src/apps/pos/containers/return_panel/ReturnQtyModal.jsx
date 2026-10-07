import React, { useState } from 'react';
import PropTypes from 'prop-types';
import { NumericKeyboard, recomputeAmount, toEditingAmount } from '../NumericKeyboard';
import { formatQtyToHumanReadableStr } from '../../../../utils/qtys';
import useEscapeKey from '../../../../hooks/useEscapeKey';
import { trl } from '../../../../utils/translations';

const _ = (key) => trl(`pos.return.qtyModal.${key}`);

// The POS product carries no UOM precision, so kg is keyed with 3 decimals (grams) and everything else as a whole quantity.
const getQtyEditPrecision = (uom) => (uom === 'kg' ? 3 : 0);

/**
 * Lets the cashier correct a return line's quantity on the on-screen keypad — e.g. when the returned
 * weight/qty differs from what the scanned label suggested. `qty` must stay `> 0`.
 */
const ReturnQtyModal = ({ uom, initialQty, onOk, onCancel }) => {
  const precision = getQtyEditPrecision(uom);
  const [editingAmount, setEditingAmount] = useState(() => toEditingAmount({ value: initialQty, precision }));

  const qty = editingAmount.value;
  const isValid = qty > 0;

  useEscapeKey(onCancel);

  const onNumKeyPressed = (key) => setEditingAmount((prevAmount) => recomputeAmount(prevAmount, key));

  const handleOk = () => {
    if (!isValid) return;
    onOk({ qty });
  };

  return (
    <div className="modal is-active pos-return-qty-modal" data-testid="pos-return-qty-modal">
      <div className="modal-background"></div>
      <div className="modal-card">
        <header className="modal-card-head">
          <p className="modal-card-title">{_('title')}</p>
          <button className="delete" aria-label="close" onClick={onCancel}></button>
        </header>
        <section className="modal-card-body">
          <div className="amount-line">
            <div className="amount" data-testid="pos-return-qty-modal-qty">
              {formatQtyToHumanReadableStr({ qty, uom, precision })}
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
              data-testid="pos-return-qty-modal-ok-button"
              disabled={!isValid}
              onClick={handleOk}
            >
              {_('actions.ok')}
            </button>
            <button className="button is-large" data-testid="pos-return-qty-modal-cancel-button" onClick={onCancel}>
              {_('actions.cancel')}
            </button>
          </div>
        </footer>
      </div>
    </div>
  );
};

ReturnQtyModal.propTypes = {
  uom: PropTypes.string.isRequired,
  initialQty: PropTypes.number.isRequired,
  onOk: PropTypes.func.isRequired,
  onCancel: PropTypes.func.isRequired,
};

export default ReturnQtyModal;
