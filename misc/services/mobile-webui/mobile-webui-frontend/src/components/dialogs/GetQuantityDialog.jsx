import React, { useState } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import QtyInputField from './QtyInputField';

const GetQuantityDialog = ({
  qtyInitial,
  qtyTarget,
  qtyCaption,
  uom,
  validateQtyEntered,
  onQtyChange,
  onCloseDialog,
}) => {
  const [qty, setQty] = useState(null);

  const onDialogYes = () => {
    if (qty != null) {
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
            <QtyInputField
              qtyInitial={qtyInitial}
              validateQtyEntered={validateQtyEntered}
              onQtyChange={(qty) => setQty(qty)}
              isRequestFocus={true}
            />
            <div className="buttons is-centered">
              <button className="button is-danger" disabled={qty == null} onClick={onDialogYes}>
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
