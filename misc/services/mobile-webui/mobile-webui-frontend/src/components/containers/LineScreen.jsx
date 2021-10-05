import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { push } from 'connected-react-router';
import PropTypes from 'prop-types';

import PickProductsSteps from './PickProductsSteps';

class LineScreen extends PureComponent {
  render() {
    const { activityId, wfProcessId, lineIndex, lineProps } = this.props;

    if (lineProps) {
      const { steps } = lineProps;

      return (
        <div className="lines-screen-container">
          <PickProductsSteps steps={steps} activityId={activityId} wfProcessId={wfProcessId} lineIndex={lineIndex} />
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
    lineProps: targetActivity.length ? targetActivity[0].componentProps.lines[lineId] : null,
    activityId,
    wfProcessId,
    lineIndex: Number(lineId),
  };
};

LineScreen.propTypes = {
  push: PropTypes.func.isRequired,
  activityId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  lineIndex: PropTypes.number.isRequired,
  lineProps: PropTypes.any.isRequired,
};

export default withRouter(connect(mapStateToProps, { push })(LineScreen));
