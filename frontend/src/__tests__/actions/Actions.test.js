import thunk from 'redux-thunk';
import configureStore from 'redux-mock-store';
import nock from 'nock';
import { merge } from 'merge-anything';

import actionsHandler, {
  getQuickActionsId,
  initialSingleActionsState,
} from '../../reducers/actionsHandler';
import { initialState as initialViewsState } from '../../reducers/viewHandler';
import tablesHandler, {
  getTableId,
  initialTableState,
} from '../../reducers/tables';
import {
  deleteQuickActions,
  fetchQuickActions,
  requestQuickActions,
} from '../../actions/Actions';
import { createTableData } from '../../actions/TableActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';

import gridDataFixtures from '../../../test_setup/fixtures/grid/data.json';
import gridLayoutFixtures from '../../../test_setup/fixtures/grid/layout.json';
import gridRowFixtures from '../../../test_setup/fixtures/grid/row_data.json';
import quickActionsFixtures
  from '../../../test_setup/fixtures/grid/quick_actions.json';

const middlewares = [thunk];
const mockStore = configureStore(middlewares);

const createState = function(state = {}) {
  return merge(
    {
      viewHandler: initialViewsState,
      actionsHandler: { ...actionsHandler(undefined, {}) },
      tables: { ...tablesHandler(undefined, {}) },
    },
    state
  );
};

describe('QuickActions', () => {
  beforeEach(() => {
    nock.cleanAll();
  });

  afterEach(() => {
    nock.cleanAll();
  });

  it(`call 'FETCH_QUICK_ACTIONS_SUCCESS' on fetching actions`, () => {
    const { windowId, viewId } = gridDataFixtures.data1;
    const { actions } = quickActionsFixtures;
    const state = createState();
    const store = mockStore(state);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/documentView/${windowId}/${viewId}/quickActions`)
      .reply(200, { actions });

    return store
      .dispatch(
        requestQuickActions({
          windowId,
          viewId,
        })
      )
      .then(() => {
        const id = getQuickActionsId({ windowId, viewId });
        expect(store.getActions()).toEqual([
          {
            type: ACTION_TYPES.FETCH_QUICK_ACTIONS,
            payload: { id }
          },
          {
            type: ACTION_TYPES.FETCH_QUICK_ACTIONS_SUCCESS,
            payload: { id, actions }
          },
        ]);
      });
  });

  it(`call 'FETCH_QUICK_ACTIONS_FAILURE' on failed actions fetching`, () => {
    const windowId = '143';
    const viewId = '143-F';
    const store = mockStore(createState());

    const id = getQuickActionsId({ windowId, viewId });

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/documentView/${windowId}/${viewId}/quickActions`)
      .reply(500, { some_error: 'bla bla' });

    return store
      .dispatch(requestQuickActions({ windowId, viewId }))
      .catch(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            { type: ACTION_TYPES.FETCH_QUICK_ACTIONS, payload: { id } },
            { type: ACTION_TYPES.FETCH_QUICK_ACTIONS_FAILURE, payload: { id } },
          ])
        );
      });
  });

  it(`call 'DELETE_QUICK_ACTIONS' with correct payload`, () => {
    const { windowId, viewId } = gridDataFixtures.data1;
    const id = getQuickActionsId({ windowId, viewId });
    const state = createState();
    const store = mockStore(state);

    store.dispatch(deleteQuickActions(windowId, viewId));

    expect(store.getActions()).toEqual(expect.arrayContaining([
      { type: ACTION_TYPES.DELETE_QUICK_ACTIONS, payload: { id } },
    ]));
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

    store.dispatch(requestQuickActions({ windowId, viewId }));
    expect(store.getActions()).toEqual([]);
  });

  it(`fetch included parent's quick actions`, () => {
    const parentLayoutResponse = gridLayoutFixtures.layout2_parent;
    const childLayoutResponse = gridLayoutFixtures.layout2_child;
    const parentRowResponse = gridRowFixtures.data3_parent;
    const childRowResponse = gridRowFixtures.data3_child;
    const { actions2: actions } = quickActionsFixtures;
    const { windowId, viewId, parentWindowId, parentViewId } = childRowResponse;
    const id = getQuickActionsId({
      windowId: parentWindowId,
      viewId: parentViewId
    });
    const store = mockStore(createState({
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
        [getTableId({ windowId, viewId })]: {
          ...initialTableState,
          ...(createTableData({
            ...childLayoutResponse,
            ...childRowResponse,
            keyProperty: 'id',
          })),
          selected: [childRowResponse.result[0].id],
        },
        [getTableId({
          windowId: parentWindowId,
          viewId: parentViewId,
        })]: {
          ...initialTableState,
          ...(createTableData({
            ...parentLayoutResponse,
            ...parentRowResponse,
            keyProperty: 'id',
          })),
          selected: [parentRowResponse.result[0].id],
        },
      },
      actionsHandler: {
        [id]: initialSingleActionsState,
        // child actions already requested
        [getQuickActionsId({ windowId, viewId })]: {
          ...initialSingleActionsState,
          pending: true,
        },
      },
    }));

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/documentView/${parentWindowId}/${parentViewId}/quickActions`)
      .reply(200, { actions });
    //console.log('ACTIVE MOCKS: ', nock.activeMocks());

    return store
      .dispatch(fetchQuickActions({ windowId, viewId, isModal: true }))
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            { type: ACTION_TYPES.FETCH_QUICK_ACTIONS, payload: { id } },
            {
              type: ACTION_TYPES.FETCH_QUICK_ACTIONS_SUCCESS,
              payload: { id, actions }
            },
          ])
        );

      });
  });

  it(`fetch included parent and child quick actions`, () => {
    const parentLayoutResponse = gridLayoutFixtures.layout2_parent;
    const childLayoutResponse = gridLayoutFixtures.layout2_child;
    const parentRowResponse = gridRowFixtures.data3_parent;
    const childRowResponse = gridRowFixtures.data3_child;
    const { actions2: actions } = quickActionsFixtures;
    const { windowId, viewId, parentWindowId, parentViewId } = childRowResponse;
    const parentRowId = parentRowResponse.result[0].id;
    const childRowId = childRowResponse.result[0].id;
    const id = getQuickActionsId({
      windowId: parentWindowId,
      viewId: parentViewId
    });
    const childId = getQuickActionsId({ windowId, viewId });
    const store = mockStore(createState({
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
        [getTableId({ windowId, viewId })]: {
          ...initialTableState,
          ...(createTableData({
            ...childLayoutResponse,
            ...childRowResponse,
            keyProperty: 'id',
          })),
          selected: [childRowId],
        },
        [getTableId({
          windowId: parentWindowId,
          viewId: parentViewId,
        })]: {
          ...initialTableState,
          ...(createTableData({
            ...parentLayoutResponse,
            ...parentRowResponse,
            keyProperty: 'id',
          })),
          selected: [parentRowId],
        },
      },
      actionsHandler: {
        [id]: initialSingleActionsState,
        [childId]: initialSingleActionsState,
      },
    }));

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/documentView/${windowId}/${viewId}/quickActions`)
      .reply(200, { actions });
    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/documentView/${parentWindowId}/${parentViewId}/quickActions`)
      .reply(200, { actions: [] });

    return store
      .dispatch(
        fetchQuickActions({
          windowId,
          viewId,
          isModal: true,
        })
      )
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            { type: ACTION_TYPES.FETCH_QUICK_ACTIONS, payload: { id } },
            {
              type: ACTION_TYPES.FETCH_QUICK_ACTIONS,
              payload: { id: childId }
            },
            {
              type: ACTION_TYPES.FETCH_QUICK_ACTIONS_SUCCESS,
              payload: { id, actions: [] }
            },
            {
              type: ACTION_TYPES.FETCH_QUICK_ACTIONS_SUCCESS,
              payload: { id: childId, actions }
            },
          ])
        );

      });
  });

  it(`fetch underlying view's actions when modal with included view is visible`, () => {
    const layoutResponse = gridLayoutFixtures.layout3_payments;
    const rowResponse = gridRowFixtures.data4_payments;
    const { windowId, viewId, result } = rowResponse;
    const {
      includedViewId,
      includedWindowId,
      includedParentWindowId,
    } = gridDataFixtures.data3_payments;
    const id = getQuickActionsId({ windowId, viewId });
    const tableId = getTableId({ windowId, viewId });
    const selectedId = result[0].id;

    const initialStateData = createState({
      viewHandler: {
        includedView: {
          viewId: includedViewId,
          windowId: includedWindowId,
          parentId: includedParentWindowId,
        },
      },
      tables: {
        length: 1,
        [tableId]: {
          ...initialTableState,
          ...(createTableData({
            ...layoutResponse,
            ...rowResponse,
            keyProperty: 'id',
          })),
          selected: [selectedId],
        },
      },
      actionsHandler: {
        [id]: initialSingleActionsState,
      },
    });
    const store = mockStore(initialStateData);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/documentView/${windowId}/${viewId}/quickActions`)
      .reply(200, { actions: [] });

    return store
      .dispatch(fetchQuickActions({ windowId, viewId }))
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            { type: ACTION_TYPES.FETCH_QUICK_ACTIONS, payload: { id } },
            {
              type: ACTION_TYPES.FETCH_QUICK_ACTIONS_SUCCESS,
              payload: { id, actions: [] }
            },
          ])
        );

      });
  });
});
