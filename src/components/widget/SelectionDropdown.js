import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';

export default class SelectionDropdown extends Component {
  static propTypes = {
    options: PropTypes.oneOfType([
      PropTypes.array,
      PropTypes.shape({
        map: PropTypes.func.isRequired,
        includes: PropTypes.func.isRequired,
        size: PropTypes.number.isRequired,
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
    loading: PropTypes.bool,
  };

  renderHeader = children => {
    return (
      <div className="input-dropdown-list-option input-dropdown-list-header">
        {children}
      </div>
    );
  };

  renderOption = option => {
    const { key, caption } = option;

    return (
      <div key={`${key}${caption}`} className="input-dropdown-list-option">
        {caption}
      </div>
    );
  };

  empty = this.renderHeader('There is no choice available');

  loading = this.renderHeader(
    <ReactCSSTransitionGroup
      transitionName="rotate"
      transitionEnterTimeout={1000}
      transitionLeaveTimeout={1000}
    >
      <div className="rotate icon-rotate">
        <i className="meta-icon-settings" />
      </div>
    </ReactCSSTransitionGroup>
  );

  render() {
    const { options, width, loading } = this.props;

    const empty = !!(options.size && options.length);

    return (
      <div className="input-dropdown-list" style={{ width }}>
        {loading && (empty ? this.empty : this.loading)}
        {options.map(this.renderOption)}
      </div>
    );
  }
}
