import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
class PickAlternatives extends Component {
  render() {
    return <div>PickAlternatives</div>;
  }
}

PickAlternatives.propTypes = {
  stepId: PropTypes.string.isRequired,
};

const mapStateToProps = (state, ownProps) => {
  const { wfProcessId, activityId } = ownProps;

  return {
    pickFromAlternatives:
      state.wfProcesses_status[wfProcessId].activities[activityId].componentProps.pickFromAlternatives,
  };
};

export default connect(mapStateToProps, null)(PickAlternatives);
