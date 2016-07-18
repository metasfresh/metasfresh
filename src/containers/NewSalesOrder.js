import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import Purchaser from '../components/salesOrder/Purchaser';
import ProductSearch from '../components/salesOrder/ProductSearch';
import ProductTable from '../components/salesOrder/ProductTable';
import OrderInfo from '../components/salesOrder/OrderInfo';

import {
    createWindow
} from '../actions/SalesOrderActions';

class NewSalesOrder extends Component {
    constructor(props){
        super(props);
        // this.props.dispatch(createWindow());
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



NewSalesOrder.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    return {

    }
}

NewSalesOrder = connect(mapStateToProps)(NewSalesOrder)

export default NewSalesOrder;
