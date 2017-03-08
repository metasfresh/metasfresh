import Moment from 'moment';

export const drawData = (svg, dimensions, ranges, data, labelField) => {
    svg.append('g')
        .classed('datasets', true)
        .selectAll('g')
        .data(data)
        // draw bar groups
        .enter().append('g')
            .attr('transform', d => 'translate(' + ranges.x0(Moment(d[labelField]).format('YYYY.MM.DD')) + ',0)')
        .selectAll('rect')
        // data format for bars
        .data(d => Object.keys(d)
            .filter(key => key[0] !== '_' && key !== labelField)
            .map(key => ({
                key,
                value: d[key]
            }))
        )
        // draw bars in groups
        .enter().append('rect')
            .attr('class', 'bar')
            .attr('x', d => ranges.x1(d.key))
            .attr('width', ranges.x1.bandwidth())
            .attr('y', d => ranges.y(d.value))
            .attr('height', d => dimensions.height - ranges.y(d.value));
};
