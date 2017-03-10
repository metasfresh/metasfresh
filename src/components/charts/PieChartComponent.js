import React, { Component } from 'react';
import * as d3 from 'd3';

class PieChartComponent extends Component {
    constructor(props){
        super(props);
    }

    componentDidMount() {
        const {data, responsive, colors} = this.props;

        const color = d3.scaleOrdinal()
            .range(colors);

        const dimensions = this.setDimensions();
        this.drawChart(dimensions.wrapperWidth, dimensions.width, dimensions.height, dimensions.pie,
                        dimensions.arc, data, color, dimensions.radius);
        if(responsive){
            this.addResponsive(data, color);
        }

    }

    setDimensions = (width=400) => {
        const {chartClass, responsive, fields, height} = this.props;
        let wrapperWidth = 0;
        let chartWidth = width;
        let chartHeight = height;

        if(responsive) {
            wrapperWidth = document.getElementsByClassName(chartClass+'-wrapper')[0].offsetWidth;
            if(wrapperWidth<height){
                chartWidth = wrapperWidth;
            }

        }
        const radius = Math.min(chartWidth, 0.66*chartHeight) / 2;
        const arc = d3.arc().outerRadius(radius * 0.8).innerRadius(radius * 0.4);
        const pie = d3.pie()
            .sort(null)
            .value( d => d[fields[0].fieldName]);

        return {
                wrapperWidth: wrapperWidth,
                width: chartWidth,
                height: chartHeight,
                radius: radius,
                arc: arc,
                pie: pie
            };
    }

    drawChart = (wrapperWidth, width, height, pie, arc, data, color) => {
        const {chartClass, fields} = this.props;

        var svg = d3.select('.' + chartClass)
            .attr('width', width)
            .attr('height', height)
            .append('g');

        var chart = svg
            .attr('width', width)
            .attr('height', height)
            .append('g')
            .attr('class', 'chart')
            .attr('transform', 'translate(' + wrapperWidth / 2 + ',' + 0.66*height + ')');

        chart.append('g')
            .attr('class', 'slices');
        chart.append('g')
            .attr('class', 'labels');
        chart.append('g')
            .attr('class', 'lines');

        var g = d3.select('.slices').selectAll('.arc')
            .data(pie(data))
            .enter().append('g')
            .attr('class', 'arc');

        g.append('path')
            .attr('d', arc)
            .attr('class', 'pie-path')
            .style('fill', d => color(d.data[fields[0].fieldName]));

        this.drawLegend(svg, width, height, color);
    };

    drawLegend = (svg, width, height, color) => {
        const {groupBy, data} = this.props;

        var legend = svg
            .attr('width', width)
            .attr('height', 0.30*height)
            .append('g')
            .attr('class', 'legends')
            .attr('transform', 'translate(' + 20 + ',' + 20 + ')');

        var legendRectSize = 18;
        var legendSpacing = 4;

        var legendItem = legend.selectAll('.legend')
        .data(color.domain())
        .enter()
        .append('g')
        .attr('class', 'legend')
        .attr('transform', function(d, i) {
            var height = legendRectSize + legendSpacing;
            var vert = i * height;
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
        .attr('font-size', 12)
        .text(function(d, i) {
            return data[i][groupBy.fieldName];
        });
    };

    addResponsive = (data, color) => {
        const {chartClass} = this.props;
        const chartWrap = document.getElementsByClassName(chartClass+'-wrapper')[0];

        d3.select(window)
            .on('resize.'+chartClass, () => {
                this.clearChart();
                const dimensions = this.setDimensions(chartWrap.offsetWidth);
                this.drawChart(dimensions.wrapperWidth, dimensions.width, dimensions.height, dimensions.pie,
                                dimensions.arc, data, color);
            });
    };

    clearChart = () => {
        const {chartClass} = this.props;
        document.getElementsByClassName(chartClass)[0].childNodes[0].remove();
    };

    render() {
        const {chartClass} = this.props;
        return (
            <div className={chartClass+'-wrapper' + ' chart-wrapper'}>
                <svg className={chartClass} />
            </div>
        );
    }
}

export default PieChartComponent;
