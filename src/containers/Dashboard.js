import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Container from '../components/Container';
import DraggableWrapper from '../components/widget/DraggableWrapper';

import BarChart from '../components/charts/BarChartComponent';
import PieChart from '../components/charts/PieChartComponent';
import RawChart from '../components/charts/RawChart';

import {
    getKPIsDashboard,
    getTargetIndicatorsDashboard,
    getKPIData,
    getTargetIndicatorsData
} from '../actions/AppActions';

export class Dashboard extends Component {
    constructor(props){
        super(props);
        this.state ={
            apiData: [],
            kpiLayout: [],
            targetLayout: []
        }
    }

    componentDidMount(){
        const {dispatch} = this.props;

        const {apiData} = this.state;

        dispatch(getKPIsDashboard()).then(response => {
            // console.log(response.data.items);
            this.setState({
                kpiLayout: response.data.items
            });
        });
        dispatch(getTargetIndicatorsDashboard()).then(response => {
            // console.log(response.data.items);
        });
        dispatch(getKPIData(1000013)).then(response => {
            this.setState({
                apiData: response.data.data
            });
        });
        dispatch(getTargetIndicatorsData(1000014)).then(response => {
            // console.log(response.data);
        });

    }

    render() {

        const {apiData, kpiLayout} = this.state;

        const {breadcrumb} = this.props;

        return (
            <Container
                siteName = "Dashboard"
                noMargin = {true}
            >
                <div className="container-fluid dashboard-wrapper">
                    <DraggableWrapper dashboard={this.props.location.pathname} />
                </div>

                {kpiLayout && kpiLayout.length > 0 && kpiLayout.map((item, id) => 
                     <RawChart
                        key={id} 
                        id={item.id}
                        chartType={item.kpi.chartType}
                        caption={item.kpi.caption}
                        fields={item.kpi.fields}
                        groupBy={item.kpi.groupByField}
                        pollInterval={item.kpi.pollIntervalSec}
                        kpi={true}
                     />
                )}

            </Container>
        );
    }
}

Dashboard.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Dashboard = connect()(Dashboard);

export default Dashboard
