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

// The element the user put the cursor in last (by mouse or keyboard), or typed or clicked in while it held the cursor.
let elementInUseByUser = null;
// True while the browser handles a user's key or mouse press: a focus change then is the user's.
let isHandlingUserInput = false;

const handleUserInput = () => {
  isHandlingUserInput = true;
  elementInUseByUser = document.activeElement;
  // the browser moves the cursor (Tab, a click) within the task of the key or mouse press
  setTimeout(() => {
    isHandlingUserInput = false;
  }, 0);
};

const handleFocusIn = (event) => {
  if (isHandlingUserInput) {
    elementInUseByUser = event.target;
  }
};

if (typeof document !== 'undefined') {
  document.addEventListener('keydown', handleUserInput, true);
  document.addEventListener('mousedown', handleUserInput, true);
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
 * @returns {boolean} true if the cursor is in a field outside the given containers and the user works in it: the
 *          user put the cursor there, or typed or clicked in it. False for a field that got the cursor by itself
 *          (e.g. the first field of a loaded document) and for an element that is not a field (e.g. a tab or the grid).
 */
export const isUserWorkingInFieldOutside = (...containers) => {
  if (!isCursorHeldOutside(...containers)) {
    return false;
  }
  const activeElement = document.activeElement;
  return activeElement === elementInUseByUser && isField(activeElement);
};
