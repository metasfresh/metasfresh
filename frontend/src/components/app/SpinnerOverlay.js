import PropTypes from 'prop-types';
import React from 'react';
import classnames from 'classnames';

/**
 * @file functional component
 * @module SpinnerOverlay
 */
const SpinnerOverlay = (props) => {
  const { iconSize, spinnerType } = props;
  let style = {};

  if (iconSize) {
    style = {
      width: `${iconSize}px`,
      height: `${iconSize}px`,
    };
  }

  return (
    <div
      className={classnames('screen-freeze screen-prompt-freeze spinner', {
        'modal-spinner': spinnerType === 'modal',
      })}
    >
      <i style={style} className="icon spinner" />
    </div>
  );
};

SpinnerOverlay.defaultProps = {
  iconSize: 32,
};

/**
 * @typedef {object} Props Component props
 * @prop {number} [iconSize]
 */
SpinnerOverlay.propTypes = {
  iconSize: PropTypes.number,
  spinnerType: PropTypes.string,
};

export default SpinnerOverlay;
