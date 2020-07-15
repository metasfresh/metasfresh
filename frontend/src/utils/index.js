import Moment from 'moment';
import { DATE_FORMAT } from '../constants/Constants';
import queryString from 'query-string';
import counterpart from 'counterpart';

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

// TODO: Not used ? Kuba
export function findRowByPropName(arr, name) {
  let ret = -1;

  if (!arr) {
    return ret;
  }

  for (let i = 0; i < arr.length; i++) {
    if (arr[i].field === name) {
      ret = arr[i];
      break;
    }
  }

  return ret;
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

const cleanupParameter = ({ parameterName, value, valueTo }) => ({
  parameterName,
  value,
  valueTo,
});

export function cleanupFilter({ filterId, parameters }) {
  if (parameters && parameters.length) {
    parameters.map((param, index) => {
      if (param.widgetType === 'Date' && param.value) {
        param.value = Moment(param.value).format(DATE_FORMAT);
      }

      param = cleanupParameter(param);
      parameters[index] = param;
    });
  }

  return {
    filterId,
    parameters,
  };
}

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
