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
        const {purchaser} = this.props;
        return (
            <div className="panel panel-bordered panel-spaced panel-primary">

                <div className="panel-title">Purchaser</div>
                <Dropdown items={purchaser.recent} />
                <div className="form-group row">
                    <label className="col-sm-4 form-control-label">Invoice partner</label>
                    <div className="col-sm-8">
                        <div className="input-icon-container input-block">
                            <input
                                onChange={this.handleInvoiceChange}
                                value={purchaser.invoice}
                                ref={(c)=>this.invoiceInput=c}
                                className="form-control form-control-meta"
                                type="text"
                            />
                            <i className="meta-icon-edit input-icon-right"></i>
                        </div>
                    </div>
                </div>
                <div className="form-group row">
                    <label className="col-sm-4 form-control-label">Unloading partner</label>
                    <div className="col-sm-8">
                        <div className="input-icon-container input-block">
                            <input
                                onChange={this.handleUnloadingChange}
                                value={purchaser.unloading}
                                ref={(c)=>this.unloadingInput=c}
                                className="form-control form-control-meta"
                                type="text"
                            />
                            <i className="meta-icon-edit input-icon-right"></i>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}


Purchaser.propTypes = {
    purchaser: PropTypes.object.isRequired,
    dispatch: PropTypes.func.isRequired
};

function mapStateToProps(state) {
    const { salesOrderStateHandler } = state;
    const {
        purchaser
    } = salesOrderStateHandler || {
        purchaser: {}
    }

    return {
        purchaser
    }
}

Purchaser = connect(mapStateToProps)(Purchaser)

export default Purchaser
