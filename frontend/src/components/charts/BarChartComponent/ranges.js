import * as d3 from 'd3';

export const getX0Range = (width, data, groupBy) => {
  return d3
    .scaleBand()
    .range([0, width])
    .padding(0.2)
    .domain(data.map((value) => value[groupBy.fieldName]));
};

export const getX1Range = (width, fields) => {
  return d3
    .scaleBand()
    .range([0, width])
    .padding(0.1)
    .domain(fields.map((field) => field.fieldName));
};

export const getYRange = (height, data, fields) => {
  const fieldNames = fields.map((field) => field.fieldName);

  let min = d3.min([...data, 0], (d) => {
    return d3.min(fieldNames, (key) => d[key]);
  });

  // make sure the zero is also present else the bars will look crappy
  // because the bars are always drawn starting from zero.
  if (min > 0) {
    min = 0;
  }

  let max = d3.max(data, (d) => {
    return d3.max(fieldNames, (key) => d[key]);
  });

  // make sure the zero is also present else the bars will look crappy
  // because the bars are always drawn starting from zero.
  if (max < 0) {
    max = 0;
  }

  return d3.scaleLinear().range([height, 0]).domain([min, max]).nice();
};

export const getZRange = (colors) => {
  return d3.scaleOrdinal().range(colors);
};
