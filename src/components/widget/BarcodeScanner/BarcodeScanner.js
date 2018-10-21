import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { BrowserBarcodeReader } from '@zxing/library';
import classnames from 'classnames';

import BrowserQRCodeReader from '../../../services/CustomBrowserQRCodeReader';

export default class BarcodeScanner extends Component {
  constructor(props) {
    super(props);

    this.state = {
      mode: 'Qr',
    };

    this.reader = new BrowserQRCodeReader();
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
      .then(result => this._onDetected(result))
      // eslint-disable-next-line no-console
      .catch(() => {
        this._changeReader();
      });
  };

  _onDetected = result => {
    this._handleStop(false);
    this.props.onDetected(result.text);
  };

  _handleStop = close => {
    this.reader.stop();

    close && this.props.onClose();
  };

  _changeReader = () => {
    this.reader.stop();
    this.reader = new BrowserBarcodeReader();

    this.props.onClose(true);

    this.setState(
      {
        mode: 'Barcode',
      },
      () => this._process()
    );
  };

  render() {
    const { mode } = this.state;

    return (
      <div className="row scanner-wrapper">
        <div className="scan-mode">
          Scan mode:
          <i
            className={classnames('btn-control btn-mode', {
              [`btn-${mode}`]: mode,
            })}
            title={`Scan ${mode}`}
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
  onDetected: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};
