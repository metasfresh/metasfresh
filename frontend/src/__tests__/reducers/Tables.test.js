import produce from 'immer';
import merge from 'merge';

import { deleteTable } from '../../actions/TableActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';
import reducer, { initialState, tableState } from '../../reducers/tables';

const createState = function(state = {}) {
  return merge.recursive(
    true,
    {
      ...initialState,
    },
    state
  );
}

const basicData = {
  windowType: '143',
  docId: '1000037',
  tabId: 'AD_Tab-187',
  allowCreateNew: true,
  allowDelete: true,
  stale: true,
}

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
        }
      })
    ).toEqual(expect.objectContaining({
      [id]: expect.objectContaining({ ...basicData, queryLimit: 0 }),
      length: 1,
    }));
  });

  it('Should handle UPDATE_TABLE', () => {
    const id = '143_1000037_AD_Tab-187';
    const initialStateData = createState(
      { [id]: { ...tableState, ...basicData }, length: 1  }
    );
    const updateData = {
      internalName: 'C_OrderLine',
      elements: [
        {
          caption: 'Not avail. on short term',
          fields: [
            {
              field: 'InsufficientQtyAvailableForSalesColor_ID',
            }
          ]
        }
      ],
    }
    const updateAction = {
      type: ACTION_TYPES.UPDATE_TABLE,
      payload: {
        id,
        data: updateData,
      }
    };

    const actions = [updateAction];
    const state = actions.reduce(reducer, initialStateData);

    expect(state).toEqual(expect.objectContaining(
      { [id]: expect.objectContaining({
          rows: [],
        }),
        length: 1,
      }
    ));
  });

  it('Should handle DELETE_TABLE', () => {
    const id = '143_1000037_AD_Tab-187';
    const initialStateData = createState(
      { [id]: { ...tableState, ...basicData }, length: 1  }
    );

    const actions = [deleteTable(id)];
    const state = actions.reduce(reducer, initialStateData);

    expect(state).toEqual({ length: 0 })
  });

  it('Should handle SET_ACTIVE_SORT', () => {
    const id = '143_1000037_AD_Tab-187';
    const initialStateData = createState(
      { [id]: { ...tableState, ...basicData }, length: 1  }
    );

    const updateAction = {
      type: ACTION_TYPES.SET_ACTIVE_SORT_NEW,
      payload: {
        id,
        active: true,
      }
    };

    const actions = [updateAction];
    const state = actions.reduce(reducer, initialStateData);

    expect(state).toEqual(expect.objectContaining(
      { [id]: expect.objectContaining({
          activeSort: true,
        }),
        length: 1,
      }
    ));
  });
});
