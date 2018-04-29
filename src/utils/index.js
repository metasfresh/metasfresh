import queryString from 'query-string';

export const getQueryString = query =>
  queryString.stringify(
    Object.keys(query).reduce((parameters, key) => {
      const value = query[key];

      if (Array.isArray(value) && value.length > 0) {
        parameters[key] = value.join(',');
      } else {
        parameters[key] = value;
      }

      return parameters;
    }, {})
  );

export const arePropTypesIdentical = (nextProps, currentProps) => {
  for (const key of Object.keys(nextProps)) {
    const nextType = typeof nextProps[key];
    const currentType = typeof currentProps[key];

    if (nextType !== currentType) {
      return false;
    }
  }

  return true;
};
