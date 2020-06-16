import merge from 'merge';

import { deleteTable, updateTableSelection } from '../../actions/TableActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';
import reducer, {
  initialState,
  initialTableState,
} from '../../reducers/tables';

const createState = function(state = {}) {
  return merge.recursive(
    true,
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

    const actions = [deleteTable(id)];
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
          collapsedArrayMap: [],
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
});
