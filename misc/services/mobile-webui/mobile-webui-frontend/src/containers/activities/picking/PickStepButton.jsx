import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import counterpart from 'counterpart';

import { pushHeaderEntry } from '../../../actions/HeaderActions';
import Indicator from '../../../components/Indicator';
import * as CompleteStatus from '../../../constants/CompleteStatus';

class PickStepButton extends PureComponent {
  handleClick = () => {
    const { wfProcessId, activityId, lineId, stepId, locatorName } = this.props;
    const { push, pushHeaderEntry } = this.props;

    const location = `/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}`;
    push(location);
    pushHeaderEntry({
      location,
      values: [
        {
          caption: counterpart.translate('general.Locator'),
          value: locatorName,
        },
      ],
    });
  };

  goBackToPickingSteps = () => this.setState({ activePickingStep: false });

  render() {
    const {
      lineId,
      locatorName,
      uom,
      pickStepState: { qtyPicked, completeStatus },
      qtyToPick,
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
                    <div className="picking-to-pick">{counterpart.translate('activities.picking.toPick')}:</div>
                    <div className="picking-row-qty">
                      {qtyToPick} {uom}
                    </div>
                    <div className="picking-row-picking">{counterpart.translate('activities.picking.picked')}:</div>
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

const mapStateToProps = (state, ownProps) => {
  const { wfProcessId, activityId, lineId, stepId } = ownProps;

  return {
    pickStepState: state.wfProcesses_status[wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId],
  };
};

PickStepButton.propTypes = {
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
  qtyToPick: PropTypes.number.isRequired,
  pickStepState: PropTypes.object,
  //
  // Actions
  push: PropTypes.func.isRequired,
  pushHeaderEntry: PropTypes.func.isRequired,
};

export default connect(mapStateToProps, { push, pushHeaderEntry })(PickStepButton);
