import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { updatePickingStepQty } from '../actions/PickingActions';
import { push } from 'connected-react-router';
class PickStep extends PureComponent {
  constructor(props) {
    super(props);
  }

  handleClick = () => {
    const { wfProcessId, activityId, lineIndex, stepId, push } = this.props;
    push(`/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineIndex}/stepId/${stepId}`);
  };

  goBackToPickingSteps = () => this.setState({ activePickingStep: false });

  render() {
    const { id, locatorName, productName, uom, pickstepState, qtyToPick } = this.props;
    const { qtyPicked } = pickstepState;
    return (
      <div>
        <button
          key={id}
          className="button is-outlined complete-btn pick-higher-btn"
          onClick={() => this.handleClick(id)}
        >
          <div className="rows">
            <div className="row is-full">Product: {productName}</div>
            <div className="row is-full is-size-7">
              To Pick: <span className="has-text-weight-bold">{qtyToPick}</span> Quantity picked:{' '}
              <span className="has-text-weight-bold">{qtyPicked}</span>
            </div>
            <div className="row is-full is-size-7">
              UOM: {uom} Locator Name: {locatorName}
            </div>
          </div>
        </button>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { wfProcessId, activityId, lineIndex, stepId } = ownProps;
  return {
    pickstepState:
      state.wfProcesses_status[wfProcessId].activities[activityId].dataStored.lines[lineIndex].steps[stepId],
  };
};

PickStep.propTypes = {
  id: PropTypes.string.isRequired,
  locatorName: PropTypes.string.isRequired,
  productName: PropTypes.string.isRequired,
  qtyPicked: PropTypes.number,
  qtyToPick: PropTypes.number.isRequired,
  uom: PropTypes.string,
  updatePickingStepQty: PropTypes.func.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  stepId: PropTypes.number.isRequired,
  lineIndex: PropTypes.number.isRequired,
  pickstepState: PropTypes.object,
  huBarcode: PropTypes.string,
  push: PropTypes.func.isRequired,
};

export default connect(mapStateToProps, { updatePickingStepQty, push })(PickStep);
