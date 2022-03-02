import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../utils/translations';

import BarcodeScannerComponent from './BarcodeScannerComponent';
import GetQuantityDialog from './dialogs/GetQuantityDialog';
import Button from './buttons/Button';

const STATUS_READ_BARCODE = 'READ_BARCODE';
const STATUS_READ_QTY = 'READ_QTY';

const ScanHUAndGetQtyComponent = ({
  eligibleBarcode,
  uom,
  qtyCaption,
  qtyInitial,
  qtyTarget,
  qtyRejectedReasons,
  scaleDevice,
  invalidBarcodeMessageKey,
  invalidQtyMessageKey,
  onResult,
}) => {
  const [progressStatus, setProgressStatus] = useState(STATUS_READ_BARCODE);
  const [currentScannedBarcode, setCurrentScannedBarcode] = useState(null);

  const resolveScannedBarcode = ({ scannedBarcode }) => {
    // console.log('resolveScannedBarcode', { scannedBarcode, eligibleBarcode });

    // If an eligible barcode was provided, make sure scanned barcode is matching it
    if (eligibleBarcode && scannedBarcode !== eligibleBarcode) {
      return {
        error: trl(invalidBarcodeMessageKey ?? 'activities.picking.notEligibleHUBarcode'),
      };
    } else {
      return { scannedBarcode };
    }
  };

  const onBarcodeScanned = ({ scannedBarcode }) => {
    const askForQty = qtyTarget != null;
    if (askForQty) {
      setCurrentScannedBarcode(scannedBarcode);
      setProgressStatus(STATUS_READ_QTY);
    } else {
      onResult({ qty: 0, reason: null, scannedBarcode });
    }
  };

  const validateQtyEntered = (qtyEntered) => {
    // Qty shall be positive
    if (qtyEntered <= 0) {
      return trl(invalidQtyMessageKey || 'activities.picking.invalidQtyPicked');
    }

    // Qty shall be less than or equal to qtyTarget
    if (qtyTarget > 0 && qtyEntered > qtyTarget) {
      return trl(invalidQtyMessageKey || 'activities.picking.invalidQtyPicked');
    }

    // OK
    return null;
  };

  const onQtyEntered = ({ qtyEnteredAndValidated, qtyRejectedReason }) => {
    onResult({ qty: qtyEnteredAndValidated, reason: qtyRejectedReason, scannedBarcode: currentScannedBarcode });
  };

  switch (progressStatus) {
    case STATUS_READ_BARCODE:
      return (
        <>
          <BarcodeScannerComponent resolveScannedBarcode={resolveScannedBarcode} onResolvedResult={onBarcodeScanned} />
          {window.metasfresh_debug && eligibleBarcode && (
            <Button
              caption={`DEBUG: ${eligibleBarcode}`}
              onClick={() => onBarcodeScanned({ scannedBarcode: eligibleBarcode })}
            />
          )}
        </>
      );
    case STATUS_READ_QTY:
      return (
        <GetQuantityDialog
          qtyInitial={qtyInitial}
          qtyTarget={qtyTarget}
          qtyCaption={qtyCaption}
          uom={uom}
          qtyRejectedReasons={qtyRejectedReasons}
          scaleDevice={scaleDevice}
          //
          validateQtyEntered={validateQtyEntered}
          onQtyChange={onQtyEntered}
          onCloseDialog={() => setProgressStatus(STATUS_READ_BARCODE)}
        />
      );
    default:
      return null;
  }
};

ScanHUAndGetQtyComponent.propTypes = {
  //
  // Props
  eligibleBarcode: PropTypes.string,
  qtyCaption: PropTypes.string,
  qtyTarget: PropTypes.number,
  qtyInitial: PropTypes.number,
  uom: PropTypes.string,
  qtyRejectedReasons: PropTypes.array,
  // Error messages:
  invalidBarcodeMessageKey: PropTypes.string,
  invalidQtyMessageKey: PropTypes.string,
  scaleDevice: PropTypes.object,
  //
  // Functions
  onResult: PropTypes.func,
};

export default ScanHUAndGetQtyComponent;
