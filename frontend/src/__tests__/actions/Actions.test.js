import thunk from 'redux-thunk';
import configureStore from 'redux-mock-store';
import nock from 'nock';
import merge from 'merge';
import { combineReducers } from 'redux';

import actionsHandler, {
  initialState as initialActionsState,
  initialSingleActionsState,
  getQuickActionsId,
  getQuickActions
} from '../../reducers/actionsHandler';
import viewHandler, { getView, initialState as initialViewsState } from '../../reducers/viewHandler';
import tablesHandler, { initialTableState, getTableId } from '../../reducers/tables';

import {
  requestQuickActions,
  fetchQuickActions,
  deleteQuickActions
} from '../../actions/Actions';
import { createTableData } from '../../actions/TableActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';

import gridDataFixtures from '../../../test_setup/fixtures/grid/data.json';
import gridLayoutFixtures from '../../../test_setup/fixtures/grid/layout.json';
import gridRowFixtures from '../../../test_setup/fixtures/grid/row_data.json';
import quickActionsFixtures from '../../../test_setup/fixtures/grid/quick_actions.json';

const middlewares = [thunk];
const mockStore = configureStore(middlewares);

const createState = function(state = {}) {
  const res = merge.recursive(
    true,
    {
      viewHandler: initialViewsState,
      actionsHandler: { ...actionsHandler(undefined, {}) },
      tables: { ...tablesHandler(undefined, {}) },
    },
    state
  );

  return res;
};

describe('QuickActions', () => {
  afterAll(() => {
    nock.cleanAll();
  });

  it(`call 'FETCH_QUICK_ACTIONS_SUCCESS' on fetching actions`, () => {
    const { windowId, viewId } = gridDataFixtures.data1;
    const { actions } = quickActionsFixtures;
    const state = createState();
    const store = mockStore(state);

    const id = getQuickActionsId({ windowId, viewId });
    const payload1 = { id };
    const payload2 = { id, actions };
    const expectedActions = [
      { type: ACTION_TYPES.FETCH_QUICK_ACTIONS, payload: payload1 },
      { type: ACTION_TYPES.FETCH_QUICK_ACTIONS_SUCCESS, payload: payload2 },
    ];

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/documentView/${windowId}/${viewId}/quickActions`)
      .reply(200, { actions });

    return store
      .dispatch(
        requestQuickActions({
          windowId,
          viewId,
        })
      )
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining(expectedActions)
        );
      }); 
  });

  it(`call 'FETCH_QUICK_ACTIONS_FAILURE' on failed actions fetching`, () => {
    const { windowId, viewId } = gridDataFixtures.data1;
    const { actions } = quickActionsFixtures;
    const state = createState();
    const store = mockStore(state);

    const id = getQuickActionsId({ windowId, viewId });
    const payload = { id };
    const expectedActions = [
      { type: ACTION_TYPES.FETCH_QUICK_ACTIONS, payload },
      { type: ACTION_TYPES.FETCH_QUICK_ACTIONS_FAILURE, payload },
    ];

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/documentView/${windowId}/${viewId}/quickActions`)
      .reply(404);

    return store
      .dispatch(
        requestQuickActions({
          windowId,
          viewId,
        })
      )
      .catch(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining(expectedActions)
        );  
      });
  });

  it(`call 'DELETE_QUICK_ACTIONS' with correct payload`, () => {
    const { windowId, viewId } = gridDataFixtures.data1;
    const id = getQuickActionsId({ windowId, viewId });
    const state = createState();
    const store = mockStore(state);

    const expectedActions = [
      { type: ACTION_TYPES.DELETE_QUICK_ACTIONS, payload: { id} },
    ];
    store.dispatch(deleteQuickActions(windowId, viewId))

    expect(store.getActions()).toEqual(
      expect.arrayContaining(expectedActions)
    );
  });

  it(`not fetch quick actions when there's already a pending request`, () => {
    const { windowId, viewId } = gridDataFixtures.data1;
    const id = getQuickActionsId({ windowId, viewId });
    const { actions } = quickActionsFixtures;
    const state = createState({
      actionsHandler: {
        [id]: { actions, pending: true },
      },
    });
    const store = mockStore(state);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/documentView/${windowId}/${viewId}/quickActions`)
      .reply(200, { actions: [] });

    store.dispatch(requestQuickActions({ windowId, viewId }));
    expect(store.getActions()).toEqual([]);
  }); 

  it(`fetch included parent's quick actions`, async (done) => {
    const parentLayoutResponse = gridLayoutFixtures.layout2_parent;
    const childLayoutResponse = gridLayoutFixtures.layout2_child;
    const parentRowResponse = gridRowFixtures.data3_parent;
    const childRowResponse = gridRowFixtures.data3_child;
    const { actions2: actions } = quickActionsFixtures;
    const { windowId, viewId, parentWindowId, parentViewId } = childRowResponse;
    const parentRowId = parentRowResponse.result[0].id;
    const childRowId = childRowResponse.result[0].id;
    const id = getQuickActionsId({ windowId: parentWindowId, viewId: parentViewId });
    const childId = getQuickActionsId({ windowId, viewId });
    const parentTableId = getTableId({ windowId: parentWindowId, viewId: parentViewId });
    const childTableId = getTableId({ windowId, viewId });
    const c_tableData_create = createTableData({
      ...childLayoutResponse,
      ...childRowResponse,
      keyProperty: 'id',
    });
    const p_tableData_create = createTableData({
      ...parentLayoutResponse,
      ...parentRowResponse,
      keyProperty: 'id',
    });

    const initialStateData = createState({
      viewHandler: {
        modals: {
          [windowId]: {
            layout: { ...childLayoutResponse },
            windowId,
            viewId,
          },
          [parentWindowId]: {
            layout: { ...parentLayoutResponse },
            viewId: parentViewId,
            windowId: parentWindowId,
          },
        },
        includedView: {
          viewId,
          windowId,
          parentId: parentWindowId,
        },
      },
      tables: {
        length: 2,
        [childTableId]: {
          ...initialTableState,
          ...c_tableData_create,
          selected: [childRowId],
        },
        [parentTableId]: {
          ...initialTableState,
          ...p_tableData_create,
          selected: [parentRowId],
        },
      },
      actionsHandler: {
        [id]: initialSingleActionsState,
        // child actions already requested
        [childId]: {
          ...initialSingleActionsState,
          pending: true,
        }
      },
    });
    const store = mockStore(initialStateData);

    const payload1 = { id };
    const payload2 = { id, actions };
    const expectedActions = [
      { type: ACTION_TYPES.FETCH_QUICK_ACTIONS, payload: payload1 },
      { type: ACTION_TYPES.FETCH_QUICK_ACTIONS_SUCCESS, payload: payload2 },
    ];

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/documentView/${parentWindowId}/${parentViewId}/quickActions?childViewId=${
        viewId}&childViewSelectedIds=${childRowId}&selectedIds=${parentRowId}`)
      .reply(200, { actions });

    store
      .dispatch(
        fetchQuickActions({
          windowId,
          viewId,
          isModal: true,
        })
      ).then((res) => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining(expectedActions)
        );

        done();  
      });
  });

  it(`fetch included parent and child quick actions`, async (done) => {
    const parentLayoutResponse = gridLayoutFixtures.layout2_parent;
    const childLayoutResponse = gridLayoutFixtures.layout2_child;
    const parentRowResponse = gridRowFixtures.data3_parent;
    const childRowResponse = gridRowFixtures.data3_child;
    const { actions2: actions } = quickActionsFixtures;
    const { windowId, viewId, parentWindowId, parentViewId } = childRowResponse;
    const parentRowId = parentRowResponse.result[0].id;
    const childRowId = childRowResponse.result[0].id;
    const id = getQuickActionsId({ windowId: parentWindowId, viewId: parentViewId });
    const childId = getQuickActionsId({ windowId, viewId });
    const parentTableId = getTableId({ windowId: parentWindowId, viewId: parentViewId });
    const childTableId = getTableId({ windowId, viewId });
    const c_tableData_create = createTableData({
      ...childLayoutResponse,
      ...childRowResponse,
      keyProperty: 'id',
    });
    const p_tableData_create = createTableData({
      ...parentLayoutResponse,
      ...parentRowResponse,
      keyProperty: 'id',
    });

    const initialStateData = createState({
      viewHandler: {
        modals: {
          [windowId]: {
            layout: { ...childLayoutResponse },
            windowId,
            viewId,
          },
          [parentWindowId]: {
            layout: { ...parentLayoutResponse },
            viewId: parentViewId,
            windowId: parentWindowId,
          },
        },
        includedView: {
          viewId,
          windowId,
          parentId: parentWindowId,
        },
      },
      tables: {
        length: 2,
        [childTableId]: {
          ...initialTableState,
          ...c_tableData_create,
          selected: [childRowId],
        },
        [parentTableId]: {
          ...initialTableState,
          ...p_tableData_create,
          selected: [parentRowId],
        },
      },
      actionsHandler: {
        [id]: initialSingleActionsState,
        [childId]: initialSingleActionsState,
      },
    });
    const store = mockStore(initialStateData);

    const payload1 = { id }; 
    const payload2 = { id: childId };
    const payload3 = { id, actions: [] };
    const payload4 = { id: childId, actions };
    const expectedActions = [
      { type: ACTION_TYPES.FETCH_QUICK_ACTIONS, payload: payload1 },
      { type: ACTION_TYPES.FETCH_QUICK_ACTIONS, payload: payload2 },
      { type: ACTION_TYPES.FETCH_QUICK_ACTIONS_SUCCESS, payload: payload3 },
      { type: ACTION_TYPES.FETCH_QUICK_ACTIONS_SUCCESS, payload: payload4 },
    ];

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/documentView/${parentWindowId}/${parentViewId}/quickActions?childViewId=${
        viewId}&childViewSelectedIds=${childRowId}&selectedIds=${parentRowId}`)
      .reply(200, { actions: [] });

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/documentView/${windowId}/${viewId}/quickActions?parentViewId=${
        parentViewId}&parentViewSelectedIds=${parentRowId}&selectedIds=${childRowId}`)
      .reply(200, { actions });

    store
      .dispatch(
        fetchQuickActions({
          windowId,
          viewId,
          isModal: true,
        })
      ).then((res) => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining(expectedActions)
        );

        done();  
      });
  });
});