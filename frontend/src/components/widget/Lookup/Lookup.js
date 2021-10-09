import React, { Component } from 'react';
import counterpart from 'counterpart';
import PropTypes from 'prop-types';
import onClickOutside from 'react-onclickoutside';
import classnames from 'classnames';
import * as _ from 'lodash';

import { withForwardedRef } from '../../hoc/WithRouterAndRef';
import { getItemsByProperty } from '../../../utils';
import BarcodeScanner from '../BarcodeScanner/BarcodeScannerWidget';
import List from '../List/List';
import RawLookup from './RawLookup';
import WidgetTooltip from '../WidgetTooltip';
class Lookup extends Component {
  rawLookupsState = {};

  constructor(props) {
    super(props);

    const lookupWidgets = {};
    if (props.properties) {
      props.properties.forEach((item) => {
        lookupWidgets[`${item.field}`] = {
          dropdownOpen: false,
          tooltipOpen: false,
          fireDropdownList: false,
          isFocused: false,
          isInputEmpyt: false,
        };
      });

      this.rawLookupsState = { ...lookupWidgets };
    }

    this.state = {
      isInputEmpty: true,
      propertiesCopy: getItemsByProperty(props.properties, 'source', 'list'),
      property: '',
      initialFocus: props.initialFocus,
      localClearing: false,
      autofocusDisabled: false,
      isFocused: false,
      isDropdownListOpen: false,
      lookupWidgets,
    };
  }

  componentDidMount() {
    this.checkIfDefaultValue();

    this.mounted = true;
  }

  componentWillUnmount() {
    this.mounted = false;
  }

  UNSAFE_componentWillReceiveProps(nextProps) {
    const { widgetData, selected } = this.props;

    if (
      widgetData &&
      nextProps.widgetData &&
      !_.isEqual(widgetData[0].value, nextProps.widgetData[0].value)
    ) {
      this.checkIfDefaultValue();
    }

    if (!_.isEqual(selected, nextProps.selected)) {
      this.setState({
        isInputEmpty: !nextProps.selected,
        localClearing: false,
      });
    }
  }

  getLookupWidget = (name) => {
    return { ...this.state.lookupWidgets[`${name}`] };
  };

  getFocused = (fieldName) => this.getLookupWidget(fieldName).isFocused;

  _changeWidgetProperty = (field, property, value, callback) => {
    const { lookupWidgets } = this.state;

    if (lookupWidgets[`${field}`][`${property}`] !== property) {
      const newLookupWidgets = {
        ...lookupWidgets,
        [`${field}`]: {
          ...lookupWidgets[`${field}`],
          [`${property}`]: value,
        },
      };

      this.setState(
        {
          lookupWidgets: newLookupWidgets,
        },
        callback
      );
    }
  };

  checkIfDefaultValue = () => {
    const { isFilterActive, updateItems, widgetData } = this.props;

    if (widgetData) {
      !isFilterActive &&
        updateItems &&
        updateItems({
          widgetField: widgetData[0].field,
          value: widgetData[0].defaultValue,
        });
      widgetData.map((item) => {
        if (item.value) {
          this.setState({
            isInputEmpty: false,
          });
        }
      });
    }
  };

  setNextProperty = (prop) => {
    const { widgetData, properties, onBlurWidget } = this.props;

    if (widgetData) {
      widgetData.map((item, index) => {
        const nextIndex = index + 1;

        if (nextIndex < widgetData.length && widgetData[index].field === prop) {
          let nextProp = properties[nextIndex];

          this.setState(
            {
              property: nextProp.field,
            },
            () => {
              onBlurWidget && onBlurWidget();
            }
          );
        } else if (widgetData[widgetData.length - 1].field === prop) {
          this.setState(
            {
              property: '',
            },
            () => {
              onBlurWidget && onBlurWidget();
            }
          );
        }
      });
    }
  };

  // mouse param is to tell us if we should enable listening to keys
  // in Table or not. If user selected option with mouse, we still
  // wait for more keyboard action (until the field is blurred with keyboard)
  dropdownListToggle = (value, field, mouse) => {
    const { onFocus, onBlur } = this.props;

    this._changeWidgetProperty(field, 'dropdownOpen', value, () => {
      this.setState({
        isDropdownListOpen: value,
      });

      if (mouse) {
        if (value && onFocus) {
          onFocus();
        } else if (!value && onBlur) {
          onBlur();
        }
      }
    });
  };

  widgetTooltipToggle = (field, value) => {
    const curVal = this.getLookupWidget(field).tooltipOpen;
    const newVal = value != null ? value : !curVal;

    this._changeWidgetProperty(field, 'tooltipOpen', newVal);
  };

  resetLocalClearing = () => {
    // TODO: Rewrite per widget if needed
    this.setState({
      localClearing: false,
    });
  };

  handleClickOutside = () => {
    const { onClickOutside } = this.props;
    const { isDropdownListOpen, isFocused } = this.state;

    if (isDropdownListOpen || isFocused) {
      this.setState(
        {
          isDropdownListOpen: false,
          isFocused: false,
          lookupWidgets: this.rawLookupsState,
          property: '',
        },
        () => {
          onClickOutside && onClickOutside();
        }
      );
    }
  };

  handleInputEmptyStatus = (isEmpty) => {
    this.setState({
      isInputEmpty: isEmpty,
    });
  };

  // TODO: Rewrite per widget if needed
  handleClear = () => {
    if (this.mounted) {
      const { updateItems, widgetData, onChange, properties, onSelectBarcode } =
        this.props;
      const propsWithoutTooltips = properties.filter(
        (prop) => prop.type !== 'Tooltip'
      );
      const onChangeResp =
        onChange && onChange(propsWithoutTooltips, null, false);

      if (onChangeResp && onChangeResp.then) {
        onChangeResp.then((resp) => {
          if (resp) {
            updateItems &&
              updateItems({
                widgetField: widgetData[0].field,
                value: '',
              });

            if (this.mounted) {
              onSelectBarcode && onSelectBarcode(null);

              this.setState({
                isInputEmpty: true,
                property: '',
                initialFocus: true,
                localClearing: true,
                autofocusDisabled: false,
              });
            }
          }
        });
      }
    }
  };

  handleListFocus = (field) => {
    this._changeWidgetProperty(field, 'isFocused', true, () => {
      this.setState({
        isFocused: true,
      });
      this.props.onFocus();
    });
  };

  handleListBlur = (field) => {
    this._changeWidgetProperty(field, 'isFocused', false, () => {
      this.setState({
        isFocused: false,
      });
      this.props.onBlur();
    });
  };

  disableAutofocus = () => {
    this.setState({
      autofocusDisabled: true,
    });
  };

  enableAutofocus = () => {
    this.setState({
      autofocusDisabled: false,
    });
  };

  renderInputControls = (showBarcodeScanner) => {
    const { onScanBarcode } = this.props;
    const { isInputEmpty } = this.state;

    return (
      <div
        className="input-icon input-icon-lg lookup-widget-wrapper"
        onClick={null}
      >
        {showBarcodeScanner ? (
          <button
            className="btn btn-sm btn-meta-success btn-scanner"
            onClick={() => onScanBarcode(true)}
          >
            {counterpart.translate('widget.scanFromCamera.caption')}
          </button>
        ) : null}
        <i
          className={classnames({
            'meta-icon-close-alt': !isInputEmpty,
            'meta-icon-preview': isInputEmpty,
          })}
          onClick={!isInputEmpty ? this.handleClear : null}
        />
      </div>
    );
  };

  setRef = (refNode) => {
    this.dropdown = refNode;
  };

  render() {
    const {
      rank,
      readonly,
      widgetData,
      placeholder,
      align,
      isModal,
      updated,
      filterWidget,
      mandatory,
      rowId,
      tabIndex,
      validStatus,
      onChange,
      newRecordCaption,
      properties,
      windowType,
      parameterName,
      entity,
      dataId,
      tabId,
      subentity,
      subentityId,
      viewId,
      autoFocus,
      newRecordWindowId,
      scanning,
      codeSelected,
      scannerElement,
      forceFullWidth,
      forceHeight,
      advSearchCaption,
      advSearchWindowId,
      forwardedRef,
      isFilterActive,
      updateItems,
    } = this.props;

    const {
      isInputEmpty,
      property,
      initialFocus,
      localClearing,
      fireDropdownList,
      autofocusDisabled,
    } = this.state;

    const mandatoryInputCondition =
      mandatory &&
      (isInputEmpty ||
        (validStatus && validStatus.initialValue && !validStatus.valid));

    const errorInputCondition =
      validStatus && !validStatus.valid && !validStatus.initialValue;
    const classRank = rank || 'primary';
    let showBarcodeScannerBtn = false;

    if (scanning) {
      return (
        <div className="overlay-field js-not-unselect">{scannerElement}</div>
      );
    }

    return (
      <div
        ref={this.setRef}
        className={classnames(
          'input-dropdown-container lookup-wrapper',
          `input-${classRank}`,
          {
            'pulse-on': updated,
            'pulse-off': !updated,
            'input-full': filterWidget,
            'input-mandatory': mandatoryInputCondition,
            'input-error': errorInputCondition,
            'lookup-wrapper-disabled': readonly,
          }
        )}
      >
        {properties &&
          properties.map((item, index) => {
            // if (index < 2 && this.props.properties[0].field === "C_BPartner_ID") {
            // TODO: This is really not how we should be doing this. Backend should send
            // us info which fields are usable with barcode scanner
            showBarcodeScannerBtn = item.field === 'M_LocatorTo_ID';

            const lookupWidget = this.getLookupWidget(item.field);
            const disabled = isInputEmpty && index !== 0;
            const itemByProperty = getItemsByProperty(
              widgetData,
              'field',
              item.field
            )[0];
            const widgetTooltipToggled = lookupWidget.tooltipOpen;
            const idValue = `lookup_${item.field}`;

            if (item.type === 'Tooltip') {
              if (!itemByProperty.value) {
                return null;
              }

              return (
                <div
                  key={item.field}
                  id={idValue}
                  className="lookup-widget-wrapper lookup-tooltip"
                >
                  <WidgetTooltip
                    widget={item}
                    fieldName={item.field}
                    data={itemByProperty}
                    isToggled={widgetTooltipToggled}
                    onToggle={this.widgetTooltipToggle}
                  />
                </div>
              );
            } else if (
              item.source === 'lookup' ||
              item.widgetType === 'Lookup' ||
              (itemByProperty && itemByProperty.widgetType === 'Lookup')
            ) {
              let defaultValue = localClearing ? null : itemByProperty.value;

              if (codeSelected) {
                defaultValue = { caption: codeSelected };
              }

              defaultValue =
                !isFilterActive && updateItems
                  ? item.defaultValue
                  : defaultValue;

              let width = null;
              // for multiple lookup widget we want the dropdown
              // to be full width of the widget component
              if (forceFullWidth && this.dropdown) {
                width = this.dropdown.offsetWidth;
              }

              return (
                <RawLookup
                  ref={index === 0 && forwardedRef}
                  key={index}
                  idValue={idValue}
                  defaultValue={defaultValue}
                  autoFocus={index === 0 && autoFocus}
                  initialFocus={index === 0 && initialFocus}
                  mainProperty={item}
                  readonly={widgetData[index].readonly}
                  mandatory={widgetData[index].mandatory}
                  resetLocalClearing={this.resetLocalClearing}
                  setNextProperty={this.setNextProperty}
                  lookupEmpty={isInputEmpty}
                  fireDropdownList={fireDropdownList}
                  handleInputEmptyStatus={
                    index === 0 && this.handleInputEmptyStatus
                  }
                  enableAutofocus={this.enableAutofocus}
                  isOpen={lookupWidget.dropdownOpen}
                  onDropdownListToggle={this.dropdownListToggle}
                  forcedWidth={width}
                  forceHeight={forceHeight}
                  isComposed={this.props.properties.length > 1 ? true : false}
                  {...{
                    placeholder,
                    tabIndex,
                    windowType,
                    parameterName,
                    entity,
                    dataId,
                    isModal,
                    rank,
                    updated,
                    filterWidget,
                    validStatus,
                    align,
                    onChange,
                    item,
                    disabled,
                    viewId,
                    subentity,
                    subentityId,
                    tabId,
                    rowId,
                    newRecordCaption,
                    newRecordWindowId,
                    localClearing,
                    advSearchCaption,
                    advSearchWindowId,
                    updateItems,
                  }}
                />
              );
            } else if (
              widgetData &&
              (item.source === 'list' ||
                item.widgetType === 'List' ||
                (itemByProperty && itemByProperty.source === 'List'))
            ) {
              const isFirstProperty = index === 0;
              const isCurrentProperty =
                item.field === property && !autofocusDisabled;
              let defaultValue = localClearing ? null : itemByProperty.value;

              return (
                <div
                  key={item.field}
                  id={idValue}
                  className={classnames(
                    'lookup-widget-wrapper lookup-widget-wrapper-bcg',
                    {
                      'raw-lookup-disabled': disabled || readonly,
                      focused: this.getFocused(item.field),
                    }
                  )}
                >
                  <List
                    ref={forwardedRef}
                    field={item.field}
                    clearable={false}
                    readonly={disabled || widgetData[index].readonly}
                    lookupList={true}
                    autoFocus={isCurrentProperty}
                    doNotOpenOnFocus={false}
                    properties={item}
                    mainProperty={item}
                    defaultValue={defaultValue ? defaultValue : ''}
                    initialFocus={isFirstProperty ? initialFocus : false}
                    emptyText={
                      this.props.properties
                        ? this.props.properties[0].emptyText
                        : placeholder
                    }
                    mandatory={widgetData[index].mandatory}
                    setNextProperty={this.setNextProperty}
                    disableAutofocus={this.disableAutofocus}
                    enableAutofocus={this.enableAutofocus}
                    onFocus={this.handleListFocus}
                    onBlur={this.handleListBlur}
                    compositeWidgetData={widgetData}
                    {...{
                      dataId,
                      entity,
                      windowType,
                      filterWidget,
                      tabId,
                      rowId,
                      subentity,
                      subentityId,
                      viewId,
                      onChange,
                      isInputEmpty,
                      property,
                      tabIndex,
                    }}
                  />
                </div>
              );
            }
          })}

        {!readonly && this.renderInputControls(showBarcodeScannerBtn)}
      </div>
    );
  }
}

Lookup.propTypes = {
  onFocus: PropTypes.func,
  onBlur: PropTypes.func,
  onBlurWidget: PropTypes.func,
  forceFullWidth: PropTypes.bool,
  forceHeight: PropTypes.number,
  widgetData: PropTypes.array,
  defaultValue: PropTypes.any,
  selected: PropTypes.any,
  mandatory: PropTypes.bool,
  properties: PropTypes.array,
  initialFocus: PropTypes.bool,
  onClickOutside: PropTypes.func,
  onChange: PropTypes.func,
  validStatus: PropTypes.object,
  newRecordCaption: PropTypes.any,
  windowType: PropTypes.string,
  parameterName: PropTypes.string,
  entity: PropTypes.any,
  dataId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  tabId: PropTypes.string,
  subentity: PropTypes.any,
  subentityId: PropTypes.string,
  viewId: PropTypes.string,
  onSelectBarcode: PropTypes.any,
  onScanBarcode: PropTypes.any,
  rank: PropTypes.any,
  readonly: PropTypes.bool,
  placeholder: PropTypes.string,
  align: PropTypes.any,
  isModal: PropTypes.bool,
  updated: PropTypes.bool,
  filterWidget: PropTypes.any,
  rowId: PropTypes.string,
  tabIndex: PropTypes.number,
  autoFocus: PropTypes.bool,
  newRecordWindowId: PropTypes.string,
  scanning: PropTypes.any,
  codeSelected: PropTypes.any,
  scannerElement: PropTypes.any,
  advSearchCaption: PropTypes.string,
  advSearchWindowId: PropTypes.string,
  forwardedRef: PropTypes.any,
  isFilterActive: PropTypes.bool,
  updateItems: PropTypes.func,
};

export default withForwardedRef(BarcodeScanner(onClickOutside(Lookup)));
