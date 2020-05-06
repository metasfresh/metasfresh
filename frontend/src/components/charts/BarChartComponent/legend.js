import * as d3 from 'd3';

import boxSize from './boxSize';

const size = {
  width: 20,
  height: 20,
  offset: boxSize.padding,
};

export const drawLegend = (svg, fields, horizontal, rangeZ) => {
  const width =
    horizontal.width + horizontal.left + horizontal.right - 2 * size.offset;

  const legend = drawContainer(svg, fields, width);
  drawRects(legend, rangeZ);
  drawTexts(legend, width);

  return legend;
};

const drawContainer = (svg, fields, width) => {
  const existingLegend = svg
    .select('.legend')
    .attr('font-family', 'sans-serif')
    .attr('font-size', 10)
    .attr('text-anchor', 'start')
    .selectAll('g')
    .data(fields);

  const legend = existingLegend
    .enter()
    .append('g')
    .merge(existingLegend);

  return legend.attr(
    'transform',
    (d, i) =>
      'translate(' + i * (width / legend.size()) + ', ' + size.offset + ')'
  );
};

const drawRects = (legend, rangeZ) => {
  const existingColors = legend.selectAll('rect').data(d => [d]);

  return existingColors
    .enter()
    .append('rect')
    .merge(existingColors)
    .attr('x', size.offset)
    .attr('width', size.width)
    .attr('height', size.height)
    .attr('fill', d => rangeZ(d.fieldName));
};

const drawTexts = (legend, width) => {
  const existingTexts = legend.selectAll('text').data(d => [d]);

  return existingTexts
    .enter()
    .append('text')
    .merge(existingTexts)
    .attr('x', 2 * size.offset + size.width)
    .attr('y', 10)
    .attr('dy', '0.32em')
    .text(d => d.caption + (d.unit ? ' [' + d.unit + ']' : ''))
    .each(function() {
      addEllipsis(this, width / legend.size() - (size.offset + size.width));
    });
};

const addEllipsis = (element, width) => {
  let self = d3.select(element),
    textLength = self.node().getComputedTextLength(),
    text = self.text();
  while (textLength > width && text.length > 0) {
    text = text.slice(0, -1);
    self.text(text + '...');
    textLength = self.node().getComputedTextLength();
  }
};
