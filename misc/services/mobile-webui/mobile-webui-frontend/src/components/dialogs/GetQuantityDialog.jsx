import React, { useState } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import QtyInputField from '../QtyInputField';
import QtyReasonsRadioGroup from '../QtyReasonsRadioGroup';

const GetQuantityDialog = ({
  qtyInitial,
  qtyTarget,
  qtyCaption,
  uom,
  qtyRejectedReasons,
  validateQtyEntered,
  onQtyChange,
  onCloseDialog,
}) => {
  const [qty, setQty] = useState(null);
  const [rejectedReason, setRejectedReason] = useState(null);

  const onQtyEntered = (qtyEntered) => {
    setQty(qtyEntered);
  };
  const onReasonSelected = (reason) => {
    console.log('onReasonSelected', reason);
    setRejectedReason(reason);
  };

  const requiredQtyRejectedReason = Array.isArray(qtyRejectedReasons) && qtyRejectedReasons.length > 0;
  const qtyRejected = requiredQtyRejectedReason ? Math.max(qtyTarget - qty, 0) : 0;

  const allValid = qty != null && (qtyRejected === 0 || rejectedReason != null);

  const onDialogYes = () => {
    if (allValid) {
      onQtyChange({
        qtyEnteredAndValidated: qty,
        qtyRejectedReason: qtyRejected > 0 ? rejectedReason : null,
      });
    }
  };

  return (
    <div>
      <div className="prompt-dialog-screen get-qty-dialog">
        <article className="message is-dark">
          <div className="message-body">
            <table className="table">
              <tbody>
                <tr>
                  <th>{qtyCaption}</th>
                  <td>
                    {qtyTarget > 0 ? qtyTarget : 0} {uom}
                  </td>
                </tr>
                <tr>
                  <th>Qty</th>
                  <td>
                    <QtyInputField
                      qtyInitial={qtyInitial}
                      uom={uom}
                      validateQtyEntered={validateQtyEntered}
                      onQtyChange={onQtyEntered}
                      isRequestFocus={true}
                    />
                  </td>
                </tr>
                {requiredQtyRejectedReason && (
                  <tr>
                    <th>{counterpart.translate('general.QtyRejected')}</th>
                    <td>
                      {qtyRejected} {uom}
                    </td>
                  </tr>
                )}
                {requiredQtyRejectedReason && (
                  <tr>
                    <td colSpan={2}>
                      <QtyReasonsRadioGroup
                        reasons={qtyRejectedReasons}
                        selectedReason={rejectedReason}
                        disabled={qtyRejected === 0}
                        onReasonSelected={onReasonSelected}
                      />
                    </td>
                  </tr>
                )}
              </tbody>
            </table>

            <div className="buttons is-centered">
              <button className="button is-danger" disabled={!allValid} onClick={onDialogYes}>
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
  qtyRejectedReasons: PropTypes.arrayOf(PropTypes.object),

  // Callbacks
  validateQtyEntered: PropTypes.func,
  onQtyChange: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func,
};

export default GetQuantityDialog;
