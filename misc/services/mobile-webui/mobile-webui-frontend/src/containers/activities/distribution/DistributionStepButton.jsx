import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import { pushHeaderEntry } from '../../../actions/HeaderActions';
import ApplicationStepButton from '../StepButton';
import Indicator from '../../../components/Indicator';
import * as CompleteStatus from '../../../constants/CompleteStatus';

class DistributionStepButton extends PureComponent {
  handleClick = () => {
    const { locatorName } = this.props;
    const { dispatch, onHandleClick } = this.props;

    onHandleClick();
    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('general.Locator'),
            value: locatorName,
          },
        ],
      })
    );
  };

  render() {
    const {
      lineId,
      locatorName,
      uom,
      stepState: { qtyPicked, completeStatus },
      qtyToMove,
    } = this.props;

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
                <div className="row is-full pl-5">{locatorName}</div>
                <div className="row is-full is-size-7">
                  <div className="picking-row-info">
                    <div className="picking-to-pick">{counterpart.translate('activities.distribution.target')}:</div>
                    <div className="picking-row-qty">
                      {qtyToMove} {uom}
                    </div>
                    <div className="picking-row-picking">
                      {counterpart.translate('activities.distribution.picked')}:
                    </div>
                    <div className="picking-row-picked">
                      {qtyPicked} {uom}
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

DistributionStepButton.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  productName: PropTypes.string.isRequired,
  locatorName: PropTypes.string.isRequired,
  huBarcode: PropTypes.string,
  uom: PropTypes.string,
  qtyPicked: PropTypes.number,
  qtyToMove: PropTypes.number.isRequired,
  stepState: PropTypes.object,
  onHandleClick: PropTypes.func.isRequired,
  //
  // Actions
  dispatch: PropTypes.func.isRequired,
};

export default ApplicationStepButton(DistributionStepButton);
