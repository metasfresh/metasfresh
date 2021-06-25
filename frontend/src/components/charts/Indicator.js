import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Loader from '../app/Loader';

class Indicator extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const {
      amount,
      unit,
      caption,
      loader,
      fullWidth,
      editmode,
      framework,
      zoomToDetailsAvailable,
    } = this.props;

    if (loader)
      return (
        <div className="indicator">
          <Loader />
        </div>
      );

    return (
      <div
        className={
          'indicator js-indicator ' +
          (editmode || framework ? 'indicator-draggable ' : '')
        }
        style={fullWidth ? { width: '100%' } : {}}
      >
        <div>
          <div className="indicator-kpi-caption">{caption}</div>
          {/* TODO: !!! this needs not to be hardcoded and must be provided by the BE */}
          {zoomToDetailsAvailable && (
            <div className="indicator-details-link">DETAILS</div>
          )}
        </div>
        <div className="indicator-data">
          <div className="indicator-amount">{amount}</div>
          <div className="indicator-unit">{unit}</div>
        </div>
      </div>
    );
  }
}

Indicator.propTypes = {
  value: PropTypes.any,
  caption: PropTypes.string,
  loader: PropTypes.any,
  fullWidth: PropTypes.bool,
  editmode: PropTypes.bool,
  framework: PropTypes.any,
  amount: PropTypes.number,
  unit: PropTypes.string,
  zoomToDetailsAvailable: PropTypes.bool,
};

export default Indicator;
