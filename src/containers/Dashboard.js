import React, { Component } from 'react';
import { Link } from 'react-router';

export default class Dashboard extends Component {
    render() {
        return (
            <div className="container text-xs-center">
                <Link to="/sales-order">New sales order</Link>
            </div>
        );
    }
}
