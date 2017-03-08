import React, { PureComponent }from 'react';
import * as d3 from 'd3';

import { getX0Range, getX1Range, getYRange } from './ranges';
import { addXAxis, addYAxis, moveXAxis, getXAxisLabelsHeight } from './axes';
import { getSvg, sizeSvg } from './svg';
import { getHorizontalDimensions, getVerticalDimensions } from './dimensions';
import { drawData } from './data';

class BarChartComponent extends PureComponent {
    draw(){
        const { data, layout, chartClass, responsive } = this.props;

        // main container
        const svg = getSvg(chartClass);

        // horizontal sizing
        const horizontal = getHorizontalDimensions(responsive, chartClass);
        const rangeX0 = getX0Range(horizontal.width, data, layout);
        const rangeX1 = getX1Range(rangeX0.bandwidth(), layout);
        addXAxis(svg, rangeX0);

        // vertical sizing
        const labelsHeight = getXAxisLabelsHeight(svg);
        const vertical = getVerticalDimensions({bottom: labelsHeight, top: 5});
        const rangeY = getYRange(vertical.height, data, layout);
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
            y: rangeY
        }, data, layout.groupByField.fieldName);
    }

    clear = () => {
        const { chartClass } = this.props;
        document.getElementsByClassName(chartClass)[0].childNodes[0].remove();
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
        const { responsive } = this.props;

        this.draw();

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
