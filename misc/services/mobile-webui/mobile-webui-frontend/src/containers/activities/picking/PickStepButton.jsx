import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import StepButton from '../common/StepButton';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import Indicator from '../../../components/Indicator';
import PickAlternatives from './PickAlternatives';
import { computePickFromStatus } from '../../../reducers/wfProcesses_status/picking';

class PickStepButton extends PureComponent {
  handleClick = () => {
    const { pickFrom } = this.props;
    const { dispatch, onHandleClick } = this.props;

    onHandleClick();

    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('general.Locator'),
            value: pickFrom.locatorName,
          },
        ],
      })
    );
  };

  render() {
    const {
      appId,
      wfProcessId,
      activityId,
      lineId,
      stepId,
      //
      pickFromAlternatives,
      uom,
      qtyToPick,
      pickFrom,
    } = this.props;

    const isAlternative = !pickFromAlternatives;
    const completeStatus = computePickFromStatus(pickFrom);

    return (
      <div className="mt-3">
        <button
          key={lineId}
          className="button is-outlined complete-btn pick-higher-btn"
          onClick={() => this.handleClick()}
        >
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
        {pickFromAlternatives && (
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

PickStepButton.propTypes = {
  //
  // Props
  appId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  //
  pickFromAlternatives: PropTypes.object,
  uom: PropTypes.string.isRequired,
  qtyToPick: PropTypes.number.isRequired,
  pickFrom: PropTypes.object.isRequired,
  //
  // Actions/Functions
  dispatch: PropTypes.func.isRequired,
  onHandleClick: PropTypes.func.isRequired,
};

export default StepButton(PickStepButton);
