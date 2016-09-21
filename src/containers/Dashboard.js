import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Header from '../components/app/Header';
import OrderList from '../components/app/OrderList';

import {
    getWindowBreadcrumb
 } from '../actions/MenuActions';

export class Dashboard extends Component {
    constructor(props){
        super(props);
    }

    componentDidMount = () => {
        const {dispatch} = this.props;
        dispatch(getWindowBreadcrumb("143"));
    }

    render() {
        const {breadcrumb} = this.props;
        return (
            <div>
                <Header
                    breadcrumb={breadcrumb.slice(0,1)}
                    siteName = {"Dashboard"}
                />
                <OrderList />
                <div className="header-sticky-distance"/>
            </div>
        );
    }

}

function mapStateToProps(state) {
    const { windowHandler, menuHandler } = state;
    const {
        master,
        connectionError,
        modal
    } = windowHandler || {
        master: {},
        connectionError: false,
        modal: false
    }


    const {
        breadcrumb
    } = menuHandler || {
        breadcrumb: {}
    }

    return {
        master,
        connectionError,
        breadcrumb,
        modal
    }
}

Dashboard.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Dashboard = connect(mapStateToProps)(Dashboard);

export default Dashboard
