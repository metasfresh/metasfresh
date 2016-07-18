import React, { Component } from 'react';
import Header from '../components/app/Header';
import OrderList from '../components/app/OrderList';
import './styles.css';

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
