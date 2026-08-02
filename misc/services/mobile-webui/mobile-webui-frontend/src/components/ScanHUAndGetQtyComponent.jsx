import React, { useCallback, useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../utils/translations';
import GetQuantityDialog from './dialogs/GetQuantityDialog';
import YesNoDialog from './dialogs/YesNoDialog';
import Button from './buttons/Button';
import ButtonWithIndicator from './buttons/ButtonWithIndicator';
import GraiCapturePanel from './GraiCapturePanel';
import { getAssignedGrais, getExtraGrais, mergeGraiArrays } from '../utils/grai';
import { formatQtyToHumanReadable, formatQtyToHumanReadableStr } from '../utils/qtys';
import { useBooleanSetting } from '../reducers/settings';
import { toastError, toastErrorFromObj, toastNotification } from '../utils/toast';
import { toQRCodeString } from '../utils/qrCode/hu';
import HUScanner from './huSelector/HUScanner';
import BarcodeScannerComponent from './BarcodeScannerComponent';
import Spinner from './Spinner';
import { doFinally } from '../utils';
import { PICK_ON_THE_FLY_QRCODE } from '../containers/activities/picking/PickConfig';
import { ATTR_isTUToBePickedAsWhole, ATTR_isUnique } from '../utils/qrCode/common';

const STATUS_NOT_INITIALIZED = 'NOT_INITIALIZED';
const STATUS_READ_HU_BARCODE = 'READ_HU_BARCODE';
const STATUS_READ_PRODUCT_BARCODE = 'READ_PRODUCT_BARCODE';
const STATUS_READ_QTY = 'READ_QTY';
const STATUS_READ_GRAI = 'READ_GRAI';

const DEFAULT_MSG_qtyAboveMax = 'activities.picking.qtyAboveMax';
const DEFAULT_MSG_notPositiveQtyNotAllowed = 'activities.picking.notPositiveQtyNotAllowed';
const DEFAULT_MSG_notEligibleHUBarcode = 'activities.picking.notEligibleHUBarcode';

// Stable empty-array default (never a fresh [] literal per render) so handleAddGrais's useCallback
// dep on `existingLuGrais` does not change identity when the prop is simply absent/not-yet-loaded.
const EMPTY_LU_GRAIS = [];

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
  graiScanEnabled = false,
  existingLuGrais = EMPTY_LU_GRAIS,
  //
  getConfirmationPromptForQty,
  onResult,
  onClose: onCloseCallback,
}) => {
  const [progressStatus, setProgressStatus] = useState(STATUS_NOT_INITIALIZED);
  const [confirmationDialogProps, setConfirmationDialogProps] = useState(undefined);
  const [isProcessing, setProcessing] = useState(false);
  // GRAI Flow-Through: when graiScanEnabled, the confirmed qty result is stashed here and the GRAI
  // capture is shown inline (non-skippable) before the pick is reported, so qty + GRAIs go out in one
  // atomic onResult call.
  const [pendingGraiResult, setPendingGraiResult] = useState(null);
  // `codes` + `skippedCodes` are ONE state object (not two separate useState calls) so both derive
  // from a SINGLE queued functional update per scan event, and — crucially — `skippedCodes` is the
  // actual list of already-reported LU-skips, not just a count. A skipped code is NOT added to
  // `codes` (it must never occupy a crate slot), so without its own memory a second delivery of the
  // same physical scan would re-skip and re-count it. BarcodeScannerComponent and
  // useKeyboardBarcodeReader can both deliver the same scan in one event tick (see the handler-identity
  // comment below); feeding `prev.skippedCodes` back into mergeGraiArrays makes a redelivered skip a
  // silent no-op, so the count (and the toast) fire at most once per physical crate.
  const [graiCapture, setGraiCapture] = useState({ codes: [], skippedCodes: [] });
  const { codes: graiCodes, skippedCodes } = graiCapture;
  const skippedCount = skippedCodes.length;
  // Stable handler identities (matching the HU-Manager useGrais hook): an inline arrow here would be
  // a new function every render, so GraiCapturePanel's onResolvedResult useCallback would change each
  // render and BarcodeScannerComponent would re-subscribe its keyboard listener mid-scan — dropping
  // codes during a rapid RFID burst (multiple GRAIs scanned back-to-back). handleAddGrais additionally
  // depends on `existingLuGrais`, which is stable for the whole capture session (fetched once per
  // pick-step entry — see useAvailablePickingTargets), so identity stability still holds.
  const handleAddGrais = useCallback(
    (newGrais) =>
      setGraiCapture((prev) => {
        const { merged, skipped } = mergeGraiArrays(prev.codes, newGrais, existingLuGrais, prev.skippedCodes);
        const didCodesChange = merged !== prev.codes;
        if (!didCodesChange && skipped.length === 0) {
          return prev;
        }
        return {
          codes: merged,
          skippedCodes: skipped.length ? [...prev.skippedCodes, ...skipped] : prev.skippedCodes,
        };
      }),
    [existingLuGrais]
  );
  const handleRemoveGrai = useCallback(
    (grai) => setGraiCapture((prev) => ({ ...prev, codes: prev.codes.filter((g) => g !== grai) })),
    []
  );
  const handleClearAllGrais = useCallback(() => setGraiCapture((prev) => ({ ...prev, codes: [] })), []);

  // non-blocking "N skipped" notice — fires once per genuinely-new skip (the delta in
  // skippedCodes.length since the last commit). Because a redelivered skip is already folded out by
  // mergeGraiArrays (via prev.skippedCodes above), the delta is exactly the number of distinct new
  // already-on-LU crates, so a dual-reader duplicate never produces a second (or inflated) toast.
  const prevSkippedCountRef = useRef(0);
  useEffect(() => {
    const delta = skippedCount - prevSkippedCountRef.current;
    prevSkippedCountRef.current = skippedCount;
    if (delta > 0) {
      toastNotification({ plainMessage: trl('activities.picking.graiScan.skippedNotice', { count: delta }) });
    }
  }, [skippedCount]);
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

  // Mirrors GetQuantityDialog.fireOnQtyChange: isProcessing is what suppresses the scan target while
  // the pick's POST is in flight, and it has to be cleared on the error branch too.
  const fireOnResult = (onResultPayload) => {
    setProcessing(true);
    try {
      const promise = onResult(onResultPayload)?.catch?.((error) => toastErrorFromObj(error));
      return doFinally(promise, () => setProcessing(false));
    } catch (error) {
      setProcessing(false);
      throw error;
    }
  };

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

    // The whole-TU mirror of the over-delivery handling GetQuantityDialog performs for the CU path.
    // The comparison input is the TU's own content (qtyInitial), not the qty: 0 booking instruction above.
    if (getConfirmationPromptForQty) {
      const confirmationPrompt = await getConfirmationPromptForQty(resolvedBarcodeData.qtyInitial);
      if (confirmationPrompt) {
        setConfirmationDialogProps({ promptQuestion: confirmationPrompt, onResultPayload });
        return;
      }
    } else if (Number.isFinite(resolvedBarcodeData.qtyInitial)) {
      // Gated on Number.isFinite: with no resolved qtyInitial there is nothing to compare, so the pick books
      // unchecked - as on the prompt branch above, where an absent qty raises no confirmation either.
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
    serialNos,
    productNo,
    barcodeType,
    isCloseTarget = false,
    isDone = true,
  }) => {
    const result = {
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
      serialNos,
      productNo,
      isCloseTarget,
      isDone,
    };

    // GRAI Flow-Through: when GRAI scanning is required, do not report the pick yet — auto-invoke the
    // inline GRAI capture (one GRAI per picked crate) and report qty + GRAIs together on save. This
    // applies to BOTH the plain "OK" and the "OK und LU schließen" (isCloseTarget) completion paths:
    // the close-LU result carries isCloseTarget through pendingGraiResult, so on save the backend
    // stamps the GRAIs and then closes the LU within the same atomic pick. Closing the LU must never
    // be a way to skip the GRAI scan for a GRAI-required partner.
    if (graiScanEnabled && qtyEnteredAndValidated > 0) {
      setGraiCapture({ codes: [], skippedCodes: [] });
      setPendingGraiResult(result);
      setProgressStatus(STATUS_READ_GRAI);
      return undefined;
    }

    return onResult(result);
  };

  const onCloseDialog = () => {
    setProgressStatus(STATUS_READ_HU_BARCODE);
    onCloseCallback?.();
  };

  const showEligibleBarcodeDebugButton = useBooleanSetting('barcodeScanner.showEligibleBarcodeDebugButton');

  // Early return (after every hook), so the BarcodeScannerComponent below is unmounted while the pick
  // is in flight: re-arming it would let the next scan book the same line a second time.
  if (isProcessing) {
    return <Spinner />;
  }

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
    case STATUS_READ_GRAI: {
      // Coerce to a number: the entered qty can arrive as a string, and `length === expectedCount`
      // (and thus canSave) would silently fail the strict-equality check against a string.
      const expectedCount = Number(pendingGraiResult.qty);
      const assignedGrais = getAssignedGrais(graiCodes, expectedCount);
      const extraGrais = getExtraGrais(graiCodes, expectedCount);
      // Save only when exactly N (= picked crates) GRAIs are captured, no extras.
      const canSave = graiCodes.length === expectedCount;
      return (
        <GraiCapturePanel
          graiCodes={graiCodes}
          assignedGrais={assignedGrais}
          extraGrais={extraGrais}
          expectedCount={expectedCount}
          skippedCount={skippedCount}
          countKey="activities.picking.graiScan.count"
          countExtraKey="activities.picking.graiScan.countExtra"
          countSkippedKey="activities.picking.graiScan.countSkipped"
          clearAllButtonKey="activities.picking.graiScan.clearAll.buttonCaption"
          clearAllConfirmKey="activities.picking.graiScan.clearAll.confirmQuestion"
          onAddGrais={handleAddGrais}
          onRemoveGrai={handleRemoveGrai}
          onClearAll={handleClearAllGrais}
        >
          <ButtonWithIndicator
            captionKey="activities.picking.graiScan.save.buttonCaption"
            testId="grai-save-button"
            disabled={!canSave}
            onClick={() =>
              onResult({ ...pendingGraiResult, setGrais: true, graiCodes: assignedGrais })?.catch?.((error) =>
                toastErrorFromObj(error)
              )
            }
            additionalCssClass="action-button"
          />
        </GraiCapturePanel>
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
  // GRAI Flow-Through: when true, an inline GRAI capture is auto-invoked after qty entry and the
  // captured codes are reported on the same onResult call (setGrais/graiCodes).
  graiScanEnabled: PropTypes.bool,
  // Canonical GRAI strings already assigned to this pick's effective loading unit (from prior picks
  // on this LU) — mirrors the server-side LU-wide dedupe so a re-scanned code does not advance the
  // count (see handleAddGrais).
  existingLuGrais: PropTypes.arrayOf(PropTypes.string),
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
 * The one qtyAboveMax ceiling, shared by the CU path (validateQtyEntered) and the whole-TU path
 * (requestQtyOrReportResult).
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
