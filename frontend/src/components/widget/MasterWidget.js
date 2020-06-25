import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import Moment from 'moment-timezone';
import _ from 'lodash';

import { getZoomIntoWindow } from '../../api';
import {
  convertTimeStringToMoment,
  formatDateWithZeros,
} from '../../utils/documentListHelper';
import {
  validatePrecision,
  formatValueByWidgetType,
} from '../../utils/widgetHelper';
import { DATE_FIELD_TYPES, TIME_FIELD_TYPES } from '../../constants/Constants';
import { getTableId } from '../../reducers/tables';
import {
  openModal,
  patch,
  updatePropertyValue,
} from '../../actions/WindowActions';

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
    const { data, widgetData, clearValue } = this.props;
    // `clearValue` removes current field value for the widget. This is used when user
    // focuses on table cell and starts typing without pressing {enter} first
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
    let hasNewData = widgetData[0] && !_.isEqual(widgetData[0].value, next);

    let isUnconvertedDate =
      next && dateParse.includes(widgetType) && !Moment.isMoment(next);

    if (!edited && hasNewData) {
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
   * @method handlePatch
   * @summary ToDo: Describe the method.
   * @param {*} property
   * @param {*} value
   */
  handlePatch = (property, value) => {
    const {
      isModal,
      widgetType,
      dataId,
      windowType,
      patch,
      rowId,
      tabId,
      onChange,
      relativeDocId,
      isAdvanced = false,
      viewId,
    } = this.props;
    value = formatValueByWidgetType({ widgetType, value });
    let entity = viewId ? 'documentView' : this.props.entity;
    let currRowId = rowId === 'NEW' ? relativeDocId : rowId;
    let ret = null;
    let isEdit = viewId ? true : false;

    // TODO: Leaving this for now in case this is used in some edga cases
    // but seems like a duplication of what we have in `handleChange`.
    // widgetType !== 'Button' &&
    //   updatePropertyValue(property, value, tabId, currRowId, isModal, entity);

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
      windowType,
    } = this.props;
    // Add special case of formating for the case when people input 04.7.2020 to be transformed to 04.07.2020
    val = widgetType === 'Date' ? await formatDateWithZeros(val) : val;

    this.setState({ edited: true, data: val }, () => {
      if (
        !dateParse.includes(widgetType) &&
        !validatePrecision({ widgetValue: val, widgetType, precision })
      ) {
        return;
      }
      const currRowId = rowId === 'NEW' ? relativeDocId : rowId;
      const tableId = getTableId({
        windowId: windowType,
        docId: dataId,
        tabId,
        viewId,
      });

      updatePropertyValue({
        property,
        value: val,
        tabId,
        rowId: currRowId,
        isModal,
        entity,
        tableId,
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

    openModal(caption, buttonProcessId, 'process', tabId, rowId, false, false);
  };

  /**
   * @method handleZoomInto
   * @summary Opens a new window in a new tab for a given field
   * @param {*} field
   */
  handleZoomInto = (field) => {
    const { dataId, windowType, tabId, rowId } = this.props;

    getZoomIntoWindow('window', windowType, dataId, tabId, rowId, field).then(
      (res) => {
        const url = `/window/${res.data.documentPath.windowId}/${
          res.data.documentPath.documentId
        }`;

        res && res.data && window.open(url, '_blank');
      }
    );
  };

  /**
   * @method handleBlurWidget
   * @summary This is just a forwarder for onBlurWidget from the props
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
    openModal,
    patch,
    updatePropertyValue,
  },
  null,
  { forwardRef: true }
)(MasterWidget);
