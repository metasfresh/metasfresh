import React, { Component } from 'react';

export default class Label extends Component {
  handleClick = () => {
    const { onRemove, label } = this.props;

    onRemove(label);
  };

  render() {
    const { label } = this.props;

    return (
      <div className="labels-label">
        {label.caption}
        <span className="labels-label-remove" onClick={this.handleClick}>
          {' '}
          ✕
        </span>
      </div>
    );
  }
}
