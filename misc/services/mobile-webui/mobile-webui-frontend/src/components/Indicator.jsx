import React, { Component } from 'react';
import PropTypes from 'prop-types';
import * as CompleteStatus from '../constants/CompleteStatus';

class Indicator extends Component {
  getIndicatorColor(completeStatus) {
    switch (completeStatus) {
      case CompleteStatus.NOT_STARTED:
        return 'indicator-red';
      case CompleteStatus.COMPLETED:
        return 'indicator-green';
      case CompleteStatus.IN_PROGRESS:
        return 'indicator-yellow';
      case CompleteStatus.HIDDEN:
        return '';
      default:
        return 'indicator-red';
    }
  }

  render() {
    const { completeStatus } = this.props;
    const indicatorColor = this.getIndicatorColor(completeStatus);

    return <span className={indicatorColor} />;
  }
}

Indicator.propTypes = {
  completeStatus: PropTypes.string.isRequired,
};

export default Indicator;
