import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';

import '../../assets/css/font-meta.css';

class Purchaser extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div className="panel panel-bordered panel-spaced panel-primary">
                <div className="panel-title">Purchaser</div>
                <div className="input-toggled">
                    <span className="font-weight-bold">Jazzy Innovations</span>
                    <i className="icon-rounded icon-rounded-space pull-xs-right">x</i>
                    <span className="pull-xs-right">Tracka 18, Gliwice, Poland, VAT 541-141-56-23</span>
                </div>
                <div className="form-group row">
                    <label className="col-sm-4 form-control-label">Invoice partner</label>
                    <div className="col-sm-8">
                        <input className="form-control form-control-meta" type="text"/>
                        <i className="meta-icon-edit input-icon-right"></i>
                    </div>
                </div>
                <div className="form-group row">
                    <label className="col-sm-4 form-control-label">Unloading partner</label>
                    <div className="col-sm-8">
                        <input className="form-control form-control-meta" type="text"/>
                        <i className="meta-icon-edit input-icon-right"></i>
                    </div>
                </div>
            </div>
        )
    }
}


Purchaser.propTypes = {
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

Purchaser = connect(mapStateToProps)(Purchaser)

export default Purchaser
