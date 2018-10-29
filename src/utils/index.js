import queryString from 'query-string';

export const getQueryString = query =>
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
    arr.map(item => {
      if (item[prop] === value) {
        ret.push(item);
      }
    });

  return ret;
}
