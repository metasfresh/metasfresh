import React, { useCallback } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../../../utils/translations';
import BarcodeScannerComponent from '../../../../components/BarcodeScannerComponent';
import DialogButton from '../../../../components/dialogs/DialogButton';
import Dialog from '../../../../components/dialogs/Dialog';

// Destination scanner for an unpick: scan the target HU to return goods into, or Skip to drop them
// on the floor (always offered). Shared by the job-level UnpickPanel and the step-level
// PickStepScreen / DistributionStepScreen.
const UnpickTargetScanDialog = ({
  onSubmit,
  onCloseDialog,
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
      <BarcodeScannerComponent
        testId={scannerTestId}
        inputPlaceholderText={trl(scanPlaceholderKey)}
        onResolvedResult={onResolvedQrCode}
      />
      <div className="buttons is-centered">
        <DialogButton captionKey="activities.picking.skip" testId={skipTestId} onClick={onSubmit} />
        <DialogButton
          captionKey="general.closeText"
          className="is-danger"
          testId="unpick-close-button"
          onClick={onCloseDialog}
        />
      </div>
    </Dialog>
  );
};

UnpickTargetScanDialog.propTypes = {
  // Properties
  scanPlaceholderKey: PropTypes.string,
  scannerTestId: PropTypes.string,
  skipTestId: PropTypes.string,
  // Callbacks
  onSubmit: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func.isRequired,
};

export default UnpickTargetScanDialog;
