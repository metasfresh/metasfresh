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
