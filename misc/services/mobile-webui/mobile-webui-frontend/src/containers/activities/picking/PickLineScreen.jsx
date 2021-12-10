import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import counterpart from 'counterpart';

import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { pickingLineScreenLocation } from '../../../routes/picking';
import { selectWFProcessFromState } from '../../../reducers/wfProcesses_status';

import PickStepButton from './PickStepButton';

class PickLineScreen extends PureComponent {
  componentDidMount() {
    const {
      pushHeaderEntry,
      lineProps: { caption },
      wfProcessId,
      activityId,
      lineId,
    } = this.props;
    const location = pickingLineScreenLocation({ wfProcessId, activityId, lineId });

    pushHeaderEntry({
      location,
      values: [
        {
          caption: counterpart.translate('activities.picking.PickingLine'),
          value: caption,
          bold: true,
        },
      ],
    });
  }

  render() {
    const { wfProcessId, activityId, lineId, steps } = this.props;

    return (
      <div className="pt-2 section lines-screen-container">
        <div className="steps-container">
          {steps.length > 0 &&
            steps.map((stepItem, idx) => {
              return (
                <PickStepButton
                  key={idx}
                  wfProcessId={wfProcessId}
                  activityId={activityId}
                  lineId={lineId}
                  stepId={stepItem.pickingStepId}
                  qtyToPick={stepItem.qtyToPick}
                  pickFrom={stepItem.mainPickFrom}
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

PickLineScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  steps: PropTypes.array.isRequired,
  lineProps: PropTypes.object.isRequired,
  pushHeaderEntry: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { pushHeaderEntry })(PickLineScreen));
