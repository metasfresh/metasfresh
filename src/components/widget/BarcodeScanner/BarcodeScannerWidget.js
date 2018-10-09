import React, { Component } from 'react';
import BarcodeScanner from './BarcodeScanner';

function addBarcodeScanner(WrappedComponent) {
  return class BarcodeScannerWidget extends Component {
    constructor(props) {
      super(props);

      this.state = {
        scanning: false,
        codeSelected: null,
      };
    }

    scanBarcode = val => {
      this.setState({
        scanning: typeof val !== 'undefined' ? val : !this.state.scanning,
        codeSelected: null,
      });
    };

    onBarcodeDetected = result => {
      this.setState({
        codeSelected: result || null,
        scanning: false,
      });
    };

    renderScanner = () => {
      return (
        <div className="row barcode-scanner-widget">
          <div className="col-sm-12">
            <BarcodeScanner
              onDetected={this.onBarcodeDetected}
              onClose={() => this.scanBarcode(false)}
            />
          </div>
        </div>
      );
    };

    render() {
      const scannerElement = this.renderScanner();

      return (
        <WrappedComponent
          {...this.props}
          {...this.state}
          scannerElement={scannerElement}
          onScanBarcode={this.scanBarcode}
          onSelectBarcode={this.onBarcodeDetected}
        />
      );
    }
  };
}

export default addBarcodeScanner;
