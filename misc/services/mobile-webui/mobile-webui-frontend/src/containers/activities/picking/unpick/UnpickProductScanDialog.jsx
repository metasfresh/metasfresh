import React, { useCallback, useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../../../utils/translations';
import { resolveUnpickByScannedCode } from '../../../../api/picking';
import BarcodeScannerComponent from '../../../../components/BarcodeScannerComponent';
import DialogButton from '../../../../components/dialogs/DialogButton';
import Dialog from '../../../../components/dialogs/Dialog';

// Trust the backend's single source of truth: JsonUnpickResolveResponse.unpickable (true when
// packedQty>0). Don't recompute "is there something to unpick" from packedQty on the FE — that would
// drift from the backend rule.
export const isResolvedProductUnpickable = (response) => Boolean(response?.productId && response?.unpickable);

// Stage 1 of UnpickPanel: scans a product GTIN and resolves it against the picking job; surfaces an
// inline error when the scanned product is not packed in this job (the qty/target stages live in
// UnpickPanel).
const UnpickProductScanDialog = ({ wfProcessId, onResolved, onCloseDialog }) => {
  const [errorMessage, setErrorMessage] = useState(null);

  // Resolve always succeeds here (carrying the backend response); a real axios failure is re-thrown
  // so BarcodeScannerComponent's catch toasts it. "Not packed in this job" is decided below so it
  // renders inline (one error surface), not as a toast.
  const resolveScannedBarcode = useCallback(
    ({ scannedBarcode }) => {
      return resolveUnpickByScannedCode({ wfProcessId, scannedCode: scannedBarcode }).then((response) => ({
        scannedBarcode,
        resolved: response,
      }));
    },
    [wfProcessId]
  );

  const onResolvedResult = useCallback(
    (result) => {
      const response = result?.resolved;
      if (!isResolvedProductUnpickable(response)) {
        setErrorMessage(trl('activities.picking.unpick.productNotInPackage'));
        return;
      }
      setErrorMessage(null);
      onResolved({ ...response, scannedCode: result?.scannedBarcode });
    },
    [onResolved]
  );

  return (
    <Dialog className="screen unpick-dialog">
      <BarcodeScannerComponent
        testId="unpick-product-scanner"
        inputPlaceholderText={trl('activities.picking.unpick.scanProduct')}
        resolveScannedBarcode={resolveScannedBarcode}
        onResolvedResult={onResolvedResult}
      />
      {errorMessage && (
        <p className="help is-danger" data-testid="unpick-error">
          {errorMessage}
        </p>
      )}
      <div className="buttons is-centered">
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

UnpickProductScanDialog.propTypes = {
  // Properties
  wfProcessId: PropTypes.string.isRequired,
  // Callbacks
  onResolved: PropTypes.func.isRequired,
  onCloseDialog: PropTypes.func.isRequired,
};

export default UnpickProductScanDialog;
