import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';

const Tooltips = ({
  action,
  className,
  delay,
  extraClass,
  name,
  tooltipOnFirstlevelPositionLeft,
  type,
}) => {
  const [opacity, setOpacity] = useState(0);

  useEffect(() => {
    const timeout = setTimeout(
      () => {
        setOpacity(1);
      },
      delay ? delay : 1000
    );

    return () => {
      clearTimeout(timeout);
    };
  }, []);

  const cx = classNames(
    'tooltip-wrapp',
    { [`tooltip-${type}`]: type },
    { [`${extraClass}`]: extraClass },
    { [`${className}`]: className }
  );

  return (
    <div style={{ opacity: opacity }}>
      <div
        className={cx}
        style={{ left: tooltipOnFirstlevelPositionLeft + 'px' }}
      >
        <div className="tooltip-shortcut">{name}</div>
        <div className="tooltip-name">{action}</div>
      </div>
    </div>
  );
};

/**
 * @typedef {object} Props Component props
 * @prop {String} action - i.e. the actual tooltip caption (displayed beneath the shortcut)
 * @prop {String} className
 * @prop {Number} delay
 * @prop {String} extraClass
 * @prop {String} name - i.e. shortcut
 * @prop {*} tooltipOnFirstlevelPositionLeft - deprecated, looks that is no longer used
 * @prop {String} type - deprecated, looks that is no longer used (i.e. it's always empty string)
 */
Tooltips.propTypes = {
  action: PropTypes.string,
  className: PropTypes.string,
  delay: PropTypes.number,
  extraClass: PropTypes.string,
  name: PropTypes.string,
  tooltipOnFirstlevelPositionLeft: PropTypes.number,
  type: PropTypes.string,
};

export default Tooltips;
