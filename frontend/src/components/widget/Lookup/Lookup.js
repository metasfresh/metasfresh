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

/**
 * Composed lookup (e.g. partner/location/contact) component.
 * NOTE: this component is covering also the case of a simple lookup which is just a particular case.
 */
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
      property: '', // current property
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

  getItemLocalState = (name) => {
    return { ...this.state.lookupWidgets[`${name}`] };
  };

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

  setNextProperty = (currentFieldName) => {
    const { widgetData, properties, onBlurWidget } = this.props;

    if (widgetData) {
      widgetData.map((item, index) => {
        const nextIndex = index + 1;

        if (
          nextIndex < widgetData.length &&
          widgetData[index].field === currentFieldName
        ) {
          const nextProp = properties[nextIndex];
          this.setState(
            { property: nextProp.field }, //
            () => {
              onBlurWidget && onBlurWidget();
            }
          );
        } else if (
          widgetData[widgetData.length - 1].field === currentFieldName
        ) {
          this.setState(
            { property: '' }, //
            () => {
              onBlurWidget && onBlurWidget();
            }
          );
        }
      });
    }
  };

  // isMouseEvent param is to tell us if we should enable listening to keys
  // in Table or not. If user selected option with mouse, we still
  // wait for more keyboard action (until the field is blurred with keyboard)
  dropdownListToggle = (isDropdownListOpen, field, isMouseEvent) => {
    //console.log('dropdownListToggle', { isDropdownListOpen, field, isMouseEvent });

    const { onFocus, onBlur } = this.props;

    this._changeWidgetProperty(
      field,
      'dropdownOpen',
      isDropdownListOpen,
      () => {
        this.setState({ isDropdownListOpen });

        if (isMouseEvent) {
          if (isDropdownListOpen) {
            onFocus && onFocus();
          } else {
            onBlur && onBlur();
          }
        }
      }
    );
  };

  widgetTooltipToggle = (field, tooltipOpen = null) => {
    const tooltipOpenEffective =
      tooltipOpen != null
        ? !!tooltipOpen
        : !this.getItemLocalState(field).tooltipOpen;

    this._changeWidgetProperty(field, 'tooltipOpen', tooltipOpenEffective);
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
      this.setState({ isFocused: true });
      this.props.onFocus();
    });
  };

  handleListBlur = (field) => {
    this._changeWidgetProperty(field, 'isFocused', false, () => {
      this.setState({ isFocused: false });
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

  setWrapperElement = (node) => (this.wrapperElement = node);

  renderInputControls = () => {
    const { properties, onScanBarcode } = this.props;
    const { isInputEmpty } = this.state;

    // // if (index < 2 && this.props.properties[0].field === "C_BPartner_ID") {
    // // TODO: This is really not how we should be doing this. Backend should send
    // // us info which fields are usable with barcode scanner
    const isShowBarcodeScanner =
      properties && properties.some((item) => item.field === 'M_LocatorTo_ID');

    return (
      <div
        className="input-icon input-icon-lg lookup-widget-wrapper"
        onClick={null}
      >
        {isShowBarcodeScanner ? (
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

  renderSingleLookupPart = (item, index) => {
    const { widgetData } = this.props;
    const itemData = getItemsByProperty(widgetData, 'field', item.field)[0];

    if (item.type === 'Tooltip') {
      return this.renderSingleLookupPart_Tooltip(item);
    } else if (
      item.source === 'lookup' ||
      item.widgetType === 'Lookup' ||
      (itemData && itemData.widgetType === 'Lookup')
    ) {
      return this.renderSingleLookupPart_Lookup(item, index);
    } else if (
      widgetData &&
      (item.source === 'list' ||
        item.widgetType === 'List' ||
        (itemData && itemData.source === 'List'))
    ) {
      return this.renderSingleLookupPart_List(item, index);
    }
  };

  renderSingleLookupPart_Tooltip = (item) => {
    const { widgetData } = this.props;
    const itemData = getItemsByProperty(widgetData, 'field', item.field)[0];

    if (!itemData.value) {
      return null;
    }

    const idValue = `lookup_${item.field}`;
    const widgetTooltipToggled = this.getItemLocalState(item.field).tooltipOpen;

    return (
      <div
        key={item.field}
        id={idValue}
        className="lookup-widget-wrapper lookup-tooltip"
      >
        <WidgetTooltip
          iconName={item.tooltipIconName}
          text={itemData.value}
          isToggled={widgetTooltipToggled}
          onToggle={(tooltipOpen) =>
            this.widgetTooltipToggle(item.field, tooltipOpen)
          }
        />
      </div>
    );
  };

  renderSingleLookupPart_Lookup = (itemDescriptor, index) => {
    const {
      rank,
      widgetData,
      placeholder,
      align,
      isModal,
      updated,
      filterWidget,
      rowId,
      tabIndex,
      validStatus,
      onChange,
      newRecordCaption,
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
      codeSelected,
      forceFullWidth,
      forceHeight,
      advSearchCaption,
      advSearchWindowId,
      forwardedRef,
      isFilterActive,
      updateItems,
      typeaheadSupplier,
    } = this.props;

    const { isInputEmpty, initialFocus, localClearing, fireDropdownList } =
      this.state;

    const field = itemDescriptor.field;
    const itemData = getItemsByProperty(widgetData, 'field', field)[0];

    const isDropdownOpen = this.getItemLocalState(field).dropdownOpen;
    const disabled = isInputEmpty && index !== 0;
    const idValue = `lookup_${field}`;

    // console.log('renderSingleLookupPart_Lookup', {
    //   field: field,
    //   itemDescriptor,
    //   itemData,
    //   itemLocalState,
    // });

    //
    // Default value:
    let defaultValue = localClearing ? null : itemData.value;
    if (codeSelected) {
      defaultValue = { caption: codeSelected };
    }
    defaultValue =
      !isFilterActive && updateItems
        ? itemDescriptor.defaultValue
        : defaultValue;

    //
    // Width:
    let width = null;
    // for multiple lookup widget we want the dropdown
    // to be full width of the widget component
    if (forceFullWidth && this.wrapperElement) {
      width = this.wrapperElement.offsetWidth;
    }

    const isPrimaryField = index === 0;

    return (
      <RawLookup
        ref={isPrimaryField && forwardedRef}
        key={index}
        idValue={idValue}
        defaultValue={defaultValue}
        autoFocus={isPrimaryField && autoFocus}
        initialFocus={isPrimaryField && initialFocus}
        mainProperty={itemDescriptor}
        readonly={itemData.readonly}
        mandatory={itemData.mandatory}
        resetLocalClearing={this.resetLocalClearing}
        setNextProperty={this.setNextProperty}
        lookupEmpty={isInputEmpty}
        fireDropdownList={fireDropdownList}
        handleInputEmptyStatus={isPrimaryField && this.handleInputEmptyStatus}
        enableAutofocus={this.enableAutofocus}
        isOpen={isDropdownOpen}
        onDropdownListToggle={this.dropdownListToggle}
        forcedWidth={width}
        forceHeight={forceHeight}
        item={itemDescriptor}
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
          typeaheadSupplier,
        }}
      />
    );
  };

  renderSingleLookupPart_List = (itemDescriptor, index) => {
    const {
      readonly,
      widgetData,
      placeholder,
      filterWidget,
      rowId,
      tabIndex,
      onChange,
      windowType,
      entity,
      dataId,
      tabId,
      subentity,
      subentityId,
      viewId,
      forwardedRef,
    } = this.props;

    const {
      isInputEmpty,
      property,
      initialFocus,
      localClearing,
      autofocusDisabled,
    } = this.state;

    const field = itemDescriptor.field;
    const itemData = getItemsByProperty(widgetData, 'field', field)[0];

    const idValue = `lookup_${field}`;
    const isPrimaryField = index === 0;
    const isCurrentProperty = field === property && !autofocusDisabled;
    const defaultValue = localClearing ? null : itemData.value;
    const disabled = isInputEmpty && !isPrimaryField;

    // console.log('renderSingleLookupPart_List', {
    //   field: field,
    //   itemDescriptor,
    //   itemData,
    // });

    return (
      <div
        key={field}
        id={idValue}
        className={classnames(
          'lookup-widget-wrapper lookup-widget-wrapper-bcg',
          {
            'raw-lookup-disabled': disabled || readonly,
            focused: this.getItemLocalState(field).isFocused,
          }
        )}
      >
        <List
          ref={forwardedRef}
          field={field}
          clearable={false}
          readonly={disabled || itemData.readonly}
          lookupList={true}
          autoFocus={isCurrentProperty}
          doNotOpenOnFocus={false}
          properties={itemDescriptor}
          mainProperty={itemDescriptor}
          defaultValue={defaultValue ? defaultValue : ''}
          initialFocus={isPrimaryField ? initialFocus : false}
          emptyText={itemDescriptor?.emptyText || placeholder}
          mandatory={itemData.mandatory}
          setNextProperty={this.setNextProperty}
          disableAutofocus={this.disableAutofocus}
          enableAutofocus={this.enableAutofocus}
          onFocus={this.handleListFocus}
          onBlur={this.handleListBlur}
          compositeWidgetData={widgetData}
          wrapperElement={this.wrapperElement}
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
  };

  render() {
    const {
      rank,
      readonly,
      updated,
      filterWidget,
      mandatory,
      validStatus,
      properties,
      scanning,
      scannerElement,
    } = this.props;

    const { isInputEmpty } = this.state;

    const mandatoryInputCondition =
      mandatory &&
      (isInputEmpty ||
        (validStatus && validStatus.initialValue && !validStatus.valid));

    const errorInputCondition =
      validStatus && !validStatus.valid && !validStatus.initialValue;
    const classRank = rank || 'primary';

    if (scanning) {
      return (
        <div className="overlay-field js-not-unselect">{scannerElement}</div>
      );
    }

    return (
      <div
        ref={this.setWrapperElement}
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
        {properties && properties.map(this.renderSingleLookupPart)}

        {!readonly && this.renderInputControls()}
      </div>
    );
  }
}

Lookup.propTypes = {
  forceFullWidth: PropTypes.bool,
  forceHeight: PropTypes.number,
  widgetData: PropTypes.array,
  defaultValue: PropTypes.any,
  selected: PropTypes.any, // could be string,number,{key,caption} etc
  mandatory: PropTypes.bool,
  properties: PropTypes.array, // array of field descriptors
  initialFocus: PropTypes.bool,
  validStatus: PropTypes.object,
  newRecordCaption: PropTypes.string,
  windowType: PropTypes.string,
  parameterName: PropTypes.string,
  entity: PropTypes.string,
  dataId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  tabId: PropTypes.string,
  subentity: PropTypes.string,
  subentityId: PropTypes.string,
  viewId: PropTypes.string,
  rank: PropTypes.string, // e.g. "primary"
  readonly: PropTypes.bool,
  placeholder: PropTypes.string,
  align: PropTypes.any,
  isModal: PropTypes.bool,
  updated: PropTypes.bool,
  filterWidget: PropTypes.bool,
  rowId: PropTypes.string,
  tabIndex: PropTypes.number,
  autoFocus: PropTypes.bool,
  newRecordWindowId: PropTypes.string,
  advSearchCaption: PropTypes.string,
  advSearchWindowId: PropTypes.string,
  forwardedRef: PropTypes.object,
  isFilterActive: PropTypes.bool,

  //
  // Callbacks and other functions:
  onFocus: PropTypes.func,
  onBlur: PropTypes.func,
  onBlurWidget: PropTypes.func,
  onChange: PropTypes.func,
  updateItems: PropTypes.func,
  typeaheadSupplier: PropTypes.func,

  //
  // onClickOutside HOC:
  onClickOutside: PropTypes.func,

  //
  // BarcodeScanner HOC:
  barcodeScannerType: PropTypes.string,
  scanning: PropTypes.any,
  codeSelected: PropTypes.any,
  scannerElement: PropTypes.any,
  onSelectBarcode: PropTypes.func.isRequired,
  onScanBarcode: PropTypes.func.isRequired,
};

export default withForwardedRef(BarcodeScanner(onClickOutside(Lookup)));
