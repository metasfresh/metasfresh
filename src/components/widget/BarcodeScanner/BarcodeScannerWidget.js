import React, { Component } from 'react';
import BarcodeScanner from './BarcodeScanner';
import PropTypes from 'prop-types';

import currentDevice from 'current-device';

function addBarcodeScanner(WrappedComponent) {
  return class BarcodeScannerWidget extends Component {
    static propTypes = {
      layout: PropTypes.object,
      closeOverlay: PropTypes.any,
    };

    constructor(props) {
      super(props);

      const barcodeScannerType =
        props.layout && props.layout.barcodeScannerType
          ? props.layout.barcodeScannerType
          : null;

      const scanning = barcodeScannerType && currentDevice.type === 'mobile';

      this.state = {
        barcodeScannerType: barcodeScannerType,
        scanning: scanning,
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
      const { closeOverlay } = this.props;
      const { barcodeScannerType } = this.state;

      return (
        <div className="row barcode-scanner-widget">
          <div className="col-sm-12">
            <BarcodeScanner
              barcodeScannerType={barcodeScannerType}
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
