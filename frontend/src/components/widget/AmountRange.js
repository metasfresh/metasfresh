import React, { forwardRef, useCallback } from 'react';
import Amount from './Amount';
import PropTypes from 'prop-types';

const AmountRange = forwardRef(
  (
    {
      widgetField,
      valueFrom,
      valueTo,
      step,
      devices,
      //
      id,
      autoComplete,
      className,
      inputClassName,
      disabled,
      placeholder,
      tabIndex,
      title,
      //
      onChange,
      onFocus,
      onBlur,
      onKeyDown,
      onPatch,
    },
    ref
  ) => {
    const valueFrom_onChange = useCallback(
      (e) => onChange?.(e, false),
      [onChange]
    );
    const valueFrom_onFocus = useCallback(
      (e) => onFocus?.(e, false),
      [onFocus]
    );
    const valueFrom_onBlur = useCallback((e) => onBlur?.(e, false), [onBlur]);
    const valueFrom_onKeyDown = useCallback(
      (e) => onKeyDown?.(e, false),
      [onKeyDown]
    );
    const valueFrom_onPatch = useCallback(
      (field, value) => onPatch?.(field, value, /*id*/ undefined, valueTo),
      [onPatch, valueTo]
    );

    //
    //
    //

    const valueTo_onChange = useCallback(
      (e) => onChange?.(e, true),
      [onChange]
    );
    const valueTo_onFocus = useCallback((e) => onFocus?.(e, true), [onFocus]);
    const valueTo_onBlur = useCallback((e) => onBlur?.(e, true), [onBlur]);
    const valueTo_onKeyDown = useCallback(
      (e) => onKeyDown?.(e, true),
      [onKeyDown]
    );
    const valueTo_onPatch = useCallback(
      (field, value) => onPatch?.(field, valueFrom, /*id*/ undefined, value),
      [onPatch, valueFrom]
    );

    //
    //
    //

    return (
      <div className="input-range-container">
        <Amount
          ref={ref}
          widgetField={widgetField}
          value={valueFrom}
          step={step}
          devices={devices}
          //
          id={id}
          autoComplete={autoComplete}
          className={className}
          inputClassName={inputClassName}
          disabled={disabled}
          placeholder={placeholder}
          tabIndex={tabIndex}
          title={title}
          //
          onChange={valueFrom_onChange}
          onFocus={valueFrom_onFocus}
          onBlur={valueFrom_onBlur}
          onKeyDown={valueFrom_onKeyDown}
          onPatch={valueFrom_onPatch}
        />
        <div className="input-range-separator">-</div>
        <Amount
          //ref={ref}
          widgetField={widgetField}
          value={valueTo}
          step={step}
          devices={devices}
          //
          //id={id}
          autoComplete={autoComplete}
          className={className}
          inputClassName={inputClassName}
          disabled={disabled}
          placeholder={placeholder}
          //tabIndex={tabIndex}
          //title={title}
          //
          onChange={valueTo_onChange}
          onFocus={valueTo_onFocus}
          onBlur={valueTo_onBlur}
          onKeyDown={valueTo_onKeyDown}
          onPatch={valueTo_onPatch}
        />
      </div>
    );
  }
);

AmountRange.displayName = 'AmountRange';
AmountRange.propTypes = {
  widgetField: PropTypes.string.isRequired,
  valueFrom: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  valueTo: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  step: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  devices: PropTypes.any,
  //
  id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  autoComplete: PropTypes.string,
  className: PropTypes.func.isRequired,
  inputClassName: PropTypes.string,
  disabled: PropTypes.bool,
  placeholder: PropTypes.string,
  tabIndex: PropTypes.number,
  title: PropTypes.string,
  //
  onChange: PropTypes.func.isRequired,
  onFocus: PropTypes.func,
  onBlur: PropTypes.func.isRequired,
  onKeyDown: PropTypes.func,
  onPatch: PropTypes.func.isRequired,
};

export default AmountRange;
