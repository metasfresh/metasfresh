import { updateDataIncludedTabsInfo } from '../../actions/WindowActions';
import reducer, { initialState } from '../../reducers/windowHandler';

/**
 * A PATCH response carries the included tabs' info as a delta: a property the backend did not set is left out
 * (e.g. saving the order's contact sends only `stale: true` for the lines tab). A left-out property must keep its
 * value: losing `allowCreateNew` closes the quick entry the user just opened with Alt+Q.
 */
describe('windowHandler — UPDATE_DATA_INCLUDED_TABS_INFO', () => {
  const linesTab = 'AD_Tab-187';
  const stateWith = (tabInfo) =>
    reducer(
      { ...initialState, master: { ...initialState.master, includedTabsInfo: { [linesTab]: tabInfo } } },
      { type: '@@INIT' }
    );
  const apply = (state, delta) => reducer(state, updateDataIncludedTabsInfo('master', delta));

  it('keeps allowCreateNew and allowDelete when the delta only marks the tab stale', () => {
    const state = stateWith({ tabId: linesTab, allowCreateNew: true, allowDelete: true });

    const next = apply(state, { [linesTab]: { tabId: linesTab, stale: true } });

    expect(next.master.includedTabsInfo[linesTab]).toEqual({
      tabId: linesTab,
      allowCreateNew: true,
      allowDelete: true,
      stale: true,
    });
  });

  it('keeps the reasons when the delta does not send the permission they belong to', () => {
    const refused = {
      tabId: linesTab,
      allowCreateNew: false,
      allowCreateNewReason: 'Processed document',
      allowCreateNewReasonKey: 'SomeKey',
      allowDelete: false,
      allowDeleteReason: 'Processed document',
    };
    const state = stateWith(refused);

    const next = apply(state, { [linesTab]: { tabId: linesTab, stale: true } });

    expect(next.master.includedTabsInfo[linesTab]).toEqual({ ...refused, stale: true });
  });

  it('takes allowCreateNew from the delta together with its reason, dropping the previous reason', () => {
    const state = stateWith({
      tabId: linesTab,
      allowCreateNew: false,
      allowCreateNewReason: 'ParentDocumentProcessed',
      allowCreateNewReasonKey: 'SomeKey',
      allowDelete: true,
    });

    const next = apply(state, { [linesTab]: { tabId: linesTab, allowCreateNew: true } });

    expect(next.master.includedTabsInfo[linesTab]).toEqual({
      tabId: linesTab,
      allowCreateNew: true,
      allowDelete: true,
    });
  });

  it('takes allowDelete from the delta together with its reason, dropping the previous reason', () => {
    const state = stateWith({ tabId: linesTab, allowCreateNew: true, allowDelete: false, allowDeleteReason: 'old' });

    const next = apply(state, { [linesTab]: { tabId: linesTab, allowDelete: true } });

    expect(next.master.includedTabsInfo[linesTab]).toEqual({ tabId: linesTab, allowCreateNew: true, allowDelete: true });
  });

  it('adds a tab that was not known before and keeps the other tabs', () => {
    const state = stateWith({ tabId: linesTab, allowCreateNew: true });

    const next = apply(state, { 'AD_Tab-236': { tabId: 'AD_Tab-236', allowCreateNew: false } });

    expect(next.master.includedTabsInfo).toEqual({
      [linesTab]: { tabId: linesTab, allowCreateNew: true },
      'AD_Tab-236': { tabId: 'AD_Tab-236', allowCreateNew: false },
    });
  });
});
