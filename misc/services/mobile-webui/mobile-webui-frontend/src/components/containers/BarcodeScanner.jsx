import React, { Component } from 'react';
import PropTypes from 'prop-types';
//import ZXingBrowser from '@zxing/browser';
import Webcam from 'react-webcam';
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
    console.log(this.size);
    const { barcodeCaption } = this.props.componentProps;
    const { activeScanning } = this.state;
    const scanBtnCaption = barcodeCaption || 'Scan';
    return (
      <div>
        {!activeScanning && (
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
        )}
        {activeScanning && (
          <div className="scanner-container">
            {/* <video className="viewport scanner-window" id="video" /> */}
            <Webcam
              clssname="scanner-container"
              audio={false}
              screenshotFormat="image/jpeg"
              forceScreenshotSourceSize="true"
            />
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
