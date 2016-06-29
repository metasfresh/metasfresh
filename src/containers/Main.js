import React, { Component } from 'react';
import Header from '../components/app/Header';
import './styles.css';

export default class Main extends Component {
    render() {
        return (
            <div>
                <Header/>
                <div>
                    { this.props.children }
                </div>
            </div>
        );
    }
}
