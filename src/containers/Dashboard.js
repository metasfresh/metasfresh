import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Header from '../components/app/Header';
import OrderList from '../components/app/OrderList';

import {
    getWindowBreadcrumb,
    getDashboardLink
 } from '../actions/MenuActions';

export class Dashboard extends Component {
    constructor(props){
        super(props);
        this.state = {
            link: ''
        };
    }

    componentDidMount = () => {
        const {dispatch} = this.props;
        dispatch(getWindowBreadcrumb("143"));
        this.getDashboardLink();
    }

    getDashboardLink = () => {
        const {dispatch} = this.props;
        dispatch(getDashboardLink()).then(response => {
                this.setState(Object.assign({}, this.state, {
                    link: response.data
                }))
        });

        console.log(this.state.link);
    }

    render() {
        const {breadcrumb} = this.props;
        const {link} = this.state;
        return (
            <div>
                <Header
                    breadcrumb={breadcrumb.slice(0,1)}
                    siteName = {"Dashboard"}
                />
                <OrderList />
                <div className="header-sticky-distance"/>
                <iframe className="dashboard" src={link}></iframe>
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
