import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

class ButtonWithIndicator extends PureComponent {
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
    const { caption, indicatorType } = this.props;

    const indicatorColor = this.getIndicatorColor(indicatorType);

    return (
      <div className="full-size-btn">
        <div className="left-btn-side"></div>
        <div className="caption-btn">{caption}</div>
        <div className="right-btn-side">
          <span className={indicatorColor}></span>
        </div>
      </div>
    );
  }
}

ButtonWithIndicator.propTypes = {
  caption: PropTypes.string.isRequired,
  indicatorType: PropTypes.string.isRequired,
};

export default ButtonWithIndicator;
