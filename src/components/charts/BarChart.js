import React, { Component } from 'react';
import * as d3 from "d3";

class BarChart extends Component {
    constructor(props){
        super(props);
    }

    componentDidMount() {
        
    }
    

    render() {


        return (
            <rect
                className="bar"
                x={400}
                y={300}
                width={600}
                height={200}
                fill={'blue'}
            />
        );
    }
}

export default BarChart;
