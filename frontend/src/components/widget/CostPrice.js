import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';
import { fieldValueToString } from '../../utils/tableHelpers';

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

  // Strip Swiss thousands separators (apostrophe + typographic apostrophe)
  // and whitespace separators (space, non-breaking space) before the value
  // flows upstream — the metasfresh backend's BigDecimal parser
  // (DataTypes.convertToBigDecimal) rejects them with HTTP 500
  // NumberFormatException. Verified by reproducing against ipshotfix:
  //   PATCH … {"op":"replace","path":"PriceEntered","value":"1'500.00"}
  //   → 500 java.lang.NumberFormatException: Character ' is neither …
  // Keeping the keypress filter permissive (apostrophe allowed in the
  // field) makes copy-paste from a price catalogue work; the strip here
  // makes sure the cleaned value is what actually gets PATCHed.
  handleChange = (e) => {
    const { onChange } = this.props;
    const raw = e.target.value;
    const cleaned = raw.replace(/['\u2019 \u00A0]/g, '');
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

    // me03#27080 follow-up: previously this component swapped between a
    // display <input type="text"> (formatted value, no real editing) and
    // an edit <input type="number"> (real editing), toggled via an
    // `editMode` state on focus/blur. The swap unmounted the focused
    // input mid-Tab during PATCH-triggered re-renders, dropping focus
    // to <body> and triggering the modal-wrapper safety net to grab
    // focus into a tabindex=-1 scroll container (arrow-key scrolling
    // instead of next-field navigation). We now render a single stable
    // <input> at all times — never reconciled out from under the user.
    // type="text" with inputMode="decimal" preserves the formatted value
    // (so monetary trailing zeros render as "19.00", not "19" like a
    // type="number" input would normalise), and on mobile still triggers
    // the decimal numeric keyboard. ArrowUp / ArrowDown PATCH semantics
    // continue to work because they are intercepted by
    // RawWidget.handleKeyDown for the NumberWidgets list (CostPrice is
    // in that list).
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
