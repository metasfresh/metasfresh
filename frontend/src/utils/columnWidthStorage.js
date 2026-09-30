import { COMBOBOX_MIN_WIDTH_PX, COMBOBOX_WIDGET_TYPES } from './tableHelpers';

const STORAGE_KEY_PREFIX = 'columnWidths_';

/**
 * @method getStorageKey
 * @summary Generate a localStorage key for the given window/view
 */
function getStorageKey(windowId, viewProfileId) {
  return `${STORAGE_KEY_PREFIX}${windowId}${
    viewProfileId ? `_${viewProfileId}` : ''
  }`;
}

/**
 * @method loadColumnWidths
 * @summary Load persisted column widths from localStorage
 * @param {string} windowId
 * @param {string} [viewProfileId]
 * @returns {object} Map of fieldName -> width in pixels
 */
export function loadColumnWidths(windowId, viewProfileId) {
  if (!windowId) return {};

  try {
    const key = getStorageKey(windowId, viewProfileId);
    const stored = localStorage.getItem(key);
    return stored ? JSON.parse(stored) : {};
  } catch (e) {
    return {};
  }
}

/**
 * @method saveColumnWidths
 * @summary Save column widths to localStorage
 * @param {string} windowId
 * @param {string} [viewProfileId]
 * @param {object} widths - Map of fieldName -> width in pixels
 */
export function saveColumnWidths(windowId, viewProfileId, widths) {
  if (!windowId) return;

  try {
    const key = getStorageKey(windowId, viewProfileId);
    if (Object.keys(widths).length === 0) {
      localStorage.removeItem(key);
    } else {
      localStorage.setItem(key, JSON.stringify(widths));
    }
  } catch (e) {
    // localStorage might be full or unavailable
  }
}

/**
 * @method clampComboboxColumnWidths
 * @param {object} columnWidths - Map of fieldName -> stored width in pixels
 * @param {Array} columns - column definitions (`fields[0].field` + `widgetType`), as rendered
 * @summary Load-time combobox floor: a width persisted before the ~210px combobox floor existed
 * (or restored on a returning session) can still be below it, so a stored **combobox**
 * (widgetType Lookup/List) column width under the floor is raised to it here, on the read path.
 * Non-combobox stored widths are returned unchanged (untouched — no floor applies to them).
 */
export function clampComboboxColumnWidths(columnWidths, columns) {
  if (!columnWidths || Object.keys(columnWidths).length === 0) {
    return columnWidths;
  }

  const widgetTypeByField = {};
  (columns || []).forEach((col) => {
    const fieldName = col.fields && col.fields[0] ? col.fields[0].field : null;
    if (fieldName) {
      widgetTypeByField[fieldName] = col.widgetType;
    }
  });

  const clamped = {};
  Object.keys(columnWidths).forEach((fieldName) => {
    const width = columnWidths[fieldName];
    const widgetType = widgetTypeByField[fieldName];

    clamped[fieldName] =
      COMBOBOX_WIDGET_TYPES.indexOf(widgetType) > -1
        ? Math.max(COMBOBOX_MIN_WIDTH_PX, width)
        : width;
  });

  return clamped;
}
