import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import PropTypes from 'prop-types';

import PickStepButtonsGroup from './PickStepButtonsGroup';
import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';

class PickLineScreen extends PureComponent {
  render() {
    const { wfProcessId, activityId, lineId, stepsById } = this.props;

    return (
      <div className="pt-2 section lines-screen-container">
        <PickStepButtonsGroup
          wfProcessId={wfProcessId}
          activityId={activityId}
          lineId={lineId}
          steps={Object.values(stepsById)}
        />
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId } = ownProps.match.params;
  const wfProcess = selectWFProcessFromState(state, wfProcessId);
  const activity = wfProcess.activities[activityId];
  const lineProps = activity.componentProps.lines[lineId];
  const stepsById = lineProps.steps;

  return {
    wfProcessId,
    activityId,
    lineId,
    stepsById,
  };
};

PickLineScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepsById: PropTypes.object.isRequired,
};

export default withRouter(connect(mapStateToProps, null)(PickLineScreen));
