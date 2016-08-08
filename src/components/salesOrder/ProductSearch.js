import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import LookupDropdown from './LookupDropdown';
import ProductSearchSummary from './ProductSearchSummary';

import {toggleProductSummary} from '../../actions/SalesOrderActions';

class ProductSearch extends Component {
    constructor(props) {
        super(props);
    }
    handleMouseover = () => {
        this.props.dispatch(toggleProductSummary(true));
    }
    handleMouseout = () => {
        this.props.dispatch(toggleProductSummary(false));
    }
    render() {
        const {salesOrderWindow, recentProducts} = this.props;
        return (
            <div
                className="panel panel-bordered panel-spaced panel-primary panel-distance"
                onMouseEnter={this.handleMouseover}
                onMouseLeave={this.handleMouseout}
            >
                <ProductSearchSummary />
                {salesOrderWindow.M_Product_ID && [
                    <div key="title" className="panel-title">{salesOrderWindow.M_Product_ID.caption}</div>,
                    <LookupDropdown className="primary" key="dropdown" recent={[]} properties={[]} />

                ]}

                <div className="row m-t-1 items-row">
                    <div className="form-group col-sm-4">
                        <label>Packages amount</label>
                        <div className="input-primary">
                            <input className="input-field" type="text"/>
                        </div>
                    </div>
                    <div className="form-group col-sm-4">
                        {salesOrderWindow.Qty_FastInput_TU && [
                            <label key="label">{salesOrderWindow.Qty_FastInput_TU.caption}</label>,
                            <div className="input-secondary">
                                <input className="input-field" type="text"/>
                            </div>
                        ]}
                    </div>

                    <div className="form-group col-sm-3 offset-sm-1 items-col-bottom">
                        <button className="btn btn-sm btn-block btn-meta-primary">Add</button>
                    </div>
                </div>
            </div>
        )
    }
}


ProductSearch.propTypes = {
    isProductOrderSummaryShow: PropTypes.bool.isRequired,
    recentProducts: PropTypes.array.isRequired,
    salesOrderWindow: PropTypes.object.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const {salesOrderStateHandler} = state;
    const {
        salesOrderWindow,
        recentProducts,
        isProductOrderSummaryShow
    } = salesOrderStateHandler || {
        salesOrderWindow: {},
        recentProducts: [],
        isProductOrderSummaryShow: false
    }
    return {
        salesOrderWindow,
        recentProducts,
        isProductOrderSummaryShow
    }
}

ProductSearch = connect(mapStateToProps)(ProductSearch)

export default ProductSearch
