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
    this.setState({ editMode: true }, () => {
      this.inputRef?.current?.focus?.();
    });
  };

  render() {
    const { className, rank, value, precision } = this.props;
    const { editMode } = this.state;

    if (!editMode) {
      return (
        <input
          className={cx(
            className,
            'input-field js-input-field',
            rank ? `input-${rank}` : null
          )}
          value={fieldValueToString({ fieldValue: value, precision })}
          type="text"
          onChange={() => false}
          onFocus={this.focus}
        />
      );
    } else {
      return (
        <input
          {...this.props}
          className={cx(className, rank ? `input-${rank}` : null)}
          onBlur={this.handleBlur}
          type="number"
          ref={this.inputRef}
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
  onChange: PropTypes.func.isRequired,
  onFocus: PropTypes.func,
  onBlur: PropTypes.func.isRequired,
  onKeyDown: PropTypes.func,
};
