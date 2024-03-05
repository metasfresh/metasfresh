import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { fieldValueToString } from '../../utils/tableHelpers';

export default class CostPrice extends PureComponent {
  constructor(props) {
    super(props);
    this.costRef = React.createRef();
    this.state = { editMode: false };
  }

  /**
   * @method localOnBlur
   * @summary - proxy onBlur that also sets the visibility before passing further the event to the onBlur function from the props
   */
  localOnBlur = (e) => {
    const { onBlur } = this.props;
    this.setState({ editMode: false });
    onBlur(e);
  };

  focus = () => {
    this.setState({ editMode: true }, () => {
      this.costRef.current.focus();
    });
  };

  render() {
    const { value, precision, disabled } = this.props;
    const { editMode } = this.state;

    return (
      <div>
        {!editMode && (
          <input
            className="input-field js-input-field"
            value={fieldValueToString({ fieldValue: value, precision })}
            type="text"
            onChange={() => false}
            onFocus={this.focus}
            disabled={disabled}
          />
        )}

        {editMode && (
          <input
            {...this.props}
            onBlur={this.localOnBlur}
            type="number"
            ref={this.costRef}
          />
        )}
      </div>
    );
  }
}

CostPrice.propTypes = {
  onChange: PropTypes.func.isRequired,
  onBlur: PropTypes.func.isRequired,
  value: PropTypes.string,
  precision: PropTypes.number,
  disabled: PropTypes.bool,
};
