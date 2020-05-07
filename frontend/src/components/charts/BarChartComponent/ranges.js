import * as d3 from 'd3';

export const getX0Range = (width, data, groupBy) => {
  return d3
    .scaleBand()
    .range([0, width])
    .padding(0.2)
    .domain(data.map(value => value[groupBy.fieldName]));
};

export const getX1Range = (width, fields) => {
  return d3
    .scaleBand()
    .range([0, width])
    .padding(0.1)
    .domain(fields.map(field => field.fieldName));
};

export const getYRange = (height, data, fields) => {
  const keys = fields.map(field => field.fieldName);

  return d3
    .scaleLinear()
    .range([height, 0])
    .domain([
      d3.min(data, d => {
        return d3.min(keys, key => {
          return d[key];
        });
      }),
      d3.max(data, d => {
        return d3.max(keys, key => {
          return d[key];
        });
      }),
    ])
    .nice();
};

export const getZRange = colors => {
  return d3.scaleOrdinal().range(colors);
};
