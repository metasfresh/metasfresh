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

import { useHistory, useRouteMatch } from 'react-router-dom';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import React, { useCallback, useEffect } from 'react';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { trl } from '../../../utils/translations';
import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import { getActivityById, getLineById, getQtyRejectedReasonsFromActivity } from '../../../reducers/wfProcesses';
import { parseQRCodeString } from '../../../utils/qrCode/hu';
import { postStepPicked } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useBooleanSetting } from '../../../reducers/settings';
import { getQtyPickedOrRejectedTotalForLine, getQtyToPickRemainingForLine } from '../../../utils/picking';
import { isShowBestBeforeDate, isShowLotNo } from './PickConfig';

const PickLineScanScreen = () => {
  const {
    url,
    params: { workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const { productId, qtyToPickRemaining, uom, qtyRejectedReasons, catchWeightUom } = useSelector(
    (state) => getPropsFromState({ state, wfProcessId, activityId, lineId }),
    shallowEqual
  );

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: trl('activities.picking.scanQRCode'),
        values: [],
      })
    );
  }, []);

  const resolveScannedBarcode = useCallback(
    (scannedBarcode) => convertScannedBarcodeToResolvedResult({ scannedBarcode, expectedProductId: productId }),
    [productId]
  );

  const onResult = usePostQtyPicked({ wfProcessId, activityId, lineId });

  const history = useHistory();
  const isGotoPickingJobOnClose = useBooleanSetting('PickLineScanScreen.gotoPickingJobOnClose', true);
  const onClose = () => {
    if (isGotoPickingJobOnClose) {
      history.go(-2); // go to picking job screen
    }
  };

  return (
    <ScanHUAndGetQtyComponent
      qtyCaption={trl('general.QtyToPick')}
      qtyMax={qtyToPickRemaining}
      qtyTarget={qtyToPickRemaining}
      uom={uom}
      qtyRejectedReasons={qtyRejectedReasons}
      catchWeight={0}
      catchWeightUom={catchWeightUom}
      isShowBestBeforeDate={isShowBestBeforeDate}
      isShowLotNo={isShowLotNo}
      //
      resolveScannedBarcode={resolveScannedBarcode}
      onResult={onResult}
      onClose={onClose}
    />
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const qtyRejectedReasons = getQtyRejectedReasonsFromActivity(activity);

  const line = getLineById(state, wfProcessId, activityId, lineId);

  return {
    productId: line.productId,
    qtyToPick: line.qtyToPick,
    qtyPicked: getQtyPickedOrRejectedTotalForLine({ line }),
    qtyToPickRemaining: getQtyToPickRemainingForLine({ line }),
    uom: line.uom,
    qtyRejectedReasons,
    catchWeightUom: line.catchWeightUOM,
  };
};

const convertScannedBarcodeToResolvedResult = ({ scannedBarcode, expectedProductId }) => {
  const parsedHUQRCode = parseQRCodeString(scannedBarcode);
  //console.log('resolveScannedBarcode', { parsedHUQRCode });

  if (parsedHUQRCode.productId != null && parsedHUQRCode.productId !== expectedProductId) {
    throw trl('activities.picking.notEligibleHUBarcode');
  }

  return convertQRCodeObjectToResolvedResult({ parsedHUQRCode });
};

export const convertQRCodeObjectToResolvedResult = (qrCodeObj) => {
  const result = {};

  if (qrCodeObj.weightNet != null) {
    result['catchWeight'] = qrCodeObj.weightNet;
  }

  if (qrCodeObj.isTUToBePickedAsWhole === true) {
    result['isTUToBePickedAsWhole'] = true;
  }

  result['bestBeforeDate'] = qrCodeObj.bestBeforeDate;
  result['lotNo'] = qrCodeObj.lotNo;

  //console.log('resolveScannedBarcode', { result, qrCodeObj });
  return result;
};

export const usePostQtyPicked = ({ wfProcessId, activityId, lineId: lineIdParam = null }) => {
  const dispatch = useDispatch();
  const history = useHistory();

  return ({
    lineId = null,
    qty = 0,
    qtyRejected,
    reason = null,
    scannedBarcode = null,
    catchWeight = null,
    catchWeightUom = null,
    isTUToBePickedAsWhole = false,
    bestBeforeDate = null,
    lotNo = null,
    gotoPickingLineScreen = true,
    resolvedBarcodeData,
    ...others
  }) => {
    const lineIdEffective = resolvedBarcodeData?.lineId ?? lineIdParam;
    console.log('usePostQtyPicked.onResult', {
      lineIdEffective,
      lineId,
      lineIdParam,
      qty,
      reason,
      scannedBarcode,
      catchWeight,
      catchWeightUom,
      isShowBestBeforeDate,
      bestBeforeDate,
      isShowLotNo,
      lotNo,
      gotoPickingLineScreen,
      ...others,
    });

    return postStepPicked({
      wfProcessId,
      activityId,
      lineId: lineIdEffective,
      //stepId,
      huQRCode: scannedBarcode,
      qtyPicked: qty,
      qtyRejectedReasonCode: reason,
      qtyRejected,
      catchWeight,
      pickWholeTU: isTUToBePickedAsWhole,
      checkIfAlreadyPacked: catchWeight == null, // in case we deal with a catch weight product, always split, else we won't be able to pick a CU from CU if last CU
      setBestBeforeDate: isShowBestBeforeDate,
      bestBeforeDate,
      setLotNo: isShowLotNo,
      lotNo,
    }).then((wfProcess) => {
      dispatch(updateWFProcess({ wfProcess }));
      if (gotoPickingLineScreen) {
        history.go(-1);
      }
    });
    //.catch((axiosError) => toastError({ axiosError })); // no need to catch, will be handled by caller
  };
};

export default PickLineScanScreen;
