import { getOrCreateDeviceId, saveEvent } from './db';
import { v4 as uuidv4 } from 'uuid';

let identityProperties = {};

export const identity = (properties = {}) => {
  if (!properties) {
    return;
  }

  identityProperties = { ...identityProperties, ...properties };
  console.log('identity', { identityProperties, properties });
};

export const trace = (properties) => {
  // noinspection JSIgnoredPromiseFromCall
  createEvent(properties).then(saveEvent);
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
