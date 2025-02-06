import queryString from 'query-string';
import counterpart from 'counterpart';
import { updateLastBackPage } from '../actions/AppActions';
import history from '../services/History';

/**
 * @method updateUri
 * @summary Replaces the history url with updated query URL that contain viewId/page/sorting
 */
export function updateUri(pathname, query, updatedQuery) {
  const isDifferentPage =
    query.page && Number(query.page) !== Number(updatedQuery.page);
  const isDifferentView = query.viewId && query.viewId !== updatedQuery.viewId;

  const queryObject = {
    ...query,
    ...updatedQuery,
  };
  const queryUrl = queryString.stringify(queryObject);
  const url = `${pathname}?${queryUrl}`;

  isDifferentPage || isDifferentView ? history.push(url) : history.replace(url);
}

/**
 * @method historyDoubleBackOnPopstate
 * @summary Does move back twice in history - on popstate (back btn) - this acts as a `patch` for the cases introduced by the above `updateUri` function
 *          when there are no viewIs and we end up with one one added to the URL.
 *          i.e when we go to http://localhost:3000/window/143 we are taken to an URL like http://localhost:3000/window/143?page=1&sort&viewId=143-CQ
 *          If we do not use this function when the user hits the back button he will remain on http://localhost:3000/window/143?page=1&sort&viewId=143-CQ
 *          as if nothing happen. Instead if we use this funcion on popstate we will be taken back to the appropriate page before we landed on
 *          http://localhost:3000/window/143
 * @param {object} store - redux store
 */
export function historyDoubleBackOnPopstate(store) {
  const appState = store.getState().appHandler;
  const { lastBackPage } = appState;

  if (
    lastBackPage &&
    lastBackPage.includes('viewId') &&
    lastBackPage === document.location.href
  ) {
    window.history.back(-2);
  }

  store.dispatch(updateLastBackPage(document.location.href));
}

/**
 * @method getQueryString
 * @summary Stringifies URL with 'query-string', formatting query and escaping unwanted characters
 */
export const getQueryString = (query) => {
  return queryString.stringify(query, { arrayFormat: 'comma', skipNull: true });
};

export const buildURL = (baseUrl, query) => {
  const queryString = query ? getQueryString(query) : null;
  return !queryString ? baseUrl : `${baseUrl}?${queryString}`;
};

// TODO: Move to api ?
export const createPatchRequestPayload = (property, value) => {
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
};

export const toSingleFieldPatchRequest = (fieldName, value) => ({
  op: 'replace',
  path: fieldName,
  value,
});

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
 * @param {function} dispatch
 * @param {function} actionName to dispatch - added this in case we need to perform custom actions when opening new tab ()
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

/**
 * @method leftTrim
 * @summary - removes spaces from the left side of a string
 * @param {string} str
 */
export function leftTrim(str) {
  return str.replace(/^\s+/, '');
}

export const isBlank = (str) => {
  return !str || str.length === 0 || str.trim().length === 0;
};
