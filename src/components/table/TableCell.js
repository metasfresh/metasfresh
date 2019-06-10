import React, { PureComponent } from 'react';
import Moment from 'moment';
import PropTypes from 'prop-types';
import numeral from 'numeral';
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
import WidgetTooltip from '../widget/WidgetTooltip';

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

  constructor(props) {
    super(props);

    this.state = {
      tooltipToggled: false,
      widgetFocused: false,
      attributesDropdownShown: false,
    };
  }

  UNSAFE_componentWillReceiveProps(nextProps) {
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

  setFocusedWidgetFalse = () => {
    this.setState({
      widgetFocused: false,
    });
  };

  setFocusedWidgetTrue = callback => {
    this.setState(
      {
        widgetFocused: true,
      },
      () => {
        this.cell.focus();

        callback && callback();
      }
    );
  };

  toggleDropdownShown = val => {
    let attributesDropdownShown =
      val != null ? val : !this.state.attributesDropdownShown;

    this.setState({
      attributesDropdownShown,
    });
  };

  widgetTooltipToggle = (field, value) => {
    const curVal = this.state.tooltipToggled;
    const newVal = value != null ? value : !curVal;

    this.setState({
      tooltipToggled: newVal,
    });
  };

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

  handlePatch = () => {
    const { onCellChange, mainTable } = this.props;

    this.setFocusedWidgetTrue(() => {
      mainTable && onCellChange && onCellChange();
    });
  };

  handleKeyDown = e => {
    const { property, handleKeyDown, widgetData } = this.props;
    const { key } = e;
    const { attributesDropdownShown } = this.state;

    if (widgetData[0].widgetType === 'ProductAttributes') {
      if (key === 'Enter' && !attributesDropdownShown) {
        this.toggleDropdownShown(true);
        // return;
      } else if (key === 'Escape' && attributesDropdownShown) {
        this.toggleDropdownShown(false);
      } else if (attributesDropdownShown) {
        e.stopPropagation();
        return;
      }
    }

    if (['Enter', 'Tab', 'Escape'].includes(key)) {
      this.setFocusedWidgetTrue();
    }

    handleKeyDown(e, property, widgetData);
  };

  render() {
    const {
      isEdited,
      cellExtended,
      extendLongText,
      widgetData,
      item,
      windowId,
      rowId,
      property,
      tabId,
      handleDoubleClick,
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
      onCellFocused,
      viewId,
      modalVisible,
      onClickOutside,
      showWidget,
      isEditable,
    } = this.props;
    const docId = `${this.props.docId}`;
    const { tooltipToggled, widgetFocused } = this.state;
    const tdValue = !isEdited
      ? TableCell.fieldValueToString(
          widgetData[0].value,
          item.widgetType,
          widgetData[0].precision
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

    return (
      <td
        tabIndex={modalVisible ? -1 : tabIndex}
        ref={c => (this.cell = c)}
        onDoubleClick={handleDoubleClick}
        onKeyDown={this.handleKeyDown}
        onFocus={e => {
          if (!widgetFocused) {
            onCellFocused(e, property, widgetData);
          } else {
            this.setFocusedWidgetFalse();
          }
        }}
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
        {(isEditable && isEdited) || showWidget ? (
          <MasterWidget
            {...item}
            entity={mainTable ? 'window' : entity}
            dateFormat={isDateField}
            dataId={mainTable ? null : docId}
            widgetData={widgetData}
            windowType={windowId}
            isMainTable={mainTable}
            rowId={rowId}
            viewId={viewId}
            tabId={mainTable ? null : tabId}
            noLabel={true}
            tabIndex={-1}
            gridAlign={item.gridAlign}
            handleBackdropLock={this.handleBackdropLock}
            onClickOutside={e => {
              this.toggleDropdownShown(false);
              onClickOutside(e);
            }}
            listenOnKeys={listenOnKeys}
            listenOnKeysTrue={listenOnKeysTrue}
            listenOnKeysFalse={listenOnKeysFalse}
            listenOnKeysdoNotOpenOnFocusFalse={listenOnKeysFalse}
            onChange={this.handlePatch}
            closeTableField={closeTableField}
            isOpenDatePicker={isOpenDatePicker}
            ref={c => {
              this.widget = c && c.getWrappedInstance();
            }}
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
                isToggled={tooltipToggled}
                onToggle={val => this.widgetTooltipToggle(item.field, val)}
              />
            )}
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
  isEditable: PropTypes.bool,
  showWidget: PropTypes.bool,
  widgetData: PropTypes.array,
  property: PropTypes.string,
  updateRow: PropTypes.func,
  readonly: PropTypes.string,
  rowId: PropTypes.string,
  item: PropTypes.object,
  mainTable: PropTypes.bool,
  windowId: PropTypes.string,
  tabId: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
  updatedRow: PropTypes.bool,
  tabIndex: PropTypes.number,
  entity: PropTypes.string,
  listenOnKeys: PropTypes.bool,
  listenOnKeysFalse: PropTypes.func,
  listenOnKeysTrue: PropTypes.func,
  closeTableField: PropTypes.func,
  getSizeClass: PropTypes.func,
  onCellFocused: PropTypes.func,
  viewId: PropTypes.string,
  modalVisible: PropTypes.bool,
  docId: PropTypes.string,
};

export default connect(state => ({
  modalVisible: state.windowHandler.modal.visible,
}))(TableCell);
