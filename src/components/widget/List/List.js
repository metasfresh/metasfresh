import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import { dropdownRequest } from '../../../actions/GenericActions';
import { getViewAttributeDropdown } from '../../../actions/ViewAttributesActions';
import RawList from './RawList';

class List extends Component {
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
    // const { autoFocus: stateAutoFocus } = this.state;
    // const { autoFocus } = this.state;
    // if (!isFocused) {
    //   if (autoFocus) {
    //     this.focus();
    //   } else {
    //     if (initialFocus && !defaultValue) {
    //       this.focus();
    //     }
    //   }
    // }
    if (nextAutoFocus !== autoFocus && !isModal) {
      this.setState({
        autoFocus: nextAutoFocus,
      });
    }
  }

  componentDidUpdate(prevProps) {
    const { isInputEmpty } = this.props;
    const { initialFocus, defaultValue } = this.props;
    const { autoFocus, isFocused } = this.state;

    if (isInputEmpty && prevProps.isInputEmpty !== isInputEmpty) {
      this.previousValue = '';
    }

    // if (!this.props.lookupList && this.dropdown.className.indexOf('select-dropdown') > 0) {
    //   console.log('here1: ', autoFocus, initialFocus, defaultValue, doNotOpenOnFocus)
    // }

    // focus
    if (prevProps.autoFocus !== autoFocus && !isFocused) {
      if (autoFocus) {
        this.handleFocus();
      } else {
        if (initialFocus && !defaultValue) {
          this.handleFocus();
        }
      }
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
      lastProperty,
      disableAutofocus,
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

      if (values.length === 0 && lastProperty) {
        disableAutofocus();
      } else if ((forceFocus || (!forceFocus && this.state.autoFocus)) &&
        values && values.length > 0) {
        this.focus();
      }
    });
  };

  handleFocus = () => {
    const { onFocus } = this.props;
    const { list, loading } = this.state;

    console.log('handleFocus: ', list, loading)

    if (!list && !loading) {
      this.requestListData();
    }

    onFocus && onFocus();

    this.focus();
  };

  // handleFocus = () => {
  //   const { onFocus, doNotOpenOnFocus, autoFocus } = this.props;

  //   console.log('RawList handle focus')

  //   if (!doNotOpenOnFocus && !autoFocus) {
  //     this.openDropdownList(true);
  //   } else {
  //     this.focus();
  //   }

  //   onFocus && onFocus();
  // };

  focus = () => {
    // if (this.rawList && this.rawList.focus) {
    //   this.rawList.focus();
    // }

    this.setState({
      listFocused: true,
    });
  };

  // focus = () => {
  //   if (this.state.isOpened) {
  //   // console.log('RawList focus')
  //     if (this.dropdown && this.dropdown.focus) {
  //       this.dropdown.focus();
  //     }

  //     this.setState({
  //       isFocused: true,
  //     });
  //   }
  // };

  handleBlur = () => {
    const { onHandleBlur } = this.props;

    this.setState(
    {
      listFocused: false,
    }, () => {
      onHandleBlur && onHandleBlur();
    });
  };

  // blur = () => {
  //   if (this.state.isFocused) {
  //     if (this.dropdown && this.dropdown.blur) {
  //       this.dropdown.blur();
  //     }

  //     this.setState({
  //       isFocused: false,
  //     });
  //   }
  // };

  closeDropdownList = () => {
    this.setState({
      listToggled: false,
    });

    this.handleBlur();
  };

  activate = () => {
    const { list } = this.state;

    console.log('activate !!: ', list)

    // if (list && list.length > 1) {
      // if (this.rawList) {
      //   this.rawList.focus();
      //   this.rawList.openDropdownList();
      // }
      this.setState({
        listToggled: true,
      });
    // }
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

        if (isModal) {
          console.log('List handleSelect isModal !')
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
    const { list, loading, selectedItem, autoFocus, listToggled, listFocused } = this.state;

    return (
      <RawList
        {...this.props}
        autoFocus={autoFocus}
        loading={loading}
        list={list || []}
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
// RawList.defaultProps = {
//   doNotOpenOnFocus: true,
// };

List.propTypes = {
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
  subentity: PropTypes.object,
  subentityId: PropTypes.string,
  viewId: PropTypes.any,
  attribute: PropTypes.any,
  lookupList: PropTypes.bool,
  mainProperty: PropTypes.any,
  isModal: PropTypes.bool,
  autoFocus: PropTypes.bool,
  selected: PropTypes.object,
  setNextProperty: PropTypes.func,
  enableAutofocus: PropTypes.func,
  onChange: PropTypes.func,
  onFocus: PropTypes.func,
  onHandleBlur: PropTypes.func,
};

const mapStateToProps = state => ({
  filter: state.windowHandler.filter,
});

export default connect(mapStateToProps, false, false, { withRef: true })(List);
