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
    onSelect: PropTypes.func.isRequired,
    onCancel: PropTypes.func,
  };

  /* Those are instance variables since no rendering needs to be done depending on
   * those properties. Additionally, setState can't be used with the callback in
   * an event listener since it needs to return synchronously */
  ignoreMouse = false;
  ignoreNextMouseEnter = false;
  ignoreOption = null;

  optionToRef = new Map();

  componentDidMount() {
    window.addEventListener('keydown', this.handleKeyDown);
    window.addEventListener('keyup', this.handleKeyUp);
  }

  componentWillUnmount() {
    window.removeEventListener('keydown', this.handleKeyDown);
    window.removeEventListener('keyup', this.handleKeyUp);
  }

  componentWillReceiveProps(propsNext) {
    if (propsNext.options !== this.props.options) {
      this.optionToRef.clear();
    }
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

  scrollIntoView = (element, up) => {
    const {
      top: topMax,
      bottom: bottomMax,
    } = this.wrapper.getBoundingClientRect();

    const { top, bottom } = element.getBoundingClientRect();

    if (top < topMax || bottom > bottomMax) {
      element.scrollIntoView(up);
    }
  };

  navigate = up => {
    this.ignoreMouse = true;
    this.ignoreNextMouseEnter = true;

    const { selected, options, onChange } = this.props;

    let index = options.indexOf(selected);

    if (up) {
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

    const selectedNew = this.get(options, index);

    if (selectedNew === selected) {
      return;
    }

    const ref = this.optionToRef.get(selectedNew);

    this.scrollIntoView(ref, up);

    onChange(selectedNew);
  };

  handleKeyDown = event => {
    const { navigate } = this;
    const { selected, onCancel, onSelect } = this.props;

    switch (event.key) {
      case 'ArrowUp':
        navigate(true);
        break;

      case 'ArrowDown':
        navigate(false);
        break;

      case 'Escape':
        onCancel();
        break;

      case 'Enter':
        onSelect(selected);
        break;

      default:
        return;
    }

    event.preventDefault();
  };

  handleKeyUp = () => {
    this.ignoreMouse = false;
  };

  handleMouseEnter = option => {
    if (this.ignoreMouse) {
      return;
    }

    if (this.ignoreNextMouseEnter) {
      this.ignoreNextMouseEnter = false;
      this.ignoreOption = option;

      return;
    }

    if (option === this.ignoreOption) {
      return;
    }

    this.ignoreOption = null;

    this.props.onChange(option);
  };

  handleMouseDown = option => {
    this.props.onSelect(option);
  };

  renderHeader = children => {
    return (
      <div className="input-dropdown-list-option input-dropdown-list-header">
        {children}
      </div>
    );
  };

  renderOption = option => {
    const { selected } = this.props;
    const { key, caption } = option;

    const classNames = ['input-dropdown-list-option'];

    if (option === selected) {
      classNames.push('input-dropdown-list-option-key-on');
    }

    return (
      <div
        ref={ref => this.optionToRef.set(option, ref)}
        key={`${key}${caption}`}
        className={classNames.join(' ')}
        onMouseEnter={() => this.handleMouseEnter(option)}
        onMouseDown={() => this.handleMouseDown(option)}
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

    const empty = this.size(options) === 0;

    return (
      <div
        ref={ref => (this.wrapper = ref)}
        className="input-dropdown-list"
        style={{ width }}
      >
        {loading && (empty ? this.empty : this.loading)}
        {options.map(this.renderOption)}
      </div>
    );
  }
}
