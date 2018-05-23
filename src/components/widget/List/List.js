import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import { List } from 'immutable';
import { findKey } from 'lodash';

import {
  dropdownRequest,
  dropdownModalRequest,
} from '../../../actions/GenericActions';
import { getViewAttributeDropdown } from '../../../actions/ViewAttributesActions';
import RawList from './RawList';

class ListWidget extends Component {
  previousValue = '';

  constructor(props) {
    super(props);

    this.state = {
      list: null,
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

  componentWillReceiveProps(nextProps) {
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
        !doNotOpenOnFocus && list && list.size > 1 && this.activate();
      } else {
        if (initialFocus && !defaultValue) {
          this.handleFocus();
          !doNotOpenOnFocus && list && list.size > 1 && this.activate();
        }
      }
    }
  }

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
    } = this.props;
    const { listFocused } = this.state;

    this.setState({
      list: List(),
      loading: true,
    });

    const propertyName = filterWidget
      ? properties[0].parameterName
      : properties[0].field;

    let request = null;

    if (viewId && entity === 'window' && !filterWidget) {
      request = dropdownModalRequest({
        windowId: windowType,
        fieldName: propertyName,
        entity: 'documentView',
        viewId,
        rowId,
      });
    } else {
      request = attribute
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
    }

    request.then(res => {
      let values = res.data.values || [];
      let singleOption = values && values.length === 1;

      if (forceSelection && singleOption) {
        this.previousValue = '';

        this.setState({
          list: List(values),
          loading: false,
        });

        let firstListValue = values[0];
        if (firstListValue) {
          this.handleSelect(firstListValue);
        }
      } else {
        this.setState({
          list: List(values),
          loading: false,
        });
      }

      if (values.length === 0 && lastProperty) {
        disableAutofocus();
      } else if (
        !ignoreFocus &&
        this.state.autoFocus &&
        values &&
        values.length > 1
      ) {
        !listFocused && this.handleFocus();
        !doNotOpenOnFocus && this.activate();
      }
    });
  };

  handleFocus = () => {
    const { onFocus, mandatory } = this.props;
    const { list, loading } = this.state;

    this.focus();
    onFocus && onFocus();

    if (!list && !loading) {
      this.requestListData(mandatory, true);
    }
  };

  focus = () => {
    this.setState({
      listFocused: true,
    });
  };

  handleBlur = () => {
    const { onBlur } = this.props;

    this.setState(
      {
        autoFocus: false,
        listFocused: false,
        list: null,
      },
      () => {
        onBlur && onBlur();
      }
    );
  };

  closeDropdownList = () => {
    this.setState({
      listToggled: false,
    });
  };

  activate = () => {
    const { list, listToggled } = this.state;
    const { lookupList } = this.props;

    if (list && !listToggled && !(lookupList && list.size < 1)) {
      this.setState({
        listToggled: true,
      });
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
      isModal,
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
              const patchFields = patchResult[0].fieldsByName;

              if (
                patchFields.lookupValuesStale === true ||
                findKey(patchFields, ['widgetType', 'List'])
              ) {
                this.setState({
                  list: null,
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
        onChange(option);
      }
    }
  };

  render() {
    const { selected, lookupList } = this.props;
    const {
      list,
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
        list={list || List()}
        selected={lookupList ? selectedItem : selected}
        isToggled={listToggled}
        isFocused={listFocused}
        onOpenDropdown={this.activate}
        onCloseDropdown={this.closeDropdownList}
        onFocus={this.handleFocus}
        onBlur={this.handleBlur}
        onSelect={option => this.handleSelect(option)}
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
  filter: PropTypes.object.isRequired,
  dispatch: PropTypes.func.isRequired,
  properties: PropTypes.array,
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
  viewId: PropTypes.any,
  attribute: PropTypes.any,
  lookupList: PropTypes.bool,
  mainProperty: PropTypes.any,
  isModal: PropTypes.bool,
  autoFocus: PropTypes.bool,
  selected: PropTypes.object,
  initialFocus: PropTypes.any,
  doNotOpenOnFocus: PropTypes.bool,
  setNextProperty: PropTypes.func,
  disableAutoFocus: PropTypes.func,
  enableAutofocus: PropTypes.func,
  onChange: PropTypes.func,
  onFocus: PropTypes.func,
  onBlur: PropTypes.func,
};

const mapStateToProps = state => ({
  filter: state.windowHandler.filter,
});

export default connect(mapStateToProps, false, false, { withRef: true })(
  ListWidget
);
