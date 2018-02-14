import React, { Component, PureComponent } from 'react';
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
      selected: props.selected || null,
      dropdownList: props.list || [],
    };
  }

  componentWillMount() {
    window.addEventListener('keydown', this.handleTab);
  }

  componentWillUnmount() {
    window.removeEventListener('keydown', this.handleTab);
  }

  componentDidUpdate(prevProps, prevState) {
    const { list, mandatory, defaultValue, selected, autoFocus,
      onOpenDropdown, doNotOpenOnFocus } = this.props;

    //select
    // TODO: Use Immutable structure here
    if (prevProps.list.length !== list.length) {
      let dropdownList = [...list];

      if (!mandatory && defaultValue) {
        dropdownList.unshift({
          caption: defaultValue.caption,
          key: null,
        });
      }

      if (dropdownList.length > 0) {
        let selectedValue = false;

        if (!selected && dropdownList.length) {
          selectedValue = {
            selected: dropdownList[0],
          };
        }

        this.setState({
          ...selectedValue,
          dropdownList,
        });
      }
    }

    if (prevProps.selected !== selected) {
      this.handleSwitch(selected);
    }

    //open if selection changed via props
    if (autoFocus) {
      if (prevState.selected !== this.state.selected) {
        if (!doNotOpenOnFocus && this.state.dropdownList.length > 1) {
          onOpenDropdown();
        }
      }
    }

    this.checkIfDropDownListOutOfFilter();
  }

  checkIfDropDownListOutOfFilter = () => {
    if (
      !this.tetheredList ||
      (this.tetheredList && !this.tetheredList.getBoundingClientRect)
    ) {
      return;
    }

    const { top } = this.tetheredList.getBoundingClientRect();
    const { filter, isToggled, onCloseDropdown } = this.props;
    if (
      isToggled &&
      filter.visible &&
      (top + 20 > filter.boundingRect.bottom ||
        top - 20 < filter.boundingRect.top)
    ) {
      onCloseDropdown();
    }
  };

  handleClickOutside() {
    const { isToggled, onCloseDropdown } = this.props;

    if (isToggled) {
      onCloseDropdown();
    }
  }

  /*
   * Alternative method to open dropdown, in case of disabled opening
   * on focus.
   */
  handleClick = () => {
    const { onOpenDropdown, onFocus } = this.props;

    onFocus();
    onOpenDropdown();
  };

  handleSelect = selected => {
    const { onSelect, onCloseDropdown } = this.props;

    this.setState(
      {
        selected,
      },
      () => {
        if (selected.key === null) {
          onSelect(null);
        } else {
          onSelect(selected);
        }
        onCloseDropdown();
      }
    );
  };

  handleSwitch = selected => {
    this.setState({
      selected,
    });
  };

  handleKeyDown = e => {
    const { onSelect, list, readonly, isToggled,
      onOpenDropdown, onCloseDropdown } = this.props;
    const { selected } = this.state;

    if (e.keyCode > 47 && e.keyCode < 123) {
      this.navigateToAlphanumeric(e.key);
    } else {
      switch (e.key) {
        case 'ArrowUp':
          e.preventDefault();
          this.navigate(false);
          break;
        case 'ArrowDown':
          e.preventDefault();

          if (!isToggled) {
            onOpenDropdown();
          } else {
            this.navigate(true);
          }
          break;
        case 'Enter':
          e.preventDefault();

          if (isToggled) {
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
          onCloseDropdown();
          break;
        case 'Tab':
          list.length === 0 && !readonly && onSelect(null);
          break;
      }
    }
  };

  handleTab = ({ key }) => {
    const { isToggled, onCloseDropdown } = this.props;

    if (key === 'Tab' && isToggled) {
      onCloseDropdown();
    }
  };

  navigateToAlphanumeric = char => {
    const { isToggled, onOpenDropdown, onFocus } = this.props;
    const { selected, dropdownList } = this.state;

    if (!isToggled) {
      onFocus();
      onOpenDropdown();
    }

    const items = dropdownList.filter(
      item => item.caption.toUpperCase() === char.toUpperCase()
    );

    const selectedIndex = items.indexOf(selected);
    const item = selectedIndex > -1 ? items[selectedIndex + 1] : items[0];

    if (!item) {
      return;
    }

    this.handleSwitch(item);
  };

  navigate = up => {
    const { isToggled, onOpenDropdown, onFocus } = this.props;
    const { selected, dropdownList } = this.state;

    if (!isToggled) {
      onFocus();
      onOpenDropdown();
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

  getRow = option => {
    const { selected } = this.state;
    let selectedRow = false;

    if (
      selected != null &&
      (selected.key === option.key ||
        (selected.key === null && selected.caption === option.caption))
    ) {
      selectedRow = true;
    }

    return (
      <div
        key={option.key}
        className={classnames(
          'input-dropdown-list-option ignore-react-onclickoutside',
          {
            'input-dropdown-list-option-key-on': selectedRow,
          }
        )}
        onMouseEnter={() => this.handleSwitch(option)}
        onClick={() => this.handleSelect(option)}
      >
        <p className="input-dropdown-item-title">{option.caption}</p>
      </div>
    );
  };

  renderOptions = () => {
    const { list, mandatory, emptyText } = this.props;
    let emptyRow = null;

    /* if field is not mandatory add extra empty row */
    if (!mandatory && emptyText) {
      emptyRow = this.getRow({ key: null, caption: emptyText });
    }

    return (
      <div>
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
      lookupList,
      isToggled,
      isFocused,
      onFocus,
    } = this.props;

    let value = '';
    let placeholder = '';
    const isListEmpty = list.length === 0;

    if (typeof defaultValue === 'string') {
      placeholder = defaultValue;
    } else {
      placeholder = defaultValue && defaultValue.caption;
    }

    if (lookupList) {
      value = placeholder;
    } else if (selected) {
      value = selected.caption;
    }

    return (
      <div
        ref={c => (this.dropdown = c)}
        className={classnames('input-dropdown-container', {
          'input-disabled': readonly,
          'input-dropdown-container-static': rowId,
          'input-table': rowId && !isModal,
          'lookup-dropdown': lookupList,
          'select-dropdown': !lookupList,
          focused: isFocused,
          opened: isToggled,
        })}
        tabIndex={tabIndex ? tabIndex : 0}
        onFocus={readonly ? null : onFocus}
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
                !isToggled,
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
                className={classnames(
                  'input-field js-input-field',
                  'font-weight-semibold',
                  {
                    'input-disabled': disabled,
                  }
                )}
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
            isToggled && (
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

RawList.propTypes = {
  filter: PropTypes.object.isRequired,
  readonly: PropTypes.bool,
  list: PropTypes.array,
  rank: PropTypes.any,
  defaultValue: PropTypes.any,
  selected: PropTypes.any,
  align: PropTypes.any,
  emptyText: PropTypes.string,
  lastProperty: PropTypes.any,
  updated: PropTypes.bool,
  loading: PropTypes.bool,
  rowId: PropTypes.any,
  isModal: PropTypes.bool,
  tabIndex: PropTypes.number,
  disabled: PropTypes.bool,
  mandatory: PropTypes.bool,
  validStatus: PropTypes.any,
  lookupList: PropTypes.any,
  initialFocus: PropTypes.any,
  doNotOpenOnFocus: PropTypes.bool,
  isFocused: PropTypes.bool.isRequired,
  isToggled: PropTypes.bool.isRequired,
  autoFocus: PropTypes.bool,
  disableAutofocus: PropTypes.func,
  onFocus: PropTypes.func.isRequired,
  onBlur: PropTypes.func.isRequired,
  onSelect: PropTypes.func.isRequired,
  onOpenDropdown: PropTypes.func.isRequired,
  onCloseDropdown: PropTypes.func.isRequired,
};

export default onClickOutside(RawList);
