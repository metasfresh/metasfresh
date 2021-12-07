import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import { withRouter } from 'react-router';

import { manufacturingStepScreenLocation } from '../../../routes/manufacturing';
import Indicator from '../../../components/Indicator';
import * as CompleteStatus from '../../../constants/CompleteStatus';

class RawMaterialIssueStepButton extends PureComponent {
  handleClick = () => {
    const { push } = this.props;
    const location = manufacturingStepScreenLocation(this.props);

    push(location);
  };

  render() {
    const {
      lineId,
      stepState: { locatorName, uom, qtyIssued, qtyToIssue, completeStatus },
    } = this.props;
    const qtyCurrent = qtyIssued || 0;

    return (
      <div className="mt-3">
        <button key={lineId} className="button is-outlined complete-btn pick-higher-btn" onClick={this.handleClick}>
          <div className="full-size-btn">
            <div className="left-btn-side" />
            <div className="caption-btn">
              <div className="rows">
                <div className="row is-full pl-5">{locatorName}</div>
                <div className="row is-full is-size-7">
                  <div className="picking-row-info">
                    <div className="picking-to-pick">{counterpart.translate('activities.mfg.issues.target')}:</div>
                    <div className="picking-row-qty">
                      {qtyToIssue} {uom}
                    </div>
                    <div className="picking-row-picking">{counterpart.translate('activities.mfg.issues.picked')}:</div>
                    <div className="picking-row-picked">
                      {qtyCurrent} {uom}
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div className="right-btn-side pt-4">
              <Indicator completeStatus={completeStatus || CompleteStatus.NOT_STARTED} />
            </div>
          </div>
        </button>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { wfProcessId, activityId, lineId, stepId } = ownProps;

  return {
    stepState: state.wfProcesses_status[wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId],
    appId: state.applications.activeApplication ? state.applications.activeApplication.id : null,
  };
};

RawMaterialIssueStepButton.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  stepState: PropTypes.object.isRequired,
  //
  // Actions
  push: PropTypes.func.isRequired,
};

// export default StepButton(RawMaterialIssueStepButton);
export default withRouter(connect(mapStateToProps, { push })(RawMaterialIssueStepButton));
