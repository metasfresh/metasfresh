import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import Dropdown from './Dropdown';

class ProductSearch extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const {salesOrderWindow,recentProducts} = this.props;
        return (
            <div className="panel panel-bordered panel-spaced panel-primary">
                {salesOrderWindow.M_Product_ID && [
                    <div key="title" className="panel-title">{salesOrderWindow.M_Product_ID.caption}</div>,
                    <Dropdown key="dropdown" recent={recentProducts} property="M_Product_ID" />
                ]}

                <div className="row">
                    <div className="form-group col-sm-4">
                        <label>Packages amount</label>
                        <input className="form-control" type="number"/>
                    </div>
                    <div className="form-group col-sm-4">
                        {salesOrderWindow.Qty_FastInput_TU && [
                            <label key="label">{salesOrderWindow.Qty_FastInput_TU.caption}</label>,
                            <div key="input" className="input-icon-container input-block">
                                <input className="form-control form-control-meta" type="number"/>
                                <i className="meta-icon-edit input-icon-right"></i>
                            </div>
                        ]}
                    </div>

                    <div className="form-group col-sm-2 col-sm-offset-2">
                        <label/>
                        <button className=" btn btn-sm btn-block btn-meta-primary">Add</button>
                    </div>
                </div>
            </div>
        )
    }
}


ProductSearch.propTypes = {
    recentProducts: PropTypes.array.isRequired,
    salesOrderWindow: PropTypes.object.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const {salesOrderStateHandler} = state;
    const {
        salesOrderWindow,
        recentProducts
    } = salesOrderStateHandler || {
        salesOrderWindow: {},
        recentProducts: []
    }
    return {
        salesOrderWindow,
        recentProducts
    }
}

ProductSearch = connect(mapStateToProps)(ProductSearch)

export default ProductSearch
