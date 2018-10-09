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
    this._handleStop(false);
  }

  _process = () => {
    const reader = this[`${this.state.mode}Reader`];

    reader
      .decodeFromInputVideoDevice(undefined, 'video')
      .then(result => this._onDetected(result))
      .catch(err => console.error(err));
  };

  _onDetected = result => {
    this._handleStop(false);
    this.props.onDetected(result.text);
  };

  _handleStop = close => {
    this.QrReader.stop();
    this.BarcodeReader.stop();

    close && this.props.onClose();
  };

  _changeReader = type => {
    this.QrReader.reset();
    this.BarcodeReader.reset();

    this.props.onClose(true);

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
