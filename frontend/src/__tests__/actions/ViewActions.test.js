import thunk from 'redux-thunk'
import nock from 'nock';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import _ from 'lodash';

import viewHandler, { viewState, initialState } from '../../reducers/viewHandler';
import { getTableId } from '../../reducers/tables';
import * as viewActions from '../../actions/ViewActions';
import { createTableData } from '../../actions/TableActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';

import gridDataFixtures from '../../../test_setup/fixtures/grid/data.json';
import gridLayoutFixtures from '../../../test_setup/fixtures/grid/layout.json';
import gridRowFixtures from '../../../test_setup/fixtures/grid/row_data.json';

import fixtures from '../../../test_setup/fixtures/grid/reducers.json';

const middlewares = [thunk];
const mockStore = configureStore(middlewares);

const createStore = function(state = {}) {
  const res = merge.recursive(
    true,
    {
      viewHandler: initialState,
    },
    state
  );

  return res;
}

describe('ViewActions synchronous', () => {
  const viewLayout = fixtures.viewLayout1;
  const viewData = fixtures.basicViewData1;

  it('should call DELETE_VIEW action with correct payload', () => {
    const id = viewLayout.windowId;
    const action = viewActions.deleteView(id, false);

    expect(action.type).toEqual(ACTION_TYPES.DELETE_VIEW);
    expect(action.payload).toHaveProperty('id', id);
  });

  it('should call RESET_VIEW action with correct payload', () => {
    const id = viewLayout.windowId;
    const action = viewActions.resetView(id, false);

    expect(action.type).toEqual(ACTION_TYPES.RESET_VIEW);
    expect(action.payload).toHaveProperty('id', id);
    expect(action.payload).toHaveProperty('isModal', false);
  });

  it('should call TOGGLE_INCLUDED_VIEW action with correct payload', () => {
    const id = viewLayout.windowId;
    const show = true;
    const action = viewActions.toggleIncludedView(id, show, false);

    expect(action.type).toEqual(ACTION_TYPES.TOGGLE_INCLUDED_VIEW);
    expect(action.payload).toHaveProperty('id', id);
    expect(action.payload).toHaveProperty('showIncludedView', show);
  });
});

describe('ViewActions thunks', () => {
  const limitedViewLayout = fixtures.viewLayout1;
  const limitedViewData = fixtures.basicViewData1;
  const limitedCreateViewData = _.omit(
    limitedViewData,
    ['columnsByFieldName', 'result', 'firstRow', 'pageLength', 'headerProperties']
  );

  it(`dispatches 'FETCH_LAYOUT_PENDING/SUCCESS' when fetching layout data`, () => {
    const { windowId } = limitedViewLayout;
    const state = createStore();
    const store = mockStore(state); 
    const payload1 = {
      id: windowId,
      isModal: false,
    };
    const payload2 = {
      id: windowId,
      layout: limitedViewLayout,
      isModal: false,
    };
    const expectedActions = [
      { type: ACTION_TYPES.FETCH_LAYOUT_PENDING, payload: payload1 },
      { type: ACTION_TYPES.FETCH_LAYOUT_SUCCESS, payload: payload2 },
    ];

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/documentView/${windowId}/layout?viewType=grid`)
      .reply(200, limitedViewLayout);

    return store.dispatch(viewActions.fetchLayout(windowId, 'grid')).then(() => {
      expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
    });
  });

  it(`dispatches 'CREATE_VIEW*' and 'CREATE_TABLE' actions when fetching view data`, () => {
    const { windowId, viewId } = limitedCreateViewData;
    const tableData = createTableData({
      ...limitedCreateViewData,
      ...limitedViewLayout,
    });
    const tableId = getTableId({ windowId, viewId });
    const state = createStore({
      viewHandler: {
        views: {
          [windowId]: {
            layout: { ...limitedViewLayout },
          },
        },
      },
    });
    const store = mockStore(state);
    const payload1 = {
      id: windowId,
      isModal: false,
    };
    const payload2 = {
      id: windowId,
      viewId,
      isModal: false,
    };
    const payload3 = {
      id: tableId,
      // we have to remove `size` as in the real flow it's not present in the layout
      data: _.omit(tableData, 'size'),
    }
    const expectedActions = [
      { type: ACTION_TYPES.CREATE_VIEW, payload: payload1 },
      { type: ACTION_TYPES.CREATE_VIEW_SUCCESS, payload: payload2 },
      { type: ACTION_TYPES.CREATE_TABLE, payload: payload3 },
    ];

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/documentView/${windowId}`)
      .reply(200, limitedCreateViewData);

    return store
      .dispatch(viewActions.createView({ windowId, viewType: 'grid', filters: [], isModal: false }))
      .then(() => {
        expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
      });
  });
});
