import boxSize from './boxSize';

export const getHorizontalDimensions = (svg, chartClass, margin = {left: 35, right: 5}) => {
    const parentWidth = svg.node().parentNode.offsetWidth;
    const applyingMargin = {
        left: margin.left + boxSize.padding,
        right: margin.right + boxSize.padding,
    };

    return {
        ...applyingMargin,
        width: (
            parentWidth < boxSize.minWidth ?
                boxSize.minWidth :
                parentWidth
        ) - (applyingMargin.left + applyingMargin.right)
    }
};

export const getVerticalDimensions = (margin = {top: 35, bottom: 35}) => {
    const applyingMargin = {
        top: margin.top + boxSize.padding,
        bottom: margin.bottom + boxSize.padding
    };

    return ({
        ...applyingMargin,
        height: boxSize.height - (applyingMargin.top + applyingMargin.bottom)
    });
};
