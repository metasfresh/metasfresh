import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

const noOp = () => {};

/**
 * @file Class based component.
 * @module Label
 * @extends Component
 */
class Label extends PureComponent {
  /**
   * @method handleClick
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleClick = () => {
    const { onClick, label } = this.props;

    onClick(label);
  };

  /**
   * @method handleRemove
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleRemove = (e) => {
    e.stopPropagation();
    const { onRemove, label, readonly } = this.props;

    if (readonly) return false;

    onRemove(label);
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const { label } = this.props;

    return (
      <span className="labels-label" onClick={this.handleClick}>
        {label.caption}
        <span className="labels-label-remove" onClick={this.handleRemove}>
          {' '}
          ✕
        </span>
      </span>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {*} label
 * @prop {func} [onClick]
 * @prop {func} [onRemove]
 * @todo Check props. Which proptype? Required or optional?
 */
Label.propTypes = {
  label: PropTypes.shape({
    caption: PropTypes.node,
  }).isRequired,
  onClick: PropTypes.func,
  onRemove: PropTypes.func,
  readonly: PropTypes.bool,
};

Label.defaultProps = {
  onClick: noOp,
  onRemove: noOp,
};

export default Label;
