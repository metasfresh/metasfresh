const mapDataset = (dataset, prevData, labelField) => Object.keys(dataset)
    .filter(key => key[0] !== '_' && key !== labelField)
    .map(key => ({
        key,
        value: dataset[key],
        valuePrev: prevData[key] ? prevData[key] : 0
    }));

export const drawData = (svg, dimensions, ranges, data, labelField, initialAnimation, prev) => {
    
    // console.log('----Draw----');
        // console.log(prev);
        // console.log(data);
        // console.log('**************************************');
        let chartData = [];
        data.map((item, index) => {

            // console.log(index);
            chartData.push({data: item, prevData: prev ? prev[index] : 0});
        });

        // console.log(chartData);
    const groups = svg.select('g.datasets')
        .selectAll('g')
        .data(chartData);

    const bars = groups.enter().append('g')
        .classed('bar-group', true)
    .merge(groups)
        .attr('transform', d => 'translate(' +
            ranges.x0(d.data[labelField]) + ', 0)')
        .selectAll('rect')
        .data(d => mapDataset(d.data, d.prevData, labelField))

    bars.enter().append('rect')
        .classed('bar', true)
    .merge(bars)
        .attr('x', d => ranges.x1(d.key))
        .attr('width', ranges.x1.bandwidth())

        .attr('y', initialAnimation ? dimensions.height : d => {   
            // console.log(d.valuePrev);

            if(d.valuePrev === d.value){
                // console.log('true');
                return ranges.y(d.valuePrev)
            } else {
                // console.log('false');
                return dimensions.height;
            }


            // console.log(dimensions.height);
     

// return ranges.y(d.valuePrev)

            
            // return dimensions.height - ranges.y(d.value)
            // ranges.y(d.value)
        }
            
        )
        .attr('height', initialAnimation ? 0 : d => {
            // console.log(ranges.y(d.value));

            // console.log('--------------------------');
            // console.log(dimensions.height - ranges.y(d.valuePrev));


            if(d.valuePrev === d.value){
                // console.log('true');
                return dimensions.height - ranges.y(d.valuePrev);
            } else {
                // console.log('false');
                return 0;
            }


// return dimensions.height - ranges.y(d.valuePrev);
            // return ranges.y(d.value)
            // dimensions.height - ranges.y(d.value)
            
            }
        )

        .transition()
        .duration(6000)
        .attr('y', d => {
            // console.log(d.value);
            return ranges.y(d.value);
        } )
        .attr('height', d => dimensions.height - ranges.y(d.value))

        
        // .attr('y', initialAnimation ?  d => ranges.y(d.value) : d => 40)
        // .attr('height', initialAnimation ? d => dimensions.height - ranges.y(d.value) : d => dimensions.height - 40)

        .attr('fill', d => ranges.z(d.key))



        // console.log("============================");
        // console.log(prev);

};