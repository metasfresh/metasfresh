import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { updatePickingStepQty } from '../actions/PickingActions';

class PickStep extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      activePickingStep: false,
    };
  }

  handleClick = () => {
    this.setState({ activePickingStep: true });
    window.scrollTo(0, 0);
  };

  goBackToPickingSteps = () => this.setState({ activePickingStep: false });

  modifyQty = (e) => {
    const { updatePickingStepQty, wfProcessId, activityId, lineIndex, stepId } = this.props;
    updatePickingStepQty({ wfProcessId, activityId, lineIndex, stepId, qty: e.target.value });
  };

  render() {
    const { activePickingStep } = this.state;
    const { id, locatorName, productName, qtyToPick, uom, pickstepState } = this.props;
    const { qtyPicked } = pickstepState;
    return (
      <div>
        {activePickingStep && (
          <div className="picking-step-container">
            <div className="subtitle centered-text is-size-4 pt-3">
              Pick Item{' '}
              <button className="pickgoback-btn-green" onClick={this.goBackToPickingSteps}>
                Go back
              </button>
            </div>
            <div className="picking-step-details centered-text is-size-5">
              <div>Product: {productName}</div>
              <div>Locator: {locatorName}</div>
              <div>UOM: {uom}</div>
              <div>Qty to pick: {qtyToPick}</div>
              <div className="columns">
                <div className="column is-size-4-mobile">
                  Qty picked:{' '}
                  <input
                    className="input"
                    type="text"
                    placeholder="Quantity"
                    value={qtyPicked}
                    onChange={(e) => this.modifyQty(e)}
                  />
                </div>
              </div>
            </div>
          </div>
        )}
        <div key={id} className="ml-3 mr-3 is-light launcher" onClick={() => this.handleClick(id)}>
          <div className="box">
            <div className="columns is-mobile">
              <div className="column is-12">
                <div className="columns">
                  <div className="column is-size-4-mobile no-p">Product: {productName}</div>
                  <div className="column is-size-7 no-p">
                    To Pick: <span className="has-text-weight-bold">{qtyToPick}</span> Quantity picked:{' '}
                    <span className="has-text-weight-bold">{qtyPicked}</span> UOM: {uom} Locator Name: {locatorName}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
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
};

export default connect(mapStateToProps, { updatePickingStepQty })(PickStep);
