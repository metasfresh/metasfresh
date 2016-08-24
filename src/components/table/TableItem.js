import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    saveProductProperty,
    editProductProperty,
    changeProductProperty,
    selectOneProduct
} from '../../actions/AppActions';

import {
    findRowByPropName
} from '../../actions/WindowActions';

class TableItem extends Component {
    constructor(props) {
        super(props);
    }
    handleEditProperty = (e) => {
    //     e.preventDefault();
    //     this.props.dispatch(selectOneProduct(this.props.product.id));
    //
    //     let property = e._targetInst._currentElement.key;
    //     let inputContainer = document.createDocumentFragment();
    //     let input = document.createElement("input");
    //     let td = e.nativeEvent.target;
    //     let span = td.getElementsByTagName("span")[0];
    //
    //     input.value = span.innerHTML;
    //     this.props.dispatch(editProductProperty(property));
    //
    //     input.classList.add('table-input-inline');
    //
    //     input.addEventListener('blur', (e) => {
    //         this.handleSaveProperty(e, property, input.value);
    //     });
    //
    //     inputContainer.appendChild(input);
    //     span ? span.classList.add('table-hide-property'): null;
    //     td.appendChild(inputContainer);
    //     input.focus();
    }
    handleSaveProperty = (e, property, value) => {
        // e.preventDefault();
        // let parent = e.target.parentElement;
        // let span = parent.getElementsByTagName("span")[0];
        // const {product} = this.props;
        //
        // this.props.dispatch(
        //     saveProductProperty(product.id, property, value)
        // )
        // parent.removeChild(e.target);
        // span ? span.classList.remove('table-hide-property') : null;
    }
    renderCells = (cols, cells) => {
        return cols.map((item, index) =>{
            const value = findRowByPropName(cells, item.fields[0].field).value;
            return (
                <td key={index}>
                    {value && value.toString()}
                </td>
            )
        })
    }
    render() {
        const {fields, selectedProducts, onClick, rowId, cols} = this.props;
        const index = selectedProducts.indexOf(rowId);
        const isSelected = index > -1;
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

// <td
//     key="amount"
//     tabIndex="0"
//     onDoubleClick={this.handleEditProperty}
// >
//     <span>{product.amount}</span>
// </td>


TableItem.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { appHandler } = state;
    const {
        selectedProducts
    } = appHandler || {
        selectedProducts: []
    }

    return {
        selectedProducts
    }
}

TableItem = connect(mapStateToProps)(TableItem)

export default TableItem
