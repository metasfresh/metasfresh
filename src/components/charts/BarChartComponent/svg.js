import * as d3 from 'd3';

export const getSvg = (className) => {
    return d3.select('.' + className).append('g');
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
