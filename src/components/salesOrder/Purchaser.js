import React, { Component, PropTypes } from 'react';
import ReactDOM from 'react-dom';
import { connect } from 'react-redux';

import {unloadingChanged, invoiceChanged} from '../../actions/SalesOrderActions';

import Dropdown from './Dropdown';

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
        const {purchaser, salesOrderWindow} = this.props;
        return (
            <div className="panel panel-bordered panel-spaced panel-primary">

                <div className="panel-title">{salesOrderWindow.C_BPartner_ID ? salesOrderWindow.C_BPartner_ID.caption : ""}</div>
                <Dropdown recent={purchaser.recent} property="C_BPartner_ID" items={purchaser.recent} />

                <div className="panel-title">{salesOrderWindow.Bill_BPartner_ID ? salesOrderWindow.Bill_BPartner_ID.caption : ""}</div>
                <Dropdown items={purchaser.recent} />

                <div className="panel-title">Unloading partner</div>
                <Dropdown items={purchaser.recent} />
            </div>
        )
    }
}


Purchaser.propTypes = {
    salesOrderWindow: PropTypes.object.isRequired,
    purchaser: PropTypes.object.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { salesOrderStateHandler } = state;
    const {
        purchaser,
        salesOrderWindow
    } = salesOrderStateHandler || {
        purchaser: {},
        salesOrderWindow: {}
    }

    return {
        purchaser,
        salesOrderWindow
    }
}

Purchaser = connect(mapStateToProps)(Purchaser)

export default Purchaser
