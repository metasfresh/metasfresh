import React, { useCallback, useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../../../../utils/translations';
import { resolveUnpickByScannedCode } from '../../../../api/picking';
import BarcodeScannerComponent from '../../../../components/BarcodeScannerComponent';
import DialogButton from '../../../../components/dialogs/DialogButton';
import Dialog from '../../../../components/dialogs/Dialog';

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
      const isPacked = response?.packedQty != null && Number(response.packedQty) > 0;
      if (!response?.productId || !isPacked) {
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
