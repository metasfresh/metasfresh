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

import { useRouteMatch } from 'react-router-dom';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import React, { useEffect } from 'react';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { trl } from '../../../utils/translations';
import ScanHUAndGetQtyComponent from '../../../components/ScanHUAndGetQtyComponent';
import { getActivityById, getLineById, getQtyRejectedReasonsFromActivity } from '../../../reducers/wfProcesses';
import { parseQRCodeString } from '../../../utils/huQRCodes';
import { postStepPicked } from '../../../api/picking';
import { toastError } from '../../../utils/toast';
import { updateWFProcess } from '../../../actions/WorkflowActions';

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

  const resolveScannedBarcode = (scannedBarcode) => {
    const result = {};

    const parsedHUQRCode = parseQRCodeString(scannedBarcode);
    //console.log('resolveScannedBarcode', { parsedHUQRCode });

    if (parsedHUQRCode.productId != null && parsedHUQRCode.productId !== productId) {
      throw trl('activities.picking.notEligibleHUBarcode');
    }

    if (parsedHUQRCode.weightNet != null) {
      result['catchWeight'] = parsedHUQRCode.weightNet;
    }

    if (parsedHUQRCode.isTUToBePickedAsWhole === true) {
      result['isTUToBePickedAsWhole'] = true;
    }

    //console.log('resolveScannedBarcode', { result, parsedHUQRCode });
    return result;
  };
  const onResult = ({
    qty = 0,
    qtyRejected,
    reason = null,
    scannedBarcode = null,
    catchWeight = null,
    catchWeightUom = null,
    isTUToBePickedAsWhole = false,
    gotoPickingLineScreen = true,
    ...others
  }) => {
    console.log('onResult', {
      qty,
      reason,
      scannedBarcode,
      catchWeight,
      catchWeightUom,
      gotoPickingLineScreen,
      ...others,
    });

    postStepPicked({
      wfProcessId,
      activityId,
      lineId,
      //stepId,
      huQRCode: scannedBarcode,
      qtyPicked: qty,
      qtyRejectedReasonCode: reason,
      qtyRejected,
      catchWeight,
      pickWholeTU: isTUToBePickedAsWhole,
      checkIfAlreadyPacked: catchWeight == null, // in case we deal with a catch weight product, always split, else we won't be able to pick a CU from CU if last CU
    })
      .then((wfProcess) => {
        dispatch(updateWFProcess({ wfProcess }));
        if (gotoPickingLineScreen) {
          history.go(-1);
        }
      })
      .catch((axiosError) => toastError({ axiosError }));
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
      //
      resolveScannedBarcode={resolveScannedBarcode}
      onResult={onResult}
    />
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const qtyRejectedReasons = getQtyRejectedReasonsFromActivity(activity);

  const line = getLineById(state, wfProcessId, activityId, lineId);

  const qtyPickedOrRejectedTotal = Object.values(line.steps)
    .map((step) => step.mainPickFrom.qtyPicked + step.mainPickFrom.qtyRejected)
    .reduce((acc, qtyPickedOrRejected) => acc + qtyPickedOrRejected, 0);

  return {
    productId: line.productId,
    qtyToPickRemaining: line.qtyToPick - qtyPickedOrRejectedTotal,
    uom: line.uom,
    qtyRejectedReasons,
    catchWeightUom: line.catchWeightUOM,
  };
};

export default PickLineScanScreen;
