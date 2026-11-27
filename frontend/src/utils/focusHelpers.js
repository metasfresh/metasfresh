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
