import React, { useRef, useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

const GetQuantityDialog = ({ qtyInitial, qtyTarget, qtyCaption, uom, onQtyChange, onCloseDialog }) => {
  const [qty, setQty] = useState(qtyInitial > 0 ? qtyInitial : 0);
  const qtyInputRef = useRef(null);

  useEffect(() => {
    qtyInputRef.current.focus();
    qtyInputRef.current.select();
  }, []);

  const changeQuantity = (e) => {
    if (!e.target.value) {
      setQty(0);
    } else {
      const qtyEntered = parseFloat(e.target.value);
      if (isNaN(qtyEntered)) {
        return;
      }

      setQty(qtyEntered);
    }
  };

  const onDialogYes = () => {
    onQtyChange(qty);
  };

  const isYesButtonDisabled = !(qty > 0);

  return (
    <div>
      <div className="prompt-dialog-screen">
        <article className="message is-dark">
          <div className="message-body">
            <strong>
              {qtyCaption}: {qtyTarget > 0 ? qtyTarget : 0} {uom}
            </strong>
            <div className="control">
              <input ref={qtyInputRef} className="input" type="number" value={qty} onChange={changeQuantity} />
            </div>
            <div className="buttons is-centered">
              <button className="button is-danger" disabled={isYesButtonDisabled} onClick={onDialogYes}>
                {counterpart.translate('activities.picking.confirmDone')}
              </button>
              <button className="button is-success" onClick={onCloseDialog}>
                {counterpart.translate('general.cancelText')}
              </button>
            </div>
          </div>
        </article>
      </div>
    </div>
  );
};

GetQuantityDialog.propTypes = {
  // Properties
  qtyInitial: PropTypes.number,
  qtyTarget: PropTypes.number.isRequired,
  qtyCaption: PropTypes.string.isRequired,
  uom: PropTypes.string.isRequired,

  // Callbacks
  onQtyChange: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func,
};

export default GetQuantityDialog;
