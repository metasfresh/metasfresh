import axios from 'axios';
import { getOrCreateDeviceId, saveEvent } from './db';
import { v4 as uuidv4 } from 'uuid';
import { doFinally } from '../index';

const HEADER_UITraceId = 'X-Ui-Trace-Id'; // important: keep this upper/lower case because that's how the header will be formated anyway

let identityProperties = {};

export const identity = (properties = {}) => {
  if (!properties) {
    return;
  }

  identityProperties = { ...identityProperties, ...properties };
  console.log('identity', { identityProperties, properties });
};

export const trace = async (properties) => {
  const event = await createEvent(properties);

  // noinspection ES6MissingAwait
  saveEvent(event);

  return event;
};

export const traceFunction = (func, properties) => {
  if (!func) {
    return () => {};
  }

  return async (...args) => {
    const { id } = await trace(properties);
    setCurrentEventId(id);
    return doFinally(func(args), clearCurrentEventId);
  };
};

const setCurrentEventId = (id) => {
  axios.defaults.headers.common[HEADER_UITraceId] = id;
};
const clearCurrentEventId = () => {
  delete axios.defaults.headers.common[HEADER_UITraceId];
};

const createEvent = async (properties) => {
  return {
    ...properties,
    id: uuidv4(),
    timestamp: Date.now(),
    device: await getDeviceMetadata(),
    user: identityProperties,
    page: {
      url: window?.location?.href,
      title: document.title,
      referrer: document?.referrer,
    },
  };
};

const getDeviceMetadata = async () => {
  return {
    deviceId: await getOrCreateDeviceId(),
    tabId: getOrCreateTabId(),
    userAgent: navigator.userAgent,
    platform: navigator.platform,
    language: navigator.language,
    screenSize: `${window.screen.width}x${window.screen.height}`,
  };
};

const getOrCreateTabId = () => {
  let tabId = sessionStorage.getItem('tab_id');
  if (!tabId) {
    tabId = uuidv4();
    sessionStorage.setItem('tab_id', tabId);
  }
  return tabId;
};
