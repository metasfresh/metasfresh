import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import ReactDOM from 'react-dom';
import classnames from 'classnames';

import {
  shouldRenderColumn,
  getIconClassName,
  prepareWidgetData,
  isEditableOnDemand,
  isCellEditable,
  getCellWidgetData,
} from '../../utils/tableHelpers';

import TableCell from './TableCell';
import WithMobileDoubleTap from '../WithMobileDoubleTap';

/**
 * @file Class based component.
 * @module TableItem
 * @extends PureComponent
 */
class TableItem extends PureComponent {
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

  initPropertyEditor = (fieldName) => {
    const { cols, fieldsByName } = this.props;

    if (cols && fieldsByName) {
      cols.map((item) => {
        const property = item.fields[0].field;
        if (property === fieldName) {
          const widgetData = prepareWidgetData(item, fieldsByName);

          if (widgetData) {
            this.handleEditProperty(null, property, true, widgetData[0]);
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

  handleDoubleClick = () => {
    const { rowId, onDoubleClick, supportOpenRecord } = this.props;

    if (supportOpenRecord) {
      onDoubleClick && onDoubleClick(rowId);
    }
  };

  handleKeyDown = (e, property, widgetData) => {
    const { changeListenOnTrue } = this.props;
    const { listenOnKeys, edited } = this.state;

    switch (e.key) {
      case 'Enter':
        if (listenOnKeys) {
          this.handleEditProperty(e, property, true, widgetData);
        }
        break;
      case 'Tab':
      case 'Escape':
        if (edited === property) {
          e.stopPropagation();
          this.handleEditProperty(e);
          changeListenOnTrue();
        }
        break;
      default: {
        const inp = String.fromCharCode(e.keyCode);
        if (/[a-zA-Z0-9]/.test(inp) && !e.ctrlKey && !e.altKey) {
          this.listenOnKeysTrue();

          this.handleEditProperty(e, property, true, widgetData, true);
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
   */
  handleEditProperty = (e, property, focus, item, select) => {
    this._focusCell(property, () => {
      this._editProperty(e, property, focus, item, select);
    });
  };

  /**
   * @method _editProperty
   * @summary Depending on the params provided, sets or resets edited cell
   * and focuses the cell dom element enabling/disabling key listeners
   *
   * @param {object} e - event
   * @param {object} [property] - field name
   * @param {boolean} [focus] - flag if cell's widget should be focused
   * @param {object} [item] - widget data object
   * @param {boolean} [select] - flag if selected cell should be cleared
   */
  _editProperty = (e, property, focus, item, select) => {
    if (item ? !item.readonly : true) {
      if (this.state.edited === property && e) e.stopPropagation();

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
              this.handleEditProperty(e);
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
   * @method nestedSelect
   * @summary selects row and it's descendants
   *
   * @param {object} e - event
   */
  handleIndentSelect = (e) => {
    e.stopPropagation();
    const { handleSelect, rowId, includedDocuments } = this.props;

    handleSelect(this.nestedSelect(includedDocuments).concat([rowId]));
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

    this.handleEditProperty(e);
    this.listenOnKeysTrue();

    activeCell && activeCell.focus();
  };

  /**
   * @method getWidgetData
   * @summary Call the helper `getCellWidgetData` and provide cells data.
   *
   * @param {object} item - widget data object
   * @param {boolean} isEditable - flag if cell is editable
   * @param {boolean} supportfieldEdit - flag if selected cell can be editable
   */
  getWidgetData = (item, isEditable, supportFieldEdit) => {
    const { fieldsByName: cells } = this.props;

    return getCellWidgetData(cells, item, isEditable, supportFieldEdit);
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
   * @method nestedSelect
   * @summary Recursive fn to get row and it's descendants
   *
   * @param {array} elem - row element
   */
  nestedSelect = (elem) => {
    let res = [];

    elem &&
      elem.map((item) => {
        res = res.concat([item.id]);

        if (item.includedDocuments) {
          res = res.concat(this.nestedSelect(item.includedDocuments));
        }
      });

    return res;
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
      fieldsByName: cells,
      /*
       * This function is called when cell's value changes and triggers re-fetching
       * quickactions in grids.
       */
      onItemChange,
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
        cols.map((item) => {
          if (shouldRenderColumn(item)) {
            const { supportZoomInto } = item.fields[0];
            const supportFieldEdit =
              mainTable && isEditableOnDemand(item, cells);
            const property = item.fields[0].field;
            const isEditable = isCellEditable(item, cells);
            const isEdited = edited === property;
            const extendLongText = multilineText ? multilineTextLines : 0;
            const widgetData = this.getWidgetData(
              item,
              isEditable,
              supportFieldEdit
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
                  isEditable,
                  supportZoomInto,
                  supportFieldEdit,
                  handleRightClick,
                  keyProperty,
                  modalVisible,
                  isGerman,
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
                tdValue={
                  widgetData[0].value
                    ? JSON.stringify(widgetData[0].value)
                    : null
                }
                getWidgetData={this.getWidgetData}
                cellExtended={cellsExtended}
                key={`${rowId}-${property}`}
                isRowSelected={isSelected}
                isEdited={isEdited}
                handleDoubleClick={this.handleEditProperty}
                onClickOutside={this.handleClickOutside}
                onCellChange={onItemChange}
                updatedRow={updatedRow || newRow}
                updateRow={this.updateRow}
                handleKeyDown={this.handleKeyDown}
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
          className={classnames(dataKey, `row-${keyProperty}`, {
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

TableItem.propTypes = {
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
  changeListenOnTrue: PropTypes.func,
  onRowCollapse: PropTypes.func,
  handleRightClick: PropTypes.func,
  fieldsByName: PropTypes.object,
  indent: PropTypes.array,
  rowId: PropTypes.string,
  onItemChange: PropTypes.func,
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
};

export default TableItem;
