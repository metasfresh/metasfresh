import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import _ from 'lodash';

import { getZoomIntoWindow } from '../../api';
import { formatDateWithZeros } from '../../utils/documentListHelper';
import {
  validatePrecision,
  formatValueByWidgetType,
} from '../../utils/widgetHelpers';
import { DATE_FIELD_TYPES, TIME_FIELD_TYPES } from '../../constants/Constants';
import { getTableId } from '../../reducers/tables';

import RawWidget from './RawWidget';

const dateParse = [...DATE_FIELD_TYPES, ...TIME_FIELD_TYPES];

/**
 * @file Class based component.
 * @module MasterWidget
 * @extends Component
 */
class MasterWidget extends PureComponent {
  constructor(props) {
    super(props);
    const { value, widgetData, clearValue } = this.props;
    // `clearValue` removes current field value for the widget. This is used when user
    // focuses on table cell and starts typing without pressing {enter} first. `value` is
    // only used by the `BarcodeScanner` to force formatting of the visible value, so there's
    // no collision possible here
    this.state = {
      updated: false,
      edited: false,
      value: value || (clearValue ? '' : widgetData[0].value),
      widgetData: props.widgetData, // this is used for comparison in the getDerivedStateFromProps lifecycle
    };
  }

  componentDidMount = () => (this.mounted = true);

  componentWillUnmount = () => (this.mounted = false);

  /**
   * @method getDerivedStateFromProps
   * @summary is invoked right before calling the render method, both on the initial mount and on subsequent updates
   *          updates the data and the widgetData from the MasterWidget state, also the updated flag
   *          Used this in order to ditch the deprecated UNSAFE_componentWillReceiveProps
   */
  static getDerivedStateFromProps(nextProps, prevState) {
    const { edited, widgetData } = prevState;
    let next = nextProps.widgetData[0].value;
    let hasNewData = widgetData[0] && !_.isEqual(widgetData[0].value, next);

    if (!edited && hasNewData) {
      return { updated: true, value: next, widgetData: nextProps.widgetData };
    }
    return null;
  }

  /**
   *  Unset the updated flag in the state of the component such that we won't have the widget highlighted after update
   */
  componentDidUpdate() {
    const { updated } = this.state;
    if (updated) {
      this.timeout = setTimeout(() => {
        this.mounted && this.setState({ updated: false });
      }, 1000);
    }
  }

  /**
   * @method handlePatch
   * @summary Performs the actual patch request and for `Product attributes` also
   * updates the field value stored in the redux store.
   * @param {*} property
   * @param {*} value
   */
  handlePatch = (property, value) => {
    const {
      isModal,
      widgetType,
      dataId,
      windowId,
      patch,
      rowId,
      dataId: docId,
      tabId,
      relativeDocId,
      isAdvanced = false,
      viewId,
      updatePropertyValue,
      disconnected,
      updateRow,
    } = this.props;

    value = formatValueByWidgetType({ widgetType, value });

    let entity = viewId ? 'documentView' : this.props.entity;
    let currRowId = rowId === 'NEW' ? relativeDocId : rowId;
    let ret = null;
    let isEdit = viewId ? true : false;
    const tableId = getTableId({
      windowId,
      viewId,
    });

    const updateOptions = {
      windowId,
      docId,
      property,
      value,
      tabId,
      rowId: currRowId,
      isModal,
      entity,
      tableId,
      disconnected,
      action: 'patch',
    };

    // TODO: Leaving this for now in case this is used in some edge cases
    // but seems like a duplication of what we have in `handleChange`.
    // *HOTFIX update*: This is used by attributes. I think we should try to rewrite the
    // Attributes component so that it won't need it anymore.
    // https://github.com/metasfresh/me03/issues/5384
    widgetType !== 'Button' &&
      !dataId &&
      (widgetType === 'ProductAttributes' || widgetType === 'Quantity') &&
      updatePropertyValue(updateOptions);

    ret = patch(
      entity,
      windowId,
      dataId,
      tabId,
      currRowId,
      property,
      value,
      isModal,
      isAdvanced,
      viewId,
      isEdit
    );

    // flash the row to indicate a change
    updateRow && updateRow();
    this.setState({ edited: false });

    /** we are using this `disconnected` flag to know when the Master widget should update the property value differently */
    disconnected === 'inlineTab' &&
      updatePropertyValue({ ...updateOptions, ret });

    return ret;
  };

  /**
   * @method handleChange
   * @summary This method may look like a redundant for this one above, but is needed to handle controlled
   *          components if they patch on other event than onchange
   * @param {*} property
   * @param {*} val
   */
  handleChange = async (property, val) => {
    const {
      updatePropertyValue,
      tabId,
      rowId,
      isModal,
      relativeDocId,
      widgetType,
      precision,
      entity,
      viewId,
      dataId,
      windowId,
      widgetData,
      disconnected,
    } = this.props;

    // Add special case of formating for the case when people input 04.7.2020 to be transformed to 04.07.2020
    val = widgetType === 'Date' ? await formatDateWithZeros(val) : val;
    let fieldName = widgetData[0] ? widgetData[0].field : '';
    this.setState({ edited: true, value: val }, () => {
      if (
        !dateParse.includes(widgetType) &&
        !validatePrecision({
          widgetValue: val,
          widgetType,
          precision,
          fieldName,
        })
      ) {
        return;
      }
      const currRowId = rowId === 'NEW' ? relativeDocId : rowId;
      const tableId = getTableId({
        windowId,
        docId: dataId,
        tabId,
        viewId,
      });

      updatePropertyValue({
        windowId,
        docId: dataId,
        property,
        value: val,
        tabId,
        rowId: currRowId,
        isModal,
        entity,
        tableId,
        disconnected,
        action: 'change',
      });
    });
  };

  /**
   * @method handleProcess
   * @summary handle process function, opens the corresponding modal
   * @param {*} caption
   * @param {*} buttonProcessId
   * @param {*} tabId
   * @param {*} rowId
   */
  handleProcess = (caption, buttonProcessId, tabId, rowId) => {
    const { openModal } = this.props;

    openModal({
      title: caption,
      windowId: buttonProcessId,
      modalType: 'process',
      tabId,
      rowId,
    });
  };

  /**
   * @method handleZoomInto
   * @summary Opens a new window in a new tab for a given field
   * @param {*} field
   */
  handleZoomInto = (field) => {
    const { dataId, windowId, tabId, rowId, entity } = this.props;
    const fallBackEntity = entity ? entity : 'window';

    getZoomIntoWindow(
      fallBackEntity,
      windowId,
      dataId,
      tabId,
      rowId,
      field
    ).then((res) => {
      const url = `/${fallBackEntity}/${res.data.documentPath.windowId}/${
        res.data.documentPath.documentId
      }`;

      res && res.data && window.open(url, '_blank');
    });
  };

  /**
   * @method handleBlurWidget
   * @summary This is just a forwarder for onBlurWidget from the props
   */
  handleBlurWidget = () => {
    const { onBlurWidget, fieldName } = this.props;

    onBlurWidget && onBlurWidget(fieldName);
  };

  handleFocus = () => this.handleFocusFn(true);

  handleBlur = () => this.handleFocusFn(false);

  handleFocusFn = (val) => {
    const { handleBackdropLock } = this.props;

    handleBackdropLock && handleBackdropLock(val);
  };

  render() {
    const { windowId } = this.props;
    const { updated, value } = this.state;

    return (
      <RawWidget
        {...this.props}
        windowType={windowId}
        updated={updated}
        data={value}
        handleFocus={this.handleFocus}
        handleBlur={this.handleBlur}
        handlePatch={this.handlePatch}
        handleChange={this.handleChange}
        handleProcess={this.handleProcess}
        handleZoomInto={this.handleZoomInto}
        onBlurWidget={this.handleBlurWidget}
      />
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {bool} [dataEntry]
 * @prop {bool} [isOpenDataPicker]
 * @prop {func} openModal
 */
MasterWidget.propTypes = {
  dataId: PropTypes.string,
  windowId: PropTypes.string,
  viewId: PropTypes.string,
  rowId: PropTypes.string,
  tabId: PropTypes.string,
  isModal: PropTypes.bool,
  dataEntry: PropTypes.bool,
  fieldName: PropTypes.string,
  isOpenDatePicker: PropTypes.bool,
  onClickOutside: PropTypes.func,
  onBlurWidget: PropTypes.func,
  handleBackdropLock: PropTypes.func,
  updatePropertyValue: PropTypes.func,
  openModal: PropTypes.func.isRequired,
  value: PropTypes.object,
  widgetData: PropTypes.array,
  widgetType: PropTypes.string,
  patch: PropTypes.func,
  relativeDocId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  isAdvanced: PropTypes.bool,
  entity: PropTypes.string,
  precision: PropTypes.bool,
  clearValue: PropTypes.bool,
  disconnected: PropTypes.string,
  updateRow: PropTypes.func,
};

export default MasterWidget;
