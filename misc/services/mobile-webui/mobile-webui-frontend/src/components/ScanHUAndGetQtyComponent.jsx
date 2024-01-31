import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../utils/translations';

import BarcodeScannerComponent from './BarcodeScannerComponent';
import GetQuantityDialog from './dialogs/GetQuantityDialog';
import Button from './buttons/Button';
import { formatQtyToHumanReadableStr, formatQtyToHumanReadable } from '../utils/qtys';
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
  lineQtyToIssue,
  lineQtyIssued,
  qtyHUCapacity,
  qtyAlreadyOnScale,
  uom,
  qtyRejectedReasons,
  scaleDevice,
  scaleTolerance,
  catchWeight,
  catchWeightUom,
  //
  invalidBarcodeMessageKey,
  invalidQtyMessageKey,
  //
  onResult,
}) => {
  const [progressStatus, setProgressStatus] = useState(STATUS_READ_BARCODE);
  const [resolvedBarcodeData, setResolvedBarcodeData] = useState({
    userInfo,
    qtyCaption,
    qtyTarget,
    qtyMax,
    lineQtyToIssue,
    lineQtyIssued,
    qtyHUCapacity,
    qtyAlreadyOnScale,
    uom,
    qtyRejectedReasons,
    scaleDevice,
    scaleTolerance,
    catchWeight,
    catchWeightUom,
  });

  useEffect(() => {
    setResolvedBarcodeData({
      userInfo,
      qtyCaption,
      qtyTarget,
      qtyMax,
      lineQtyToIssue,
      lineQtyIssued,
      qtyHUCapacity,
      qtyAlreadyOnScale,
      uom,
      qtyRejectedReasons,
      scaleDevice,
      scaleTolerance,
      catchWeight,
      catchWeightUom,
    });
  }, [
    userInfo,
    qtyCaption,
    qtyTarget,
    qtyMax,
    lineQtyToIssue,
    lineQtyIssued,
    qtyHUCapacity,
    qtyAlreadyOnScale,
    uom,
    qtyRejectedReasons,
    scaleDevice,
    scaleTolerance,
    catchWeight,
    catchWeightUom,
  ]);

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
    const { qtyEffective: diff, uomEffective: diffUom } =
      resolvedBarcodeData.qtyMax &&
      resolvedBarcodeData.qtyMax > 0 &&
      formatQtyToHumanReadable({
        qty: qtyEntered - resolvedBarcodeData.qtyMax,
        uom,
      });

    if (diff > 0) {
      const qtyDiff = formatQtyToHumanReadableStr({ qty: diff, uom: diffUom });

      return trl(invalidQtyMessageKey || DEFAULT_MSG_qtyAboveMax, { qtyDiff: qtyDiff });
    }

    // OK
    return null;
  };

  const onQtyEntered = ({
    qtyEnteredAndValidated,
    qtyRejected,
    qtyRejectedReason,
    catchWeight,
    catchWeightUom,
    gotoPickingLineScreen = true,
  }) => {
    onResult({
      qty: qtyEnteredAndValidated,
      qtyRejected,
      reason: qtyRejectedReason,
      scannedBarcode: resolvedBarcodeData.scannedBarcode,
      resolvedBarcodeData,
      catchWeight,
      catchWeightUom,
      isTUToBePickedAsWhole: resolvedBarcodeData.isTUToBePickedAsWhole,
      gotoPickingLineScreen,
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
              caption={`DEBUG: QR`}
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
          totalQty={resolvedBarcodeData.lineQtyToIssue}
          qtyAlreadyOnScale={resolvedBarcodeData.qtyAlreadyOnScale}
          uom={resolvedBarcodeData.uom}
          qtyRejectedReasons={resolvedBarcodeData.qtyRejectedReasons}
          scaleDevice={resolvedBarcodeData.scaleDevice}
          scaleTolerance={resolvedBarcodeData.scaleTolerance}
          catchWeight={resolvedBarcodeData.catchWeight}
          catchWeightUom={resolvedBarcodeData.catchWeightUom}
          readOnly={!!resolvedBarcodeData.isTUToBePickedAsWhole}
          hideQtyInput={!!resolvedBarcodeData.isTUToBePickedAsWhole}
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
  lineQtyToIssue: PropTypes.number,
  lineQtyIssued: PropTypes.number,
  qtyHUCapacity: PropTypes.number,
  qtyAlreadyOnScale: PropTypes.number,
  uom: PropTypes.string,
  qtyRejectedReasons: PropTypes.array,
  scaleDevice: PropTypes.object,
  scaleTolerance: PropTypes.object,
  catchWeight: PropTypes.number,
  catchWeightUom: PropTypes.string,
  //
  // Error messages:
  invalidBarcodeMessageKey: PropTypes.string,
  invalidQtyMessageKey: PropTypes.string,
  //
  // Functions
  onResult: PropTypes.func,
};

export default ScanHUAndGetQtyComponent;
