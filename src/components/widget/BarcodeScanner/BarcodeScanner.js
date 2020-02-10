import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { BrowserBarcodeReader } from '@zxing/library';
import { BrowserDatamatrixCodeReader } from '@zxing/library';
import classnames from 'classnames';

import BrowserQRCodeReader from '../../../services/CustomBrowserQRCodeReader';

export default class BarcodeScanner extends Component {
  constructor(props) {
    super(props);

    const barcodeScannerType = props.barcodeScannerType
      ? props.barcodeScannerType
      : 'qrCode';

    this.state = {
      barcodeScannerType: barcodeScannerType,
    };

    if (barcodeScannerType == 'qrCode') {
      this.reader = new BrowserQRCodeReader();
    } else if (barcodeScannerType == 'barcode') {
      this.reader = new BrowserBarcodeReader();
    } else if (barcodeScannerType == 'datamatrix') {
      this.reader = new BrowserDatamatrixCodeReader();
    } else {
      throw new Error('Unknown barcodeScannerType: ' + barcodeScannerType);
    }
  }

  componentDidMount() {
    this._process();
  }

  componentWillUnmount() {
    this._handleStop(false);
  }

  _process = () => {
    this.reader
      .decodeFromInputVideoDevice(undefined, 'video')
      .then((result) => this._onDetected(result))
      .catch(() => {
        this._changeReader();
      });
  };

  _onDetected = (result) => {
    this._handleStop(false);
    this.props.onDetected(result.text);
  };

  _handleStop = (close) => {
    this.reader.stopStreams();

    close && this.props.onClose();
  };

  _changeReader = () => {
    const { barcodeScannerType } = this.state;

    this.reader.stopStreams();

    if (barcodeScannerType == 'qrCode') {
      this.reader = new BrowserBarcodeReader();
      this.props.onClose(true);
      this.setState(
        {
          barcodeScannerType: 'barcode',
        },
        () => this._process()
      );
    }
  };

  render() {
    const { barcodeScannerType } = this.state;

    return (
      <div className="row scanner-wrapper">
        <div className="scan-mode">
          Scan mode:
          <i
            className={classnames('btn-control btn-mode', {
              [`btn-${barcodeScannerType}`]: barcodeScannerType,
            })}
            title={`Scan ${barcodeScannerType}`}
          />
        </div>
        <video
          className="col-sm-12 viewport scanner-window"
          id="video"
          width="1280"
          height="720"
        />
        <i
          className="btn-control btn-close meta-icon-close-1"
          onClick={this._handleStop}
        />
      </div>
    );
  }
}

BarcodeScanner.propTypes = {
  barcodeScannerType: PropTypes.string,
  onDetected: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};
