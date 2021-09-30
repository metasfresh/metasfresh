import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { updatePickingStepQty } from '../actions/PickingActions';
import BarcodeScanner from './containers/BarcodeScanner';
import { push } from 'connected-react-router';
class PickStep extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      activePickingStep: false,
    };
  }

  handleClick = () => {
    const { wfProcessId, activityId, lineIndex, stepId, push } = this.props;
    console.log('P:', this.props);
    push(`/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineIndex}/stepId/${stepId}`);
  };

  goBackToPickingSteps = () => this.setState({ activePickingStep: false });

  modifyQty = (e) => {
    const { updatePickingStepQty, wfProcessId, activityId, lineIndex, stepId } = this.props;
    updatePickingStepQty({ wfProcessId, activityId, lineIndex, stepId, qty: e.target.value });
  };

  render() {
    const { activePickingStep } = this.state;
    const { id, locatorName, productName, huBarcode, uom, pickstepState, qtyToPick } = this.props;
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
              <div className="columns is-mobile">
                <div className="column is-half has-text-right has-text-weight-bold">Product:</div>
                <div className="column is-half has-text-left">{productName}</div>
              </div>
              <div className="columns is-mobile">
                <div className="column is-half has-text-right has-text-weight-bold">Locator:</div>
                <div className="column is-half has-text-left">{locatorName}</div>
              </div>
              <div className="columns is-mobile">
                <div className="column is-half has-text-right has-text-weight-bold">UOM:</div>
                <div className="column is-half has-text-left">{uom}</div>
              </div>
              <div className="columns is-mobile">
                <div className="column is-half has-text-right has-text-weight-bold">Barcode:</div>
                <div className="column is-half has-text-left">{huBarcode}</div>
              </div>
              <div className="columns is-mobile">
                <div className="column is-half has-text-right has-text-weight-bold">Quantity to pick:</div>
                <div className="column is-half has-text-left">{qtyToPick}</div>
              </div>
              <div className="columns is-mobile">
                <div className="column is-half has-text-right has-text-weight-bold">Quantity picked:</div>
                <div className="column is-half has-text-left">{qtyPicked}</div>
              </div>
              <BarcodeScanner id={huBarcode} componentProps={{ caption: 'Scan' }} />
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
  huBarcode: PropTypes.string,
  push: PropTypes.func.isRequired,
};

export default connect(mapStateToProps, { updatePickingStepQty, push })(PickStep);
