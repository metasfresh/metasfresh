import * as d3 from 'd3';
import Moment from 'moment';

export const getX0Range = (width, data, layout) => {
    return d3.scaleBand()
        .range([0, width])
        .padding(0.2)
        .domain(data.map(value => Moment(value[layout.groupByField.fieldName]).format('YYYY.MM.DD')));
};

export const getX1Range = (width, layout) => {
    return d3.scaleBand()
        .range([0, width])
        .padding(0.1)
        .domain(layout.fields.map(field => field.fieldName))
};

export const getYRange = (height, data, layout) => {
    const keys = layout.fields.map(field => field.fieldName);

    return d3.scaleLinear()
        .range([height, 0])
        .domain([0, d3.max(data, d => {
            return d3.max(keys, key => {
                return d[key];
            });
        })]).nice()
};
