import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import BarcodeScanner from './BarcodeScanner';

class PickScreen extends Component {
  render() {
    let huBarcode = 'test';
    return (
      <div className="picking-step-container">
        <div className="picking-step-details centered-text is-size-5">
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold">Product:</div>
            <div className="column is-half has-text-left">productName</div>
          </div>
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold">Locator:</div>
            <div className="column is-half has-text-left">locatorName</div>
          </div>
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold">UOM:</div>
            <div className="column is-half has-text-left">uom</div>
          </div>
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold">Barcode:</div>
            <div className="column is-half has-text-left">huBarcode</div>
          </div>
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold">Quantity to pick:</div>
            <div className="column is-half has-text-left">qtyToPick</div>
          </div>
          <div className="columns is-mobile">
            <div className="column is-half has-text-right has-text-weight-bold">Quantity picked:</div>
            <div className="column is-half has-text-left">qtyPicked</div>
          </div>
          <BarcodeScanner id={huBarcode} componentProps={{ caption: 'Scan' }} />
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  console.log(ownProps.match.params);
  const { workflowId: wfProcessId, activityId, lineId } = ownProps.match.params;

  const targetActivity = state.wfProcesses[wfProcessId].activities.filter(
    (activity) => activity.activityId === activityId
  );
  return {
    lineProps: targetActivity[0].componentProps.lines[lineId],
    activityId,
    wfProcessId,
    lineIndex: Number(lineId),
  };
};

PickScreen.propTypes = {
  activityId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  lineIndex: PropTypes.number.isRequired,
  lineProps: PropTypes.object.isRequired,
};

export default connect(mapStateToProps, {})(PickScreen);
