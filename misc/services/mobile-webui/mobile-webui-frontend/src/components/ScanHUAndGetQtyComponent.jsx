import React, { useState } from 'react';
import PropTypes from 'prop-types';

import BarcodeScannerComponent from './BarcodeScannerComponent';
import GetQuantityDialog from './dialogs/GetQuantityDialog';
import QtyReasonsView from '../containers/activities/QtyReasonsView';
import Button from './buttons/Button';
import counterpart from 'counterpart';

const STATUS_READ_BARCODE = 'READ_BARCODE';
const STATUS_READ_QTY = 'READ_QTY';
const STATUS_READ_QTY_REJECTED_REASON = 'READ_QTY_REJECTED_REASON';

const ScanHUAndGetQtyComponent = ({
  eligibleBarcode,
  uom,
  qtyCaption,
  qtyInitial,
  qtyTarget,
  qtyRejectedReasons,
  invalidBarcodeMessageKey,
  invalidQtyMessageKey,
  onResult,
}) => {
  const [progressStatus, setProgressStatus] = useState(STATUS_READ_BARCODE);
  const [currentScannedBarcode, setCurrentScannedBarcode] = useState(null);
  const [currentQtys, setCurrentQtys] = useState({ qty: 0, qtyRejected: 0 });

  const validateScannedBarcode = (barcode) => {
    // If an eligible barcode was provided, make sure scanned barcode is matching it
    if (eligibleBarcode && barcode !== eligibleBarcode) {
      return counterpart.translate(invalidBarcodeMessageKey ?? 'activities.picking.notEligibleHUBarcode');
    }

    // OK
    return null;
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
      return counterpart.translate(invalidQtyMessageKey || 'activities.picking.invalidQtyPicked');
    }

    // Qty shall be less than or equal to qtyTarget
    if (qtyTarget > 0 && qtyEntered > qtyTarget) {
      return counterpart.translate(invalidQtyMessageKey || 'activities.picking.invalidQtyPicked');
    }

    // OK
    return null;
  };

  const onQtyEntered = (qtyEnteredAndValidated) => {
    const qtyRejected = Math.max(qtyTarget - qtyEnteredAndValidated, 0);
    setCurrentQtys({ qty: qtyEnteredAndValidated, qtyRejected });

    if (qtyRejected !== 0) {
      setProgressStatus(STATUS_READ_QTY_REJECTED_REASON);
    } else {
      onResult({ qty: qtyEnteredAndValidated, reason: null, scannedBarcode: currentScannedBarcode });
    }
  };

  const onQtyRejectedReasonEntered = (reason) => {
    onResult({ qty: currentQtys.qty, reason, scannedBarcode: currentScannedBarcode });
  };

  switch (progressStatus) {
    case STATUS_READ_BARCODE:
      return (
        <>
          <BarcodeScannerComponent
            validateScannedBarcode={validateScannedBarcode}
            onBarcodeScanned={onBarcodeScanned}
          />
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
          validateQtyEntered={validateQtyEntered}
          onQtyChange={onQtyEntered}
          onCloseDialog={() => setProgressStatus(STATUS_READ_BARCODE)}
        />
      );
    case STATUS_READ_QTY_REJECTED_REASON:
      return (
        <QtyReasonsView
          onHide={onQtyRejectedReasonEntered}
          uom={uom}
          qtyRejected={currentQtys.qtyRejected}
          qtyRejectedReasons={qtyRejectedReasons}
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
  //
  // Functions
  onResult: PropTypes.func,
};

export default ScanHUAndGetQtyComponent;
