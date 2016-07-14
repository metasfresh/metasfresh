import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    selectProduct,
    selectRangeProduct,
    selectOneProduct,
    deselectProduct,
    deselectAllProducts,
    deleteSelectedProducts
} from '../../actions/SalesOrderActions';

import ProductTableItem from './ProductTableItem';
import ProductTableHeader from './ProductTableHeader';
import ProductTableSubtotal from './ProductTableSubtotal';
import ProductTableTotal from './ProductTableTotal';
import ProductTableSubheader from './ProductTableSubheader';

class ProductTable extends Component {
    constructor(props) {
        super(props);
    }
    handleContextMenu = (e) => {
        e.preventDefault();
        this.contextMenu.classList.add('context-menu-open');
        this.contextMenu.style.left = e.clientX + "px";
        this.contextMenu.style.top = e.clientY + "px";
        this.contextMenu.focus();
        this.contextMenu.addEventListener("blur", ()=>{
            this.contextMenu.classList.remove('context-menu-open');
        });
    }
    handleClick = (e, id, index) => {
        e.preventDefault();

        const {selectedProducts, dispatch} = this.props
        const selectMore = e.nativeEvent.metaKey || e.nativeEvent.ctrlKey;
        const selectRange = e.shiftKey;
        const isSelected = index > -1;
        const isAnySelected = selectedProducts.length > 0;
        const isMoreSelected = selectedProducts.length > 1;

        if(selectMore){
            if(isSelected){
                dispatch(deselectProduct(index));
            }else{
                dispatch(selectProduct(id));
            }
        }else if(selectRange){
            if(isAnySelected){
                const idsToSelect = this.getProductRange(id);
                dispatch(selectRangeProduct(idsToSelect));
            }else{
                dispatch(selectOneProduct(id));
            }
        }else{
            if(isSelected){
                if(isMoreSelected){
                    dispatch(selectOneProduct(id));
                }else{
                    dispatch(deselectAllProducts());
                }
            }else{
                dispatch(selectOneProduct(id));
            }
        }
    }
    handleRemoveSelected = () => {
        this.props.dispatch(deleteSelectedProducts(this.props.selectedProducts));
    }
    sumProperty = (items, prop) => {
        return items.reduce((a, b) => {
            return b[prop] == null ? a : a + b[prop];
        }, 0);
    }
    getProductRange = (id) => {
        const {products, selectedProducts} = this.props;
        let selected = [
            products.products.findIndex(x => x.id === id),
            products.products.findIndex(x => x.id === selectedProducts[0])
        ];
        selected.sort((a,b) => a - b);
        return products.products.slice(selected[0], selected[1]+1).map(p => {
            return p.id
        });
    }
    renderTableBody = () => {
        const {products, selectedProducts} = this.props
        return products.products.map((product) => {
            return (
                <ProductTableItem
                    product={product}
                    key={product.id}
                    onClick={(e) => this.handleClick(e, product.id, selectedProducts.indexOf(product.id))}
                />
            )
        })
    }
    renderTableFooter = () => {
        const {products} = this.props
        return products.containers.map((container) => <ProductTableItem product={container} key={container.id} />)
    }
    render() {
        const {products} = this.props
        return (
            <div className="col-xs-12 m-b-3" onContextMenu={this.handleContextMenu}>
                <div
                    className="context-menu panel-bordered panel-primary"
                    ref={(c) => this.contextMenu = c}
                    tabIndex="0"
                >
                    <div className="context-menu-item">
                        <i className="meta-icon-file" /> Change log
                    </div>
                    {this.props.selectedProducts.length > 0 ? (
                        <div className="context-menu-item" onClick={this.handleRemoveSelected}>
                            <i className="meta-icon-trash" /> Remove selected
                        </div>
                    ) : null }
                </div>
                <div className=" panel panel-primary panel-bordered panel-bordered-force">
                    <table className="table table-bordered-vertically table-striped">
                        <thead>
                            <ProductTableHeader />
                        </thead>
                        <tbody>
                            {this.renderTableBody()}
                            <ProductTableSubtotal />
                        </tbody>
                        <tfoot>
                            <ProductTableSubheader />
                            {this.renderTableFooter()}
                            <ProductTableSubtotal />
                            <ProductTableTotal />
                        </tfoot>
                    </table>
                </div>
            </div>
        )
    }
}


ProductTable.propTypes = {
    selectedProducts: PropTypes.array.isRequired,
    products: PropTypes.object.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { salesOrderStateHandler } = state;
    const {
        products,
        selectedProducts
    } = salesOrderStateHandler || {
        selectedProducts: [],
        products: {}
    }


    return {
        selectedProducts,
        products
    }
}

ProductTable = connect(mapStateToProps)(ProductTable)

export default ProductTable
