import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../utils/translations';

import QtyInputField from '../QtyInputField';
import { qtyInfos } from '../../utils/qtyInfos';
import { toastError } from '../../utils/toast';

const ReadQtyDialog = ({
  qtyLabelTrlKey,
  submitButtonTrlKey,
  cancelButtonTrlKey,
  isReadOnly = false,
  //
  onSubmit,
  onCloseDialog,
}) => {
  const [nrOfCopies, setNrOfCopies] = useState(qtyInfos.invalidOfNumber(1));

  const onQtyEntered = (qtyInfo) => setNrOfCopies(qtyInfo);

  const readQty = () => {
    if (!nrOfCopies || !nrOfCopies.isQtyValid) {
      toastError({ plainMessage: 'Invalid number' });
      return;
    }

    onSubmit({
      qty: qtyInfos.toNumberOrString(nrOfCopies),
    });
  };

  return (
    <div>
      <div className="prompt-dialog get-qty-dialog">
        <article className="message is-dark">
          <div className="message-body">
            <table className="table">
              <tbody>
                <tr>
                  <th>{trl(qtyLabelTrlKey || 'general.Qty')}</th>
                  <td>
                    <QtyInputField
                      readonly={isReadOnly}
                      qty={qtyInfos.toNumberOrString(nrOfCopies)}
                      onQtyChange={onQtyEntered}
                      isRequestFocus={true}
                    />
                  </td>
                </tr>
              </tbody>
            </table>
            <div className="buttons is-centered">
              <button disabled={isReadOnly} className="button is-danger" onClick={onCloseDialog}>
                {trl(cancelButtonTrlKey || 'general.cancelText')}
              </button>
              <button disabled={isReadOnly} className="button is-success" onClick={readQty}>
                {trl(submitButtonTrlKey || 'general.okText')}
              </button>
            </div>
          </div>
        </article>
      </div>
    </div>
  );
};

ReadQtyDialog.propTypes = {
  qtyLabelTrlKey: PropTypes.string,
  submitButtonTrlKey: PropTypes.string,
  cancelButtonTrlKey: PropTypes.string,
  isReadOnly: PropTypes.bool,
  // Callbacks
  onCloseDialog: PropTypes.func,
  onSubmit: PropTypes.func.isRequired,
};

export default ReadQtyDialog;
