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

export const isNullOrUndefined = (arg) => arg === null || arg === undefined;

export const doFinally = (promise, func) => {
  if (!func) return;

  if (promise?.finally) {
    return promise.finally(func);
  } else {
    func();
    return promise;
  }
};
