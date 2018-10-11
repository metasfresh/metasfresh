import React, { Component } from 'react';
import onClickOutside from 'react-onclickoutside';
import { BrowserQRCodeSvgWriter } from '@zxing/library';

class QRCode extends Component {
  constructor(props) {
    super(props);

    this.width = 300;
    this.height = 300;
  }
  componentDidMount() {
    const { data } = this.props;

    if (data) {
      const codeWriter = new BrowserQRCodeSvgWriter('qr-code');

      codeWriter.write(data.data, 300, 300);
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

export default onClickOutside(QRCode);
