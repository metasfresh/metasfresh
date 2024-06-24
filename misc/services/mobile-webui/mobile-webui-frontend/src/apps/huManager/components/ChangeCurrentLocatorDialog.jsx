import { trl } from '../../../utils/translations';
import React from 'react';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { parseLocatorQRCodeString } from '../../../utils/qrCode/locator';
import PropTypes from 'prop-types';

const ChangeCurrentLocatorDialog = ({ onOK, onClose }) => {
  return (
    <div>
      <div className="prompt-dialog screen">
        <article className="message is-dark">
          <BarcodeScannerComponent
            continuousRunning={true}
            inputPlaceholderText={trl('huManager.locator')}
            onResolvedResult={({ scannedBarcode }) => {
              const locatorQRCode = parseLocatorQRCodeString(scannedBarcode);
              onOK(locatorQRCode);
            }}
          />
          <div className="message-body">
            <div className="buttons is-centered">
              <button className="button is-danger" onClick={onClose}>
                {trl('general.cancelText')}
              </button>
            </div>
          </div>
        </article>
      </div>
    </div>
  );
};

ChangeCurrentLocatorDialog.propTypes = {
  onOK: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export default ChangeCurrentLocatorDialog;
