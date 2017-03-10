import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Container from '../components/Container';
import DraggableWrapper from '../components/widget/DraggableWrapper';

import BarChart from '../components/charts/BarChartComponent';
import PieChart from '../components/charts/PieChartComponent';
import RawChart from '../components/charts/RawChart';

export class Dashboard extends Component {
    constructor(props){
        super(props);
    }

    render() {
        const {breadcrumb} = this.props;

        return (
            <Container
                siteName = "Dashboard"
                noMargin = {true}
            >
                <div className="container-fluid dashboard-wrapper">
                    <DraggableWrapper dashboard={this.props.location.pathname} />
                </div>
            </Container>
        );
    }
}

Dashboard.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Dashboard = connect()(Dashboard);

export default Dashboard
