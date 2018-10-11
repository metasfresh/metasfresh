import React, { Component } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';

import { toggleOverlay } from '../../actions/WindowActions';

class Overlay extends Component {
  handleKeyDown = e => {
    const { toggleOverlay } = this.props;

    if (e.key === 'Escape') {
      toggleOverlay();
    }
  };

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
      <div
        onKeyDown={this.handleKeyDown}
        className="overlay overlay-field js-not-unselect"
      >
        <div id="qr-code" />
      </div>
    );
  }
}

export default connect(null, { toggleOverlay })(onClickOutside(Overlay));
