import React, { useRef, useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../utils/translations';

import QtyInputField from '../QtyInputField';
import { qtyInfos } from '../../utils/qtyInfos';

const ChangeHUQtyDialog = ({
  currentQty,
  uom,
  //
  onSubmit,
  onCloseDialog,
}) => {
  const [qtyInfo, setQtyInfo] = useState(qtyInfos.invalidOfNumber(currentQty));
  const [qtyChangeDescription, setQtyChangeDescription] = useState('');

  const onQtyEntered = (qtyInfo) => setQtyInfo(qtyInfo);

  const allValid = qtyInfo !== null && qtyInfo.isQtyValid;

  const onDialogYes = () => {
    if (allValid) {
      onSubmit({
        qty: {
          qty: qtyInfos.toNumberOrString(qtyInfo),
          uomCode: uom,
        },
        description: qtyChangeDescription,
      });
    }
  };

  const onDescriptionChange = (e) => {
    const description = e.target.value ? e.target.value : '';
    setQtyChangeDescription(description);
  };

  const descriptionRef = useRef(null);

  return (
    <div>
      <div className="prompt-dialog get-qty-dialog">
        <article className="message is-dark">
          <div className="message-body">
            <table className="table">
              <tbody>
                <tr>
                  <th>{trl('huManager.action.changeQty.qtyLabel')}</th>
                  <td>
                    <QtyInputField
                      qty={qtyInfos.toNumberOrString(qtyInfo)}
                      uom={uom}
                      onQtyChange={onQtyEntered}
                      isRequestFocus={true}
                    />
                  </td>
                </tr>
                <tr>
                  <th>{trl('huManager.action.changeQty.descriptionLabel')}</th>
                  <td>
                    <textarea
                      ref={descriptionRef}
                      className="input"
                      value={qtyChangeDescription}
                      onChange={onDescriptionChange}
                    />
                  </td>
                </tr>
              </tbody>
            </table>

            <div className="buttons is-centered">
              <button className="button is-danger" disabled={!allValid} onClick={onDialogYes}>
                {trl('activities.picking.confirmDone')}
              </button>
              <button className="button is-success" onClick={onCloseDialog}>
                {trl('general.cancelText')}
              </button>
            </div>
          </div>
        </article>
      </div>
    </div>
  );
};

ChangeHUQtyDialog.propTypes = {
  // Properties
  currentQty: PropTypes.number.isRequired,
  uom: PropTypes.string.isRequired,

  // Callbacks
  onCloseDialog: PropTypes.func,
  onSubmit: PropTypes.func.isRequired,
};

export default ChangeHUQtyDialog;
