import React, { forwardRef } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';
import CostPrice from './CostPrice';

const CostPriceRange = forwardRef((props, ref) => {
  const {
    rank,
    valueFrom,
    valueTo,
    precision,
    autoComplete,
    className,
    disabled,
    placeholder,
    tabIndex,
    title,
    onChange,
    onFocus,
    onBlur,
    onKeyDown,
  } = props;
  return (
    <div className="input-range-container">
      <CostPrice
        ref={ref}
        rank={rank}
        value={valueFrom}
        precision={precision}
        autoComplete={autoComplete}
        className={cx(className, 'input-range-from')}
        disabled={disabled}
        placeholder={placeholder}
        tabIndex={tabIndex}
        title={title}
        onChange={(e) => onChange?.(e, false)}
        onFocus={(e) => onFocus?.(e, false)}
        onBlur={(e) => onBlur?.(e, false)}
        onKeyDown={(e) => onKeyDown?.(e, false)}
      />
      <div className="input-range-separator">-</div>
      <CostPrice
        rank={rank}
        value={valueTo}
        precision={precision}
        autoComplete={autoComplete}
        className={cx(className, 'input-range-to')}
        disabled={disabled}
        placeholder={placeholder}
        tabIndex={tabIndex}
        //title
        onChange={(e) => onChange?.(e, true)}
        onFocus={(e) => onFocus?.(e, true)}
        onBlur={(e) => onBlur?.(e, true)}
        onKeyDown={(e) => onKeyDown?.(e, true)}
      />
    </div>
  );
});

CostPriceRange.displayName = 'CostPriceRange';
CostPriceRange.propTypes = {
  rank: PropTypes.string,
  valueFrom: PropTypes.string,
  valueTo: PropTypes.string,
  precision: PropTypes.number,
  autoComplete: PropTypes.string,
  className: PropTypes.string,
  disabled: PropTypes.bool,
  placeholder: PropTypes.string,
  tabIndex: PropTypes.number,
  title: PropTypes.string,
  //
  onChange: PropTypes.func.isRequired,
  onFocus: PropTypes.func,
  onBlur: PropTypes.func.isRequired,
  onKeyDown: PropTypes.func,
};

export default CostPriceRange;
