import React, { PureComponent } from 'react';
import { List } from 'immutable';
import onClickOutside from 'react-onclickoutside';
import TetherComponent from 'react-tether';
import PropTypes from 'prop-types';
import classnames from 'classnames';

import RawListDropdown from './RawListDropdown';

class RawList extends PureComponent {
  constructor(props) {
    super(props);

    this.state = {
      selected: props.selected || null,
      dropdownList: props.list,
    };
  }

  componentWillMount() {
    window.addEventListener('keydown', this.handleTab);
  }

  componentWillUnmount() {
    window.removeEventListener('keydown', this.handleTab);
  }

  componentDidUpdate(prevProps) {
    const {
      list,
      mandatory,
      defaultValue,
      selected,
      autoFocus,
      emptyText,
      lookupList,
    } = this.props;

    if (prevProps.list !== list) {
      let dropdownList = List(list);

      if (!mandatory && emptyText) {
        dropdownList = dropdownList.unshift({
          caption: emptyText,
          key: null,
        });
      }

      if (dropdownList.size > 0) {
        let idx = 0;

        if (selected || defaultValue) {
          const selectedCaption = selected ? selected.caption : defaultValue;

          idx = dropdownList.findIndex(
            item => item.caption === selectedCaption
          );
        }

        if (idx !== 0) {
          const item = dropdownList.get(idx);
          dropdownList = dropdownList.delete(idx);
          dropdownList = dropdownList.insert(0, item);
          idx = 0;
        }

        const selectedValue = {
          selected: dropdownList.get(idx),
        };

        this.setState(
          {
            dropdownList,
            selected: selectedValue.selected,
          },
          () => {
            autoFocus && this.dropdown.focus();
          }
        );
      } else {
        // list changed so we reset selected value no matter what
        this.handleSwitch(null);
      }
    }

    // for lookups
    if (lookupList && prevProps.selected !== selected) {
      this.handleSwitch(selected);
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
    const { isToggled, onCloseDropdown, onBlur } = this.props;

    if (isToggled) {
      onCloseDropdown();
      onBlur();
    }
  }

  /*
   * Alternative method to open dropdown, in case of disabled opening
   * on focus.
   */
  handleClick = () => {
    const { onOpenDropdown } = this.props;

    this.dropdown.focus();
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
    const {
      onSelect,
      list,
      readonly,
      isToggled,
      onOpenDropdown,
      onCloseDropdown,
    } = this.props;
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
          list.size === 0 && !readonly && onSelect(null);
          break;
      }
    }
  };

  handleTab = e => {
    const { isToggled, isFocused, onCloseDropdown } = this.props;

    if (e.key === 'Tab' && isFocused) {
      if (isToggled) {
        e.preventDefault();
        onCloseDropdown();
      } else {
        this.handleBlur();
      }
    }
  };

  handleBlur() {
    const { onBlur } = this.props;

    this.dropdown.blur();
    onBlur();
  }

  navigateToAlphanumeric = char => {
    const { isToggled, onOpenDropdown } = this.props;
    const { selected, dropdownList } = this.state;

    if (!isToggled) {
      this.dropdown.focus();
      onOpenDropdown();
    }

    const items = dropdownList.filter(
      item => item.caption.toUpperCase() === char.toUpperCase()
    );

    const selectedIndex = items.indexOf(selected);
    const item =
      selectedIndex > -1 ? items.get(selectedIndex + 1) : items.get(0);

    if (!item) {
      return;
    }

    this.handleSwitch(item);
  };

  navigate = up => {
    const { isToggled, onOpenDropdown } = this.props;
    const { selected, dropdownList } = this.state;

    if (!isToggled) {
      this.dropdown.focus();
      onOpenDropdown();
    }

    const selectedIndex = dropdownList.findIndex(
      item => item.caption === selected.caption
    );

    const next = up ? selectedIndex + 1 : selectedIndex - 1;

    this.setState({
      selected:
        next >= 0 && next <= dropdownList.size - 1
          ? dropdownList.get(next)
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
    const { dropdownList } = this.state;

    return <div>{dropdownList.map(this.getRow)}</div>;
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
    const isListEmpty = list.size === 0;

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
          'input-mandatory': !lookupList && mandatory && !selected,
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
              <RawListDropdown
                ref={c => (this.tetheredList = c)}
                isListEmpty={isListEmpty}
                offsetWidth={this.dropdown.offsetWidth}
                loading={loading}
              >
                {this.renderOptions()}
              </RawListDropdown>
            )}
        </TetherComponent>
      </div>
    );
  }
}

RawList.propTypes = {
  filter: PropTypes.object,
  readonly: PropTypes.bool,
  // Immutable List
  list: PropTypes.object,
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
