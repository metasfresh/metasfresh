import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router';

import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import ScreenToaster from '../../../components/ScreenToaster';
import PickStepScreen from '../picking/PickStepScreen';
import MaterialIssueStepScreen from '../manufacturing/RawMaterialIssueStepScreen';

const getStepComponent = (appId) => {
  switch (appId) {
    case 'picking':
      return PickStepScreen;
    case 'mfg':
      return MaterialIssueStepScreen;
    default:
      return null;
  }
};

class StepScreen extends PureComponent {
  onScanButtonClick = () => {
    const { wfProcessId, activityId, lineId, stepId, dispatch, appId, altStepId } = this.props;

    const altStepPath = altStepId ? `/altStepId/${altStepId}` : ``;
    const location = `/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}${altStepPath}/scanner/${appId}`;

    dispatch(push(location));
  };

  render() {
    const { appId } = this.props;
    const StepComponent = getStepComponent(appId);

    return (
      <div className="pt-3 section picking-step-container">
        <div className="picking-step-details centered-text is-size-5">
          <StepComponent {...this.props} onScanButtonClick={this.onScanButtonClick} />
          <ScreenToaster />
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId, stepId, altStepId } = ownProps.match.params;

  const activity = selectWFProcessFromState(state, wfProcessId).activities[activityId];
  const stepProps = activity.dataStored.lines[lineId].steps[stepId];
  const appId = state.applications.activeApplication ? state.applications.activeApplication.id : null;

  return {
    appId,
    wfProcessId,
    activityId,
    lineId,
    stepId,
    altStepId,
    stepProps,
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
  altStepId: PropTypes.string,
  stepProps: PropTypes.object.isRequired,
  appId: PropTypes.string.isRequired,
  //
  // Actions
  dispatch: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps)(StepScreen));
