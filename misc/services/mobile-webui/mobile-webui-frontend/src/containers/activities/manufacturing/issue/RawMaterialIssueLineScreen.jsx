import React from 'react';
import PropTypes from 'prop-types';

import StepButton from './RawMaterialIssueStepButton';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { selectWFProcessFromState } from '../../../../reducers/wfProcesses_status';

const RawMaterialIssueLineScreen = (props) => {
  const { wfProcessId, activityId, lineId, steps } = props;

  return (
    <div className="pt-2 section lines-screen-container">
      <div className="steps-container">
        {steps.length > 0 &&
          steps.map((stepItem, idx) => {
            return (
              <StepButton
                key={idx}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineId={lineId}
                stepId={stepItem.id}
                locatorName={stepItem.productName}
                {...stepItem}
              />
            );
          })}
      </div>
    </div>
  );
};

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId } = ownProps.match.params;
  const wfProcess = selectWFProcessFromState(state, wfProcessId);
  const activity = wfProcess && wfProcess.activities ? wfProcess.activities[activityId] : null;

  const lineProps = activity != null ? activity.dataStored.lines[lineId] : null;
  const stepsById = lineProps != null && lineProps.steps ? lineProps.steps : {};

  return {
    wfProcessId,
    activityId,
    lineId,
    steps: Object.values(stepsById),
  };
};

RawMaterialIssueLineScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  steps: PropTypes.array.isRequired,
};

export default withRouter(connect(mapStateToProps)(RawMaterialIssueLineScreen));
