import { trl } from '../../../utils/translations';
import React from 'react';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import PropTypes from 'prop-types';
import DialogButton from '../../../components/dialogs/DialogButton';
import Dialog from '../../../components/dialogs/Dialog';
import { toLocatorQRCode } from '../../../api/warehouse';
import { toastError } from '../../../utils/toast';

const ChangeCurrentLocatorDialog = ({ onOK, onClose }) => {
  return (
    <div>
      <Dialog className="screen" testId="ChangeCurrentLocatorDialog">
        <BarcodeScannerComponent
          inputPlaceholderText={trl('huManager.locator')}
          onResolvedResult={({ scannedBarcode }) => {
            toLocatorQRCode({ scannedBarcode })
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
