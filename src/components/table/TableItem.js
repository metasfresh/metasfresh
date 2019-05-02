import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import classnames from 'classnames';
import { merge } from 'lodash';

import {
  VIEW_EDITOR_RENDER_MODES_ALWAYS,
  VIEW_EDITOR_RENDER_MODES_ON_DEMAND,
} from '../../constants/Constants';
import TableCell from './TableCell';
import { shouldRenderColumn } from '../../utils/tableHelpers';

class TableItem extends PureComponent {
  constructor(props) {
    super(props);

    const multilineText = props.cols.filter(item => item.multilineText === true)
      .length;

    let multilineTextLines = 0;
    props.cols.forEach(col => {
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
      updatedRow: false,
      listenOnKeys: true,
      editedCells: {},
      multilineText,
      multilineTextLines,
    };
  }

  componentDidUpdate(prevProps) {
    const { multilineText } = this.state;

    if (multilineText && this.props.isSelected !== prevProps.isSelected) {
      this.handleCellExtend();
    }
  }

  isEditableOnDemand = item => {
    const { fieldsByName } = this.props;
    const { editedCells } = this.state;
    const cells = merge({}, fieldsByName, editedCells);
    const property = item.fields ? item.fields[0].field : item.field;
    // const property = item.field ? item.field : item.fields[0].field;

    // console.log('isEditableOnDemand: ', cells[property], item)

    return (
      (cells &&
        cells[property] &&
        cells[property].viewEditorRenderMode ===
          VIEW_EDITOR_RENDER_MODES_ON_DEMAND) ||
      item.viewEditorRenderMode === VIEW_EDITOR_RENDER_MODES_ON_DEMAND
    );
  };

  shouldShowWidget = item => {
    const { fieldsByName } = this.props;
    const { editedCells } = this.state;
    const cells = merge({}, fieldsByName, editedCells);
    // const property = item.field ? item.field : item.fields[0].field;
    const property = item.fields ? item.fields[0].field : item.field;

    return (
      (cells &&
        cells[property] &&
        cells[property].viewEditorRenderMode ===
          VIEW_EDITOR_RENDER_MODES_ALWAYS) ||
      item.viewEditorRenderMode === VIEW_EDITOR_RENDER_MODES_ALWAYS
    );
  };

  isAllowedFieldEdit = item =>
    item.viewEditorRenderMode === VIEW_EDITOR_RENDER_MODES_ON_DEMAND;

  getCell = colIdx => this.props.cols[colIdx];

  prepareWidgetData = item => {
    const { fieldsByName } = this.props;
    const widgetData = item.fields.map(prop => fieldsByName[prop.field]);

    return widgetData;
  };

  initPropertyEditor = fieldName => {
    const { cols, fieldsByName } = this.props;

    if (cols && fieldsByName) {
      cols.map((item, idx) => {
        const property = item.fields[0].field;
        if (property === fieldName) {
          const widgetData = this.prepareWidgetData(item);

          if (widgetData) {
            this.handleEditProperty(null, property, widgetData[0], idx);
          }
        }
      });
    }
  };

  handleEditProperty = (e, property, item) => {
    const { activeCell } = this.state;
    const elem = document.activeElement;
    let showWidget = false;

    if (item) {
      // const cell = this.getCell(col);
      // const property = cell.fields[0].field;
      const showOnEdit = this.shouldShowWidget(item);
      const isEditable = this.isEditableOnDemand(item);
      showWidget = showOnEdit || isEditable;
    }

    // @TODO: this won't be necessary ?
    if (activeCell !== elem && !elem.className.includes('js-input-field')) {
    // if (activeCell !== elem) {
      this.setState({
        activeCell: elem,
      });
    }
    // const { fieldsByName } = this.props;
    // const { editedCells } = this.state;
    // const cells = merge({}, fieldsByName, editedCells);
    // // const property = item.field ? item.field : item.fields[0].field;
    // // const property = item.fields ? item.fields[0].field : item.field;
    // console.log('TableItem hatdleEditProperty: ', showOnEdit, isEditable, item, cells[property]);
    this.editProperty(e, property, item, showWidget);
  };

  editProperty = (e, property, item, showWidget) => {
    // if (item ? !item.readonly : true) {
    if (showWidget) {
      if (this.state.edited === property) {
        console.log('stop propagation');
        e && e.stopPropagation();
      }
      console.log('ITEM: ', item);
      // console.log('EDIT PROPERTY: ', property, this.state.edited)
      this.setState(
        {
          edited: property,
        },
        () => {
          const elem = document.activeElement.getElementsByClassName(
            'js-input-field'
          )[0];

          // TODO: We need to focus attributes button if it exists
          console.log('INNER edit property: ', elem);

          // const disabled = elem && elem.className.includes('input-disabled');
          const disabled = elem && elem.hasAttribute('disabled');
          const readonly = elem && elem.className.includes('disabled');

          if (elem && !disabled && !readonly) {
            elem.focus();
            // console.log('ABAB');
            this.listenOnKeysFalse();
          }
          // if (disabled || readonly) {
          //   // this.listenOnKeysTrue();
          //   // this.handleEditProperty(e);
        }
      );
    }
  };

  handleDoubleClick = () => {
    const { rowId, onDoubleClick, supportOpenRecord } = this.props;

    if (supportOpenRecord) {
      onDoubleClick && onDoubleClick(rowId);
    }
  };
//   handleKeyDown = e => {
//     const { changeListenOnTrue, widgetData, property } = this.props;
  handleKeyDown = (e, property, widgetData) => {
    // const { changeListenOnTrue } = this.props;
    const { listenOnKeys, edited } = this.state;
    console.log('TableItem handleKeyDown: ', e.key, listenOnKeys, property, widgetData);

    switch (e.key) {
      case 'Enter':
        if (listenOnKeys) {
          console.log('TableItem handleKeyDown ENTER 1: ', property, widgetData)
          this.handleEditProperty(e, property, widgetData[0]);
        } else {
          console.log('TableItem handleKeyDown ENTER 2')
          this.listenOnKeysTrue();
        }
        break;
      case 'Tab':
      case 'Escape':
        if (edited === property) {
          e.stopPropagation();
          // @TODO This should be done differently
          this.handleEditProperty(e);
          // changeListenOnTrue();
          this.listenOnKeysTrue();
        }
        break;
    }
  };

  listenOnKeysTrue = () => {
    const { changeListenOnTrue } = this.props;

    console.log('TableItem listenOnKeysTrue');

    this.setState({
      listenOnKeys: true,
    });
    changeListenOnTrue();
  };

  listenOnKeysFalse = () => {
    const { changeListenOnFalse } = this.props;

    console.log('TableItem listenOnKeysFalse');

    this.setState({
      listenOnKeys: false,
    });
    changeListenOnFalse();
  };

  closeTableField = e => {
    const { activeCell } = this.state;

    // @TODO This should be done differently
    this.handleEditProperty(e);
    this.listenOnKeysTrue();

    activeCell && activeCell.focus();
  };

  /*
   * This function is called when cell's value changes
   */
  handleCellValueChange = (rowId, property, value, ret) => {
    const { onItemChange } = this.props;
    const editedCells = { ...this.state.editedCells };

    console.log('handleCellValueChange');

    // this is something we're not doing usually as all field
    // layouts come from the server. But in cases of modals
    // sometimes we need to modify the state of fields that are displayed
    // for instance to show/hide them
    if (ret) {
      ret.then(resp => {
        if (resp[0] && resp[0].fieldsByName) {
          const fields = resp[0].fieldsByName;

          for (let [k, v] of Object.entries(fields)) {
            editedCells[k] = v;
          }
        }

        this.setState(
          {
            editedCells,
          },
          () => onItemChange(rowId, property, value)
        );
      });
    } else {
      onItemChange(rowId, property, value);
    }
  };

  handleCellFocused = (e, property, widgetData) => {
    // console.log('ELEMENT: ', element);
    // const cell = this.getCell(col);
    // const property = cell.fields[0].field;
    // const showOnEdit = this.isEditableOnDemand(cell);
    console.log('TableItem handleCellFocused: ', property);//, showOnEdit, cell)
    this.handleEditProperty(e, property, widgetData[0]);
  };

  handleCellExtend = () => {
    this.setState({
      cellsExtended: !this.state.cellsExtended,
    });
  };

  handleClickOutside = e => {
    // const { changeListenOnTrue, rowId, isSelected } = this.props;
    console.log('handleclickoutside')
    // if (isSelected) {
    //   console.log('THIS: ', rowId, this);
    //   this.handleEditProperty(e);
    //   changeListenOnTrue();
    // } else {
    //   console.log('NOT SELECTED: ', rowId)
    // }
  };

  renderCells = () => {
    const {
      cols,
      fieldsByName,
      windowId,
      docId,
      rowId,
      tabId,
      mainTable,
      newRow,
      tabIndex,
      entity,
      getSizeClass,
      handleRightClick,
      caption,
      colspan,
      viewId,
      isSelected,
    } = this.props;
    const {
      edited,
      updatedRow,
      listenOnKeys,
      editedCells,
      cellsExtended,
      multilineText,
      multilineTextLines,
    } = this.state;
    const cells = merge({}, fieldsByName, editedCells);

    // Iterate over layout settings
    if (colspan) {
      return <td colSpan={cols.length}>{caption}</td>;
    } else {
      return (
        cols &&
        cols.map((item, colIdx) => {
          if (shouldRenderColumn(item)) {
            const { supportZoomInto } = item.fields[0];
            const property = item.fields[0].field;
            const extendLongText = multilineText ? multilineTextLines : 0;

            // @TODO: This should be replaced by showOnEdit only ?
            const supportFieldEdit = mainTable && this.isAllowedFieldEdit(item);
            let showOnEdit = this.isEditableOnDemand(item);
            let showWidget = this.shouldShowWidget(item);
            const isEdited = edited === property;

            let widgetData = item.fields.map(prop => {
              if (cells) {
                let cellWidget = cells[prop.field] || -1;

                if (
                  showOnEdit ||
                  // @TODO: Use showWidget and remove the second check ?
                  // (supportFieldEdit && typeof cellWidget === 'object')
                  showWidget
                ) {
                  cellWidget = {
                    ...cellWidget,
                    widgetType: item.widgetType,
                    displayed: true,
                    readonly: false,
                  };
                }
                return cellWidget;
              }
              return -1;
            });
            // HACK: Color fields should always be readonly
            if (item.widgetType === 'Color') {
              showWidget = false;
              showOnEdit = false;
            }
            // console.log('isEditable: ', isEditable, cells[property], item.viewEditorRenderMode, showWidget)

            return (
              <TableCell
                {...{
                  getSizeClass,
                  entity,
                  windowId,
                  docId,
                  rowId,
                  colIdx,
                  tabId,
                  item,
                  widgetData,
                  tabIndex,
                  listenOnKeys,
                  caption,
                  mainTable,
                  viewId,
                  extendLongText,
                  showWidget,
                  cellsExtended,
                  isEdited,
                  property,
                }}
                isEditable={showOnEdit}
                key={`${rowId}-${property}`}
                isRowSelected={isSelected}
                handleDoubleClick={e => {
                  this.handleEditProperty(e, property, widgetData[0]);
                }}
                onClickOutside={this.handleClickOutside}
                onCellChange={this.handleCellValueChange}
                onCellExtend={this.handleCellExtend}
                onCellFocused={this.handleCellFocused}
                updatedRow={updatedRow || newRow}
                updateRow={this.updateRow}
                handleKeyDown={this.handleKeyDown}
                listenOnKeysTrue={this.listenOnKeysTrue}
                listenOnKeysFalse={this.listenOnKeysFalse}
                closeTableField={e => this.closeTableField(e)}
                handleRightClick={e =>
                  handleRightClick(
                    e,
                    property,
                    supportZoomInto,
                    supportFieldEdit
                  )
                }
              />
            );
          }
        })
      );
    }
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
      elem.map(item => {
        res = res.concat([item.id]);

        if (item.includedDocuments) {
          res = res.concat(this.nestedSelect(item.includedDocuments));
        } else {
          cb && cb();
        }
      });

    return res;
  };

  handleIndentSelect = (e, id, elem) => {
    const { handleSelect } = this.props;
    e.stopPropagation();
    handleSelect(this.nestedSelect(elem).concat([id]));
  };

  getIconClassName = huType => {
    switch (huType) {
      case 'LU':
        return 'meta-icon-pallete';
      case 'TU':
        return 'meta-icon-package';
      case 'CU':
        return 'meta-icon-product';
      case 'PP_Order_Receive':
        return 'meta-icon-receipt';
      case 'PP_Order_Issue':
        return 'meta-icon-issue';
      case 'M_Picking_Slot':
        // https://github.com/metasfresh/metasfresh/issues/2298
        return 'meta-icon-beschaffung';
    }
  };

  renderTree = huType => {
    const {
      indent,
      lastChild,
      includedDocuments,
      rowId,
      collapsed,
      handleRowCollapse,
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
              onClick={handleRowCollapse}
              className="meta-icon-plus indent-collapse-icon"
            />
          ) : (
            <i
              onClick={handleRowCollapse}
              className="meta-icon-minus indent-collapse-icon"
            />
          )
        ) : (
          ''
        )}
        <div
          className="indent-icon"
          onClick={e => this.handleIndentSelect(e, rowId, includedDocuments)}
        >
          <i className={this.getIconClassName(huType)} />
        </div>
      </div>
    );
  };

  render() {
    const {
      key,
      isSelected,
      onClick,
      odd,
      indentSupported,
      indent,
      contextType,
      lastChild,
      processed,
      includedDocuments,
      notSaved,
      caption,
    } = this.props;

    return (
      <tr
        key={key}
        onClick={(e) => {
          console.log('TR click')
          onClick(e);
        }}
        onDoubleClick={this.handleDoubleClick}
        className={classnames({
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
    );
  }
}

TableItem.propTypes = {
  dispatch: PropTypes.func.isRequired,
  onClick: PropTypes.func.isRequired,
  handleSelect: PropTypes.func,
  onDoubleClick: PropTypes.func,
  indentSupported: PropTypes.bool,
  collapsed: PropTypes.bool,
  processed: PropTypes.bool,
  notSaved: PropTypes.bool,
  isSelected: PropTypes.bool,
  fieldsByName: PropTypes.object,
  cols: PropTypes.array,
  odd: PropTypes.number,
  key: PropTypes.string,
  indent: PropTypes.array,
  onItemChange: PropTypes.func,
  rowId: PropTypes.string,
};

export default connect(
  false,
  false,
  false,
  { withRef: true }
)(TableItem);
