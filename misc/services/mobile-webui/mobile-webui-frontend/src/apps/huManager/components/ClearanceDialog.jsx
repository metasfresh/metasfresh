import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../../utils/translations';

import ClearanceStatusRadioGroup from './ClearanceStatusRadioGroup';

const ClearanceDialog = ({ handlingUnitInfo, clearanceStatuses, onClearanceChange, onCloseDialog }) => {
  const { clearanceStatus, clearanceNote } = handlingUnitInfo;

  const [currentStatus, setClearanceStatus] = useState(clearanceStatus || {});
  const [currentNote, setClearanceNote] = useState(clearanceNote || '');

  const onClearanceNoteChange = (e) => {
    const val = e.target.value;

    setClearanceNote(val);
  };

  const onSelectedStatus = ({ key, caption }) => {
    setClearanceStatus({ key, caption });
  };

  const onDialogYes = () => {
    onClearanceChange({
      clearanceStatus: { key: currentStatus.key, caption: currentStatus.caption },
      clearanceNote: currentNote,
    });
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
                      selectedStatus={currentStatus.key}
                      onSelectedStatus={onSelectedStatus}
                    />
                  </td>
                </tr>
                <tr>
                  <th>{trl('huManager.clearanceNote')}</th>
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
              <button className="button is-success" onClick={onDialogYes}>
                {trl('activities.picking.confirmDone')}
              </button>
              <button className="button is-danger" onClick={onCloseDialog}>
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
  onClearanceChange: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func.isRequired,
};

export default ClearanceDialog;
