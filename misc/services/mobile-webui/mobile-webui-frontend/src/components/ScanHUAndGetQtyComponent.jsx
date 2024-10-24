import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../utils/translations';
import GetQuantityDialog from './dialogs/GetQuantityDialog';
import Button from './buttons/Button';
import { formatQtyToHumanReadable, formatQtyToHumanReadableStr, roundToQtyPrecision } from '../utils/qtys';
import { useBooleanSetting } from '../reducers/settings';
import { toastError } from '../utils/toast';
import { toQRCodeString } from '../utils/huQRCodes';
import HUScanner from './huSelector/HUScanner';
import BarcodeScannerComponent from './BarcodeScannerComponent';

const STATUS_READ_BARCODE = 'READ_BARCODE';
const STATUS_READ_QTY = 'READ_QTY';

const DEFAULT_MSG_qtyAboveMax = 'activities.picking.qtyAboveMax';
const DEFAULT_MSG_notPositiveQtyNotAllowed = 'activities.picking.notPositiveQtyNotAllowed';
const DEFAULT_MSG_notEligibleHUBarcode = 'activities.picking.notEligibleHUBarcode';

const ScanHUAndGetQtyComponent = ({
  eligibleBarcode,
  resolveScannedBarcode,
  useHUScanner,
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
  ]);

  const handleResolveScannedBarcode = ({ scannedBarcode, huId }) => {
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
      ...resolveScannedBarcode?.(scannedBarcode, huId),
      scannedBarcode,
    };

    // console.log('handleResolveScannedBarcode', { resolvedBarcodeDataNew, resolvedBarcodeData });

    return resolvedBarcodeDataNew;
  };

  const handleEligibleBarcodeClicked = ({ scannedBarcode }) => {
    return handleResolveScannedBarcode({ scannedBarcode });
  };

  const handleHandlingUnitInfoScanned = (handlingUnitInfo) => {
    try {
      onBarcodeScanned(
        handleResolveScannedBarcode({
          scannedBarcode: toQRCodeString(handlingUnitInfo.qrCode),
          huId: handlingUnitInfo.id,
        })
      );
    } catch (e) {
      toastError({ plainMessage: e });
    }
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

    const adjustedQtyMax = roundQtyMaxToScalePrecision(
      resolvedBarcodeData.qtyMax,
      resolvedBarcodeData.uom,
      resolvedBarcodeData.scaleDevice
    );

    // Qty shall be less than or equal to qtyMax
    const { qtyEffective: diff, uomEffective: diffUom } =
      adjustedQtyMax &&
      adjustedQtyMax > 0 &&
      formatQtyToHumanReadable({
        qty: qtyEntered - adjustedQtyMax,
        uom,
      });

    if (diff > 0) {
      const qtyDiff = formatQtyToHumanReadableStr({ qty: diff, uom: diffUom });

      return trl(invalidQtyMessageKey || DEFAULT_MSG_qtyAboveMax, { qtyDiff: qtyDiff });
    }

    // OK
    return null;
  };

  const roundQtyMaxToScalePrecision = (qtyMax, qtyMaxUOM, scaleDevice) => {
    if (!scaleDevice || !scaleDevice.roundingToScale) {
      return qtyMax;
    }

    try {
      return roundToQtyPrecision(
        { qty: qtyMax, uom: qtyMaxUOM },
        { qty: scaleDevice.roundingToScale.qty, uom: scaleDevice.roundingToScale.uomSymbol }
      );
    } catch (e) {
      toastError({ plainMessage: e.message });
    }
  };

  const onQtyEntered = ({ qtyEnteredAndValidated, qtyRejected, qtyRejectedReason }) => {
    return onResult({
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
          {useHUScanner ? (
            <HUScanner onResolvedBarcode={handleHandlingUnitInfoScanned} eligibleBarcode={eligibleBarcode} />
          ) : (
            <BarcodeScannerComponent
              resolveScannedBarcode={handleResolveScannedBarcode}
              onResolvedResult={onBarcodeScanned}
            />
          )}
          {showEligibleBarcodeDebugButton && eligibleBarcode && (
            <Button
              caption={`DEBUG: ${eligibleBarcode}`}
              onClick={() => onBarcodeScanned(handleEligibleBarcodeClicked({ scannedBarcode: eligibleBarcode }))}
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
  useHUScanner: PropTypes.bool,
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
  //
  // Error messages:
  invalidBarcodeMessageKey: PropTypes.string,
  invalidQtyMessageKey: PropTypes.string,
  //
  // Functions
  onResult: PropTypes.func,
};

export default ScanHUAndGetQtyComponent;
