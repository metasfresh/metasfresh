import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Quagga from 'quagga';

const BarcodeScannerResult = function({ result, onSelect }) {
  if (!result) {
    return null;
  }
  return (
    <button
      className="btn btn-filter btn-meta-outline-secondary btn-distance btn-s"
      onClick={() => onSelect(result)}
    >
      {result.codeResult.code}
    </button>
  );
};

BarcodeScannerResult.propTypes = {
  result: PropTypes.object,
  onSelect: PropTypes.func.isRequired,
};

export default class BarcodeScanner extends Component {
  componentDidMount() {
    Quagga.init(
      {
        inputStream: {
          type: 'LiveStream',
          constraints: {
            width: 640,
            height: 480,
            facingMode: 'environment',
          },
        },
        locator: {
          patchSize: 'medium',
          halfSample: true,
        },
        numOfWorkers: 4,
        frequency: 10,
        decoder: {
          readers: ['ean_reader'],
        },
        locate: true,
      },
      err => {
        if (err) {
          return console.log(err);
        }
        Quagga.start();
      }
    );
    Quagga.onDetected(this._onDetected);
    Quagga.onProcessed(this._onProcessed);
  }

  componentWillUnmount() {
    Quagga.offDetected(this._onDetected);
  }

  _handleStop = () => {
    Quagga.stop();

    this.props.onClose();
  }

  _handleStart = () => {
    this.props.onReset();
    Quagga.start();
  }

  _onDetected = result => {
    Quagga.stop();
    this.props.onDetected(result);
  }

  _onProcessed(result) {
    const drawingCtx = Quagga.canvas.ctx.overlay;
    const drawingCanvas = Quagga.canvas.dom.overlay;

    if (result) {
      if (result.boxes) {
        drawingCtx.clearRect(
          0,
          0,
          parseInt(drawingCanvas.getAttribute('width')),
          parseInt(drawingCanvas.getAttribute('height'))
        );
        result.boxes.filter(box => box !== result.box).forEach(box => {
          Quagga.ImageDebug.drawPath(box, { x: 0, y: 1 }, drawingCtx, {
            color: 'green',
            lineWidth: 2,
          });
        });
      }

      if (result.box) {
        Quagga.ImageDebug.drawPath(result.box, { x: 0, y: 1 }, drawingCtx, {
          color: '#00F',
          lineWidth: 2,
        });
      }

      if (result.codeResult && result.codeResult.code) {
        Quagga.ImageDebug.drawPath(
          result.line,
          { x: 'x', y: 'y' },
          drawingCtx,
          { color: 'red', lineWidth: 3 }
        );
      }
    }
  }

  render() {
    return (
      <div className="scanner-wrapper">
        <div id="interactive" className="viewport scanner-window"/>
        <div className="scanner-controls">
          <button onClick={this._handleStart}>
            Scan again
          </button>
          <button onClick={this._handleStop}>
            Close
          </button>
        </div>
      </div>
    );
  }
}

BarcodeScanner.propTypes = {
  onDetected: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
  onReset: PropTypes.func.isRequired,
};

export { BarcodeScannerResult };
