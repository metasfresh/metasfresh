import React, { Component } from 'react';
import { connect } from 'react-redux';
import { push } from 'connected-react-router';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import toast, { Toaster } from 'react-hot-toast';
import { updatePickingStepQty } from '../../../actions/PickingActions';
import ButtonWithIndicator from '../../../components/ButtonWithIndicator';

class PickStepScreen extends Component {
  onScanHUButtonClick = () => {
    const {
      wfProcessId,
      activityId,
      lineId,
      stepId,
      stepProps: { huBarcode, qtyToPick, qtyPicked },
    } = this.props;

    const { push, pushHeaderEntry } = this.props;

    const location = `/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}/scanner`;
    push(location);
    pushHeaderEntry({
      location,
      values: [
        {
          caption: 'Barcode',
          value: huBarcode,
        },
        {
          caption: 'To Pick',
          value: qtyToPick,
        },
        {
          caption: 'Picked',
          value: qtyPicked,
        },
      ],
    });
  };

  onQtyPickedChanged = (e) => {
    const { updatePickingStepQty, wfProcessId, activityId, lineId, stepId } = this.props;
    const inputQty = parseInt(e.target.value);
    if (isNaN(inputQty)) {
      updatePickingStepQty({ wfProcessId, activityId, lineId, stepId, qty: '' });
      return;
    }

    const isValidQty = this.validateQtyInput(inputQty);
    if (isValidQty) {
      updatePickingStepQty({ wfProcessId, activityId, lineId, stepId, qty: e.target.value });
    } else {
      // show error
      toast('Quantity picked is invalid!', { type: 'error', style: { color: 'white' } });
    }
  };

  validateQtyInput = (numberInput) => {
    const {
      stepProps: { qtyToPick },
    } = this.props;
    return numberInput >= 0 && numberInput <= qtyToPick;
  };

  componentWillUnmount() {
    const {
      stepProps: { qtyPicked },
      wfProcessId,
      activityId,
      lineId,
      stepId,
      updatePickingStepQty,
    } = this.props;
    qtyPicked === '' && updatePickingStepQty({ wfProcessId, activityId, lineId, stepId, qty: 0 });
  }

  render() {
    const {
      stepProps: { huBarcode, qtyToPick, qtyPicked, scannedHUBarcode },
    } = this.props;

    const isValidCode = !!scannedHUBarcode;
    const scanButtonCaption = isValidCode ? `${scannedHUBarcode}` : `Scan`;
    const scanButtonStatus = isValidCode ? `complete` : `incomplete`;

    return (
      <div className="pt-3 section picking-step-container">
        <div className="picking-step-details centered-text is-size-5">
          {/* <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold pt-0 pb-0 pl-0 pr-0">Product:</div>
            <div className="column is-half has-text-left pt-0 pb-0">{productName}</div>
          </div>
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">Locator:</div>
            <div className="column is-half has-text-left pb-0">{locatorName}</div>
          </div>
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">UOM:</div>
            <div className="column is-half has-text-left pb-0">{uom}</div>
          </div> */}
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">Barcode:</div>
            <div className="column is-half has-text-left pb-0">{huBarcode}</div>
          </div>
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">Quantity to pick:</div>
            <div className="column is-half has-text-left pb-0">{qtyToPick}</div>
          </div>
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold pb-0 pl-0 pr-0">Quantity picked:</div>
            <div className="column is-half has-text-left pb-0">
              {isValidCode && <input type="number" value={qtyPicked} onChange={(e) => this.onQtyPickedChanged(e)} />}
              {!isValidCode && qtyPicked}
            </div>
          </div>
          <div className="mt-0">
            <button className="button is-outlined complete-btn" onClick={this.onScanHUButtonClick}>
              <ButtonWithIndicator caption={scanButtonCaption} indicatorType={scanButtonStatus} />
            </button>
          </div>
          <Toaster
            position="bottom-center"
            toastOptions={{
              success: {
                style: {
                  background: 'green',
                },
              },
              error: {
                style: {
                  background: 'red',
                },
              },
            }}
          />
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId, stepId } = ownProps.match.params;

  const stepProps = state.wfProcesses_status[wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId];

  return {
    wfProcessId,
    activityId,
    lineId,
    stepId,
    stepProps,
  };
};

PickStepScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  stepProps: PropTypes.object.isRequired,
  //
  // Actions
  updatePickingStepQty: PropTypes.func.isRequired,
  push: PropTypes.func.isRequired,
  pushHeaderEntry: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps, { updatePickingStepQty, push, pushHeaderEntry })(PickStepScreen));
