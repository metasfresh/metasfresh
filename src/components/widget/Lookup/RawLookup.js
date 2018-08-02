import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import TetherComponent from 'react-tether';
import ReactDOM from 'react-dom';
import classnames from 'classnames';

import {
  autocompleteRequest,
  autocompleteModalRequest,
} from '../../../actions/GenericActions';
import { getViewAttributeTypeahead } from '../../../actions/ViewAttributesActions';
import { openModal } from '../../../actions/WindowActions';
import SelectionDropdown from '../SelectionDropdown';

class RawLookup extends Component {
  constructor(props) {
    super(props);

    this.state = {
      query: '',
      list: [],
      isInputEmpty: true,
      selected: null,
      direction: null,
      loading: false,
      oldValue: '',
      shouldBeFocused: true,
      isFocused: false,
      parentElement: undefined,
    };
  }

  componentDidMount() {
    const { selected, defaultValue, initialFocus, parentElement } = this.props;

    if (parentElement) {
      // eslint-disable-next-line react/no-find-dom-node
      let parentEl = ReactDOM.findDOMNode(parentElement);

      this.setState({
        parentElement: parentEl,
      });
    }

    this.handleValueChanged();

    if (selected) {
      this.inputSearch.value = selected.caption;
    } else {
      this.handleBlur(this.clearState);
    }

    if (defaultValue) {
      this.inputSearch.value = defaultValue.caption;
    }

    if (initialFocus && !this.inputSearch.value) {
      this.inputSearch.focus();
    }
  }

  componentDidUpdate(prevProps) {
    this.handleValueChanged();

    const {
      autoFocus,
      defaultValue,
      fireClickOutside,
      handleInputEmptyStatus,
      filterWidget,
      lookupEmpty,
      localClearing,
      fireDropdownList,
    } = this.props;

    const { shouldBeFocused } = this.state;

    if (localClearing && !defaultValue) {
      this.inputSearch.value = '';
    }

    if (autoFocus && !this.inputSearch.value && shouldBeFocused) {
      this.inputSearch.focus();

      this.setState({
        shouldBeFocused: false,
      });
    }

    defaultValue &&
      (prevProps.defaultValue == null ||
        (prevProps.defaultValue &&
          prevProps.defaultValue.caption !== defaultValue.caption)) &&
      handleInputEmptyStatus(false);

    if (fireClickOutside && prevProps.fireClickOutside !== fireClickOutside) {
      if (defaultValue !== null && typeof defaultValue !== 'undefined') {
        if (defaultValue.caption !== this.inputSearch.value) {
          this.inputSearch.value = defaultValue.caption || '';
        }
      }
    }

    if (filterWidget && lookupEmpty && defaultValue === null) {
      this.inputSearch.value = defaultValue;
    }

    if (fireDropdownList && prevProps.fireDropdownList !== fireDropdownList) {
      this.handleChange('', true);
    }

    this.checkIfComponentOutOfFilter();
  }

  checkIfComponentOutOfFilter = () => {
    if (!this.lookupList) return;
    // eslint-disable-next-line react/no-find-dom-node
    let element = ReactDOM.findDOMNode(this.lookupList);
    const { top } = element.getBoundingClientRect();
    const { filter, isOpen } = this.props;

    if (
      isOpen &&
      filter.visible &&
      (top + 20 > filter.boundingRect.bottom ||
        top - 20 < filter.boundingRect.top)
    ) {
      this.props.onDropdownListToggle(false);
    }
  };

  clearState = () => {
    this.setState({
      list: [],
      isInputEmpty: true,
      selected: null,
      loading: false,
    });
  };

  handleSelect = select => {
    const {
      onChange,
      handleInputEmptyStatus,
      mainProperty,
      setNextProperty,
      filterWidget,
      subentity,
    } = this.props;

    let mainProp = mainProperty[0];

    this.setState({
      selected: null,
    });

    if (select.key === 'NEW') {
      this.handleAddNew();

      return;
    }

    if (filterWidget) {
      const promise = onChange(mainProp.parameterName, select);

      if (promise) {
        promise.then(() => {
          setNextProperty(mainProp.parameterName);
        });
      } else {
        setNextProperty(mainProp.parameterName);
      }
    } else {
      if (subentity === 'quickInput') {
        onChange(mainProperty[0].field, select, () =>
          setNextProperty(mainProp.field)
        );
      } else {
        const promise = onChange(mainProp.field, select);

        if (promise) {
          promise.then(() => {
            setNextProperty(mainProp.field);
          });
        } else {
          setNextProperty(mainProp.field);
        }
      }
    }

    if (select) {
      this.inputSearch.value = select.caption;
    }

    handleInputEmptyStatus(false);

    setTimeout(() => {
      this.inputSearch.focus();
    }, 0);

    this.handleBlur();
  };

  handleAddNew = () => {
    const {
      dispatch,
      newRecordWindowId,
      newRecordCaption,
      filterWidget,
      parameterName,
      mainProperty,
    } = this.props;

    this.handleBlur();

    dispatch(
      openModal(
        newRecordCaption,
        newRecordWindowId,
        'window',
        null,
        null,
        null,
        null,
        null,
        'NEW',
        filterWidget ? parameterName : mainProperty[0].field
      )
    );
  };

  handleBlur = () => {
    this.setState(
      {
        isFocused: false,
      },
      () => {
        this.props.onDropdownListToggle(false);
      }
    );
  };

  handleFocus = () => {
    const { onFocus } = this.props;

    this.setState(
      {
        isFocused: true,
      },
      () => {
        onFocus && onFocus();
      }
    );
  };

  handleChange = (handleChangeOnFocus, allowEmpty) => {
    const {
      recent,
      windowType,
      dataId,
      filterWidget,
      parameterName,
      tabId,
      rowId,
      entity,
      subentity,
      subentityId,
      viewId,
      mainProperty,
      handleInputEmptyStatus,
      enableAutofocus,
      isModal,
      newRecordCaption,
    } = this.props;

    enableAutofocus();

    if (this.props.localClearing) {
      this.props.resetLocalClearing();
    }

    if (this.inputSearch.value || allowEmpty) {
      !allowEmpty && handleInputEmptyStatus(false);

      this.setState({
        isInputEmpty: false,
        loading: true,
        query: this.inputSearch.value,
      });

      this.props.onDropdownListToggle(true);

      let typeaheadRequest;
      if (entity === 'documentView' && !filterWidget) {
        typeaheadRequest = getViewAttributeTypeahead(
          windowType,
          viewId,
          dataId,
          mainProperty[0].field,
          this.inputSearch.value
        );
      } else if (viewId && !filterWidget) {
        typeaheadRequest = autocompleteModalRequest({
          docId: filterWidget ? viewId : dataId,
          docType: windowType,
          entity: 'documentView',
          propertyName: filterWidget ? parameterName : mainProperty[0].field,
          query: this.inputSearch.value,
          viewId,
          rowId,
          tabId,
        });
      } else {
        typeaheadRequest = autocompleteRequest({
          docId: filterWidget ? viewId : dataId,
          docType: windowType,
          entity,
          propertyName: filterWidget ? parameterName : mainProperty[0].field,
          query: this.inputSearch.value,
          rowId,
          subentity,
          subentityId,
          tabId,
        });
      }

      typeaheadRequest.then(response => {
        let values = response.data.values || [];

        if (values.length === 0 && !isModal) {
          const optionNew = { key: 'NEW', caption: newRecordCaption };
          const list = [optionNew];

          this.setState({
            list,
            forceEmpty: true,
            loading: false,
            selected: optionNew,
          });
        } else {
          this.setState({
            list: values,
            forceEmpty: false,
            loading: false,
            selected: values[0],
          });
        }
      });
    } else {
      this.setState({
        isInputEmpty: true,
        query: this.inputSearch.value,
        list: recent,
      });

      handleInputEmptyStatus(true);
    }
  };

  handleValueChanged = () => {
    const { defaultValue, filterWidget } = this.props;
    const { oldValue, isInputEmpty } = this.state;

    if (!filterWidget && !!defaultValue && this.inputSearch) {
      const init = defaultValue;
      const inputValue = init.caption;

      if (inputValue !== oldValue) {
        this.inputSearch.value = inputValue;

        this.setState({
          oldValue: inputValue,
          isInputEmpty: false,
          list: [init],
        });
      } else if (isInputEmpty) {
        this.setState({
          isInputEmpty: false,
          list: [init],
        });
      }
    } else if (oldValue && !defaultValue && this.inputSearch) {
      const inputEmptyValue = defaultValue;

      if (inputEmptyValue !== oldValue) {
        this.inputSearch.value = inputEmptyValue;

        this.setState({
          oldValue: inputEmptyValue,
          isInputEmpty: true,
        });
      }
    }
  };

  handleTemporarySelection = selected => {
    this.setState({ selected });
  };

  render() {
    const {
      align,
      placeholder,
      readonly,
      disabled,
      tabIndex,
      isOpen,
    } = this.props;

    const {
      isInputEmpty,
      list,
      loading,
      selected,
      forceEmpty,
      isFocused,
      parentElement,
    } = this.state;

    const tetherProps = {};
    if (parentElement) {
      tetherProps.target = parentElement;
    }

    return (
      <TetherComponent
        attachment="top left"
        {...tetherProps}
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
          className={classnames('raw-lookup-wrapper raw-lookup-wrapper-bcg', {
            'raw-lookup-disabled': disabled,
            'input-disabled': readonly,
            focused: isFocused,
          })}
          ref={ref => (this.wrapper = ref)}
        >
          <div className={'input-dropdown input-block'}>
            <div className={'input-editable' + (align ? ' text-' + align : '')}>
              <input
                ref={c => (this.inputSearch = c)}
                type="text"
                className="input-field js-input-field font-weight-semibold"
                readOnly={readonly}
                disabled={readonly && !disabled}
                tabIndex={tabIndex}
                placeholder={placeholder}
                onChange={this.handleChange}
                onFocus={this.handleFocus}
              />
            </div>
          </div>
        </div>
        {isOpen &&
          !isInputEmpty && (
            <SelectionDropdown
              loading={loading}
              options={list}
              empty="No results found"
              forceEmpty={forceEmpty}
              selected={selected}
              width={
                this.props.forcedWidth
                  ? this.props.forcedWidth
                  : this.wrapper && this.wrapper.offsetWidth
              }
              onChange={this.handleTemporarySelection}
              onSelect={this.handleSelect}
              onCancel={this.handleBlur}
            />
          )}
      </TetherComponent>
    );
  }
}

const mapStateToProps = state => ({
  filter: state.windowHandler.filter,
});

RawLookup.propTypes = {
  dispatch: PropTypes.func.isRequired,
  onFocus: PropTypes.func,
  onBlur: PropTypes.func,
};

export default connect(mapStateToProps)(RawLookup);
