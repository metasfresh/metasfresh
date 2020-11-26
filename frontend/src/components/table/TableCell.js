import PropTypes from 'prop-types';
import React, { PureComponent, createRef } from 'react';
import classnames from 'classnames';
import counterpart from 'counterpart';

import {
  getSizeClass,
  getTdTitle,
  checkIfDateField,
} from '../../utils/tableHelpers';

import WidgetWrapper from '../../containers/WidgetWrapper';
import WidgetTooltip from '../widget/WidgetTooltip';

/**
 * @file Class based component.
 * @module TableCell
 * @extends PureComponent
 */
class TableCell extends PureComponent {
  constructor(props) {
    super(props);
    this.cellRef = createRef();
    this.clearWidgetValue = false;

    this.state = {
      tooltipToggled: false, // keeping in the local state the flag for the tooltip
    };
  }

  /**
   * @method widgetTooltipToggle
   * @summary Alternative method to open dropdown, in case of disabled opening on focus.
   *
   * @param {bool|null} value - boolean value used to toggle the tooltipToggled value
   */
  widgetTooltipToggle = (value) => {
    const curVal = this.state.tooltipToggled;
    const newVal = value != null ? value : !curVal;

    this.setState({ tooltipToggled: newVal });
  };

  /**
   * @method handleBackdropLock
   * @summary checks widget against widget list and calls parent onClickOutside fnct
   *
   * @param {bool} state  - boolean indicator given from child components like the DatePicker,
   * Attributes used for the backdrop state
   */
  handleBackdropLock = (state) => {
    const { item } = this.props;
    const widgetsList = ['ProductAttributes', 'Attributes', 'List', 'Lookup'];

    if (!widgetsList.includes(item.widgetType)) {
      !state && this.props.onClickOutside();
    }
  };

  /**
   * @method handleKeyDown
   * @summary Key down function handler
   *
   * @param {object} e - this is the corresponding event from a text input for example
   * when you change a value within a table cell by typing something in that specific cell.
   */
  handleKeyDown = (e) => {
    const { onKeyDown, property, isReadonly, updateRow } = this.props;

    if (e.keyCode === 67 && (e.ctrlKey || e.metaKey)) {
      return false; // CMD + C on Mac has to just copy
    }

    onKeyDown(e, property, isReadonly);

    // TODO: We should limit this to only action keys (esc, tab, enter)
    !isReadonly && updateRow(); // toggle the flag in parent component highlighting
    // the row giving the user feedback that an action is running
  };

  /**
   * @method handleRightClick
   * @summary Function called on right click that further calls the parent handler
   * function to handleRightClick
   *
   * @param {object} e
   */
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

  /**
   * @method handleFocus
   * @summary Function called when the cell is focused and that further calls the parent handler
   * function to handleFocusAction
   */
  handleFocus = () => {
    const { property, handleFocusAction, supportFieldEdit } = this.props;
    handleFocusAction({ fieldName: property, supportFieldEdit });
  };

  /**
   * @method onDoubleClick
   * @summary Function called on double click that retrieves widget data and
   * further calls the parent handler function to handleDounbleClick
   *
   * @param {object} e
   */
  onDoubleClick = (e) => {
    const { property, isEditable, handleDoubleClick, isReadonly } = this.props;

    if (isEditable) {
      handleDoubleClick({
        event: e,
        property,
        focus: true,
        readonly: isReadonly,
      });
    }
  };

  /**
   * @method clearValue
   * @summary Set local `clearWidgetValue` value based on a given `reset` param. It controls
   * if the widget should be constructed with current value cleared or not. It is called
   * by the TableRow
   *
   * @param {string|null} reset - might also be `undefined` in which case (because we don't
   * have a strict comparison below) it will be true
   */
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
      mainTable,
      viewId,
      modalVisible,
      onClickOutside,
      updateHeight,
      rowIndex,
      hasComments,
      tableId,
      isReadonly,
      isMandatory,
      tooltipData,
      tooltipWidget,
      tdValue,
      description,
      colIndex,
    } = this.props;
    const docId = `${this.props.docId}`;
    const { tooltipToggled } = this.state;

    const tdTitle = getTdTitle({ item, description });
    const isOpenDatePicker = isEdited && item.widgetType === 'Date';
    const isDateField = checkIfDateField({ item });
    let style = cellExtended ? { height: extendLongText * 20 } : {};

    return (
      <td
        tabIndex={modalVisible ? -1 : tabIndex}
        ref={this.cellRef}
        onDoubleClick={this.onDoubleClick}
        onKeyDown={this.handleKeyDown}
        onContextMenu={this.handleRightClick}
        onFocus={this.handleFocus}
        className={classnames(
          'table-cell',
          {
            [`text-${item.gridAlign}`]: item.gridAlign,
            'cell-disabled': isReadonly,
            'cell-mandatory': isMandatory,
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
        {hasComments && (
          <span
            className="notification-number size-sm"
            title={counterpart.translate('window.comments.caption')}
          />
        )}
        {isEdited ? (
          <WidgetWrapper
            renderMaster={true}
            dataSource="table"
            tableId={tableId}
            {...item}
            {...{
              tableId,
              windowId,
              viewId,
              rowId,
              closeTableField,
              isOpenDatePicker,
              listenOnKeys,
              listenOnKeysFalse,
              listenOnKeysTrue,
              onClickOutside,
              rowIndex,
              colIndex,
              isEditable,
              supportFieldEdit,
              entity,
              updateHeight,
            }}
            clearValue={this.clearWidgetValue}
            dateFormat={isDateField}
            dataId={mainTable ? null : docId}
            tabId={mainTable ? null : tabId}
            noLabel={true}
            gridAlign={item.gridAlign}
            handleBackdropLock={this.handleBackdropLock}
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
  viewId: PropTypes.string,
  rowId: PropTypes.string,
  docId: PropTypes.any,
  rowIndex: PropTypes.number, // used for knowing the row index within the Table (used on AttributesDropdown component)
  colIndex: PropTypes.number,
  tabIndex: PropTypes.number,
  keyProperty: PropTypes.string,
  listenOnKeys: PropTypes.bool,
  listenOnKeysFalse: PropTypes.func,
  listenOnKeysTrue: PropTypes.func,
  closeTableField: PropTypes.func,
  isReadonly: PropTypes.bool,
  isMandatory: PropTypes.bool,
  tdValue: PropTypes.any,
  description: PropTypes.any, // TODO: We have 4 types of values here. Needs fixing at some point.
  tooltipData: PropTypes.any,
  tooltipWidget: PropTypes.object,
  supportFieldEdit: PropTypes.bool,
  supportZoomInto: PropTypes.bool,
  updatedRow: PropTypes.any,
  item: PropTypes.object,
  isEditable: PropTypes.bool,
  updateRow: PropTypes.any,
  cellExtended: PropTypes.bool,
  extendLongText: PropTypes.number,
  property: PropTypes.string,
  getWidgetData: PropTypes.func,
  handleRightClick: PropTypes.func,
  onKeyDown: PropTypes.func,
  handleDoubleClick: PropTypes.func,
  onClickOutside: PropTypes.func,
  onCellChange: PropTypes.func,
  isEdited: PropTypes.bool,
  isGerman: PropTypes.bool,
  entity: PropTypes.any,
  mainTable: PropTypes.bool,
  modalVisible: PropTypes.bool,
  updateHeight: PropTypes.func, // adjusts the table container with a given height from a child component when child exceeds visible area
  hasComments: PropTypes.bool,
  handleFocusAction: PropTypes.func,
  tableId: PropTypes.string.isRequired,
};

export default TableCell;
