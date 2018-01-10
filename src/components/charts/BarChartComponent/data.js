import * as d3 from "d3";

const mapDataset = (dataset, prevData, labelField) =>
  Object.keys(dataset)
    .filter(key => key[0] !== "_" && key !== labelField)
    .map(key => ({
      key,
      value: dataset[key],
      valuePrev: prevData && prevData[key] ? prevData[key] : 0
    }));

export const isYRangeChanged = (data, prevData, fields) => {
  const keys = fields.map(field => field.fieldName);

  const yprev =
    prevData &&
    d3.max(prevData, d => {
      return d3.max(keys, key => {
        return d[key];
      });
    });

  const ynext = d3.max(data, d => {
    return d3.max(keys, key => {
      return d[key];
    });
  });

  return yprev !== ynext;
};

export const isXRangeChanged = (data, prevData, svg) => {
  if (data.length !== (prevData && prevData.length)) {
    svg
      .select("g.datasets")
      .selectAll("g")
      .remove();
  } else {
    data.map((item, index) => {
      if (
        prevData &&
        JSON.stringify(Object.keys(data[index])) !==
          JSON.stringify(Object.keys(prevData[index]))
      ) {
        svg
          .select("g.datasets")
          .selectAll("g")
          .remove();
      }
    });
  }
};

export const drawData = (
  svg,
  dimensions,
  ranges,
  data,
  labelField,
  prev,
  fields,
  reRender
) => {
  if (reRender) {
    svg
      .select("g.datasets")
      .selectAll("g")
      .remove();
  }

  let chartData = [];
  data.map((item, index) => {
    chartData.push({ data: item, prevData: prev ? prev[index] : 0 });
  });

  const yChanged = isYRangeChanged(data, prev, fields);
  isXRangeChanged(data, prev, svg);

  const groups = svg
    .select("g.datasets")
    .selectAll("g")
    .data(chartData);

  const bars = groups
    .enter()
    .append("g")
    .classed("bar-group", true)
    .merge(groups)
    .attr(
      "transform",
      d => "translate(" + ranges.x0(d.data[labelField]) + ", 0)"
    )
    .selectAll("rect")
    .data(d => mapDataset(d.data, d.prevData, labelField));

  bars
    .enter()
    .append("rect")
    .classed("bar", true)
    .merge(bars)
    .attr("x", d => ranges.x1(d.key))
    .attr("width", ranges.x1.bandwidth())
    .attr("y", d => {
      if (yChanged || reRender) {
        return dimensions.height;
      } else {
        return getY(d.valuePrev, ranges);
      }
    })
    .attr("height", d => {
      if (yChanged || reRender) {
        return 0;
      } else {
        return Math.abs(ranges.y(d.value) - ranges.y(0));
      }
    })
    .transition()
    .duration(1000)
    .attr("y", d => {
      return getY(d.value, ranges);
    })
    .attr("height", d => Math.abs(ranges.y(d.value) - ranges.y(0)))
    .attr("fill", d => ranges.z(d.key));
};

function getY(value, ranges) {
  if (value < 0) {
    return ranges.y(0);
  } else {
    return ranges.y(value);
  }
}
