import React, { Component } from 'react';
import * as d3 from 'd3';

class PieChartComponent extends Component {
    constructor(props){
        super(props);
    }

    componentDidMount() {
        const {data, responsive, fields, groupBy, colors} = this.props;

        const color = d3.scaleOrdinal()
            .range(colors);

        const dimensions = this.setDimensions();
        this.drawChart(dimensions.width, dimensions.height, dimensions.pie, 
                        dimensions.arc, data, color, dimensions.radius);
        if(responsive){
            this.addResponsive(data, color);
        }

    }

    setDimensions = (width=400, height=400) => {
        const {chartClass, responsive, fields} = this.props;
        let chartWidth = width;
        let chartHeight = height;

        const keys = fields.map(field => field.fieldName);

        if(responsive) {
            const wrapperWidth = document.getElementsByClassName(chartClass+"-wrapper")[0].offsetWidth;
            chartWidth = wrapperWidth;
            chartHeight = height;
        }
        const radius = Math.min(chartWidth, chartHeight) / 2;
        const arc = d3.arc().outerRadius(radius * 0.8).innerRadius(0);
        const pie = d3.pie()
            .sort(null)
            .value( d => d[fields[0].fieldName]);

        return {
                width: chartWidth,
                height: chartHeight,
                radius: radius,
                arc: arc,
                pie: pie
            };
    }

    drawChart = (width, height, pie, arc, data, color, radius) => {
        const {chartClass, fields, groupBy} = this.props;

        var svg = d3.select('.' + chartClass)
            .attr('width', width)
            .attr('height', height)
            .append('g');
            
        var chart = svg
            .attr('width', width)
            .attr('height', height)
            .append('g')
            .attr("class", "chart")
            .attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')');

        chart.append("g")
            .attr("class", "slices");
        chart.append("g")
            .attr("class", "labels");
        chart.append("g")
            .attr("class", "lines");

        var g = d3.select('.slices').selectAll('.arc')
            .data(pie(data))
            .enter().append('g')
            .attr('class', 'arc');

        g.append('path')
            .attr('d', arc)
            .style('fill', d => color(d.data[fields[0].fieldName]));

        g.append('text')
            .attr('transform', d => 'translate(' + arc.centroid(d) + ')')
            .attr('dy', '.35em')
            .text(d => d.data[groupBy.fieldName]);


        // this.drawLegend(svg, width, height, color);
    }

    drawLegend = (svg, width, height, color) => {

        const {groupBy, data} = this.props;

        var legend = svg
            .attr('width', width)
            .attr('height', 0.30*height)
            .append('g')
            .attr("class", "legends")
            .attr('transform', 'translate(' + 0 + ',' + 0.30*height / 2 + ')');

        var legendRectSize = 18;
        var legendSpacing = 4;

        var legendItem = legend.selectAll('.legend')
        .data(color.domain())
        .enter()
        .append('g')
        .attr('class', 'legend')
        .attr('transform', function(d, i) {
            
            var height = legendRectSize + legendSpacing;
            var offset =  height * color.domain().length / 2;
            var horz = -2 * legendRectSize;
            var vert = i * height - offset;
            return 'translate(' + 0 + ',' + vert + ')';
        });

        legendItem.append('rect')
        .attr('width', legendRectSize)
        .attr('height', legendRectSize)
        .style('fill', color)
        .style('stroke', color);

        legendItem.append('text')
        .attr('x', legendRectSize + legendSpacing)
        .attr('y', legendRectSize - legendSpacing)
        .text(function(d, i) { 
            return data[i][groupBy.fieldName]; 
        });
    }

    addResponsive = (data, color) => {
        const {chartClass} = this.props;
        const chartWrapp = document.getElementsByClassName(chartClass+"-wrapper")[0];

        d3.select(window)
          .on("resize."+chartClass, () => {
            this.clearChart();
            const dimensions = this.setDimensions(chartWrapp.offsetWidth);
            this.drawChart(dimensions.width, dimensions.height, dimensions.pie, 
                            dimensions.arc, data, color);
          });
    }

    clearChart = () => {
        const {chartClass} = this.props;
        document.getElementsByClassName(chartClass)[0].childNodes[0].remove();
    }

    render() {
        const {chartClass} = this.props;
        return (
            <div className={chartClass+"-wrapper" + ' chart-wrapper'}>
                <svg className={chartClass}></svg>
            </div>
        );
    }
}

export default PieChartComponent;
