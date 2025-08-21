import PropTypes from 'prop-types';
import React from 'react';
import classnames from 'classnames';

const SpinnerOverlay = ({ iconSize, spinnerType = 'overlay' } = {}) => {
  const style = {};
  const iconStyle = {};

  if (iconSize) {
    if (spinnerType === 'inline') {
      style.minHeight = iconSize;
    }

    iconStyle.width = `${iconSize}px`;
    iconStyle.height = `${iconSize}px`;
  }

  console.log('SpinnerOverlay', { spinnerType, iconSize, style, iconStyle });
  return (
    <div
      style={style}
      className={classnames('spinner', `spinner-wrapper-${spinnerType}`, {
        'screen-freeze': spinnerType === 'overlay',
        'screen-prompt-freeze': spinnerType === 'overlay',
      })}
    >
      <i style={iconStyle} className="icon spinner" />
    </div>
  );
};

SpinnerOverlay.defaultProps = {
  iconSize: 32,
};

SpinnerOverlay.propTypes = {
  iconSize: PropTypes.number,
  spinnerType: PropTypes.string,
};

export default SpinnerOverlay;
