import React, { useEffect } from 'react';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import { updateHeaderEntry } from '../../../actions/HeaderActions';
import { getActivityById, getLineByIdFromActivity } from '../../../reducers/wfProcesses';

import PickStepButton from './PickStepButton';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { pickingLineScanScreenLocation } from '../../../routes/picking';
import {
  getCurrentPickFromHUQRCode,
  getQtyPickedOrRejectedTotalForLine,
  getQtyToPickForLine,
  getQtyToPickRemainingForLine,
  isAllowPickingAnyHUForLine,
} from '../../../utils/picking';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';
import { closePickingJobLine, openPickingJobLine } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { getWFProcessScreenLocation } from '../../../routes/workflow_locations';

const PickLineScreen = () => {
  const { history, url, applicationId, wfProcessId, activityId, lineId } = useScreenDefinition({
    screenId: 'PickLineScreen',
    back: getWFProcessScreenLocation,
  });
  const dispatch = useDispatch();

  const {
    caption,
    pickFromHUQRCode,
    allowPickingAnyHU,
    steps,
    pickingUnit,
    packingItemName,
    catchWeightUOM,
    qtyToPick,
    qtyPicked,
    qtyToPickRemaining,
    uom,
    manuallyClosed,
  } = useSelector((state) => getPropsFromState({ state, wfProcessId, activityId, lineId }), shallowEqual);

  useHeaderUpdate({ url, caption, uom, pickingUnit, packingItemName, qtyToPick, qtyPicked });

  const onScanButtonClick = () =>
    history.push(
      pickingLineScanScreenLocation({
        applicationId,
        wfProcessId,
        activityId,
        lineId,
      })
    );

  const onPickButtonClick = () => {
    history.push(
      pickingLineScanScreenLocation({
        applicationId,
        wfProcessId,
        activityId,
        lineId,
        qrCode: pickFromHUQRCode,
      })
    );
  };

  const onClose = () => {
    closePickingJobLine({ wfProcessId, lineId })
      .then((wfProcess) => {
        dispatch(updateWFProcess({ wfProcess }));
      })
      .then(() => history.goBack); // go back to Picking Job
  };

  const onReOpen = () => {
    openPickingJobLine({ wfProcessId, lineId }).then((wfProcess) => {
      dispatch(updateWFProcess({ wfProcess }));
    });
  };

  const isShowScanQRCodeButton = !manuallyClosed && allowPickingAnyHU && pickFromHUQRCode == null;
  const isShowPickButton = !manuallyClosed && pickFromHUQRCode != null;

  return (
    <div className="section pt-2">
      <div className="buttons">
        {isShowScanQRCodeButton && (
          <ButtonWithIndicator captionKey="activities.picking.scanQRCode" onClick={onScanButtonClick} />
        )}
        {isShowPickButton && <ButtonWithIndicator captionKey="activities.picking.PickHU" onClick={onPickButtonClick} />}
        {steps.length > 0 &&
          steps.map((stepItem, idx) => {
            return (
              <PickStepButton
                id={`step-${idx}-button`}
                key={idx}
                disabled={manuallyClosed}
                applicationId={applicationId}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineId={lineId}
                stepId={stepItem.pickingStepId}
                pickFromAlternatives={stepItem.pickFromAlternatives}
                catchWeightUOM={catchWeightUOM}
                //
                uom={stepItem.uom}
                qtyToPick={stepItem.qtyToPick}
                pickFrom={stepItem.mainPickFrom}
              />
            );
          })}
        {!manuallyClosed && qtyToPickRemaining > 0 && (
          <ButtonWithIndicator captionKey="general.closeText" onClick={onClose} />
        )}
        {manuallyClosed && <ButtonWithIndicator captionKey="general.reOpenText" onClick={onReOpen} />}
      </div>
    </div>
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const line = getLineByIdFromActivity(activity, lineId);
  const stepsById = line?.steps ?? {};

  return {
    caption: line?.caption,
    pickFromHUQRCode: getCurrentPickFromHUQRCode({ activity }),
    allowPickingAnyHU: isAllowPickingAnyHUForLine({ line }),
    steps: Object.values(stepsById),
    pickingUnit: line?.pickingUnit,
    packingItemName: line?.packingItemName,
    catchWeightUOM: line?.catchWeightUOM,
    uom: line?.uom,
    qtyToPick: getQtyToPickForLine({ line }),
    qtyPicked: getQtyPickedOrRejectedTotalForLine({ line }),
    qtyToPickRemaining: getQtyToPickRemainingForLine({ line }),
    manuallyClosed: line?.manuallyClosed,
  };
};

export const useHeaderUpdate = ({ url, caption, pickingUnit, packingItemName, uom, qtyToPick, qtyPicked }) => {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      updateHeaderEntry({
        location: url,
        caption: trl('activities.picking.PickingLine'),
        values: [
          {
            caption: trl('activities.picking.PickingLine'),
            value: caption,
            bold: true,
          },
          {
            caption: trl('general.PackingItemName'),
            value: packingItemName,
            hidden: !(pickingUnit === 'TU' && packingItemName),
          },
          {
            caption: trl('activities.picking.target'),
            value: formatQtyToHumanReadableStr({ qty: qtyToPick, uom }),
          },
          {
            caption: trl('activities.picking.picked'),
            value: formatQtyToHumanReadableStr({ qty: qtyPicked, uom }),
          },
        ],
      })
    );
  }, [url, caption]);
};

export default PickLineScreen;
