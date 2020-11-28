import isTabbable from './isTabbable';

/**
 * Returns the next element that would received focus using Tab key navigation.
 *
 * Note: this uses a naive/simplified algorithm adapted from jQuery UI. It does not support image
 * maps, disabled fieldsets, among other things.
 */
export default function nextTabbable($referenceElement, direction = 'forward') {
  if (!(direction === 'forward' || direction === 'backward')) {
    throw new Error('Expected direction to be forward or backward');
  }

  const stack = [];
  let element;

  // Queue up all siblings and our ancestor's siblings.
  const siblingProp = direction === 'forward' ? 'nextElementSibling' : 'previousElementSibling';
  element = $referenceElement.get(0);
  while (element) {
    let sibling = element[siblingProp];
    while (sibling) {
      stack.unshift(sibling);
      sibling = sibling[siblingProp];
    }
    element = element.parentElement;
  }

  // Find a tabbable element among our siblings using depth first search.
  while (stack.length > 0) {
    element = stack.pop();

    const $candidateElement = $referenceElement.constructor(element);
    if (isTabbable($candidateElement)) {
      return $candidateElement;
    }

    let children = Array.from(element.children);
    if (direction === 'forward') {
      children = children.reverse();
    }
    children.forEach(child => {
      stack.push(child);
    });
  }

  return $referenceElement.constructor();
}
