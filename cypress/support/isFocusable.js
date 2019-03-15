const DISABLEABLE_ELEMENTS = ['input', 'button', 'select', 'textarea', 'button', 'object'];

/**
 * Determines if the given element is focusable.
 *
 * Note: this is a naive/simplified version adapted from jQuery UI. It does not support image maps,
 * disabled fieldsets, among other things.
 */
export default function isFocusable($element) {
  const nodeName = $element.prop('nodeName').toLowerCase();
  return (
    (nodeName === 'a' ||
      !!$element.attr('tabindex') ||
      (DISABLEABLE_ELEMENTS.includes(nodeName) && $element.is(':enabled'))) &&
    $element.is(':visible')
  );
}
