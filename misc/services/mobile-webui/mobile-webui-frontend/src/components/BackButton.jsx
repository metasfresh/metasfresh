import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { stopScanning } from '../actions/ScanActions';

class BackButton extends Component {
  /**
   * Execute the function passed in when this button is clicked.
   */
  handleClick = () => {
    const { onClickExec, isScannerActive, stopScanning } = this.props;
    if (isScannerActive) {
      stopScanning();
    } else {
      onClickExec();
    }
  };

  render() {
    return (
      <span className="btn-icon" onClick={this.handleClick}>
        <i className="icon-chevron-left-solid" />
      </span>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    isScannerActive: state.scanner.active,
  };
};

BackButton.propTypes = {
  caption: PropTypes.string,
  onClickExec: PropTypes.func.isRequired,
  isScannerActive: PropTypes.bool.isRequired,
  stopScanning: PropTypes.func.isRequired,
};

export default connect(mapStateToProps, { stopScanning })(BackButton);
