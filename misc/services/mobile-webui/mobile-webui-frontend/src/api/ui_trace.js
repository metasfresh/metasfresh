import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

/**
 * Hard ceiling on one trace POST.
 *
 * axios 0.21's default timeout is 0, i.e. none. The same failure this guards against is already
 * documented for these devices in api/confirmation.js: the scanner on flaky wifi drops the TCP
 * connection mid-roam and the request hangs forever instead of erroring.
 *
 * It matters more here than for a one-off request, because the sync task holds a single in-flight
 * promise to keep the periodic task and the `online` listener from posting the same batch twice
 * (see utils/ui_trace/useUIEventsTracing.js). A POST that never settles would leave that promise
 * pending forever, so every later sync from either trigger would join a dead promise and do nothing
 * — ui-trace would go permanently silent on that device, with no error and no log. Rejecting on a
 * timeout keeps that impossible: the sync's own catch runs, the in-flight handle is released, and
 * the next cycle retries.
 */
export const TRACE_POST_TIMEOUT_MILLIS = 20000;

export const postEventsToBackend = (events) => {
  return axios
    .post(`${apiBasePath}/trace`, { events }, { timeout: TRACE_POST_TIMEOUT_MILLIS })
    .then((response) => unboxAxiosResponse(response));
};
