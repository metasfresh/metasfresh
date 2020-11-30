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
import viewHandler, { initialState as initialViewsState } from '../../reducers/viewHandler';
import { getView } from '../../reducers/viewHandler';

import {
  fetchQuickActions,
  fetchIncludedQuickActions,
  deleteQuickActions
} from '../../actions/Actions';
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
    },
    state
  );

  return res;
};

describe('QuickActions', () => {
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
        fetchQuickActions({
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
        fetchQuickActions({
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

    store.dispatch(fetchQuickActions({ windowId, viewId }));
    expect(store.getActions()).toEqual([]);
  }); 

  it(`fetch included parent's quick actions`, () => {
    const parentLayoutResponse = gridLayoutFixtures.layout2_parent;
    const childLayoutResponse = gridLayoutFixtures.layout2_child;
    const parentRowResponse = gridRowFixtures.data3_parent;
    const childRowResponse = gridRowFixtures.data3_child;
    const actionsResponse = quickActionsFixtures.actions2;
    const { windowId, viewId, parentWindowId, parentViewId } = childRowResponse;
    const parentRowId = parentRowResponse.result[0].id;
    const childRowId = childRowResponse.result[0].id;
    const nextChildRowId = childRowResponse.result[1].id;
    const id = getQuickActionsId({ windowId: parentWindowId, viewId: parentViewId });
    const childId = getQuickActionsId({ windowId, viewId });
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
      actionsHandler: {
        [id]: initialSingleActionsState,
        // child actions alerady requested
        [childId]: {
          ...initialSingleActionsState,
          pending: true,
        }
      },
    });
    const store = mockStore(initialStateData);

    const payload1 = { id };
    const payload2 = { id, actions: [] };
    const expectedActions = [
      { type: ACTION_TYPES.FETCH_QUICK_ACTIONS, payload: payload1 },
      { type: ACTION_TYPES.FETCH_QUICK_ACTIONS_SUCCESS, payload: payload2 },
    ];

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/documentView/${parentWindowId}/${parentViewId}/quickActions?childViewId=${
        viewId}&childViewSelectedIds=${nextChildRowId}`)
      .reply(200, { actions: [] });

    return store
      .dispatch(
        fetchIncludedQuickActions({
          windowId,
          selectedIds: [nextChildRowId],
          isModal: true,
        })
      ).then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining(expectedActions)
        );     
      });
  });
});