import React, { Component } from 'react';
import {connect} from 'react-redux';

import BarChart from './BarChartComponent';
import PieChart from './PieChartComponent';
import Indicator from './Indicator';
import Loader from '../app/Loader';

import {
    getKPIData,
    getTargetIndicatorsData
} from '../../actions/AppActions';

class RawChart extends Component {
    constructor(props){
        super(props);

        this.state = {
            chartData: null,
            intervalId: null,
            err: null
        }
    }

    componentDidUpdate(prevProps) {
        if(
            prevProps.isMaximize !== this.props.isMaximize ||
            prevProps.index !== this.props.index ||
            prevProps.id !== this.props.id
        ) {
            if(this.props.chartType === 'Indicator'){
                this.fetchData();
            }else{
                this.setState({
                    forceChartReRender: true,
                }, () => {
                    this.setState({
                        forceChartReRender: false
                    })
                })
            }
        }
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

    getData() {
        const { id, chartType } = this.props;

        if (chartType === 'Indicator') {
            return getTargetIndicatorsData(id)
                .then(response => {
                    return response.data.datasets
                }).catch(err => {throw err});
        }

        return getKPIData(id)
            .then(response => {
                return response.data.datasets
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
            id, chartType, caption, fields, groupBy, height,
            isMaximize, chartTitle
        } = this.props;
        const {chartData, forceChartReRender} = this.state;
        const data = chartData[0] && chartData[0].values;

        switch(chartType){
            case 'BarChart':
                return(
                    <BarChart
                        {...{
                            data, groupBy, caption, chartType, height,
                            fields, isMaximize, chartTitle
                        }}
                        chartClass={'chart-' + id}
                        reRender={forceChartReRender}
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
                        {...{data, fields, groupBy, height,
                            isMaximize, chartTitle}}
                        chartClass={'chart-' + id}
                        responsive={true}
                        reRender={forceChartReRender}
                        colors = {[
                            '#89d729', '#9aafbd', '#7688c9', '#c1ea8e',
                            '#c9d5dc', '#aab5e0', '#6aad18', '#298216',
                            '#32520d', '#605a7f'
                        ]}
                    />
                );
            case 'Indicator':
                return(
                    <Indicator
                        value={data[0][fields[0].fieldName] +
                            (fields[0].unit ? ' ' + fields[0].unit : '')}
                        {...{caption}}
                    />
                );
            default:
                return <div>{ chartType }</div>;
        }
    }

    renderNoData(data) {
        const {chartType} = this.props;

        switch(chartType){
            case 'Indicator':
                return <Indicator value={"No data"} loader={data === null} />
            default:
                return <div>{data === null ? <Loader /> : "No data"}</div>
        }
    }

    render(){
        const {chartData, err} = this.state;

        return err ?
            this.renderError() :
            (chartData && chartData.length > 0 ?
                this.renderChart() :
                this.renderNoData(chartData)
            )
    }
}

RawChart = connect()(RawChart);

export default RawChart;
