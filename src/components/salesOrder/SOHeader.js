import React, { Component } from 'react';
import '../app/Header.css';

export default class SOHeader extends Component {
    render() {
        return (
            <nav className="navbar navbar-full navbar-light navbar-super-faded navbar-wider navbar-bordered">
                <div className="container">
                    <div className="row">
                        <div className="col-xs-12 text-xs-right">
                            <button className="btn btn-sm btn-meta-primary">Actions</button> <button className="btn btn-sm btn-meta-success">Complete</button>
                        </div>
                    </div>
                </div>
            </nav>
        )
    }
}
