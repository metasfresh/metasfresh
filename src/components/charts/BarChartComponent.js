import React, { Component } from 'react';
import * as d3 from 'd3';

class BarChartComponent extends Component {
    constructor(props){
        super(props);
    }

    componentDidMount() {
        var data = [
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
        var dimensions = this.setDimensions();
        var ranges = this.setRanges(dimensions.width, dimensions.height, data);
        var svg = this.drawChart(dimensions, ranges, data);
        this.addAxis(svg, dimensions, ranges);

    }

    setDimensions = (mTop=20, mRight=20, mBottom=20, mLeft=20, mwidth=600, mheight=400) => {
        var margin = {top: mTop, right: mRight, bottom: mBottom, left: mLeft},
            width = mwidth - margin.left - margin.right,
            height = mheight - margin.top - margin.bottom;
            return {
                margin: {
                    top: margin.top,
                    right: margin.right,
                    bottom: margin.bottom,
                    left: margin.left
                },
                width: width,
                height:height
            };
    }
    setRanges = (width, height, data) => {
        var x = d3.scaleBand().range([0,width]).padding(0.1);
        var y = d3.scaleLinear().range([height, 0]);

        x.domain(data.map(function(d) { return d.name; }));
        y.domain([0, d3.max(data, function(d) { return d.value; })]);

        return {x: x, y: y};
    }

    drawChart = (dimensions, ranges, data) => {
        var svg = d3.select('.barchart')
            .attr('width', dimensions.width + dimensions.margin.left + dimensions.margin.right)
            .attr('height', dimensions.height + dimensions.margin.top + dimensions.margin.bottom)
            .append('g')
            .attr('transform',
                'translate(' + dimensions.margin.left + ',' + dimensions.margin.top + ')');

        svg.selectAll('.bar')
            .data(data)
            .enter().append('rect')
            .attr('class', 'bar')
            .attr('x', function(d) { return ranges.x(d.name); })
            .attr('width', ranges.x.bandwidth())
            .attr('y', function(d) { return ranges.y(d.value); })
            .attr('height', function(d) { return dimensions.height - ranges.y(d.value); })

            return svg;
    }

    addAxis = (svg, dimensions, ranges) => {
        // add the x Axis
        svg.append('g')
            .attr('transform', 'translate(0,' + dimensions.height + ')')
            .call(d3.axisBottom(ranges.x));

        // add the y Axis
        svg.append('g')
            .call(d3.axisLeft(ranges.y));
    }

    clearChart = () => {
        document.getElementsByClassName('barchart')[0].childNodes[0].remove();
    }

    render() {

        return (
            <div>
                <svg className="barchart"></svg>
            </div>
        );
    }
}

export default BarChartComponent;
