import React, { Component } from 'react';
import PropTypes from 'prop-types';

const noOp = () => {};

export default class Label extends Component {
  static propTypes = {
    label: PropTypes.shape({
      caption: PropTypes.node,
    }).isRequired,
    onClick: PropTypes.func,
    onRemove: PropTypes.func,
  };

  static defaultProps = {
    onClick: noOp,
    onRemove: noOp,
  };

  handleClick = () => {
    const { onClick, label } = this.props;

    onClick(label);
  };

  handleRemove = () => {
    const { onRemove, label } = this.props;

    onRemove(label);
  };

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
