import React, { Component, PropTypes } from 'react';
import TableItem from './TableItem';

class TableItemWrapper extends Component {
    constructor(props){
        super(props);
    }
    render() {
        const {
            included, item, entity, tabid, cols, type, docId, isSelected, onDoubleClick,
            handleClick, handleRightClick, changeListenOnTrue, changeListenOnFalse,
            newRow, tabIndex, readonly, mainTable
        } = this.props;

        return (
            <tbody>
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
                    isSelected={isSelected}
                    onDoubleClick={() => onDoubleClick && onDoubleClick()}
                    onMouseDown={(e) => handleClick && handleClick(e)}
                    onContextMenu={(e) => handleRightClick && handleRightClick(e)}
                    changeListenOnTrue={() => changeListenOnTrue && changeListenOnTrue()}
                    changeListenOnFalse={() => changeListenOnFalse && changeListenOnFalse()}
                    newRow={newRow}
                />
            </tbody>
        );
    }
}
export default TableItemWrapper;
