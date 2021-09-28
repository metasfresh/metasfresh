import React, { Component } from 'react';
import PropTypes from 'prop-types';
//import ZXingBrowser from '@zxing/browser';

class BarcodeScanner extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeScanning: false,
    };
  }

  initiateScanning = () => {
    this.setState({ activeScanning: true });
    window.scrollTo(0, 0);
  };
  stopScanning = () => this.setState({ activeScanning: false });

  render() {
    const { barcodeCaption } = this.props.componentProps;
    const { activeScanning } = this.state;
    const scanBtnCaption = barcodeCaption || 'Scan';
    return (
      <div>
        {/* INITIAL SCN BUTTON */}
        {/* <div className="buttons">
          <button className="button scanner-btn-green" onClick={this.initiateScanning}>
            {scanBtnCaption}
          </button>
        </div> */}
        <div className="ml-3 mr-3 is-light launcher" onClick={this.initiateScanning}>
          <div className="box">
            <div className="columns is-mobile">
              <div className="column is-12">
                <div className="columns">
                  <div className="column is-size-4-mobile no-p">{scanBtnCaption}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
        {activeScanning && (
          <div className="scanner-container">
            <div className="subtitle centered-text is-size-4">
              Please scan a Barcode &nbsp; <button onClick={this.stopScanning}>Stop Scan</button>
            </div>
            <video className="viewport scanner-window" id="video" />
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
