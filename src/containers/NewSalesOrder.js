import React, { Component } from 'react';

import Purchaser from '../components/salesOrder/Purchaser.js';
import ProductSearch from '../components/salesOrder/ProductSearch.js';
import ProductTable from '../components/salesOrder/ProductTable.js';
import OrderInfo from '../components/salesOrder/OrderInfo.js';

export default class NewSalesOrder extends Component {
    constructor(props){
        super(props);
    }
    render() {
        return (
            <div>
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
