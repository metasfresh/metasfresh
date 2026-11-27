/**
 * @returns {boolean} true if no element holds the cursor: `document.activeElement` is null or `document.body`
 */
export const isCursorHeldByNothing = () => {
  const activeElement = document.activeElement;
  return !activeElement || activeElement === document.body;
};

/**
 * @param {...?{contains: function(Node): boolean}} containers DOM elements, or objects with a `contains(node)` method;
 *        a null/undefined container contains nothing
 * @returns {boolean} true if an element holds the cursor and none of the given containers contains it.
 *          False when nothing holds the cursor (see {@link isCursorHeldByNothing}).
 */
export const isCursorHeldOutside = (...containers) => {
  if (isCursorHeldByNothing()) {
    return false;
  }
  const activeElement = document.activeElement;
  return !containers.some((container) => container?.contains(activeElement));
};

/**
 * @param {?Element} element
 * @returns {?Element} the widget's field container (`.form-group`, which holds all sub-fields of a lookup) the
 *          element is in, else the element itself; null for no element or the body
 */
const fieldContainerOf = (element) => {
  if (!element || element === document.body) {
    return null;
  }
  return element.closest('.form-group') || element;
};

// The field (see fieldContainerOf) the user put the cursor in last, by mouse or keyboard, or typed or clicked in
// while it held the cursor.
let fieldInUseByUser = null;
// True while the browser handles a user's key or mouse press: a focus change then is the user's.
let isHandlingUserInput = false;

/** Forgets the user's last input, as if the user had not touched any field yet (e.g. between tests). */
export const resetUserInputTracking = () => {
  fieldInUseByUser = null;
  isHandlingUserInput = false;
};

/** The browser moves the cursor (Tab, a click) within the task of the key or mouse press. */
const startHandlingUserInput = () => {
  isHandlingUserInput = true;
  setTimeout(() => {
    isHandlingUserInput = false;
  }, 0);
};

// A key goes to the field that has the cursor: the user types in it.
const handleKeyDown = () => {
  startHandlingUserInput();
  fieldInUseByUser = fieldContainerOf(document.activeElement);
};

// A press inside the field that has the cursor is the user clicking in it. A press elsewhere is the user's only
// if it moves the cursor (see handleFocusIn), not e.g. a press on the scrollbar.
const handleMouseDown = (event) => {
  startHandlingUserInput();
  const activeField = fieldContainerOf(document.activeElement);
  if (activeField && activeField.contains(event.target)) {
    fieldInUseByUser = activeField;
  }
};

const handleFocusIn = (event) => {
  if (isHandlingUserInput) {
    fieldInUseByUser = fieldContainerOf(event.target);
  }
};

if (typeof document !== 'undefined') {
  document.addEventListener('keydown', handleKeyDown, true);
  document.addEventListener('mousedown', handleMouseDown, true);
  document.addEventListener('focusin', handleFocusIn, true);
}

/**
 * @param {Element} element
 * @returns {boolean} true if the element is a field the user types or picks a value in: an input, textarea,
 *          select, an editable element, or a list widget's focusable container
 */
const isField = (element) =>
  element.matches('input, textarea, select, [contenteditable="true"]') ||
  element.classList.contains('input-dropdown-container');

/**
 * @param {...?{contains: function(Node): boolean}} containers as for {@link isCursorHeldOutside}
 * @returns {boolean} true if the cursor is in a field outside the given containers and the user works in that
 *          field: the user put the cursor in it, or typed or clicked in it, and the cursor has stayed within that
 *          widget's field container since (an app hand-over between a lookup's sub-fields, or a new input the widget
 *          rendered, still counts). False for a field that got the cursor by itself (e.g. the first field of a loaded
 *          document) unless the user typed or clicked in it, and for an element that is not a field (e.g. a tab or
 *          the grid). Limits: a press that does not move the cursor counts only inside the field that has it; a
 *          widget without a `.form-group` container counts only for the very element the user used.
 */
export const isUserWorkingInFieldOutside = (...containers) => {
  if (!isCursorHeldOutside(...containers)) {
    return false;
  }
  if (fieldInUseByUser && !fieldInUseByUser.isConnected) {
    // the field is gone (e.g. another document was opened); don't keep it alive
    fieldInUseByUser = null;
  }
  const activeElement = document.activeElement;
  return (
    !!fieldInUseByUser &&
    fieldInUseByUser.contains(activeElement) &&
    isField(activeElement)
  );
};
