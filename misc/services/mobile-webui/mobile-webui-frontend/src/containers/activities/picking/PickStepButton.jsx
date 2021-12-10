import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import { withRouter } from 'react-router';

import { pickingStepScreenLocation } from '../../../routes/picking';
import Indicator from '../../../components/Indicator';
import PickAlternatives from './PickAlternatives';
import { computePickFromStatus } from '../../../reducers/wfProcesses_status/picking';

class PickStepButton extends PureComponent {
  handleClick = () => {
    const { push, wfProcessId, activityId, lineId, stepId, altStepId } = this.props;
    const location = pickingStepScreenLocation({ wfProcessId, activityId, lineId, stepId, altStepId });

    push(location);
  };

  render() {
    const {
      appId,
      wfProcessId,
      activityId,
      lineId,
      stepId,
      altStepId,
      //
      stepState: { pickFromAlternatives, uom },
      qtyToPick,
      pickFrom,
    } = this.props;

    const isAlternative = altStepId;
    const completeStatus = computePickFromStatus(pickFrom);

    return (
      <div className="mt-3">
        <button key={lineId} className="button is-outlined complete-btn pick-higher-btn" onClick={this.handleClick}>
          <div className="full-size-btn">
            <div className="left-btn-side" />
            <div className="caption-btn">
              <div className="rows">
                <div className="row is-full pl-5">
                  {isAlternative ? 'ALT:' : ''}
                  {pickFrom.locatorName}
                </div>
                <div className="row is-full is-size-7">
                  <div className="picking-row-info">
                    <div className="picking-to-pick">{counterpart.translate('activities.picking.target')}:</div>
                    <div className="picking-row-qty">
                      {qtyToPick} {uom}
                    </div>
                    <div className="picking-row-picking">{counterpart.translate('activities.picking.picked')}:</div>
                    <div className="picking-row-picked">
                      {pickFrom.qtyPicked} {uom}
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div className="right-btn-side pt-4">
              <Indicator completeStatus={completeStatus} />
            </div>
          </div>
        </button>
        {pickFromAlternatives && !altStepId && (
          <PickAlternatives
            appId={appId}
            wfProcessId={wfProcessId}
            activityId={activityId}
            lineId={lineId}
            stepId={stepId}
            pickFromAlternatives={pickFromAlternatives}
            uom={uom}
          />
        )}
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

PickStepButton.propTypes = {
  //
  // Props
  appId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  pickFrom: PropTypes.object.isRequired,
  qtyToPick: PropTypes.number.isRequired,
  altStepId: PropTypes.string,
  //
  stepState: PropTypes.object.isRequired,
  //
  // Actions/Functions
  push: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { push })(PickStepButton));
