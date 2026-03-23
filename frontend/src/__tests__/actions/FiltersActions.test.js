import thunk from 'redux-thunk';
import configureStore from 'redux-mock-store';

import {
  clearAllFilters,
  clearStaticFilters,
  createFilter,
  deleteFilter,
  updateActiveFilters,
  updateFilterWidgetShown,
  updateNotValidFields,
} from '../../actions/FiltersActions';

import * as ACTION_TYPES from '../../constants/ActionTypes';
import filtersData
  from '../../../test_setup/fixtures/filters/filtersActionsMock.json';
import filtersDataClearAll
  from '../../../test_setup/fixtures/filters/filtersDataClearAllMock.json';

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
    store.dispatch(
      createFilter({ filterId: '540092_540092-FF', data: filtersData })
    );
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
      clearAllFilters({ filterId: '143_143-E', data: filtersDataClearAll })
    );
    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
  });

  it(`dispatches 'UPDATE_FLAG_NOTVALIDFIELDS' action `, () => {
    const store = mockStore();
    store.dispatch(
      updateNotValidFields({ id: '540092_540092-FF', data: filtersData })
    );
    expect(store.getActions()[0].payload.filterId).toEqual(undefined);
    expect(store.getActions()[0].type).toEqual(
      ACTION_TYPES.UPDATE_FLAG_NOTVALIDFIELDS
    );
  });

  it(`dispatches 'UPDATE_ACTIVE_FILTERS' action when creating the filters`, () => {
    const expectedActionsOnCreation = [
      {
        type: ACTION_TYPES.CREATE_FILTER,
        payload: { id: '540092_540092-FF', data: filtersData },
      },
    ];

    const store = mockStore();
    store.dispatch(
      createFilter({ filterId: '540092_540092-FF', data: filtersData })
    );
    expect(store.getActions()).toEqual(
      expect.arrayContaining(expectedActionsOnCreation)
    );

    const activeFilter = [
      {
        filterId: 'default',
        parameters: [
          {
            parameterName: 'C_BPartner_ID',
            value: {
              key: '2156429',
              caption: '1000003_TestVendor',
              description: '1000003_TestVendor',
            },
            valueTo: '',
            defaultValue: null,
            defaultValueTo: null,
          },
        ],
      },
    ];
    store.dispatch(
      updateActiveFilters({ filterId: '540092_540092-FF', data: activeFilter })
    );
    expect(store.getActions()[1].type).toEqual(
      ACTION_TYPES.UPDATE_ACTIVE_FILTERS
    );
    expect(store.getActions()[1].payload).toEqual({
      id: '540092_540092-FF',
      data: activeFilter,
    });
  });

  it(`dispatches 'FILTER_UPDATE_WIDGET_SHOWN' action `, () => {
    const store = mockStore();
    store.dispatch(
      createFilter({ filterId: '540092_540092-FF', data: filtersData })
    );
    // after we have in the store the filter we will set the flag to either `false` or `true` via the updateFilterWidgetShown action

    Promise.all([
      store.dispatch(
        updateFilterWidgetShown({ filterId: '540092_540092-FF', data: true })
      ),
    ]).then(() => {
      expect(store.getActions()[1].payload).toEqual({
        id: '540092_540092-FF',
        data: true,
      });
    });

    Promise.all([
      store.dispatch(
        updateFilterWidgetShown({ filterId: '540092_540092-FF', data: false })
      ),
    ]).then(() => {
      expect(store.getActions()[2].payload).toEqual({
        id: '540092_540092-FF',
        data: false,
      });
    });
  });

  it(`dispatches 'CLEAR_STATIC_FILTERS' action `, () => {
    const store = mockStore();
    store.dispatch(
      createFilter({ filterId: '500221_500221-G', data: filtersData })
    );

    Promise.all([
      store.dispatch(
        clearStaticFilters({ filterId: '500221_500221-G', data: true })
      ),
    ]).then(() => {
      expect(store.getActions()[1].payload).toEqual({
        filterId: '500221_500221-G',
        data: true,
      });
    });
  });
});
