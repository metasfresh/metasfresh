/*
 * #%L
 * ic114
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import React, { useCallback, useMemo } from 'react';
import { trl } from '../../../utils/translations';
import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import {
  getActivityById,
  getCustomQRCodeFormats,
  getLineById,
  getQtyRejectedReasonsFromActivity,
} from '../../../reducers/wfProcesses';
import { parseQRCodeString, toQRCodeString } from '../../../utils/qrCode/hu';
import { getScannedHUQRCodeInfo, useAvailablePickingTargets } from '../../../api/picking';
import { PickingTargetType } from '../../../constants/PickingTargetType';
import Spinner from '../../../components/Spinner';
import { useBooleanSetting } from '../../../reducers/settings';
import {
  computeCUEntryToRemember,
  computeSuggestedQty,
  getNextEligibleLineToPick,
  getQtyPickedOrRejectedTotalForLine,
  getQtyToPickForLine,
  getQtyToPickRemainingForLine,
} from '../../../utils/picking';
import { useSearchParams } from '../../../hooks/useSearchParams';
import { useHeaderUpdate } from './PickLineScreen';
import { pickingLineScanScreenLocation, pickingLineScreenLocation } from '../../../routes/picking';
import { getWFProcessScreenLocation } from '../../../routes/workflow_locations';
import { useCurrentPickingTargetInfo } from '../../../reducers/wfProcesses/picking/useCurrentPickTarget';
import { toNumberOrZero } from '../../../utils/numbers';
import { isBarcodeProductNoMatching } from '../../../utils/qrCode/common';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { PICKING_UNIT_TU } from '../../../reducers/wfProcesses/picking/PickingUnit';
import { getReadAttributesFromActivity } from '../../../reducers/wfProcesses/picking/getReadAttributesFromActivity';
import { resolveCULabel, routeScannedCode, selectCULabelFormat, SCAN_TYPE_GRAI } from '../../../utils/cuLabel';
import { parseGraiFromRawInput } from '../../../utils/grai';
import NextBoxActions, { useNextBox } from '../../../components/NextBoxActions';
import { isNewMode, isScanOnce as isScanOnceMode } from '../../../constants/CUQtyEntryMode';
import { rememberCUEntry } from '../../../actions/PickingActions';
import { getCUEntry } from '../../../reducers/wfProcesses/picking';
import { postStepPickedThunk } from '../../../apps/picking/redux/postStepPickedThunk';

export const NEXT_PickingJob = 'pickingJob';
export const NEXT_NextPickingLine = 'nextPickingLine';

const PickLineScanScreen = () => {
  const { url, applicationId, wfProcessId, activityId, lineId } = useScreenDefinition({
    screenId: 'PickLineScanScreen',
    back: pickingLineScreenLocation,
  });

  const [urlParams] = useSearchParams();
  const qrCode = urlParams.get('qrCode');
  const next = urlParams.get('next');
  const isResume = urlParams.get('resume') === 'Y';

  const {
    activity,
    caption,
    productId,
    productNo,
    gs1ProductCodes,
    pickingUnit,
    packingItemName,
    qtyToPick,
    qtyPicked,
    qtyToPickRemaining,
    uom,
    qtyRejectedReasons,
    catchWeightUom,
    isShowPromptWhenOverPicking,
    customQRCodeFormats,
    readAttributes,
    cuQtyEntryMode,
    cuLabelFormatId,
    packingTuPIId,
    cuEntry,
  } = useSelector((state) => getPropsFromState({ state, wfProcessId, activityId, lineId }), shallowEqual);

  const cuLabelFormat = useMemo(
    () => selectCULabelFormat(customQRCodeFormats, cuLabelFormatId),
    [customQRCodeFormats, cuLabelFormatId]
  );

  const { luPickingTarget } = useCurrentPickingTargetInfo({ wfProcessId, activityId });
  // The current TU pick target (line-scoped for PRODUCT aggregation, header-scoped otherwise — hence
  // fallbackToHeader). When a TU pick target is set, the operator established it via the pick-target
  // GRAI scan (SelectPickTargetScreen's GraiScanPanel), which already captured that TU's GRAI — so the
  // inline GRAI capture below must NOT fire (it would swallow the pick). The pre-materialization target
  // exposes `.grai`; once the TU is materialized on first pick it becomes an existing-TU target (grai
  // nulled in the JSON) but is still present — so we gate on presence, which survives materialization.
  // The Flow-Through pick uses an LU target with NO TU target, so the inline capture still fires there.
  const { tuPickingTarget } = useCurrentPickingTargetInfo({
    wfProcessId,
    activityId,
    lineId,
    fallbackToHeader: true,
  });

  // GRAI Flow-Through: when GRAI scanning is required for this job's customer, the qty confirm
  // auto-invokes the inline GRAI capture (handled by ScanHUAndGetQtyComponent) and the captured codes
  // are reported on the same onResult, so qty + GRAIs go out as ONE atomic pick. `existingLuGrais` are
  // the GRAIs already assigned to this line's effective LU by prior picks — the capture panel mirrors
  // the server-side LU-wide dedupe against them, so re-scanning a GRAI already on the LU (a
  // different product picked onto the SAME LU) is skipped, not counted. The Flow-Through pick happens on
  // this line-scan screen (no per-step scan screen), so the wiring must live here too, not only in
  // PickStepScanScreen.
  const { graiScanEnabled, existingLuGrais, isTargetsLoading, setPickingTarget } = useAvailablePickingTargets({
    wfProcessId,
    lineId,
    type: PickingTargetType.TU,
  });
  const isInlineGraiCapture = isInlineGraiCaptureEnabled({ graiScanEnabled, tuPickingTarget, cuQtyEntryMode });

  const isNewModeLine = isNewMode(cuQtyEntryMode);
  const qtyUnitProps = computeQtyUnitProps({ pickingUnit, packingItemName });
  const nextBox = useNextBox({
    applicationId,
    wfProcessId,
    activityId,
    lineId,
    packingTuPIId,
    packingItemName,
    tuPickingTarget,
    setPickingTarget,
  });

  useHeaderUpdate({
    url,
    caption,
    uom,
    qtyToPick,
    qtyPicked,
    additionalHeaderProperties: { entries: computeOpenBoxHeaderEntries({ isNewModeLine, tuPickingTarget }) },
    rootDataAttributes: { 'data-cu-qty-entry-mode': cuQtyEntryMode },
  });

  // not memoized: `nextBox.onGraiScanned` is a new function on every render
  const resolveScannedBarcode = (scannedBarcode) =>
    isNewModeLine
      ? convertScannedCULabelToResolvedResult({
          scannedBarcode,
          cuLabelFormat,
          expectedProductNo: productNo,
          expectedGS1ProductCodes: gs1ProductCodes,
          onGrai: nextBox.onGraiScanned,
        })
      : convertScannedBarcodeToResolvedResult({
          scannedBarcode,
          expectedProductId: productId,
          expectedProductNo: productNo,
          isShowPromptWhenOverPicking,
          customQRCodeFormats,
          pickingUnit,
        });

  const onClose = useOnClose({ applicationId, wfProcessId, activity, lineId, next });

  // scan once: the count offered for the next box (the last typed count, capped by the open quantity)
  const isScanOnce = isScanOnceMode(cuQtyEntryMode);
  const suggestedQty = isScanOnce
    ? computeSuggestedQty({ lastTypedQty: cuEntry?.lastTypedQty, qtyOpen: qtyToPickRemaining }) ?? undefined
    : undefined;

  const onResult = usePostQtyPicked({
    wfProcessId,
    activityId,
    lineId,
    expectedProductNo: productNo,
    expectedGS1ProductCodes: gs1ProductCodes,
    rememberedLastTypedQty: isScanOnce ? cuEntry?.lastTypedQty : undefined,
    suggestedQty,
    isRememberCUEntry: isScanOnce,
    cuQtyEntryMode,
    sourceHUQRCode: computeSourceHUQRCode({ qrCode, activity }),
    onClose,
  });

  const getConfirmationPromptForQty = useCallback(
    (qtyInput) => {
      if (qtyToPickRemaining !== undefined && toNumberOrZero(qtyInput) > qtyToPickRemaining) {
        return trl('activities.picking.overPickConfirmationPrompt');
      }
      return undefined;
    },
    [qtyToPickRemaining]
  );

  // Block qty entry until graiScanEnabled is known (the targets GET defaults it false while in flight),
  // else a GRAI-required pick could be confirmed and sent without GRAIs.
  if (isTargetsLoading) {
    return <Spinner />;
  }

  return (
    <ScanHUAndGetQtyComponent
      key={`${applicationId}_${wfProcessId}_${activityId}_${lineId}_scan`} // very important, to force the component recreation when we do history.replace
      // the source HU of the hop from the line screen is never a CU label
      scannedBarcode={computeScannedBarcodeParam({ qrCode, cuQtyEntryMode })}
      graiScanEnabled={isInlineGraiCapture}
      existingLuGrais={existingLuGrais}
      qtyTargetCaption={trl('general.QtyToPick')}
      qtyCaption={trl(qtyUnitProps.qtyCaptionKey)}
      packingItemName={qtyUnitProps.packingItemName}
      qtyMax={qtyToPickRemaining}
      qtyTarget={qtyToPickRemaining}
      uom={uom}
      qtyRejectedReasons={qtyRejectedReasons}
      catchWeight={0}
      catchWeightUom={catchWeightUom}
      customQRCodeFormats={customQRCodeFormats}
      readAttributes={readAttributes}
      cuQtyEntryMode={cuQtyEntryMode}
      suggestedQty={suggestedQty}
      rememberedCULabel={computeRememberedCULabel({ isScanOnce, isResume, cuEntry })}
      isShowCloseTargetButton={!!luPickingTarget}
      nextBoxActions={
        isNewModeLine ? <NextBoxActions isProcessing={nextBox.isProcessing} onNextBox={nextBox.onNextBox} /> : undefined
      }
      //
      resolveScannedBarcode={resolveScannedBarcode}
      onResult={onResult}
      onClose={onClose}
      getConfirmationPromptForQty={isShowPromptWhenOverPicking ? getConfirmationPromptForQty : undefined}
    />
  );
};

export default PickLineScanScreen;

//
//
// -------------------------------------------------------------------------------------
//
//

/**
 * The box open on a new-mode line stays visible in the header (a toast alone is gone when the picker looks back).
 */
// @VisibleForTesting
export const computeOpenBoxHeaderEntries = ({ isNewModeLine, tuPickingTarget }) =>
  isNewModeLine && tuPickingTarget?.caption
    ? [{ caption: trl('activities.picking.tuPickingTarget.Current'), value: tuPickingTarget.caption }]
    : [];

/**
 * Caption and packing name of the quantity block follow the picking unit the line JSON reports (a new-mode line is
 * reported in CU, so it shows neither the TU caption nor the packing name).
 */
// @VisibleForTesting
export const computeQtyUnitProps = ({ pickingUnit, packingItemName }) =>
  pickingUnit === PICKING_UNIT_TU
    ? { qtyCaptionKey: 'general.QtyTU', packingItemName }
    : { qtyCaptionKey: 'general.Qty', packingItemName: null };

/**
 * The `qrCode` param of the screen URL is the source HU when the picker comes back through the line screen's
 * "HU verpacken"; a line with a CU quantity entry mode reads labels only, so it never takes that param as a scan.
 */
// @VisibleForTesting
export const computeScannedBarcodeParam = ({ qrCode, cuQtyEntryMode }) =>
  isNewMode(cuQtyEntryMode) ? undefined : qrCode;

/**
 * The HU the PICK event takes the packs from (`huQRCode`). A line with a CU quantity entry mode takes them from the
 * source HU of the screen URL ("Kommissionieren aus", or the pick-on-the-fly code); the scanned CU label only gives
 * the attributes and travels as `cuLabel`. Manual keeps today: the scanned code is the source HU.
 */
// @VisibleForTesting
export const computePickFromHUQRCode = ({ cuQtyEntryMode, sourceHUQRCode, scannedBarcode }) => {
  if (!isNewMode(cuQtyEntryMode)) return scannedBarcode;
  if (!sourceHUQRCode) throw { messageKey: 'picking.pickFromRequired' };
  return sourceHUQRCode;
};

/**
 * The source HU of a new-mode pick: the `qrCode` of the screen URL, else the job's current "Kommissionieren aus" HU.
 * @returns the source HU QR code, or undefined when neither exists (e.g. "Scannen" without a source HU)
 */
export const computeSourceHUQRCode = ({ qrCode, activity }) => qrCode || activity?.dataStored?.pickFromHU?.code;

/**
 * Only the hop back from the line screen ("HU verpacken") reopens the quantity block with the remembered label; every
 * other entry (e.g. the scan button, a line reopened after closing it) starts with the scan step.
 */
// @VisibleForTesting
export const computeRememberedCULabel = ({ isScanOnce, isResume, cuEntry }) =>
  isScanOnce && isResume ? cuEntry?.label : undefined;

/**
 * The pick-time GRAI capture (after OK) belongs to the Manual path; in the CU quantity entry modes the picker opens
 * every box beforehand (GRAI scan or "Weitere Kiste"), so it never shows there.
 */
// @VisibleForTesting
export const isInlineGraiCaptureEnabled = ({ graiScanEnabled, tuPickingTarget, cuQtyEntryMode }) =>
  graiScanEnabled && tuPickingTarget == null && !isNewMode(cuQtyEntryMode);

const getPropsFromState = ({ state, wfProcessId, activityId, lineId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const qtyRejectedReasons = getQtyRejectedReasonsFromActivity(activity);
  const customQRCodeFormats = getCustomQRCodeFormats({ activity });
  const line = getLineById(state, wfProcessId, activityId, lineId);

  return {
    activity,
    caption: line?.caption,
    productId: line.productId,
    productNo: line.productNo,
    gs1ProductCodes: line.gs1ProductCodes,
    pickingUnit: line?.pickingUnit,
    packingItemName: line?.packingItemName,
    qtyToPick: getQtyToPickForLine({ line }),
    qtyPicked: getQtyPickedOrRejectedTotalForLine({ line }),
    qtyToPickRemaining: getQtyToPickRemainingForLine({ line }),
    uom: line.uom,
    qtyRejectedReasons,
    catchWeightUom: line.catchWeightUOM,
    isShowPromptWhenOverPicking: activity?.dataStored?.isShowPromptWhenOverPicking,
    customQRCodeFormats,
    readAttributes: getReadAttributesFromActivity({ activity }),
    cuQtyEntryMode: line?.cuQtyEntryMode,
    cuLabelFormatId: line?.cuLabelFormatId,
    packingTuPIId: line?.packingTuPIId, // null when the line's packing names no TU
    cuEntry: getCUEntry({ activity, lineId }),
  };
};

//
//
// -------------------------------------------------------------------------------------
//
//

// @VisibleForTesting
export const convertScannedBarcodeToResolvedResult = async ({
  scannedBarcode,
  expectedProductId,
  expectedProductNo,
  isShowPromptWhenOverPicking,
  customQRCodeFormats,
  pickingUnit,
}) => {
  let parsedQRCode = parseQRCodeString({
    string: scannedBarcode,
    customQRCodeFormats,
    returnFalseOnError: true,
    checkOnlyPreciseFormats: true, // consider only precise formats, all the others will be matched on backend.
  });
  // console.log('convertScannedBarcodeToResolvedResult 1', { parsedQRCode, scannedBarcode });

  //
  // If the scanned barcode could not be parsed, we have to check on the backend side
  let huInfoFromBackend = null;
  if (!parsedQRCode) {
    huInfoFromBackend = await getScannedHUQRCodeInfo({ qrCode: scannedBarcode });
    // console.log('convertScannedBarcodeToResolvedResult 2', { huInfoFromBackend });
    parsedQRCode = parseQRCodeString({
      string: huInfoFromBackend?.huQRCode?.code,
      customQRCodeFormats,
      returnFalseOnError: false, // fail
      checkOnlyPreciseFormats: false, // after we have checked the backend, it's fine to try matching everything
    });
    // console.log('convertScannedBarcodeToResolvedResult 3', { parsedQRCode });
  }

  if (expectedProductId != null && parsedQRCode.productId != null && parsedQRCode.productId !== expectedProductId) {
    console.warn('Scanned barcode does not match the expected product', {
      expectedProductId,
      actualProductId: parsedQRCode.productId,
      parsedQRCode,
      scannedBarcode,
    });
    throw trl('activities.picking.notEligibleHUBarcode');
  }

  return convertQRCodeObjectToResolvedResult({
    parsedQRCode,
    pickingUnit,
    huInfoFromBackend,
    expectedProductNo,
    isShowPromptWhenOverPicking,
  });
};

/**
 * A line with a CU quantity entry mode reads the scanned string as a CU label with the line's format only.
 * Returns the label data for the quantity block (lot, best-before) and the raw label; refusals are thrown.
 * A scanned GRAI is accepted on every such line: `onGrai` (required) opens the box from it.
 */
// @VisibleForTesting
export const convertScannedCULabelToResolvedResult = ({
  scannedBarcode,
  cuLabelFormat,
  expectedProductNo,
  expectedGS1ProductCodes,
  onGrai,
}) => {
  if (routeScannedCode(scannedBarcode) === SCAN_TYPE_GRAI) {
    // a GRAI opens a box, it is never read as a label
    return onGrai(parseGraiFromRawInput(scannedBarcode)).then(() => ({ isNextBoxOpened: true }));
  }

  const { errorKey, lotNo, bestBeforeDate, packId, cuLabel } = resolveCULabel({
    string: scannedBarcode,
    format: cuLabelFormat,
    expectedProductNo,
    expectedGS1ProductCodes,
  });
  if (errorKey) {
    throw { messageKey: errorKey };
  }

  return { lotNo, bestBeforeDate, packId, cuLabel, isTUToBePickedAsWhole: false };
};

//
//
// -------------------------------------------------------------------------------------
//
//

const convertQRCodeObjectToResolvedResult = async ({
  parsedQRCode,
  pickingUnit,
  huInfoFromBackend,
  expectedProductNo,
  isShowPromptWhenOverPicking,
}) => {
  const result = {
    qrCode: parsedQRCode,
  };

  if (parsedQRCode.weightNet != null) {
    result['catchWeight'] = parsedQRCode.weightNet;
  }

  if (parsedQRCode.isTUToBePickedAsWhole === true) {
    result['isTUToBePickedAsWhole'] = true;
  }

  result['bestBeforeDate'] = parsedQRCode.bestBeforeDate;
  result['productionDate'] = parsedQRCode.productionDate;
  result['lotNo'] = parsedQRCode.lotNo;

  result.scannedHU = {
    huUnitType: parsedQRCode.huUnitType,
  };

  // Existing behavior: fetch HU info for LU in TU-picking (qtyTUs)
  if (parsedQRCode.huUnitType === 'LU' && pickingUnit === PICKING_UNIT_TU) {
    let huInfo = huInfoFromBackend;
    if (huInfo == null) {
      try {
        huInfo = await getScannedHUQRCodeInfo({ qrCode: toQRCodeString(parsedQRCode) });
        result.scannedHU.qtyTUs = huInfo.qtyTUs;
      } catch (error) {
        console.warn('Failed to get LU info. Ignored', error);
      }
    }
    if (huInfo != null) {
      result.scannedHU.qtyTUs = huInfo.qtyTUs;
    }
  }

  // For whole-TU picks with overdelivery prompt enabled,
  // fetch actual product qty from backend so the prompt can detect overdelivery.
  // The backend picks the full HU storage qty when isPickWholeTU=true,
  // so we need the actual qty to compare against remaining.
  if (parsedQRCode.isTUToBePickedAsWhole === true && isShowPromptWhenOverPicking) {
    try {
      const huInfo =
        huInfoFromBackend ??
        (await getScannedHUQRCodeInfo({ qrCode: toQRCodeString(parsedQRCode), productNo: expectedProductNo }));
      if (huInfo?.productQty != null) {
        result.qtyInitial = parseFloat(huInfo.productQty);
      }
    } catch (error) {
      console.warn('Failed to get HU product qty for overdelivery check. Ignored', error);
    }
  }

  // console.log('convertQRCodeObjectToResolvedResult', { result, qrCodeObj: parsedQRCode, pickingUnit });
  return result;
};

//
//
// -------------------------------------------------------------------------------------
//
//

const useOnClose = ({ applicationId, wfProcessId, activity, lineId, next }) => {
  const history = useMobileNavigation();
  const isGotoPickingJobOnClose = useBooleanSetting('PickLineScanScreen.gotoPickingJobOnClose', true);

  const gotoPickingJob = () => {
    history.replace(getWFProcessScreenLocation({ applicationId, wfProcessId }));
  };

  const gotoNextPickingLine = () => {
    const nextLineId = getNextEligibleLineToPick({ activity, excludeLineId: lineId })?.pickingLineId;
    if (nextLineId) {
      history.replace(
        pickingLineScanScreenLocation({
          applicationId,
          wfProcessId,
          activityId: activity.activityId,
          lineId: nextLineId,
          next: NEXT_NextPickingLine,
        })
      );
    } else {
      gotoPickingJob();
    }
  };

  return () => {
    if (next === NEXT_PickingJob) {
      gotoPickingJob();
    } else if (next === NEXT_NextPickingLine) {
      gotoNextPickingLine();
    } else {
      if (isGotoPickingJobOnClose) {
        gotoPickingJob();
      } else {
        history.goBack(); // go to picking line screen
      }
    }
  };
};

//
//
// -------------------------------------------------------------------------------------
//
//

const usePostQtyPicked = ({
  wfProcessId,
  activityId,
  lineId: lineIdParam = null,
  expectedProductNo,
  expectedGS1ProductCodes,
  isRememberCUEntry = false,
  rememberedLastTypedQty,
  suggestedQty,
  cuQtyEntryMode,
  sourceHUQRCode,
  onClose,
}) => {
  const dispatch = useDispatch();
  const history = useMobileNavigation();

  return ({
    qty = 0,
    qtyRejected,
    reason = null,
    scannedBarcode = null,
    catchWeight = null,
    bestBeforeDate,
    lotNo,
    productNo,
    isCloseTarget = false,
    isDone = true,
    resolvedBarcodeData,
    barcodeType,
    setGrais,
    graiCodes,
    cuLabel,
  }) => {
    const lineIdEffective = resolvedBarcodeData?.lineId ?? lineIdParam;

    if (
      !isBarcodeProductNoMatching({
        expectedProductNo,
        expectedGS1ProductCodes,
        barcodeProductNo: productNo,
        barcodeType,
      })
    ) {
      throw {
        messageKey: 'activities.picking.qrcode.differentProduct',
        context: { expectedProductNo, expectedGS1ProductCodes, productNo, barcodeType },
      };
    }

    return dispatch(
      postStepPickedThunk({
        history,
        wfProcessId,
        activityId,
        lineId: lineIdEffective,
        huQRCode: computePickFromHUQRCode({ cuQtyEntryMode, sourceHUQRCode, scannedBarcode }),
        qtyPicked: qty,
        qtyRejectedReasonCode: reason,
        qtyRejected,
        catchWeight,
        pickWholeTU: resolvedBarcodeData?.isTUToBePickedAsWhole ?? false,
        checkIfAlreadyPacked: catchWeight == null, // in case we deal with a catch weight product, always split, else we won't be able to pick a CU from CU if last CU
        setBestBeforeDate: bestBeforeDate !== undefined,
        bestBeforeDate,
        setLotNo: lotNo !== undefined,
        lotNo,
        setGrais,
        graiCodes,
        cuLabel,
        isCloseTarget,
      })
    ).then(({ isPickingJobCompleted }) => {
      if (isRememberCUEntry && !isPickingJobCompleted) {
        const cuEntryToRemember = computeCUEntryToRemember({
          previousLastTypedQty: rememberedLastTypedQty,
          suggestedQty,
          qtyBooked: qty,
          cuLabel,
          lotNo,
          bestBeforeDate,
        });
        dispatch(rememberCUEntry({ wfProcessId, activityId, lineId: lineIdEffective, ...cuEntryToRemember }));
      }

      return !isPickingJobCompleted && isDone && onClose();
    });
    //.catch((axiosError) => toastError({ axiosError })); // no need to catch, will be handled by caller
  };
};
