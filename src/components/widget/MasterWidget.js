import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import Moment from 'moment';

import * as windowActions from '../../actions/WindowActions';
import RawWidget from './RawWidget';
import { DATE_FIELDS, DATE_FORMAT } from '../../constants/Constants';

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

class MasterWidget extends Component {
  state = {
    updated: false,
    edited: false,
    data: '',
  };

  componentDidMount() {
    const { data, widgetData } = this.props;

    this.setState({
      data: data || widgetData[0].value,
    });
  }

  UNSAFE_componentWillReceiveProps(nextProps) {
    const { widgetData } = this.props;
    const { edited, data } = this.state;

    if (
      !edited &&
      JSON.stringify(nextProps.widgetData[0].value) !== data &&
      JSON.stringify(widgetData[0].value) !==
        JSON.stringify(nextProps.widgetData[0].value)
    ) {
      this.setState({
        data: nextProps.widgetData[0].value,
      });

      if (!edited) {
        this.setState(
          {
            updated: true,
          },
          () => {
            this.timeout = setTimeout(() => {
              this.setState({
                updated: false,
              });
            }, 1000);
          }
        );
      } else {
        this.setState({
          edited: false,
        });
      }
    }
  }

  componentWillUnmount() {
    clearTimeout(this.timeout);
  }

  // i.e 2018-01-27T17:00:00.000-06:00
  parseDateBeforePatch = (widgetType, value) => {
    if (DATE_FIELDS.indexOf(widgetType) > -1) {
      if (value) {
        return Moment(value).format(DATE_FORMAT);
      }
    }
    return value;
  };

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
    let parseValue = this.parseDateBeforePatch(widgetType, value);

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
      parseValue,
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
  // This method may looks like a redundant for this one above,
  // but is need to handle controlled components if
  // they patch on other event than onchange
  //
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
    const dateParse = ['Date', 'DateTime', 'Time'];
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

  setEditedFlag = edited => {
    this.setState({
      edited: edited,
    });
  };

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

  handleProcess = (caption, buttonProcessId, tabId, rowId) => {
    const { openModal } = this.props;

    openModal(caption, buttonProcessId, 'process', tabId, rowId, false, false);
  };

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

  handleBlurWidget = () => {
    const { onBlurWidget, fieldName } = this.props;

    onBlurWidget && onBlurWidget(fieldName);
  };

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
        setEditedFlag={this.setEditedFlag}
        handleZoomInto={this.handleZoomInto}
        onBlurWidget={this.handleBlurWidget}
      />
    );
  }
}

MasterWidget.propTypes = {
  dataEntry: PropTypes.bool,
  isOpenDatePicker: PropTypes.bool,
  openModal: PropTypes.func.isRequired,
  data: PropTypes.object,
  widgetData: PropTypes.array,
  isModal: PropTypes.bool,
  widgetType: PropTypes.string,
  dataId: PropTypes.string,
  windowType: PropTypes.string,
  updatePropertyValue: PropTypes.func,
  patch: PropTypes.func,
  rowId: PropTypes.string,
  tabId: PropTypes.string,
  onChange: PropTypes.func,
  relativeDocId: PropTypes.number,
  isAdvanced: PropTypes.bool,
  viewId: PropTypes.bool,
  entity: PropTypes.string,
  precision: PropTypes.bool,
  onBlurWidget: PropTypes.func,
  fieldName: PropTypes.string,
  handleBackdropLock: PropTypes.func,
  onClickOutside: PropTypes.func,
};

export default connect(
  null,
  {
    openModal: windowActions.openModal,
    patch: windowActions.patch,
    updatePropertyValue: windowActions.updatePropertyValue,
  },
  null,
  { withRef: true }
)(MasterWidget);
