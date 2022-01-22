import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import counterpart from 'counterpart';

import DistributionStepButton from './DistributionStepButton';
import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';
import { distributionLineScreenLocation } from '../../../routes/distribution';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

class DistributionLineScreen extends PureComponent {
  componentDidMount() {
    const {
      pushHeaderEntry,
      lineProps: { caption },
      wfProcessId,
      activityId,
      lineId,
    } = this.props;
    const location = distributionLineScreenLocation({ wfProcessId, activityId, lineId });

    pushHeaderEntry({
      location,
      values: [
        {
          caption: counterpart.translate('activities.distribution.DistributionLine'),
          value: caption,
          bold: true,
        },
      ],
    });
  }

  render() {
    const { wfProcessId, activityId, lineId, steps } = this.props;

    return (
      <div className="section pt-2">
        <div className="steps-container">
          {steps.length > 0 &&
            steps.map((stepItem, idx) => {
              return (
                <DistributionStepButton
                  key={idx}
                  wfProcessId={wfProcessId}
                  activityId={activityId}
                  lineId={lineId}
                  stepId={stepItem.id}
                  productName={stepItem.productName}
                  pickFromLocator={stepItem.pickFromLocator}
                  pickFromHU={stepItem.pickFromHU}
                  uom={stepItem.uom}
                  qtyPicked={stepItem.qtyPicked}
                  qtyToMove={stepItem.qtyToMove}
                  completeStatus={stepItem.completeStatus}
                />
              );
            })}
        </div>
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

DistributionLineScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  steps: PropTypes.array.isRequired,
  lineProps: PropTypes.object.isRequired,

  // Actions
  pushHeaderEntry: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { pushHeaderEntry })(DistributionLineScreen));
