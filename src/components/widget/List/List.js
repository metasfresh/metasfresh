import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import { dropdownRequest } from '../../../actions/GenericActions';
import { getViewAttributeDropdown } from '../../../actions/ViewAttributesActions';
import RawList from './RawList';

class List extends Component {
  state = {
    list: null,
    loading: false,
    selectedItem: '',
  };

  previousValue = '';

  componentDidMount() {
    const { defaultValue } = this.props;

    if (defaultValue) {
      this.previousValue = defaultValue.caption;
    }
  }

  componentDidUpdate(prevProps) {
    const { isInputEmpty } = this.props;

    if (isInputEmpty && prevProps.isInputEmpty !== isInputEmpty) {
      this.previousValue = '';
    }
  }

  requestListData = (forceSelection = false, forceFocus = false) => {
    const {
      properties,
      dataId,
      rowId,
      tabId,
      windowType,
      filterWidget,
      entity,
      subentity,
      subentityId,
      viewId,
      attribute,
    } = this.props;

    this.setState({
      list: [],
      loading: true,
    });

    const propertyName = filterWidget
      ? properties[0].parameterName
      : properties[0].field;

    const request = attribute
      ? getViewAttributeDropdown(windowType, viewId, dataId, propertyName)
      : dropdownRequest({
          attribute,
          docId: dataId,
          docType: windowType,
          entity,
          subentity,
          subentityId,
          tabId,
          viewId,
          propertyName,
          rowId,
        });

    request.then(res => {
      let values = res.data.values || [];
      let singleOption = values && values.length === 1;

      if (forceSelection && singleOption) {
        this.previousValue = '';

        this.setState({
          list: values,
          loading: false,
        });

        let firstListValue = values[0];
        if (firstListValue) {
          this.handleSelect(firstListValue);
        }
      } else {
        this.setState({
          list: values,
          loading: false,
        });
      }

      if (forceFocus && values && values.length > 0) {
        this.focus();
      }
    });
  };

  handleFocus = () => {
    const { onFocus } = this.props;

    if (this.state && !this.state.list && !this.state.loading) {
      this.requestListData();
    }

    onFocus && onFocus();
  };

  focus = () => {
    if (this.rawList && this.rawList.focus) {
      this.rawList.focus();
    }
  };

  closeDropdownList = () => {
    if (this.rawList) {
      this.rawList.closeDropdownList();
    }
  };

  activate = () => {
    const { list } = this.state;

    if (list && list.length > 1) {
      if (this.rawList) {
        this.rawList.openDropdownList();
        this.rawList.focus();
      }
    }
  };

  handleSelect = option => {
    const {
      onChange,
      lookupList,
      properties,
      setNextProperty,
      mainProperty,
      enableAutofocus,
    } = this.props;

    if (enableAutofocus) {
      enableAutofocus();
    }

    if (this.previousValue !== (option && option.caption)) {
      if (lookupList) {
        const promise = onChange(properties[0].field, option);
        const mainPropertyField = mainProperty[0].field;

        if (option) {
          this.setState({
            selectedItem: option,
          });

          this.previousValue = option.caption;
        }

        if (promise) {
          promise.then(patchResult => {
            setNextProperty(mainPropertyField);

            if (
              patchResult &&
              Array.isArray(patchResult) &&
              patchResult[0] &&
              patchResult[0].fieldsByName
            ) {
              let patchFields = patchResult[0].fieldsByName;
              if (patchFields.lookupValuesStale === true) {
                this.setState({
                  list: null,
                });
              }
            }
          });
        } else {
          setNextProperty(mainPropertyField);
        }
      } else {
        onChange(option);
      }
    }
  };

  render() {
    const { selected, lookupList } = this.props;
    const { list, loading, selectedItem } = this.state;

    return (
      <RawList
        {...this.props}
        ref={c => (this.rawList = c && c.getWrappedInstance())}
        loading={loading}
        list={list || []}
        selected={lookupList ? selectedItem : selected}
        onRequestListData={this.requestListData}
        onFocus={this.handleFocus}
        onSelect={option => this.handleSelect(option)}
      />
    );
  }
}

List.propTypes = {
  dispatch: PropTypes.func.isRequired,
  autoFocus: PropTypes.bool.isRequired,
};

export default connect(false, false, false, { withRef: true })(List);
