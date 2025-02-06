import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { findKey } from 'lodash';
import { v4 as uuidv4 } from 'uuid';

import {
  dropdownRequest,
  dropdownModalRequest,
} from '../../../actions/GenericActions';
import { getViewAttributeDropdown } from '../../../api';
import RawList from './RawList';

class ListWidget extends Component {
  previousValue = '';

  constructor(props) {
    super(props);

    this.state = {
      list: [],
      listHash: null,
      loading: false,
      selectedItem: '',
      autoFocus: props.autoFocus,
      listToggled: false,
      listFocused: false,
    };
  }

  componentDidMount() {
    const { defaultValue } = this.props;
    const { autoFocus } = this.state;

    if (defaultValue) {
      this.previousValue = defaultValue.caption;
    }

    if (autoFocus) {
      this.requestListData();
    }
  }

  UNSAFE_componentWillReceiveProps(nextProps) {
    const { autoFocus: nextAutoFocus } = nextProps;
    const { autoFocus, isModal } = this.props;

    if (nextAutoFocus !== autoFocus && !isModal) {
      this.setState({
        autoFocus: nextAutoFocus,
      });
    }
  }

  componentDidUpdate(prevProps) {
    const { isInputEmpty } = this.props;
    const { initialFocus, defaultValue, doNotOpenOnFocus } = this.props;
    const { autoFocus, isToggled, list } = this.state;

    if (isInputEmpty && prevProps.isInputEmpty !== isInputEmpty) {
      this.previousValue = '';
    }

    if (prevProps.autoFocus !== autoFocus && !isToggled) {
      if (autoFocus) {
        this.handleFocus();
        !doNotOpenOnFocus && list.length > 1 && this.activate();
      } else {
        if (initialFocus && !defaultValue) {
          this.handleFocus();
          !doNotOpenOnFocus && list.length > 1 && this.activate();
        }
      }
    }
  }

  requestListDataIfNotLoaded = (autoSelectIfSingleOption = null) => {
    const { list, loading } = this.state;

    // Do nothing if loading in progress...
    if (loading) {
      return;
    }

    // Already loaded
    if (list && list.length > 0) {
      return;
    }

    const autoSelectIfSingleOptionEffective =
      autoSelectIfSingleOption != null
        ? !!autoSelectIfSingleOption
        : this.props.mandatory;

    this.requestListData(autoSelectIfSingleOptionEffective, true);
  };

  requestListData = (forceSelection = false, ignoreFocus = false) => {
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
      lastProperty,
      disableAutofocus,
      doNotOpenOnFocus,
      dropdownValuesSupplier,
    } = this.props;

    // console.trace('requestListData', {
    //   forceSelection,
    //   ignoreFocus,
    //   state: this.state,
    //   props: this.props,
    // });

    this.setState(
      {
        list: [],
        loading: true,
      },
      () => {
        const propertyName = filterWidget
          ? properties.parameterName
          : properties.field;

        let request;

        if (dropdownValuesSupplier) {
          request = dropdownValuesSupplier({
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
        } else if (viewId && entity === 'window' && !filterWidget) {
          request = dropdownModalRequest({
            windowId: windowType,
            fieldName: propertyName,
            entity: 'documentView',
            viewId,
            rowId,
          });
        } else if (attribute) {
          request = getViewAttributeDropdown(
            windowType,
            viewId,
            dataId,
            propertyName
          );
        } else {
          request = dropdownRequest({
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
        }

        request.then((res) => {
          const hasMoreResults = !!res.data.hasMoreResults;
          const values = res.data.values || [];
          const singleOption = values && values.length === 1;

          if (forceSelection && singleOption) {
            this.previousValue = '';

            this.setState({
              list: values,
              listHash: uuidv4(),
              hasMoreResults,
              loading: false,
            });

            let firstListValue = values[0];
            if (firstListValue) {
              this.handleSelect(firstListValue);
            }
          } else {
            this.setState({
              list: values,
              listHash: uuidv4(),
              hasMoreResults,
              loading: false,
            });
          }

          if (values.length === 0 && lastProperty) {
            disableAutofocus();
          } else if (
            (ignoreFocus || this.state.autoFocus) &&
            values &&
            values.length > 1
          ) {
            !this.state.listFocused && this.handleFocus();
            !doNotOpenOnFocus && this.activate();
          }
        });
      }
    );
  };

  handleFocus = () => {
    this.focus();
    this.requestListDataIfNotLoaded();
  };

  focus = () => {
    this.setState({ listFocused: true });
  };

  handleBlur = () => {
    const { field, onBlur } = this.props;

    this.setState(
      {
        autoFocus: false,
        listFocused: false,
        list: [],
        listHash: null,
        listToggled: false,
      },
      () => {
        onBlur && onBlur(field);
      }
    );
  };

  handleOpenDropdownRequest = () => {
    this.requestListDataIfNotLoaded();
    this.activate();
  };

  handleCloseDropdownRequest = () => {
    this.setState({ listToggled: false });
  };

  activate = () => {
    const { list, listToggled } = this.state;
    const { lookupList, mandatory } = this.props;

    if (
      (!listToggled && !(lookupList && list.length < 1)) ||
      (list.size === 0 && mandatory)
    ) {
      this.setState({ listToggled: true });
    }
  };

  handleSelect = (option) => {
    const {
      onChange,
      lookupList,
      properties,
      setNextProperty,
      mainProperty,
      enableAutofocus,
      isModal,
      widgetField,
    } = this.props;

    if (enableAutofocus) {
      enableAutofocus();
    }

    if (this.previousValue !== (option && option.caption)) {
      if (lookupList) {
        const promise = onChange(properties.field, option);
        const mainPropertyField = mainProperty.field;

        this.setState({
          selectedItem: option,
        });

        this.previousValue =
          option !== null && option.caption !== 'undefined'
            ? option.caption
            : option;

        if (promise) {
          promise.then((patchResult) => {
            setNextProperty(mainPropertyField);

            if (
              patchResult &&
              Array.isArray(patchResult) &&
              patchResult[0] &&
              patchResult[0].fieldsByName
            ) {
              const patchFields = patchResult[0].fieldsByName;

              if (
                patchFields.lookupValuesStale === true ||
                findKey(patchFields, ['widgetType', 'List'])
              ) {
                this.setState({
                  list: [],
                  listHash: null,
                });
              }
            }
          });
        } else {
          setNextProperty(mainPropertyField);
        }

        if (isModal) {
          this.setState({
            autoFocus: false,
          });
        }
      } else {
        onChange(widgetField, option);
      }
    }
  };

  render() {
    const { selected, lookupList } = this.props;
    const {
      list,
      listHash,
      hasMoreResults,
      loading,
      selectedItem,
      autoFocus,
      listToggled,
      listFocused,
    } = this.state;

    return (
      <RawList
        {...this.props}
        autoFocus={autoFocus}
        loading={loading}
        list={list}
        listHash={listHash}
        hasMoreResults={hasMoreResults}
        selected={lookupList ? selectedItem : selected}
        isToggled={listToggled}
        isFocused={listFocused}
        onOpenDropdown={this.handleOpenDropdownRequest}
        onCloseDropdown={this.handleCloseDropdownRequest}
        onFocus={this.handleFocus}
        onBlur={this.handleBlur}
        onSelect={this.handleSelect}
      />
    );
  }
}

/**
 * doNotOpenOnFocus - by default we expect user to press space/arrow down when the dropdown field is focused ho
 *                    show the dropdown with options
 */
ListWidget.defaultProps = {
  doNotOpenOnFocus: true,
};

ListWidget.propTypes = {
  properties: PropTypes.object,
  isInputEmpty: PropTypes.bool,
  defaultValue: PropTypes.any,
  dataId: PropTypes.any,
  rowId: PropTypes.any,
  tabId: PropTypes.any,
  windowType: PropTypes.string,
  filterWidget: PropTypes.any,
  entity: PropTypes.string,
  subentity: PropTypes.string,
  subentityId: PropTypes.string,
  viewId: PropTypes.string,
  attribute: PropTypes.any,
  lookupList: PropTypes.bool,
  mainProperty: PropTypes.object,
  isModal: PropTypes.bool,
  autoFocus: PropTypes.bool,
  selected: PropTypes.oneOfType([PropTypes.object, PropTypes.array]),
  initialFocus: PropTypes.any,
  doNotOpenOnFocus: PropTypes.bool,
  widgetField: PropTypes.string,
  field: PropTypes.string,
  mandatory: PropTypes.bool,
  lastProperty: PropTypes.string,
  compositeWidgetData: PropTypes.array,
  //
  // Callbacks and other functions
  setNextProperty: PropTypes.func,
  disableAutofocus: PropTypes.func,
  enableAutofocus: PropTypes.func,
  onChange: PropTypes.func,
  onFocus: PropTypes.func,
  onBlur: PropTypes.func,
  dropdownValuesSupplier: PropTypes.func,

  //
  // mapStateToProps:
  filter: PropTypes.shape({
    visible: PropTypes.bool,
    boundingRect: PropTypes.object,
  }),

  //
  // mapDispatchToProps
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = (state) => ({
  filter: state.windowHandler.filter,
});

export default connect(mapStateToProps, false, false, { forwardRef: true })(
  ListWidget
);
