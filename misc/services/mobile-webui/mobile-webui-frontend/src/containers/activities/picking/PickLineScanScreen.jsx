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

  const { qtyToPick, uom, qtyRejectedReasons, catchWeight, catchWeightUom } = useSelector(
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
    const huQRCode = parseQRCodeString(scannedBarcode);
    return { huQRCode };
  };
  const onResult = ({
    qty = 0,
    qtyRejected,
    reason = null,
    scannedBarcode = null,
    catchWeight = null,
    catchWeightUom = null,
    ...others
  }) => {
    console.log('onResult', { qty, reason, scannedBarcode, catchWeight, catchWeightUom, ...others });

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
    })
      .then((wfProcess) => {
        dispatch(updateWFProcess({ wfProcess }));
        history.go(-1); // go to picking line screen
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  return (
    <ScanHUAndGetQtyComponent
      qtyCaption={trl('general.QtyToPick')}
      qtyMax={qtyToPick}
      qtyTarget={qtyToPick}
      uom={uom}
      qtyRejectedReasons={qtyRejectedReasons}
      catchWeight={catchWeight}
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

  return {
    qtyToPick: line.qtyToPick,
    uom: line.uom,
    qtyRejectedReasons,
    //catchWeight:,
    catchWeightUom: 'Kg', // FIXME HARDCODED
  };
};

export default PickLineScanScreen;
