import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router';

import CodeScanner from './CodeScanner';
import { stopScanning } from '../../actions/ScanActions';
import { updatePickingStepDetectedCode } from '../../actions/PickingActions';

class PickScreen extends Component {
  onDetection = (scannedData) => {
    const { detectedCode } = scannedData;
    const { stopScanning, updatePickingStepDetectedCode, wfProcessId, activityId, lineIndex, stepId } = this.props;
    updatePickingStepDetectedCode({ wfProcessId, activityId, lineIndex, stepId, detectedCode });
    stopScanning();
  };

  render() {
    const {
      stepProps: { huBarcode, qtyToPick, qtyPicked, detectedCode },
      activityId,
    } = this.props;

    const scanBtnCaption = detectedCode ? `Code: ${detectedCode}` : ``;
    const scanButtonStatus = detectedCode ? `complete` : `incomplete`;

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
              {detectedCode && <input type="text" value={qtyPicked} onChange={() => null} />}
              {!detectedCode && qtyPicked}
            </div>
          </div>
          <CodeScanner
            id={huBarcode}
            caption={scanBtnCaption}
            onDetection={this.onDetection}
            activityId={activityId}
            scanButtonStatus={scanButtonStatus}
          />
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId, stepId } = ownProps.match.params;

  return {
    stepProps: state.wfProcesses_status[wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId],
    activityId,
    wfProcessId,
    stepId,
    lineIndex: Number(lineId),
  };
};

PickScreen.propTypes = {
  activityId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  lineIndex: PropTypes.number.isRequired,
  stepProps: PropTypes.object.isRequired,
  stopScanning: PropTypes.func.isRequired,
  updatePickingStepDetectedCode: PropTypes.func.isRequired,
  stepId: PropTypes.string.isRequired,
};

export default withRouter(connect(mapStateToProps, { stopScanning, updatePickingStepDetectedCode })(PickScreen));
