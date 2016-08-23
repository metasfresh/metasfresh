import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import {
    selectProduct,
    selectRangeProduct,
    selectOneProduct,
    deselectProduct,
    deselectAllProducts,
} from '../../actions/AppActions';

import TableFilter from './TableFilter';
import TableHeader from './TableHeader';
import TableContextMenu from './TableContextMenu';
import TableItem from './TableItem';

class ProductTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            contextMenu: {
                open: false,
                x: 0,
                y: 0
            }
        }
    }
    handleContextMenu = (e) => {
        e.preventDefault();
    }
    handleClick = (e, id, index) => {
        // e.preventDefault();
        //
        // const {selectedProducts, dispatch} = this.props
        // const selectMore = e.nativeEvent.metaKey || e.nativeEvent.ctrlKey;
        // const selectRange = e.shiftKey;
        // const isSelected = index > -1;
        // const isAnySelected = selectedProducts.length > 0;
        // const isMoreSelected = selectedProducts.length > 1;
        //
        // if(selectMore){
        //     if(isSelected){
        //         dispatch(deselectProduct(index));
        //     }else{
        //         dispatch(selectProduct(id));
        //     }
        // }else if(selectRange){
        //     if(isAnySelected){
        //         const idsToSelect = this.getProductRange(id);
        //         dispatch(selectRangeProduct(idsToSelect));
        //     }else{
        //         dispatch(selectOneProduct(id));
        //     }
        // }else{
        //     if(isSelected){
        //         if(isMoreSelected){
        //             dispatch(selectOneProduct(id));
        //         }else{
        //             dispatch(deselectAllProducts());
        //         }
        //     }else{
        //         dispatch(selectOneProduct(id));
        //     }
        // }
    }
    handleRemoveSelected = () => {
        // this.props.dispatch(deleteSelectedProducts(this.props.selectedProducts));
    }
    sumProperty = (items, prop) => {
        return items.reduce((a, b) => {
            return b[prop] == null ? a : a + b[prop];
        }, 0);
    }
    getProductRange = (id) => {
        // const {products, selectedProducts} = this.props;
        // let selected = [
        //     products.products.findIndex(x => x.id === id),
        //     products.products.findIndex(x => x.id === selectedProducts[0])
        // ];
        // selected.sort((a,b) => a - b);
        // return products.products.slice(selected[0], selected[1]+1).map(p => {
        //     return p.id
        // });
    }
    renderTableBody = () => {
        // const {products, selectedProducts} = this.props
        // return products.products.map((product) => {
        //     return (
        //         <ProductTableItem
        //             product={product}
        //             key={product.id}
        //             onClick={(e) => this.handleClick(e, product.id, selectedProducts.indexOf(product.id))}
        //         />
        //     )
        // })
    }
    render() {
        const {cols} = this.props;
        return (
            <div className="row">
                <div className="col-xs-12">
                    <TableFilter />
                    <TableContextMenu />
                    <div className="panel panel-primary panel-bordered panel-bordered-force" onContextMenu={this.handleContextMenu}>
                        <table className="table table-bordered-vertically table-striped">
                            <thead>
                                <TableHeader cols={cols} />
                            </thead>
                            <tbody>
                                {this.renderTableBody()}
                            </tbody>
                            <tfoot>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        )
    }
}


ProductTable.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { appHandler } = state;
    const {
    } = appHandler || {
    }


    return {
    }
}

ProductTable = connect(mapStateToProps)(ProductTable)

export default ProductTable
