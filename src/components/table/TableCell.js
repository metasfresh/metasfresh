import Moment from "moment";
import numeral from "numeral";
import React, { Component } from "react";
import onClickOutside from "react-onclickoutside";

import MasterWidget from "../widget/MasterWidget";

class TableCell extends Component {
  constructor(props) {
    super(props);

    this.state = {
      backdropLock: false
    };
  }

  componentWillReceiveProps(nextProps) {
    const { widgetData, updateRow, readonly, rowId } = this.props;
    // We should avoid highlighting when whole row is exchanged (sorting)
    if (rowId !== nextProps.rowId) {
      return;
    }

    if (
      !readonly &&
      JSON.stringify(widgetData[0].value) !==
        JSON.stringify(nextProps.widgetData[0].value)
    ) {
      updateRow();
    }
  }

  handleBackdropLock = state => {
    this.setState(
      Object.assign({}, this.state, {
        backdropLock: !!state
      })
    );
  };

  handleClickOutside = e => {
    const { onClickOutside } = this.props;
    const { backdropLock } = this.state;

    //We can handle click outside only if
    //nested elements has no click oustide listening pending
    if (!backdropLock) {
      //it is important to change focus before collapsing to
      //blur Widget field and patch data
      this.cell && this.cell.focus();

      onClickOutside(e);
    }
  };

  static AMOUNT_FIELD_TYPES = ["Amount", "CostPrice"];
  static AMOUNT_FIELD_FORMATS_BY_PRECISION = [
    "0,0.[00000]",
    "0,0.0[0000]",
    "0,0.00[000]",
    "0,0.000[00]",
    "0,0.0000[0]",
    "0,0.00000"
  ];

  static getAmountFormatByPrecision = precision =>
    precision &&
    precision >= 0 &&
    precision < TableCell.AMOUNT_FIELD_FORMATS_BY_PRECISION.length
      ? TableCell.AMOUNT_FIELD_FORMATS_BY_PRECISION[precision]
      : null;

  static DATE_FIELD_TYPES = ["Date", "DateTime", "Time"];
  static DATE_FIELD_FORMATS = {
    Date: "DD.MM.YYYY",
    DateTime: "DD.MM.YYYY HH:mm:ss",
    Time: "HH:mm:ss"
  };
  static TIME_FIELD_TYPES = ["Time"];

  static getDateFormat = fieldType =>
    TableCell.DATE_FIELD_FORMATS[fieldType] ||
    TableCell.DATE_FIELD_FORMATS.Date;

  static createDate = (fieldValue, fieldType) =>
    fieldValue
      ? Moment(new Date(fieldValue)).format(TableCell.getDateFormat(fieldType))
      : "";

  static createAmount = (fieldValue, precision) => {
    if (fieldValue) {
      const fieldValueAsNum = numeral(parseFloat(fieldValue));
      const numberFormat = TableCell.getAmountFormatByPrecision(precision);
      return numberFormat
        ? fieldValueAsNum.format(numberFormat)
        : fieldValueAsNum.format();
    } else {
      return "";
    }
  };

  static fieldValueToString = (
    fieldValue,
    fieldType = "Text",
    precision = null
  ) => {
    if (fieldValue === null) {
      return "";
    }

    switch (typeof fieldValue) {
      case "object": {
        if (Array.isArray(fieldValue)) {
          return fieldValue
            .map(value => TableCell.fieldValueToString(value, fieldType))
            .join(" - ");
        }

        return TableCell.DATE_FIELD_TYPES.includes(fieldType)
          ? TableCell.createDate(fieldValue, fieldType)
          : fieldValue.caption;
      }

      case "boolean": {
        return fieldValue ? (
          <i className="meta-icon-checkbox-1" />
        ) : (
          <i className="meta-icon-checkbox" />
        );
      }

      case "string": {
        if (TableCell.DATE_FIELD_TYPES.includes(fieldType)) {
          return TableCell.createDate(fieldValue, fieldType);
        } else if (TableCell.AMOUNT_FIELD_TYPES.includes(fieldType)) {
          return TableCell.createAmount(fieldValue, precision);
        }
        return fieldValue;
      }

      default: {
        return fieldValue;
      }
    }
  };

  render() {
    const {
      isEdited,
      widgetData,
      item,
      docId,
      type,
      rowId,
      tabId,
      onDoubleClick,
      onKeyDown,
      readonly,
      updatedRow,
      tabIndex,
      entity,
      listenOnKeys,
      listenOnKeysFalse,
      listenOnKeysTrue,
      closeTableField,
      getSizeClass,
      handleRightClick,
      mainTable,
      onCellChange,
      viewId
    } = this.props;

    const tdValue = !isEdited
      ? TableCell.fieldValueToString(
          widgetData[0].value,
          item.widgetType,
          widgetData[0].precision
        )
      : null;

    return (
      <td
        tabIndex={tabIndex}
        ref={c => (this.cell = c)}
        onDoubleClick={readonly ? null : onDoubleClick}
        onKeyDown={onKeyDown}
        onContextMenu={handleRightClick}
        className={
          (item.gridAlign ? "text-xs-" + item.gridAlign + " " : "") +
          (widgetData[0].readonly ? "cell-disabled " : "") +
          (widgetData[0].mandatory ? "cell-mandatory " : "") +
          (getSizeClass(item) + " ") +
          item.widgetType +
          (updatedRow ? " pulse-on" : " pulse-off")
        }
      >
        {isEdited ? (
          <MasterWidget
            {...item}
            entity={mainTable ? "window" : entity}
            dataId={mainTable ? null : docId}
            widgetData={widgetData}
            windowType={type}
            isMainTable={mainTable}
            rowId={rowId}
            viewId={viewId}
            tabId={mainTable ? null : tabId}
            noLabel={true}
            gridAlign={item.gridAlign}
            handleBackdropLock={this.handleBackdropLock}
            listenOnKeys={listenOnKeys}
            listenOnKeysTrue={listenOnKeysTrue}
            listenOnKeysFalse={listenOnKeysFalse}
            onChange={mainTable ? onCellChange : null}
            closeTableField={closeTableField}
            ref={c => {
              this.widget = c && c.getWrappedInstance();
            }}
          />
        ) : (
          <div
            className="cell-text-wrapper"
            title={
              item.widgetType === "YesNo" || item.widgetType === "Switch"
                ? ""
                : tdValue
            }
          >
            {tdValue}
          </div>
        )}
      </td>
    );
  }
}

export default onClickOutside(TableCell);
export { TableCell };
