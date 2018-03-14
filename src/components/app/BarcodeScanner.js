import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Quagga from 'quagga';

const BarcodeScannerResult = function({ result, onSelect }) {
  if (!result) {
    return null;
  }
  return (
    <div className="col-xs-9">
      <button
        className="btn btn-filter btn-meta-outline-secondary btn-distance btn-s"
        onClick={() => onSelect(result)}
      >
        {result.codeResult.code}
      </button>
    </div>
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
            width: 1280,
            height: 720,
            facingMode: 'environment',
          },
        },
        locator: {
          patchSize: 'medium',
          halfSample: true,
        },
        numOfWorkers: 4,
        frequency: 5,
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

    if (this.props.debug) {
      Quagga.onProcessed(this._onProcessed);
    }
  }

  componentWillUnmount() {
    Quagga.offDetected(this._onDetected);
  }

  _handleStop = () => {
    Quagga.offDetected(this._onDetected);

    this.props.onClose();
  };

  _handleStart = () => {
    this.props.onReset();
    Quagga.onDetected(this._onDetected);
  };

  _onDetected = result => {
    Quagga.offDetected(this._onDetected);
    this.props.onDetected(result);
  };

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
    const { result } = this.props;

    return (
      <div className="row scanner-wrapper">
        <div id="interactive" className="col-sm-12 viewport scanner-window" />
        <div className="col-sm-12 scanner-controls">
          <button
            disabled={!result}
            className="btn btn-filter btn-meta-outline-secondary btn-distance btn-s"
            onClick={this._handleStart}
          >
            Scan again
          </button>
          <button
            className="btn btn-filter btn-meta-outline-secondary btn-distance btn-s"
            onClick={this._handleStop}
          >
            Close
          </button>
        </div>
      </div>
    );
  }
}

BarcodeScanner.propTypes = {
  result: PropTypes.object,
  onDetected: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
  onReset: PropTypes.func.isRequired,
};

export { BarcodeScannerResult };
