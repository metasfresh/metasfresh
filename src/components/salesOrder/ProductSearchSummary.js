import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

class ProductSearchSummary extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const {isProductOrderSummaryShow} = this.props;
        return (
            <div className={"tooltip-extended tooltip-primary tooltip-arrowed tooltip-extended-right " + (isProductOrderSummaryShow ? "tooltip-extended-show":"")}>
                <p>Product order summary</p>
                <table className="sm product-order-summary">
                    <tbody>
                        <tr>
                            <td>15x</td>
                            <td className="text-weight-bold">Convinience Salad 250g</td>
                            <td>150 Stk</td>
                            <td></td>
                        </tr>
                        <tr className="row-bordered-bottom row-bordered-bright row-bordered-mod">
                            <td></td>
                            <td>IFCO 6410 x 10Stk</td>
                            <td>2 EUR Each</td>
                            <td className="font-weight-bold">300.00 EUR</td>
                        </tr>
                        <tr className="row-bordered-bottom row-bordered-dark row-bordered-mod">
                            <td></td>
                            <td>Discount</td>
                            <td>-10%</td>
                            <td className="font-weight-bold">-30.00 EUR</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td className="font-weight-bold">300.00 EUR</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        )
    }
}


ProductSearchSummary.propTypes = {
    isProductOrderSummaryShow: PropTypes.bool.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const {salesOrderStateHandler} = state;
    const {
        isProductOrderSummaryShow
    } = salesOrderStateHandler || {
        isProductOrderSummaryShow: false
    }
    return {
        isProductOrderSummaryShow
    }
}

ProductSearchSummary = connect(mapStateToProps)(ProductSearchSummary)

export default ProductSearchSummary
