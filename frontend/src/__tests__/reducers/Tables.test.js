import { merge } from 'merge-anything';

import { deleteTable, updateTableSelection } from '../../actions/TableActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';
import reducer, {
  initialState,
  initialTableState,
} from '../../reducers/tables';

const createState = function(state = {}) {
  return merge(
    {
      ...initialState,
    },
    state
  );
};

const basicData = {
  windowId: '143',
  docId: '1000037',
  tabId: 'AD_Tab-187',
  keyProperty: 'id',
};

describe('Tables reducer', () => {
  it('should return the initial state', () => {
    expect(reducer(undefined, {})).toEqual(initialState);
  });

  it('Should handle CREATE_TABLE', () => {
    const id = '143_1000037_AD_Tab-187';

    expect(
      reducer(undefined, {
        type: ACTION_TYPES.CREATE_TABLE,
        payload: {
          id,
          data: basicData,
        },
      })
    ).toEqual(
      expect.objectContaining({
        [id]: expect.objectContaining({ ...basicData }),
        length: 1,
      })
    );
  });

  it('Should handle CREATE_TABLE with no rows', () => {
    const id = '143_1000037_AD_Tab-187';

    expect(
      reducer(undefined, {
        type: ACTION_TYPES.CREATE_TABLE,
        payload: {
          id,
          data: { ...basicData, rows: [] },
        },
      })
    ).toEqual(
      expect.objectContaining({
        [id]: expect.objectContaining({ ...basicData }),
        length: 1,
      })
    );
  });

  it('Should handle UPDATE_TABLE', () => {
    const id = '143_1000037_AD_Tab-187';
    const initialStateData = createState({
      [id]: { ...initialTableState, ...basicData },
      length: 1,
    });
    const updateData = {
      internalName: 'C_OrderLine',
      elements: [
        {
          caption: 'Not avail. on short term',
          fields: [
            {
              field: 'InsufficientQtyAvailableForSalesColor_ID',
            },
          ],
        },
      ],
    };
    const updateAction = {
      type: ACTION_TYPES.UPDATE_TABLE,
      payload: {
        id,
        data: updateData,
      },
    };

    const actions = [updateAction];
    const state = actions.reduce(reducer, initialStateData);

    expect(state).toEqual(
      expect.objectContaining({
        [id]: expect.objectContaining({
          docId: '1000037',
          elements: [
            {
              caption: 'Not avail. on short term',
              fields: [{ field: 'InsufficientQtyAvailableForSalesColor_ID' }],
            },
          ],
          tabId: 'AD_Tab-187',
          windowId: '143',
        }),
        length: 1,
      })
    );
  });

  it('Should handle DELETE_TABLE', () => {
    const id = '143_1000037_AD_Tab-187';
    const initialStateData = createState({
      [id]: { ...initialTableState, ...basicData },
      length: 1,
    });
    const deleteAction = {
      type: ACTION_TYPES.DELETE_TABLE,
      payload: { id },
    }
    const actions = [deleteAction];
    const state = actions.reduce(reducer, initialStateData);

    expect(state).toEqual({ length: 0 });
  });

  it('Should handle SET_ACTIVE_SORT', () => {
    const id = '143_1000037_AD_Tab-187';
    const initialStateData = createState({
      [id]: { ...initialTableState, ...basicData },
      length: 1,
    });

    const updateAction = {
      type: ACTION_TYPES.SET_ACTIVE_SORT,
      payload: {
        id,
        active: true,
      },
    };

    const actions = [updateAction];
    const state = actions.reduce(reducer, initialStateData);

    expect(state).toEqual(
      expect.objectContaining({
        [id]: expect.objectContaining({
          activeSort: true,
        }),
        length: 1,
      })
    );
  });

  it('Should update selection UPDATE_TABLE_SELECTION', () => {
    const id = '143_1000037_AD_Tab-187';
    const row = { rowId: '100000' };
    const initialStateData = createState({
      [id]: { ...initialTableState, ...basicData, rows: [row], selected: ['100000'], keyProperty: 'rowId' },
      length: 1,
    });
    const actions = [updateTableSelection(id, ['100000'], 'rowId')];
    const state = actions.reduce(reducer, initialStateData);

    expect(state).toEqual(
      expect.objectContaining({
        [id]: expect.objectContaining({
          activeSort: false,
          columns: [],
          collapsedParentRows: [],
          collapsedRows: [],
          docId: '1000037',
          emptyHint: null,
          emptyText: null,
          headerElements: {},
          headerProperties: {},
          rows: expect.arrayContaining([row]),
          selected: ['100000'],
          size: 0,
          tabId: 'AD_Tab-187',
          viewId: null,
          windowId: '143',
          keyProperty: 'rowId',
          expandedDepth: 0,
          collapsible: false,
          indentSupported: false,
        }),
        length: 1,
      })
    );
  });

  // Regression tests for race condition: actions dispatched before table creation
  describe('Race condition handling - actions before table creation', () => {
    it('Should handle SET_ACTIVE_SORT gracefully when table does not exist', () => {
      const nonExistentId = '541851_541851-g';

      const action = {
        type: ACTION_TYPES.SET_ACTIVE_SORT,
        payload: {
          id: nonExistentId,
          active: true,
        },
      };

      // Should not throw - this was causing white-screen crashes
      expect(() => {
        reducer(initialState, action);
      }).not.toThrow();

      // State should remain unchanged
      const state = reducer(initialState, action);
      expect(state).toEqual(initialState);
      expect(state[nonExistentId]).toBeUndefined();
    });

    it('Should still set activeSort when table exists', () => {
      const id = '143_1000037_AD_Tab-187';
      const initialStateData = createState({
        [id]: { ...initialTableState, ...basicData, activeSort: false },
        length: 1,
      });

      const action = {
        type: ACTION_TYPES.SET_ACTIVE_SORT,
        payload: {
          id,
          active: true,
        },
      };

      const state = reducer(initialStateData, action);
      expect(state[id].activeSort).toBe(true);
    });
  });

  describe('Tab column sort — SORT_TAB + UPDATE_TAB_ROWS_DATA with orderBys', () => {
    const windowId = '541851';
    const docId = '1000001';
    const tabId = 'AD_Tab-999';
    const tableId = `${windowId}_${docId}_${tabId}`;

    const numericRow = (rowId, amount) => ({
      rowId,
      fieldsByName: {
        Amount: { value: amount, widgetType: 'Amount' },
      },
    });

    const stringRow = (rowId, name) => ({
      rowId,
      fieldsByName: {
        Name: { value: name, widgetType: 'Text' },
      },
    });

    it('SORT_TAB is ignored when the table does not exist (no throw, state unchanged)', () => {
      const action = { type: ACTION_TYPES.SORT_TAB, scope: 'master', windowId, docId, tabId, field: 'Amount', asc: true };
      expect(() => reducer(initialState, action)).not.toThrow();
      expect(reducer(initialState, action)).toEqual(initialState);
    });

    it('SORT_TAB is ignored when scope is not "master"', () => {
      const action = { type: ACTION_TYPES.SORT_TAB, scope: 'included', windowId, docId, tabId, field: 'Amount', asc: false };
      expect(reducer(initialState, action)).toEqual(initialState);
    });

    it('SORT_TAB writes orderBys onto the target tab when the table exists', () => {
      const state = createState({
        [tableId]: { ...initialTableState, windowId, docId, tabId },
        length: 1,
      });
      const action = { type: ACTION_TYPES.SORT_TAB, scope: 'master', windowId, docId, tabId, field: 'Amount', asc: true };
      expect(reducer(state, action)[tableId].orderBys).toEqual([
        { fieldName: 'Amount', ascending: true },
      ]);
    });

    it('UPDATE_TAB_ROWS_DATA merges a new numeric row respecting the current ASC orderBys', () => {
      const state = createState({
        [tableId]: {
          ...initialTableState,
          windowId, docId, tabId,
          rows: [numericRow('r1', 5), numericRow('r3', 20)],
          orderBys: [{ fieldName: 'Amount', ascending: true }],
        },
        length: 1,
      });
      const action = {
        type: ACTION_TYPES.UPDATE_TAB_ROWS_DATA,
        payload: {
          id: tableId,
          rows: { changed: { r2: numericRow('r2', 10) }, removed: undefined },
        },
      };
      const rowsAfter = reducer(state, action)[tableId].rows.map((r) => r.rowId);
      expect(rowsAfter).toEqual(['r1', 'r2', 'r3']);
    });

    it('UPDATE_TAB_ROWS_DATA merges a new string row respecting DESC orderBys', () => {
      const state = createState({
        [tableId]: {
          ...initialTableState,
          windowId, docId, tabId,
          rows: [stringRow('r1', 'Zeta'), stringRow('r3', 'Alpha')],
          orderBys: [{ fieldName: 'Name', ascending: false }],
        },
        length: 1,
      });
      const action = {
        type: ACTION_TYPES.UPDATE_TAB_ROWS_DATA,
        payload: {
          id: tableId,
          rows: { changed: { r2: stringRow('r2', 'Mu') }, removed: undefined },
        },
      };
      const rowsAfter = reducer(state, action)[tableId].rows.map((r) => r.rowId);
      expect(rowsAfter).toEqual(['r1', 'r2', 'r3']);
    });

    it('UPDATE_TAB_ROWS_DATA in-place replacement preserves the numeric sort order for a value change', () => {
      const state = createState({
        [tableId]: {
          ...initialTableState,
          windowId, docId, tabId,
          rows: [numericRow('r1', 5), numericRow('r2', 10), numericRow('r3', 20)],
          orderBys: [{ fieldName: 'Amount', ascending: true }],
        },
        length: 1,
      });
      // r1's Amount changes from 5 to 15 → new order should be r2 (10), r1 (15), r3 (20)
      const action = {
        type: ACTION_TYPES.UPDATE_TAB_ROWS_DATA,
        payload: {
          id: tableId,
          rows: { changed: { r1: numericRow('r1', 15) }, removed: undefined },
        },
      };
      const rowsAfter = reducer(state, action)[tableId].rows.map((r) => r.rowId);
      expect(rowsAfter).toEqual(['r2', 'r1', 'r3']);
    });
  });
});
