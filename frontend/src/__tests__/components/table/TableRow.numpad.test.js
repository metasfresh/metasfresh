import React from 'react';
import { shallow } from 'enzyme';

import { getTableId } from '../../../reducers/tables';
import fixtures from '../../../../test_setup/fixtures/table/table_item_props.json';

import TableRow from '../../../components/table/TableRow';

/**
 * Typing a key on a focused, not-yet-edited grid cell starts editing it ("activates" it) only for
 * a single letter or digit, as reported by event.key: numpad-0 and main-row-0 both activate, while
 * non-printable keys, Ctrl/Alt combinations, Space and punctuation do not.
 *
 * Activation is observed as the row's `edited` state becoming the cell's field name.
 */

const PROPERTY = 'QtyEntered'; // fixture: scalar, editable cell

function createInitProps(customProps) {
  const propsSeed = fixtures.oldProps1;
  return {
    ...propsSeed,
    tableId: getTableId(propsSeed),
    onClick: jest.fn(),
    handleSelect: jest.fn(),
    onDoubleClick: jest.fn(),
    changeListenOnTrue: jest.fn(),
    changeListenOnFalse: jest.fn(),
    handleRowCollapse: jest.fn(),
    handleRightClick: jest.fn(),
    onItemChange: jest.fn(),
    getSizeClass: jest.fn(),
    updatePropertyValue: jest.fn(),
    ...customProps,
  };
}

function typeKeyEvent({
  key,
  keyCode,
  ctrlKey = false,
  altKey = false,
  metaKey = false,
}) {
  return {
    key,
    keyCode,
    ctrlKey,
    altKey,
    metaKey,
    target: { value: key },
    persist: jest.fn(),
    stopPropagation: jest.fn(),
  };
}

function fireKeyDown(instance, event) {
  instance.handleKeyDown({
    event,
    property: PROPERTY,
    readonly: false,
    isAttributeWidget: false,
  });
}

describe('TableRow — type-to-activate gate keys off event.key (numpad-0)', () => {
  it('activates the cell on numpad-0 (keyCode 96), same as main-row-0 (keyCode 48)', () => {
    const numpadWrapper = shallow(<TableRow {...createInitProps()} />);
    fireKeyDown(
      numpadWrapper.instance(),
      typeKeyEvent({ key: '0', keyCode: 96 })
    );
    expect(numpadWrapper.instance().state.edited).toBe(PROPERTY);

    const mainRowWrapper = shallow(<TableRow {...createInitProps()} />);
    fireKeyDown(
      mainRowWrapper.instance(),
      typeKeyEvent({ key: '0', keyCode: 48 })
    );
    expect(mainRowWrapper.instance().state.edited).toBe(PROPERTY);
  });

  it('does NOT activate the cell on non-printable keys (ArrowDown/F-key)', () => {
    // Enter is not listed: it has its own handling, which does open the cell for editing.
    const arrowWrapper = shallow(<TableRow {...createInitProps()} />);
    fireKeyDown(
      arrowWrapper.instance(),
      typeKeyEvent({ key: 'ArrowDown', keyCode: 40 })
    );
    expect(arrowWrapper.instance().state.edited).not.toBe(PROPERTY);

    const fKeyWrapper = shallow(<TableRow {...createInitProps()} />);
    fireKeyDown(
      fKeyWrapper.instance(),
      typeKeyEvent({ key: 'F5', keyCode: 116 })
    );
    expect(fKeyWrapper.instance().state.edited).not.toBe(PROPERTY);
  });

  it('does NOT activate on Ctrl/Alt + printable key', () => {
    const ctrlWrapper = shallow(<TableRow {...createInitProps()} />);
    fireKeyDown(
      ctrlWrapper.instance(),
      typeKeyEvent({ key: '0', keyCode: 48, ctrlKey: true })
    );
    expect(ctrlWrapper.instance().state.edited).not.toBe(PROPERTY);

    const altWrapper = shallow(<TableRow {...createInitProps()} />);
    fireKeyDown(
      altWrapper.instance(),
      typeKeyEvent({ key: '0', keyCode: 48, altKey: true })
    );
    expect(altWrapper.instance().state.edited).not.toBe(PROPERTY);
  });

  it('does NOT activate on Cmd (meta) + printable key, e.g. Cmd+V on a Mac', () => {
    const wrapper = shallow(<TableRow {...createInitProps()} />);
    fireKeyDown(
      wrapper.instance(),
      typeKeyEvent({ key: 'v', keyCode: 86, metaKey: true })
    );
    expect(wrapper.instance().state.edited).not.toBe(PROPERTY);
  });
});

describe('TableRow — type-to-activate gate is restricted to letters and digits', () => {
  // Activation replaces the cell content, so Space or "-" on a filled cell must not activate it.
  it.each([
    ['Space', ' ', 32],
    ['minus', '-', 189],
    ['numpad minus', '-', 109],
    ['period', '.', 190],
    ['comma', ',', 188],
    ['numpad plus', '+', 107],
    ['numpad divide', '/', 111],
    ['numpad multiply', '*', 106],
  ])('does NOT activate the cell on %s', (_label, key, keyCode) => {
    const wrapper = shallow(<TableRow {...createInitProps()} />);
    fireKeyDown(wrapper.instance(), typeKeyEvent({ key, keyCode }));
    expect(wrapper.instance().state.edited).not.toBe(PROPERTY);
  });

  it.each(['ä', 'ö', 'ü', 'Ä', 'Ö', 'Ü', 'ß'])(
    'activates the cell on the German letter %s',
    (key) => {
      const wrapper = shallow(<TableRow {...createInitProps()} />);
      fireKeyDown(wrapper.instance(), typeKeyEvent({ key, keyCode: 0 }));
      expect(wrapper.instance().state.edited).toBe(PROPERTY);
    }
  );

  it('activates the cell on an ASCII letter (a)', () => {
    const wrapper = shallow(<TableRow {...createInitProps()} />);
    fireKeyDown(wrapper.instance(), typeKeyEvent({ key: 'a', keyCode: 65 }));
    expect(wrapper.instance().state.edited).toBe(PROPERTY);
  });

  it('does not throw and does NOT activate when event.key is undefined', () => {
    const wrapper = shallow(<TableRow {...createInitProps()} />);
    expect(() =>
      fireKeyDown(
        wrapper.instance(),
        typeKeyEvent({ key: undefined, keyCode: 65 })
      )
    ).not.toThrow();
    expect(wrapper.instance().state.edited).not.toBe(PROPERTY);
  });
});
