import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../utils/translations';
import GetQuantityDialog from './dialogs/GetQuantityDialog';
import Button from './buttons/Button';
import { formatQtyToHumanReadable, formatQtyToHumanReadableStr } from '../utils/qtys';
import { useBooleanSetting } from '../reducers/settings';
import { toastError, toastErrorFromObj } from '../utils/toast';
import { toQRCodeString } from '../utils/qrCode/hu';
import HUScanner from './huSelector/HUScanner';
import BarcodeScannerComponent from './BarcodeScannerComponent';
import { PICK_ON_THE_FLY_QRCODE } from '../containers/activities/picking/PickConfig';
import { ATTR_isTUToBePickedAsWhole, ATTR_isUnique } from '../utils/qrCode/common';

const STATUS_NOT_INITIALIZED = 'NOT_INITIALIZED';
const STATUS_READ_HU_BARCODE = 'READ_HU_BARCODE';
const STATUS_READ_PRODUCT_BARCODE = 'READ_PRODUCT_BARCODE';
const STATUS_READ_QTY = 'READ_QTY';

const DEFAULT_MSG_qtyAboveMax = 'activities.picking.qtyAboveMax';
const DEFAULT_MSG_notPositiveQtyNotAllowed = 'activities.picking.notPositiveQtyNotAllowed';
const DEFAULT_MSG_notEligibleHUBarcode = 'activities.picking.notEligibleHUBarcode';

const ScanHUAndGetQtyComponent = ({
  scannedBarcode: scannedBarcodeParam,
  eligibleBarcode,
  resolveScannedBarcode: resolveScannedBarcodeFunc,
  resolveProductScannedCode: resolveProductScannedCodeFunc,
  useHUScanner,
  scanHUPlaceholderText,
  scanProductPlaceholderText,
  //
  userInfo,
  qtyCaption,
  packingItemName,
  qtyTargetCaption,
  qtyTarget: qtyTargetParam,
  qtyMax: qtyMaxParam,
  lineQtyToIssue,
  lineQtyIssued,
  qtyHUCapacity,
  qtyAlreadyOnScale,
  uom: uomParam,
  qtyRejectedReasons,
  scaleDevice,
  scaleTolerance,
  catchWeight,
  catchWeightUom,
  customQRCodeFormats,
  readAttributes = [],
  isShowCloseTargetButton = false,
  //
  invalidBarcodeMessageKey,
  invalidQtyMessageKey,
  //
  getConfirmationPromptForQty,
  onResult,
  onClose: onCloseCallback,
}) => {
  const [progressStatus, setProgressStatus] = useState(STATUS_NOT_INITIALIZED);
  const { resolvedBarcodeData, setResolvedBarcodeData, updateResolvedBarcodeData, computeNewResolvedBarcodeData } =
    useResolvedBarcodeData({
      userInfo,
      qtyCaption,
      packingItemName,
      qtyTarget: qtyTargetParam,
      qtyTargetCaption,
      qtyMax: qtyMaxParam,
      lineQtyToIssue,
      lineQtyIssued,
      qtyHUCapacity,
      qtyAlreadyOnScale,
      uom: uomParam,
      qtyRejectedReasons,
      scaleDevice,
      scaleTolerance,
      catchWeight,
      catchWeightUom,
    });

  //
  // Simulate barcode scanning when we get "qrCode" url param
  // IMPORTANT: this shall be called after the "Init / reset resolvedBarcodeData" effect
  useEffect(() => {
    if (scannedBarcodeParam) {
      handleResolveHUScannedBarcode({ scannedBarcode: scannedBarcodeParam })
        .then(onHUBarcodeResolvedResult)
        .catch((error) => {
          toastErrorFromObj(error);
          setProgressStatus(STATUS_READ_HU_BARCODE);
        });
    } else {
      setProgressStatus(STATUS_READ_HU_BARCODE);
    }
  }, [scannedBarcodeParam]);

  const handleResolveHUScannedBarcode = async ({ scannedBarcode, huId }) => {
    // If an eligible barcode was provided, make sure the scanned barcode is matching it
    if (eligibleBarcode && scannedBarcode !== eligibleBarcode) {
      console.warn(
        `Scanned barcode (${scannedBarcode}) does not match the provided eligible barcode (${eligibleBarcode})`
      );
      return {
        error: trl(invalidBarcodeMessageKey ?? DEFAULT_MSG_notEligibleHUBarcode),
      };
    }

    let resolveScannedBarcodeFuncResult = {};
    if (resolveScannedBarcodeFunc && scannedBarcode !== PICK_ON_THE_FLY_QRCODE) {
      resolveScannedBarcodeFuncResult = await resolveScannedBarcodeFunc(scannedBarcode, huId);
    }

    return computeNewResolvedBarcodeData({ ...resolveScannedBarcodeFuncResult, scannedBarcode });
  };

  const onHUScannerResult = async (handlingUnitInfo) => {
    try {
      const resolvedBarcodeDataNew = await handleResolveHUScannedBarcode({
        scannedBarcode: toQRCodeString(handlingUnitInfo.qrCode),
        huId: handlingUnitInfo.id,
      });
      await onHUBarcodeResolvedResult(resolvedBarcodeDataNew);
    } catch (e) {
      toastError({ plainMessage: e });
    }
  };

  const onHUBarcodeResolvedResult = async (resolvedBarcodeDataNew) => {
    setResolvedBarcodeData(resolvedBarcodeDataNew);

    if (resolvedBarcodeDataNew.isScanProductCodeRequired) {
      setProgressStatus(STATUS_READ_PRODUCT_BARCODE);
      return;
    }

    await requestQtyOrReportResult({ resolvedBarcodeData: resolvedBarcodeDataNew });
  };

  const onProductScannedCode = async ({ scannedBarcode: productScannedCode }) => {
    let resolveProductScannedCodeFuncResult = {};
    if (resolveProductScannedCodeFunc) {
      resolveProductScannedCodeFuncResult = await resolveProductScannedCodeFunc?.({
        huScannedCode: resolvedBarcodeData.scannedBarcode,
        productScannedCode,
      });
    }

    const resolvedBarcodeDataNew = updateResolvedBarcodeData({
      ...resolveProductScannedCodeFuncResult,
      productScannedCode,
    });

    // console.log('onProductScannedCode', { resolvedBarcodeDataNew, resolveProductScannedCodeFuncResult });
    await requestQtyOrReportResult({ resolvedBarcodeData: resolvedBarcodeDataNew });
  };

  const requestQtyOrReportResult = async ({ resolvedBarcodeData }) => {
    if (isAskForQty({ resolvedBarcodeData })) {
      setProgressStatus(STATUS_READ_QTY);
    } else {
      await onResult({
        qty: 0,
        reason: null,
        scannedBarcode: resolvedBarcodeData.scannedBarcode,
        resolvedBarcodeData: resolvedBarcodeData,
      })?.catch?.((error) => toastErrorFromObj(error));
    }
  };

  const validateQtyEntered = (qtyEntered, uom) => {
    // Qty shall be positive
    if (qtyEntered <= 0) {
      return trl(DEFAULT_MSG_notPositiveQtyNotAllowed);
    }

    // Qty shall be less than or equal to qtyMax
    // NOTE: skip qtyMax validation when over-pick confirmation prompt is enabled,
    // because the prompt handles the over-delivery scenario instead (gh#29069)
    if (!getConfirmationPromptForQty && resolvedBarcodeData.qtyMax && resolvedBarcodeData.qtyMax > 0) {
      const { qtyEffective: diff, uomEffective: diffUom } = formatQtyToHumanReadable({
        qty: qtyEntered - resolvedBarcodeData.qtyMax,
        uom,
      });

      if (diff > 0) {
        const qtyDiff = formatQtyToHumanReadableStr({ qty: diff, uom: diffUom });
        return trl(invalidQtyMessageKey || DEFAULT_MSG_qtyAboveMax, { qtyDiff });
      }
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
    bestBeforeDate,
    productionDate,
    lotNo,
    productNo,
    barcodeType,
    isCloseTarget = false,
    isDone = true,
  }) => {
    return onResult({
      qty: qtyEnteredAndValidated,
      qtyRejected,
      reason: qtyRejectedReason,
      scannedBarcode: resolvedBarcodeData.scannedBarcode,
      resolvedBarcodeData,
      barcodeType,
      catchWeight,
      catchWeightUom,
      isTUToBePickedAsWhole: resolvedBarcodeData.isTUToBePickedAsWhole,
      bestBeforeDate,
      productionDate,
      lotNo,
      productNo,
      isCloseTarget,
      isDone,
    });
  };

  const onCloseDialog = () => {
    setProgressStatus(STATUS_READ_HU_BARCODE);
    onCloseCallback?.();
  };

  const showEligibleBarcodeDebugButton = useBooleanSetting('barcodeScanner.showEligibleBarcodeDebugButton');

  switch (progressStatus) {
    case STATUS_READ_HU_BARCODE: {
      return (
        <>
          {useHUScanner ? (
            <HUScanner onResolvedBarcode={onHUScannerResult} eligibleBarcode={eligibleBarcode} />
          ) : (
            <BarcodeScannerComponent
              key="scanHUBarcode"
              testId="scanHUBarcode-input"
              inputPlaceholderText={scanHUPlaceholderText}
              resolveScannedBarcode={handleResolveHUScannedBarcode}
              onResolvedResult={onHUBarcodeResolvedResult}
            />
          )}
          {showEligibleBarcodeDebugButton && eligibleBarcode && (
            <Button
              caption={`DEBUG: QR`}
              onClick={() =>
                onHUBarcodeResolvedResult(handleResolveHUScannedBarcode({ scannedBarcode: eligibleBarcode }))
              }
            />
          )}
        </>
      );
    }
    case STATUS_READ_PRODUCT_BARCODE: {
      return (
        <BarcodeScannerComponent
          key="scanProductCode"
          testId="scanProductCode-input"
          inputPlaceholderText={scanProductPlaceholderText}
          onResolvedResult={onProductScannedCode}
        />
      );
    }
    case STATUS_READ_QTY: {
      return (
        <GetQuantityDialog
          userInfo={resolvedBarcodeData.userInfo}
          qtyTargetCaption={resolvedBarcodeData.qtyTargetCaption}
          qtyTarget={resolvedBarcodeData.qtyTarget}
          qtyCaption={resolvedBarcodeData.qtyCaption}
          qtyInitial={resolvedBarcodeData.qtyInitial}
          packingItemName={resolvedBarcodeData.packingItemName}
          totalQty={resolvedBarcodeData.lineQtyToIssue}
          qtyAlreadyOnScale={resolvedBarcodeData.qtyAlreadyOnScale}
          uom={resolvedBarcodeData.uom}
          qtyRejectedReasons={resolvedBarcodeData.qtyRejectedReasons}
          scaleDevice={resolvedBarcodeData.scaleDevice}
          scaleTolerance={resolvedBarcodeData.scaleTolerance}
          catchWeight={resolvedBarcodeData.catchWeight}
          catchWeightUom={resolvedBarcodeData.catchWeightUom}
          customQRCodeFormats={customQRCodeFormats}
          readOnly={!!resolvedBarcodeData.isTUToBePickedAsWhole}
          hideQtyInput={!!resolvedBarcodeData.isTUToBePickedAsWhole}
          readAttributes={readAttributes}
          bestBeforeDate={resolvedBarcodeData.bestBeforeDate}
          lotNo={resolvedBarcodeData.lotNo}
          isShowCloseTargetButton={isShowCloseTargetButton}
          //
          getConfirmationPromptForQty={getConfirmationPromptForQty}
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
  resolveProductScannedCode: PropTypes.func,
  useHUScanner: PropTypes.bool,
  scanHUPlaceholderText: PropTypes.string,
  scanProductPlaceholderText: PropTypes.string,
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
  customQRCodeFormats: PropTypes.array,
  readAttributes: PropTypes.array,
  isShowCloseTargetButton: PropTypes.bool,
  //
  // Error messages:
  invalidBarcodeMessageKey: PropTypes.string,
  invalidQtyMessageKey: PropTypes.string,
  //
  // Functions
  getConfirmationPromptForQty: PropTypes.func,
  onResult: PropTypes.func,
  onClose: PropTypes.func,
};

export default ScanHUAndGetQtyComponent;

//
//
// -----------------------------------------------------------------------------
//
//

const isAskForQty = ({ resolvedBarcodeData }) => {
  const qrCode = resolvedBarcodeData?.qrCode;
  if (qrCode && qrCode[ATTR_isUnique] === false) {
    // user just scanned a non-unique QR code (e.g. EAN13, custom)
    return qrCode[ATTR_isTUToBePickedAsWhole] === false;
  } else {
    return resolvedBarcodeData.qtyTarget != null || resolvedBarcodeData.qtyMax != null;
  }
};

//
//
// -----------------------------------------------------------------------------
//
//

const useResolvedBarcodeData = ({
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
}) => {
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
  // Init/reset resolvedBarcodeData on parameters changed (usually the first time or when we get here from history.replace)
  useEffect(() => {
    setResolvedBarcodeData((prevState) => ({
      ...prevState,
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

  const computeNewResolvedBarcodeData = (dataToUpdate) => {
    // noinspection UnnecessaryLocalVariableJS
    let resolvedBarcodeDataNew = {
      ...resolvedBarcodeData,
      ...dataToUpdate,
    };

    //
    // Make sure qtyTarget is not exceeding the number of available TUs on scanned LU
    if (
      resolvedBarcodeDataNew?.qtyMax != null &&
      resolvedBarcodeDataNew?.scannedHU?.huUnitType === 'LU' &&
      resolvedBarcodeDataNew?.scannedHU?.qtyTUs != null
    ) {
      resolvedBarcodeDataNew.qtyInitial = Math.min(
        resolvedBarcodeDataNew.qtyMax,
        resolvedBarcodeDataNew.scannedHU.qtyTUs
      );
    }

    // console.log('computeNewResolvedBarcodeData', {
    //   resolvedBarcodeDataNew,
    //   resolvedBarcodeData,
    //   dataToUpdate,
    // });

    return resolvedBarcodeDataNew;
  };

  const updateResolvedBarcodeData = (dataToUpdate) => {
    const resolvedBarcodeDataNew = computeNewResolvedBarcodeData(dataToUpdate);
    setResolvedBarcodeData(resolvedBarcodeDataNew);
    return resolvedBarcodeDataNew;
  };

  return {
    resolvedBarcodeData,
    computeNewResolvedBarcodeData,
    updateResolvedBarcodeData,
    setResolvedBarcodeData,
  };
};

//
//
// -----------------------------------------------------------------------------
//
//
