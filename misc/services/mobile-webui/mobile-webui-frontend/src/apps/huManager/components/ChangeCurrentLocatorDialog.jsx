import { trl } from '../../../utils/translations';
import React from 'react';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { isLocatorQRCodeString, parseLocatorQRCodeString } from '../../../utils/qrCode/locator';
import PropTypes from 'prop-types';
import DialogButton from '../../../components/dialogs/DialogButton';
import Dialog from '../../../components/dialogs/Dialog';
import * as warehouseAPI from '../../../api/warehouse';
import { toastError } from '../../../utils/toast';

const ChangeCurrentLocatorDialog = ({ onOK, onClose }) => {
  return (
    <div>
      <Dialog className="screen">
        <BarcodeScannerComponent
          continuousRunning={true}
          inputPlaceholderText={trl('huManager.locator')}
          onResolvedResult={({ scannedBarcode }) => {
            resolveLocatorQRCode({ scannedBarcode })
              .then((locatorQRCode) => onOK(locatorQRCode))
              .catch((axiosError) => toastError({ axiosError }));
          }}
        />
        <div className="buttons is-centered">
          <DialogButton captionKey="general.cancelText" className="is-danger" onClick={onClose} />
        </div>
      </Dialog>
    </div>
  );
};

ChangeCurrentLocatorDialog.propTypes = {
  onOK: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export default ChangeCurrentLocatorDialog;

//
//
//
//
//

const resolveLocatorQRCode = ({ scannedBarcode }) => {
  if (isLocatorQRCodeString(scannedBarcode)) {
    return Promise.resolve(parseLocatorQRCodeString(scannedBarcode));
  }

  return warehouseAPI.resolveLocatorScannedCode({ scannedBarcode }).then((response) => {
    if (response.error) {
      throw response.error;
    } else {
      return parseLocatorQRCodeString(response.locator.qrCode);
    }
  });
};
