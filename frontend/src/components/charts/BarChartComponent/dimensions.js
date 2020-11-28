import boxSize from './boxSize';

export const getHorizontalDimensions = (
  svg,
  chartClass,
  height,
  margin = { left: 35, right: 5 }
) => {
  const parentWidth = svg.node().parentNode.offsetWidth;
  const minWidth = height * boxSize.ratio || boxSize.minWidth;
  const applyingMargin = {
    left: margin.left + boxSize.padding,
    right: margin.right + boxSize.padding,
  };

  return {
    ...applyingMargin,
    width:
      (parentWidth < minWidth ? minWidth : parentWidth) -
      (applyingMargin.left + applyingMargin.right),
  };
};

export const getVerticalDimensions = (
  margin = { top: 35, bottom: 35 },
  height
) => {
  const applyingMargin = {
    top: margin.top + boxSize.padding,
    bottom: margin.bottom + boxSize.padding,
  };

  return {
    ...applyingMargin,
    height:
      (height || boxSize.height) - (applyingMargin.top + applyingMargin.bottom),
  };
};
