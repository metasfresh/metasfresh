import React, { Component } from 'react';
import Header from '../components/app/Header';
import OrderList from '../components/app/OrderList';

export default class Dashboard extends Component {
    render() {
        return (
            <div>
                <Header/>
                <OrderList />
                <div className="header-sticky-distance"/>
            </div>
        );
    }
}
