import React, { useCallback, useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../../utils/translations';
import { resolveUnpickByScannedCode } from '../../../api/picking';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import DialogButton from '../../../components/dialogs/DialogButton';
import Dialog from '../../../components/dialogs/Dialog';

/**
 * Step 1 of the partial-unpick flow: scan a product GTIN of an item in the package.
 *
 * The scanned code is sent to the backend resolve endpoint (a GTIN is not a QR code, so it is
 * NOT parsed client-side). The backend answers with the product and the qty currently packed for
 * it in this job. If nothing is packed for the scanned product (packedQty null/zero), AC2 applies:
 * the product is not in this package -> show ONE inline error (data-testid="unpick-error") and
 * remove nothing. A genuine backend/network failure is re-thrown so BarcodeScannerComponent
 * surfaces it via its single toast (keeps exactly ONE error surface per failure).
 */
const UnpickProductScanDialog = ({ wfProcessId, onResolved, onCloseDialog }) => {
  const [errorMessage, setErrorMessage] = useState(null);

  // Always resolve successfully here (carrying the backend response); a real axios failure is
  // re-thrown so the scanner component's catch toasts it. AC2 (not-in-package) is decided in
  // onResolvedResult so it renders inline as the one error surface, not as a toast.
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
      // AC2: code resolved but the product is not (or no longer) packed in this job.
      const isPacked = response?.packedQty != null && Number(response.packedQty) > 0;
      if (!response?.productId || !isPacked) {
        setErrorMessage(trl('activities.picking.unpick.productNotInPackage'));
        return;
      }
      setErrorMessage(null);
      onResolved(response);
    },
    [onResolved]
  );

  return (
    <Dialog className="screen unpick-dialog">
      <table className="table">
        <tbody>
          <tr>
            <td colSpan="2">
              <BarcodeScannerComponent
                testId="unpick-product-scanner"
                inputPlaceholderText={trl('activities.picking.unpick.scanProduct')}
                resolveScannedBarcode={resolveScannedBarcode}
                onResolvedResult={onResolvedResult}
              />
            </td>
          </tr>
        </tbody>
      </table>
      {errorMessage && (
        <p className="help is-danger" data-testid="unpick-error">
          {errorMessage}
        </p>
      )}
      <div className="buttons is-centered">
        <DialogButton captionKey="general.closeText" className="is-danger" onClick={onCloseDialog} />
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
