import React, { PureComponent } from 'react';
import ReactDOM from 'react-dom';
import classnames from 'classnames';
import { F2_KEY } from '../../constants/Constants';
import {
  shouldRenderColumn,
  getIconClassName,
  prepareWidgetData,
  isEditableOnDemand,
  isCellEditable,
  tableRowPropTypes,
  getCellWidgetData,
  getDescription,
  getTdValue,
  nestedSelect,
} from '../../utils/tableHelpers';

import TableCell from './TableCell';
import WithMobileDoubleTap from '../WithMobileDoubleTap';

/**
 * @file Class based component.
 * @module TableRow
 * @extends PureComponent
 */
class TableRow extends PureComponent {
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
      listenOnKeys: true,
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
  }

  /**
   * @method initPropertyEditor
   * @summary Initialize the editor for an item
   * @param {object} fieldName - the name of the field, mark - marks the text(like when you click and hold and select the text)
   */
  initPropertyEditor = ({ fieldName, mark }) => {
    const { cols, fieldsByName } = this.props;

    this.setState({ valueBeforeEditing: fieldsByName[fieldName].value });

    if (cols && fieldsByName) {
      cols.map((item) => {
        const property = item.fields[0].field;
        if (property === fieldName) {
          const widgetData = prepareWidgetData(item, fieldsByName);

          if (widgetData) {
            this.handleEditProperty(
              null,
              property,
              true,
              widgetData[0],
              false,
              mark
            );
          }
        }
      });
    }
  };

  listenOnKeysTrue = () => {
    const { changeListenOnTrue } = this.props;

    this.setState({
      listenOnKeys: true,
    });
    changeListenOnTrue();
  };

  listenOnKeysFalse = () => {
    const { changeListenOnFalse } = this.props;

    this.setState({
      listenOnKeys: false,
    });
    changeListenOnFalse();
  };

  handleClick = (e) => {
    const { onClick, item } = this.props;

    onClick(e, item);
  };

  handleClickOutside = (e) => {
    const { changeListenOnTrue } = this.props;

    this.selectedCell && this.selectedCell.clearValue(true);
    this.handleEditProperty(e);

    changeListenOnTrue();
  };

  handleDoubleClick = (e) => {
    const { rowId, onDoubleClick, supportOpenRecord } = this.props;

    this.setState({ valueBeforeEditing: e.target.textContent });

    if (supportOpenRecord) {
      onDoubleClick && onDoubleClick(rowId);
    }
  };

  handleKeyDown = (e, property, readonly) => {
    const {
      changeListenOnTrue,
      rowId,
      tabId,
      entity,
      modalVisible,
      tableId,
      updatePropertyValue,
      fieldsByName,
      onFastInlineEdit,
    } = this.props;

    const inputContent = e.target.value;

    const { listenOnKeys, edited, valueBeforeEditing } = this.state;
    switch (e.key) {
      case 'Enter':
        if (listenOnKeys) {
          this.handleEditProperty({
            event: e,
            property,
            focus: true,
            readonly,
          });
          this.setState({ valueBeforeEditing: e.target.textContent });
        }
        updatePropertyValue({
          property,
          value: inputContent,
          tabId,
          rowId,
          isModal: modalVisible,
          entity,
          tableId,
        });
        break;
      case 'Tab':
        updatePropertyValue({
          property,
          value: e.target.value,
          tabId,
          rowId,
          isModal: modalVisible,
          entity,
          tableId,
        });
        e.stopPropagation();
        this.handleEditProperty(e);
        break;
      case 'Escape':
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
          e.stopPropagation();
          this.handleEditProperty({ event: e });
          changeListenOnTrue();
          this.setState({ valueBeforeEditing: null });
        }
        break;
      default: {
        valueBeforeEditing === null &&
          this.setState({ valueBeforeEditing: fieldsByName[property].value });

        const inp = String.fromCharCode(e.keyCode);
        if (/[a-zA-Z0-9]/.test(inp) && !e.ctrlKey && !e.altKey) {
          if (e.keyCode === F2_KEY) {
            onFastInlineEdit();
            return false;
          }
          this.listenOnKeysTrue();

          this.handleEditProperty({
            event: e,
            property,
            focus: true,
            readonly,
            select: true,
          });
        }
        break;
      }
    }
  };

  /**
   * @method handleEditProperty
   * @summary focuses and sets the cell as edited or clears the edited cell
   * if optional params are not provided
   *
   * @param {object} e - event
   * @param {object} [property] - field name
   * @param {boolean} [focus] - flag if cell should be focused
   * @param {object} [item] - widget data object
   * @param {boolean} [select] - flag if selected cell should be cleared
   * @param {boolean} [mark] - marks the text(like when you click and hold and select the text)
   */
  handleEditProperty = (event, property, focus, readonly, select, mark) => {
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
   */
  _editProperty = ({ event, property, focus, readonly, select, mark }) => {
    if (typeof readonly === undefined ? !readonly : true) {
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
            const elem = document.activeElement.getElementsByClassName(
              'js-input-field'
            )[0];

            if (elem) {
              mark && elem.select();
              elem.focus();
            }

            const disabled = document.activeElement.querySelector(
              '.input-disabled'
            );
            const readonly = document.activeElement.querySelector(
              '.input-readonly'
            );

            if (disabled || readonly) {
              this.listenOnKeysTrue();
              this.handleEditProperty({ event });
            } else {
              this.listenOnKeysFalse();
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
    this.listenOnKeysTrue();

    activeCell && activeCell.focus();
  };

  /**
   * @method updateRow
   * @summary sets a flag to render row as edited to visualize an edit
   */
  updateRow = () => {
    this.setState(
      {
        updatedRow: true,
      },
      () => {
        setTimeout(() => {
          this.setState({
            updatedRow: false,
          });
        }, 1000);
      }
    );
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
    const { activeCell } = this.state;
    const elem = document.activeElement;

    if (
      (activeCell !== elem && !elem.className.includes('js-input-field')) ||
      cb
    ) {
      this.setState(
        {
          activeCell: elem,
          activeCellName: property,
        },
        () => {
          cb && cb();
        }
      );
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
      fieldsByName: cells,
      hasComments,
      /*
       * This function is called when cell's value changes and triggers re-fetching
       * quickactions in grids.
       */
      onItemChange,
      handleFocusAction,
      tableId,
    } = this.props;
    const {
      edited,
      updatedRow,
      listenOnKeys,
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
              mainTable && isEditableOnDemand(item, cells);
            const property = item.fields[0].field;
            const isEditable = isCellEditable(item, cells);
            const isEdited = edited === property;
            const extendLongText = multilineText ? multilineTextLines : 0;
            const widgetData = getCellWidgetData(
              cells,
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
                key={`${rowId}-${property}`}
                isRowSelected={isSelected}
                handleDoubleClick={this.handleEditProperty}
                handleFocusAction={handleFocusAction}
                onClickOutside={this.handleClickOutside}
                onCellChange={onItemChange}
                updatedRow={updatedRow || newRow}
                updateRow={this.updateRow}
                onKeyDown={this.handleKeyDown}
                listenOnKeysTrue={this.listenOnKeysTrue}
                listenOnKeysFalse={this.listenOnKeysFalse}
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
    const {
      indent,
      lastChild,
      includedDocuments,
      collapsed,
      collapsible,
    } = this.props;

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

TableRow.propTypes = tableRowPropTypes;

export default TableRow;
