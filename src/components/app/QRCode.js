import React, { Component } from 'react';
import PropTypes from 'prop-types';
import onClickOutside from 'react-onclickoutside';
import { BrowserQRCodeSvgWriter } from '@zxing/library';
import currentDevice from 'current-device';

class QRCode extends Component {
  constructor(props) {
    super(props);

    let val = 400;
    if (currentDevice.type === 'mobile') {
      val = 300;
    }

    this.width = val;
    this.height = val;
  }
  componentDidMount() {
    const { data } = this.props;

    if (data) {
      const codeWriter = new BrowserQRCodeSvgWriter();

      codeWriter.writeToDom('#qr-code', data.data, this.width, this.height);
    }
  }

  componentWillUnmount() {
    this.handleClickOutside();
  }

  handleClickOutside = () => {
    const { toggleOverlay } = this.props;

    toggleOverlay(false);
  };

  render() {
    return (
      <div className="qr-bg">
        <div id="qr-code" />
      </div>
    );
  }
}

QRCode.propTypes = {
  data: PropTypes.object,
  toggleOverlay: PropTypes.func,
};

export default onClickOutside(QRCode);
