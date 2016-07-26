import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

class ProductTableHeader extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const tabProp = this.props.salesOrderTable;

        return (
            <tr>
                {tabProp.childDescriptors && [
                    <th key="Line">{tabProp.childDescriptors.Line.caption}</th>,
                    <th key="Product">Product</th>,
                    <th key="QtyEnteredTU">{tabProp.childDescriptors.QtyEnteredTU.caption }</th>,
                    <th key="M_HU_PI_Item_Product_ID">{tabProp.childDescriptors.M_HU_PI_Item_Product_ID.caption }</th>,
                    <th key="QtyEntered">{tabProp.childDescriptors.QtyEntered.caption }</th>,
                    <th key="PriceEntered">{tabProp.childDescriptors.PriceEntered.caption }</th>,
                    <th key="C_UOM_ID">{tabProp.childDescriptors.C_UOM_ID.caption }</th>,
                    <th key="LineNetAmt">{tabProp.childDescriptors.LineNetAmt.caption }</th>,
                    <th key="Discount">{tabProp.childDescriptors.Discount.caption }</th>,
                    <th key="GrandTotal">{tabProp.childDescriptors.GrandTotal.caption }</th>]
                }
            </tr>
        )
    }
}

ProductTableHeader.propTypes = {
    salesOrderTable: PropTypes.object.isRequired
};

function mapStateToProps(state) {
    const {salesOrderStateHandler} = state;
    const {
        salesOrderTable
    } = salesOrderStateHandler || {
        salesOrderTable: {}
    }
    return {
        salesOrderTable
    }
}

ProductTableHeader = connect(mapStateToProps)(ProductTableHeader)

export default ProductTableHeader
