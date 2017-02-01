import React, { Component, PropTypes } from 'react';
import TableItem from './TableItem';
import update from 'react-addons-update';

import {flattenLastElem} from '../../actions/MenuActions';

class TableItemWrapper extends Component {
    constructor(props){
        super(props);

        this.state = {
            rows: []
        }
    }

    componentDidMount(){
        const {item} = this.props;

        this.setState(Object.assign({}, this.state, {
            rows: this.mapIncluded(item)
        }))
    }

    componentDidUpdate(prevProps, prevState) {
        if(
            JSON.stringify(prevProps.item) !=
            JSON.stringify(this.props.item)
        ){
            this.setState(Object.assign({}, this.state, {
                rows: this.mapIncluded(this.props.item)
            }))
        }
    }

    mapIncluded = (node, indent, isParentLastChild = false) => {
        let ind = indent ? indent : [];
        let result = [];

        const nodeCopy = Object.assign({}, node, {
            indent: ind
        });

        result = result.concat([nodeCopy]);

        if(isParentLastChild){
            ind[ind.length - 2] = false;
        }

        if(node.includedDocuments){
            for(let i = 0; i < node.includedDocuments.length; i++){
                let copy = node.includedDocuments[i];
                if(i === node.includedDocuments.length - 1){
                    copy = Object.assign({}, copy, {
                        lastChild: true
                    });
                }

                result = result.concat(
                    this.mapIncluded(copy, ind.concat([true]), node.lastChild)
                )
            }
        }

        return result;
    }

    render() {
        const {
            item, entity, tabId, cols, type, docId, selected, onDoubleClick,
            handleClick, handleRightClick, changeListenOnTrue, changeListenOnFalse,
            newRow, tabIndex, readonly, mainTable, handleSelect, odd, keyProperty
        } = this.props;

        const {rows} = this.state;

        return (
            <tbody>
                {rows.map((row, index) =>
                    <TableItem
                        entity={entity}
                        fields={row.fields}
                        rowId={item.rowId}
                        tabId={tabId}
                        cols={cols}
                        type={type}
                        docId={docId}
                        tabIndex={tabIndex}
                        readonly={readonly}
                        mainTable={mainTable}
                        onDoubleClick={() => onDoubleClick && onDoubleClick(row[keyProperty])}
                        onMouseDown={(e) => handleClick && handleClick(e, row[keyProperty])}
                        handleRightClick={(e) => handleRightClick(e, row[keyProperty])}
                        changeListenOnTrue={() => changeListenOnTrue && changeListenOnTrue()}
                        changeListenOnFalse={() => changeListenOnFalse && changeListenOnFalse()}
                        newRow={newRow}
                        isSelected={selected.indexOf(row[keyProperty]) > -1}
                        key={index}
                        indent={row.indent}
                        includedDocuments={row.includedDocuments}
                        lastSibling={row.lastChild}
                        handleSelect={handleSelect}
                        odd={(odd && (rows.length & 1))}
                    />
                )}
            </tbody>
        );
    }
}
export default TableItemWrapper;
