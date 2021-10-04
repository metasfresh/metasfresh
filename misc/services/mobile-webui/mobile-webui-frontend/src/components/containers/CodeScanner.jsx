import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { BrowserMultiFormatReader, DecodeHintType, BarcodeFormat } from '@zxing/library';
import { connect } from 'react-redux';
import { startScanning, stopScanning } from '../../actions/ScanActions';

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
        onDetection({ detectedCode: result.getText(), activityId });
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

  stopScanning = () => {
    const { stopScanning } = this.props;
    this.codeReader.stopContinuousDecode();
    stopScanning();
  };

  render() {
    const { videoInputDevices } = this.state;
    const { barcodeCaption } = this.props.componentProps;
    const {
      scanner: { active },
    } = this.props;
    const scanBtnCaption = barcodeCaption || 'Scan';

    !active && this.codeReader.stopContinuousDecode();

    return (
      <div>
        {!active && (
          <>
            <div className="title is-4 header-caption">Scanner</div>
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
  componentProps: PropTypes.object.isRequired,
  scanner: PropTypes.object.isRequired,
  startScanning: PropTypes.func.isRequired,
  stopScanning: PropTypes.func.isRequired,
  onDetection: PropTypes.func.isRequired,
  activityId: PropTypes.string.isRequired,
};

export default connect(mapStateToProps, { startScanning, stopScanning })(CodeScanner);
