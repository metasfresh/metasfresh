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
