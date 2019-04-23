import React, { Component } from 'react';
import BarcodeScanner from './BarcodeScanner';

import currentDevice from 'current-device';

function addBarcodeScanner(WrappedComponent) {
  return class BarcodeScannerWidget extends Component {
    constructor(props) {
      super(props);

      if (props.data && props.data.Barcode && currentDevice.type === 'mobile') {
        this.state = {
          scanning: true,
          codeSelected: null,
        };
      } else {
        this.state = {
          scanning: false,
          codeSelected: null,
        };
      }
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
      const { closeOverlay } = this.props;

      return (
        <div className="row barcode-scanner-widget">
          <div className="col-sm-12">
            <BarcodeScanner
              onDetected={this.onBarcodeDetected}
              onClose={val => {
                const value = typeof val !== 'undefined' ? val : false;
                this.scanBarcode(value);

                if (currentDevice.type === 'mobile') {
                  closeOverlay();
                }
              }}
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
