import React, { Component } from 'react';
import BarcodeScanner from './BarcodeScanner';

function addBarcodeScanner(WrappedComponent) {
  return class BarcodeScannerWidget extends Component {
    constructor(props) {
      super(props);

      this.state = {
        scanning: false,
        barcodeSelected: null,
      };
    }

    scanBarcode = val => {
      this.setState({
        scanning: typeof val !== 'undefined' ? val : !this.state.scanning,
        barcodeSelected: null,
      });
    };

    onBarcodeDetected = result => {
      this.setState({
        barcodeSelected: result ? result.codeResult.code : null,
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
              onReset={() => this.scanBarcode(true)}
              debug={true}
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
