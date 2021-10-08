import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { BrowserMultiFormatReader, DecodeHintType, BarcodeFormat } from '@zxing/library';
import { connect } from 'react-redux';

import { startScanning } from '../../actions/ScanActions';

class CodeScanner extends Component {
  _isMounted = false;

  constructor(props) {
    super(props);

    this.videoInput = React.createRef();
    const hints = new Map();
    const formats = [BarcodeFormat.CODE_128, BarcodeFormat.DATA_MATRIX, BarcodeFormat.QR_CODE];
    hints.set(DecodeHintType.POSSIBLE_FORMATS, formats);
    hints.set(DecodeHintType.TRY_HARDER, true);
    this.codeReader = new BrowserMultiFormatReader(hints);

    this.state = {
      videoInputDevices: [],
      selectedDeviceId: '',
    };
  }

  setSelectedDeviceId = (deviceId) => {
    this._isMounted && this.setState({ selectedDeviceId: deviceId });
  };

  setupDevices = (videoInputDevices) => {
    // selects first device
    this._isMounted && this.setState({ selectedDeviceId: videoInputDevices[0].deviceId });

    // setup devices dropdown
    if (videoInputDevices.length >= 1) {
      this._isMounted && this.setState({ videoInputDevices });
    }
  };

  decodeContinuously = (selectedDeviceId) => {
    const { onDetection, isScanDisabled } = this.props;

    this.codeReader.decodeFromInputVideoDeviceContinuously(selectedDeviceId, 'video', (result, err) => {
      if (result) {
        // properly decoded the code
        console.log('Found:', result);
        let detectedCode = result.getText();

        // close the video sources
        if (isScanDisabled) {
          const mediaStream = this.videoInput.current.srcObject;
          const tracks = mediaStream.getTracks();
          tracks.forEach((track) => track.stop());
        }

        onDetection({ scannedBarcode: detectedCode });
        isScanDisabled && this.codeReader.stopContinuousDecode();
      }

      if (err) {
        console.error(err);
      }
    });
  };

  componentDidMount() {
    this._isMounted = true;

    const { selectedDeviceId } = this.state;

    this.decodeContinuously(selectedDeviceId);

    console.log('CodeScanner initialized');
    this.codeReader
      .getVideoInputDevices()
      .then((videoInputDevices) => {
        navigator.mediaDevices
          .getUserMedia({
            audio: false,
            video: {
              facingMode: 'environment',
            },
          })
          .then((stream) => this.setSelectedDeviceId(stream.getVideoTracks()[0].getSettings().deviceId))
          .catch(console.error);

        console.log('videoInputDevices', videoInputDevices);
        this._isMounted &&
          this.setState({ videoInputDevices }, () => {
            this.setupDevices(videoInputDevices);
          });
      })
      .catch((err) => {
        console.error(err);
      });
  }

  componentWillUnmount() {
    this._isMounted = false;
  }

  render() {
    const {
      scanner: { active },
    } = this.props;

    !active && this.codeReader.stopContinuousDecode();

    return (
      <div>
        {active && (
          <div className="scanner-container">
            {/* Select video source */}
            {/* <div id="sourceSelectPanel">
              <label htmlFor="sourceSelect">Video source:</label>
              <select id="sourceSelect" onChange={() => this.setSelectedDeviceId(this.value)}>
                {videoInputDevices.map((element) => (
                  <option key={element.deviceId} value={element.deviceId}>
                    {element.label}
                  </option>
                ))}
              </select>
            </div> */}

            {/* Video stream  */}
            <div>
              <video id="video" width="100%" height="100%" ref={this.videoInput} />
            </div>
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

CodeScanner.propTypes = {
  scanner: PropTypes.object.isRequired,
  startScanning: PropTypes.func.isRequired,
  onDetection: PropTypes.func.isRequired,
  caption: PropTypes.string,
  scanButtonStatus: PropTypes.string,
  isScanDisabled: PropTypes.bool, // future indicator for scanning status
  isEnabled: PropTypes.bool,
};

export default connect(mapStateToProps, { startScanning })(CodeScanner);
