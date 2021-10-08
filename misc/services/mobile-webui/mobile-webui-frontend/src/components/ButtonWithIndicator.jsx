import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import Indicator from './Indicator';
class ButtonWithIndicator extends PureComponent {
  render() {
    const { caption, indicatorType } = this.props;

    return (
      <div className="full-size-btn">
        <div className="left-btn-side"></div>
        <div className="caption-btn">{caption}</div>
        <div className="right-btn-side">
          <Indicator indicatorType={indicatorType} />
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
