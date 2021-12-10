import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import PropTypes from 'prop-types';

import RawMaterialIssueLineScreen from '../manufacturing/RawMaterialIssueLineScreen';
import MaterialReceiptLineScreen from '../manufacturing/MaterialReceiptLineScreen';

import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';

const getLineComponent = (componentType) => {
  if (componentType === 'manufacturing/rawMaterialsIssue') {
    return RawMaterialIssueLineScreen;
  } else if (componentType === 'manufacturing/materialReceipt') {
    return MaterialReceiptLineScreen;
  }
};

class LineScreen extends PureComponent {
  render() {
    const { componentType } = this.props;
    const Component = getLineComponent(componentType);

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

  return {
    wfProcessId,
    activityId,
    lineId,
    steps: Object.values(stepsById),
    componentType: activity.componentType,
    lineProps,
  };
};

LineScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  steps: PropTypes.array.isRequired,
  componentType: PropTypes.string.isRequired,
  lineProps: PropTypes.object.isRequired,
};

export default withRouter(connect(mapStateToProps)(LineScreen));
