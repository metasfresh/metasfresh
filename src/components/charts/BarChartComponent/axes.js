import * as d3 from 'd3';

const getXAxisTickAngle = (size, width) => {
    if (width < size.width){
        const a = size.height;
        const b = size.width;
        const c = width;

        // the best angle of labels to fit
        return 2 * Math.atan(
            (a + Math.sqrt((a * a) + (b * b) - (c * c))) /
            (b + c)
        );
    }
};

export const addXAxis = (svg, rangeX0) => {
    const sizes = [];

    svg.append('g')
        .classed('x-axis', true)
        .call(d3.axisBottom(rangeX0))
        .selectAll('text')
        // .classed('x-axis-label', true)
        .each(function(){
            console.log(this.getBBox());
            sizes.push(this.getBBox());
        });

    const maximum = Math.max(...(sizes.map(size => size.width)));
    const size = sizes.find(item => item.width === maximum);
    const radianAngle = getXAxisTickAngle(size, rangeX0.bandwidth());

    d3.select('.x-axis-label')
        .each(function(){
            const angle = (radianAngle > (Math.PI / 2)) ? -90 : (radianAngle * (-180 / Math.PI));
            const line = (radianAngle > (Math.PI / 2)) ? 0 : (6 * Math.cos(angle * (Math.PI / 180)));

            d3.select(this)
                .style('text-anchor', 'end')
                .attr('dx', '-.8em')
                .attr('dy', '.15em')
                .attr('transform', 'rotate(' + angle + ') translate(0, ' + ((size.height / -2) + line) + ')');
        });
};

export const getXAxisLabelsHeight = (svg) => {
    const heights = [];

    svg.selectAll('.tick')
        .each(function(){
            heights.push(this.getBBox().height);
        });

    return Math.max(...heights);
}

export const moveXAxis = (svg, height) => {
    svg.select('.x-axis')
        .attr('transform', 'translate(0,' + height + ')')
};

export const addYAxis = (svg, rangeY) => {
    svg.append('g')
        .classed('y-axis', true)
        .call(d3.axisLeft(rangeY).ticks(null, 's'));
};
