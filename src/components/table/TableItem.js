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
            edited: ""
        };
    }
    handleEditProperty = (e,property) => {
        e.preventDefault();

        this.setState({
            edited: property
        })
    }
    renderCells = (cols, cells) => {
        const { type, docId, rowId, tabId } = this.props;
        const { edited } = this.state;

        //iterate over layout settings
        return cols.map((item, index) => {
            const property = item.fields[0].field;
            const widgetData = findRowByPropName(cells, property);

            return (
                <TableCell
                    type={type}
                    docId={docId}
                    rowId={rowId}
                    tabId={tabId}
                    item={item}
                    key={index}
                    widgetData={widgetData}
                    isEdited={edited === property}
                    onDoubleClick={(e) => this.handleEditProperty(e,property)}
                    onClickOutside={(e) => this.handleEditProperty(e)}
                    disableOnClickOutside={edited !== property}
                />
            )
        })
    }
    render() {
        const {isSelected, fields, selectedProducts, onClick, onContextMenu, rowId, cols} = this.props;
        return (
            <tr
                onClick={onClick}
                className={isSelected ? "row-selected" : null}
            >
                {this.renderCells(cols, fields)}
            </tr>
        )
    }
}

TableItem.propTypes = {
    dispatch: PropTypes.func.isRequired
};

TableItem = connect()(TableItem)

export default TableItem
