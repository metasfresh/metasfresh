import React, { Component } from 'react';
import BarcodeScanner, { BarcodeScannerResult } from './BarcodeScanner';

function addBarcodeScanner(WrappedComponent) {
  return class BarcodeScannerWidget extends Component {
    constructor(props) {
      super(props);

      this.state = {
        scanning: false,
        result: null,
        barcodeSelected: null,
      };
    }

    scanBarcode = val => {
      this.setState({
        scanning: typeof val !== 'undefined' ? val : !this.state.scanning,
        result: null,
        barcodeSelected: null,
      });
    };

    onBarcodeDetected = result => {
      this.setState({
        result,
      });
    };

    selectBarcode = result => {
      this.setState({
        barcodeSelected: result ? result.codeResult.code : null,
        scanning: false,
      });
    };

    renderScanner = () => {
      const { result } = this.state;

      return (
        <div className="row barcode-scanner-widget">
          <div className="col-sm-12">
            <BarcodeScanner
              result={result}
              onDetected={this.onBarcodeDetected}
              onClose={() => this.scanBarcode(false)}
              onReset={() => this.scanBarcode(true)}
            />
            <div className="row scanning-result">
              <span className="label col-xs-3 col-sm-2">Barcode</span>
              <BarcodeScannerResult
                result={result}
                onSelect={this.selectBarcode}
              />
            </div>
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
          onSelectBarcode={this.selectBarcode}
        />
      );
    }
  };
}

export default addBarcodeScanner;
