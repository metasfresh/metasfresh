/*
 * Code extracted and upgrade from no longer maintained react-file-input project
 * https://github.com/captivationsoftware/react-file-input
 */

import React, { Component } from 'react';

export default class FileInput extends Component {
  state = {
    value: '',
    styles: {
      parent: {
        position: 'relative',
      },
      file: {
        position: 'absolute',
        top: 0,
        left: 0,
        opacity: 0,
        width: '100%',
        zIndex: 1,
      },
      text: {
        position: 'relative',
        zIndex: -1,
      },
    },
  };

  handleChange = event => {
    this.setState({
      value: event.target.value.split(/(\\|\/)/g).pop(),
    });

    if (this.props.onChange) {
      this.props.onChange(event);
    }
  };

  render() {
    return (
      <div style={this.state.styles.parent}>
        <input
          type="file"
          name={this.props.name}
          className={this.props.className}
          onChange={this.handleChange}
          disabled={this.props.disabled}
          accept={this.props.accept}
          style={this.state.styles.file}
        />
        <input
          type="text"
          tabIndex="-1"
          name={`${this.props.name}_filename`}
          value={this.state.value}
          className={this.props.className}
          onChange={() => {}}
          placeholder={this.props.placeholder}
          disabled={this.props.disabled}
          style={this.state.styles.text}
        />
      </div>
    );
  }
}
