import React, { Component }from 'react';
import * as d3 from 'd3';

import { getX0Range, getX1Range, getYRange, getZRange } from './ranges';
import { addXAxis, addYAxis, moveXAxis, getXAxisLabelsHeight } from './axes';
import { getSvg, sizeSvg } from './svg';
import { getHorizontalDimensions, getVerticalDimensions } from './dimensions';
import { drawData } from './data';

class BarChartComponent extends Component {
    draw(){
        const { data, groupBy, fields, colors, chartClass, responsive } = this.props;

        // main container
        const svg = getSvg(chartClass);

        // colors
        const rangeZ = getZRange(colors);

        // horizontal sizing
        const horizontal = getHorizontalDimensions(responsive, chartClass);
        const rangeX0 = getX0Range(horizontal.width, data, groupBy);
        const rangeX1 = getX1Range(rangeX0.bandwidth(), fields);
        addXAxis(svg, rangeX0);

        // vertical sizing
        const labelsHeight = getXAxisLabelsHeight(svg);
        const vertical = getVerticalDimensions({bottom: labelsHeight, top: 5});
        const rangeY = getYRange(vertical.height, data, fields);
        addYAxis(svg, rangeY);

        // adjust x axis
        moveXAxis(svg, vertical.height);

        // adjust svg container
        sizeSvg(svg, chartClass, {
            ...horizontal,
            ...vertical
        });

        // draw bars
        drawData(svg, {
            ...horizontal,
            ...vertical
        }, {
            x0: rangeX0,
            x1: rangeX1,
            y: rangeY,
            z: rangeZ
        }, data, groupBy.fieldName);
    }

    clear = () => {
        const { chartClass } = this.props;
        const svgChildren = document.getElementsByClassName(chartClass)[0].childNodes;

        if (svgChildren.length){
            svgChildren[0].remove();
        }
    };

    redraw = () => {
        this.clear();
        this.draw();
    };

    addResponsive = () => {
        const { chartClass } = this.props;

        d3.select(window)
            .on('resize.' + chartClass, () => this.redraw())
    };

    componentDidMount() {
        const { responsive, data } = this.props;

        data.length && this.draw();

        if(responsive){
            this.addResponsive();
        }
    }

    componentDidUpdate(){
        this.redraw();
    }

    render() {
        const {chartClass} = this.props;

        return (
            <div className={chartClass + '-wrapper'}>
                <svg className={chartClass} />
            </div>
        );
    }
}

export default BarChartComponent;
