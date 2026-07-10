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

    // dev-note (bug AC2): `listHash` is memoized on `[list]` only (SimpleList.js line 21),
    // so when `selected` changes but `list` keeps the same reference, RawList's
    // componentDidUpdate never re-runs setSelectedValue (gated on listHash change),
    // leaving the dropdown's highlighted option (state.selected) stale at itemA
    // instead of reflecting the new itemB.
    const updatedRawListInstance = wrapper.find('RawList0').instance();
    expect(updatedRawListInstance.state.selected).toEqual(itemB);
  });
});
