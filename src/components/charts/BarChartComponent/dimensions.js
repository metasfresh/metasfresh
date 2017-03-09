export const getHorizontalDimensions = (responsive, chartClass, margin = {left: 35, right: 0}, width = 600) => {
    const wrapperWidth = document.getElementsByClassName(chartClass + '-wrapper')[0].offsetWidth;

    return {
        ...margin,
        width: (responsive ? wrapperWidth : width) - (margin.left + margin.right)
    }
};

export const getVerticalDimensions = (margin = {top: 5, bottom: 5}, height = 400) => ({
    ...margin,
    height: height - (margin.top + margin.bottom)
});
