import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { BrowserMultiFormatReader, DecodeHintType, BarcodeFormat } from '@zxing/library';
import { connect } from 'react-redux';
import { startScanning } from '../../actions/ScanActions';

class CodeScanner extends Component {
  constructor(props) {
    super(props);
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

  setSelectedDeviceId = (deviceId) => this.setState({ selectedDeviceId: deviceId });

  setupDevices = (videoInputDevices) => {
    // selects first device
    this.setState({ selectedDeviceId: videoInputDevices[0].deviceId });

    // setup devices dropdown
    if (videoInputDevices.length >= 1) {
      this.setState({ videoInputDevices });
    }
  };

  decodeContinuously = (selectedDeviceId) => {
    const { onDetection, activityId } = this.props;
    this.codeReader.decodeFromInputVideoDeviceContinuously(selectedDeviceId, 'video', (result, err) => {
      if (result) {
        // properly decoded qr code
        console.log('Found:', result);
        let detectedCode = result.getText();
        console.log('Detected code:', detectedCode);
        onDetection({ detectedCode, activityId });
        this.codeReader.stopContinuousDecode();
      }

      if (err) {
        console.error(err);
      }
    });
  };

  componentDidMount() {
    console.log('CodeScanner initialized');
    this.codeReader
      .getVideoInputDevices()
      .then((videoInputDevices) => {
        this.setState({ videoInputDevices }, () => {
          this.setupDevices(videoInputDevices);
        });
      })
      .catch((err) => {
        console.error(err);
      });
  }

  initiateScanning = () => {
    const { selectedDeviceId } = this.state;
    const { startScanning } = this.props;
    startScanning();
    this.decodeContinuously(selectedDeviceId);
    // window.scrollTo(0, 0);
  };

  render() {
    const { videoInputDevices } = this.state;
    const {
      scanner: { active },
      caption,
    } = this.props;

    let scanBtnCaption = caption || 'Scan';

    !active && this.codeReader.stopContinuousDecode();

    return (
      <div>
        {!active && (
          <>
            <button className="button is-outlined complete-btn" onClick={this.initiateScanning}>
              {scanBtnCaption}
            </button>
          </>
        )}
        {active && (
          <div className="scanner-container">
            {/* Select video source */}
            <div id="sourceSelectPanel">
              <label htmlFor="sourceSelect">Video source:</label>
              <select id="sourceSelect" onChange={() => this.setSelectedDeviceId(this.value)}>
                {videoInputDevices.map((element) => (
                  <option key={element.deviceId} value={element.deviceId}>
                    {element.label}
                  </option>
                ))}
              </select>
            </div>

            {/* Video stream  */}
            <div>
              <video id="video" width="100%" height="100%" />
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
  activityId: PropTypes.string.isRequired,
  caption: PropTypes.string,
};

export default connect(mapStateToProps, { startScanning })(CodeScanner);
