import queryString from 'query-string';
import counterpart from 'counterpart';

import history from '../services/History';

/**
 * @method updateUri
 * @summary Prepends viewId/page/sorting to the url
 */
export function updateUri(pathname, query, updatedQuery) {
  const fullPath = window.location.href;
  const queryObject = {
    ...query,
    ...updatedQuery,
  };
  const queryUrl = queryString.stringify(queryObject);
  const url = `${pathname}?${queryUrl}`;

  if (!fullPath.includes('viewId')) {
    history.replace(url);
  }
}

// TODO: Move to api ?
export const getQueryString = (query) =>
  queryString.stringify(
    Object.keys(query).reduce((parameters, key) => {
      const value = query[key];

      if (Array.isArray(value) && value.length > 0) {
        parameters[key] = value.join(',');
      } else if (value) {
        parameters[key] = value;
      }

      return parameters;
    }, {})
  );

// TODO: Move to api ?
export function createPatchRequestPayload(property, value) {
  if (Array.isArray(property) && Array.isArray(value)) {
    return property.map((item, index) => ({
      op: 'replace',
      path: item,
      value: value[index],
    }));
  } else if (Array.isArray(property) && value !== undefined) {
    return property.map((item) => ({
      op: 'replace',
      path: item.field,
      value,
    }));
  } else if (property && value !== undefined) {
    return [
      {
        op: 'replace',
        path: property,
        value,
      },
    ];
  } else {
    // never return undefined; backend does not support it
    return [];
  }
}

export const arePropTypesIdentical = (nextProps, currentProps) => {
  for (const key of Object.keys(nextProps)) {
    const nextType = typeof nextProps[key];
    const currentType = typeof currentProps[key];

    if (nextType !== currentType) {
      return false;
    }
  }

  return true;
};

// UTILITIES
export function toggleFullScreen() {
  const doc = window.document;
  const docEl = doc.documentElement;

  const requestFullScreen =
    docEl.requestFullscreen ||
    docEl.mozRequestFullScreen ||
    docEl.webkitRequestFullScreen ||
    docEl.msRequestFullscreen;
  const cancelFullScreen =
    doc.exitFullscreen ||
    doc.mozCancelFullScreen ||
    doc.webkitExitFullscreen ||
    doc.msExitFullscreen;

  if (
    !doc.fullscreenElement &&
    !doc.mozFullScreenElement &&
    !doc.webkitFullscreenElement &&
    !doc.msFullscreenElement
  ) {
    requestFullScreen.call(docEl);
  } else {
    cancelFullScreen.call(doc);
  }
}

export function nullToEmptyStrings(fieldsByName) {
  return Object.keys(fieldsByName).reduce((acc, fieldName) => {
    acc[fieldName] =
      fieldsByName[fieldName].value === null
        ? { ...fieldsByName[fieldName], value: '' }
        : fieldsByName[fieldName];
    return acc;
  }, {});
}

export function getItemsByProperty(arr, prop, value) {
  let ret = [];

  arr &&
    arr.forEach((item) => {
      if (item[prop] === value) {
        ret.push(item);
      }
    });

  return ret;
}

// TODO: Move to locale helpers
export function translateCaption(caption) {
  const translatedString = counterpart.translate(caption);
  // show a default placeholder in case translation is missing such that the BE would know what specific key they need to add
  return !translatedString.includes('{') ? translatedString : `${caption}`;
}

/**
 * This can be further adapted to allow pre-formatting of the data before post
 * @param {string} target
 * @param {string} data
 */
export function preFormatPostDATA({ target, postData }) {
  const dataToSend = {};
  if (target === 'comments') {
    dataToSend.text = postData.txt;
  }
  return dataToSend;
}

/**
 * Opens the url given as param in a new window and focuses on that window
 * @param {string} urlPath
 * @param {fnct} dispatch
 * @param {fnct} function to dispatch - added this in case we need to perform custom actions when opening new tab ()
 *               https://github.com/metasfresh/metasfresh/issues/10145 (in this case we send setProcessSaved that will
 *               update the store flag - processStatus)
 */
export function openInNewTab({ urlPath, dispatch, actionName }) {
  dispatch(actionName());
  let newTabBrowser = window.open(urlPath, '_blank');
  newTabBrowser.focus();
}

/**
 * Returns the tab with `tabId` from the data set, or null if no match
 *
 * @param {object} dataSet
 * @param {string} tabId
 */
export function getTab(dataSet, tabId) {
  let tab = null;
  if (dataSet.tabs) {
    dataSet.tabs.forEach((t) => {
      if (t.tabs) {
        tab = getTab(t, tabId);
      } else if (t.tabId === tabId) {
        tab = t;
      }
    });

    return tab;
  }

  return null;
}

/**
 * Updates the layout of a tab with `tabId` and creates a new copy of the data set.
 * Works for nested (data entry) tabs too.
 *
 * @param {object} dataSet
 * @param {string} tabId
 * @param {object} updatedData - updated tab data
 */
export function updateTab(dataSet, tabId, updatedData) {
  return dataSet.map((tabItem) => {
    if (tabItem.tabId === tabId) {
      tabItem = {
        ...tabItem,
        ...updatedData,
      };
    } else {
      if (tabItem.tabs) {
        tabItem.tabs = updateTab(tabItem.tabs, tabId, updatedData);
      }
    }

    return tabItem;
  });
}

// unfreezes a property (object) only after determining it wasn't frozen in the first place.
const unfreezeProp = (prop) => {
  return Object.isFrozen(prop) ? deepUnfreeze(prop) : prop;
};

// our local copy of https://www.npmjs.com/package/deep-unfreeze
// as it's not maintained anymore and has some problems unfreezing our
// compound date objects
export function deepUnfreeze(obj) {
  if (obj != null) {
    if (
      obj.constructor.name !== 'Date' &&
      !Array.isArray(obj) &&
      typeof obj !== 'function' &&
      typeof obj === 'object'
    ) {
      return Object.getOwnPropertyNames(obj)
        .map((prop) => {
          const clonedObj = {};
          clonedObj[prop] = unfreezeProp(obj[prop]);

          return clonedObj;
        })
        .reduce((leftObj, rightObj) => Object.assign({}, leftObj, rightObj));
    } else if (Array.isArray(obj)) {
      return obj.map((item) => unfreezeProp(item));
    } else if (typeof obj === 'function') {
      const target = function () {
        obj.call(this, ...arguments);
      };
      target.prototype = Object.create(obj.prototype);
      return target;
    }
  }
  return obj;
}
