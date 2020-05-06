import * as d3 from 'd3';

export const getSvg = className => {
  const svg = d3.select('.' + className);

  // put legend outside of container
  svg.append('g').classed('legend', true);

  const container = svg.append('g').classed('chart-container', true);

  container.append('g').classed('x-axis', true);

  container.append('g').classed('y0-axis', true);

  container.append('g').classed('y-axis', true);

  container.append('g').classed('datasets', true);

  return svg;
};

export const sizeSvg = (svg, { width, height, top, left, right, bottom }) => {
  svg.attr('width', width + left + right).attr('height', height + top + bottom);

  svg
    .select('.chart-container')
    .attr('transform', 'translate(' + left + ',' + top + ')');
};
