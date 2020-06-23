import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import ReactDOM from 'react-dom';
import classnames from 'classnames';
import { merge } from 'lodash';

import {
  VIEW_EDITOR_RENDER_MODES_ALWAYS,
  VIEW_EDITOR_RENDER_MODES_ON_DEMAND,
} from '../../constants/Constants';
import { shouldRenderColumn, getIconClassName } from '../../utils/tableHelpers';

import TableCell from './TableCell';
import WithMobileDoubleTap from '../WithMobileDoubleTap';

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
      this.focusCell();
    }
  }

  componentDidMount() {
    const { focusOnFieldName, isSelected } = this.props;

    if (focusOnFieldName && isSelected && this.autofocusCell) {
      // eslint-disable-next-line react/no-find-dom-node
      ReactDOM.findDOMNode(this.autofocusCell).focus();
    }
  }

  isAllowedFieldEdit = (item) =>
    item.viewEditorRenderMode === VIEW_EDITOR_RENDER_MODES_ON_DEMAND;

  isEditableOnDemand = (item) => {
    const { fieldsByName } = this.props;
    const property = item.fields ? item.fields[0].field : item.field;

    return (
      (fieldsByName &&
        fieldsByName[property] &&
        fieldsByName[property].viewEditorRenderMode ===
          VIEW_EDITOR_RENDER_MODES_ON_DEMAND) ||
      item.viewEditorRenderMode === VIEW_EDITOR_RENDER_MODES_ON_DEMAND
    );
  };

  isCellEditable = (item) => {
    const { fieldsByName: cells } = this.props;
    const property = item.fields[0].field;
    let isEditable =
      (cells &&
        cells[property] &&
        cells[property].viewEditorRenderMode ===
          VIEW_EDITOR_RENDER_MODES_ALWAYS) ||
      item.viewEditorRenderMode === VIEW_EDITOR_RENDER_MODES_ALWAYS;

    isEditable = item.widgetType === 'Color' ? false : isEditable;

    return isEditable;
  };

  prepareWidgetData = (item) => {
    const { fieldsByName } = this.props;
    const widgetData = item.fields.map((prop) => fieldsByName[prop.field]);

    return widgetData;
  };

  initPropertyEditor = (fieldName) => {
    const { cols, fieldsByName } = this.props;

    if (cols && fieldsByName) {
      cols.map((item) => {
        const property = item.fields[0].field;
        if (property === fieldName) {
          const widgetData = this.prepareWidgetData(item);

          if (widgetData) {
            this.handleEditProperty(null, property, true, widgetData[0]);
          }
        }
      });
    }
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

  focusCell = (property, cb) => {
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

  handleEditProperty = (e, property, focus, item, select) => {
    this.focusCell(property, () => {
      this.editProperty(e, property, focus, item, select);
    });
  };

  editProperty = (e, property, focus, item, select) => {
    if (item ? !item.readonly : true) {
      if (this.state.edited === property && e) e.stopPropagation();

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

  closeTableField = (e) => {
    const { activeCell } = this.state;

    this.handleEditProperty(e);
    this.listenOnKeysTrue();

    activeCell && activeCell.focus();
  };

  handleCellExtend = (selected) => {
    this.setState({
      cellsExtended: selected,
    });
  };

  getWidgetData = (item, isEditable, supportFieldEdit) => {
    const { fieldsByName: cells } = this.props;

    const widgetData = item.fields.reduce((result, prop) => {
      if (cells) {
        let cellWidget = cells[prop.field] || null;

        if (
          isEditable ||
          (supportFieldEdit && typeof cellWidget === 'object')
        ) {
          cellWidget = {
            ...cellWidget,
            widgetType: item.widgetType,
            displayed: true,
            readonly: false,
          };
        } else {
          cellWidget = {
            ...cellWidget,
            readonly: true,
          };
        }

        if (cellWidget) {
          result.push(cellWidget);
        }
      }
      return result;
    }, []);

    if (widgetData.length) {
      return widgetData;
    }

    return [{}];
  };

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

  nestedSelect = (elem, cb) => {
    let res = [];

    elem &&
      elem.map((item) => {
        res = res.concat([item.id]);

        if (item.includedDocuments) {
          res = res.concat(this.nestedSelect(item.includedDocuments));
        } else {
          cb && cb();
        }
      });

    return res;
  };

  handleIndentSelect = (e) => {
    const { handleSelect, rowId, includedDocuments } = this.props;

    e.stopPropagation();
    handleSelect(this.nestedSelect(includedDocuments).concat([rowId]));
  };

  handleRowCollapse = () => {
    const { item, collapsed, onRowCollapse } = this.props;

    onRowCollapse(item, collapsed);
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
            const supportFieldEdit = mainTable && this.isAllowedFieldEdit(item);
            const property = item.fields[0].field;
            const isEditable = this.isCellEditable(item);
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
                onCellChange={this.handleCellValueChange}
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
