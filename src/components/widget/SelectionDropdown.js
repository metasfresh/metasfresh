import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import classnames from 'classnames';

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
    selected: PropTypes.object,
    empty: PropTypes.node,
    forceEmpty: PropTypes.bool,
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
    const size = this.size(options);
    let index = options.indexOf(selected);

    if (up) {
      index--;
    } else {
      index++;
    }

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

  navigateToAlphanumeric = char => {
    const { selected, options, onChange } = this.props;
    const items = options.filter(
      item =>
        item.caption &&
        item.caption.length &&
        item.caption[0].toUpperCase() === char.toUpperCase()
    );

    const selectedIndex = items.indexOf(selected);
    const itemsSize = items.get ? items.size : items.length;
    let selectedNew = null;

    if (selectedIndex > -1 && selectedIndex < itemsSize - 1) {
      selectedNew = this.get(items, selectedIndex + 1);
    } else {
      selectedNew = this.get(items, 0);
    }

    if (!selectedNew) {
      return;
    }

    const ref = this.optionToRef.get(selectedNew);

    this.scrollIntoView(ref, false);

    onChange(selectedNew);
  };

  handleKeyDown = event => {
    const { navigate } = this;
    const { selected, onCancel, onSelect } = this.props;

    if (event.keyCode > 47 && event.keyCode < 123) {
      this.navigateToAlphanumeric(event.key);
    } else {
      switch (event.key) {
        case 'ArrowUp':
          event.preventDefault();
          navigate(true);
          break;
        case 'ArrowDown':
          event.preventDefault();
          navigate(false);
          break;
        case 'Escape':
          event.preventDefault();
          onCancel();
          break;
        case 'Enter':
          event.preventDefault();
          onSelect(selected);
          break;
        default:
          return;
      }
    }
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
    this.props.onSelect(option, true);
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

    return (
      <div
        ref={ref => this.optionToRef.set(option, ref)}
        key={`${key}${caption}`}
        className={classnames(
          'input-dropdown-list-option ignore-react-onclickoutside',
          {
            'input-dropdown-list-option-key-on': option === selected,
          }
        )}
        onMouseEnter={() => this.handleMouseEnter(option)}
        onMouseDown={() => this.handleMouseDown(option)}
      >
        {caption}
      </div>
    );
  };

  renderEmpty = () => this.renderHeader(this.props.empty);

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
    const { options, width, loading, forceEmpty } = this.props;
    const empty = this.size(options) === 0;

    return (
      <div
        ref={ref => (this.wrapper = ref)}
        className="input-dropdown-list"
        style={{ width }}
      >
        {loading ? this.loading : (empty || forceEmpty) && this.renderEmpty()}
        {options.map(this.renderOption)}
      </div>
    );
  }
}
