import React, { useEffect } from 'react';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import { useRouteMatch } from 'react-router-dom';

import { trl } from '../../../utils/translations';
import { getLineById, getStepsArrayFromLine } from '../../../reducers/wfProcesses';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

import DistributionStepButton from './DistributionStepButton';

const DistributionLineScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const { lineCaption, steps } = useSelector(
    (state) => getPropsFromState({ state, wfProcessId, activityId, lineId }),
    shallowEqual
  );

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        values: [
          {
            caption: trl('activities.distribution.DistributionLine'),
            value: lineCaption,
            bold: true,
          },
        ],
      })
    );
  }, []);

  return (
    <div className="section pt-2">
      {steps.length > 0 &&
        steps.map((stepItem, idx) => {
          return (
            <DistributionStepButton
              key={idx}
              applicationId={applicationId}
              wfProcessId={wfProcessId}
              activityId={activityId}
              lineId={lineId}
              stepId={stepItem.id}
              productName={stepItem.productName}
              pickFromHU={stepItem.pickFromHU}
              uom={stepItem.uom}
              qtyPicked={stepItem.qtyPicked}
              qtyToMove={stepItem.qtyToMove}
              completeStatus={stepItem.completeStatus}
            />
          );
        })}
    </div>
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId }) => {
  const line = getLineById(state, wfProcessId, activityId, lineId);
  return {
    lineCaption: line?.caption,
    steps: getStepsArrayFromLine(line),
  };
};

export default DistributionLineScreen;
