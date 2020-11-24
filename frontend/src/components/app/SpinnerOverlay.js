import PropTypes from 'prop-types';
import React from 'react';

/**
 * @file functional component
 * @module SpinnerOverlay
 */
const SpinnerOverlay = (props) => {
  const { iconSize } = props;
  let style = {};

  if (iconSize) {
    style = {
      width: `${iconSize}px`,
      height: `${iconSize}px`,
    };
  }

  return (
    <div className="screen-freeze screen-prompt-freeze spinner">
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
};

export default SpinnerOverlay;
