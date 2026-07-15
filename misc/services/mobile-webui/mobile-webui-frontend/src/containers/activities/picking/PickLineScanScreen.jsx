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
import React, { useCallback } from 'react';
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
  } = useSelector((state) => getPropsFromState({ state, wfProcessId, activityId, lineId }), shallowEqual);

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
  // are reported on the same onResult, so qty + GRAIs go out as ONE atomic pick.
  const { graiScanEnabled, isTargetsLoading } = useAvailablePickingTargets({
    wfProcessId,
    lineId,
    type: PickingTargetType.TU,
  });
  const isInlineGraiCaptureEnabled = graiScanEnabled && tuPickingTarget == null;

  useHeaderUpdate({ url, caption, uom, qtyToPick, qtyPicked });

  const resolveScannedBarcode = useCallback(
    (scannedBarcode) =>
      convertScannedBarcodeToResolvedResult({
        scannedBarcode,
        expectedProductId: productId,
        expectedProductNo: productNo,
        isShowPromptWhenOverPicking,
        customQRCodeFormats,
        pickingUnit,
      }),
    [productId, productNo, isShowPromptWhenOverPicking, customQRCodeFormats, pickingUnit]
  );

  const onClose = useOnClose({ applicationId, wfProcessId, activity, lineId, next });

  const onResult = usePostQtyPicked({
    wfProcessId,
    activityId,
    lineId,
    expectedProductNo: productNo,
    expectedGS1ProductCodes: gs1ProductCodes,
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
      scannedBarcode={qrCode}
      graiScanEnabled={isInlineGraiCaptureEnabled}
      qtyTargetCaption={trl('general.QtyToPick')}
      qtyCaption={trl(pickingUnit === PICKING_UNIT_TU ? 'general.QtyTU' : 'general.Qty')}
      packingItemName={pickingUnit === PICKING_UNIT_TU ? packingItemName : null}
      qtyMax={qtyToPickRemaining}
      qtyTarget={qtyToPickRemaining}
      uom={uom}
      qtyRejectedReasons={qtyRejectedReasons}
      catchWeight={0}
      catchWeightUom={catchWeightUom}
      customQRCodeFormats={customQRCodeFormats}
      readAttributes={readAttributes}
      isShowCloseTargetButton={!!luPickingTarget}
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
    // Per-line readAttributes (e.g. SerialNo for serial-no products); falls back to the job-level set.
    readAttributes: line?.readAttributes ?? getReadAttributesFromActivity({ activity }),
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
    serialNos,
    productNo,
    isCloseTarget = false,
    isDone = true,
    resolvedBarcodeData,
    barcodeType,
    setGrais,
    graiCodes,
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
        huQRCode: scannedBarcode,
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
        setSerialNos: serialNos !== undefined,
        serialNos,
        isCloseTarget,
      })
    ).then(({ isPickingJobCompleted }) => !isPickingJobCompleted && isDone && onClose());
    //.catch((axiosError) => toastError({ axiosError })); // no need to catch, will be handled by caller
  };
};
