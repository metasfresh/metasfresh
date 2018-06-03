import React, { Component } from 'react';

const noOp = () => {};

export default class Label extends Component {
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
