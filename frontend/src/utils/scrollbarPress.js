/**
 * True when the mouse event's point lies on the target's own scrollbar: inside its border box, outside its
 * client box (which excludes borders and scrollbars), and the target actually overflows.
 * A press on a scrollbar is dispatched with the scrollable element itself as target, so it can otherwise not be told
 * apart from a click on the element's blank area.
 *
 * Overlay scrollbars reserve no space, so they never match.
 *
 * Assumes symmetric borders (`clientLeft`/`clientTop` are used for the right/bottom border too) and ignores sub-pixel
 * rounding of `clientWidth`/`clientHeight` vs the fractional bounding rect (at most a 1px band at the client edge);
 * both hold for the list wrapper, which has no border.
 *
 * @param {{clientX: number, clientY: number, target: Element}} event
 * @returns {boolean}
 */
export const isMouseDownOnScrollbar = ({ clientX, clientY, target }) => {
  if (!target || typeof target.getBoundingClientRect !== 'function') {
    return false;
  }
  const isOverflowing =
    target.scrollHeight > target.clientHeight ||
    target.scrollWidth > target.clientWidth;
  if (!isOverflowing) {
    return false;
  }

  const rect = target.getBoundingClientRect();
  const clientRight = rect.left + target.clientLeft + target.clientWidth;
  const clientBottom = rect.top + target.clientTop + target.clientHeight;
  const isInsideBorderBox =
    clientX >= rect.left &&
    clientX < rect.left + rect.width &&
    clientY >= rect.top &&
    clientY < rect.top + rect.height;

  const isOnVerticalBar =
    clientX >= clientRight &&
    clientX < rect.left + rect.width - target.clientLeft;
  const isOnHorizontalBar =
    clientY >= clientBottom &&
    clientY < rect.top + rect.height - target.clientTop;

  return isInsideBorderBox && (isOnVerticalBar || isOnHorizontalBar);
};
