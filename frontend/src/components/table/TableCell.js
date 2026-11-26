import PropTypes from 'prop-types';
import React, { createRef, PureComponent } from 'react';
import classnames from 'classnames';
import counterpart from 'counterpart';

import {
  ATTRIBUTE_WIDGET_TYPES,
  checkIfDateField,
  getSizeClass,
  getSizeStyle,
  getTdTitle,
} from '../../utils/tableHelpers';
import TableCellWidget from './TableCellWidget';
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
      widthKeeperValue: null,
    };
  }

  /**
   * The width keeper shows the value the cell had when its editor opened, not the value being
   * edited: clearing a Lookup with its "x" or choosing another value must not resize a column
   * whose width comes from that value. Taken anew each time the editor opens.
   */
  static getDerivedStateFromProps(props, state) {
    if (!props.isEdited) {
      return state.widthKeeperValue ? { widthKeeperValue: null } : null;
    }
    if (state.widthKeeperValue) {
      return null;
    }
    const { tdValue, tableCellData, description, tooltipData } = props;
    return {
      widthKeeperValue: { tdValue, tableCellData, description, tooltipData },
    };
  }

  /**
   * @param {bool|null} tooltipOpen - boolean value used to toggle the tooltipToggled value
   */
  widgetTooltipToggle = (tooltipOpen = null) => {
    const tooltipOpenEffective =
      tooltipOpen != null ? tooltipOpen : !this.state.tooltipToggled;

    this.setState({ tooltipToggled: tooltipOpenEffective });
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
    // 'Address' edits through the same <Attributes> button and popup as
    // 'ProductAttributes' and behaves the same: when the popup closes, the cell
    // stays in edit mode (the grid's onClickOutside is not called) until the
    // user leaves it, e.g. with Escape.
    const widgetsList = ['ProductAttributes', 'Address', 'List', 'Lookup'];

    if (!widgetsList.includes(item.widgetType)) {
      !state && this.props.onClickOutside();
    }
  };

  /**
   * @method handleKeyDown
   * @summary Key down function handler
   *
   * @param {object} event - this is the corresponding event from a text input for example
   * when you change a value within a table cell by typing something in that specific cell.
   */
  handleKeyDown = (event) => {
    if (event.keyCode === 67 && (event.ctrlKey || event.metaKey)) {
      return false; // CMD + C on Mac has to just copy
    }

    const { onKeyDown, property, isReadonly, tableCellData } = this.props;
    const widgetType = tableCellData?.widgetType;

    const isAttributeWidget = ATTRIBUTE_WIDGET_TYPES.includes(widgetType);

    onKeyDown &&
      onKeyDown({
        event,
        property,
        readonly: isReadonly,
        isAttributeWidget,
      });
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
    this.clearWidgetValue = reset == null;
  };

  /**
   * @method renderStaticContent
   * @summary The cell's read-only presentation. Rendered visibly when the cell is not being
   * edited, and as an invisible width keeper next to the editor while it is.
   * @param {object} [value] - the value to show ({tdValue, tableCellData, description,
   * tooltipData}); the current props when omitted
   */
  renderStaticContent = (value = this.props) => {
    const { item, cellExtended, extendLongText, tooltipWidget, rowId } =
      this.props;
    const { tdValue, tableCellData, description, tooltipData } = value;
    const { tooltipToggled } = this.state;
    const { widgetType } = item;
    const style = cellExtended ? { height: extendLongText * 20 } : {};

    return (
      <div className={classnames({ 'with-widget': tooltipWidget })}>
        <div
          className={classnames('cell-text-wrapper', {
            [`${widgetType.toLowerCase()}-cell`]: widgetType,
            extended: cellExtended,
          })}
          style={style}
          title={getTdTitle({ item, description })}
        >
          <TableCellWidget {...{ tdValue, widgetType, tableCellData, rowId }} />
        </div>
        {tooltipWidget && (
          <WidgetTooltip
            iconName={tooltipWidget.tooltipIconName}
            text={tooltipData?.value}
            isToggled={tooltipToggled}
            onToggle={(tooltipOpen) => this.widgetTooltipToggle(tooltipOpen)}
          />
        )}
      </div>
    );
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
      isModal,
      onClickOutside,
      updateHeight,
      rowIndex,
      hasComments,
      tableId,
      isReadonly,
      isMandatory,
      colIndex,
      updateRow,
      columnWidth,
    } = this.props;
    const docId = `${this.props.docId}`;
    const isOpenDatePicker = isEdited && item.widgetType === 'Date';
    const isDateField = checkIfDateField({ item });
    const style = cellExtended ? { height: extendLongText * 20 } : {};
    // An extended (multi-line) row makes the static value taller than one line. Hand that height
    // to the cell's editor too (table.scss `--cell-content-height`), so the editor box equals the
    // static box and opening it changes nothing.
    const contentHeightStyle = cellExtended
      ? { '--cell-content-height': `${extendLongText * 20}px` }
      : null;
    // a stored custom width wins over the size class (handled above); absent that, a combobox (90px)
    // or price/amount (68px) column still needs its minimum width applied inline, without promoting
    // the td-* band
    const minWidthFloorStyle = columnWidth ? undefined : getSizeStyle(item);
    const widthStyle = columnWidth
      ? {
          ...style,
          width: `${columnWidth}px`,
          minWidth: `${columnWidth}px`,
          maxWidth: `${columnWidth}px`,
        }
      : minWidthFloorStyle
      ? { ...style, ...minWidthFloorStyle }
      : undefined;
    const tdStyle = contentHeightStyle
      ? { ...widthStyle, ...contentHeightStyle }
      : widthStyle;

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
          { [getSizeClass(item)]: !columnWidth },
          item.widgetType,
          {
            'pulse-on': updatedRow,
            'pulse-off': !updatedRow,
          }
        )}
        style={tdStyle}
        data-cy={`cell-${property}`}
      >
        {hasComments && (
          <span
            className="notification-number size-sm"
            title={counterpart.translate('window.comments.caption')}
          />
        )}
        {isEdited ? (
          <>
            {/*
              Keeps the column exactly as wide as the static value made it: the editor itself
              takes no width of its own in a grid cell (table.scss), so without this invisible
              copy of the static content a column sized by its value would snap to its band
              minimum while editing.
            */}
            <div className="cell-width-keeper" aria-hidden="true">
              {this.renderStaticContent(this.state.widthKeeperValue)}
            </div>
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
                isEdited,
                supportFieldEdit,
                entity,
                updateHeight,
                updateRow,
                isModal,
              }}
              suppressChange={isEdited}
              clearValue={this.clearWidgetValue}
              dateFormat={isDateField}
              dataId={mainTable ? null : docId}
              tabId={mainTable ? null : tabId}
              noLabel={true}
              gridAlign={item.gridAlign}
              handleBackdropLock={this.handleBackdropLock}
            />
          </>
        ) : (
          this.renderStaticContent()
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
  isModal: PropTypes.bool,
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
  tableCellData: PropTypes.object,
  tableId: PropTypes.string.isRequired,
  columnWidth: PropTypes.number,
};

export default TableCell;
