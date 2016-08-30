import React, { Component } from 'react';
import '../assets/css/styles.css';
import Header from '../components/app/Header';
import OrderList from '../components/app/OrderList';

export default class Main extends Component {
    render() {
        return (
            <div>
                <Header/>
                <OrderList />

                <div className="header-sticky-distance">
                    { this.props.children }
                </div>
            </div>
        );
    }
}
