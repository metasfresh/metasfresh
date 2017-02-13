import React, { Component, PropTypes } from 'react';
import TableItem from './TableItem';
import update from 'react-addons-update';

import {flattenLastElem} from '../../actions/MenuActions';

class TableItemWrapper extends Component {
    constructor(props){
        super(props);
    }

    render() {
        const {
            item, entity, tabId, cols, type, docId, selected, onDoubleClick,
            handleClick, handleRightClick, changeListenOnTrue, changeListenOnFalse,
            newRow, tabIndex, readonly, mainTable, handleSelect, odd, keyProperty,
            indentSupported, rows
        } = this.props;

        console.log(rows);

        return (
            <tbody>
                {rows.map((row, index) =>
                    <TableItem
                        entity={entity}
                        fields={row.fields}
                        rowId={row[keyProperty]}
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
                        indentSupported={indentSupported}
                        indent={row.indent}
                        includedDocuments={row.includedDocuments}
                        lastSibling={row.lastChild}
                        handleSelect={handleSelect}
                        odd={(odd && (rows.length & 1))}
                        contextType={row.type}
                    />
                )}
            </tbody>
        );
    }
}
export default TableItemWrapper;
