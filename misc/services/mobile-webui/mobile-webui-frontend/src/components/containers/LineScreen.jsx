import React, { Component } from 'react';
import PickProductsSteps from './PickProductsSteps';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import PropTypes from 'prop-types';
class LineScreen extends Component {
  render() {
    const { activityId, wfProcessId, lineIndex } = this.props;
    const { caption, steps } = this.props.lineProps;
    return (
      <div className="lines-screen-container">
        <div className="has-text-centered">{caption}</div>
        <PickProductsSteps steps={steps} activityId={activityId} wfProcessId={wfProcessId} lineIndex={lineIndex} />
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId } = ownProps.match.params;

  const targetActivity = state.wfProcesses[wfProcessId].activities.filter(
    (activity) => activity.activityId === activityId
  );
  return {
    lineProps: targetActivity[0].componentProps.lines[lineId],
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
  lineProps: PropTypes.object.isRequired,
};

export default connect(mapStateToProps, { push })(LineScreen);
