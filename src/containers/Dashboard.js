import React, { Component, PropTypes } from 'react';
import {connect} from 'react-redux';
import Container from '../components/Container';
import DraggableWrapper from '../components/widget/DraggableWrapper';

import BarChart from '../components/charts/BarChartComponent';

export class Dashboard extends Component {
    constructor(props){
        super(props);
    }

    render() {
        const {breadcrumb} = this.props;
        const data = [
            {name: "Alice", value: 2},
            {name: "Bob", value: 3},
            {name: "Carol", value: 1},
            {name: "Dwayne", value: 5},
            {name: "Anne", value: 8},
            {name: "Robin", value: 28},
            {name: "Eve", value: 12},
            {name: "Karen", value: 6},
            {name: "Lisa", value: 22},
            {name: "Tom", value: 18}
        ];
        const data2 = [
            {name: "Alice", value: 2},
            {name: "Bob", value: 3},
            {name: "Carol", value: 1},
            {name: "Dwayne", value: 5},
            {name: "Anne", value: 8},
            {name: "Robin", value: 18},
            {name: "Eve", value: 12},
            {name: "Karen", value: 6},
            {name: "Lisa", value: 22},
            {name: "Tom", value: 18},
            {name: "Valice", value: 12},
            {name: "Boob", value: 23},
            {name: "Darol", value: 31},
            {name: "Cwayne", value: 45},
            {name: "Tanne", value: 58},
            {name: "Mobin", value: 28},
            {name: "Meve", value: 12},
            {name: "Caren", value: 46},
            {name: "Gisa", value: 42},
            {name: "Pom", value: 18},
            {name: "Slice", value: 2},
            {name: "Yob", value: 3},
            {name: "CCarol", value: 1},
            {name: "Duayne", value: 35},
            {name: "Canne", value: 18},
            {name: "Oobin", value: 28},
            {name: "Weve", value: 12},
            {name: "Laren", value: 16},
            {name: "Zisa", value: 22},
            {name: "Oom", value: 18}
        ];
        const data3 = [
            {name: "Alice", value: 42},
            {name: "Bob", value: 33},
            {name: "Carol", value: 41},
            {name: "Dwayne", value: 5},
            {name: "Anne", value: 8},
            {name: "Robin", value: 28},
            {name: "Eve", value: 12},
            {name: "Karen", value: 6},
            {name: "Lisa", value: 22},
            {name: "Tom", value: 18},
            {name: "CCarol", value: 1},
            {name: "Duayne", value: 35},
            {name: "Canne", value: 18},
            {name: "Oobin", value: 28},
            {name: "Weve", value: 12},
            {name: "Laren", value: 16},
            {name: "Zisa", value: 22},
            {name: "Oom", value: 18}
        ];
        return (
            <Container
                siteName = {"Dashboard"}
                noMargin = {true}
            >
                <div className="container-fluid dashboard-wrapper">
                    <DraggableWrapper dashboard={this.props.location.pathname} />
                </div>

                <BarChart chartClass="chartone" responsive={false} data={data}/>
                <BarChart chartClass="charttwo" responsive={true} data={data2}/>
                <BarChart chartClass="charthree" responsive={true} data={data3}/>
            </Container>
        );
    }
}

Dashboard.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Dashboard = connect()(Dashboard);

export default Dashboard
