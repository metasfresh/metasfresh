import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../utils/translations';
import GetQuantityDialog from './dialogs/GetQuantityDialog';
import YesNoDialog from './dialogs/YesNoDialog';
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
  const [confirmationDialogProps, setConfirmationDialogProps] = useState(undefined);
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

  const fireOnResult = (onResultPayload) => onResult(onResultPayload)?.catch?.((error) => toastErrorFromObj(error));

  const requestQtyOrReportResult = async ({ resolvedBarcodeData }) => {
    if (isAskForQty({ resolvedBarcodeData })) {
      setProgressStatus(STATUS_READ_QTY);
      return;
    }

    const onResultPayload = {
      qty: 0,
      reason: null,
      scannedBarcode: resolvedBarcodeData.scannedBarcode,
      resolvedBarcodeData: resolvedBarcodeData,
    };

    // There is no qty dialog on this path (the whole scanned TU is booked), so the over-delivery
    // handling GetQuantityDialog performs for the CU path has to happen here. The qty to compare is
    // the TU's own content, fetched into qtyInitial for exactly this purpose; the qty: 0 above is the
    // booking instruction, not the comparison input. Which handling applies depends on the profile,
    // exactly as on the CU path: with the prompt configured, the same YesNoDialog; without it, the
    // same qtyAboveMax ceiling - so no configuration leaves this path unbounded.
    if (getConfirmationPromptForQty) {
      const confirmationPrompt = await getConfirmationPromptForQty(resolvedBarcodeData.qtyInitial);
      if (confirmationPrompt) {
        setConfirmationDialogProps({ promptQuestion: confirmationPrompt, onResultPayload });
        return;
      }
    } else if (Number.isFinite(resolvedBarcodeData.qtyInitial)) {
      // qtyInitial is absent only when the HU lookup returned no productQty at all - there is then no
      // number to bound the pick by, and we book as before. Same as the prompt branch above, where an
      // absent qty raises no confirmation either; a lookup that outright fails already fails the scan.
      const qtyAboveMaxError = validateQtyAgainstMax({
        qty: resolvedBarcodeData.qtyInitial,
        qtyMax: resolvedBarcodeData.qtyMax,
        uom: resolvedBarcodeData.uom,
        invalidQtyMessageKey,
      });
      if (qtyAboveMaxError) {
        // Thrown, not toasted: the caller (BarcodeScannerComponent / HUScanner) turns this into the
        // error beep + toast every other rejected scan produces, and leaves the scanner armed.
        throw qtyAboveMaxError;
      }
    }

    await fireOnResult(onResultPayload);
  };

  const validateQtyEntered = (qtyEntered, uom) => {
    // Qty shall be positive
    if (qtyEntered <= 0) {
      return trl(DEFAULT_MSG_notPositiveQtyNotAllowed);
    }

    // Qty shall be less than or equal to qtyMax
    // NOTE: skip qtyMax validation when over-pick confirmation prompt is enabled,
    // because the prompt handles the over-delivery scenario instead
    if (!getConfirmationPromptForQty) {
      return validateQtyAgainstMax({ qty: qtyEntered, qtyMax: resolvedBarcodeData.qtyMax, uom, invalidQtyMessageKey });
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

  // Early return (after every hook), so the BarcodeScannerComponent below is unmounted while the
  // operator answers: two mounted scanners would both capture the next hardware scan.
  // progressStatus is deliberately left untouched, so declining returns to the very step we came from.
  if (confirmationDialogProps) {
    return (
      <YesNoDialog
        promptQuestion={confirmationDialogProps.promptQuestion}
        onYes={() => {
          fireOnResult(confirmationDialogProps.onResultPayload);
          setConfirmationDialogProps(undefined);
        }}
        onNo={() => setConfirmationDialogProps(undefined)}
      />
    );
  }

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

/**
 * The qtyAboveMax ceiling, shared by the two paths that book a qty: the CU path via
 * validateQtyEntered (qty typed into GetQuantityDialog) and the whole-TU path via
 * requestQtyOrReportResult (qty read from the scanned TU's own content).
 *
 * @returns the translated error message, or null when the qty is within qtyMax.
 */
const validateQtyAgainstMax = ({ qty, qtyMax, uom, invalidQtyMessageKey }) => {
  if (!qtyMax || qtyMax <= 0) {
    return null;
  }

  const { qtyEffective: diff, uomEffective: diffUom } = formatQtyToHumanReadable({ qty: qty - qtyMax, uom });
  if (diff <= 0) {
    return null;
  }

  const qtyDiff = formatQtyToHumanReadableStr({ qty: diff, uom: diffUom });
  return trl(invalidQtyMessageKey || DEFAULT_MSG_qtyAboveMax, { qtyDiff });
};

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
