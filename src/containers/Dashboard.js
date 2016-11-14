import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Container from '../components/Container';
import DraggableWrapper from '../components/widget/DraggableWrapper';


import {
    getRootBreadcrumb
} from '../actions/MenuActions';

import {
    getUserDashboardIndicators
} from '../actions/AppActions';

export class Dashboard extends Component {
    constructor(props){
        super(props);

        this.state = {
            indicators: []
        }
    }

    componentDidMount = () => {
        const {dispatch} = this.props;
        dispatch(getRootBreadcrumb());
        this.getIndicators();
    }

    getIndicators = () => {
        const {dispatch} = this.props;
        dispatch(getUserDashboardIndicators()).then(response => {
            this.setState(Object.assign({}, this.state, {
                indicators: response.data.items
            }));
        });
    }

    render() {
        const {breadcrumb} = this.props;
        const {indicators} = this.state;
        return (
            <Container
                breadcrumb={breadcrumb}
                siteName = {"Dashboard"}
                noMargin = {true}
            >
                <div className="container-fluid dashboard-wrapper">
                    <div className="indicators-wrapper">
                        {
                            indicators.map((indicator, id) =>
                                <div
                                    className="indicator"
                                    key={id}
                                >
                                    <iframe
                                        src={indicator.url}
                                        scrolling="no"
                                        frameBorder="no"
                                    ></iframe>
                                </div>
                            )
                        }
                    </div>
                    <DraggableWrapper/>
                </div>

            </Container>
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
