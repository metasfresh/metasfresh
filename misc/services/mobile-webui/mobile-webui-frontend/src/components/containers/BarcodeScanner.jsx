import React, { Component } from 'react';
import PropTypes from 'prop-types';
//import ZXingBrowser from '@zxing/browser';
import Webcam from 'react-webcam';
import { connect } from 'react-redux';
import { startScanning, stopScanning } from '../../actions/ScanActions';

class BarcodeScanner extends Component {
  initiateScanning = () => {
    const { startScanning } = this.props;
    startScanning();
    // window.scrollTo(0, 0);
  };

  stopScanning = () => {
    const { stopScanning } = this.props;
    stopScanning();
  };

  render() {
    const { barcodeCaption } = this.props.componentProps;
    const {
      scanner: { active },
    } = this.props;
    const scanBtnCaption = barcodeCaption || 'Scan';
    return (
      <div>
        {!active && (
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
        {active && (
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

const mapStateToProps = (state) => {
  return {
    scanner: state.scanner,
  };
};

BarcodeScanner.propTypes = {
  componentProps: PropTypes.object.isRequired,
  scanner: PropTypes.object.isRequired,
  startScanning: PropTypes.func.isRequired,
  stopScanning: PropTypes.func.isRequired,
};

export default connect(mapStateToProps, { startScanning, stopScanning })(BarcodeScanner);
