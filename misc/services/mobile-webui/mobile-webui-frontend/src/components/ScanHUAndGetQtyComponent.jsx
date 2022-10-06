import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../utils/translations';

import BarcodeScannerComponent from './BarcodeScannerComponent';
import GetQuantityDialog from './dialogs/GetQuantityDialog';
import Button from './buttons/Button';
import { formatQtyToHumanReadable } from '../utils/qtys';

const STATUS_READ_BARCODE = 'READ_BARCODE';
const STATUS_READ_QTY = 'READ_QTY';

const DEFAULT_MSG_qtyAboveMax = 'activities.picking.qtyAboveMax';
const DEFAULT_MSG_notPositiveQtyNotAllowed = 'activities.picking.notPositiveQtyNotAllowed';
const DEFAULT_MSG_notEligibleHUBarcode = 'activities.picking.notEligibleHUBarcode';

const ScanHUAndGetQtyComponent = ({
  userInfo,
  eligibleBarcode,
  uom,
  qtyCaption,
  qtyTarget,
  qtyMax,
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
        error: trl(invalidBarcodeMessageKey ?? DEFAULT_MSG_notEligibleHUBarcode),
      };
    } else {
      return { scannedBarcode };
    }
  };

  const onBarcodeScanned = ({ scannedBarcode }) => {
    const askForQty = qtyTarget != null || qtyMax != null;
    if (askForQty) {
      setCurrentScannedBarcode(scannedBarcode);
      setProgressStatus(STATUS_READ_QTY);
    } else {
      onResult({ qty: 0, reason: null, scannedBarcode });
    }
  };

  const validateQtyEntered = (qtyEntered, uom) => {
    // Qty shall be positive
    if (qtyEntered <= 0) {
      return trl(DEFAULT_MSG_notPositiveQtyNotAllowed);
    }

    // Qty shall be less than or equal to qtyMax
    if (qtyMax && qtyMax > 0 && qtyEntered > qtyMax) {
      return trl(invalidQtyMessageKey || DEFAULT_MSG_qtyAboveMax, {
        qtyDiff: formatQtyToHumanReadable({ qty: qtyEntered - qtyMax, uom }),
      });
    }

    // OK
    return null;
  };

  const onQtyEntered = ({ qtyEnteredAndValidated, qtyRejected, qtyRejectedReason }) => {
    onResult({
      qty: qtyEnteredAndValidated,
      qtyRejected,
      reason: qtyRejectedReason,
      scannedBarcode: currentScannedBarcode,
    });
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
          userInfo={userInfo}
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
  userInfo: PropTypes.array,
  qtyCaption: PropTypes.string,
  qtyMax: PropTypes.number,
  qtyTarget: PropTypes.number,
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
