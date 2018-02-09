import React, { Component, PureComponent } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';
import ReactCSSTransitionGroup from 'react-addons-css-transition-group';
import TetherComponent from 'react-tether';
import PropTypes from 'prop-types';
import classnames from 'classnames';

export class ListDropdown extends Component {
  static propTypes = {
    isListEmpty: PropTypes.bool,
    offsetWidth: PropTypes.number,
    loading: PropTypes.bool,
    children: PropTypes.any,
  };

  render() {
    const { isListEmpty, offsetWidth, loading, children } = this.props;

    return (
      <div
        className="input-dropdown-list"
        style={{ width: `${offsetWidth}px` }}
      >
        {isListEmpty &&
          loading === false && (
            <div className="input-dropdown-list-header">
              There is no choice available
            </div>
          )}
        {loading &&
          isListEmpty && (
            <div className="input-dropdown-list-header">
              <ReactCSSTransitionGroup
                transitionName="rotate"
                transitionEnterTimeout={1000}
                transitionLeaveTimeout={1000}
              >
                <div className="rotate icon-rotate">
                  <i className="meta-icon-settings" />
                </div>
              </ReactCSSTransitionGroup>
            </div>
          )}
        {children}
      </div>
    );
  }
}

class RawList extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      selected: props.selected || 0,
      dropdownList: props.list || [],
      isOpen: false,
      isFocused: props.autoFocus,
    };
  }

  componentWillMount() {
    window.addEventListener('keydown', this.handleTab);
  }

  componentWillUnmount() {
    window.removeEventListener('keydown', this.handleTab);
  }

  componentDidMount() {
    const { autoFocus, onRequestListData } = this.props;

    if (this.dropdown && autoFocus && onRequestListData) {
      onRequestListData();
    }

    if (this.state.isFocused) {
      this.openDropdownList();
    }
  }

  componentDidUpdate(prevProps, prevState) {
    const {
      list,
      mandatory,
      defaultValue,
      autoFocus,
      property,
      initialFocus,
      selected,
      doNotOpenOnFocus,
      lastProperty,
      loading,
      disableAutofocus,
    } = this.props;

    if (
      list.length === 0 &&
      prevProps.loading !== loading &&
      loading === false &&
      lastProperty
    ) {
      disableAutofocus();
    }

    if (this.dropdown && autoFocus) {
      if (prevState.selected !== this.state.selected) {
        if (list.length === 1) {
          this.handleSelect(list[0]);
        }

        if (!doNotOpenOnFocus && list.length > 1) {
          this.openDropdownList();
        }
      }
    }

    if (this.dropdown) {
      if (autoFocus) {
        if (list && list.length > 0) {
          this.focus();
        }
      } else {
        if (property && prevProps.defaultValue !== defaultValue) {
          this.focus();
        } else {
          if (initialFocus && !defaultValue) {
            this.focus();
          }
        }
      }
    }

    if (prevProps.list !== list) {
      let dropdown = [];

      if (!mandatory) {
        dropdown.push(0);
      }

      if (list.length > 0) {
        let openDropdownState = {};

        if (list.length > 1) {
          openDropdownState.isOpen = true;
        }

        let dropdownList = dropdown.concat(list);

        this.setState({
          ...openDropdownState,
          dropdownList: dropdownList,
          selected: defaultValue ? defaultValue : list[0],
        });
      }
    }

    if (prevProps.selected !== selected) {
      this.setState({
        selected: selected,
      });
    }

    this.checkIfDropDownListOutOfFilter();
  }

  checkIfDropDownListOutOfFilter = () => {
    if (!this.tetheredList ||
      (this.tetheredList && !this.tetheredList.getBoundingClientRect)) {
      return;
    }

    const { top } = this.tetheredList.getBoundingClientRect();
    const { filter } = this.props;
    const { isOpen } = this.state;
    if (
      isOpen &&
      filter.visible &&
      (top + 20 > filter.boundingRect.bottom ||
        top - 20 < filter.boundingRect.top)
    ) {
      this.setState({ isOpen: false });
    }
  };

  focus = () => {
    if (this.state.isOpen) {
      if (this.dropdown && this.dropdown.focus) {
        this.dropdown.focus();
      }

      this.setState({
        isFocused: true,
      });
    }
  };

  blur = () => {
    if (this.state.isFocused) {
      if (this.dropdown && this.dropdown.blur) {
        this.dropdown.blur();
      }

      this.setState({
        isFocused: false,
      });
    }
  };

  openDropdownList = focus => {
    this.setState(
      {
        isOpen: true,
      },
      () => {
        focus && this.focus();
      }
    );
  };

  closeDropdownList = () => {
    const { isOpen } = this.state;

    if (isOpen) {
      this.setState(
        {
          isOpen: false,
        },
        this.blur
      );
    }
  };

  handleBlur = () => {
    const { onHandleBlur } = this.props;

    this.blur();
    this.closeDropdownList();

    onHandleBlur && onHandleBlur();
  };

  handleFocus = () => {
    const { onFocus, doNotOpenOnFocus, autoFocus } = this.props;

    if (!doNotOpenOnFocus && !autoFocus) {
      this.openDropdownList(true);
    }

    onFocus && onFocus();
  };

  handleClickOutside() {
    const { isOpen } = this.state;

    if (isOpen) {
      this.closeDropdownList();
      this.handleBlur();
    }
  }

  /*
     * Alternative method to open dropdown, in case of disabled opening
     * on focus.
     */
  handleClick = () => {
    this.openDropdownList();
  };

  handleSelect = option => {
    const { onSelect } = this.props;

    if (option.key === null) {
      onSelect(null);
    } else {
      onSelect(option);
    }

    this.setState(
      {
        selected: option || 0,
      },
      () => {
        this.handleBlur();
        this.closeDropdownList();
      }
    );
  };

  handleSwitch = option => {
    this.setState({
      selected: option || 0,
    });
  };

  handleKeyDown = e => {
    const { onSelect, list, readonly } = this.props;
    const { selected, isOpen } = this.state;

    if (e.keyCode > 47 && e.keyCode < 123) {
      this.navigateToAlphanumeric(e.key);
    } else {
      switch (e.key) {
        case 'ArrowDown':
          e.preventDefault();
          this.navigate(true);
          break;
        case 'ArrowUp':
          e.preventDefault();
          this.navigate(false);
          break;
        case 'Enter':
          e.preventDefault();

          if (isOpen) {
            e.stopPropagation();
          }

          if (selected) {
            this.handleSelect(selected);
          } else {
            onSelect(null);
          }

          break;
        case 'Escape':
          e.preventDefault();
          this.handleBlur();
          this.closeDropdownList();
          break;
        case 'Tab':
          list.length === 0 && !readonly && onSelect(null);
          break;
      }
    }
  };

  handleTab = ({ key }) => {
    const { isOpen } = this.state;

    if (key === 'Tab' && isOpen) {
      this.closeDropdownList();
    }
  };

  getSelectedIndex() {
    const { list, mandatory } = this.props;
    const { selected } = this.state;

    if (selected === 0) {
      return 0;
    }

    let baseIndex = list.indexOf(selected);
    if (selected && baseIndex < 0) {
      baseIndex = list.findIndex(item => item.key === selected.key);
    }

    if (!mandatory) {
      return baseIndex + 1;
    }

    return baseIndex;
  }

  navigateToAlphanumeric = char => {
    const { list } = this.props;
    const { isOpen, selected } = this.state;

    if (!isOpen) {
      this.openDropdownList(true);
    }

    const items = list.filter(
      item => item.caption.toUpperCase() === char.toUpperCase()
    );

    const selectedIndex = items.indexOf(selected);
    const item = selectedIndex > -1 ? items[selectedIndex + 1] : items[0];

    if (!item) {
      return;
    }

    this.setState({
      selected: item,
    });
  };

  navigate = up => {
    const { selected, dropdownList, isOpen } = this.state;

    if (!isOpen) {
      this.openDropdownList(true);
    }

    let selectedIndex = null;

    dropdownList.map((item, index) => {
      if (JSON.stringify(item) === JSON.stringify(selected)) {
        selectedIndex = index;
      }
    });

    const next = up ? selectedIndex + 1 : selectedIndex - 1;

    this.setState({
      selected:
        next >= 0 && next <= dropdownList.length - 1
          ? dropdownList[next]
          : selected,
    });
  };

  getRow = (option, index) => {
    const { defaultValue } = this.props;
    const { selected } = this.state;
    const value = defaultValue ? defaultValue.caption : null;
    const classes = ['input-dropdown-list-option ignore-react-onclickoutside'];

    if (selected != null && selected !== 0) {
      if (
        selected.key === option.key ||
        (!selected && (value === option.caption || (!value && index === 0)))
      ) {
        classes.push('input-dropdown-list-option-key-on');
      }
    }

    return (
      <div
        key={option.key}
        className={classes.join(' ')}
        onMouseEnter={() => this.handleSwitch(option)}
        onClick={() => this.handleSelect(option)}
      >
        <p className="input-dropdown-item-title">{option.caption}</p>
      </div>
    );
  };

  renderOptions = () => {
    const { list, mandatory, emptyText } = this.props;

    let emptyRow;

    if (!mandatory && emptyText) {
      emptyRow = this.getRow({ key: null, caption: emptyText });
    }

    return (
      <div>
        {/* if field is not mandatory add extra empty row */}
        {emptyRow}
        {list.map(this.getRow)}
      </div>
    );
  };

  render() {
    const {
      list,
      rank,
      readonly,
      defaultValue,
      selected,
      align,
      updated,
      loading,
      rowId,
      isModal,
      tabIndex,
      disabled,
      mandatory,
      validStatus,
      lookupList
    } = this.props;

    let placeholder = '';
    const isListEmpty = list.length === 0;
    const { isOpen, isFocused } = this.state;

    if (typeof defaultValue === 'string') {
      placeholder = defaultValue;
    } else {
      placeholder = defaultValue && defaultValue.caption;
    }

    let value;

    if (lookupList) {
      value = placeholder;
    } else if (selected) {
      value = selected.caption;
    }

    if (!value) {
      value = '';
    }

    return (
      <div
        ref={c => (this.dropdown = c)}
        className={classnames('input-dropdown-container', {
          'input-disabled': readonly,
          'input-dropdown-container-static': rowId,
          'input-table': rowId && !isModal,
        })}
        tabIndex={tabIndex ? tabIndex : 0}
        onFocus={readonly ? null : this.handleFocus}
        onClick={readonly ? null : this.handleClick}
        onKeyDown={this.handleKeyDown}
      >
        <TetherComponent
          attachment="top left"
          targetAttachment="bottom left"
          constraints={[
            {
              to: 'scrollParent',
            },
            {
              to: 'window',
              pin: ['bottom'],
            },
          ]}
        >
          <div
            className={classnames('input-dropdown input-block input-readonly', {
              'input-secondary': rank,
              pulse: updated,
              'input-mandatory': mandatory && !selected,
              'input-error':
                validStatus &&
                (!validStatus.valid && !validStatus.initialValue) &&
                !isOpen,
            })}
            ref={c => (this.inputContainer = c)}
          >
            <div
              className={classnames('input-editable input-dropdown-focused', {
                [`text-xs-${align}`]: align,
              })}
            >
              <input
                type="text"
                className={
                  'input-field js-input-field ' +
                  'font-weight-semibold ' +
                  (disabled ? 'input-disabled ' : '')
                }
                readOnly
                tabIndex={-1}
                placeholder={placeholder}
                value={value}
                disabled={readonly || disabled}
              />
            </div>

            <div className="input-icon">
              <i className="meta-icon-down-1 input-icon-sm" />
            </div>
          </div>
          {isFocused &&
            isOpen && (
              <ListDropdown
                ref={c => (this.tetheredList = c)}
                isListEmpty={isListEmpty}
                offsetWidth={this.dropdown.offsetWidth}
                loading={loading}
              >
                {this.renderOptions()}
              </ListDropdown>
            )}
        </TetherComponent>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  filter: state.windowHandler.filter,
});

RawList.propTypes = {
  filter: PropTypes.object.isRequired,
  dispatch: PropTypes.func.isRequired,
  autoFocus: PropTypes.bool,
  readonly: PropTypes.bool,
  list: PropTypes.array,
};

export default connect(mapStateToProps, false, false, { withRef: true })(
  onClickOutside(RawList)
);
