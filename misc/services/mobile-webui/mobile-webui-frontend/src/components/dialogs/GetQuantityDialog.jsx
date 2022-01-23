import React, { useRef, useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

const GetQuantityDialog = ({
  qtyInitial,
  qtyTarget,
  qtyCaption,
  uom,
  validateQtyEntered,
  onQtyChange,
  onCloseDialog,
}) => {
  const [qtyStr, setQtyStr] = useState(qtyInitial > 0 ? `${qtyInitial}` : '0');
  const qtyInputRef = useRef(null);

  useEffect(() => {
    qtyInputRef.current.focus();
    qtyInputRef.current.select();
  }, []);

  const changeQuantity = (e) => {
    if (!e.target.value) {
      setQtyStr('0');
    } else {
      const qtyEntered = parseFloat(e.target.value);
      if (isNaN(qtyEntered)) {
        return;
      }

      setQtyStr(`${qtyEntered}`);
    }
  };

  const qty = parseFloat(qtyStr);
  let isQtyValid = true;
  let notValidMessage = null;
  if (isNaN(qty)) {
    isQtyValid = false;
    // preserve last notValidMessage
  } else if (validateQtyEntered) {
    notValidMessage = validateQtyEntered(qty);
    isQtyValid = !notValidMessage;
  }

  const onDialogYes = () => {
    if (isQtyValid) {
      onQtyChange(qty);
    }
  };

  return (
    <div>
      <div className="prompt-dialog-screen">
        <article className="message is-dark">
          <div className="message-body">
            <strong>
              {qtyCaption}: {qtyTarget > 0 ? qtyTarget : 0} {uom}
            </strong>
            <p className="help is-danger">{notValidMessage}&nbsp;</p>
            <div className="control has-icons-right">
              <input ref={qtyInputRef} className="input" type="number" value={qtyStr} onChange={changeQuantity} />
              {!isQtyValid && (
                <span className="icon is-small is-right">
                  <i className="fas fa-exclamation-triangle" />
                </span>
              )}
            </div>
            <div className="buttons is-centered">
              <button className="button is-danger" disabled={!isQtyValid} onClick={onDialogYes}>
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
  validateQtyEntered: PropTypes.func,
  onQtyChange: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func,
};

export default GetQuantityDialog;
