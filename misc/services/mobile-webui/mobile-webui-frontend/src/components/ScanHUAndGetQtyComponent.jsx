import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../utils/translations';
import GetQuantityDialog from './dialogs/GetQuantityDialog';
import Button from './buttons/Button';
import { formatQtyToHumanReadable, formatQtyToHumanReadableStr, roundToQtyPrecision } from '../utils/qtys';
import { useBooleanSetting } from '../reducers/settings';
import { toastError } from '../utils/toast';
import { toQRCodeString } from '../utils/qrCode/hu';
import HUScanner from './huSelector/HUScanner';
import BarcodeScannerComponent from './BarcodeScannerComponent';

const STATUS_NOT_INITIALIZED = 'NOT_INITIALIZED';
const STATUS_READ_BARCODE = 'READ_BARCODE';
const STATUS_READ_QTY = 'READ_QTY';

const DEFAULT_MSG_qtyAboveMax = 'activities.picking.qtyAboveMax';
const DEFAULT_MSG_notPositiveQtyNotAllowed = 'activities.picking.notPositiveQtyNotAllowed';
const DEFAULT_MSG_notEligibleHUBarcode = 'activities.picking.notEligibleHUBarcode';

const ScanHUAndGetQtyComponent = ({
  scannedBarcode: scannedBarcodeParam,
  eligibleBarcode,
  resolveScannedBarcode,
  useHUScanner,
  //
  userInfo,
  qtyCaption,
  packingItemName,
  qtyTargetCaption,
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
  isShowBestBeforeDate = false,
  isShowLotNo = false,
  //
  invalidBarcodeMessageKey,
  invalidQtyMessageKey,
  //
  onResult,
  onClose: onCloseCallback,
}) => {
  const [progressStatus, setProgressStatus] = useState(STATUS_NOT_INITIALIZED);
  const [resolvedBarcodeData, setResolvedBarcodeData] = useState({
    lineId: null,
    userInfo,
    qtyCaption,
    packingItemName,
    qtyTarget,
    qtyTargetCaption,
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

  //
  // Init/reset resolvedBarcodeData on parameters changed (usually first time or when we get here from history.replace)
  useEffect(() => {
    setResolvedBarcodeData((prevState) => ({
      lineId: prevState?.lineId,
      userInfo,
      qtyCaption,
      packingItemName,
      qtyTargetCaption,
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
      // remember the scanned barcode as no new scan has been performed
      scannedBarcode: prevState?.scannedBarcode,
    }));
  }, [
    userInfo,
    qtyCaption,
    packingItemName,
    qtyTargetCaption,
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

  //
  // Simulate barcode scanning when we get "qrCode" url param
  // IMPORTANT: this shall be called after "Init/reset resolvedBarcodeData" effect
  useEffect(() => {
    if (scannedBarcodeParam) {
      onBarcodeScanned(handleResolveScannedBarcode({ scannedBarcode: scannedBarcodeParam }));
    } else {
      setProgressStatus(STATUS_READ_BARCODE);
    }
  }, [scannedBarcodeParam]);

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

    //console.log('handleResolveScannedBarcode', { resolvedBarcodeDataNew, resolvedBarcodeData });

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

    console.log('onBarcodeScanned', { resolvedBarcodeDataNew, resolvedBarcodeData, askForQty });

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

  const onQtyEntered = ({
    qtyEnteredAndValidated,
    qtyRejected,
    qtyRejectedReason,
    catchWeight,
    catchWeightUom,
    bestBeforeDate,
    lotNo,
    productNo,
    isDone = true,
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
      bestBeforeDate,
      lotNo,
      productNo,
      isDone,
    });
  };

  const onCloseDialog = () => {
    setProgressStatus(STATUS_READ_BARCODE);
    onCloseCallback?.();
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
              caption={`DEBUG: QR`}
              onClick={() => onBarcodeScanned(handleEligibleBarcodeClicked({ scannedBarcode: eligibleBarcode }))}
            />
          )}
        </>
      );
    }
    case STATUS_READ_QTY: {
      console.log('rendering qty dialog', { resolvedBarcodeData });
      return (
        <GetQuantityDialog
          userInfo={resolvedBarcodeData.userInfo}
          qtyTargetCaption={resolvedBarcodeData.qtyTargetCaption}
          qtyTarget={resolvedBarcodeData.qtyTarget}
          qtyCaption={resolvedBarcodeData.qtyCaption}
          packingItemName={resolvedBarcodeData.packingItemName}
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
          isShowBestBeforeDate={isShowBestBeforeDate}
          bestBeforeDate={resolvedBarcodeData.bestBeforeDate}
          isShowLotNo={isShowLotNo}
          lotNo={resolvedBarcodeData.lotNo}
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
  scannedBarcode: PropTypes.string,
  eligibleBarcode: PropTypes.string,
  resolveScannedBarcode: PropTypes.func,
  useHUScanner: PropTypes.bool,
  //
  // Props: Qty related
  userInfo: PropTypes.array,
  qtyCaption: PropTypes.string,
  packingItemName: PropTypes.string,
  qtyMax: PropTypes.number,
  qtyTarget: PropTypes.number,
  qtyTargetCaption: PropTypes.string,
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
  isShowBestBeforeDate: PropTypes.bool,
  isShowLotNo: PropTypes.bool,
  //
  // Error messages:
  invalidBarcodeMessageKey: PropTypes.string,
  invalidQtyMessageKey: PropTypes.string,
  //
  // Functions
  onResult: PropTypes.func,
  onClose: PropTypes.func,
};

export default ScanHUAndGetQtyComponent;
