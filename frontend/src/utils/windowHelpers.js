export const toOrderBysCommaSeparatedString = (orderBysArray) => {
  if (!orderBysArray) {
    return '';
  }

  if (Array.isArray(orderBysArray)) {
    return orderBysArray.reduce((acc, orderBy) => {
      if (acc) {
        return acc + ',' + toSingleOrderByClause(orderBy);
      } else {
        return toSingleOrderByClause(orderBy);
      }
    }, '');
  } else {
    // possible string
    return `${orderBysArray}`;
  }
};

const toSingleOrderByClause = ({ ascending, fieldName }) =>
  (ascending ? '+' : '-') + fieldName;

export const extractWindowIdFromViewId = (viewId) => {
  // we assume the viewId has the following format: <windowId>-<unique id>
  return viewId?.split('-')?.[0] ?? null;
};
