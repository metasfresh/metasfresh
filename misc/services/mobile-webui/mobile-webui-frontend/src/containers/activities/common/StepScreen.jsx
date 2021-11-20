import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router';

import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import ScreenToaster from '../../../components/ScreenToaster';
import PickStepScreen from '../picking/PickStepScreen';
import DistributionStepScreen from '../distribution/DistributionStepScreen';

const getStepComponent = (appId) => {
  switch (appId) {
    case 'picking':
      return PickStepScreen;
    case 'distribution':
      return DistributionStepScreen;
    default:
      return null;
  }
};

class StepScreen extends PureComponent {
  // if `locatorId` exists, scanner will be configured for distributions.locator case
  onScanButtonClick = (locatorId) => {
    const { wfProcessId, activityId, lineId, stepId, dispatch, appId } = this.props;

    const location = `/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}/scanner/${appId}${
      locatorId ? `/${locatorId}` : ''
    }`;

    dispatch(push(location));
  };

  render() {
    const { appId } = this.props;
    const StepScreenComponent = getStepComponent(appId);

    return (
      <div className="pt-3 section picking-step-container">
        <div className="picking-step-details centered-text is-size-5">
          <StepScreenComponent {...this.props} onScanButtonClick={this.onScanButtonClick} />
          <ScreenToaster />
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId, stepId } = ownProps.match.params;
  const activity = selectWFProcessFromState(state, wfProcessId).activities[activityId];
  const stepProps = activity.dataStored.lines[lineId].steps[stepId];
  const appId = state.applications.activeApplication ? state.applications.activeApplication.id : null;

  return {
    wfProcessId,
    activityId,
    lineId,
    stepId,
    stepProps,
    appId,
    location: ownProps.location,
  };
};

StepScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  stepProps: PropTypes.object.isRequired,
  appId: PropTypes.string.isRequired,
  //
  // Actions
  dispatch: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps)(StepScreen));
