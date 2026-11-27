import {
  isCursorHeldByNothing,
  isCursorHeldOutside,
} from '../../utils/focusHelpers';

describe('focusHelpers', () => {
  let container;
  let inside;
  let outside;

  beforeEach(() => {
    container = document.createElement('div');
    inside = document.createElement('input');
    container.appendChild(inside);
    outside = document.createElement('input');
    document.body.appendChild(container);
    document.body.appendChild(outside);
  });

  afterEach(() => {
    document.body.removeChild(container);
    document.body.removeChild(outside);
  });

  describe('isCursorHeldByNothing', () => {
    it('is true when the body has the focus', () => {
      document.activeElement.blur();

      expect(isCursorHeldByNothing()).toBe(true);
    });

    it('is false when an element has the focus', () => {
      outside.focus();

      expect(isCursorHeldByNothing()).toBe(false);
    });
  });

  describe('isCursorHeldOutside', () => {
    it('is true when an element outside the container has the focus', () => {
      outside.focus();

      expect(isCursorHeldOutside(container)).toBe(true);
    });

    it('is false when an element inside the container has the focus', () => {
      inside.focus();

      expect(isCursorHeldOutside(container)).toBe(false);
    });

    it('is false when the body has the focus', () => {
      document.activeElement.blur();

      expect(isCursorHeldOutside(container)).toBe(false);
    });

    it('is false when any of several containers holds the focused element', () => {
      outside.focus();

      expect(isCursorHeldOutside(container, outside)).toBe(false);
    });

    it('accepts any object with a contains method as container', () => {
      outside.focus();
      const dropdown = { contains: (node) => node === outside };

      expect(isCursorHeldOutside(container, dropdown)).toBe(false);
    });

    it('treats a missing container as not holding the focus', () => {
      outside.focus();

      expect(isCursorHeldOutside(null, undefined)).toBe(true);
    });
  });
});
