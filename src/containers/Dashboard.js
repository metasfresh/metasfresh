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
            {name: 'Alice', value: 2},
            {name: 'Bob', value: 3},
            {name: 'Carol', value: 1},
            {name: 'Dwayne', value: 5},
            {name: 'Anne', value: 8},
            {name: 'Robin', value: 28},
            {name: 'Eve', value: 12},
            {name: 'Karen', value: 6},
            {name: 'Lisa', value: 22},
            {name: 'Tom', value: 18}
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

        const data4 = {
            "took": "9.853 ms",
            "itemId": 1000013,
            "range": {
                "fromMillis": 0,
                "toMillis": 1488540082884
            },
            "datasets": [
                {
                    "name": "agg",
                    "values": [
                        {
                            "_key": "2016-09-08T00:00:00.000+02:00",
                            "Counter": 3,
                            "DateOrdered": "2016-09-08T00:00:00.000+02:00"
                        },
                        {
                            "_key": "2016-10-25T00:00:00.000+02:00",
                            "Counter": 1,
                            "DateOrdered": "2016-10-25T00:00:00.000+02:00"
                        },
                        {
                            "_key": "2016-11-11T00:00:00.000+01:00",
                            "Counter": 1,
                            "DateOrdered": "2016-11-11T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-12T00:00:00.000+01:00",
                            "Counter": 1,
                            "DateOrdered": "2016-11-12T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-13T00:00:00.000+01:00",
                            "Counter": 1,
                            "DateOrdered": "2016-11-13T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-14T00:00:00.000+01:00",
                            "Counter": 5,
                            "DateOrdered": "2016-11-14T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-15T00:00:00.000+01:00",
                            "Counter": 9,
                            "DateOrdered": "2016-11-15T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-16T00:00:00.000+01:00",
                            "Counter": 1,
                            "DateOrdered": "2016-11-16T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-17T00:00:00.000+01:00",
                            "Counter": 5,
                            "DateOrdered": "2016-11-17T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-18T00:00:00.000+01:00",
                            "Counter": 1,
                            "Counter_offset": 1,
                            "_DateOrdered_offset": "2016-11-11T00:00:00.000+01:00",
                            "DateOrdered": "2016-11-18T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-19T00:00:00.000+01:00",
                            "Counter": 5,
                            "Counter_offset": 1,
                            "_DateOrdered_offset": "2016-11-12T00:00:00.000+01:00",
                            "DateOrdered": "2016-11-19T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-20T00:00:00.000+01:00",
                            "Counter": 5,
                            "Counter_offset": 1,
                            "_DateOrdered_offset": "2016-11-13T00:00:00.000+01:00",
                            "DateOrdered": "2016-11-20T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-21T00:00:00.000+01:00",
                            "Counter": 15,
                            "Counter_offset": 5,
                            "_DateOrdered_offset": "2016-11-14T00:00:00.000+01:00",
                            "DateOrdered": "2016-11-21T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-22T00:00:00.000+01:00",
                            "Counter": 24,
                            "Counter_offset": 9,
                            "_DateOrdered_offset": "2016-11-15T00:00:00.000+01:00",
                            "DateOrdered": "2016-11-22T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-23T00:00:00.000+01:00",
                            "Counter": 9,
                            "Counter_offset": 1,
                            "_DateOrdered_offset": "2016-11-16T00:00:00.000+01:00",
                            "DateOrdered": "2016-11-23T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-24T00:00:00.000+01:00",
                            "Counter": 3,
                            "Counter_offset": 5,
                            "_DateOrdered_offset": "2016-11-17T00:00:00.000+01:00",
                            "DateOrdered": "2016-11-24T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-25T00:00:00.000+01:00",
                            "Counter": 36,
                            "Counter_offset": 1,
                            "_DateOrdered_offset": "2016-11-18T00:00:00.000+01:00",
                            "DateOrdered": "2016-11-25T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-27T00:00:00.000+01:00",
                            "Counter": 5,
                            "Counter_offset": 5,
                            "_DateOrdered_offset": "2016-11-20T00:00:00.000+01:00",
                            "DateOrdered": "2016-11-27T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-28T00:00:00.000+01:00",
                            "Counter": 14,
                            "Counter_offset": 15,
                            "_DateOrdered_offset": "2016-11-21T00:00:00.000+01:00",
                            "DateOrdered": "2016-11-28T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-29T00:00:00.000+01:00",
                            "Counter": 35,
                            "Counter_offset": 24,
                            "_DateOrdered_offset": "2016-11-22T00:00:00.000+01:00",
                            "DateOrdered": "2016-11-29T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-11-30T00:00:00.000+01:00",
                            "Counter": 11,
                            "Counter_offset": 9,
                            "_DateOrdered_offset": "2016-11-23T00:00:00.000+01:00",
                            "DateOrdered": "2016-11-30T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-01T00:00:00.000+01:00",
                            "Counter": 25,
                            "Counter_offset": 3,
                            "_DateOrdered_offset": "2016-11-24T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-01T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-02T00:00:00.000+01:00",
                            "Counter": 4,
                            "Counter_offset": 36,
                            "_DateOrdered_offset": "2016-11-25T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-02T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-04T00:00:00.000+01:00",
                            "Counter": 6,
                            "Counter_offset": 5,
                            "_DateOrdered_offset": "2016-11-27T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-04T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-05T00:00:00.000+01:00",
                            "Counter": 19,
                            "Counter_offset": 14,
                            "_DateOrdered_offset": "2016-11-28T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-05T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-06T00:00:00.000+01:00",
                            "Counter": 55,
                            "Counter_offset": 35,
                            "_DateOrdered_offset": "2016-11-29T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-06T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-07T00:00:00.000+01:00",
                            "Counter": 36,
                            "Counter_offset": 11,
                            "_DateOrdered_offset": "2016-11-30T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-07T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-08T00:00:00.000+01:00",
                            "Counter": 22,
                            "Counter_offset": 25,
                            "_DateOrdered_offset": "2016-12-01T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-08T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-09T00:00:00.000+01:00",
                            "Counter": 6,
                            "Counter_offset": 4,
                            "_DateOrdered_offset": "2016-12-02T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-09T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-11T00:00:00.000+01:00",
                            "Counter": 6,
                            "Counter_offset": 6,
                            "_DateOrdered_offset": "2016-12-04T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-11T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-12T00:00:00.000+01:00",
                            "Counter": 16,
                            "Counter_offset": 19,
                            "_DateOrdered_offset": "2016-12-05T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-12T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-13T00:00:00.000+01:00",
                            "Counter": 4,
                            "Counter_offset": 55,
                            "_DateOrdered_offset": "2016-12-06T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-13T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-14T00:00:00.000+01:00",
                            "Counter": 11,
                            "Counter_offset": 14,
                            "_DateOrdered_offset": "2016-12-07T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-14T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-15T00:00:00.000+01:00",
                            "Counter": 5,
                            "Counter_offset": 22,
                            "_DateOrdered_offset": "2016-12-08T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-15T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-16T00:00:00.000+01:00",
                            "Counter": 6,
                            "Counter_offset": 6,
                            "_DateOrdered_offset": "2016-12-09T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-16T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-18T00:00:00.000+01:00",
                            "Counter": 6,
                            "Counter_offset": 6,
                            "_DateOrdered_offset": "2016-12-11T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-18T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-19T00:00:00.000+01:00",
                            "Counter": 12,
                            "Counter_offset": 16,
                            "_DateOrdered_offset": "2016-12-12T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-19T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-20T00:00:00.000+01:00",
                            "Counter": 6,
                            "Counter_offset": 4,
                            "_DateOrdered_offset": "2016-12-13T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-20T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-21T00:00:00.000+01:00",
                            "Counter": 12,
                            "Counter_offset": 11,
                            "_DateOrdered_offset": "2016-12-14T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-21T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-22T00:00:00.000+01:00",
                            "Counter": 16,
                            "Counter_offset": 5,
                            "_DateOrdered_offset": "2016-12-15T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-22T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2016-12-23T00:00:00.000+01:00",
                            "Counter": 1,
                            "Counter_offset": 6,
                            "_DateOrdered_offset": "2016-12-16T00:00:00.000+01:00",
                            "DateOrdered": "2016-12-23T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-04T00:00:00.000+01:00",
                            "Counter": 12,
                            "DateOrdered": "2017-01-04T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-05T00:00:00.000+01:00",
                            "Counter": 8,
                            "DateOrdered": "2017-01-05T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-09T00:00:00.000+01:00",
                            "Counter": 16,
                            "DateOrdered": "2017-01-09T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-11T00:00:00.000+01:00",
                            "Counter": 15,
                            "Counter_offset": 12,
                            "_DateOrdered_offset": "2017-01-04T00:00:00.000+01:00",
                            "DateOrdered": "2017-01-11T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-12T00:00:00.000+01:00",
                            "Counter": 3,
                            "Counter_offset": 8,
                            "_DateOrdered_offset": "2017-01-05T00:00:00.000+01:00",
                            "DateOrdered": "2017-01-12T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-16T00:00:00.000+01:00",
                            "Counter": 2,
                            "Counter_offset": 16,
                            "_DateOrdered_offset": "2017-01-09T00:00:00.000+01:00",
                            "DateOrdered": "2017-01-16T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-17T00:00:00.000+01:00",
                            "Counter": 2,
                            "DateOrdered": "2017-01-17T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-18T00:00:00.000+01:00",
                            "Counter": 11,
                            "Counter_offset": 15,
                            "_DateOrdered_offset": "2017-01-11T00:00:00.000+01:00",
                            "DateOrdered": "2017-01-18T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-19T00:00:00.000+01:00",
                            "Counter": 3,
                            "Counter_offset": 3,
                            "_DateOrdered_offset": "2017-01-12T00:00:00.000+01:00",
                            "DateOrdered": "2017-01-19T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-20T00:00:00.000+01:00",
                            "Counter": 1,
                            "DateOrdered": "2017-01-20T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-21T00:00:00.000+01:00",
                            "Counter": 7,
                            "DateOrdered": "2017-01-21T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-25T00:00:00.000+01:00",
                            "Counter": 2,
                            "Counter_offset": 11,
                            "_DateOrdered_offset": "2017-01-18T00:00:00.000+01:00",
                            "DateOrdered": "2017-01-25T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-26T00:00:00.000+01:00",
                            "Counter": 14,
                            "Counter_offset": 3,
                            "_DateOrdered_offset": "2017-01-19T00:00:00.000+01:00",
                            "DateOrdered": "2017-01-26T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-27T00:00:00.000+01:00",
                            "Counter": 4,
                            "Counter_offset": 1,
                            "_DateOrdered_offset": "2017-01-20T00:00:00.000+01:00",
                            "DateOrdered": "2017-01-27T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-28T00:00:00.000+01:00",
                            "Counter": 1,
                            "Counter_offset": 7,
                            "_DateOrdered_offset": "2017-01-21T00:00:00.000+01:00",
                            "DateOrdered": "2017-01-28T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-30T00:00:00.000+01:00",
                            "Counter": 5,
                            "DateOrdered": "2017-01-30T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-01-31T00:00:00.000+01:00",
                            "Counter": 2,
                            "DateOrdered": "2017-01-31T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-02-01T00:00:00.000+01:00",
                            "Counter": 2,
                            "Counter_offset": 2,
                            "_DateOrdered_offset": "2017-01-25T00:00:00.000+01:00",
                            "DateOrdered": "2017-02-01T00:00:00.000+01:00"
                        },
                        {
                            "_key": "2017-02-02T00:00:00.000+01:00",
                            "Counter": 7,
                            "Counter_offset": 14,
                            "_DateOrdered_offset": "2017-01-26T00:00:00.000+01:00",
                            "DateOrdered": "2017-02-02T00:00:00.000+01:00"
                        },
                    ]
                }
            ]
        };
        const layout4 = {
            "id": 1000013,
            "caption": "Order lines / day",
            "seqNo": 10,
            "kpi": {
                "caption": "Order lines / day",
                "chartType": "BarChart",
                "pollIntervalSec": 10,
                "groupByField": {
                    "caption": "DateOrdered",
                    "unit": "day",
                    "fieldName": "DateOrdered",
                    "dataType": "Date"
                },
                "fields": [
                    {
                        "caption": "Number of docs",
                        "unit": null,
                        "fieldName": "Counter",
                        "groupBy": false,
                        "dataType": "Number"
                    },
                    {
                        "caption": "Number of docs",
                        "unit": null,
                        "fieldName": "Counter_offset",
                        "groupBy": false,
                        "dataType": "Number"
                    }
                ]
            }
        };

        return (
            <Container
                siteName = "Dashboard"
                noMargin = {true}
            >
                <div className="container-fluid dashboard-wrapper">
                    <DraggableWrapper dashboard={this.props.location.pathname} />
                </div>

                <BarChart chartClass="chartone" responsive={true} layout={layout4.kpi} data={data4.datasets[0].values}/>
            </Container>
        );
    }
}

Dashboard.propTypes = {
    dispatch: PropTypes.func.isRequired
};

Dashboard = connect()(Dashboard);

export default Dashboard
