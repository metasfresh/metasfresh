import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import ProductTableItem from './ProductTableItem';

class ProductTable extends Component {
    constructor(props) {
        super(props);
    }
    renderTableBody = () => {
        const {products} = this.props
        return products.products.map((product) => <ProductTableItem key={product.id} />)
    }
    renderTableFooter = () => {
        const {products} = this.props
        return products.containers.map((product) => <ProductTableItem key={product.id} />)
    }
    render() {
        const {products} = this.props
        return (
            <div className="col-xs-12 m-b-3">
                <div className=" panel panel-primary panel-bordered">
                    <table className="table table-striped">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Product</th>
                                <th>Amount</th>
                                <th>Packing</th>
                                <th>Product quantity</th>
                                <th>Price</th>
                                <th>Price for</th>
                                <th>Line amount</th>
                                <th>Discount</th>
                                <th>Final amount</th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.renderTableBody()}
                        </tbody>
                        <tfoot>
                            <tr className="table-footer-header">
                                <td></td>
                                <td>Containers and taxes</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            {this.renderTableFooter()}
                        </tfoot>
                    </table>
                </div>
            </div>
        )
    }
}


ProductTable.propTypes = {
    products: PropTypes.object.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { salesOrderStateHandler } = state;
    const {
        products
    } = salesOrderStateHandler || {
        products: {}
    }

    return {
        products
    }
}

ProductTable = connect(mapStateToProps)(ProductTable)

export default ProductTable
