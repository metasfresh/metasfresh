import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import PropTypes from 'prop-types';

import PickStepButtonsGroup from './PickStepButtonsGroup';

class PickLineScreen extends PureComponent {
  render() {
    const { activityId, wfProcessId, lineId, lineProps } = this.props;

    if (lineProps) {
      const { steps } = lineProps;

      return (
        <div className="pt-2 section lines-screen-container">
          <PickStepButtonsGroup steps={steps} activityId={activityId} wfProcessId={wfProcessId} lineId={lineId} />
        </div>
      );
    }

    return null;
  }
}

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId } = ownProps.match.params;
  const workflow = state.wfProcesses[wfProcessId];
  const activities = workflow ? workflow.activities : [];
  const targetActivity = activities.filter((activity) => activity.activityId === activityId);

  return {
    wfProcessId,
    activityId,
    lineId,
    lineProps: targetActivity.length ? targetActivity[0].componentProps.lines[lineId] : null,
  };
};

PickLineScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  lineProps: PropTypes.object.isRequired,
};

export default withRouter(connect(mapStateToProps, null)(PickLineScreen));
