import React from 'react';
import { shallow } from 'enzyme';

import { getTableId } from '../../../reducers/tables';
import fixtures from '../../../../test_setup/fixtures/table/table_item_props.json';

import TableRow from '../../../components/table/TableRow';

/**
 * Type-to-activate gate: the default branch of TableRow.handleKeyDown must
 * decide "is this a printable single character?" from event.key, not from
 * String.fromCharCode(event.keyCode).
 *
 * Concrete failure pinned here: numpad-0 (keyCode 96) maps via
 * String.fromCharCode to a backtick, which fails the `[a-zA-Z0-9]` gate, so
 * activation never happens. Main-row-0 (keyCode 48) happens to map to the
 * character "0" and activates today. Both must activate identically since
 * both press produce the same event.key ("0"). A non-printable key (Enter,
 * ArrowDown, an F-key) must NOT activate the cell — event.key for those is a
 * multi-character name, so the printable-single-char gate must keep
 * rejecting them.
 *
 * Activation is observed the same way TableRow itself models it: the row's
 * `edited` state is set to the property name (see handleEditProperty ->
 * _editProperty). Calling handleKeyDown directly (as the existing
 * TableRow.undefinedGuard.test.js does) exercises the real gate without
 * deep-mounting the Redux-connected cell editor.
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

function typeKeyEvent({ key, keyCode, ctrlKey = false, altKey = false }) {
  return {
    key,
    keyCode,
    ctrlKey,
    altKey,
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
    // NOTE: 'Enter' is deliberately excluded here. event.key === 'Enter' is
    // matched by handleKeyDown's own switch-case and routed to
    // handleKeyDown_Enter, which legitimately activates a non-edited cell
    // for editing (pre-existing behavior, untouched by this fix, and NOT
    // part of the type-to-activate default branch under test). Asserting
    // non-activation for it here would pin the wrong code path.
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

  it('does NOT activate on Ctrl/Alt + printable key (regression control)', () => {
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
});
