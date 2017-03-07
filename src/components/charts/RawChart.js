import React, { Component } from 'react';

import BarChart from './BarChartComponent';
import PieChart from './PieChartComponent';

import {
    getKPIsDashboard,
    getTargetIndicatorsDashboard,
    getKPIData,
    getTargetIndicatorsData
} from '../../actions/AppActions';

class RawChart extends Component {

    render() {
        return (
            <div>
                raw chart component
            </div>
        );
    }
}

export default RawChart;