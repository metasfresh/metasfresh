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

export const toQueryString = (query) => {
  return queryString.stringify(query, { arrayFormat: 'comma', skipNull: true });
};

export const toUrl = ({ urlBase, queryParams }) => {
  let url = urlBase;

  const queryParamsStr = queryParams ? toQueryString(queryParams) : null;
  if (queryParamsStr) {
    url += '?' + queryParamsStr;
  }

  return url;
};
