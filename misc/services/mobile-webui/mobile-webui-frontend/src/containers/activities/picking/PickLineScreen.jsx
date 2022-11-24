import React, { useEffect } from 'react';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import { useRouteMatch } from 'react-router-dom';

import { trl } from '../../../utils/translations';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { getLineById } from '../../../reducers/wfProcesses';

import PickStepButton from './PickStepButton';

const PickLineScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const { caption, steps } = useSelector(
    (state) => getPropsFromState({ state, wfProcessId, activityId, lineId }),
    shallowEqual
  );

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
        ],
      })
    );
  }, [url, caption]);

  return (
    <div className="section pt-2">
      <div className="buttons">
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
  const lineProps = getLineById(state, wfProcessId, activityId, lineId);
  const stepsById = lineProps != null && lineProps.steps ? lineProps.steps : {};

  return {
    caption: lineProps?.caption,
    steps: Object.values(stepsById),
  };
};

export default PickLineScreen;
