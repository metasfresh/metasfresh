import * as d3 from "d3";

const getXAxisTickAngle = (size, width) => {
  const a = size.height;
  const b = size.width;
  const c = width;

  // the best angle of labels to fit
  return 2 * Math.atan((a + Math.sqrt(a * a + b * b - c * c)) / (b + c));
};

export const populateY0Axis = (svg, rangeY, width, data, fields) => {
  const keys = fields.map(field => field.fieldName);
  const minY = d3.min(data, d => {
    return d3.min(keys, key => {
      return d[key];
    });
  });
  if (minY >= 0) return;

  svg
    .select("g.y0-axis")
    .selectAll("*")
    .remove();

  svg
    .select("g.y0-axis")
    .append("line")
    .attr("y1", rangeY(0))
    .attr("x1", 0)
    .attr("y2", rangeY(0))
    .attr("x2", width)
    .attr("stroke-width", 1)
    .attr("opacity", 0.5)
    .attr("stroke", "black");
};

export const populateXAxis = (svg, rangeX0) => {
  const sizes = [];

  svg
    .select("g.x-axis")
    .call(d3.axisBottom(rangeX0))
    .selectAll("text")
    .classed("x-axis-label", true)
    .each(function() {
      sizes.push(this.getBBox());
    });

  const maxW = Math.max(...sizes.map(size => size.width));

  if (maxW <= rangeX0.bandwidth()) {
    svg
      .selectAll(".x-axis-label")
      .style("text-anchor", "middle")
      .attr("dx", "0")
      .attr("dy", ".71em")
      .attr("transform", "");
    return;
  }

  const maxH = Math.max(
    ...sizes.filter(size => size.width >= maxW).map(size => size.height)
  );

  const size = sizes.find(item => item.width === maxW && item.height === maxH);
  const radianAngle = getXAxisTickAngle(size, rangeX0.bandwidth());
  const angle =
    radianAngle > Math.PI / 2 ? -90 : radianAngle * (-180 / Math.PI);
  const line =
    radianAngle > Math.PI / 2 ? 0 : 6 * Math.cos(angle * (Math.PI / 180));

  svg
    .selectAll(".x-axis-label")
    .style("text-anchor", "end")
    .attr("dx", "-.8em")
    .attr("dy", ".15em")
    .attr(
      "transform",
      "rotate(" + angle + ") translate(0, " + (size.height / -2 + line) + ")"
    );
};

export const getXAxisLabelsHeight = svg => {
  const heights = [];

  svg.selectAll(".tick").each(function() {
    heights.push(this.getBBox().height);
  });

  return heights.length ? Math.max(...heights) : 0;
};

export const moveXAxis = (svg, height) => {
  svg.select(".x-axis").attr("transform", "translate(0," + height + ")");
};

export const populateYAxis = (svg, rangeY) => {
  svg.select("g.y-axis").call(d3.axisLeft(rangeY).ticks(null, "s"));
};
