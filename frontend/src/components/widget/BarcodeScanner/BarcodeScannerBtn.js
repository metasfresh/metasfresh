import React, { Component } from 'react';
import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import BarcodeScanner from '../BarcodeScanner/BarcodeScannerWidget';

class BarcodeScannerBtn extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { onScanBarcode } = this.props;
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
};

export default BarcodeScanner(BarcodeScannerBtn);
