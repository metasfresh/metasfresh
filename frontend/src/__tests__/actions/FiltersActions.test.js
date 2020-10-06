import thunk from 'redux-thunk';
import configureStore from 'redux-mock-store';
import produce from 'immer';
import merge from 'merge';

import tablesHandler, { tableState, getTableId } from '../../reducers/tables';
import viewHandler from '../../reducers/viewHandler';

import {
  clearAllFilters,
  createFilter,
  deleteFilter,
  updateNotValidFields,
  updateActiveFilter,
  updateInlineFilter,
  updateFilterWidgetShown,
  clearStaticFilters,
} from '../../actions/FiltersActions';
import * as ACTION_TYPES from '../../constants/FilterTypes';
import filtersData from '../../../test_setup/fixtures/filters/filtersActionsMock.json';
import filtersDataClearAll from '../../../test_setup/fixtures/filters/filtersDataClearAllMock.json';

const middlewares = [thunk];
const mockStore = configureStore(middlewares);

describe('FiltersActions general', () => {
  it('should call DELETE_FILTER action with correct payload', () => {
    const payload = { id: '143_143-Q' };
    const action = deleteFilter(payload.id);

    expect(action.type).toEqual(ACTION_TYPES.DELETE_FILTER);
    expect(action.payload).toHaveProperty('id', payload.id);
  });

  it(`dispatches 'CREATE_FILTER' action when creating the filters`, () => {
    const expectedActions = [
      {
        type: ACTION_TYPES.CREATE_FILTER,
        payload: { id: '540092_540092-FF', data: filtersData },
      },
    ];

    const store = mockStore();
    store.dispatch(createFilter({ id: '540092_540092-FF', data: filtersData }));
    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
  });

  it(`dispatches 'CLEAR_ALL_FILTERS' action when clearing the filters`, () => {
    const expectedActions = [
      {
        type: ACTION_TYPES.CLEAR_ALL_FILTERS,
        payload: { id: '143_143-E', data: filtersDataClearAll },
      },
    ];

    const store = mockStore();
    store.dispatch(
      clearAllFilters({ id: '143_143-E', data: filtersDataClearAll })
    );
    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
  });
});
