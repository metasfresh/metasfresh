import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import PickAltStepButton from './PickAltStepButton';
class PickAlternatives extends Component {
  renderAlternatives = ({ diffToPick }) => {
    // const { pickFromAlternativeIds, pickFromAlternatives, altSteps } = this.props;

    const { altSteps, wfProcessId, activityId, lineId, stepId } = this.props;
    const { genSteps } = altSteps;

    console.log('G:', genSteps);
    // let totalQty = 0;

    // const alternativeHUs = pickFromAlternatives.reduce((accumulator, item) => {
    //   if (pickFromAlternativeIds.includes(item.id) && totalQty < diffToPick && diffToPick) {
    //     accumulator.push(item);
    //     totalQty += item.qtyAvailable;
    //   }
    //   return accumulator;
    // }, []);

    // console.log('DIFFtoPick:', diffToPick);
    // console.log('ALTHUs', alternativeHUs);

    // calculate here - filter to achieve the amount
    return (
      <div>
        DIFF: {diffToPick}
        {Object.keys(genSteps).length > 0 &&
          Object.keys(genSteps).map((altStepKey) => {
            return (
              <PickAltStepButton
                key={genSteps[altStepKey].id}
                wfProcessId={wfProcessId}
                activityId={activityId}
                lineId={lineId}
                stepId={stepId}
                altStepId={genSteps[altStepKey].id}
                {...genSteps[altStepKey]}
              />
            );
          })}
      </div>
    );
  };

  render() {
    const { qtyPicked, qtyToPick } = this.props;
    const diffToPick = qtyToPick - qtyPicked;

    return (
      <div>
        PickAlternatives remaining to pick {diffToPick}
        {this.renderAlternatives({ diffToPick })}
      </div>
    );
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
  altSteps: PropTypes.object,
};

const mapStateToProps = (state, ownProps) => {
  const { wfProcessId, activityId } = ownProps;

  return {
    pickFromAlternatives: state.wfProcesses_status[wfProcessId].activities[activityId].dataStored.pickFromAlternatives,
  };
};

export default connect(mapStateToProps, null)(PickAlternatives);
