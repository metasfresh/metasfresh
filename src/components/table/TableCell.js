import Moment from 'moment';
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

  constructor(props) {
    super(props);

    this.state = {
      backdropLock: false,
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
      {
        backdropLock: !state,
      },
      () => {
        if (!state) {
          this.props.onClickOutside();
        }
      }
    );
  };

  render() {
    const {
      isEdited,
      widgetData,
      item,
      type,
      rowId,
      tabId,
      onDoubleClick,
      onKeyDown,
      updatedRow,
      tabIndex,
      entity,
      listenOnKeys,
      listenOnKeysFalse,
      listenOnKeysTrue,
      closeTableField,
      getSizeClass,
      onRightClick,
      mainTable,
      onCellChange,
      viewId,
      modalVisible,
    } = this.props;
    // TODO: Hack, Color fields should be readonly
    const readonly = item.widgetType === 'Color' ? true : this.props.readonly;
    const docId = this.props.docId + '';
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

    return (
      <td
        tabIndex={modalVisible ? -1 : tabIndex}
        ref={c => (this.cell = c)}
        onDoubleClick={e => {
          if (isEdited) e.stopPropagation();
          if (!readonly) onDoubleClick(e);
        }}
        onKeyDown={onKeyDown}
        onContextMenu={onRightClick}
        className={classnames(
          {
            [`text-xs-${item.gridAlign}`]: item.gridAlign,
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
            onBackdropLock={this.handleBackdropLock}
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
            })}
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

export default connect(state => ({
  modalVisible: state.windowHandler.modal.visible,
}))(TableCell);
