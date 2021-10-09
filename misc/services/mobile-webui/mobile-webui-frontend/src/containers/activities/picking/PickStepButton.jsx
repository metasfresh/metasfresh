import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import Indicator from '../../../components/Indicator';
class PickStepButton extends PureComponent {
  constructor(props) {
    super(props);
  }

  handleClick = () => {
    const { wfProcessId, activityId, lineId, stepId, push } = this.props;
    push(`/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}`);
  };

  goBackToPickingSteps = () => this.setState({ activePickingStep: false });

  render() {
    const { lineId, locatorName, productName, uom, pickstepState, qtyToPick } = this.props;
    const { qtyPicked, scannedHUBarcode } = pickstepState;

    return (
      <div>
        <button
          key={lineId}
          className="button is-outlined complete-btn pick-higher-btn"
          onClick={() => this.handleClick()}
        >
          <div className="full-size-btn">
            <div className="left-btn-side" />

            <div className="caption-btn">
              <div className="rows">
                <div className="row is-full">{productName}</div>
                <div className="row is-full is-size-7">
                  To Pick: <span className="has-text-weight-bold">{qtyToPick}</span> Quantity picked:{' '}
                  <span className="has-text-weight-bold">{qtyPicked}</span>
                </div>
                <div className="row is-full is-size-7">
                  UOM: {uom} Locator Name: {locatorName}
                </div>
              </div>
            </div>

            <div className="right-btn-side pt-4">
              <Indicator indicatorType={scannedHUBarcode ? 'complete' : 'incomplete'} />
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
    pickstepState: state.wfProcesses_status[wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId],
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
  pickstepState: PropTypes.object,
  //
  // Actions
  push: PropTypes.func.isRequired,
};

export default connect(mapStateToProps, { push })(PickStepButton);
