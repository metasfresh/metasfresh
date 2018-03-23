import React, { PureComponent } from 'react';
import { is, List } from 'immutable';
import onClickOutside from 'react-onclickoutside';
import TetherComponent from 'react-tether';
import PropTypes from 'prop-types';
import classnames from 'classnames';
import SelectionDropdown from '../SelectionDropdown';

const setSelectedValue = function(dropdownList, selected) {
  const changedValues = {};
  let idx = 0;

  let selectedOption = selected;

  if (selected) {
    if (!selected.caption) {
      selectedOption = { caption: selected, key: null };
    }

    idx = dropdownList.findIndex(
      item => item.caption === selectedOption.caption
    );
  }

  if (idx !== 0) {
    const item = dropdownList.get(idx);
    dropdownList = dropdownList.delete(idx);
    dropdownList = dropdownList.insert(0, item);
    changedValues.dropdownList = dropdownList;
    idx = 0;
  }

  changedValues.selected = dropdownList.get(idx);
  changedValues.dropdownList = dropdownList;

  return changedValues;
};

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
    } = this.props;

    let dropdownList = this.state.dropdownList;
    let changedValues = {};

    if (!is(prevProps.list, list)) {
      dropdownList = List(list);
      if (!mandatory && emptyText) {
        dropdownList = dropdownList.unshift({
          caption: emptyText,
          key: null,
        });
      }

      changedValues.dropdownList = dropdownList;

      if (dropdownList.size > 0) {
        let selectedOption = null;

        if (selected || defaultValue) {
          selectedOption = selected ? selected : defaultValue;
        }
        changedValues = {
          ...changedValues,
          ...setSelectedValue(dropdownList, selectedOption),
        };
      } else {
        changedValues.selected = null;
      }
    }

    if (!changedValues.selected && dropdownList.size > 0) {
      let newSelected = null;

      if (prevProps.selected !== selected) {
        newSelected = selected;
      }

      if (newSelected) {
        changedValues = {
          ...changedValues,
          ...setSelectedValue(dropdownList, newSelected),
        };
      }
    }

    if (Object.keys(changedValues).length) {
      this.setState(
        {
          ...changedValues,
        },
        () => {
          autoFocus && this.dropdown.focus();
        }
      );
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

  handleClickOutside() {
    const { isFocused, onCloseDropdown, onBlur, selected } = this.props;

    if (isFocused) {
      this.setState(
        {
          selected: selected || null,
        },
        () => {
          onCloseDropdown();
          onBlur();
        }
      );
    }
  }

  handleSelect = selected => {
    const { onSelect, onCloseDropdown } = this.props;
    const { dropdownList } = this.state;
    const changedValues = {
      ...setSelectedValue(dropdownList, selected),
    };

    this.setState(changedValues, () => {
      if (selected.key === null) {
        onSelect(null);
      } else {
        onSelect(selected);
      }
      onCloseDropdown();
    });
  };

  handleTemporarySelection = selected => {
    this.setState({
      selected,
    });
  };

  handleCancel = () => {
    this.props.disableAutofocus();
    this.handleBlur();
    this.props.onCloseDropdown();
  };

  handleKeyDown = event => {
    const { onSelect, list, readonly } = this.props;

    if (event.key === 'Tab') {
      if (list.size === 0 && !readonly) {
        onSelect(null);
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

  handleBlur = () => {
    const { onBlur } = this.props;

    this.dropdown.blur();
    onBlur();
  };

  render() {
    const {
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
          ref={ref => (this.dropdown = ref)}
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
          onBlur={this.props.onBlur}
          onClick={readonly ? null : this.handleClick}
          onKeyDown={this.handleKeyDown}
          onKeyUp={this.handleKeyUp}
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
        </div>
        {isFocused &&
          isToggled && (
            <SelectionDropdown
              loading={loading}
              options={this.state.dropdownList}
              empty="There is no choice available"
              selected={this.state.selected}
              width={this.dropdown.offsetWidth}
              onChange={this.handleTemporarySelection}
              onSelect={this.handleSelect}
              onCancel={this.handleCancel}
            />
          )}
      </TetherComponent>
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
