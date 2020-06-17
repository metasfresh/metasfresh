import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import Moment from 'moment-timezone';
import * as windowActions from '../../actions/WindowActions';
import { getZoomIntoWindow } from '../../api';
import { convertTimeStringToMoment } from '../../utils/documentListHelper';
import { formatDateWithZeros } from '../../utils/documentListHelper';
import RawWidget from './RawWidget';
import { isNumberField } from '../../utils/widgetHelper';
import { DATE_FIELD_TYPES, TIME_FIELD_TYPES } from '../../constants/Constants';
import _ from 'lodash';

const dateParse = [...DATE_FIELD_TYPES, ...TIME_FIELD_TYPES];

/**
 * @file Class based component.
 * @module MasterWidget
 * @extends Component
 */
class MasterWidget extends Component {
  constructor(props) {
    super(props);
    const { data, widgetData, clearValue } = this.props;
    // `clearValue` removes current field value for the widget. This is used when user focuses on table cell and starts typing
    this.state = {
      updated: false,
      edited: false,
      data: data || (clearValue ? '' : widgetData[0].value),
      widgetData: props.widgetData, // this is used for comparison in the getDerivedStateFromProps lifecycle
    };
  }

  /**
   * @method getDerivedStateFromProps
   * @summary is invoked right before calling the render method, both on the initial mount and on subsequent updates
   *          updates the data and the widgetData from the MasterWidget state, also the updated flag
   *          Used this in order to ditch the deprecated UNSAFE_componentWillReceiveProps
   */
  static getDerivedStateFromProps(nextProps, prevState) {
    const { widgetType } = nextProps;
    const { edited, widgetData } = prevState;
    let next = nextProps.widgetData[0].value;
    let bringsModifs = widgetData[0] && !_.isEqual(widgetData[0].value, next);

    let isUnconvertedDate =
      next && dateParse.includes(widgetType) && !Moment.isMoment(next);

    if (!edited && bringsModifs) {
      next = isUnconvertedDate ? Moment(convertTimeStringToMoment(next)) : next;

      return { updated: true, data: next, widgetData: nextProps.widgetData };
    } else if (edited) {
      return { edited: false };
    }
    return null;
  }

  /**
   *  Unset the updated flag in the state of the component such that we won't have the widget highlighted after update
   */
  componentDidUpdate() {
    const { updated } = this.state;
    if (updated) {
      this.timeout = setTimeout(() => this.setState({ updated: false }), 1000);
    }
  }

  /**
   * Clear the timeout used on previous lifecycle method
   */
  componentWillUnmount() {
    clearTimeout(this.timeout);
  }

  /**
   * @method handlePatch
   * @summary Performs patching at MasterWidget level, shaping in the same time the `value` for various cases
   * @param {string} widgetType
   * @param {string|undefined} value
   */
  formatValueByWidgetType = ({ widgetType, value }) => {
    const numberField = isNumberField(widgetType);
    if (widgetType === 'Quantity' && value === '') {
      return null;
    } else if (numberField && !value) {
      return '0';
    }
  }

  /**
   * @method handlePatch
   * @summary Performs patching at MasterWidget level, shaping in the same time the `value` for various cases
   * @param {*} property
   * @param {*} value
   */
  handlePatch = (property, value) => {
    const {
      isModal,
      widgetType,
      dataId,
      windowType,
      updatePropertyValue,
      patch,
      rowId,
      tabId,
      onChange,
      relativeDocId,
      isAdvanced = false,
      viewId,
    } = this.props;

    let entity = viewId ? 'documentView' : this.props.entity;
    value = this.formatValueByWidgetType({ widgetType, value });
    let currRowId = rowId === 'NEW' ? relativeDocId : rowId;
    let ret = null;
    let isEdit = viewId ? true : false;

    widgetType !== 'Button' &&
      updatePropertyValue(property, value, tabId, currRowId, isModal, entity);

    ret = patch(
      entity,
      windowType,
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

    onChange && onChange(rowId, property, value, ret); //callback

    return ret;
  };

  //
  // This method may look like a redundant for this one above,
  // but is need to handle controlled components if
  // they patch on other event than onchange
  //
  /**
   * @method handleKeyDown
   * @summary ToDo: Describe the method.
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
      entity,
    } = this.props;
    let currRowId = rowId;
    // Add special case of formating for the case when people input 04.7.2020 to be transformed to 04.07.2020
    val = widgetType === 'Date' ? await formatDateWithZeros(val) : val;
    this.setState(
      {
        edited: true,
        data: val,
      },
      () => {
        if (!dateParse.includes(widgetType) && !this.validatePrecision(val)) {
          return;
        }
        if (rowId === 'NEW') {
          currRowId = relativeDocId;
        }
        updatePropertyValue(property, val, tabId, currRowId, isModal, entity);
      }
    );
  };

  /**
   * @method validatePrecision
   * @summary ToDo: Describe the method.
   * @param {*} value
   */
  validatePrecision = (value) => {
    const { widgetType, precision } = this.props;
    let precisionProcessed = precision;

    if (widgetType === 'Integer' || widgetType === 'Quantity') {
      precisionProcessed = 0;
    }

    if (precisionProcessed < (value.split('.')[1] || []).length) {
      return false;
    } else {
      return true;
    }
  };

  /**
   * @method handleProcess
   * @summary ToDo: Describe the method.
   * @param {*} caption
   * @param {*} buttonProcessId
   * @param {*} tabId
   * @param {*} rowId
   */
  handleProcess = (caption, buttonProcessId, tabId, rowId) => {
    const { openModal } = this.props;

    openModal(caption, buttonProcessId, 'process', tabId, rowId, false, false);
  };

  /**
   * @method handleKeyDown
   * @summary ToDo: Describe the method.
   * @param {*} field
   */
  handleZoomInto = (field) => {
    const { dataId, windowType, tabId, rowId } = this.props;

    getZoomIntoWindow('window', windowType, dataId, tabId, rowId, field).then(
      (res) => {
        const url = `/window/${res.data.documentPath.windowId}/${
          res.data.documentPath.documentId
        }`;

        res &&
          res.data &&
          /*eslint-disable */
          window.open(url, '_blank');
          /*eslint-enable */
      }
    );
  };

  /**
   * @method handleBlurWidget
   * @summary ToDo: Describe the method.
   */
  handleBlurWidget = () => {
    const { onBlurWidget, fieldName } = this.props;

    onBlurWidget && onBlurWidget(fieldName);
  };

  /**
   * @method render
   * @summary ToDo: Describe the method.
   */
  render() {
    const { handleBackdropLock, onClickOutside } = this.props;
    const { updated, data } = this.state;
    const handleFocusFn = handleBackdropLock ? handleBackdropLock : () => {};

    return (
      <RawWidget
        {...this.props}
        updated={updated}
        data={data}
        handleFocus={() => handleFocusFn(true)}
        handleBlur={() => handleFocusFn(false)}
        onClickOutside={onClickOutside}
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
  isModal: PropTypes.bool,
  dataEntry: PropTypes.bool,
  fieldName: PropTypes.string,
  isOpenDatePicker: PropTypes.bool,
  onClickOutside: PropTypes.func,
  onBlurWidget: PropTypes.func,
  handleBackdropLock: PropTypes.func,
  updatePropertyValue: PropTypes.func,
  openModal: PropTypes.func.isRequired,
  data: PropTypes.object,
  widgetData: PropTypes.array,
  widgetType: PropTypes.string,
  dataId: PropTypes.string,
  windowType: PropTypes.string,
  patch: PropTypes.func,
  rowId: PropTypes.string,
  tabId: PropTypes.string,
  onChange: PropTypes.func,
  relativeDocId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  isAdvanced: PropTypes.bool,
  viewId: PropTypes.string,
  entity: PropTypes.string,
  precision: PropTypes.bool,
  clearValue: PropTypes.bool,
};

export default connect(
  null,
  {
    openModal: windowActions.openModal,
    patch: windowActions.patch,
    updatePropertyValue: windowActions.updatePropertyValue,
  },
  null,
  { forwardRef: true }
)(MasterWidget);
