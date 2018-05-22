import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import classnames from 'classnames';

import {
  VIEW_EDITOR_RENDER_MODES_ALWAYS,
  VIEW_EDITOR_RENDER_MODES_ON_DEMAND,
} from '../../constants/Constants';
import TableCell from './TableCell';
import { shouldRenderColumn } from './Table';

class TableItem extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      edited: '',
      activeCell: '',
      updatedRow: false,
      listenOnKeys: true,
    };
  }

  initPropertyEditor = fieldName => {
    const { cols, fieldsByName } = this.props;

    if (cols && fieldsByName) {
      cols.map(item => {
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
    }
  };

  handleEditProperty = (e, property, callback, item) => {
    const { activeCell } = this.state;
    const elem = document.activeElement;

    if (activeCell !== elem && !elem.className.includes('js-input-field')) {
      this.setState({
        activeCell: elem,
      });
    }

    this.editProperty(e, property, callback, item);
  };

  prepareWidgetData = item => {
    const { fieldsByName } = this.props;
    const widgetData = item.fields.map(prop => fieldsByName[prop.field]);

    return widgetData;
  };

  editProperty = (e, property, callback, item) => {
    const { changeListenOnTrue, changeListenOnFalse } = this.props;

    if (item ? !item.readonly : true) {
      this.setState(
        {
          edited: property,
        },
        () => {
          if (callback) {
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
              changeListenOnTrue();
              this.handleEditProperty(e);
            } else {
              changeListenOnFalse();
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

  closeTableField = e => {
    const { activeCell } = this.state;

    this.handleEditProperty(e);
    this.listenOnKeysTrue();

    activeCell && activeCell.focus();
  };

  isAllowedFieldEdit = item => {
    return item.viewEditorRenderMode === VIEW_EDITOR_RENDER_MODES_ON_DEMAND;
  };

  renderCells = (cols, cells) => {
    const {
      type,
      docId,
      rowId,
      tabId,
      readonly,
      mainTable,
      newRow,
      changeListenOnTrue,
      tabIndex,
      entity,
      getSizeClass,
      onRightClick,
      caption,
      colspan,
      onItemChange,
      viewId,
    } = this.props;

    const { edited, updatedRow, listenOnKeys } = this.state;

    // Iterate over layout settings

    if (colspan) {
      return <td colSpan={cols.length}>{caption}</td>;
    } else {
      return (
        cols &&
        cols.map(item => {
          if (shouldRenderColumn(item)) {
            const { supportZoomInto } = item.fields[0];
            const supportFieldEdit = mainTable && this.isAllowedFieldEdit(item);
            const property = item.fields[0].field;
            let isEditable =
              ((cells &&
                cells[property] &&
                cells[property].viewEditorRenderMode) ||
                item.viewEditorRenderMode) === VIEW_EDITOR_RENDER_MODES_ALWAYS;

            let widgetData = item.fields.map(prop => {
              if (cells) {
                let cellWidget = cells[prop.field] || -1;

                if (
                  isEditable ||
                  (supportFieldEdit && typeof cellWidget === 'object')
                ) {
                  cellWidget = {
                    ...cellWidget,
                    widgetType: item.widgetType,
                    displayed: true,
                    mandatory: true,
                    readonly: false,
                  };
                }

                return cellWidget;
              }

              return -1;
            });

            return (
              <TableCell
                {...{
                  getSizeClass,
                  entity,
                  type,
                  docId,
                  rowId,
                  tabId,
                  item,
                  readonly,
                  widgetData,
                  tabIndex,
                  listenOnKeys,
                  caption,
                  mainTable,
                  viewId,
                }}
                key={`${rowId}-${property}`}
                isRowSelected={this.props.isSelected}
                isEdited={isEditable || edited === property}
                onDoubleClick={e =>
                  this.handleEditProperty(e, property, true, widgetData[0])
                }
                onClickOutside={e => {
                  this.handleEditProperty(e);
                  changeListenOnTrue();
                }}
                disableOnClickOutside={edited !== property}
                onCellChange={onItemChange}
                updatedRow={updatedRow || newRow}
                updateRow={this.updateRow}
                onKeyDown={e => this.handleKeyDown(e, property, widgetData[0])}
                listenOnKeysTrue={this.listenOnKeysTrue}
                listenOnKeysFalse={this.listenOnKeysFalse}
                closeTableField={e => this.closeTableField(e)}
                onRightClick={e =>
                  onRightClick(e, property, supportZoomInto, supportFieldEdit)
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
    const { onSelect } = this.props;
    e.stopPropagation();
    onSelect(this.nestedSelect(elem).concat([id]));
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
      onRowCollapse,
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
        {includedDocuments &&
          !collapsed && (
            <div
              className={classnames('indent-bot', {
                'indent-collapsible-bot': collapsible,
              })}
            />
          )}
        {includedDocuments && collapsible ? (
          collapsed ? (
            <i
              onClick={onRowCollapse}
              className="meta-icon-plus indent-collapse-icon"
            />
          ) : (
            <i
              onClick={onRowCollapse}
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
      fieldsByName,
      cols,
      onClick,
      onDoubleClick,
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
        onClick={onClick}
        onDoubleClick={onDoubleClick}
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
        {indentSupported &&
          indent && (
            <td className="indented">{this.renderTree(contextType)}</td>
          )}
        {this.renderCells(cols, fieldsByName)}
      </tr>
    );
  }
}

TableItem.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

export default connect(false, false, false, { withRef: true })(TableItem);
