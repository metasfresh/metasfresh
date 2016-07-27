import React, { Component } from 'react';

class ProductSearchSummary extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const {toggle} = this.props;
        return (
            <div className={"tooltip-extended tooltip-primary tooltip-arrowed tooltip-extended-right " + (toggle ? "tooltip-extended-show":"")}>
                <p>Product order summary</p>
                <table>
                    <tbody>
                        <tr>
                            <td>15x</td>
                            <td className="font-weight-bold">Convinience Salad 250g</td>
                            <td>150 Stk</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>IFCO 6410 x 10Stk</td>
                            <td>2 EUR Each</td>
                            <td className="font-weight-bold">300.00 EUR</td>
                        </tr>
                        <tr>
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

export default ProductSearchSummary
