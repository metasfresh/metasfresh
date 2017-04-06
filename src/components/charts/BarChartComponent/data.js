import * as d3 from 'd3';

const mapDataset = (dataset, prevData, labelField) => Object.keys(dataset)
    .filter(key => key[0] !== '_' && key !== labelField)
    .map(key => ({
        key,
        value: dataset[key],
        valuePrev: prevData && prevData[key] ? prevData[key] : 0
    }));

export const drawData = (svg, dimensions, ranges, data, labelField, initialAnimation, prev, fields) => {
    
    // console.log('----Draw----');
        // console.log(prev);
        console.log(data);
        console.log('**************************************');

        

        let chartData = [];
        data.map((item, index) => {

            // console.log(index);
            chartData.push({data: item, prevData: prev ? prev[index] : 0});
        });

        
        const keys = fields.map(field => field.fieldName);


        // max y
        // console.log(
        //     d3.max(data, d => {
        //         return d3.max(keys, key => {
        //             return d[key];
        //         });
        //     })
        // );

        // console.log('-');

        // console.log(
        //    prev && d3.max(prev, d => {
        //         return d3.max(keys, key => {
        //             return d[key];
        //         });
        //     })
        // );

        const yprev = prev && d3.max(prev, d => {
                return d3.max(keys, key => {
                    return d[key];
                });
            });

        const ynext = d3.max(data, d => {
                return d3.max(keys, key => {
                    return d[key];
                });
            });

        // console.log(yprev === ynext);

        // console.log('---------');

        const xprev = prev && prev.map(value => value[labelField]);
        const xnext = data.map(value => value[labelField]);

        // console.log(JSON.stringify(xprev) === JSON.stringify(xnext));

        // console.log(data.map(value => value[labelField]));

        const xequal = JSON.stringify(xprev) === JSON.stringify(xnext);
        const yequal = yprev === ynext;




        JSON.stringify(Object.keys(data[0])) === JSON.stringify(Object.keys(prev[0]))

        if(data.length!==(prev && prev.length)){
            // const chart = document.getElementsByClassName('datasets')[0].childNodes[0];
            // while (chart && chart.firstChild) {
            //     chart.removeChild(chart.firstChild);
            // }
            svg.select('g.datasets').selectAll('g').remove();
        } else {
            data.map((item, index) => {
                console.log(item);
                console.log(index);
            });
        }
        
        // chart && chart.remove();

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

            // if(d.valuePrev === d.value){
            //     // console.log('true');
            //     return ranges.y(d.valuePrev)
            // } else {
            //     // console.log('false');
            //     return dimensions.height;
            // }

            if(xequal && yequal){
                // console.log('y prev');
                // console.log(ranges.y(d.valuePrev));


                return ranges.y(d.valuePrev);
            } else {
                // console.log('else y');
                // console.log(dimensions.height);
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


            // if(d.valuePrev === d.value){
            //     // console.log('true');
            //     return dimensions.height - ranges.y(d.valuePrev);
            // } else {
            //     // console.log('false');
            //     return 0;
            // }

            if(xequal && yequal){
                // console.log('height prev');
                // console.log(dimensions.height - ranges.y(d.valuePrev));
                return dimensions.height - ranges.y(d.valuePrev);
            } else {
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
            // console.log('y next');
            // console.log(ranges.y(d.value));
            return ranges.y(d.value);
        } )
        .attr('height', d => {
            // console.log('height next');
            // console.log(dimensions.height - ranges.y(d.value));
            return dimensions.height - ranges.y(d.value);
        })

        
        // .attr('y', initialAnimation ?  d => ranges.y(d.value) : d => 40)
        // .attr('height', initialAnimation ? d => dimensions.height - ranges.y(d.value) : d => dimensions.height - 40)

        .attr('fill', d => ranges.z(d.key))



        // console.log("============================");
        // console.log(prev);

};