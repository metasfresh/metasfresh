import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../utils/translations';

import BarcodeScannerComponent from './BarcodeScannerComponent';
import GetQuantityDialog from './dialogs/GetQuantityDialog';
import Button from './buttons/Button';
import { formatQtyToHumanReadable } from '../utils/qtys';
import { useBooleanSetting } from '../reducers/settings';

const STATUS_READ_BARCODE = 'READ_BARCODE';
const STATUS_READ_QTY = 'READ_QTY';

const DEFAULT_MSG_qtyAboveMax = 'activities.picking.qtyAboveMax';
const DEFAULT_MSG_notPositiveQtyNotAllowed = 'activities.picking.notPositiveQtyNotAllowed';
const DEFAULT_MSG_notEligibleHUBarcode = 'activities.picking.notEligibleHUBarcode';

const ScanHUAndGetQtyComponent = ({
  eligibleBarcode,
  resolveScannedBarcode,
  //
  userInfo,
  qtyCaption,
  qtyTarget,
  qtyMax,
  uom,
  qtyRejectedReasons,
  scaleDevice,
  //
  invalidBarcodeMessageKey,
  invalidQtyMessageKey,
  //
  onResult,
}) => {
  const [progressStatus, setProgressStatus] = useState(STATUS_READ_BARCODE);
  const [resolvedBarcodeData, setResolvedBarcodeData] = useState({
    scannedBarcode: null,
    userInfo: [],
    qtyCaption: null,
    qtyTarget: null,
    qtyMax: null,
    uom: null,
    qtyRejectedReasons: null,
    scaleDevice: null,
  });

  useEffect(() => {
    setResolvedBarcodeData({ userInfo, qtyCaption, qtyTarget, qtyMax, uom, qtyRejectedReasons, scaleDevice });
  }, [userInfo, qtyCaption, qtyTarget, qtyMax, uom, qtyRejectedReasons, scaleDevice]);

  const handleResolveScannedBarcode = ({ scannedBarcode }) => {
    // console.log('handleResolveScannedBarcode', { scannedBarcode, eligibleBarcode });

    // If an eligible barcode was provided, make sure scanned barcode is matching it
    if (eligibleBarcode && scannedBarcode !== eligibleBarcode) {
      return {
        error: trl(invalidBarcodeMessageKey ?? DEFAULT_MSG_notEligibleHUBarcode),
      };
    }

    // noinspection UnnecessaryLocalVariableJS
    const resolvedBarcodeDataNew = {
      ...resolvedBarcodeData,
      ...resolveScannedBarcode?.(scannedBarcode),
      scannedBarcode,
    };

    // console.log('handleResolveScannedBarcode', { resolvedBarcodeDataNew, resolvedBarcodeData });

    return resolvedBarcodeDataNew;
  };

  const onBarcodeScanned = (resolvedBarcodeDataNew) => {
    setResolvedBarcodeData(resolvedBarcodeDataNew);
    const askForQty = resolvedBarcodeDataNew.qtyTarget != null || resolvedBarcodeDataNew.qtyMax != null;

    // console.log('onBarcodeScanned', {
    //   resolvedBarcodeDataNew,
    //   resolvedBarcodeData,
    //   askForQty,
    // });

    if (askForQty) {
      setProgressStatus(STATUS_READ_QTY);
    } else {
      onResult({ qty: 0, reason: null, scannedBarcode: resolvedBarcodeDataNew.scannedBarcode, resolvedBarcodeData });
    }
  };

  const validateQtyEntered = (qtyEntered, uom) => {
    // Qty shall be positive
    if (qtyEntered <= 0) {
      return trl(DEFAULT_MSG_notPositiveQtyNotAllowed);
    }

    // Qty shall be less than or equal to qtyMax
    if (resolvedBarcodeData.qtyMax && resolvedBarcodeData.qtyMax > 0 && qtyEntered > resolvedBarcodeData.qtyMax) {
      return trl(invalidQtyMessageKey || DEFAULT_MSG_qtyAboveMax, {
        qtyDiff: formatQtyToHumanReadable({ qty: qtyEntered - resolvedBarcodeData.qtyMax, uom }),
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
      scannedBarcode: resolvedBarcodeData.scannedBarcode,
      resolvedBarcodeData,
    });
  };

  const onCloseDialog = () => {
    setProgressStatus(STATUS_READ_BARCODE);
  };

  const showEligibleBarcodeDebugButton = useBooleanSetting('barcodeScanner.showEligibleBarcodeDebugButton');

  switch (progressStatus) {
    case STATUS_READ_BARCODE: {
      return (
        <>
          <BarcodeScannerComponent
            resolveScannedBarcode={handleResolveScannedBarcode}
            onResolvedResult={onBarcodeScanned}
          />
          {showEligibleBarcodeDebugButton && eligibleBarcode && (
            <Button
              caption={`DEBUG: ${eligibleBarcode}`}
              onClick={() => onBarcodeScanned(handleResolveScannedBarcode({ scannedBarcode: eligibleBarcode }))}
            />
          )}
        </>
      );
    }
    case STATUS_READ_QTY: {
      return (
        <GetQuantityDialog
          userInfo={resolvedBarcodeData.userInfo}
          qtyTarget={resolvedBarcodeData.qtyTarget}
          qtyCaption={resolvedBarcodeData.qtyCaption}
          uom={resolvedBarcodeData.uom}
          qtyRejectedReasons={resolvedBarcodeData.qtyRejectedReasons}
          scaleDevice={resolvedBarcodeData.scaleDevice}
          //
          validateQtyEntered={validateQtyEntered}
          onQtyChange={onQtyEntered}
          onCloseDialog={onCloseDialog}
        />
      );
    }
    default: {
      return null;
    }
  }
};

ScanHUAndGetQtyComponent.propTypes = {
  //
  // Props: Barcode scanning related
  eligibleBarcode: PropTypes.string,
  resolveScannedBarcode: PropTypes.func,
  //
  // Props: Qty related
  userInfo: PropTypes.array,
  qtyCaption: PropTypes.string,
  qtyMax: PropTypes.number,
  qtyTarget: PropTypes.number,
  uom: PropTypes.string,
  qtyRejectedReasons: PropTypes.array,
  scaleDevice: PropTypes.object,
  //
  // Error messages:
  invalidBarcodeMessageKey: PropTypes.string,
  invalidQtyMessageKey: PropTypes.string,
  //
  // Functions
  onResult: PropTypes.func,
};

export default ScanHUAndGetQtyComponent;
