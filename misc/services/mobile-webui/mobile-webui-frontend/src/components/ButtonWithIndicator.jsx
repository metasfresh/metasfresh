import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import Indicator from './Indicator';
class ButtonWithIndicator extends PureComponent {
  render() {
    const { caption, completeStatus } = this.props;

    return (
      <div className="full-size-btn">
        <div className="left-btn-side" />
        <div className="caption-btn">{caption}</div>
        {completeStatus && (
          <div className="right-btn-side">
            <Indicator completeStatus={completeStatus} />
          </div>
        )}
      </div>
    );
  }
}

ButtonWithIndicator.propTypes = {
  caption: PropTypes.string.isRequired,
  completeStatus: PropTypes.string,
};

export default ButtonWithIndicator;
