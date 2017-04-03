const mapDataset = (dataset, labelField) => Object.keys(dataset)
    .filter(key => key[0] !== '_' && key !== labelField)
    .map(key => ({
        key,
        value: dataset[key]
    }));

export const drawData = (svg, dimensions, ranges, data, labelField, initialAnimation, prev) => {
    const groups = svg.select('g.datasets')
        .selectAll('g')
        .data(data);

    const bars = groups.enter().append('g')
        .classed('bar-group', true)
    .merge(groups)
        .attr('transform', d => 'translate(' +
            ranges.x0(d[labelField]) + ', 0)')
        .selectAll('rect')
        .data(d => mapDataset(d, labelField))

    bars.enter().append('rect')
        .classed('bar', true)
    .merge(bars)
        .attr('x', d => ranges.x1(d.key))
        .attr('width', ranges.x1.bandwidth())

        .attr('y', initialAnimation ? dimensions.height : prev => 
        {   console.log(ranges.y(prev.value));
            console.log(dimensions.height);
            console.log('-------');

            return dimensions.height - 20
            // return dimensions.height - ranges.y(prev.value)
            // ranges.y(d.value)
        }
            
        )
        .attr('height', initialAnimation ? 0 : prev => {
            return 20;
            // return ranges.y(prev.value)
            // dimensions.height - ranges.y(d.value)
            }
        )

        .transition()
        .duration(6000)
        .attr('y', d => ranges.y(d.value))
        .attr('height', d => dimensions.height - ranges.y(d.value))
        .attr('fill', d => ranges.z(d.key))

};