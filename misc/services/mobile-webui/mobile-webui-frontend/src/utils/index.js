import queryString from 'query-string';

export const unboxAxiosResponse = (axiosResponse) => {
  //
  // Case: server is using API Audit feature
  if (axiosResponse.data.endpointResponse) {
    return axiosResponse.data.endpointResponse;
  }
  //
  // Case: server is NOT using API Audit feature
  else {
    return axiosResponse.data;
  }
};

export const toQueryString = (queryParams) => {
  return queryString.stringify(queryParams, { arrayFormat: 'comma', skipNull: true });
};

export const toUrlQueryParams = (queryParams) => {
  const queryParamsStr = toQueryString(queryParams);
  return queryParamsStr ? `?${queryParamsStr}` : '';
};

export const toUrl = (path, queryParams) => {
  return `${path}${toUrlQueryParams(queryParams)}`;
};
