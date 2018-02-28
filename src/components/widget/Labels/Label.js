import React, { Component } from 'react';

export default class Label extends Component {
  handleClick = () => {
    this.props.onRemove(this.props.label);
  };

  render() {
    return (
      <div className={this.props.className}>
        {this.props.label.caption}
        <span className="labels-label-remove" onClick={this.handleClick}>
          {' '}
          ✕
        </span>
      </div>
    );
  }
}
