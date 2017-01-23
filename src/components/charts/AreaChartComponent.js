import React, { Component } from 'react';
import * as d3 from "d3";

class BarChartComponent extends Component {
    constructor(props){
        super(props);
    }

    componentDidMount() {
        var data = [
            {name: 0, value: 2},
            {name: 1, value: 3},
            {name: 2, value: 1},
            {name: 3, value: 5},
            {name: 4, value: 8},
            {name: 5, value: 28},
            {name: 6, value: 12},
            {name: 7, value: 6},
            {name: 8, value: 22},
            {name: 9, value: 18},
            {name: 10, value: 38},
            {name: 11, value: 58},
            {name: 12, value: 48},
            {name: 13, value: 36},
            {name: 14, value: 118},
            {name: 15, value: 108},
            {name: 16, value: 98},
            {name: 17, value: 110},
            {name: 18, value: 56}
        ];
        var dimensions = this.setDimensions();
        var ranges = this.setRanges(dimensions.width, dimensions.height, data);
        var svg = this.drawChart(dimensions, ranges, data);
        this.addAxis(svg, dimensions, ranges);

    }

    setDimensions = (mTop=20, mRight=20, mBottom=20, mLeft=20, width=600, height=400) => {
        var margin = {top: mTop, right: mRight, bottom: mBottom, left: mLeft},
            width = width - margin.left - margin.right,
            height = height - margin.top - margin.bottom;
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

        var x = d3.scaleLinear().range([0, width]);
        var y = d3.scaleLinear().range([height, 0]);

        var xAxis = d3.axisBottom().scale(x);
        var yAxis = d3.axisLeft().scale(y);

        x.domain([0, d3.max(data, function(d) { return d.name; })])
        y.domain([0, d3.max(data, function(d) { return d.value; })]);

        return {x: x, y: y, xAxis: xAxis, yAxis: yAxis};
    }

    drawChart = (dimensions, ranges, data) => {
        // var parseTime = d3.timeParse("%d-%b-%y");
        var data = data;
        data.forEach(function(d) {
            d.name = +d.name;
            d.value = +d.value;
        });
        var svg = d3.select(".areachart")
            .attr("width", dimensions.width + dimensions.margin.left + dimensions.margin.right)
            .attr("height", dimensions.height + dimensions.margin.top + dimensions.margin.bottom)
        .append("g")
            .attr("transform", 
                "translate(" + dimensions.margin.left + "," + dimensions.margin.top + ")");

        var area = d3.area()
            .x(function(d) { return ranges.x(d.name); })
            .y0(dimensions.height)
            .y1(function(d) { return ranges.y(d.value); });

        var valueline = d3.line()
            .x(function(d) { return x(d.name); })
            .y(function(d) { return y(d.value); });

        svg.append("path")
            .datum(data)
            .attr("fill", "steelblue")
            .attr("class", "area")
            .attr("d", area);

            return svg;
    }

    addAxis = (svg, dimensions, ranges) => {
        svg.append("g")
            .attr("class", "x axis")
            .attr("transform", "translate(0," + dimensions.height + ")")
            .call(ranges.xAxis);
        
        svg.append("g")
            .attr("class", "y axis")
            .call(ranges.yAxis)
            .append("text")
            .attr("transform", "rotate(-90)")
            .attr("y", 6)
            .attr("dy", ".71em")
            .style("text-anchor", "end")
            .text("Price ($)");
    }

    clearChart = () => {
        document.getElementsByClassName('barchart')[0].childNodes[0].remove();
    }
    

    render() {


        return (
            <div>
                <svg className="areachart"></svg>
            </div>
        );
    }
}

export default BarChartComponent;
