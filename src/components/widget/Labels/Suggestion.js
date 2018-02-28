import React, { Component } from 'react';

export default class Suggestion extends Component {
  handleMouseDown = () => {
    this.props.onAdd(this.props.suggestion);
  };

  render() {
    const classes = [this.props.className];

    if (this.props.active) {
      classes.push('active');
    }

    return (
      <div className={classes.join(' ')} onMouseDown={this.handleMouseDown}>
        {this.props.suggestion.caption}
      </div>
    );
  }
}
