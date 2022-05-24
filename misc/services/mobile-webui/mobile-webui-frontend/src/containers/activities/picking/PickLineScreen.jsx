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
  const activity = wfProcess && wfProcess.activities ? wfProcess.activities[activityId] : null;

  const lineProps = activity != null ? activity.componentProps.lines[lineId] : null;
  const stepsById = lineProps != null ? lineProps.steps : {};

  // TODO: handle the case when we didn't find the wfProcess or activity or line
  // usually that happens when the workflow process is no longer in the state because:
  // * user refreshed some old link
  // * for some reason it was taken out from backend side
  // Possible solutions:
  // * have a flag here to indicate to the component that we deal with a not found case
  // * component notifies the user using a nice toast and then forwards him back to launchers
  //
  // NOTE to dev: please please check the other Screens where we could have this case and pls handle it

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
