import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import { pushHeaderEntry } from '../../../actions/HeaderActions';
import StepButton from '../common/StepButton';
import Indicator from '../../../components/Indicator';
import * as CompleteStatus from '../../../constants/CompleteStatus';

class RawMaterialIssueStepButton extends PureComponent {
  handleClick = () => {
    const { locatorName, location, dispatch, onHandleClick } = this.props;

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
    const { lineId, productName, uom, qtyIssued, qtyToIssue, completeStatus } = this.props;

    return (
      <div className="mt-3">
        <button key={lineId} className="button is-outlined complete-btn pick-higher-btn" onClick={this.handleClick}>
          <div className="full-size-btn">
            <div className="left-btn-side" />
            <div className="caption-btn">
              <div className="rows">
                <div className="row is-full pl-5">{productName}</div>
                <div className="row is-full is-size-7">
                  <div className="picking-row-info">
                    <div className="picking-to-pick">{counterpart.translate('activities.mfg.issues.toIssue')}:</div>
                    <div className="picking-row-qty">
                      {qtyToIssue} {uom}
                    </div>
                    <div className="picking-row-picking">{counterpart.translate('activities.mfg.issues.issued')}:</div>
                    <div className="picking-row-picked">
                      {qtyIssued} {uom}
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
  productName: PropTypes.string.isRequired,
  locatorName: PropTypes.string.isRequired,
  uom: PropTypes.string,
  qtyIssued: PropTypes.number,
  qtyToIssue: PropTypes.number.isRequired,
  location: PropTypes.object,
  onHandleClick: PropTypes.func.isRequired,
  completeStatus: PropTypes.string,
  //
  // Actions
  dispatch: PropTypes.func.isRequired,
};

export default StepButton(RawMaterialIssueStepButton);
