import React, { Component } from 'react';
import * as d3 from 'd3';

class PieChartComponent extends Component {
    constructor(props){
        super(props);
    }

    componentDidMount() {
        const {data, responsive, fields, groupBy} = this.props;

        const color = d3.scaleOrdinal()
            .range(['#98abc5', '#8a89a6', '#7b6888', '#6b486b', '#a05d56', '#d0743c', '#ff8c00']);

        const dimensions = this.setDimensions();
        this.drawChart(dimensions.width, dimensions.height, dimensions.pie, 
                        dimensions.arc, data, color);
        if(responsive){
            this.addResponsive(data, color);
        }

    }

    setDimensions = (width=400, height=200) => {
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
        const arc = d3.arc().outerRadius(radius * 0.8).innerRadius(radius * 0.4);
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

    drawChart = (width, height, pie, arc, data, color) => {
        const {chartClass, fields, groupBy} = this.props;
        var svg = d3.select('.' + chartClass)
            .attr('width', width)
            .attr('height', height)
            .append('g')
            .attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')');

        svg.append("g")
            .attr("class", "slices");
        svg.append("g")
            .attr("class", "labels");
        svg.append("g")
            .attr("class", "lines");

        var g = d3.select('.slices').selectAll('.arc')
            .data(pie(data))
            .enter().append('g')
            .attr('class', 'arc');

        g.append('path')
            .attr('d', arc)
            .style('fill', d => color(d.data[fields[0].fieldName]));

        // g.append('text')
        //     .attr('transform', d => 'translate(' + arc.centroid(d) + ')')
        //     .attr('dy', '.35em')
        //     .text(d => d.data[groupBy.fieldName]);





//----------------------------------//




        var key = d => d.data[groupBy.fieldName];

        var text = svg.select(".labels").selectAll("text")
		.data(pie(data), key);

        text.enter()
		.append("text")
		.attr("dy", ".35em")
		.text(d => d.data[groupBy.fieldName])
        .attr("transform", d => {
            // console.log(d);
                this._current = this._current || d;
                const interpolate = d3.interpolate(this._current, d);
                this._current = interpolate(0);
                return function(t) {
                    console.log(t);
                    // var d2 = interpolate(t);
                    // var pos = outerArc.centroid(d2);
                    // pos[0] = radius * (midAngle(d2) < Math.PI ? 1 : -1);
                    return "translate(" + 0 + "," + 60 + ")"
                }

                   
                
                // "translate(" + 0 + "," + 60 + ")"}
        });
        

        function midAngle(d){
            console.log(d.startAngle);
            return d.startAngle + (d.endAngle - d.startAngle)/2;
        }

        text.transition().duration(1000)
            .attrTween("transform", function(d) {
                this._current = this._current || d;
                var interpolate = d3.interpolate(this._current, d);
                this._current = interpolate(0);
                return function(t) {
                    var d2 = interpolate(t);
                    var pos = outerArc.centroid(d2);
                    pos[0] = radius * (midAngle(d2) < Math.PI ? 1 : -1);
                    return "translate("+ pos +")";
                };
            })
            .styleTween("text-anchor", function(d){
                this._current = this._current || d;
                var interpolate = d3.interpolate(this._current, d);
                this._current = interpolate(0);
                return function(t) {
                    var d2 = interpolate(t);
                    return midAngle(d2) < Math.PI ? "start":"end";
                };
            });

        text.exit()
            .remove();


    }

    addResponsive = (data, color) => {
        // console.log('responsive');
        const {chartClass} = this.props;
        const chartWrapp = document.getElementsByClassName(chartClass+"-wrapper")[0];
        // console.log(chartWrapp.offsetWidth);
        d3.select(window)
          .on("resize."+chartClass, () => {
            // console.log('resizing');
            // console.log(chartClass);
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
            <div className={chartClass+"-wrapper"}>
                <svg className={chartClass}></svg>
            </div>
        );
    }
}

export default PieChartComponent;
