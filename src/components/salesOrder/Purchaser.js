import React, { Component, PropTypes } from 'react';
import ReactDOM from 'react-dom';
import { connect } from 'react-redux';

import {unloadingChanged, invoiceChanged} from '../../actions/SalesOrderActions';

import LookupDropdown from './LookupDropdown';

import '../../assets/css/font-meta.css';

class Purchaser extends Component {
    constructor(props) {
        super(props);
    }
    handleUnloadingChange = (e) => {
        e.preventDefault();
        this.props.dispatch(unloadingChanged(this.unloadingInput.value));
    }
    handleInvoiceChange = (e) => {
        e.preventDefault();
        this.props.dispatch(invoiceChanged(this.invoiceInput.value));
    }
    render() {
        const {purchaser, salesOrderWindow, recentPartners} = this.props;
        return (
            <div className="panel panel-bordered panel-spaced panel-primary panel-distance">

                {salesOrderWindow.C_BPartner_ID && [
                    <div key="title" className="panel-title">{salesOrderWindow.C_BPartner_ID.caption}</div>,
                    <LookupDropdown key="dropdown" recent={recentPartners} properties={["C_BPartner_ID", "C_BPartner_Location_ID"]} />
                ]}

                {salesOrderWindow.Bill_BPartner_ID && [
                    <div className="form-group m-t-2 row">
                        <label key="title" className="col-sm-3 form-control-label">{salesOrderWindow.Bill_BPartner_ID.caption}</label>
                        <div className="col-sm-9">
                            <LookupDropdown rank="secondary" key="dropdown1" recent={recentPartners} properties={["Bill_BPartner_ID"]} />
                        </div>
                    </div>
                ]}

                <div className="form-group m-t-1 row">
                    <label key="title" className="col-sm-3 form-control-label">Unloading</label>
                    <div className="col-sm-9">
                        <LookupDropdown rank="secondary" key="dropdown2" recent={recentPartners} properties={["Bill_BPartner_ID"]} />
                    </div>
                </div>
            </div>
        )
    }
}


Purchaser.propTypes = {
    recentPartners: PropTypes.array.isRequired,
    salesOrderWindow: PropTypes.object.isRequired,
    purchaser: PropTypes.object.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { salesOrderStateHandler } = state;
    const {
        recentPartners,
        purchaser,
        salesOrderWindow
    } = salesOrderStateHandler || {
        recentPartners: [],
        purchaser: {},
        salesOrderWindow: {}
    }
    return {
        recentPartners,
        purchaser,
        salesOrderWindow
    }
}

Purchaser = connect(mapStateToProps)(Purchaser)

export default Purchaser
