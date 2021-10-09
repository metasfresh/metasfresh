import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { push } from 'connected-react-router';
import PropTypes from 'prop-types';

import PickProductsSteps from './PickProductsSteps';

class LineScreen extends PureComponent {
  render() {
    const { activityId, wfProcessId, lineId, lineProps } = this.props;

    if (lineProps) {
      const { steps } = lineProps;

      return (
        <div className="pt-2 section lines-screen-container">
          <PickProductsSteps steps={steps} activityId={activityId} wfProcessId={wfProcessId} lineId={lineId} />
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

LineScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  lineProps: PropTypes.object.isRequired,
  //
  // Actions
  push: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { push })(LineScreen));
