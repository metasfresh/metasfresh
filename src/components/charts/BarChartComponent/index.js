import React, { Component }from 'react';
import * as d3 from 'd3';

import { getX0Range, getX1Range, getYRange, getZRange } from './ranges';
import { populateXAxis, populateYAxis, moveXAxis, getXAxisLabelsHeight }
    from './axes';
import { getSvg, sizeSvg } from './svg';
import { getHorizontalDimensions, getVerticalDimensions } from './dimensions';
import { drawData } from './data';
import { drawLegend } from './legend';

class BarChartComponent extends Component {
    svg;

    setSvg(){
        const { chartClass } = this.props;

        this.svg = getSvg(chartClass);
    }

    prepare(){
        const {
            data, groupBy, fields, colors, chartClass, height
        } = this.props;

        // colors
        const rangeZ = getZRange(colors);

        // horizontal sizing
        const horizontal =
            getHorizontalDimensions(this.svg, chartClass, height);
        const rangeX0 = getX0Range(horizontal.width, data, groupBy);
        const rangeX1 = getX1Range(rangeX0.bandwidth(), fields);
        populateXAxis(this.svg, rangeX0);

        // vertical sizing
        const labelsHeight = getXAxisLabelsHeight(this.svg);
        const vertical =
            getVerticalDimensions({bottom: labelsHeight, top: 35}, height);
        const rangeY = getYRange(vertical.height, data, fields);
        populateYAxis(this.svg, rangeY);

        // adjust x axis
        moveXAxis(this.svg, vertical.height);

        // adjust svg container
        sizeSvg(this.svg, {
            ...horizontal,
            ...vertical
        });

        drawLegend(this.svg, fields, horizontal, rangeZ);

        return {
            dimensions: {
                ...horizontal,
                ...vertical
            },
            ranges: {
                x0: rangeX0,
                x1: rangeX1,
                y: rangeY,
                z: rangeZ
            }
        }
    }

    draw(){
        const { data, groupBy } = this.props;
        const { dimensions, ranges } = this.prepare();

        drawData(this.svg, dimensions, ranges, data, groupBy.fieldName);
    }

    addResponsive = () => {
        const { chartClass } = this.props;

        d3.select(window)
            .on('resize.' + chartClass + '-wrapper', () => {
                this.draw()
            })
    };

    componentDidMount() {
        this.setSvg();
        this.draw();
        this.addResponsive();
    }

    shouldComponentUpdate(nextProps){
        return !(this.props.reRender && !nextProps.reRender)
    }

    componentDidUpdate(){
        this.draw();
    }

    render() {
        const {chartClass} = this.props;

        return (
            <div className={'chart-wrapper ' + chartClass + '-wrapper'}>
                <svg className={chartClass} />
            </div>
        );
    }
}

export default BarChartComponent;
