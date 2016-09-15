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
            edited: "",
            activeCell: ""
        };
    }
    handleEditProperty = (e,property, callback) => {
        e.preventDefault();
        this.setState({
            edited: property
        }, ()=>{
          if(callback){
            // e.target
            let elem = document.activeElement.getElementsByClassName('js-input-field')[0];
            if(elem){
              elem.focus();
            }

          }
        })
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

        if(e.key === "Enter" && !edited) {
            this.handleEditProperty(e,property, true);
            changeListenOnFalse();
        } else if (e.key === "Enter" && edited) {
            this.handleEditProperty(e);
            changeListenOnTrue();
            activeCell.focus();
        }
    }

    renderCells = (cols, cells) => {
        const { type, docId, rowId, tabId,readonly } = this.props;
        const { edited } = this.state;

        //iterate over layout settings
        return cols.map((item, index) => {
            const property = item.fields[0].field;
            let widgetData = item.fields.map(property => findRowByPropName(cells, property.field));

            return (
                <TableCell
                    type={type}
                    docId={docId}
                    rowId={rowId}
                    tabId={tabId}
                    item={item}
                    readonly={readonly}
                    key={index}
                    widgetData={widgetData}
                    isEdited={edited === property}
                    onDoubleClick={(e) => this.handleEditProperty(e,property)}
                    onClickOutside={(e) => this.handleEditProperty(e)}
                    disableOnClickOutside={edited !== property}
                    onKeyDown = {(e) => this.handleKey(e, property)}
                />
            )
        })
    }
    render() {
        const {isSelected, fields, selectedProducts, onContextMenu, rowId, cols, onMouseDown} = this.props;
        return (
            <tr
                onContextMenu = {onContextMenu}
                onMouseDown ={onMouseDown}
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
