import React from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { getSteps } from '../../../../reducers/wfProcesses_status';
import { manufacturingStepScreenLocation } from '../../../../routes/manufacturing_issue';

import ButtonWithIndicator from '../../../../components/buttons/ButtonWithIndicator';
import ButtonQuantityProp from '../../../../components/buttons/ButtonQuantityProp';

const RawMaterialIssueLineScreen = () => {
  const {
    params: { applicationId, workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const history = useHistory();

  const onButtonClick = ({ stepId }) => {
    history.push(manufacturingStepScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId }));
  };

  const steps = useSelector((state) => getSteps(state, wfProcessId, activityId, lineId));

  return (
    <div className="pt-2 section">
      <div className="steps-container">
        {steps.length > 0 &&
          steps.map((stepItem) => {
            return (
              <div className="mt-3" key={stepItem.id}>
                <ButtonWithIndicator
                  caption={stepItem.locatorName}
                  completeStatus={stepItem.completeStatus}
                  onClick={() => onButtonClick({ stepId: stepItem.id })}
                >
                  <ButtonQuantityProp
                    qtyCurrent={stepItem.qtyIssued ?? 0}
                    qtyTarget={stepItem.qtyToIssue}
                    uom={stepItem.uom}
                    applicationId={applicationId}
                    subtypeId="issues"
                  />
                </ButtonWithIndicator>
              </div>
            );
          })}
      </div>
    </div>
  );
};

export default RawMaterialIssueLineScreen;
