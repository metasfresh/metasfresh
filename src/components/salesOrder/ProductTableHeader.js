import React, { Component } from 'react';

export default class ProductTableHeader extends Component {
    render() {
        return (
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
        )
    }
}
