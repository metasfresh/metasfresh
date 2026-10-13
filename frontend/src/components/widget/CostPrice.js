import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';
import { fieldValueToString } from '../../utils/tableHelpers';

// Normalises a typed / pasted number to the backend format; the backend
// BigDecimal parser accepts only digits, "-" and "." and fails with
// NumberFormatException otherwise:
// - strip thousands separators: apostrophes (' and ’), space, NBSP
// - if the value contains a comma (DE/AT decimal comma), the last "." or ","
//   is the decimal separator and every other one a thousands separator:
//   "77,5" -> "77.5", "1.500,50" -> "1500.50", "1,500.50" -> "1500.50"
const normalizeNumberInput = (value) => {
  const withoutGrouping = value.replace(/['\u2019 \u00A0]/g, '');
  if (!withoutGrouping.includes(',')) {
    return withoutGrouping;
  }

  const decimalSeparatorIndex = Math.max(
    withoutGrouping.lastIndexOf('.'),
    withoutGrouping.lastIndexOf(',')
  );
  const integerPart = withoutGrouping
    .substring(0, decimalSeparatorIndex)
    .replace(/[.,]/g, '');
  const fractionPart = withoutGrouping.substring(decimalSeparatorIndex + 1);
  return `${integerPart}.${fractionPart}`;
};

// NOTE: we cannot convert to functional component because we need callers need a ref to this object in order to call the focus() function.
export default class CostPrice extends PureComponent {
  constructor(props) {
    super(props);
    this.inputRef = React.createRef();
  }

  handleBlur = (e) => {
    const { onBlur } = this.props;
    onBlur?.(e);
  };

  focus = () => {
    const { onFocus } = this.props;
    if (this.inputRef.current) {
      this.inputRef.current.focus();
    }
    onFocus?.();
  };

  // Keypress-level filter: reject characters that can never be part of a
  // valid monetary value. Allows digits, decimal separators (. and , — DE/AT
  // users), Swiss thousands separators (apostrophe and typographic ’), space
  // and non-breaking space (Excel/Word paste of numbers like "120 000.50"),
  // and a leading minus sign. Backspace/delete/arrows fire with `e.data == null`
  // and are passed through unchanged.
  handleBeforeInput = (e) => {
    if (e.data == null) return;
    if (!/^[0-9.,'\u2019 \u00A0-]+$/.test(e.data)) {
      e.preventDefault();
    }
  };

  handleChange = (e) => {
    const { onChange } = this.props;
    const raw = e.target.value;
    const cleaned = normalizeNumberInput(raw);
    if (cleaned !== raw) {
      onChange({ ...e, target: { ...e.target, value: cleaned } });
    } else {
      onChange(e);
    }
  };

  render() {
    const {
      autoComplete,
      className,
      disabled,
      placeholder,
      tabIndex,
      title,
      rank,
      value,
      precision,
    } = this.props;
    const { onKeyDown } = this.props;

    // Always render ONE stable <input>: swapping input elements on
    // focus/blur unmounts the focused element mid-Tab and loses focus.
    // type="text" keeps the formatted value (trailing zeros like "19.00");
    // inputMode="decimal" gives the numeric keyboard on mobile. ArrowUp /
    // ArrowDown PATCH is handled by RawWidget.handleKeyDown (NumberWidgets).
    return (
      <input
        ref={this.inputRef}
        type="text"
        inputMode="decimal"
        value={fieldValueToString({ fieldValue: value, precision })}
        autoComplete={autoComplete}
        className={cx(className, rank ? `input-${rank}` : null)}
        disabled={disabled}
        placeholder={placeholder}
        tabIndex={tabIndex}
        title={title}
        //
        onBeforeInput={this.handleBeforeInput}
        onChange={this.handleChange}
        onFocus={this.focus}
        onBlur={this.handleBlur}
        onKeyDown={onKeyDown}
      />
    );
  }
}

CostPrice.propTypes = {
  rank: PropTypes.string,
  value: PropTypes.string,
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
