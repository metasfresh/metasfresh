import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import TableCell from './TableCell';

import {
    findRowByPropName
} from '../../actions/WindowActions';

class TableItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            edited: '',
            activeCell: '',
            updatedRow: false
        };
    }

    handleEditProperty = (e,property, callback) => {
        const { changeListenOnTrue, changeListenOnFalse } = this.props;

        if(
            !document.activeElement.className.includes('cell-disabled') ||
            document.activeElement.className.includes('cell-readonly')
        ) {
            this.setState({
                edited: property
            }, ()=>{
                if(callback){
                    const elem = document.activeElement.getElementsByClassName('js-input-field')[0];

                    if(elem){
                        elem.focus();
                    }

                    const disabled = document.activeElement.querySelector('.input-disabled');
                    const readonly = document.activeElement.querySelector('.input-readonly');

                    if(disabled || readonly) {
                        changeListenOnTrue();
                        this.handleEditProperty(e);

                    } else {
                        changeListenOnFalse();
                    }
                }
            })
        }


    }

    handleKey = (e, property) => {
        const elem = document.activeElement;
        const { changeListenOnTrue, changeListenOnFalse } = this.props;
        const { edited, activeCell} = this.state;

        if(!elem.className.includes('js-input-field')) {
          this.setState(Object.assign({}, this.state, {
              activeCell: elem
          }))
        }

        if(e.key === 'Enter' && !edited) {
            this.handleEditProperty(e,property, true);
        } else if (e.key === 'Enter' && edited) {
            this.handleEditProperty(e);
            changeListenOnTrue();
            activeCell && activeCell.focus();
        }
    }

    renderCells = (cols, cells) => {
        const {
            type, docId, rowId, tabId,readonly, mainTable, newRow, changeListenOnTrue,
            tabIndex, entity
        } = this.props;

        const {
            edited, updatedRow
        } = this.state;

        //iterate over layout settings
        return cols && cols.map((item, index) => {
            const property = item.fields[0].field;
            let widgetData = item.fields.map(property => findRowByPropName(cells, property.field));

            return (
                <TableCell
                    entity={entity}
                    type={type}
                    docId={docId}
                    rowId={rowId}
                    tabId={tabId}
                    item={item}
                    readonly={readonly}
                    key={index}
                    widgetData={widgetData}
                    isEdited={edited === property}
                    onDoubleClick={(e) => this.handleEditProperty(e,property, true)}
                    onClickOutside={(e) => {this.handleEditProperty(e); changeListenOnTrue()}}
                    disableOnClickOutside={edited !== property}
                    onKeyDown = {!mainTable ? (e) => this.handleKey(e, property) : ''}
                    updatedRow={updatedRow || newRow}
                    updateRow={this.updateRow}
                    tabIndex={tabIndex}
                />
            )
        })
    }

    updateRow = () => {
        this.setState(
            Object.assign({}, this.state, {
                updatedRow: true
            }), () => {
                setTimeout(() => {
                    this.setState(Object.assign({}, this.state, {
                        updatedRow: false
                    }))
                }, 1000);
            }
        )
    }

    nestedSelect = (elem, cb) => {
        let res = [];

        elem && elem.map(item => {
            res = res.concat([item.id]);

            if(item.includedDocuments){
                res = res.concat(this.nestedSelect(item.includedDocuments));
            }else{
                cb && cb();
            }
        })

        return res;
    }

    handleIndentSelect = (e, id, elem) => {
        const {handleSelect} = this.props;
        e.stopPropagation();
        handleSelect(this.nestedSelect(elem).concat([id]));
    }

    renderTree = (huType) => {
        const {
            indent, lastSibling, includedDocuments, indentSupported, rowId
        } = this.props;

        let indentation = [];

        for(let i = 0; i < indent.length; i++){
            indentation.push(
                <div
                    key={i}
                    className="indent-item-mid"
                >
                    {i === indent.length - 1 && <div className="indent-mid"/>}
                    <div
                        className={
                            (indent[i] ? 'indent-sign ' : '') +
                            ((lastSibling && i === indent.length - 1) ? 'indent-sign-bot ' : '')
                        }
                    />
                </div>
            )
        }

        if(indentSupported){
            return (
                <div
                    className="indent"
                    onClick={(e) => this.handleIndentSelect(e, rowId, includedDocuments)}
                >
                    {indentation}

                    {includedDocuments && <div className="indent-bot"/>}

                    <div
                        className="indent-icon"
                    >
                        {huType == 'LU' && <i className="meta-icon-palette"/>}
                        {huType == 'TU' && <i className="meta-icon-package"/>}
                        {huType == 'CU' && <i className="meta-icon-product"/>}
                    </div>
                </div>
            );
        }else{
            return false;
        }
    }

    render() {
        const {
            isSelected, fields, selectedProducts, rowId, cols,
            onMouseDown, onDoubleClick, included, tabid, type, docId,
            tabIndex, mainTable, entity, readonly, indent, odd,
            handleRightClick, indentSupported, contextType
        } = this.props;

        return (
            <tr
                onClick={onMouseDown}
                onDoubleClick={onDoubleClick}
                onContextMenu={handleRightClick}
                className={
                    (isSelected ? 'row-selected ' : '') +
                    (odd ? 'tr-odd ': '') +
                    (!odd ? 'tr-even ': '')
                }
            >
                {indentSupported &&
                    <td className="indented">
                        {this.renderTree(contextType)}
                    </td>
                }
                {this.renderCells(cols, fields)}
            </tr>
        );
    }
}

TableItem.propTypes = {
    dispatch: PropTypes.func.isRequired
};

TableItem = connect()(TableItem)

export default TableItem
