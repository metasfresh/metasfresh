import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

// axios 0.21 defaults to no timeout. api/confirmation.js documents the same failure on these
// devices - the scanner drops the TCP connection mid-roam and the request hangs rather than erroring.
// It matters more here: the sync holds one in-flight promise, so a POST that never settles would
// wedge every later sync from both triggers for the life of the tab.
export const TRACE_POST_TIMEOUT_MILLIS = 20000;

export const postEventsToBackend = (events) => {
  return axios
    .post(`${apiBasePath}/trace`, { events }, { timeout: TRACE_POST_TIMEOUT_MILLIS })
    .then((response) => unboxAxiosResponse(response));
};
