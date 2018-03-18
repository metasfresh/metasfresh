import React, { Component } from 'react';
import PropTypes from 'prop-types';

export default class SelectionDropdown extends Component {
  static propTypes = {
    options: PropTypes.oneOfType([
      PropTypes.array,
      PropTypes.shape({
        map: PropTypes.func.isRequired,
        includes: PropTypes.func.isRequired,
      }),
    ]).isRequired,
    selected: (props, propName) => {
      const selected = props[propName];

      if ([null, undefined].includes(selected)) {
        return;
      }

      if (!props.options.includes(selected)) {
        return new Error(
          `${propName} must be one of options. ${propName}: ${selected}`
        );
      }
    },
    width: PropTypes.number.isRequired,
  };

  renderOption = option => {
    const { key, caption } = option;

    return (
      <div key={`${key}${caption}`} className="input-dropdown-list-option">
        {caption}
      </div>
    );
  };

  render() {
    const { options, width } = this.props;

    return (
      <div className="input-dropdown-list" style={{ width }}>
        {options.map(this.renderOption)}
      </div>
    );
  }
}
