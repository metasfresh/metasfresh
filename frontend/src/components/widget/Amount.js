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
      isDecimalNumber,
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
        {/* A decimal number is typed into a text input: a browser number input drops the decimal comma (e.g. German
            '3,57' becomes 357) whatever the user's locale, and the typed text is converted by RawWidget. */}
        <input
          {...(isDecimalNumber
            ? { type: 'text', inputMode: 'decimal' }
            : { type: 'number', min: 0, step })}
          value={value}
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
  isDecimalNumber: PropTypes.bool,
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
