import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

class OrderInfo extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const {salesOrderWindow} = this.props;
        return (
            <div className="panel panel-spaced">
                <div className="row">
                    <div className="col-xs-5">
                        Shipment status
                        <div className="label label-warning pull-xs-right">Open</div>
                    </div>
                    <div className="col-xs-5 col-xs-offset-1">
                        Billing status
                        <div className="label label-warning pull-xs-right">Open</div>
                    </div>
                </div>
                <div className="m-t-2">
                    <div className="form-group row">
                        <label className="col-sm-3 form-control-label">Order date</label>
                        <div className="col-sm-9">
                            <div className="input-icon-container input-block">
                                <input className="form-control form-control-meta" type="text"/>
                                <i className="meta-icon-calendar input-icon-right"></i>
                            </div>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label className="col-sm-3 form-control-label">Promised date</label>
                        <div className="col-sm-9">
                            <div className="input-icon-container input-block">
                                <input className="form-control form-control-meta" type="text"/>
                                <i className="meta-icon-calendar input-icon-right"></i>
                            </div>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label className="col-sm-3 form-control-label">Provisioning</label>
                        <div className="col-sm-9">
                            <div className="input-icon-container input-block">
                                <input className="form-control form-control-meta" type="text"/>
                                <i className="meta-icon-calendar input-icon-right"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="m-t-2">
                    <div className="form-group row">
                        <label className="col-sm-3 form-control-label">Organisation</label>
                        <div className="col-sm-9">
                            <div className="input-icon-container input-block">
                                <input className="form-control form-control-meta" type="text"/>
                                <i className="meta-icon-down input-icon-right input-icon-sm"></i>
                            </div>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label className="col-sm-3 form-control-label">Warehouse</label>
                        <div className="col-sm-9">
                            <div className="input-icon-container input-block">
                                <input className="form-control form-control-meta" type="text"/>
                                <i className="meta-icon-down input-icon-right input-icon-sm"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="m-t-2">
                    <button className="btn btn-sm btn-secondary-outline pull-xs-right">
                        <span className="meta-icon-edit btn-icon-sm"></span>
                        Advanced edit
                    </button>
                </div>
            </div>
        )
    }
}


OrderInfo.propTypes = {
    salesOrderWindow: PropTypes.object.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const {salesOrderStateHandler} = state;
    const {
        salesOrderWindow
    } = salesOrderStateHandler || {
        salesOrderWindow: {}
    }
    return {
        salesOrderWindow
    }
}

OrderInfo = connect(mapStateToProps)(OrderInfo)

export default OrderInfo
