import React, { Component } from 'react';
import PropTypes from 'prop-types';

class BarcodeScanner extends Component {
  render() {
    const { barcodeCaption } = this.props.componentProps;
    const scanBtnCaption = barcodeCaption || 'Scan';
    return (
      <div>
        <div className="buttons">
          <button className="button scanner-btn-green">{scanBtnCaption}</button>
        </div>
      </div>
    );
  }
}

BarcodeScanner.propTypes = {
  componentProps: PropTypes.object.isRequired,
};

export default BarcodeScanner;
