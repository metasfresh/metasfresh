// import React, { Component } from 'react';
// import PropTypes from 'prop-types';
// import Quagga from 'quagga';

// export default class BarcodeScanner extends Component {
//   componentDidMount() {
//     Quagga.init(
//       {
//         inputStream: {
//           type: 'LiveStream',
//           constraints: {
//             width: 1280,
//             height: 720,
//             facingMode: 'environment',
//           },
//         },
//         locator: {
//           patchSize: 'medium',
//           halfSample: true,
//         },
//         numOfWorkers: 4,
//         frequency: 5,
//         decoder: {
//           readers: ['ean_reader'],
//         },
//         locate: true,
//       },
//       err => {
//         if (err) {
//           // eslint-disable-next-line no-console
//           return console.log(err);
//         }
//         Quagga.start();
//       }
//     );
//     Quagga.onDetected(this._onDetected);

//     if (this.props.debug) {
//       Quagga.onProcessed(this._onProcessed);
//     }
//   }

//   componentWillUnmount() {
//     Quagga.offDetected(this._onDetected);
//   }

//   _handleStop = () => {
//     Quagga.offDetected(this._onDetected);
//     Quagga.stop();

//     this.props.onClose();
//   };

//   _onDetected = result => {
//     this._handleStop(true);
//     this.props.onDetected(result);
//   };

//   _onProcessed(result) {
//     const drawingCtx = Quagga.canvas.ctx.overlay;
//     const drawingCanvas = Quagga.canvas.dom.overlay;

//     if (result) {
//       if (result.boxes) {
//         drawingCtx.clearRect(
//           0,
//           0,
//           parseInt(drawingCanvas.getAttribute('width')),
//           parseInt(drawingCanvas.getAttribute('height'))
//         );
//         result.boxes.filter(box => box !== result.box).forEach(box => {
//           Quagga.ImageDebug.drawPath(box, { x: 0, y: 1 }, drawingCtx, {
//             color: 'green',
//             lineWidth: 2,
//           });
//         });
//       }

//       if (result.box) {
//         Quagga.ImageDebug.drawPath(result.box, { x: 0, y: 1 }, drawingCtx, {
//           color: '#00F',
//           lineWidth: 2,
//         });
//       }

//       if (result.codeResult && result.codeResult.code) {
//         Quagga.ImageDebug.drawPath(
//           result.line,
//           { x: 'x', y: 'y' },
//           drawingCtx,
//           { color: 'red', lineWidth: 3 }
//         );
//       }
//     }
//   }

//   render() {
//     return (
//       <div className="row scanner-wrapper">
//         <div id="interactive" className="col-sm-12 viewport scanner-window" />
//         {/*<video className="col-sm-12 viewport scanner-window" id="video" width="1280" height="720"></video>*/}
//         <i className="btn-close meta-icon-close-1" onClick={this._handleStop} />
//       </div>
//     );
//   }
// }

// BarcodeScanner.propTypes = {
//   onDetected: PropTypes.func.isRequired,
//   onClose: PropTypes.func.isRequired,
//   onReset: PropTypes.func.isRequired,
// };

import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { BrowserQRCodeReader, BrowserBarcodeReader } from '@zxing/library';

export default class BarcodeScanner extends Component {
  constructor(props) {
    super(props);

    this.state = {
      mode: 'Barcode',
    };

    this.QrReader = new BrowserQRCodeReader();
    this.BarcodeReader = new BrowserBarcodeReader();
  }

  componentDidMount() {
    this._process();
  }

  componentWillUnmount() {
    this._handleStop();
  }

  _process = () => {
    const reader = this[`${this.state.mode}Reader`];

    reader
      .decodeFromInputVideoDevice(undefined, 'video')
      .then(result => this._onDetected(result))
      .catch(err => console.error(err));
  };

  _onDetected = result => {
    this._handleStop();
    this.props.onDetected(result.text);
  };

  _handleStop = () => {
    this.QrReader.stop();
    this.BarcodeReader.stop();

    this.props.onClose();
  };

  _changeReader = type => {
    this.QrReader.reset();
    this.BarcodeReader.reset();

    this.props.onClose();

    this.setState(
      {
        mode: type,
      },
      () => this._process
    );
  };

  render() {
    return (
      <div className="row scanner-wrapper">
        <video
          className="col-sm-12 viewport scanner-window"
          id="video"
          width="1280"
          height="720"
        />
        <i className="btn-close meta-icon-close-1" onClick={this._handleStop} />
      </div>
    );
  }
}

BarcodeScanner.propTypes = {
  onDetected: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};
