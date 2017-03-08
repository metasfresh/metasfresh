import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';

import BarChart from './BarChartComponent';
import PieChart from './PieChartComponent';

import {
    getKPIData,
    getTargetIndicatorsData
} from '../../actions/AppActions';

class RawChart extends Component {
    constructor(props){
        super(props);
        
        this.state = {
            chartData: []
        }
    }

    componentDidMount() {
        const { id, kpi, dispatch } = this.props;
        if(kpi) {
            dispatch(getKPIData(id)).then(response => {
                this.setState({
                    chartData: response.data.datasets[0].values
                });
            });
        } else {
            dispatch(getTargetIndicatorsData(id)).then(response => {
                this.setState({
                    chartData: response.data.data
                });
            });
        }
    }
    
    render() {
        const {
            id, chartType, caption, fields, groupBy, pollInterval, kpi
        } = this.props;
        const {
            chartData
        } = this.state;
        const colors = ['#98abc5', '#8a89a6', '#7b6888', '#6b486b', '#a05d56', '#d0743c', '#ff8c00'];
        console.log(chartType);
        switch(chartType){
            case 'BarChart':
                return(
                    <BarChart
                        chartType={chartType}
                        caption={caption}
                        fields={fields}
                        groupBy={groupBy}
                        data={chartData}
                        colors={colors}
                        chartClass={'chart-' + id}
                    />
                )
            case 'PieChart':
                return(
                    <div>Pie Chart</div>
                )
        }
    }
}

RawChart.propTypes = {
    dispatch: PropTypes.func.isRequired
};

RawChart = connect()(RawChart);

export default RawChart;
