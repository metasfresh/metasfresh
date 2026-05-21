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
    const { onChange, onKeyDown } = this.props;

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
        onChange={onChange}
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
