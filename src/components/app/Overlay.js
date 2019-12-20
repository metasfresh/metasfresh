import React, { PureComponent } from 'react';
import { connect } from 'react-redux';

import { toggleOverlay } from '../../actions/WindowActions';
import QRCode from './QRCode';

class Overlay extends PureComponent {
  handleKeyDown = e => {
    const { toggleOverlay } = this.props;

    if (e.key === 'Escape') {
      toggleOverlay(false);
    }
  };

  componentDidUpdate() {
    const { data } = this.props;

    if (data) {
      document.addEventListener('keydown', this.handleKeyDown);
    }
  }

  componentWillUnmount() {
    document.removeEventListener('keydown', this.handleKeyDown);
  }

  render() {
    const { data, toggleOverlay, showOverlay } = this.props;

    if (!data || !showOverlay) {
      return null;
    }

    return (
      <div className="overlay overlay-field js-not-unselect">
        <QRCode data={data} toggleOverlay={toggleOverlay} />
      </div>
    );
  }
}

export default connect(
  null,
  { toggleOverlay }
)(Overlay);
