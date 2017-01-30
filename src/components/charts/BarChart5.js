import React, { Component } from 'react';
import * as d3 from "d3";

class BarChart extends Component {
    constructor(props){
        super(props);
    }

    componentDidMount() {
        var data = [
            {name: "Locke",    value:  44},
            {name: "Reyes",    value:  8},
            {name: "Ford",     value: 15},
            {name: "Jarrah",   value: 36},
            {name: "Shephard", value: 13},
            {name: "Kwon",     value: 12}
        ];

        var margin = {top: 20, right: 30, bottom: 30, left: 40},
            width = 400 - margin.left - margin.right,
            height = 200 - margin.top - margin.bottom;

        var x = d3.scaleBand()
            .domain(data.map(function(d) { return d.name; }))
            .rangeRound([0, width], .1);

        var y = d3.scaleLinear()
            .domain([0, d3.max(data, function(d) { return d.value; })])
            .range([height, 0]);

        var xAxis = d3.axisBottom()
            .scale(x);

        var yAxis = d3.axisLeft()
            .scale(y);

        var chart = d3.select(".chart5")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")")
            .call(d3.zoom().scaleExtent([1, 10]).on("zoom", zoom));

        //Add a clip path, so the content outside the domain should be hidden

        var mSvg = d3.select(".chart5");



        //Add a "defs" element to the svg
        var defs = mSvg.append("defs");

        //Append a clipPath element to the defs element, and a Shape
        // to define the cliping area
        defs.append("clipPath").attr('id','my-clip-path').append('rect')
            .attr('width',width) //Set the width of the clipping area
            .attr('height',height); // set the height of the clipping area

        //clip path for x axis
        defs.append("clipPath").attr('id','x-clip-path').append('rect')
            .attr('width',width) //Set the width of the clipping area
            .attr('height',height + margin.bottom); // set the height of the clipping area

        //add a group that will be clipped (this will contain the bars)
        var barsGroup = chart.append('g');

        //Set the clipping path to the group (g element) that you want to clip
        barsGroup.attr('clip-path','url(#my-clip-path)');

        var xAxisGroup = chart.append("g").attr('class','x-axis')

        xAxisGroup.append('g')
            .attr("class", "x axis")
            .attr("transform", "translate(0," + height + ")")
            .call(xAxis);

        //The xAxis is scalled on zoom, so we need to clip it to

        xAxisGroup.attr('clip-path','url(#x-clip-path)');

        chart.append("g")
            .attr("class", "y axis")
            .call(yAxis);

        var bars = barsGroup.selectAll(".bar")
            .data(data)
            .enter().append("rect")
            .attr("class", "bar")
            .attr("x", function(d) { return x(d.name); })
            .attr("y", function(d) { return y(d.value); })
            .attr("height", function(d) { return height - y(d.value); })
            .attr("width", 50);

        function zoom() {
            bars.attr("transform", d3.event.transform);
            chart.select(".x.axis").attr("transform", d3.event.transform)
                .call(xAxis);
            chart.select(".y.axis").call(yAxis);
        }
    }
    

    render() {


        return (
            <div className="chart-bar">
                <svg className="chart5"></svg>
            </div>
        );
    }
}

export default BarChart;
