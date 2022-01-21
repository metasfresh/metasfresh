import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { toastError } from '../utils/toast';
import BarcodeScannerComponent from './BarcodeScannerComponent';
import GetQuantityDialog from './dialogs/GetQuantityDialog';
import QtyReasonsView from '../containers/activities/QtyReasonsView';
import Button from './buttons/Button';

const STATUS_READ_BARCODE = 'READ_BARCODE';
const STATUS_READ_QTY = 'READ_QTY';
const STATUS_READ_QTY_REJECTED_REASON = 'READ_QTY_REJECTED_REASON';

class ScanHUAndGetQtyComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      progressStatus: STATUS_READ_BARCODE,
      scannedBarcode: null,
      newQuantity: 0,
      qtyRejected: 0,
    };

    console.log('ScanHUAndGetQtyComponent.constructor()', {
      typeof: typeof this.props.qtyTarget,
      isNull: this.props.qtyTarget === null,
    });
  }

  onCancelGetQty = () => {
    this.setState({ progressStatus: STATUS_READ_BARCODE });
  };

  onBarcodeScanned = ({ scannedBarcode }) => {
    const { qtyTarget, onResult, invalidBarcodeMessageKey } = this.props;

    if (this.isEligibleBarcode(scannedBarcode)) {
      // in some cases we don't need store quantity (ie manufacturing receipts)
      if (qtyTarget != null) {
        this.setState({ progressStatus: STATUS_READ_QTY, scannedBarcode });
      } else {
        onResult({ qty: 0, reason: null, scannedBarcode });
      }
    } else {
      // show an error to user but keep scanning...
      toastError({ messageKey: invalidBarcodeMessageKey ?? 'activities.picking.notEligibleHUBarcode' });
    }
  };

  onQtyEntered = (qty) => {
    const { qtyTarget, onResult, invalidQtyMessageKey } = this.props;

    const inputQty = parseFloat(qty);
    if (isNaN(inputQty)) {
      return;
    }

    if (this.isQtyEnteredValid(inputQty)) {
      const qtyRejected = Math.max(qtyTarget - inputQty, 0);
      if (qtyRejected !== 0) {
        this.setState({ progressStatus: STATUS_READ_QTY_REJECTED_REASON, newQuantity: inputQty, qtyRejected });
      } else {
        onResult({ qty: inputQty, reason: null, scannedBarcode: this.state.scannedBarcode });
      }
    } else {
      toastError({ messageKey: invalidQtyMessageKey || 'activities.picking.invalidQtyPicked' });
    }
  };

  onQtyRejectedReasonEntered = (reason) => {
    const { onResult } = this.props;
    onResult({ qty: this.state.newQuantity, reason, scannedBarcode: this.state.scannedBarcode });
  };

  isQtyEnteredValid = (qtyEntered) => {
    // non-positive qtys are not valid
    if (qtyEntered <= 0) {
      return false;
    }

    const { qtyTarget } = this.props;
    return qtyTarget <= 0 || qtyEntered <= qtyTarget;
  };

  isEligibleBarcode = (scannedBarcode) => {
    const { eligibleBarcode } = this.props;

    console.log(`checking eligibleBarcode=${eligibleBarcode} vs scannedBarcode=${scannedBarcode}`);
    // in some cases we accept whatever code user scans and we're not constraining it
    return eligibleBarcode ? scannedBarcode === eligibleBarcode : true;
  };

  render() {
    const { uom, qtyCaption, qtyInitial, qtyTarget, qtyRejectedReasons } = this.props;
    const { progressStatus, qtyRejected } = this.state;

    switch (progressStatus) {
      case STATUS_READ_BARCODE:
        return (
          <>
            <BarcodeScannerComponent onBarcodeScanned={this.onBarcodeScanned} />
            {this.renderDebugScanEligibleBarcodeButton()}
          </>
        );
      case STATUS_READ_QTY:
        return (
          <GetQuantityDialog
            qtyInitial={qtyInitial}
            qtyTarget={qtyTarget}
            qtyCaption={qtyCaption}
            uom={uom}
            onQtyChange={this.onQtyEntered}
            onCloseDialog={this.onCancelGetQty}
          />
        );
      case STATUS_READ_QTY_REJECTED_REASON:
        return (
          <QtyReasonsView
            onHide={this.onQtyRejectedReasonEntered}
            uom={uom}
            qtyRejected={qtyRejected}
            qtyRejectedReasons={qtyRejectedReasons}
          />
        );
      default:
        return null;
    }
  }

  renderDebugScanEligibleBarcodeButton = () => {
    if (window.metasfresh_debug && this.props.eligibleBarcode) {
      return (
        <Button
          caption={`DEBUG: ${this.props.eligibleBarcode}`}
          onClick={() => this.onBarcodeScanned({ scannedBarcode: this.props.eligibleBarcode })}
        />
      );
    } else {
      return null;
    }
  };
}

ScanHUAndGetQtyComponent.propTypes = {
  //
  // Props
  eligibleBarcode: PropTypes.string,
  qtyCaption: PropTypes.string,
  qtyTarget: PropTypes.number,
  qtyInitial: PropTypes.number,
  uom: PropTypes.string,
  qtyRejectedReasons: PropTypes.array,
  // Error messages:
  invalidBarcodeMessageKey: PropTypes.string,
  invalidQtyMessageKey: PropTypes.string,
  //
  // Functions
  onResult: PropTypes.func,
};

export default ScanHUAndGetQtyComponent;
