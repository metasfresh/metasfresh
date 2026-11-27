import reducer, { initialState } from '../../reducers/headers';
import { pushHeaderEntry, updateHeaderEntry } from '../../actions/HeaderActions';

describe('headers reducer: rootDataAttributes', () => {
  it('keeps the root data attributes of the screen across the later header update', () => {
    let state = reducer(initialState, pushHeaderEntry({ location: '/line/1', screenId: 'PickLineScanScreen' }));
    state = reducer(
      state,
      updateHeaderEntry({
        location: '/line/1',
        values: [{ caption: 'c', value: 'v' }],
        rootDataAttributes: { 'data-cu-qty-entry-mode': 'SCAN_ONCE_ENTER_QTY' },
      })
    );
    state = reducer(state, updateHeaderEntry({ location: '/line/1', values: [{ caption: 'c', value: 'w' }] }));

    const entry = state.entries.find((e) => e.location === '/line/1');
    expect(entry.screenId).toBe('PickLineScanScreen');
    expect(entry.rootDataAttributes).toEqual({ 'data-cu-qty-entry-mode': 'SCAN_ONCE_ENTER_QTY' });
  });
});
