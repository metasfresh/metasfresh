import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';
import { fieldValueToString } from '../../utils/tableHelpers';

// NOTE: we cannot convert to functional component because we need callers need a ref to this object in order to call the focus() function.
export default class CostPrice extends PureComponent {
  constructor(props) {
    super(props);
    this.inputRef = React.createRef();
    this.state = { editMode: false };
  }

  handleBlur = (e) => {
    const { onBlur } = this.props;
    this.setState({ editMode: false });
    onBlur?.(e);
  };

  focus = () => {
    const { onFocus } = this.props;
    this.setState({ editMode: true }, () => {
      this.inputRef.current.focus();
      onFocus?.();
    });
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
    const { onChange, onFocus, onKeyDown } = this.props;
    const { editMode } = this.state;

    if (!editMode) {
      return (
        <input
          className={cx(
            className,
            'input-field js-input-field',
            rank ? `input-${rank}` : null
          )}
          type={'text'}
          value={fieldValueToString({ fieldValue: value, precision })}
          onChange={() => false}
          onFocus={this.focus}
        />
      );
    } else {
      return (
        <input
          ref={this.inputRef}
          type={'number'}
          value={value}
          autoComplete={autoComplete}
          className={cx(className, rank ? `input-${rank}` : null)}
          disabled={disabled}
          placeholder={placeholder}
          tabIndex={tabIndex}
          title={title}
          //
          onChange={onChange}
          onFocus={onFocus}
          onBlur={this.handleBlur}
          onKeyDown={onKeyDown}
        />
      );
    }
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
