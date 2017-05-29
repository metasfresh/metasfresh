import React, { Component } from 'react';
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
            chartData: [],
            intervalId: null,
            err: null
        }
    }

    getData(){
        const { id, chartType } = this.props;

        if (chartType === 'Indicator') {
            return getTargetIndicatorsData(id)
                .then(response => {
                    return response.data.datasets[0].values
                }).catch(err => {throw err});
        }

        return getKPIData(id)
            .then(response => {
                return response.data.datasets[0].values
            })
            .catch(err => {throw err});
    }

    fetchData(){
        this.getData()
            .then(chartData => {
                this.setState({ chartData: chartData, err: null });
            }).catch(err => {
                this.setState({ err })
            })
    }

    componentDidMount(){
        const { pollInterval } = this.props;

        this.fetchData();

        if (pollInterval){
            this.setState({
                intervalId: setInterval(() => {
                    this.fetchData();
                }, pollInterval * 1000)
            })
        }
    }

    componentWillUnmount(){
        const {intervalId} = this.state;

        if (intervalId){
            clearInterval(intervalId);

            this.setState({
                intervalId: null
            })
        }
    }

    renderError() {
        return(
            <div className="error-load-data">
                <h6 className="error-load-text">Error loading data...</h6>
                <div className="error-loading"></div>
            </div>
        );
    }

    renderChart() {
        const {
            id, chartType, caption, fields, groupBy, reRender, height
        } = this.props;
        const {chartData} = this.state;

        switch(chartType){
            case 'BarChart':
                return(
                    <BarChart
                        chartType={chartType}
                        caption={caption}
                        fields={fields}
                        groupBy={groupBy}
                        data={chartData}
                        chartClass={'chart-' + id}
                        height={height}
                        reRender={reRender}
                        colors = {[
                            '#89d729', '#9aafbd', '#7688c9', '#c1ea8e',
                            '#c9d5dc', '#aab5e0', '#6aad18', '#298216',
                            '#32520d', '#605a7f'
                        ]}
                    />
                );
            case 'PieChart':
                return(
                    <PieChart
                        chartClass={'chart-' + id}
                        responsive={true}
                        data={chartData}
                        fields={fields}
                        groupBy={groupBy}
                        height={height}
                        reRender={reRender}
                        colors = {[
                            '#89d729', '#9aafbd', '#7688c9', '#c1ea8e',
                            '#c9d5dc', '#aab5e0', '#6aad18', '#298216',
                            '#32520d', '#605a7f'
                        ]}
                    />
                );
            case 'Indicator':
                return(
                    <div className="indicator">
                        <div className="indicator-value">{
                            chartData[0][fields[0].fieldName] +
                            (fields[0].unit ? ' ' + fields[0].unit : '')}</div>
                        <div className="indicator-kpi-caption">{caption}</div>
                    </div>
                );
            default:
                return <div>{ chartType }</div>;
        }
    }

    render(){
        const {chartData, err} = this.state;

        return err ?
            this.renderError() :
            (chartData && chartData.length > 0 ?
                this.renderChart() : <div>No data</div>)

    }
}

RawChart = connect()(RawChart);

export default RawChart;
