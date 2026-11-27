import { fireEvent } from '@testing-library/react';

import {
  isCursorHeldByNothing,
  isCursorHeldOutside,
  isUserWorkingInFieldOutside,
  resetUserInputTracking,
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
    beforeEach(resetUserInputTracking);

    /** A widget's field container (`.form-group`) with the given inputs, e.g. a lookup's sub-fields. */
    const createFieldGroup = (...inputs) => {
      const group = document.createElement('div');
      group.className = 'form-group';
      inputs.forEach((input) => group.appendChild(input));
      document.body.appendChild(group);
      return group;
    };

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

    // E.g. the user picked the business partner and the lookup hands the cursor on to its contact sub-field.
    it('is true when the app moves the cursor on within the field the user works in', async () => {
      const partner = document.createElement('input');
      const contact = document.createElement('input');
      const group = createFieldGroup(partner, contact);
      try {
        clickInto(partner);
        await endOfUserInputTask();
        contact.focus();

        expect(isUserWorkingInFieldOutside(container)).toBe(true);
      } finally {
        group.remove();
      }
    });

    // E.g. a widget renders a new input after a value update.
    it('is true when the field the user works in replaced its input and the new one has the cursor', async () => {
      const oldInput = document.createElement('input');
      const group = createFieldGroup(oldInput);
      try {
        clickInto(oldInput);
        await endOfUserInputTask();
        oldInput.remove();
        const newInput = document.createElement('input');
        group.appendChild(newInput);
        newInput.focus();

        expect(isUserWorkingInFieldOutside(container)).toBe(true);
      } finally {
        group.remove();
      }
    });

    it('is false when the app moves the cursor to another field than the one the user works in', async () => {
      const userInput = document.createElement('input');
      const otherInput = document.createElement('input');
      const userGroup = createFieldGroup(userInput);
      const otherGroup = createFieldGroup(otherInput);
      try {
        clickInto(userInput);
        await endOfUserInputTask();
        otherInput.focus();

        expect(isUserWorkingInFieldOutside(container)).toBe(false);
      } finally {
        userGroup.remove();
        otherGroup.remove();
      }
    });

    // E.g. a press on the scrollbar while the first field of a loaded document has the cursor.
    it('is false when the user presses the mouse outside the field that got the cursor by itself, and the cursor stays there', async () => {
      const field = document.createElement('input');
      const group = createFieldGroup(field);
      const scrollArea = document.createElement('div');
      document.body.appendChild(scrollArea);
      try {
        field.focus();
        await endOfUserInputTask();
        fireEvent.mouseDown(scrollArea);

        expect(isUserWorkingInFieldOutside(container)).toBe(false);
      } finally {
        group.remove();
        scrollArea.remove();
      }
    });

    it('is true when the user presses the mouse inside the field that got the cursor by itself', async () => {
      const field = document.createElement('input');
      const label = document.createElement('label');
      const group = createFieldGroup(label, field);
      try {
        field.focus();
        await endOfUserInputTask();
        fireEvent.mouseDown(label);

        expect(isUserWorkingInFieldOutside(container)).toBe(true);
      } finally {
        group.remove();
      }
    });

    it('is false for a field the user worked in before the tracking was reset', () => {
      clickInto(outside);
      resetUserInputTracking();

      expect(isUserWorkingInFieldOutside(container)).toBe(false);
    });

    it('is false when the user left the field and nothing holds the cursor', () => {
      clickInto(outside);
      outside.blur();

      expect(isUserWorkingInFieldOutside(container)).toBe(false);
    });
  });
});
