import React, { Component } from 'react';
import * as d3 from 'd3';

class PieChartComponent extends Component {
    constructor(props){
        super(props);
    }

    componentDidMount() {
        var data = [
            { label: 'Alice', value: 12 },
            { label: 'Bob', value: 9 },
            { label: 'Carol', value: 14 },
            { label: 'Dwayne', value: 15 },
            { label: 'Anne', value: 8 },
            { label: 'Robin', value: 28 },
            { label: 'Eve', value: 12 },
            { label: 'Karen', value: 6 },
            { label: 'Lisa', value: 22 },
            { label: 'Tom', value: 18 }
        ];

        var color = d3.scaleOrdinal()
            .range(['#98abc5', '#8a89a6', '#7b6888', '#6b486b', '#a05d56', '#d0743c', '#ff8c00']);

        var dimensions = this.setDimensions();
        this.drawChart(dimensions.width, dimensions.height,
                                dimensions.pie, dimensions.arc, data, color);

    }

    setDimensions = (width=800, height=600) => {
        var radius = Math.min(width, height) / 2;

        var arc = d3.arc()
            .outerRadius(radius * 0.8)
            .innerRadius(radius * 0.4);

        var pie = d3.pie()
            .sort(null)
            .value(function(d) { return d.value; });

        return {
                width: width,
                height:height,
                radius: radius,
                arc: arc,
                pie: pie
            };
    }

    drawChart = (width, height, pie, arc, data, color) => {
        var svg = d3.select('body').append('svg')
            .attr('width', width)
            .attr('height', height)
            .append('g')
            .attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')');

        var g = svg.selectAll('.arc')
            .data(pie(data))
            .enter().append('g')
            .attr('class', 'arc');

        g.append('path')
            .attr('d', arc)
            .style('fill', function(d) { return color(d.data.value); });

        g.append('text')
            .attr('transform', function(d) { return 'translate(' + arc.centroid(d) + ')'; })
            .attr('dy', '.35em')
            .text(function(d) { return d.data.label; });
    }



    render() {
        return (
            <div>
                <svg className="piechart"></svg>
            </div>
        );
    }
}

export default PieChartComponent;
