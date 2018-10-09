import React, { Component } from 'react';
import BarcodeScanner from './BarcodeScanner';
import { BrowserQRCodeReader, VideoInputDevice } from '@zxing/library';
import ZXing from '@zxing/library';

function addBarcodeScanner(WrappedComponent) {
  return class BarcodeScannerWidget extends Component {
    constructor(props) {
      super(props);

      this.state = {
        scanning: false,
        barcodeSelected: null,
      };

      this.codeReader = new BrowserQRCodeReader();
    }

    scanBarcode = val => {
      this.setState({
        scanning: typeof val !== 'undefined' ? val : !this.state.scanning,
        barcodeSelected: null,
      });

      let firstDeviceId = null;

      this.codeReader.getVideoInputDevices()
        .then(videoInputDevices => {
          videoInputDevices.forEach(
              device => console.log(`${device.label}, ${device.deviceId}`)
          );

          firstDeviceId = videoInputDevices[0].deviceId;

          this.codeReader.decodeFromInputVideoDevice(firstDeviceId, 'video')
            .then(result => console.log(result.text))
            .catch(err => console.error(err));
        })
        .catch(err => console.error(err));
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
{/*            <BarcodeScanner
              onDetected={this.onBarcodeDetected}
              onClose={() => this.scanBarcode(false)}
              onReset={() => this.scanBarcode(true)}
            />*/}
            
            <div className="row scanner-wrapper">
              <div id="interactive"  />
              <video className="col-sm-12 viewport scanner-window" id="video" width="1280" height="720"></video>
              <i className="btn-close meta-icon-close-1" onClick={this._handleStop} />
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
          onSelectBarcode={this.onBarcodeDetected}
        />
      );
    }
  };
}

export default addBarcodeScanner;
