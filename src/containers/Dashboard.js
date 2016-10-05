import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Header from '../components/app/Header';

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
                <div className="header-sticky-distance"/>
                <iframe className="dashboard" src={link}></iframe>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const {menuHandler } = state;
    const {
        breadcrumb
    } = menuHandler || {
        breadcrumb: []
    }

    return {
        breadcrumb
    }
}

Dashboard.propTypes = {
    dispatch: PropTypes.func.isRequired,
    breadcrumb: PropTypes.array.isRequired
};

Dashboard = connect(mapStateToProps)(Dashboard);

export default Dashboard
