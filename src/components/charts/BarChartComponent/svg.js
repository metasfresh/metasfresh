import * as d3 from 'd3';

export const getSvg = (className) => {
    const container = d3.select('.' + className)
        .append('g')
        .classed('container', true);

    container.append('g')
        .classed('x-axis', true);

    container.append('g')
        .classed('y-axis', true);

    container.append('g')
        .classed('datasets', true);

    return container;
};

export const sizeSvg = (svg, className, {width, height, top, left, right, bottom}) => {
    d3.select('.' + className)
        .attr('width', width + left + right)
        .attr('height', height + top + bottom);

    svg.attr(
        'transform',
        'translate(' + left + ',' + top + ')'
    );
};
