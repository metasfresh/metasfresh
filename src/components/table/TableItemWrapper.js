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

    mapIncluded = (node, indent) => {
        let ind = indent ? indent : 0;
        let result = [];

        const nodeCopy = Object.assign({}, node, {
            indent: ind
        });

        result = result.concat([nodeCopy]);

        if(node.includedDocuments){
            node.includedDocuments.map(item => {
                result = result.concat(this.mapIncluded(item, ind + 1))
            })
        }

        return result;
    }

    render() {
        const {
            included, item, entity, tabid, cols, type, docId, isSelected, onDoubleClick,
            handleClick, handleRightClick, changeListenOnTrue, changeListenOnFalse,
            newRow, tabIndex, readonly, mainTable
        } = this.props;

        const {rows} = this.state;

        return (
            <tbody>
                {rows.map((item, index) =>
                    <TableItem
                        entity={entity}
                        fields={item.fields}
                        rowId={item.rowId}
                        tabId={tabid}
                        cols={cols}
                        type={type}
                        docId={docId}
                        tabIndex={tabIndex}
                        readonly={readonly}
                        mainTable={mainTable}
                        onDoubleClick={() => onDoubleClick && onDoubleClick()}
                        onMouseDown={(e) => handleClick && handleClick(e)}
                        onContextMenu={(e) => handleRightClick && handleRightClick(e)}
                        changeListenOnTrue={() => changeListenOnTrue && changeListenOnTrue()}
                        changeListenOnFalse={() => changeListenOnFalse && changeListenOnFalse()}
                        newRow={newRow}
                        isSelected={isSelected}
                        key={index}
                        indent={item.indent}
                    />
                )}
            </tbody>
        );
    }
}
export default TableItemWrapper;
