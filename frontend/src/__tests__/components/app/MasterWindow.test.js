import React from 'react';
import { shallow } from 'enzyme';

import { getTableId } from '../../../reducers/tables';

import MasterWindow from '../../../components/app/MasterWindow';
import { discardNewRequest, getRowsData } from '../../../api';

// closeModalCallback calls discardNewRequest (from ../../../api) and awaits its
// promise, then — for an already-persisted (numeric-id) abandoned row — re-fetches
// that row via getRowsData and applies its reverted DB value to the parent grid.
jest.mock('../../../api', () => ({
  __esModule: true,
  discardNewRequest: jest.fn(() => Promise.resolve()),
  getRowsData: jest.fn(() => Promise.resolve({ data: { result: [] } })),
}));

describe('MasterWindow.closeModalCallback - abandon must not drop a persisted new-record row', () => {
  const WINDOW_TYPE = '540321';
  const DOCUMENT_ID = 'd1';
  const TAB_ID = 't1';

  // Minimal props so shallow() can construct the instance without render throwing.
  const buildProps = (overrides = {}) => ({
    modal: { visible: false },
    master: {
      docId: DOCUMENT_ID,
      data: { DocumentNo: undefined },
      layout: {},
      includedTabsInfo: {},
      hasComments: false,
    },
    breadcrumb: [],
    rawModal: {},
    pluginModal: {},
    me: {},
    overlay: { data: {}, visible: false },
    params: { windowId: WINDOW_TYPE, docId: DOCUMENT_ID },
    updateTabRowsData: jest.fn(),
    onRefreshTab: jest.fn(),
    ...overrides,
  });

  const getInstance = (props) =>
    shallow(<MasterWindow {...props} />).instance();

  // A realistic ?ids= re-fetch response: the persisted row, reverted server-side
  // to its DB ValidFrom (a far-future year) after discardChanges — the abandoned
  // in-memory colliding value (01/01/2015) is gone.
  const PERSISTED_ROW_ID = '1234567';
  const revertedRowsResponse = {
    data: {
      result: [
        {
          rowId: PERSISTED_ROW_ID,
          fieldsByName: {
            ValidFrom: {
              field: 'ValidFrom',
              value: '2053-11-30',
              widgetType: 'Date',
            },
          },
        },
      ],
    },
  };

  beforeEach(() => {
    discardNewRequest.mockClear();
    discardNewRequest.mockResolvedValue();
    getRowsData.mockClear();
    getRowsData.mockResolvedValue(revertedRowsResponse);
  });

  // Case A - the bug: a modal auto-saved (rowId became a real numeric id) but
  // saveStatus stayed falsy. Abandon must NOT remove the now-persisted row.
  it('does not remove a persisted (numeric rowId) row when saveStatus is falsy', async () => {
    const props = buildProps();
    const instance = getInstance(props);

    await instance.closeModalCallback({
      isNew: true,
      windowType: WINDOW_TYPE,
      documentId: DOCUMENT_ID,
      tabId: TAB_ID,
      rowId: PERSISTED_ROW_ID,
      saveStatus: false,
    });

    const removedPersistedRow = props.updateTabRowsData.mock.calls.some(
      ([, payload]) =>
        payload && payload.removed && payload.removed[PERSISTED_ROW_ID]
    );
    expect(removedPersistedRow).toBe(false);
  });

  // Case B - regression guard: a genuinely unsaved new row (rowId === 'NEW')
  // must still be discarded from the parent grid.
  it('removes an unsaved new row (rowId === NEW) when saveStatus is falsy', async () => {
    const props = buildProps();
    const instance = getInstance(props);

    await instance.closeModalCallback({
      isNew: true,
      windowType: WINDOW_TYPE,
      documentId: DOCUMENT_ID,
      tabId: TAB_ID,
      rowId: 'NEW',
      saveStatus: false,
    });

    const expectedTableId = getTableId({
      windowId: WINDOW_TYPE,
      docId: DOCUMENT_ID,
      tabId: TAB_ID,
    });
    expect(props.updateTabRowsData).toHaveBeenCalledWith(expectedTableId, {
      removed: { NEW: true },
    });
  });

  // Case C - the fix: abandoning an already-persisted (numeric rowId) row with a
  // falsy saveStatus must re-fetch the row and UPDATE (not remove) it in the grid,
  // so the retained row shows its reverted DB value without a browser reload.
  it('re-fetches and UPDATES a persisted row with its reverted DB value on abandon', async () => {
    const props = buildProps();
    const instance = getInstance(props);

    await instance.closeModalCallback({
      isNew: true,
      windowType: WINDOW_TYPE,
      documentId: DOCUMENT_ID,
      tabId: TAB_ID,
      rowId: PERSISTED_ROW_ID,
      saveStatus: false,
    });

    const expectedTableId = getTableId({
      windowId: WINDOW_TYPE,
      docId: DOCUMENT_ID,
      tabId: TAB_ID,
    });

    // the reverted DB row was re-fetched for exactly this persisted row
    expect(getRowsData).toHaveBeenCalledWith(
      expect.objectContaining({
        entity: 'window',
        docType: WINDOW_TYPE,
        docId: DOCUMENT_ID,
        tabId: TAB_ID,
        rows: [PERSISTED_ROW_ID],
      })
    );

    // it was applied as an UPDATE (changed), never a removal
    const updateCall = props.updateTabRowsData.mock.calls.find(
      ([, payload]) =>
        payload && payload.changed && payload.changed[PERSISTED_ROW_ID]
    );
    expect(updateCall).toBeDefined();
    expect(updateCall[0]).toBe(expectedTableId);
    expect(
      updateCall[1].changed[PERSISTED_ROW_ID].fieldsByName.ValidFrom.value
    ).toBe('2053-11-30');

    const removedAny = props.updateTabRowsData.mock.calls.some(
      ([, payload]) => payload && payload.removed
    );
    expect(removedAny).toBe(false);
  });

  // Case D - reconcile the deleted-row edge: if the persisted row was removed
  // server-side between the abandon and the re-fetch, the ?ids= GET reports it in
  // missingIds → the grid row must be removed (not left stranded).
  it('removes a persisted row from the grid when the re-fetch reports it missing', async () => {
    getRowsData.mockResolvedValue({
      data: { result: [], missingIds: [PERSISTED_ROW_ID] },
    });

    const props = buildProps();
    const instance = getInstance(props);

    await instance.closeModalCallback({
      isNew: true,
      windowType: WINDOW_TYPE,
      documentId: DOCUMENT_ID,
      tabId: TAB_ID,
      rowId: PERSISTED_ROW_ID,
      saveStatus: false,
    });

    const expectedTableId = getTableId({
      windowId: WINDOW_TYPE,
      docId: DOCUMENT_ID,
      tabId: TAB_ID,
    });
    expect(props.updateTabRowsData).toHaveBeenCalledWith(expectedTableId, {
      removed: { [PERSISTED_ROW_ID]: true },
    });
  });

  // Case E - the .catch() fallback: if the revert re-fetch itself fails, the grid
  // row would otherwise strand showing the stale abandoned value. The fallback must
  // fire a full tab refresh (onRefreshTab) and must NOT apply any re-fetched row
  // update to the grid.
  it('falls back to a tab refresh (and does not update the grid) when the revert re-fetch fails', async () => {
    getRowsData.mockRejectedValue(new Error('re-fetch failed'));
    // the fallback intentionally logs the failure — silence it to keep the suite output clean
    const consoleErrorSpy = jest
      .spyOn(console, 'error')
      .mockImplementation(() => {});

    const props = buildProps();
    const instance = getInstance(props);

    await instance.closeModalCallback({
      isNew: true,
      windowType: WINDOW_TYPE,
      documentId: DOCUMENT_ID,
      tabId: TAB_ID,
      rowId: PERSISTED_ROW_ID,
      saveStatus: false,
    });

    // the re-fetch was attempted for the persisted row
    expect(getRowsData).toHaveBeenCalledWith(
      expect.objectContaining({ rows: [PERSISTED_ROW_ID] })
    );
    // the fallback fired
    expect(props.onRefreshTab).toHaveBeenCalledTimes(1);
    // no re-fetch-derived grid update was applied (the fetch rejected)
    expect(props.updateTabRowsData).not.toHaveBeenCalled();

    consoleErrorSpy.mockRestore();
  });

  // Case F - the discard call itself fails. discardNewRequest is the FIRST async
  // step of the chain; if it rejects (network error, 500 from /discardChanges) the
  // whole reconcile branch never runs, so without a fallback the grid strands on
  // the abandoned in-memory value exactly as in Case E. Modal.closeModal() does not
  // await this callback, so an unhandled rejection would also surface no error at
  // all. The same tab-refresh fallback must fire, and the returned promise must
  // settle rather than reject.
  it('falls back to a tab refresh when the discard request itself fails', async () => {
    discardNewRequest.mockRejectedValue(new Error('discard failed'));
    const consoleErrorSpy = jest
      .spyOn(console, 'error')
      .mockImplementation(() => {});

    const props = buildProps();
    const instance = getInstance(props);

    await expect(
      instance.closeModalCallback({
        isNew: true,
        windowType: WINDOW_TYPE,
        documentId: DOCUMENT_ID,
        tabId: TAB_ID,
        rowId: PERSISTED_ROW_ID,
        saveStatus: false,
      })
    ).resolves.toBeUndefined();

    // the discard rejected, so no re-fetch was attempted ...
    expect(getRowsData).not.toHaveBeenCalled();
    // ... and no grid update was applied ...
    expect(props.updateTabRowsData).not.toHaveBeenCalled();
    // ... but the grid was still reconciled with the DB via a full tab refresh
    expect(props.onRefreshTab).toHaveBeenCalledTimes(1);

    consoleErrorSpy.mockRestore();
  });

  // Case G - guard branch: a modal that is not a new-record modal is none of this
  // callback's business. It must short-circuit before any server call, so an
  // ordinary edit-modal close cannot discard the document or disturb the grid.
  it('does nothing at all when the modal is not a new-record modal', async () => {
    const props = buildProps();
    const instance = getInstance(props);

    const result = instance.closeModalCallback({
      isNew: false,
      windowType: WINDOW_TYPE,
      documentId: DOCUMENT_ID,
      tabId: TAB_ID,
      rowId: PERSISTED_ROW_ID,
      saveStatus: false,
    });

    expect(result).toBeUndefined();
    expect(discardNewRequest).not.toHaveBeenCalled();
    expect(getRowsData).not.toHaveBeenCalled();
    expect(props.updateTabRowsData).not.toHaveBeenCalled();
    expect(props.onRefreshTab).not.toHaveBeenCalled();
  });

  // Case H - a saved modal owns no abandoned in-memory change, so there is nothing
  // to reconcile: the discard still runs, but the grid must be left untouched (no
  // re-fetch, no row update, no removal).
  it('leaves the grid untouched when the modal was saved (saveStatus truthy)', async () => {
    const props = buildProps();
    const instance = getInstance(props);

    await instance.closeModalCallback({
      isNew: true,
      windowType: WINDOW_TYPE,
      documentId: DOCUMENT_ID,
      tabId: TAB_ID,
      rowId: PERSISTED_ROW_ID,
      saveStatus: true,
    });

    expect(discardNewRequest).toHaveBeenCalledTimes(1);
    expect(getRowsData).not.toHaveBeenCalled();
    expect(props.updateTabRowsData).not.toHaveBeenCalled();
    expect(props.onRefreshTab).not.toHaveBeenCalled();
  });
});
