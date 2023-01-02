import React, { Component } from 'react';
import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import BarcodeScanner from '../BarcodeScanner/BarcodeScannerWidget';

class BarcodeScannerBtn extends Component {
  render() {
    const { onScanBarcode, scanning, scannerElement } = this.props;

    // the HOC is creating an element that we need to render, we need to check the `scanning` flag and render it when it is true
    if (scanning) {
      return (
        <div className="overlay-field js-not-unselect col-sm-10 input-qr-scanner-wrapper">
          {scannerElement}
        </div>
      );
    }

    return (
      <div className="col-sm-2">
        <button
          className="btn btn-sm btn-meta-success btn-scanner"
          onClick={() => onScanBarcode(true)}
        >
          {counterpart.translate('widget.scanFromCamera.caption')}
        </button>
      </div>
    );
  }
}
BarcodeScannerBtn.propTypes = {
  onScanBarcode: PropTypes.func,
  scanning: PropTypes.bool,
  scannerElement: PropTypes.node,
};

export default BarcodeScanner(BarcodeScannerBtn);
