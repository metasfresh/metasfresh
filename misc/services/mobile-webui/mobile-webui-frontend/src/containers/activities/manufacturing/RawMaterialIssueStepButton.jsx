import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import StepButton from '../common/StepButton';
import Indicator from '../../../components/Indicator';
import * as CompleteStatus from '../../../constants/CompleteStatus';

class RawMaterialIssueStepButton extends PureComponent {
  render() {
    const { lineId, locatorName, uom, qtyIssued, qtyToIssue, completeStatus, onHandleClick } = this.props;
    const qtyCurrent = qtyIssued || 0;

    return (
      <div className="mt-3">
        <button key={lineId} className="button is-outlined complete-btn pick-higher-btn" onClick={onHandleClick}>
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

RawMaterialIssueStepButton.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  locatorName: PropTypes.string.isRequired,
  uom: PropTypes.string,
  qtyIssued: PropTypes.number,
  qtyToIssue: PropTypes.number.isRequired,
  onHandleClick: PropTypes.func.isRequired,
  completeStatus: PropTypes.string,
  //
  // Actions
  dispatch: PropTypes.func.isRequired,
};

export default StepButton(RawMaterialIssueStepButton);
