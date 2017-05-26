import React, { Component } from 'react';
import PropTypes from 'prop-types';
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
            updatedRow: false,
            listenOnKeys: true
        };
    }

    handleEditProperty = (e, property, callback) => {
        const { activeCell } = this.state;
        const elem = document.activeElement;

        if(activeCell !== elem && !elem.className.includes('js-input-field')) {
            this.setState({
                activeCell: elem
            })
        }

        this.editProperty(e, property, callback);
    }

    editProperty = (e, property, callback) => {
        const { changeListenOnTrue, changeListenOnFalse } = this.props;
        if(
            !document.activeElement.className.includes('cell-disabled') ||
            document.activeElement.className.includes('cell-readonly')
        ) {
            this.setState({
                edited: property
            }, ()=>{
                if(callback){
                    const elem =
                        document
                            .activeElement
                            .getElementsByClassName('js-input-field')[0];

                    if(elem){
                        elem.focus();
                    }

                    const disabled =
                        document.activeElement.querySelector('.input-disabled');
                    const readonly =
                        document.activeElement.querySelector('.input-readonly');

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

    listenOnKeysTrue = () => {
        this.setState({
            listenOnKeys: true
        });
    }

    listenOnKeysFalse = () => {
        this.setState({
            listenOnKeys: false
        });
    }

    handleKey = (e, property) => {
        const { edited, listenOnKeys} = this.state;
        if(listenOnKeys){
            if(e.key === 'Enter' && !edited) {
                this.handleEditProperty(e, property, true);
            } else if (e.key === 'Enter' && edited) {
                this.closeTableField(e);
            }
        }
    }

    closeTableField = (e) => {
        const { changeListenOnTrue } = this.props;
        const {activeCell} = this.state;
        this.handleEditProperty(e);
        this.listenOnKeysTrue();
        changeListenOnTrue();
        activeCell && activeCell.focus();
    }

    renderCells = (cols, cells) => {
        const {
            type, docId, rowId, tabId, readonly, mainTable, newRow,
            changeListenOnTrue, tabIndex, entity, getSizeClass,
            handleRightClick
        } = this.props;

        const {
            edited, updatedRow, listenOnKeys
        } = this.state;

        // Iterate over layout settings
        return cols && cols.map((item, index) => {
            const property = item.fields[0].field;
            const widgetData = item.fields.map(property =>
                findRowByPropName(cells, property.field)
            );
            const {supportZoomInto} = item.fields[0];

            return (
                <TableCell
                    {...{getSizeClass, entity, type, docId, rowId, tabId, item,
                        readonly, widgetData, tabIndex, listenOnKeys }}
                    key={index}
                    isEdited={edited === property}
                    onDoubleClick={(e) =>
                        this.handleEditProperty(e, property, true)
                    }
                    onClickOutside={(e) => {
                        this.handleEditProperty(e); changeListenOnTrue()}
                    }
                    disableOnClickOutside={edited !== property}
                    onKeyDown = {!mainTable ?
                        (e) => this.handleKey(e, property) : ''
                    }
                    updatedRow={updatedRow || newRow}
                    updateRow={this.updateRow}
                    listenOnKeysFalse={this.listenOnKeysFalse}
                    closeTableField={(e) => this.closeTableField(e)}
                    handleRightClick={(e) =>
                        handleRightClick(e, supportZoomInto ? property : null)}
                />
            )
        })
    }

    updateRow = () => {
        this.setState({
                updatedRow: true
            }, () => {
                setTimeout(() => {
                    this.setState({
                        updatedRow: false
                    })
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

    getIconClassName = (huType) => {
        switch(huType){
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
        }
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
                            ((lastSibling && i === indent.length - 1) ?
                                'indent-sign-bot ' : ''
                            )
                        }
                    />
                </div>
            )
        }

        if(indentSupported){
            return (
                <div
                    className="indent"
                    onClick={(e) =>
                        this.handleIndentSelect(e, rowId, includedDocuments)
                    }
                >
                    {indentation}

                    {includedDocuments && <div className="indent-bot"/>}

                    <div
                        className="indent-icon"
                    >
                        <i className={this.getIconClassName(huType)} />
                    </div>
                </div>
            );
        }else{
            return false;
        }
    }

    render() {
        const {
            isSelected, fields, cols, onMouseDown, onDoubleClick, odd,
            indentSupported, contextType, item, lastSibling,
            includedDocuments, notSaved
        } = this.props;

        return (
            <tr
                onClick={onMouseDown}
                onDoubleClick={onDoubleClick}
                className={
                    (isSelected ? 'row-selected ' : '') +
                    (odd ? 'tr-odd ': 'tr-even ') +
                    (processed ? 'row-disabled ': '') +
                    ((processed && lastSibling && !includedDocuments) ?
                        'row-boundary ': ''
                    ) +
                    (notSaved ? 'row-not-saved ': '')
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
