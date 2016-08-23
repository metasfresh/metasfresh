import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    saveProductProperty,
    editProductProperty,
    changeProductProperty,
    selectOneProduct
} from '../../actions/AppActions';

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
    render() {
        const {product, selectedProducts, onClick} = this.props;
        const index = selectedProducts.indexOf(product.id);
        const isSelected = index > -1;
        return (
            <tr
                onClick={onClick}
                className={isSelected ? "row-selected" : null}
            >
                <td>{product.id}</td>
                <td>{product.name}</td>
                <td
                    key="amount"
                    tabIndex="0"
                    onDoubleClick={this.handleEditProperty}
                >
                    <span>{product.amount}</span>
                </td>
                <td>{product.packing}</td>
                <td
                    key="quantity"
                    tabIndex="0"
                    onDoubleClick={this.handleEditProperty}
                >
                    <span>{product.quantity}</span>
                </td>
                <td>{product.price}</td>
                <td>{product.priceFor}</td>
                <td>{product.price * product.amount}</td>
                <td>{product.discount}%</td>
                <td>{product.price * product.amount}</td>
            </tr>
        )
    }
}


TableItem.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { appHandler } = state;
    const {
        selectedProducts
    } = salesOrderStateHandler || {
        selectedProducts: []
    }

    return {
        selectedProducts
    }
}

TableItem = connect(mapStateToProps)(TableItem)

export default TableItem
