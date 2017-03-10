import boxSize from './boxSize';
import * as d3 from 'd3';

export const getSvg = (className) => {
    const svg = d3.select('.' + className);

    // put legend outside of container
    svg.append('g')
        .classed('legend', true);

    const container = svg
        .append('g')
        .classed('chart-container', true);

    container.append('g')
        .classed('x-axis', true);

    container.append('g')
        .classed('y-axis', true);

    container.append('g')
        .classed('datasets', true);

    return svg;
};

export const sizeSvg = (svg, className, {width, height, top, left, right, bottom}) => {
    const applyingWidth = width + left + right;
    const applyingHeight = height + top + bottom;
    const finalWidth = applyingWidth < boxSize.minWidth ? boxSize.minWidth : applyingWidth;

    svg
        .attr('width', finalWidth)
        .attr('height', applyingHeight);

    svg.select('.chart-container')
        .attr(
        'transform',
        'translate(' + left + ',' + top + ')'
    );
};
