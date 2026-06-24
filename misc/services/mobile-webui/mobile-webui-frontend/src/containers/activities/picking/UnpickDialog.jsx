import React, { useCallback } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../../utils/translations';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import DialogButton from '../../../components/dialogs/DialogButton';
import Dialog from '../../../components/dialogs/Dialog';

const UnpickDialog = ({
  onSubmit,
  onCloseDialog,
  allowSkip = true,
  scanPlaceholderKey = 'activities.picking.scanTargetHU',
  scannerTestId,
  skipTestId,
}) => {
  const onResolvedQrCode = useCallback(
    ({ scannedBarcode }) => {
      onSubmit({ unpickToTargetQRCode: scannedBarcode });
    },
    [onSubmit]
  );

  return (
    <Dialog className="screen unpick-dialog">
      <table className="table">
        <tbody>
          <tr>
            <td colSpan="2">
              <BarcodeScannerComponent
                testId={scannerTestId}
                inputPlaceholderText={trl(scanPlaceholderKey)}
                onResolvedResult={onResolvedQrCode}
              />
            </td>
          </tr>
        </tbody>
      </table>
      <div className="buttons is-centered">
        {allowSkip && <DialogButton captionKey="activities.picking.skip" testId={skipTestId} onClick={onSubmit} />}
        <DialogButton captionKey="general.closeText" className="is-danger" onClick={onCloseDialog} />
      </div>
    </Dialog>
  );
};

UnpickDialog.propTypes = {
  // Properties
  allowSkip: PropTypes.bool,
  scanPlaceholderKey: PropTypes.string,
  scannerTestId: PropTypes.string,
  skipTestId: PropTypes.string,
  // Callbacks
  onSubmit: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func.isRequired,
};

export default UnpickDialog;
