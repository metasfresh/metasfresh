import React, { Component } from 'react';
import './Header.css';

export default class Header extends Component {
    render() {
        return (
            <nav className="navbar navbar-full navbar-light navbar-super-faded navbar-wider navbar-bordered">
                <div className="container">
                    <div className="row">
                        <div className="col-sm-4">
                            <i className="fa fa-cog"/>
                            <span>Sales Order </span>
                            <span className="label label-faded label-small">DRAFT</span>
                        </div>
                        <div className="col-sm-4 text-xs-center text-brand">
                            metasfresh
                        </div>
                        <div className="col-sm-4 text-xs-right">
                            <button className="btn btn-sm btn-secondary-outline">icon</button>
                        </div>
                    </div>
                </div>
            </nav>
        )
    }
}
