import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import PickStepButton from './PickStepButton';
class PickAlternatives extends Component {
  renderAlternatives = () => {
    const { genSteps, wfProcessId, activityId, lineId, stepId } = this.props;

    return (
      <div>
        {Object.keys(genSteps).length > 0 &&
          Object.keys(genSteps).map((altStepKey) => {
            return (
              <PickStepButton
                key={genSteps[altStepKey].id}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineId={lineId}
                stepId={stepId}
                altStepId={genSteps[altStepKey].id}
                productName=""
                qtyToPick={genSteps[altStepKey].qtyAvailable}
                locatorName={genSteps[altStepKey].locatorName}
                uom={genSteps[altStepKey].uom}
                qtyPicked={genSteps[altStepKey].qtyPicked}
              />
            );
          })}
      </div>
    );
  };

  render() {
    return <div>{this.renderAlternatives()}</div>;
  }
}

PickAlternatives.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  qtyPicked: PropTypes.number.isRequired,
  qtyToPick: PropTypes.number.isRequired,
  pickFromAlternativeIds: PropTypes.array,
  pickFromAlternatives: PropTypes.array,
  genSteps: PropTypes.object,
};

const mapStateToProps = (state, ownProps) => {
  const { wfProcessId, activityId, lineId, stepId } = ownProps;

  return {
    pickFromAlternatives: state.wfProcesses_status[wfProcessId].activities[activityId].dataStored.pickFromAlternatives,
    genSteps:
      state.wfProcesses_status[wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId].altSteps
        .genSteps,
  };
};

export default connect(mapStateToProps, null)(PickAlternatives);
