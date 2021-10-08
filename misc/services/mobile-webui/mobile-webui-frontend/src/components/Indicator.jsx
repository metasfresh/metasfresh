import React, { Component } from 'react';
import PropTypes from 'prop-types';

class Indicator extends Component {
  getIndicatorColor(indicatorType) {
    switch (indicatorType) {
      case 'incomplete':
        return 'indicator-red';
      case 'complete':
        return 'indicator-green';
      case 'pending':
        return 'indicator-yellow';
      default:
        return 'indicator-red';
    }
  }

  render() {
    const { indicatorType } = this.props;
    const indicatorColor = this.getIndicatorColor(indicatorType);
    return <span className={indicatorColor}></span>;
  }
}

Indicator.propTypes = {
  indicatorType: PropTypes.string.isRequired,
};

export default Indicator;
