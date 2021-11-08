import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
class PickAlternatives extends Component {
  renderAlternatives = ({ diffToPick }) => {
    const { pickFromAlternativeIds, pickFromAlternatives } = this.props;

    let totalQty = 0;

    const alternativeHUs = pickFromAlternatives.reduce((accumulator, item) => {
      if (pickFromAlternativeIds.includes(item.id) && totalQty < diffToPick && diffToPick) {
        accumulator.push(item);
        totalQty += item.qtyAvailable;
      }
      return accumulator;
    }, []);

    console.log('DIFFtoPick:', diffToPick);
    console.log('ALTHUs', alternativeHUs);

    // calculate here - filter to achieve the amount
    return <div>DIFF: {diffToPick}</div>;
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
  stepId: PropTypes.string.isRequired,
  qtyPicked: PropTypes.number.isRequired,
  qtyToPick: PropTypes.number.isRequired,
  pickFromAlternativeIds: PropTypes.array,
  pickFromAlternatives: PropTypes.array,
};

const mapStateToProps = (state, ownProps) => {
  const { wfProcessId, activityId } = ownProps;

  return {
    pickFromAlternatives:
      state.wfProcesses_status[wfProcessId].activities[activityId].componentProps.pickFromAlternatives,
  };
};

export default connect(mapStateToProps, null)(PickAlternatives);
