import React, { Component } from 'react';
import PropTypes from 'prop-types';

class BarcodeScanner extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeScanning: false,
    };
  }

  initiateScanning = () => this.setState({ activeScanning: true });

  render() {
    const { barcodeCaption } = this.props.componentProps;
    const { activeScanning } = this.state;
    const scanBtnCaption = barcodeCaption || 'Scan';
    return (
      <div>
        <div className="buttons">
          <button className="button scanner-btn-green" onClick={this.initiateScanning}>
            {scanBtnCaption}
          </button>
        </div>
        {activeScanning && (
          <div>
            <div>Please scan a Barcode</div>
            <div>Video Placeholder</div>
          </div>
        )}
      </div>
    );
  }
}

BarcodeScanner.propTypes = {
  componentProps: PropTypes.object.isRequired,
};

export default BarcodeScanner;
