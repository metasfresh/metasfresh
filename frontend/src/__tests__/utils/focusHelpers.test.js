import { fireEvent } from '@testing-library/react';

import {
  isCursorHeldByNothing,
  isCursorHeldOutside,
  isUserWorkingInFieldOutside,
} from '../../utils/focusHelpers';

/** Lets the task of the previous user input end, so a later focus counts as not caused by it. */
const endOfUserInputTask = () =>
  new Promise((resolve) => setTimeout(resolve, 0));

/** The user puts the cursor in the element with the mouse: mousedown, and the browser focuses it in the same task. */
const clickInto = (element) => {
  fireEvent.mouseDown(element);
  element.focus();
};

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

  describe('isUserWorkingInFieldOutside', () => {
    beforeEach(endOfUserInputTask);

    it('is true when the user clicked into a field outside the container', () => {
      clickInto(outside);

      expect(isUserWorkingInFieldOutside(container)).toBe(true);
    });

    it('is true when the user moved the cursor into a field outside the container with the keyboard (Tab)', () => {
      inside.focus();
      fireEvent.keyDown(inside, { key: 'Tab' });
      outside.focus();

      expect(isUserWorkingInFieldOutside(container)).toBe(true);
    });

    it('is false when a field outside the container got the cursor without the user (e.g. the first field of a loaded document)', () => {
      outside.focus();

      expect(isUserWorkingInFieldOutside(container)).toBe(false);
    });

    it('is true when the user types in a field outside the container that got the cursor without the user', async () => {
      outside.focus();
      await endOfUserInputTask();
      fireEvent.keyDown(outside, { key: 'a' });

      expect(isUserWorkingInFieldOutside(container)).toBe(true);
    });

    it('is true for a list widget the user clicked (its focusable container, not an input)', () => {
      const list = document.createElement('div');
      list.className = 'input-dropdown-container';
      list.tabIndex = 0;
      document.body.appendChild(list);
      try {
        clickInto(list);

        expect(isUserWorkingInFieldOutside(container)).toBe(true);
      } finally {
        list.remove();
      }
    });

    it('is false when the user clicked an element outside the container that is not a field (e.g. a tab or the grid)', () => {
      const tab = document.createElement('li');
      tab.tabIndex = 0;
      document.body.appendChild(tab);
      try {
        clickInto(tab);

        expect(isUserWorkingInFieldOutside(container)).toBe(false);
      } finally {
        tab.remove();
      }
    });

    it('is false when the user clicked into a field inside the container', () => {
      clickInto(inside);

      expect(isUserWorkingInFieldOutside(container)).toBe(false);
    });

    it('is false when the user left the field and nothing holds the cursor', () => {
      clickInto(outside);
      outside.blur();

      expect(isUserWorkingInFieldOutside(container)).toBe(false);
    });
  });
});
