import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../../utils/translations';

import ClearanceStatusRadioGroup from './ClearanceStatusRadioGroup';

const ClearanceDialog = ({ handlingUnitInfo, clearanceStatuses, onStatusChange, onCloseDialog }) => {
  const { clearanceStatus, clearanceNote } = handlingUnitInfo;
  const clearanceStatusKey = clearanceStatus ? clearanceStatus.key : null;
  const [currentStatus, setClearanceStatus] = useState(clearanceStatusKey);
  const [currentNote, setClearanceNote] = useState(clearanceNote || '');

  const onClearanceNoteChange = (e) => {
    const val = e.target.value;

    setClearanceNote(val);
  };

  const onSelectedStatus = (statusKey) => {
    setClearanceStatus(statusKey);
  };

  const onDialogYes = () => {
    onStatusChange({ clearanceStatus, clearanceNote });
  };

  return (
    <div>
      <div className="prompt-dialog screen">
        <article className="message is-dark">
          <div className="message-body">
            <table className="table">
              <tbody>
                <tr>
                  <th colSpan={2}>{trl('huManager.action.setClearance.buttonCaption')}</th>
                </tr>
                <tr>
                  <td colSpan={2}>
                    <ClearanceStatusRadioGroup
                      clearanceStatuses={clearanceStatuses}
                      selectedStatus={currentStatus}
                      onSelectedStatus={onSelectedStatus}
                    />
                  </td>
                </tr>
                <tr>
                  <th>{trl('huManager.action.setClearance.clearanceNote')}</th>
                  <td>
                    <div className="field">
                      <div className="control">
                        <input className="input" type="text" value={currentNote} onChange={onClearanceNoteChange} />
                      </div>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
            <div className="buttons is-centered">
              <button className="button is-danger" onClick={onDialogYes}>
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

ClearanceDialog.propTypes = {
  // Properties
  clearanceStatuses: PropTypes.array.isRequired,
  handlingUnitInfo: PropTypes.object.isRequired,

  // Callbacks
  onStatusChange: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func.isRequired,
};

export default ClearanceDialog;
