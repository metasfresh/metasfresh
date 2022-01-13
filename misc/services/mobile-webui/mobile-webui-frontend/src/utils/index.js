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

export const getLocation = (params, scanner) => {
  const { wfProcessId, activityId, lineId, stepId, appId, altStepId } = params;

  const location = `/workflow/${wfProcessId}${activityId ? `/activityId/${activityId}` : ``}${
    lineId ? `/lineId/${lineId}` : ``
  }${stepId ? `/stepId/${stepId}` : ``}${altStepId ? `/altStepId/${altStepId}` : ``}${
    scanner ? `/scanner/${appId}` : ``
  }`;

  return location;
};
