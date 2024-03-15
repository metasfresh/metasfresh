import React, { useEffect } from 'react';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import { useHistory, useRouteMatch } from 'react-router-dom';

import { trl } from '../../../utils/translations';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { getLineById } from '../../../reducers/wfProcesses';

import PickStepButton from './PickStepButton';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { pickingLineScanScreenLocation } from '../../../routes/picking';
import { getQtyPickedOrRejectedTotalForLine } from '../../../utils/picking';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';

const PickLineScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const { caption, allowPickingAnyHU, steps, catchWeightUOM, qtyToPick, qtyPicked, uom } = useSelector(
    (state) => getPropsFromState({ state, wfProcessId, activityId, lineId }),
    shallowEqual
  );

  useHeaderUpdate({ url, caption, uom, qtyToPick, qtyPicked });

  const history = useHistory();
  const onScanButtonClick = () =>
    history.push(
      pickingLineScanScreenLocation({
        applicationId,
        wfProcessId,
        activityId,
        lineId,
      })
    );

  return (
    <div className="section pt-2">
      <div className="buttons">
        {allowPickingAnyHU && (
          <ButtonWithIndicator caption={trl('activities.picking.scanQRCode')} onClick={onScanButtonClick} />
        )}
        {steps.length > 0 &&
          steps.map((stepItem, idx) => {
            return (
              <PickStepButton
                key={idx}
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
      </div>
    </div>
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId }) => {
  const line = getLineById(state, wfProcessId, activityId, lineId);
  const stepsById = line != null && line.steps ? line.steps : {};

  return {
    caption: line?.caption,
    allowPickingAnyHU: line?.allowPickingAnyHU ?? false,
    steps: Object.values(stepsById),
    catchWeightUOM: line.catchWeightUOM,
    uom: line.uom,
    qtyToPick: line.qtyToPick,
    qtyPicked: getQtyPickedOrRejectedTotalForLine({ line }),
  };
};

const useHeaderUpdate = ({ url, caption, uom, qtyToPick, qtyPicked }) => {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: trl('activities.picking.PickingLine'),
        values: [
          {
            caption: trl('activities.picking.PickingLine'),
            value: caption,
            bold: true,
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
