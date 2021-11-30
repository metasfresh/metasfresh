import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import PropTypes from 'prop-types';

import PickLineScreen from '../picking/PickLineScreen';
import RawMaterialIssueLineScreen from '../manufacturing/RawMaterialIssueLineScreen';
import MaterialReceiptLineScreen from '../manufacturing/MaterialReceiptLineScreen';

import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';

const getLineComponent = (appId, componentType) => {
  switch (appId) {
    case 'picking':
      return PickLineScreen;
    case 'mfg':
      if (componentType === 'manufacturing/rawMaterialsIssue') {
        return RawMaterialIssueLineScreen;
      } else if (componentType === 'manufacturing/materialReceipt') {
        return MaterialReceiptLineScreen;
      }
      return null;
    default:
      return null;
  }
};

class LineScreen extends PureComponent {
  render() {
    const { appId, componentType } = this.props;
    const Component = getLineComponent(appId, componentType);

    return (
      <div className="pt-2 section lines-screen-container">
        <Component {...this.props} />
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId } = ownProps.match.params;
  const wfProcess = selectWFProcessFromState(state, wfProcessId);
  const activity = wfProcess && wfProcess.activities ? wfProcess.activities[activityId] : null;

  const lineProps = activity != null ? activity.dataStored.lines[lineId] : null;
  const stepsById = lineProps != null && lineProps.steps ? lineProps.steps : {};

  const appId = state.applications.activeApplication ? state.applications.activeApplication.id : null;

  // TODO: handle the case when we didn't find the wfProcess or activity or line
  // usually that happens when the workflow process is no longer in the state because:
  // * user refreshed some old link
  // * for some reason it was taken out from backend side
  // Possible solutions:
  // * have a flag here to indicate to the component that we deal with a not found case
  // * component notifies the user using a nice toast and then forwards him back to launchers
  //
  // NOTE to dev: please please check the other Screens where we could have this case and pls handle it

  return {
    wfProcessId,
    activityId,
    lineId,
    steps: Object.values(stepsById),
    componentType: activity.componentType,
    lineProps,
    appId,
  };
};

LineScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  steps: PropTypes.array.isRequired,
  appId: PropTypes.string.isRequired,
  componentType: PropTypes.string.isRequired,
  lineProps: PropTypes.object.isRequired,
};

export default withRouter(connect(mapStateToProps)(LineScreen));
