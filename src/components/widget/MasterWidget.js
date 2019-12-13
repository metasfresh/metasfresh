import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import Moment from 'moment-timezone';
import { noop } from 'lodash';

import * as windowActions from '../../actions/WindowActions';
import { convertTimeStringToMoment } from '../../utils/documentListHelper';
import RawWidget from './RawWidget';

function isNumberField(widgetType) {
  switch (widgetType) {
    case 'Integer':
    case 'Amount':
    case 'Quantity':
      return true;
    default:
      return false;
  }
}

const dateParse = ['Date', 'DateTime', 'ZonedDateTime', 'Timestamp', 'Time'];

/**
 * @file Class based component.
 * @module MasterWidget
 * @extends Component
 */
class MasterWidget extends PureComponent {
  state = {
    updated: false,
    edited: false,
    data: '',
  };

  componentDidMount() {
    const { data, clearValue } = this.props;
    const localWidgetData = this.getWidgetData();

    // `clearValue` removes current field value for the widget. This is used when
    // user focuses on table cell and starts typing
    this.setState({
      data: data || (clearValue ? '' : localWidgetData[0].value),
    });
  }

  UNSAFE_componentWillReceiveProps(nextProps) {
    const { widgetType } = this.props;
    const localWidgetData = this.getWidgetData();
    const { edited, data } = this.state;

    const {
      widgetData: nextWidgetData,
      getWidgetData: nextGetWidgetData,
      item: nextItem,
      isEditable: nextIsEditable,
      supportFieldEdit: nextSupportFieldEdit,
    } = nextProps;

    const nextLocalWidgetData = nextWidgetData
      ? nextWidgetData
      : this.getWidgetData(
          nextItem,
          nextIsEditable,
          nextSupportFieldEdit,
          nextGetWidgetData
        );

    let next = nextLocalWidgetData[0].value;

    if (
      !edited &&
      JSON.stringify(next) !== data &&
      JSON.stringify(localWidgetData[0].value) !== JSON.stringify(next)
    ) {
      if (next && dateParse.includes(widgetType) && !Moment.isMoment(next)) {
        next = convertTimeStringToMoment(next);
        next = Moment(next);
      }
      this.setState(
        {
          updated: true,
          data: next,
        },
        () => {
          this.timeout = setTimeout(() => {
            this.setState({
              updated: false,
            });
          }, 1000);
        }
      );
    } else if (edited) {
      this.setState({
        edited: false,
      });
    }
  }

  componentWillUnmount() {
    clearTimeout(this.timeout);
  }

  getWidgetData = (
    nextItem,
    nextIsEditable,
    nextSupportFieldEdit,
    nextGetWidgetData
  ) => {
    const { widgetData, getWidgetData } = this.props;
    let { item, isEditable, supportFieldEdit } = this.props;
    const localItem = nextItem || item;
    const localIsEditable = nextIsEditable || isEditable;
    const localSupportEdit = nextSupportFieldEdit || supportFieldEdit;

    const getData = nextGetWidgetData ? nextGetWidgetData : getWidgetData;
    const localWidgetData = widgetData
      ? widgetData
      : getData(localItem, localIsEditable, localSupportEdit);

    return localWidgetData;
  };

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
      updatePropertyValue,
      patch,
      rowId,
      tabId,
      onChange,
      relativeDocId,
      isAdvanced = false,
      viewId,
    } = this.props;
    const numberField = isNumberField(widgetType);

    if (numberField && !value) {
      value = '0';
    }

    let { entity } = this.props;
    let currRowId = rowId;
    let ret = null;
    let isEdit = false;

    if (rowId === 'NEW') {
      currRowId = relativeDocId;
    }

    if (widgetType !== 'Button') {
      updatePropertyValue(property, value, tabId, currRowId, isModal, entity);
    }

    if (viewId) {
      entity = 'documentView';
      isEdit = true;
    }

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

    //callback
    if (onChange) {
      onChange(rowId, property, value, ret);
    }

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
  handleChange = (property, val) => {
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
  validatePrecision = value => {
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
  handleZoomInto = field => {
    const { dataId, windowType, tabId, rowId } = this.props;

    windowActions
      .getZoomIntoWindow('window', windowType, dataId, tabId, rowId, field)
      .then(res => {
        const url = `/window/${res.data.documentPath.windowId}/${
          res.data.documentPath.documentId
        }`;

        res &&
          res.data &&
          /*eslint-disable */
          window.open(url, '_blank');
          /*eslint-enable */
      });
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
    const handleFocusFn = handleBackdropLock ? handleBackdropLock : noop;
    const localWidgetData = this.getWidgetData();

    return (
      <RawWidget
        {...this.props}
        widgetData={localWidgetData}
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
  getWidgetData: PropTypes.func,
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
