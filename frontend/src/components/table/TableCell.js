import Moment from 'moment-timezone';
import PropTypes from 'prop-types';
import numeral from 'numeral';
import React, { PureComponent, createRef } from 'react';
import classnames from 'classnames';

import MasterWidget from '../widget/MasterWidget';
import {
  AMOUNT_FIELD_TYPES,
  AMOUNT_FIELD_FORMATS_BY_PRECISION,
  SPECIAL_FIELD_TYPES,
  DATE_FIELD_TYPES,
  TIME_FIELD_TYPES,
  DATE_FIELD_FORMATS,
  TIME_REGEX_TEST,
  TIME_FORMAT,
} from '../../constants/Constants';
import WidgetTooltip from '../widget/WidgetTooltip';

class TableCell extends PureComponent {
  static getAmountFormatByPrecision = (precision) =>
    precision &&
    precision >= 0 &&
    precision < AMOUNT_FIELD_FORMATS_BY_PRECISION.length
      ? AMOUNT_FIELD_FORMATS_BY_PRECISION[precision]
      : null;

  static getDateFormat = (fieldType) => DATE_FIELD_FORMATS[fieldType];

  static createDate = (fieldValue, fieldType) => {
    if (fieldValue) {
      return !Moment.isMoment(fieldValue) && fieldValue.match(TIME_REGEX_TEST)
        ? Moment.utc(Moment.duration(fieldValue).asMilliseconds()).format(
            TIME_FORMAT
          )
        : Moment(fieldValue).format(TableCell.getDateFormat(fieldType));
    }

    return '';
  };

  static createAmount = (fieldValue, precision, isGerman) => {
    if (fieldValue) {
      const fieldValueAsNum = numeral(parseFloat(fieldValue));
      const numberFormat = TableCell.getAmountFormatByPrecision(precision);
      const returnValue = numberFormat
        ? fieldValueAsNum.format(numberFormat)
        : fieldValueAsNum.format();

      // For German natives we want to show numbers with comma as a value separator
      // https://github.com/metasfresh/me03/issues/1822
      if (isGerman && parseFloat(returnValue) != null) {
        const commaRegexp = /,/g;
        commaRegexp.test(returnValue);
        const lastIdx = commaRegexp.lastIndex;

        if (lastIdx) {
          return returnValue;
        }

        return `${returnValue}`.replace('.', ',');
      }

      return returnValue;
    }

    return '';
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

  // @TODO: THIS NEEDS URGENT REFACTORING, WHY THE HECK ARE WE RETURNING
  // SIX DIFFERENT TYPES OF VALUES HERE ? UBER-BAD DESIGN !
  static fieldValueToString = (
    fieldValue,
    fieldType = 'Text',
    precision = null,
    isGerman
  ) => {
    if (fieldValue === null) {
      return '';
    }

    switch (typeof fieldValue) {
      case 'object': {
        if (Array.isArray(fieldValue)) {
          return fieldValue
            .map((value) => TableCell.fieldValueToString(value, fieldType))
            .join(' - ');
        }

        return DATE_FIELD_TYPES.includes(fieldType) ||
          TIME_FIELD_TYPES.includes(fieldType)
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
        if (
          DATE_FIELD_TYPES.includes(fieldType) ||
          TIME_FIELD_TYPES.includes(fieldType)
        ) {
          return TableCell.createDate(fieldValue, fieldType);
        } else if (AMOUNT_FIELD_TYPES.includes(fieldType)) {
          return TableCell.createAmount(fieldValue, precision, isGerman);
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

    this.widget = createRef();

    this.clearWidgetValue = false;

    this.state = {
      tooltipToggled: false,
    };
  }

  UNSAFE_componentWillReceiveProps(nextProps) {
    const { updateRow, readonly, rowId, tdValue } = this.props;
    const { tdValue: nextTdValue } = nextProps;
    // We should avoid highlighting when whole row is exchanged (sorting)
    if (rowId !== nextProps.rowId) {
      return;
    }

    if (!readonly && tdValue !== nextTdValue) {
      updateRow();
    }
  }

  widgetTooltipToggle = (field, value) => {
    const curVal = this.state.tooltipToggled;
    const newVal = value != null ? value : !curVal;

    this.setState({
      tooltipToggled: newVal,
    });
  };

  handleBackdropLock = (state) => {
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

  handleKeyDown = (e) => {
    const {
      handleKeyDown,
      property,
      item,
      getWidgetData,
      isEditable,
      supportFieldEdit,
    } = this.props;
    const widgetData = getWidgetData(item, isEditable, supportFieldEdit);
    if (e.keyCode === 67 && (e.ctrlKey || e.metaKey)) {
      return false; // CMD + C on Mac has to just copy
    }
    handleKeyDown(e, property, widgetData[0]);
  };

  handleRightClick = (e) => {
    const {
      handleRightClick,
      property,
      supportZoomInto,
      supportFieldEdit,
      keyProperty,
    } = this.props;

    handleRightClick(
      e,
      keyProperty,
      property,
      !!supportZoomInto,
      supportFieldEdit
    );
  };

  onDoubleClick = (e) => {
    const {
      property,
      item,
      getWidgetData,
      isEditable,
      supportFieldEdit,
      handleDoubleClick,
    } = this.props;
    const widgetData = getWidgetData(item, isEditable, supportFieldEdit);

    if (isEditable) {
      handleDoubleClick(e, property, true, widgetData[0]);
    }
  };

  clearValue = (reset) => {
    this.clearWidgetValue = reset == null ? true : false;
  };

  render() {
    const {
      isEdited,
      isEditable,
      supportFieldEdit,
      cellExtended,
      extendLongText,
      getWidgetData,
      item,
      windowId,
      rowId,
      tabId,
      property,
      updatedRow,
      tabIndex,
      entity,
      listenOnKeys,
      listenOnKeysFalse,
      listenOnKeysTrue,
      closeTableField,
      getSizeClass,
      mainTable,
      onCellChange,
      viewId,
      modalVisible,
      onClickOutside,
      isGerman,
    } = this.props;
    const widgetData = getWidgetData(item, isEditable, supportFieldEdit);

    const docId = `${this.props.docId}`;
    const { tooltipToggled } = this.state;
    const tdValue = !isEdited
      ? TableCell.fieldValueToString(
          widgetData[0].value,
          item.widgetType,
          widgetData[0].precision,
          isGerman
        )
      : null;
    const description =
      widgetData[0].value && widgetData[0].value.description
        ? widgetData[0].value.description
        : tdValue;
    let tdTitle =
      item.widgetType === 'YesNo' ||
      item.widgetType === 'Switch' ||
      item.widgetType === 'Color'
        ? ''
        : description;
    const isOpenDatePicker = isEdited && item.widgetType === 'Date';
    const isDateField = DATE_FIELD_FORMATS[item.widgetType]
      ? TableCell.getDateFormat(item.widgetType)
      : false;
    let style = {};
    let tooltipData = null;
    let tooltipWidget =
      item.fields && item.widgetType === 'Lookup'
        ? item.fields.find((field, idx) => {
            if (field.type === 'Tooltip') {
              tooltipData = widgetData[idx];

              if (tooltipData && tooltipData.value) {
                return field;
              }
            }
            return false;
          })
        : null;

    if (cellExtended) {
      style = {
        height: extendLongText * 20,
      };
    }

    let entityEffective;
    if (viewId) {
      entityEffective = 'documentView';
    } else if (mainTable) {
      entityEffective = 'window';
    } else {
      entityEffective = entity;
    }

    return (
      <td
        tabIndex={modalVisible ? -1 : tabIndex}
        ref={(c) => (this.cell = c)}
        onDoubleClick={this.onDoubleClick}
        onKeyDown={this.handleKeyDown}
        onContextMenu={this.handleRightClick}
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
        data-cy={`cell-${property}`}
      >
        {isEdited ? (
          <MasterWidget
            {...item}
            {...{
              getWidgetData,
              viewId,
              rowId,
              widgetData,
              closeTableField,
              isOpenDatePicker,
              listenOnKeys,
              listenOnKeysFalse,
              listenOnKeysTrue,
              onClickOutside,
            }}
            clearValue={this.clearWidgetValue}
            entity={entityEffective}
            dateFormat={isDateField}
            dataId={mainTable ? null : docId}
            windowType={windowId}
            isMainTable={mainTable}
            tabId={mainTable ? null : tabId}
            noLabel={true}
            gridAlign={item.gridAlign}
            handleBackdropLock={this.handleBackdropLock}
            onChange={mainTable ? onCellChange : null}
            ref={this.widget}
          />
        ) : (
          <div className={classnames({ 'with-widget': tooltipWidget })}>
            <div
              className={classnames('cell-text-wrapper', {
                [`${item.widgetType.toLowerCase()}-cell`]: item.widgetType,
                extended: cellExtended,
              })}
              style={style}
              title={tdTitle}
            >
              {tdValue}
            </div>
            {tooltipWidget && !isEdited && (
              <WidgetTooltip
                widget={tooltipWidget}
                data={tooltipData}
                fieldName={item.field}
                isToggled={tooltipToggled}
                onToggle={this.widgetTooltipToggle}
              />
            )}
          </div>
        )}
      </td>
    );
  }
}

TableCell.propTypes = {
  tabId: PropTypes.any,
  windowId: PropTypes.any,
  keyProperty: PropTypes.string,
  tabIndex: PropTypes.number,
  listenOnKeys: PropTypes.bool,
  listenOnKeysFalse: PropTypes.func,
  listenOnKeysTrue: PropTypes.func,
  closeTableField: PropTypes.func,
  tdValue: PropTypes.any,
  supportFieldEdit: PropTypes.bool,
  supportZoomInto: PropTypes.bool,
  updatedRow: PropTypes.any,
  readonly: PropTypes.bool,
  rowId: PropTypes.string,
  item: PropTypes.object,
  isEditable: PropTypes.bool,
  updateRow: PropTypes.any,
  cellExtended: PropTypes.bool,
  extendLongText: PropTypes.number,
  property: PropTypes.string,
  getWidgetData: PropTypes.func,
  handleRightClick: PropTypes.func,
  handleKeyDown: PropTypes.func,
  handleDoubleClick: PropTypes.func,
  onClickOutside: PropTypes.func,
  onCellChange: PropTypes.func,
  isEdited: PropTypes.bool,
  isGerman: PropTypes.bool,
  entity: PropTypes.any,
  getSizeClass: PropTypes.func,
  mainTable: PropTypes.bool,
  viewId: PropTypes.string,
  modalVisible: PropTypes.bool,
  docId: PropTypes.any,
};

export default TableCell;
