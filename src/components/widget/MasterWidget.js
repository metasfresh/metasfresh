import PropTypes from "prop-types";
import React, { Component } from "react";
import { connect } from "react-redux";
import Moment from "moment";

import {
  getZoomIntoWindow,
  openModal,
  patch,
  updatePropertyValue
} from "../../actions/WindowActions";
import RawWidget from "./RawWidget";
import { DATE_FIELDS, DATE_FORMAT } from "../../constants/Constants";

class MasterWidget extends Component {
  state = {
    updated: false,
    edited: false,
    data: ""
  };

  componentDidMount() {
    const { widgetData } = this.props;
    this.setState({
      data: widgetData[0].value
    });
  }

  componentWillReceiveProps(nextProps) {
    const { widgetData } = this.props;
    const { edited } = this.state;

    if (
      JSON.stringify(widgetData[0].value) !==
      JSON.stringify(nextProps.widgetData[0].value)
    ) {
      this.setState({
        data: nextProps.widgetData[0].value
      });

      if (!edited) {
        this.setState(
          {
            updated: true
          },
          () => {
            this.timeout = setTimeout(() => {
              this.setState({
                updated: false
              });
            }, 1000);
          }
        );
      } else {
        this.setState({
          edited: false
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
      dispatch,
      rowId,
      tabId,
      onChange,
      relativeDocId,
      isAdvanced = false,
      viewId
    } = this.props;

    let { entity } = this.props;

    let currRowId = rowId;
    let ret = null;
    let isEdit = false;

    let parseValue = this.parseDateBeforePatch(widgetType, value);

    if (rowId === "NEW") {
      currRowId = relativeDocId;
    }

    if (widgetType !== "Button") {
      dispatch(updatePropertyValue(property, value, tabId, currRowId, isModal));
    }

    if (viewId) {
      entity = "documentView";
      isEdit = true;
    }

    ret = dispatch(
      patch(
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
      )
    );

    //callback
    if (onChange) {
      onChange(rowId, property, value);
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
      dispatch,
      tabId,
      rowId,
      isModal,
      relativeDocId,
      widgetType
    } = this.props;

    let currRowId = rowId;

    const dateParse = ["Date", "DateTime", "Time"];

    this.setState(
      {
        edited: true,
        data: val
      },
      () => {
        if (
          dateParse.indexOf(widgetType) === -1 &&
          !this.validatePrecision(val)
        ) {
          return;
        }
        if (rowId === "NEW") {
          currRowId = relativeDocId;
        }
        dispatch(updatePropertyValue(property, val, tabId, currRowId, isModal));
      }
    );
  };

  setEditedFlag = edited => {
    this.setState({
      edited: edited
    });
  };

  validatePrecision = value => {
    const { widgetType, precision } = this.props;
    let precisionProcessed = precision;

    if (widgetType === "Integer" || widgetType === "Quantity") {
      precisionProcessed = 0;
    }

    if (precisionProcessed < (value.split(".")[1] || []).length) {
      return false;
    } else {
      return true;
    }
  };

  handleProcess = (caption, buttonProcessId, tabId, rowId) => {
    const { dispatch } = this.props;

    dispatch(
      openModal(caption, buttonProcessId, "process", tabId, rowId, false, false)
    );
  };

  handleZoomInto = field => {
    const { dataId, windowType, tabId, rowId } = this.props;
    getZoomIntoWindow("window", windowType, dataId, tabId, rowId, field).then(
      res => {
        res &&
          res.data &&
          window.open(
            "/window/" +
              res.data.documentPath.windowId +
              "/" +
              res.data.documentPath.documentId,
            "_blank"
          );
      }
    );
  };

  render() {
    const {
      caption,
      widgetType,
      fields,
      windowType,
      type,
      noLabel,
      widgetData,
      dataId,
      rowId,
      tabId,
      icon,
      gridAlign,
      isModal,
      entity,
      handleBackdropLock,
      tabIndex,
      dropdownOpenCallback,
      autoFocus,
      fullScreen,
      disabled,
      buttonProcessId,
      listenOnKeys,
      listenOnKeysFalse,
      listenOnKeysTrue,
      closeTableField,
      allowShowPassword,
      onBlurWidget,
      isOpenDatePicker
    } = this.props;

    const { updated, data } = this.state;
    const handleFocusFn = handleBackdropLock ? handleBackdropLock : () => {};

    return (
      <RawWidget
        {...{
          allowShowPassword,
          entity,
          widgetType,
          fields,
          windowType,
          dataId,
          widgetData,
          rowId,
          tabId,
          icon,
          gridAlign,
          updated,
          isModal,
          noLabel,
          type,
          caption,
          handleBackdropLock,
          tabIndex,
          dropdownOpenCallback,
          autoFocus,
          fullScreen,
          disabled,
          buttonProcessId,
          listenOnKeys,
          listenOnKeysFalse,
          listenOnKeysTrue,
          closeTableField,
          data,
          onBlurWidget,
          isOpenDatePicker
        }}
        handleFocus={() => handleFocusFn(true)}
        handleBlur={() => handleFocusFn(false)}
        handlePatch={this.handlePatch}
        handleChange={this.handleChange}
        handleProcess={this.handleProcess}
        setEditedFlag={this.setEditedFlag}
        handleZoomInto={this.handleZoomInto}
      />
    );
  }
}

MasterWidget.propTypes = {
  dispatch: PropTypes.func.isRequired,
  isOpenDatePicker: PropTypes.bool
};

export default connect(false, false, false, { withRef: true })(MasterWidget);
