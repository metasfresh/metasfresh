import axios from 'axios';
import { v4 as uuidv4 } from 'uuid';

const HEADER_UITraceId = 'X-Ui-Trace-Id';
const MUTATING_METHODS = new Set(['post', 'put', 'delete', 'patch']);

/**
 * Ensures every mutating mobile-UI request carries an X-Ui-Trace-Id header so the
 * backend's api_request_audit table can be correlated back to the frontend action
 * that triggered it. If traceFunction already pinned a trace id onto
 * axios.defaults.headers.common, that id is kept as-is so the request and the
 * surrounding frontend uiTrace event share the same id. Otherwise, a fresh UUID
 * is generated for this single request; that id will only appear in backend
 * audit, which is still enough to identify the individual request.
 *
 * Intentionally does NOT emit a uiTrace event — mutating mobile endpoints fire
 * frequently enough that one event per call would drown ui_trace. The caller
 * that wants a semantic frontend trail should emit its own event (see
 * ConfirmActivity.jsx for an example).
 */
export const installMutatingApiTraceIdInterceptor = () => {
  axios.interceptors.request.use((config) => {
    const method = (config.method || 'get').toLowerCase();
    if (!MUTATING_METHODS.has(method)) {
      return config;
    }
    const alreadySet = config.headers?.[HEADER_UITraceId] ?? axios.defaults.headers.common?.[HEADER_UITraceId];
    if (alreadySet) {
      return config;
    }
    config.headers = config.headers || {};
    config.headers[HEADER_UITraceId] = uuidv4();
    return config;
  });
};
