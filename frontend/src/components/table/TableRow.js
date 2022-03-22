import React, { PureComponent } from 'react';
import ReactDOM from 'react-dom';
import classnames from 'classnames';
import { F2_KEY } from '../../constants/Constants';
import {
  getCellWidgetData,
  getDescription,
  getIconClassName,
  getTdValue,
  getTooltipWidget,
  isCellEditable,
  isEditableOnDemand,
  nestedSelect,
  prepareWidgetData,
  shouldRenderColumn,
} from '../../utils/tableHelpers';
import TableCell from './TableCell';
import WithMobileDoubleTap from '../WithMobileDoubleTap';
import PropTypes from 'prop-types';

/**
 * @file Class based component.
 * @module TableRow
 * @extends PureComponent
 */
class TableRow extends PureComponent {
  mounted = false;

  constructor(props) {
    super(props);

    const multilineText = props.cols.filter(
      (item) => item.multilineText === true
    ).length;

    let multilineTextLines = 0;
    props.cols.forEach((col) => {
      if (
        col.multilineTextLines &&
        col.multilineTextLines > multilineTextLines
      ) {
        multilineTextLines = col.multilineTextLines;
      }
    });

    this.state = {
      edited: '',
      activeCell: '',
      activeCellName: null,
      updatedRow: false,
      multilineText,
      multilineTextLines,
      cellsExtended: false,
      valueBeforeEditing: null,
    };
  }

  componentDidUpdate(prevProps) {
    const { multilineText, activeCell } = this.state;
    const { focusOnFieldName, isSelected } = this.props;

    if (multilineText && this.props.isSelected !== prevProps.isSelected) {
      this.handleCellExtend(this.props.isSelected);
    }

    if (focusOnFieldName && isSelected && this.autofocusCell && !activeCell) {
      // eslint-disable-next-line react/no-find-dom-node
      ReactDOM.findDOMNode(this.autofocusCell).focus();
      this._focusCell();
    }
  }

  componentDidMount() {
    const { focusOnFieldName, isSelected } = this.props;

    if (focusOnFieldName && isSelected && this.autofocusCell) {
      // eslint-disable-next-line react/no-find-dom-node
      ReactDOM.findDOMNode(this.autofocusCell).focus();
    }

    this.mounted = true;
  }

  componentWillUnmount() {
    this.mounted = false;
  }

  getFieldValue = (fieldName) => {
    return this.props.fieldsByName?.[fieldName]?.value;
  };

  /**
   * @method initPropertyEditor
   * @summary Initialize the editor for a widget field
   * @param {object} fieldName - the name of the field,
   * @param {boolean} mark - marks the text(like when you click and hold and select the text)
   */
  initPropertyEditor = ({ fieldName, mark }) => {
    const { cols, fieldsByName } = this.props;

    if (cols && fieldsByName) {
      cols.map((item) => {
        const property = item.fields[0].field;
        if (property === fieldName) {
          const widgetData = prepareWidgetData(item, fieldsByName);
          if (widgetData) {
            this.setState({
              valueBeforeEditing: this.getFieldValue(fieldName) ?? '',
            });

            this.handleEditProperty({
              event: null,
              property,
              focus: true,
              readonly: widgetData[0] ? widgetData[0].readonly : false,
              select: false,
              mark,
            });
          }
        }
      });
    }
  };

  handleClick = (e) => {
    const { onClick, item } = this.props;

    onClick(e, item);
  };

  handleClickOutside = (event) => {
    this.selectedCell && this.selectedCell.clearValue(true);
    this.handleEditProperty({ event });
  };

  handleDoubleClick = (e) => {
    const { rowId, onDoubleClick, supportOpenRecord } = this.props;

    this.setState({ valueBeforeEditing: e.target.textContent });

    if (supportOpenRecord) {
      onDoubleClick && onDoubleClick(rowId);
    }
  };

  handleKeyDown = ({ event, property, readonly, isAttributeWidget }) => {
    switch (event.key) {
      case 'Enter': {
        this.handleKeyDown_Enter({
          event,
          property,
          readonly,
          isAttributeWidget,
        });
        break;
      }
      case 'Tab': {
        this.handleKeyDown_Tab({ event, property, isAttributeWidget });
        break;
      }
      case 'Escape': {
        this.handleKeyDown_Escape({ event, property });
        break;
      }
      default: {
        if (event.keyCode === F2_KEY) {
          const { onFastInlineEdit } = this.props;
          onFastInlineEdit();
        } else {
          const inp = String.fromCharCode(event.keyCode);
          if (/[a-zA-Z0-9]/.test(inp) && !event.ctrlKey && !event.altKey) {
            this.handleKeyDown_RegularChar({ event, property, readonly });
          }
        }
        break;
      }
    }
  };

  handleKeyDown_Enter = ({ event, property, readonly, isAttributeWidget }) => {
    if (isAttributeWidget) {
      return;
    }

    const { rowId, tabId, entity, modalVisible, tableId, updatePropertyValue } =
      this.props;
    const { edited } = this.state;
    const inputContent = event.target.value;

    // here `edited` controls if on {enter} we should edit a widget, or only submit it.
    // if true - property will be edited. Otherwise just saved.
    // If widget is not active - use the textContent as the initial value
    let fieldValue = event.target.value;

    if (!edited) {
      fieldValue = event.target.textContent;
      this.handleEditProperty({
        event,
        property,
        focus: true,
        readonly,
      });
    }

    this.setState(
      {
        valueBeforeEditing: fieldValue,
      },
      () => {
        updatePropertyValue({
          property,
          value: inputContent,
          tabId,
          rowId,
          isModal: modalVisible,
          entity,
          tableId,
        });
      }
    );
  };

  handleKeyDown_Tab = ({ event, property, isAttributeWidget }) => {
    const { rowId, tabId, entity, modalVisible, tableId, updatePropertyValue } =
      this.props;
    const { edited } = this.state;

    // if ProductAttributes widget is visible, skip over Tab navigation here
    if (isAttributeWidget) {
      return;
    }

    // this test is for a case when user is navigating around the table
    // without activating the field. Then there's no widget (input), so the value
    // is undefined and we don't have to worry about it
    if (typeof event.target.value !== 'undefined') {
      updatePropertyValue({
        property,
        value: event.target.value,
        tabId,
        rowId,
        isModal: modalVisible,
        entity,
        tableId,
      });
    }
    if (edited === property) {
      event.stopPropagation();
      this.handleEditProperty({ event });
    }
  };

  handleKeyDown_Escape = ({ event, property }) => {
    const {
      changeListenOnTrue,
      rowId,
      tabId,
      entity,
      modalVisible,
      tableId,
      updatePropertyValue,
    } = this.props;
    const { edited, valueBeforeEditing, activeCell } = this.state;

    if (edited === property) {
      updatePropertyValue({
        property,
        value: valueBeforeEditing,
        tabId,
        rowId,
        isModal: modalVisible,
        entity,
        tableId,
      });
      event.stopPropagation();

      // reset the field value to the previous one, so that we won't
      // overwrite it
      event.target.value = valueBeforeEditing;

      // we need to store the active cell to focus it after deactivating widget
      const activeCellElement = activeCell;

      this.handleEditProperty({ event });
      activeCellElement.focus();
      changeListenOnTrue();
      this.setState({ valueBeforeEditing: null });
    }
  };

  handleKeyDown_RegularChar = ({ event, property, readonly }) => {
    const { valueBeforeEditing } = this.state;
    if (valueBeforeEditing === null) {
      // for disabled fields/fields without value, we don't get the field data from the backend
      const fieldValue = this.getFieldValue(property);
      if (fieldValue !== undefined) {
        this.setState({ valueBeforeEditing: fieldValue });
      }
    }

    this.handleEditProperty({
      event,
      property,
      focus: true,
      readonly,
      select: true,
    });
  };

  /**
   * @method handleEditProperty
   * @summary focuses and sets the cell as edited or clears the edited cell
   * if optional params are not provided
   *
   * @param {object} [event] - event
   * @param {string} [property] - field name
   * @param {boolean} [focus] - flag if cell should be focused
   * @param {boolean} [readonly] - true if cell is readonly
   * @param {boolean} [select] - flag if selected cell should be cleared
   * @param {boolean} [mark] - marks the text(like when you click and hold and select the text)
   */
  handleEditProperty = ({ event, property, focus, readonly, select, mark }) => {
    this._focusCell(property, () => {
      this._editProperty({ event, property, focus, readonly, select, mark });
    });
  };

  /**
   * @method _editProperty
   * @summary Depending on the params provided, sets or resets edited cell
   * and focuses the cell dom element enabling/disabling key listeners
   *
   * @param {object} event - DOM event
   * @param {object} [property] - field name
   * @param {boolean} [focus] - flag if cell's widget should be focused
   * @param {boolean} [readonly] - widget's read status
   * @param {boolean} [select] - flag if selected cell should be cleared
   * @param {boolean} [mark] - flag if widget content should be selected
   */
  _editProperty = ({ event, property, focus, readonly, select, mark }) => {
    const { listenOnKeys, changeListenOnTrue } = this.props;

    const isEditable = typeof readonly !== undefined ? !readonly : true;
    if (isEditable) {
      if (this.state.edited === property && event) event.persist();

      // cell's widget will have the value cleared on creation
      if (select && this.selectedCell) {
        this.selectedCell.clearValue();
      }

      this.setState(
        {
          edited: property,
        },
        () => {
          if (focus) {
            const elem =
              document.activeElement.getElementsByClassName(
                'js-input-field'
              )[0];

            if (elem) {
              mark && elem.select();
              elem.focus();
            }

            const disabled =
              document.activeElement.querySelector('.input-disabled');
            const readonly =
              document.activeElement.querySelector('.input-readonly');

            if (disabled || readonly) {
              !listenOnKeys && changeListenOnTrue();
              this.handleEditProperty({ event });
            }
          }
        }
      );
    }
  };

  /**
   * @method handleCellExtend
   * @summary set flag to render cells as extended if needed
   *
   * @param {boolean} selected - is row selected
   */
  handleCellExtend = (selected) => {
    this.setState({
      cellsExtended: selected,
    });
  };

  /**
   * @method handleIndentSelect
   * @summary selects row and it's descendants
   *
   * @param {object} e - event
   */
  handleIndentSelect = (e) => {
    e.stopPropagation();
    const { handleSelect, rowId, includedDocuments } = this.props;

    handleSelect(nestedSelect(includedDocuments).concat([rowId]));
  };

  /**
   * @method handleRowCollapse
   * @summary toggle collapsible row
   */
  handleRowCollapse = () => {
    const { item, collapsed, onRowCollapse } = this.props;

    onRowCollapse(item, collapsed);
  };

  /**
   * @method closeTableField
   * @summary finish editing cell/hide widget
   *
   * @param {object} e - event
   */
  closeTableField = (e) => {
    const { activeCell } = this.state;

    this.handleEditProperty({ event: e });

    activeCell && activeCell.focus();
  };

  /**
   * @method updateRow
   * @summary sets a flag to render row as edited to visualize an edit
   */
  updateRow = () => {
    this.setState({ updatedRow: true }, () => {
      // wait one second before resetting the flag.
      // this will produce a visual fade effect
      // letting the user know the row has been updated
      setTimeout(() => {
        if (this.mounted) {
          this.setState({ updatedRow: false });
        }
      }, 1000);
    });
  };

  /**
   * @method _focusCell
   * @summary focuses and saves the cell as active element, or clears the activeCell(Name)
   * if optional params are not provided
   *
   * @param {string} [property] - cell element's name
   * @param {function} [cb] - callback function
   */
  _focusCell = (property, cb) => {
    const { activeCell, activeCellName } = this.state;

    if (property !== activeCellName || cb) {
      const newState = { activeCellName: property };
      let elem = document.activeElement;

      // only cells should be stored as `activeCell` so if current element
      // is not a cell (for instance it's a widget input or a context menu)
      // find the relevant cell in the DOM
      if (!elem.className.includes('table-cell')) {
        elem = document.querySelector(
          `.row-selected [data-cy="cell-${property}"]`
        );
      }

      if (elem && activeCell !== elem) {
        newState.activeCell = elem;
      }

      this.setState(newState, () => {
        cb && cb();
      });
    } else {
      cb && cb();
    }
  };

  renderCells = () => {
    const {
      cols,
      windowId,
      docId,
      rowId,
      tabId,
      mainTable,
      newRow,
      tabIndex,
      entity,
      handleRightClick,
      caption,
      colspan,
      viewId,
      keyProperty,
      modalVisible,
      isGerman,
      isSelected,
      focusOnFieldName,
      updateHeight,
      rowIndex,
      hasComments,
      handleFocusAction,
      tableId,
      listenOnKeys,
      changeListenOnTrue,
      changeListenOnFalse,
      fieldsByName,
    } = this.props;
    const {
      edited,
      updatedRow,
      cellsExtended,
      multilineText,
      multilineTextLines,
      activeCellName,
    } = this.state;

    // Iterate over layout settings
    if (colspan) {
      return <td colSpan={cols.length}>{caption}</td>;
    } else {
      return (
        cols &&
        cols.map((item, idx) => {
          if (shouldRenderColumn(item)) {
            const { supportZoomInto } = item.fields[0];
            const supportFieldEdit =
              mainTable && isEditableOnDemand(item, fieldsByName);
            const property = item.fields[0].field;
            const tableCellData = fieldsByName[property]
              ? fieldsByName[property]
              : undefined;
            const isEditable = isCellEditable(item, fieldsByName);
            const isEdited = edited === property;
            const extendLongText = multilineText ? multilineTextLines : 0;
            const widgetData = getCellWidgetData(
              fieldsByName,
              item,
              isEditable,
              supportFieldEdit
            );
            const isReadonly = widgetData[0].readonly;
            const isMandatory = widgetData[0].mandatory;
            const tdValue = getTdValue({
              widgetData,
              item,
              isEdited,
              isGerman,
            });
            const description = getDescription({ widgetData, tdValue });
            const { tooltipData, tooltipWidget } = getTooltipWidget(
              item,
              widgetData
            );

            return (
              <TableCell
                {...{
                  entity,
                  windowId,
                  docId,
                  rowId,
                  tabId,
                  item,
                  tabIndex,
                  listenOnKeys,
                  caption,
                  mainTable,
                  viewId,
                  extendLongText,
                  property,
                  supportZoomInto,
                  supportFieldEdit,
                  handleRightClick,
                  keyProperty,
                  modalVisible,
                  rowIndex,
                  tableId,
                  isGerman,
                  isEditable,
                  isReadonly,
                  isMandatory,
                  isEdited,
                  tooltipData,
                  tooltipWidget,
                  tdValue,
                  tableCellData,
                  description,
                  updateHeight,
                }}
                ref={(c) => {
                  if (c && isSelected) {
                    if (focusOnFieldName === property) {
                      this.autofocusCell = c;
                    }
                    if (activeCellName === property) {
                      this.selectedCell = c;
                    }
                  }
                }}
                colIndex={idx}
                hasComments={!!(hasComments && idx === 0)}
                cellExtended={cellsExtended}
                key={`${property}-${idx}`}
                isRowSelected={isSelected}
                handleDoubleClick={this.handleEditProperty}
                handleFocusAction={handleFocusAction}
                onClickOutside={this.handleClickOutside}
                updatedRow={updatedRow || newRow}
                updateRow={this.updateRow}
                onKeyDown={this.handleKeyDown}
                listenOnKeysTrue={changeListenOnTrue}
                listenOnKeysFalse={changeListenOnFalse}
                closeTableField={this.closeTableField}
              />
            );
          }
        })
      );
    }
  };

  /**
   * @method renderTree
   * @summary Renders the indented column
   *
   * @param {string} huType - type of the row (CU/TU etc) no generate proper icon
   */
  renderTree = (huType) => {
    const { indent, lastChild, includedDocuments, collapsed, collapsible } =
      this.props;

    let indentation = [];

    for (let i = 0; i < indent.length; i++) {
      indentation.push(
        <div
          key={i}
          className={classnames('indent-item-mid', {
            'indent-collapsible-item-mid': collapsible,
          })}
        >
          {i === indent.length - 1 && <div className="indent-mid" />}
          <div
            className={classnames({
              'indent-sign': indent[i],
              'indent-sign-bot': lastChild && i === indent.length - 1,
            })}
          />
        </div>
      );
    }

    return (
      <div className={'indent'}>
        {indentation}
        {includedDocuments && !collapsed && (
          <div
            className={classnames('indent-bot', {
              'indent-collapsible-bot': collapsible,
            })}
          />
        )}
        {includedDocuments && collapsible ? (
          collapsed ? (
            <i
              onClick={this.handleRowCollapse}
              className="meta-icon-plus indent-collapse-icon"
            />
          ) : (
            <i
              onClick={this.handleRowCollapse}
              className="meta-icon-minus indent-collapse-icon"
            />
          )
        ) : (
          ''
        )}
        <div className="indent-icon" onClick={this.handleIndentSelect}>
          <i className={getIconClassName(huType)} />
        </div>
      </div>
    );
  };

  render() {
    const {
      isSelected,
      odd,
      indentSupported,
      indent,
      contextType,
      lastChild,
      processed,
      includedDocuments,
      notSaved,
      caption,
      dataKey,
      keyProperty,
    } = this.props;

    return (
      <WithMobileDoubleTap>
        <tr
          onClick={this.handleClick}
          onDoubleClick={this.handleDoubleClick}
          className={classnames(dataKey, `table-row row-${keyProperty}`, {
            'row-selected': isSelected,
            'tr-odd': odd,
            'tr-even': !odd,
            'row-disabled': processed,
            'row-boundary': processed && lastChild && !includedDocuments,
            'row-not-saved': notSaved,
            'item-caption': caption,
          })}
        >
          {indentSupported && indent && (
            <td className="indented">{this.renderTree(contextType)}</td>
          )}
          {this.renderCells()}
        </tr>
      </WithMobileDoubleTap>
    );
  }
}

TableRow.propTypes = {
  lastPage: PropTypes.string,
  cols: PropTypes.array.isRequired,
  onClick: PropTypes.func.isRequired,
  item: PropTypes.object.isRequired,
  dataKey: PropTypes.string.isRequired,
  handleSelect: PropTypes.func,
  onDoubleClick: PropTypes.func,
  indentSupported: PropTypes.bool,
  collapsible: PropTypes.bool,
  collapsed: PropTypes.bool,
  processed: PropTypes.bool,
  notSaved: PropTypes.bool,
  isSelected: PropTypes.bool,
  odd: PropTypes.number,
  caption: PropTypes.string,
  listenOnKeys: PropTypes.bool,
  changeListenOnTrue: PropTypes.func,
  onRowCollapse: PropTypes.func,
  handleRightClick: PropTypes.func,
  fieldsByName: PropTypes.object,
  indent: PropTypes.array,
  rowId: PropTypes.string,
  supportOpenRecord: PropTypes.bool,
  changeListenOnFalse: PropTypes.func,
  tabId: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
  mainTable: PropTypes.bool,
  newRow: PropTypes.bool,
  tabIndex: PropTypes.number,
  entity: PropTypes.string,
  colspan: PropTypes.oneOfType([PropTypes.bool, PropTypes.string]),
  // TODO: ^^ We cannot allow having a prop which is sometimes bool and sometimes string
  viewId: PropTypes.string,
  docId: PropTypes.string,
  windowId: PropTypes.string,
  lastChild: PropTypes.bool,
  includedDocuments: PropTypes.array,
  contextType: PropTypes.any,
  focusOnFieldName: PropTypes.string,
  modalVisible: PropTypes.bool,
  isGerman: PropTypes.bool,
  keyProperty: PropTypes.string,
  page: PropTypes.number,
  activeSort: PropTypes.bool,
  updateHeight: PropTypes.func, // adjusts the table container with a given height from a child component when child exceeds visible area
  rowIndex: PropTypes.number, // used for knowing the row index within the Table
  hasComments: PropTypes.bool,
  handleFocusAction: PropTypes.func,
  tableId: PropTypes.string,
  updatePropertyValue: PropTypes.func,
  onFastInlineEdit: PropTypes.func,
  navigationActive: PropTypes.bool,
};

export default TableRow;
