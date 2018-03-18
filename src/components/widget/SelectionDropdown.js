import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';

const UP = Symbol('up');
const DOWN = Symbol('down');

export default class SelectionDropdown extends Component {
  static propTypes = {
    options: PropTypes.oneOfType([
      PropTypes.array,
      PropTypes.shape({
        map: PropTypes.func.isRequired,
        includes: PropTypes.func.isRequired,
        indexOf: PropTypes.func.isRequired,
        get: PropTypes.func.isRequired,
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
    onChange: PropTypes.func.isRequired,
  };

  componentDidMount() {
    window.addEventListener('keydown', this.handleKeyDown);
  }

  componentWillUnmount() {
    window.removeEventListener('keydown', this.handleKeyDown);
  }

  size(options) {
    if (Array.isArray(options)) {
      return options.length;
    }

    return options.size;
  }

  get(options, index) {
    if (Array.isArray(options)) {
      return options[index];
    }

    return options.get(index);
  }

  navigate = direction => {
    const { selected, options, onChange } = this.props;

    let index = options.indexOf(selected);

    if (direction === UP) {
      index--;
    } else {
      index++;
    }

    const size = this.size(options);

    if (index < 0) {
      index = 0;
    } else if (index >= size) {
      index = size - 1;
    }

    onChange(this.get(options, index));
  };

  handleKeyDown = event => {
    const { navigate } = this;

    switch (event.key) {
      case 'ArrowUp':
        navigate(UP);
        break;

      case 'ArrowDown':
        navigate(DOWN);
        break;

      default:
        return;
    }

    event.preventDefault();
  };

  renderHeader = children => {
    return (
      <div className="input-dropdown-list-option input-dropdown-list-header">
        {children}
      </div>
    );
  };

  renderOption = (option, index) => {
    const { selected } = this.props;
    const { key, caption } = option;

    const classNames = ['input-dropdown-list-option'];

    if (option === selected) {
      classNames.push('input-dropdown-list-option-key-on');
    }

    return (
      <div
        ref={ref => (this.options[index] = ref)}
        key={`${key}${caption}`}
        className={classNames.join(' ')}
      >
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

    this.options = new Array(this.size(options));

    const empty = !!(options.size && options.length);

    return (
      <div className="input-dropdown-list" style={{ width }}>
        {loading && (empty ? this.empty : this.loading)}
        {options.map(this.renderOption)}
      </div>
    );
  }
}
