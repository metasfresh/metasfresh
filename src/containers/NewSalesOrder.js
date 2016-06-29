import React, { Component } from 'react';

import SOHeader from '../components/salesOrder/SOHeader.js';
import Purchaser from '../components/salesOrder/Purchaser.js';
import ProductSearch from '../components/salesOrder/ProductSearch.js';
import ProductTable from '../components/salesOrder/ProductTable.js';
import OrderInfo from '../components/salesOrder/OrderInfo.js';

export default class NewSalesOrder extends Component {
    render() {
        return (
            <div>
                <SOHeader/>
                <div className="container">
                    <div className="row">
                        <div className="col-xs-6">
                            <Purchaser/>
                            <ProductSearch/>

                        </div>
                        <div className="col-xs-6">
                            <OrderInfo/>
                        </div>
                    </div>
                    <div className="row">
                        <ProductTable/>
                    </div>
                </div>
            </div>
        );
    }
}
