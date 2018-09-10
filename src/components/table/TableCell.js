import Moment from 'moment';
import PropTypes from 'prop-types';
import numeral from 'numeral';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import classnames from 'classnames';

import MasterWidget from '../widget/MasterWidget';
import {
  AMOUNT_FIELD_TYPES,
  AMOUNT_FIELD_FORMATS_BY_PRECISION,
  SPECIAL_FIELD_TYPES,
  DATE_FIELD_TYPES,
  DATE_FIELD_FORMATS,
} from '../../constants/Constants';

class TableCell extends PureComponent {
  static getAmountFormatByPrecision = precision =>
    precision &&
    precision >= 0 &&
    precision < AMOUNT_FIELD_FORMATS_BY_PRECISION.length
      ? AMOUNT_FIELD_FORMATS_BY_PRECISION[precision]
      : null;

  static getDateFormat = fieldType =>
    DATE_FIELD_FORMATS[fieldType] || DATE_FIELD_FORMATS.Date;

  static createDate = (fieldValue, fieldType) =>
    fieldValue
      ? Moment(new Date(fieldValue)).format(TableCell.getDateFormat(fieldType))
      : '';

  static createAmount = (fieldValue, precision) => {
    if (fieldValue) {
      const fieldValueAsNum = numeral(parseFloat(fieldValue));
      const numberFormat = TableCell.getAmountFormatByPrecision(precision);
      return numberFormat
        ? fieldValueAsNum.format(numberFormat)
        : fieldValueAsNum.format();
    } else {
      return '';
    }
  };

  static createSpecialField = (fieldType, fieldValue) => {
    switch (fieldType) {
      case 'Color': {
        const style = {
          backgroundColor: fieldValue,
        };
        return <span className="widget-color-display" style={style} />;
      }
      default:
        return fieldValue;
    }
  };

  // TODO: THIS NEEDS URGENT REFACTORING, WHY THE HECK ARE WE RETURNING
  // SIX DIFFERENT TYPES OF VALUES HERE ? UBER-BAD DESIGN !
  static fieldValueToString = (
    fieldValue,
    fieldType = 'Text',
    precision = null
  ) => {
    if (fieldValue === null) {
      return '';
    }

    switch (typeof fieldValue) {
      case 'object': {
        if (Array.isArray(fieldValue)) {
          return fieldValue
            .map(value => TableCell.fieldValueToString(value, fieldType))
            .join(' - ');
        }

        return DATE_FIELD_TYPES.includes(fieldType)
          ? TableCell.createDate(fieldValue, fieldType)
          : fieldValue.caption;
      }
      case 'boolean': {
        return fieldValue ? (
          <i className="meta-icon-checkbox-1" />
        ) : (
          <i className="meta-icon-checkbox" />
        );
      }
      case 'string': {
        if (DATE_FIELD_TYPES.includes(fieldType)) {
          return TableCell.createDate(fieldValue, fieldType);
        } else if (AMOUNT_FIELD_TYPES.includes(fieldType)) {
          return TableCell.createAmount(fieldValue, precision);
        } else if (SPECIAL_FIELD_TYPES.includes(fieldType)) {
          return TableCell.createSpecialField(fieldType, fieldValue);
        }
        return fieldValue;
      }
      default: {
        return fieldValue;
      }
    }
  };

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
    const { item } = this.props;

    if (
      !['ProductAttributes', 'Attributes', 'List', 'Lookup'].includes(
        item.widgetType
      )
    ) {
      if (!state) {
        this.props.onClickOutside();
      }
    }
  };

  render() {
    const {
      isEdited,
      cellExtended,
      extendLongText,
      widgetData,
      item,
      type,
      rowId,
      tabId,
      handleDoubleClick,
      handleKeyDown,
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
      viewId,
      modalVisible,
      onClickOutside,
    } = this.props;
    const docId = `${this.props.docId}`;
    const tdValue = !isEdited
      ? TableCell.fieldValueToString(
          widgetData[0].value,
          item.widgetType,
          widgetData[0].precision
        )
      : null;
    const isOpenDatePicker = isEdited && item.widgetType === 'Date';
    const isDateField = DATE_FIELD_FORMATS[item.widgetType]
      ? TableCell.getDateFormat(item.widgetType)
      : false;
    let style = {};

    if (cellExtended) {
      style = {
        height: extendLongText * 20,
      };
    }

    return (
      <td
        tabIndex={modalVisible ? -1 : tabIndex}
        ref={c => (this.cell = c)}
        onDoubleClick={handleDoubleClick}
        onKeyDown={handleKeyDown}
        onContextMenu={handleRightClick}
        className={classnames(
          {
            [`text-${item.gridAlign}`]: item.gridAlign,
            'cell-disabled': widgetData[0].readonly,
            'cell-mandatory': widgetData[0].mandatory,
          },
          getSizeClass(item),
          item.widgetType,
          {
            'pulse-on': updatedRow,
            'pulse-off': !updatedRow,
          }
        )}
      >
        {isEdited ? (
          <MasterWidget
            {...item}
            entity={mainTable ? 'window' : entity}
            dateFormat={isDateField}
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
            onClickOutside={onClickOutside}
            listenOnKeys={listenOnKeys}
            listenOnKeysTrue={listenOnKeysTrue}
            listenOnKeysFalse={listenOnKeysFalse}
            onChange={mainTable ? onCellChange : null}
            closeTableField={closeTableField}
            isOpenDatePicker={isOpenDatePicker}
            ref={c => {
              this.widget = c && c.getWrappedInstance();
            }}
          />
        ) : (
          <div
            className={classnames('cell-text-wrapper', {
              [`${item.widgetType.toLowerCase()}-cell`]: item.widgetType,
              extended: cellExtended,
            })}
            style={style}
            title={
              item.widgetType === 'YesNo' ||
              item.widgetType === 'Switch' ||
              item.widgetType === 'Color'
                ? ''
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

TableCell.propTypes = {
  cellExtended: PropTypes.bool,
  extendLongText: PropTypes.number,
  handleRightClick: PropTypes.func,
  handleKeyDown: PropTypes.func,
  handleDoubleClick: PropTypes.func,
  onClickOutside: PropTypes.func,
  onCellChange: PropTypes.func,
  onCellExtend: PropTypes.func,
  isEdited: PropTypes.bool,
};

export default connect(state => ({
  modalVisible: state.windowHandler.modal.visible,
}))(TableCell);
