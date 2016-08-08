import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import Datetime from 'react-datetime';

import Dropdown from './../app/Dropdown';

class OrderInfo extends Component {
    constructor(props) {
        super(props);
    }
    handleDropdownChange = (result) => {
        console.log(result);
    }
    handleSetDefaultValue = () => {
        console.log(this);
    }
    render() {
        const {salesOrderWindow} = this.props;
        return (
            <div className="panel panel-distance m-t-3">
                <div className="row">
                    {(salesOrderWindow.DeliveryStatus && salesOrderWindow.DeliveryStatus.layoutInfo.displayed) &&
                        [<div className="col-xs-3">
                            {salesOrderWindow.DeliveryStatus.caption}
                        </div>,
                        <div className="col-xs-3">
                            <div className="tag tag-warning">Open</div>
                        </div>]

                    }
                    {(salesOrderWindow.InvoiceStatus && salesOrderWindow.InvoiceStatus.layoutInfo.displayed) &&
                        [<div className="col-xs-4 offset-xs-1">
                            {salesOrderWindow.InvoiceStatus.caption}
                        </div>,
                        <div className="col-xs-1">
                            <div className="tag tag-warning pull-xs-right">Open</div>
                        </div>]
                    }
                </div>
                <div className="m-t-2">
                    {(salesOrderWindow.DateOrdered && salesOrderWindow.DateOrdered.layoutInfo.displayed) &&
                        <div className="form-group row">
                            <label className="col-sm-3 form-control-label">{salesOrderWindow.DateOrdered.caption}</label>
                            <div className="col-sm-9">
                                <div className="input-icon-container input-block">
                                    <Datetime
                                        timeFormat={true}
                                        dateFormat={false}
                                        locale="de"
                                        inputProps={{placeholder: "(none)"}}
                                    />
                                    <i className="meta-icon-calendar input-icon-right"></i>
                                </div>
                            </div>
                        </div>
                    }
                    <div className="form-group row">
                        <label className="col-sm-3 form-control-label">Promised date</label>
                        <div className="col-sm-9">
                            <div className="input-icon-container input-block">
                                <Datetime
                                    timeFormat={false}
                                    dateFormat="D. MMMM YYYY"
                                    locale="de"
                                    inputProps={{placeholder: "(none)"}}
                                />
                                <i className="meta-icon-calendar input-icon-right"></i>
                            </div>
                        </div>
                    </div>
                    <div className="form-group row">
                        <label className="col-sm-3 form-control-label">Provisioning</label>
                        <div className="col-sm-9">
                            <div className="input-icon-container input-block">
                                <Datetime
                                    timeFormat={false}
                                    inputProps={{placeholder: "(none)"}}
                                    dateFormat="D. MMMM YYYY"
                                    locale="de"
                                />
                                <i className="meta-icon-calendar input-icon-right"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="m-t-2">
                    <div className="form-group row">
                        <label className="col-sm-3 form-control-label">Organisation</label>
                        <div className="col-sm-9">

                            <Dropdown
                                options={['1','2','3']}
                                defaultValue="(none)"
                                onChange={this.handleDropdownChange}
                            />

                        </div>
                    </div>
                    {(salesOrderWindow.M_Warehouse_ID && salesOrderWindow.M_Warehouse_ID.layoutInfo.displayed) &&
                        <div className="form-group row">
                            <label className="col-sm-3 form-control-label">{salesOrderWindow.M_Warehouse_ID.caption}</label>
                            <div className="col-sm-9">

                                <Dropdown
                                    options={['1','2','3']}
                                    defaultValue="(none)"
                                    onChange={this.handleDropdownChange}
                                />

                            </div>
                        </div>
                    }
                </div>
                <div className="m-t-2">
                    <button className="btn btn-sm btn-meta-outline-secondary pull-xs-right">
                        <span className="meta-icon-edit btn-icon-md"></span>
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
