import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

class ProductTableHeader extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const {salesOrderWindow} = this.props;

        return (
            <tr>
                <th>No.</th>
                <th>Product</th>
                <th>{salesOrderWindow.QtyEntered_TU ? salesOrderWindow.QtyEntered_TU.caption : ""}</th>
                <th>{salesOrderWindow.M_HU_PI_Item_Product_ID ? salesOrderWindow.M_HU_PI_Item_Product_ID.caption : ""}</th>
                <th>{salesOrderWindow.QtyEntered ? salesOrderWindow.QtyEntered.caption : ""}</th>
                <th>{salesOrderWindow.PriceEntered ? salesOrderWindow.PriceEntered.caption : ""}</th>
                <th>{salesOrderWindow.C_UOM_ID ? salesOrderWindow.C_UOM_ID.caption : ""}</th>
                <th>Line amount</th>
                <th>Discount</th>
                <th>Final amount</th>
            </tr>
        )
    }
}

ProductTableHeader.propTypes = {
    salesOrderWindow: PropTypes.object.isRequired
};

function mapStateToProps(state) {
    const {salesOrderStateHandler} = state;
    const {
        salesOrderWindow
    } = salesOrderStateHandler || {
        salesOrderWindow: {}
    }
    return {
        salesOrderWindow
    }
}

ProductTableHeader = connect(mapStateToProps)(ProductTableHeader)

export default ProductTableHeader
