import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router';

import CodeScanner from './CodeScanner';
import { stopScanning } from '../../actions/ScanActions';

class PickScreen extends Component {
  constructor(props) {
    super(props);
    this.state = { detectedCode: '' };
  }

  onDetection = (barcode) => {
    const { stopScanning } = this.props;
    stopScanning();
    this.setState({ detectedCode: barcode });
  };

  render() {
    const { detectedCode } = this.state;
    const {
      stepProps: { productName, locatorName, uom, huBarcode, qtyToPick, qtyPicked },
      activityId,
    } = this.props;

    return (
      <div className="pt-3 section picking-step-container">
        <div className="picking-step-details centered-text is-size-5">
          <div className="columns is-mobile">
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
          </div>
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
            componentProps={{ caption: 'Scan' }}
            onDetection={this.onDetection}
            activityId={activityId}
          />
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  const { workflowId: wfProcessId, activityId, lineId, stepId } = ownProps.match.params;

  const targetActivity = state.wfProcesses[wfProcessId].activities.filter(
    (activity) => activity.activityId === activityId
  );
  return {
    stepProps: targetActivity[0].componentProps.lines[lineId].steps[stepId],
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
};

export default withRouter(connect(mapStateToProps, { stopScanning })(PickScreen));
