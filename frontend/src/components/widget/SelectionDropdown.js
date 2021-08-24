import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { CSSTransition } from 'react-transition-group';
import classnames from 'classnames';

/**
 * @file Is the component that handles the main functionality of List (RawList) and Lookup (RawLookup)
 * widgets. It's used in Labels (e.g. Master Data Attribute Feature) too.
 * @module SelectionDropdown
 * @extends Component
 */
export default class SelectionDropdown extends Component {
  /* Those are instance variables since no rendering needs to be done depending on
   * those properties. Additionally, setState can't be used with the callback in
   * an event listener since it needs to return synchronously */
  ignoreMouse = false;
  ignoreNextMouseEnter = false;
  ignoreOption = null;

  optionToRef = new Map();

  constructor(props) {
    super(props);

    this.handleKeyDown = this.handleKeyDown.bind(this);
    this.handleKeyUp = this.handleKeyUp.bind(this);
    this.scrollIntoView = this.scrollIntoView.bind(this);
    this.handleMouseEnter = this.handleMouseEnter.bind(this);
    this.handleMouseDown = this.handleMouseDown.bind(this);
  }

  componentDidMount() {
    window.addEventListener('keydown', this.handleKeyDown);
    window.addEventListener('keyup', this.handleKeyUp);
  }

  componentWillUnmount() {
    window.removeEventListener('keydown', this.handleKeyDown);
    window.removeEventListener('keyup', this.handleKeyUp);
  }

  /**
   * @method UNSAFE_componentWillReceiveProps
   * @summary ToDo: Describe the method.
   */
  UNSAFE_componentWillReceiveProps(propsNext) {
    if (propsNext.options !== this.props.options) {
      this.optionToRef.clear();
    }
  }

  /**
   * @method size
   * @summary ToDo: Describe the method.
   * @param {*} options
   */
  size(options) {
    if (Array.isArray(options)) {
      return options.length;
    }

    return options.size;
  }

  /**
   * @method get
   * @summary ToDo: Describe the method.
   * @param {*} options
   * @param {*} index
   */
  get(options, index) {
    if (Array.isArray(options)) {
      return options[index];
    }

    return options.get(index);
  }

  /**
   * @method scrollIntoView
   * @summary ToDo: Describe the method.
   * @param {*} element
   * @param {*} up
   */
  scrollIntoView(element, up) {
    const { top: topMax, bottom: bottomMax } =
      this.wrapper.getBoundingClientRect();
    const { top, bottom } = element.getBoundingClientRect();

    if (top < topMax || bottom > bottomMax) {
      element.scrollIntoView(up);
    }
  }

  /**
   * @method navigate
   * @summary ToDo: Describe the method.
   * @param {*} up
   */
  navigate = (up) => {
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

  /**
   * @method navigateToAlphanumeric
   * @summary ToDo: Describe the method.
   * @param {*} char
   */
  navigateToAlphanumeric = (char) => {
    const { selected, options, onChange } = this.props;
    const items = options.filter(
      (item) =>
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

  /**
   * @method handleKeyDown
   * @summary ToDo: Describe the method.
   * @param {object} event
   */
  handleKeyDown(event) {
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
  }

  /**
   * @method handleKeyUp
   * @summary ToDo: Describe the method.
   */
  handleKeyUp() {
    this.ignoreMouse = false;
  }

  /**
   * @method handleMouseEnter
   * @summary ToDo: Describe the method.
   * @param {*} option
   */
  handleMouseEnter(option) {
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
  }

  /**
   * @method handleMouseDown
   * @summary ToDo: Describe the method.
   * @param {*} option
   */
  handleMouseDown(option) {
    this.props.onSelect(option, true);
  }

  /**
   * @method renderHeader
   * @summary ToDo: Describe the method.
   * @param {*} children
   */
  renderHeader = (children) => {
    return (
      <div className="input-dropdown-list-option input-dropdown-list-header">
        {children}
      </div>
    );
  };

  /**
   * @method renderOption
   * @summary ToDo: Describe the method.
   * @param {*} option
   */
  renderOption = (option, idx) => {
    const { selected } = this.props;
    const { key, caption, description } = option;

    return (
      <div
        ref={(ref) => this.optionToRef.set(option, ref)}
        key={`${key}-${idx}-${caption}`}
        data-test-id={`${key}${caption}`}
        className={classnames(
          'input-dropdown-list-option ignore-react-onclickoutside',
          {
            'input-dropdown-list-option-key-on': option === selected,
          }
        )}
        title={description ? description : null}
        onMouseEnter={() => this.handleMouseEnter(option)}
        onMouseDown={() => this.handleMouseDown(option)}
      >
        {caption}
      </div>
    );
  };

  /**
   * @method renderEmpty
   * @summary ToDo: Describe the method.
   */
  renderEmpty = () => this.renderHeader(this.props.empty);

  loading = this.renderHeader(
    <CSSTransition className="rotate" timeout={{ enter: 1000, exit: 1000 }}>
      <div>
        <div className="rotate icon-rotate">
          <i className="meta-icon-settings" />
        </div>
      </div>
    </CSSTransition>
  );

  setRef = (ref) => (this.wrapper = ref);

  render() {
    const { options, width, height, loading, forceEmpty } = this.props;
    const empty = this.size(options) === 0;
    const style = {
      width,
      height: height ? height : '200px',
    };

    if (height) {
      style.maxHeight = height;
    }

    return (
      <div ref={this.setRef} className="input-dropdown-list" style={style}>
        {loading ? this.loading : (empty || forceEmpty) && this.renderEmpty()}
        {options.map(this.renderOption)}
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {array|shape} options
 * @prop {object} selected,
 * @prop {node} empty,
 * @prop {bool} forceEmpty,
 * @prop {number} width
 * @prop {number} height
 * @prop {bool} loading
 * @prop {func} onChange
 * @prop {func} onSelect
 * @prop {bool} allowShortcut
 * @prop {func} onCancel
 */
SelectionDropdown.propTypes = {
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
  height: PropTypes.number,
  loading: PropTypes.bool,
  onChange: PropTypes.func.isRequired,
  onSelect: PropTypes.func.isRequired,
  onCancel: PropTypes.func,
};
