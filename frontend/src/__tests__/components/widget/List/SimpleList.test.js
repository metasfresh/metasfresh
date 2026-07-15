import React from 'react';
import { mount } from 'enzyme';

import SimpleList from '../../../../components/widget/List/SimpleList';
import { RawList0 as RawListBare } from '../../../../components/widget/List/RawList';
import fixtures from '../../../../../test_setup/fixtures/raw_list.json';

// dev-note: RawList0's TetherComponent reads `this.inputContainerElement.offsetWidth`
// and the dropdown reads `dropdown.offsetWidth`; shim it like RawList.test.js does.
RawListBare.prototype.dropdown = { offsetWidth: 100 };

describe('SimpleList component', () => {
  it('updates the inner RawList highlighted/selected option when `selected` prop changes but `list` reference stays the same', () => {
    // NOTE: same `list` array reference is reused for both mount and setProps,
    // so `listHash` (computed via useMemo(() => uuidv4(), [list])) will NOT change.
    const list = fixtures.data1.listData;
    const itemA = list[0]; // { key: '2000284', caption: 'Divers' }
    const itemB = list[1]; // { key: '2000838', caption: 'Lagerkonferenz' }

    const wrapper = mount(
      <SimpleList list={list} selected={itemA} onSelect={jest.fn()} />
    );

    const rawListInstances = wrapper.find('RawList0');
    expect(rawListInstances.length).toBe(1);

    expect(rawListInstances.instance().state.selected).toEqual(itemA);

    wrapper.setProps({ selected: itemB });
    wrapper.update();

    // AC2: because `listHash` now depends on `selected?.key`, changing `selected` to a
    // different-key item (same `list` ref) regenerates the hash, so RawList's
    // componentDidUpdate re-runs setSelectedValue and the highlighted option updates.
    const updatedRawListInstance = wrapper.find('RawList0').instance();
    expect(updatedRawListInstance.state.selected).toEqual(itemB);
  });

  // Blast-radius guard for the shared consumers (SimulationsDropDown / ResourcesDropDown),
  // which rebuild `selected` as a fresh object literal on every (e.g. websocket-driven)
  // render: the hash must stay STABLE when only `selected`'s identity changes but its key
  // does not — otherwise an open dropdown would reorder/reload under the user's cursor.
  it('does NOT regenerate listHash when `selected` changes identity but keeps the same key', () => {
    const list = fixtures.data1.listData;
    const itemA = list[0];
    const wrapper = mount(
      <SimpleList list={list} selected={itemA} onSelect={jest.fn()} />
    );
    const hashBefore = wrapper.find('RawList0').prop('listHash');

    const sameKeyNewIdentity = { ...itemA }; // new object, same key — the calendar pattern
    expect(sameKeyNewIdentity).not.toBe(itemA);
    wrapper.setProps({ selected: sameKeyNewIdentity });
    wrapper.update();

    expect(wrapper.find('RawList0').prop('listHash')).toBe(hashBefore);
  });

  it('regenerates listHash when the `selected` key changes (re-highlight the applied entry)', () => {
    const list = fixtures.data1.listData;
    const wrapper = mount(
      <SimpleList list={list} selected={list[0]} onSelect={jest.fn()} />
    );
    const hashBefore = wrapper.find('RawList0').prop('listHash');

    wrapper.setProps({ selected: list[1] }); // different key
    wrapper.update();

    expect(wrapper.find('RawList0').prop('listHash')).not.toBe(hashBefore);
  });

  it('starts unfocused by default (shared consumers unchanged) and focused only when keepFocused is set', () => {
    const list = fixtures.data1.listData;

    const dflt = mount(
      <SimpleList list={list} selected={list[0]} onSelect={jest.fn()} />
    );
    // calendar dropdowns pass no keepFocused → identical to the previous useState(false)
    expect(dflt.find('RawList0').prop('isFocused')).toBe(false);

    const kept = mount(
      <SimpleList list={list} selected={list[0]} onSelect={jest.fn()} keepFocused />
    );
    // the email picker opts in → starts focused so it opens on the first click
    expect(kept.find('RawList0').prop('isFocused')).toBe(true);
  });
});
