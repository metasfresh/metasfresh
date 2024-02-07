import React from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../../utils/translations';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';

const UnpickDialog = ({ onSubmit, onCloseDialog }) => {
  const onResolvedQrCode = ({ scannedBarcode }) => {
    onSubmit({ unpickToTargetQRCode: scannedBarcode });
  };

  return (
    <div className="prompt-dialog screen">
      <article className="message is-dark">
        <div className="message-body">
          <table className="table">
            <tbody>
              <tr>
                <td colSpan="2">
                  <BarcodeScannerComponent
                    inputPlaceholderText={trl('activities.picking.scanTargetHU')}
                    onResolvedResult={onResolvedQrCode}
                  />
                </td>
              </tr>
            </tbody>
          </table>
          <div className="buttons is-centered">
            <button className="button" onClick={onSubmit}>
              {trl('activities.picking.skip')}
            </button>
            <button className="button is-danger" onClick={onCloseDialog}>
              {trl('general.closeText')}
            </button>
          </div>
        </div>
      </article>
    </div>
  );
};

UnpickDialog.propTypes = {
  // Callbacks
  onSubmit: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func.isRequired,
};

export default UnpickDialog;
