import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

class ProductSearch extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div className="panel panel-bordered panel-spaced panel-primary">
                <div className="panel-title">Product name</div>
                <div className="input-toggled">
                    <span className="font-weight-bold">Convenience Salad 250g</span>
                    <i className="icon-rounded icon-rounded-space pull-xs-right">x</i>
                    <span className="pull-xs-right">IFCO 510 x 10Stk</span>
                </div>
                <div className="row">
                    <div className="form-group col-sm-4">
                        <label>Packages amount</label>
                        <input className="form-control" type="number"/>
                    </div>
                    <div className="form-group col-sm-3">
                        <label>Product quantity</label>
                        <div className="input-icon-container input-block">
                            <input className="form-control form-control-meta" type="number"/>
                            <i className="meta-icon-edit input-icon-right"></i>
                        </div>
                    </div>
                    <div className="form-group col-sm-3">
                        <label>Discount</label>
                        <div className="input-icon-container input-block">
                            <input className="form-control form-control-meta" type="number"/>
                            <i className="meta-icon-edit input-icon-right"></i>
                        </div>
                    </div>
                    <div className="form-group col-sm-2">
                        <label/>
                        <button className="btn btn-sm btn-block btn-meta-primary">Add</button>
                    </div>
                </div>
            </div>
        )
    }
}


ProductSearch.propTypes = {
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { salesOrderStateHandler } = state;
    const {

    } = salesOrderStateHandler || {

    }

    return {

    }
}

ProductSearch = connect(mapStateToProps)(ProductSearch)

export default ProductSearch
