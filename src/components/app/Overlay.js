import React, { Component } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import { BrowserQRCodeSvgWriter } from '@zxing/library';

import { toggleOverlay } from '../../actions/WindowActions';

class Overlay extends Component {
  handleKeyDown = e => {
    const { toggleOverlay } = this.props;

    if (e.key === 'Escape') {
      toggleOverlay();
    }
  };

  componentDidUpdate() {
    const { data } = this.props;

    if (data) {
      document.addEventListener('keydown', this.handleKeyDown);

      const codeWriter = new BrowserQRCodeSvgWriter('qr-code');

      codeWriter.write(data.data, 300, 300);
    }
  }

  componentWillUnmount() {
    document.removeEventListener('keydown', this.handleKeyDown);
  }

  handleClickOutside = () => {
    const { toggleOverlay } = this.props;

    toggleOverlay();
  };

  render() {
    const { data } = this.props;

    if (!data) {
      return null;
    }

    return (
      <div className="overlay overlay-field js-not-unselect">
        <div id="qr-code" />
      </div>
    );
  }
}

export default connect(null, { toggleOverlay })(onClickOutside(Overlay));
