export function getBaseUrl() {
  let getUrl = window.location;
  return getUrl.protocol + '//' + getUrl.host + '/' + getUrl.pathname.split('/')[1];
}

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
