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
import DialogButton from './dialogs/DialogButton';
import { isLineDoneAfterBooking } from '../utils/picking';

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

/**
 * @param nextTUActions the node as is, or a function `({ scanner, pendingScan }) => node` for a button that owns the hidden scanner of the
 *   quantity block (see OpenNextTUButton). `scanner` is undefined on the first scan screen, which has its own visible scanner.
 *   The first scan screen shows it only when `isBookedOnScan` (a scan books right away, there is no quantity block); otherwise it is shown in the quantity block only.
 *   The pop-up's rescan scanner exists ONLY through the function form: a plain node adds no scanner, so a line that is repeated-booking
 *   but passes no function has no rescan.
 * @param scanner `{ resolveScannedBarcode, onResolvedResult }` or undefined
 * @param pendingScan `{ scannedBarcode, qtyEntered }`, the label scanned and the count typed so far in the quantity block; undefined on the scan step
 * @return the node to render, or undefined when there are no next-TU actions
 */
const renderNextTUActions = (nextTUActions, scanner, pendingScan) =>
  typeof nextTUActions === 'function' ? nextTUActions({ scanner, pendingScan }) : nextTUActions;

// With `isBookedOnScan` there is no quantity block: the scan step shows the next-TU actions and a Close button.
// Otherwise nothing is shown before the first scan.
const HUScanActionButtons = ({ nextTUActions, isBookedOnScan, onCloseDialog }) => {
  if (!isBookedOnScan) return null;

  return (
    <div className="buttons is-centered mb-4">
      {renderNextTUActions(nextTUActions, undefined)}
      <DialogButton
        captionKey="general.closeText"
        className="is-danger"
        testId="scan-each-close-button"
        onClick={onCloseDialog}
      />
    </div>
  );
};
HUScanActionButtons.propTypes = {
  nextTUActions: PropTypes.oneOfType([PropTypes.node, PropTypes.func]),
  isBookedOnScan: PropTypes.bool,
  onCloseDialog: PropTypes.func,
};

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
  isRepeatedBooking = false,
  isBookedOnScan = false,
  isLotAndBestBeforeShownReadOnly = false,
  isSubmitOnEnter = true,
  isShowCloseTargetButton = false,
  nextTUActions,
  suggestedQty,
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
  const [quantityBlockKey, setQuantityBlockKey] = useState(0);
  const [isProcessing, setProcessing] = useState(false);
  // synchronous twin of isProcessing: state is only visible after the next render, so two taps handled
  // before that render would both pass a state-only check
  const isResultInFlightRef = useRef(false);
  // GRAI Flow-Through: when graiScanEnabled, the confirmed qty result is stashed here and the GRAI
  // capture is shown inline (non-skippable) before the pick is reported, so qty + GRAIs go out in one
  // atomic onResult call.
  const [pendingGraiResult, setPendingGraiResult] = useState(null);
  // `codes` + `skippedCodes` are ONE state object (not two separate useState calls) so both derive
  // from a SINGLE queued functional update per scan event, and — crucially — `skippedCodes` is the
  // actual list of already-reported LU-skips, not just a count. A skipped code is NOT added to
  // `codes` (it must never occupy a crate slot), so without its own memory a second delivery of the
  // same physical scan would re-skip and re-count it. BarcodeScannerComponent and
  // GraiCapturePanel's own useKeyboardBarcodeReader can both deliver the same scan (see GraiCapturePanel);
  // feeding `prev.skippedCodes` back into mergeGraiArrays makes a redelivered skip a
  // silent no-op, so the count (and the toast) fire at most once per physical crate.
  const [graiCapture, setGraiCapture] = useState({ codes: [], skippedCodes: [] });
  const { codes: graiCodes, skippedCodes } = graiCapture;
  const skippedCount = skippedCodes.length;
  // Stable handler identities (matching the HU-Manager useGrais hook), so GraiCapturePanel's memoised
  // callbacks do not change on every render. handleAddGrais additionally depends on `existingLuGrais`,
  // which is stable for the whole capture session (fetched once per pick-step entry — see
  // useAvailablePickingTargets), so identity stability still holds.
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
    // the scan only opened a TU (GRAI): stay on the scan step, nothing to book yet
    if (resolvedBarcodeDataNew?.isNextTUOpened) {
      setProgressStatus(STATUS_READ_HU_BARCODE);
      return;
    }

    setResolvedBarcodeData(resolvedBarcodeDataNew);

    if (isBookedOnScan) {
      await bookScannedCU({ resolvedBarcodeData: resolvedBarcodeDataNew });
      return;
    }

    if (resolvedBarcodeDataNew.isScanProductCodeRequired) {
      setProgressStatus(STATUS_READ_PRODUCT_BARCODE);
      return;
    }

    await requestQtyOrReportResult({ resolvedBarcodeData: resolvedBarcodeDataNew });
  };

  const bookScannedCU = async ({ resolvedBarcodeData }) =>
    await fireOnResult({
      qty: 1,
      qtyRejected: 0,
      reason: null,
      scannedBarcode: resolvedBarcodeData.scannedBarcode,
      resolvedBarcodeData,
      isTUToBePickedAsWhole: false,
      cuLabel: resolvedBarcodeData.cuLabel,
      bestBeforeDate: resolvedBarcodeData.bestBeforeDate,
      lotNo: resolvedBarcodeData.lotNo,
      isCloseTarget: false,
      isDone: isLineDoneAfterBooking({ qtyBooked: 1, qtyOpen: resolvedBarcodeData.qtyMax }),
    });

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
  // The ref makes a second call before the next render a no-op (a state-only check would let both through).
  const fireOnResult = (onResultPayload) => {
    if (isResultInFlightRef.current) {
      return undefined;
    }
    const clearInFlight = () => {
      isResultInFlightRef.current = false;
      setProcessing(false);
    };

    isResultInFlightRef.current = true;
    setProcessing(true);
    try {
      const promise = onResult(onResultPayload)?.catch?.((error) => toastErrorFromObj(error));
      return doFinally(promise, clearInFlight);
    } catch (error) {
      clearInFlight();
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

  const onCULabelRescanned = async (resolvedBarcodeDataNew) => {
    if (resolvedBarcodeDataNew?.isNextTUOpened) {
      return;
    }
    setResolvedBarcodeData(resolvedBarcodeDataNew);
    setQuantityBlockKey((key) => key + 1);
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
      cuLabel: resolvedBarcodeData.cuLabel,
      bestBeforeDate,
      productionDate,
      lotNo,
      serialNos,
      productNo,
      isCloseTarget,
      // scan once: the picker stays on the line until its open quantity is booked
      isDone: isRepeatedBooking
        ? isLineDoneAfterBooking({ qtyBooked: qtyEnteredAndValidated, qtyOpen: resolvedBarcodeData.qtyMax })
        : isDone,
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

    const resultPromise = onResult(result);
    return isRepeatedBooking && resultPromise?.then
      ? resultPromise.then((value) => {
          // a done line navigates away: nothing to reset there; a booking that was not sent keeps the typed count
          if (!result.isDone && !value?.isNotBooked) {
            setQuantityBlockKey((key) => key + 1);
          }
          return value;
        })
      : resultPromise;
  };

  // The dialog knows the qty context (entered qty, rejected qty, reason); the caller's prompt may also
  // need what the SCANNED HU is (e.g. the manufacturing issue screen names the HU's remainder, not the
  // order-side shortfall), so `resolvedBarcodeData` is handed over too. Memoised because this wrapper is
  // the first dependency of GetQuantityDialog's own `getConfirmationPrompt` useCallback; an inline arrow
  // would invalidate that memo on every render of this component.
  const getConfirmationPromptForQtyWithHU = useCallback(
    (qtyInput, context) => getConfirmationPromptForQty(qtyInput, { ...context, resolvedBarcodeData }),
    [getConfirmationPromptForQty, resolvedBarcodeData]
  );

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
          {/* The tu buttons come first: on a phone the scanner panel (icon, caption, "Enter manually") is tall, and the buttons below it
              would sit under the fold. The scanner needs no tap (hardware scan), the buttons do. */}
          <HUScanActionButtons
            nextTUActions={nextTUActions}
            isBookedOnScan={isBookedOnScan}
            onCloseDialog={onCloseDialog}
          />
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
          key={`${quantityBlockKey}_${suggestedQty ?? ''}`}
          userInfo={resolvedBarcodeData.userInfo}
          qtyTargetCaption={resolvedBarcodeData.qtyTargetCaption}
          qtyTarget={resolvedBarcodeData.qtyTarget}
          qtyCaption={resolvedBarcodeData.qtyCaption}
          qtyInitial={isRepeatedBooking ? suggestedQty : resolvedBarcodeData.qtyInitial}
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
          isRepeatedBooking={isRepeatedBooking}
          isLotAndBestBeforeShownReadOnly={isLotAndBestBeforeShownReadOnly}
          isSubmitOnEnter={isSubmitOnEnter}
          bestBeforeDate={resolvedBarcodeData.bestBeforeDate}
          lotNo={resolvedBarcodeData.lotNo}
          isShowCloseTargetButton={isShowCloseTargetButton}
          nextTUActions={({ qtyEntered }) =>
            renderNextTUActions(
              nextTUActions,
              // the label rescan (new lot / best-before, next TU by its GRAI) is read by the hidden scanner of the "next TU" button
              isRepeatedBooking
                ? { resolveScannedBarcode: handleResolveHUScannedBarcode, onResolvedResult: onCULabelRescanned }
                : undefined,
              // what the screen keeps while the picker is away choosing the TU type
              { scannedBarcode: resolvedBarcodeData.scannedBarcode, qtyEntered }
            )
          }
          //
          // `undefined` when the caller passes no prompt callback: GetQuantityDialog's "no prop -> no
          // prompt" contract is a truthiness check, which a bare wrapper would always satisfy.
          getConfirmationPromptForQty={getConfirmationPromptForQty ? getConfirmationPromptForQtyWithHU : undefined}
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
            onClick={() => fireOnResult({ ...pendingGraiResult, setGrais: true, graiCodes: assignedGrais })}
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
  // Behaviour switches (the caller maps its own mode onto them)
  /** the screen stays after every booking until the open quantity is booked; label can be rescanned */
  isRepeatedBooking: PropTypes.bool,
  /** every scan books one unit at once, without a quantity block; the scan step stays open and shows a Close button */
  isBookedOnScan: PropTypes.bool,
  isLotAndBestBeforeShownReadOnly: PropTypes.bool,
  isSubmitOnEnter: PropTypes.bool,
  isShowCloseTargetButton: PropTypes.bool,
  nextTUActions: PropTypes.oneOfType([PropTypes.node, PropTypes.func]),
  suggestedQty: PropTypes.number,
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
  // Books the entered quantity: (result) => Promise|any. Its resolved value is read only for repeated booking (scan once):
  // the NOT_BOOKED marker (`{ isNotBooked: true }`, see useOpenNextTU) means the booking was not sent, so the typed count stays;
  // any other value (including undefined) means it was sent.
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
