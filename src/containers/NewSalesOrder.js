import React, { Component } from 'react';

import Purchaser from '../components/salesOrder/Purchaser';
import ProductSearch from '../components/salesOrder/ProductSearch';
import ProductTable from '../components/salesOrder/ProductTable';
import OrderInfo from '../components/salesOrder/OrderInfo';
import OrderList from '../components/salesOrder/OrderList';

export default class NewSalesOrder extends Component {
    constructor(props){
        super(props);
    }
    render() {
        return (
            <div>
                <OrderList />
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
