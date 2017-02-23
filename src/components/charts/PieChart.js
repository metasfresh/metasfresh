import React, { Component } from 'react';
import * as d3 from 'd3';

class PieChart extends Component {
    constructor(props){
        super(props);
    }

    componentDidMount() {

        var data = [1, 1, 2, 3, 5, 8, 13, 21];

        var canvas = document.querySelector('canvas'),
            context = canvas.getContext('2d');

        var width = canvas.width,
            height = canvas.height,
            outerRadius = height / 2 - 30,
            innerRadius = 0;/*outerRadius / 3;*/

        var arc = d3.arc()
            .innerRadius(innerRadius)
            .outerRadius(outerRadius)
            .context(context);

        var pie = d3.pie();

        var ease = d3.easeCubicInOut,
            duration = 2500;

        d3.timer(function(elapsed) {
        var t = ease(1 - Math.abs((elapsed % duration) / duration - 0.5) * 2),
            arcs = pie(data);

        context.save();
        context.clearRect(0, 0, width, height);
        context.translate(width / 2, height / 2);

        context.beginPath();
        arcs.forEach(arc.padAngle(0));
        context.lineWidth = 1;
        context.strokeStyle = '#777';
        context.stroke();

        context.beginPath();
        arcs.forEach(arc.padAngle(0.06 * t));
        context.fillStyle = '#ccc';
        context.fill();
        context.lineWidth = 1.5;
        context.lineJoin = 'round';
        context.strokeStyle = '#000';
        context.stroke();

        context.restore();
        });
    }


    render() {


        return (
            <div className="chart-wrapp">
                <canvas width="200" height="200"></canvas>
            </div>
        );
    }
}

export default PieChart;
