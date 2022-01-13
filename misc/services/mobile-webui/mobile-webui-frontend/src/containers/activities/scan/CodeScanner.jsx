import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { BarcodeFormat, BrowserMultiFormatReader, DecodeHintType } from '@zxing/library';
// import { connect } from 'react-redux';

class CodeScanner extends Component {
  constructor(props) {
    super(props);

    this.state = {
      videoInputDevices: [],
      selectedDeviceId: '',
    };

    this.videoInput = React.createRef();

    this.codeReader = new BrowserMultiFormatReader(
      new Map()
        .set(DecodeHintType.POSSIBLE_FORMATS, [
          BarcodeFormat.CODE_128,
          BarcodeFormat.DATA_MATRIX,
          BarcodeFormat.QR_CODE,
        ])
        .set(DecodeHintType.TRY_HARDER, true)
    );
  }

  closeCamera() {
    console.log('closing camera....');
    try {
      if (this.videoInput.current) {
        const mediaStream = this.videoInput.current.srcObject;
        if (mediaStream) {
          const tracks = mediaStream.getTracks();
          tracks.forEach((track) => track.stop());
        }
      }

      this.codeReader.stopContinuousDecode();
      this.codeReader.reset();
    } catch (e) {
      console.log(e);
    }
  }

  componentDidMount() {
    this.loadVideoInputDevices();
  }

  componentWillUnmount() {
    this.closeCamera();
  }

  loadVideoInputDevices() {
    this.codeReader
      .getVideoInputDevices()
      .then((videoInputDevices) => this.onVideoInputDevices(videoInputDevices))
      .catch((err) => console.error('getVideoInputDevices error: %o', err));
  }

  onVideoInputDevices(videoInputDevices) {
    console.log('onVideoInputDevices: %o', videoInputDevices);
    this.setState({ ...this.state, videoInputDevices }, () => {
      this.selectDefaultDevice();
    });
  }

  selectDefaultDevice() {
    console.log('selectDefaultDevice');

    navigator.mediaDevices
      .getUserMedia({
        audio: false,
        video: {
          facingMode: 'environment',
        },
      })
      .then((stream) => this.setSelectedDeviceId(stream.getVideoTracks()[0].getSettings().deviceId))
      .catch((err) => console.error('getUserMedia error: %o', err));
  }

  setSelectedDeviceId(selectedDeviceId) {
    console.log('setSelectedDeviceId: %o', selectedDeviceId);
    this.setState({ ...this.state, selectedDeviceId }, () => {
      this.decodeContinuously(selectedDeviceId);
    });
  }

  decodeContinuously(selectedDeviceId) {
    const { onBarcodeScanned } = this.props;

    // make sure previous opened camera is closed
    this.closeCamera();

    console.log('decoding camera: %o', selectedDeviceId);
    this.codeReader.decodeFromInputVideoDeviceContinuously(selectedDeviceId, 'video', (result, err) => {
      if (result) {
        console.log('Got result from camera: %o', result);
        let scannedBarcode = result.getText();

        onBarcodeScanned({ scannedBarcode }, this.closeCamera);

        // don't close the camera. let the caller decide what he wants to do.
      } else if (err) {
        // don't spam the console with NotFoundException errors, just silently discard them
        //console.error('decodeContinuously error: %o. result was %o', err, result);
      }
    });
  }

  render() {
    return (
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
    );
  }
}

CodeScanner.propTypes = {
  //
  // Props:
  onBarcodeScanned: PropTypes.func.isRequired,
};

// export default connect(null, null)(CodeScanner);
export default CodeScanner;
