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
