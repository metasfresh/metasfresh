import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import DevicesWidget from './Devices/DevicesWidget';

export default class Amount extends PureComponent {
  render() {
    const {
      widgetField,
      value,
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
    } = this.props;

    return (
      <div
        className={classnames(
          typeof className === 'function' ? className() : className,
          'number-field'
        )}
      >
        <input
          type="number"
          value={value}
          min={0}
          step={step}
          id={id}
          autoComplete={autoComplete}
          className={inputClassName}
          disabled={disabled}
          placeholder={placeholder}
          tabIndex={tabIndex}
          title={title}
          onChange={onChange}
          onFocus={onFocus}
          onBlur={onBlur}
          onKeyDown={onKeyDown}
        />
        {devices && (
          <div className="device-widget-wrapper">
            <DevicesWidget
              devices={devices}
              tabIndex={1}
              handleChange={(valueFromDevice) =>
                onPatch?.(widgetField, valueFromDevice)
              }
            />
          </div>
        )}
      </div>
    );
  }
}

Amount.propTypes = {
  widgetField: PropTypes.string.isRequired,
  value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  step: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  devices: PropTypes.any,
  //
  id: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  autoComplete: PropTypes.string,
  className: PropTypes.oneOfType([PropTypes.string, PropTypes.func]).isRequired,
  inputClassName: PropTypes.string,
  disabled: PropTypes.bool,
  placeholder: PropTypes.string,
  tabIndex: PropTypes.number,
  title: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  //
  onChange: PropTypes.func.isRequired,
  onFocus: PropTypes.func,
  onBlur: PropTypes.func.isRequired,
  onKeyDown: PropTypes.func,
  onPatch: PropTypes.func.isRequired,
};
