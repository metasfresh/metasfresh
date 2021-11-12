import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';

const mapStateToProps = (state, ownProps) => {
  const { wfProcessId, activityId, lineId, stepId } = ownProps;

  return {
    stepState: state.wfProcesses_status[wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId],
    appId: state.applications.activeApplication ? state.applications.activeApplication.id : null,
  };
};

function StepButton(WrappedComponent) {
  class Wrapped extends PureComponent {
    handleClick = () => {
      const { wfProcessId, activityId, lineId, stepId } = this.props;
      const { dispatch } = this.props;

      const location = `/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}`;
      dispatch(push(location));
    };

    render() {
      return <WrappedComponent {...this.props} onHandleClick={this.handleClick} />;
    }
  }

  Wrapped.propTypes = {
    //
    // Props
    wfProcessId: PropTypes.string.isRequired,
    activityId: PropTypes.string.isRequired,
    lineId: PropTypes.string.isRequired,
    stepId: PropTypes.string.isRequired,

    //
    // Actions
    dispatch: PropTypes.func.isRequired,
  };

  return connect(mapStateToProps)(Wrapped);
}

export default StepButton;
