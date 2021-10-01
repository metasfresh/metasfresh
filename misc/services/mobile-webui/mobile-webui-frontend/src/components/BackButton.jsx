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

/*<div className="container">
  <div
    className="columns pl-5 is-mobile"
    onClick={() => {
      navigation.removeViewFromHistory();
      history.goBack();
    }}
  >
    <div className="column is-flex is-11 is-size-3 green-color mt-1">
      <div className="mt-2">
        <i className="fas fa-chevron-left" />
      </div>
      <div className="pt-1 pl-2 nav-text">
        <span className="is-size-4">{text}</span>
      </div>
    </div>
  </div>
</div>*/
