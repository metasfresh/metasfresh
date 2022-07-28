import thunk from 'redux-thunk';
import nock from 'nock';
import configureStore from 'redux-mock-store';
import { merge } from 'merge-anything';
import _ from 'lodash';

import { initialState, viewState, } from '../../reducers/viewHandler';
import tablesHandler, { getTableId } from '../../reducers/tables';
import windowState from '../../reducers/windowHandler';
import { getEntityRelatedId } from '../../reducers/filters';

import * as viewActions from '../../actions/ViewActions';
import { createTableData } from '../../actions/TableActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';
import { flattenRows } from '../../utils/documentListHelper';
import {
  formatFilters,
  populateFiltersCaptions,
} from '../../utils/filterHelpers';

import gridLayoutFixtures from '../../../test_setup/fixtures/grid/layout.json';
import gridRowFixtures from '../../../test_setup/fixtures/grid/row_data.json';
import fixtures from '../../../test_setup/fixtures/grid/reducers.json';
import generalData from '../../../test_setup/fixtures/grid/data.json';

const middlewares = [thunk];
const mockStore = configureStore(middlewares);

const createStore = function(state = {}) {
  return merge(
    {
      viewHandler: initialState,
      tables: { ...tablesHandler(undefined, {}) },
      windowHandler: windowState,
    },
    state
  );
};

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

  it('should call SET_INCLUDED_VIEW action with correct payload', () => {
    const id = viewLayout.windowId;
    const viewId = viewData.viewId;
    const action = viewActions.setIncludedView({ windowId: id, viewId });

    expect(action.type).toEqual(ACTION_TYPES.SET_INCLUDED_VIEW);
    expect(action.payload).toHaveProperty('id', id);
    expect(action.payload).toHaveProperty('viewId', viewId);
  });

  it('should call UNSET_INCLUDED_VIEW action with correct payload', () => {
    const id = viewLayout.windowId;
    const viewId = viewData.viewId;
    const action = viewActions.unsetIncludedView({ windowId: id, viewId });

    expect(action.type).toEqual(ACTION_TYPES.UNSET_INCLUDED_VIEW);
    expect(action.payload).toHaveProperty('id', id);
    expect(action.payload).toHaveProperty('viewId', viewId);
  });
});

describe('ViewActions thunks', () => {
  const limitedViewLayout = fixtures.viewLayout1;
  const limitedViewData = fixtures.basicViewData1;
  const limitedCreateViewData = _.omit(limitedViewData, [
    'columnsByFieldName',
    'result',
    'firstRow',
    'pageLength',
    'headerProperties',
  ]);
  const limitedModalLayout = fixtures.modalLayout1;
  const limitedModalData = fixtures.basicModalData1;

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
    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/documentView/${windowId}/layout?viewType=grid`)
      .reply(200, limitedViewLayout);

    return store
      .dispatch(viewActions.fetchLayout(windowId, 'grid'))
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            { type: ACTION_TYPES.FETCH_LAYOUT_PENDING, payload: payload1 },
            { type: ACTION_TYPES.FETCH_LAYOUT_SUCCESS, payload: payload2 },
          ])
        );
      });
  });

  it(`dispatches 'CREATE_VIEW*' and 'CREATE_TABLE' actions when fetching view data`, () => {
    const { windowId, viewId } = limitedCreateViewData;
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
    const actionData = _.omit(
      createTableData({ ...limitedCreateViewData, ...limitedViewLayout }),
      'size'
    );
    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/documentView/${windowId}`)
      .reply(200, limitedCreateViewData);

    return store
      .dispatch(
        viewActions.createView({
          windowId,
          viewType: 'grid',
          filters: [],
          isModal: false,
        })
      )
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            { type: ACTION_TYPES.CREATE_VIEW, payload: {
                id: windowId,
                isModal: false,
              } },
            { type: ACTION_TYPES.CREATE_VIEW_SUCCESS, payload: {
                id: windowId,
                viewId,
                isModal: false,
              } },
            { type: ACTION_TYPES.CREATE_TABLE, payload: {
                id: tableId,
                // we have to remove `size` as in the real flow it's not present in the layout
                data: actionData,
              } },
          ])
        );
      });
  });

  it(`dispatches 'FETCH_DOCUMENT_*' and 'CREATE_TABLE' actions when fetching modal's rows data`, () => {
    const { windowId, viewId, pageLength } = limitedModalData;
    const page = 1;
    const tableId = getTableId({ windowId, viewId });
    const tableData = createTableData({
      ...limitedModalData,
      ...limitedModalLayout,
      keyProperty: 'id',
    });
    tableData.rows = flattenRows(tableData.rows);

    const state = createStore({
      viewHandler: {
        modals: {
          [windowId]: {
            layout: { ...limitedModalLayout },
          },
        },
      },
      rawModal: {
        visible: true,
        viewId,
        windowId,
      },
    });
    const store = mockStore(state);
    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(
        `/documentView/${windowId}/${viewId}?firstRow=${pageLength *
          (page - 1)}&pageLength=${pageLength}`
      )
      .reply(200, limitedModalData);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/documentView/${windowId}/${viewId}/quickActions`)
      .reply(200, { data: { actions: [] } });

    return store
      .dispatch(
        viewActions.fetchDocument({
          windowId,
          viewId,
          pageLength,
          page,
          isModal: true,
        })
      )
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            { type: ACTION_TYPES.FETCH_DOCUMENT_PENDING, payload: {
                id: windowId,
                isModal: true,
                websocketRefresh: false,
              } },
            { type: ACTION_TYPES.FETCH_DOCUMENT_SUCCESS, payload: {
                id: windowId,
                data: limitedModalData,
                isModal: true,
              } },
            { type: ACTION_TYPES.CREATE_TABLE, payload: {
                id: tableId,
                data: tableData,
              } },
          ])
        );
      });
  });

  it(`dispatches 'FETCH_DOCUMENT_*' and 'UPDATE_TABLE' actions when fetching view rows data`, () => {
    const {
      windowId,
      viewId,
      pageLength,
    } = limitedViewData;
    const tableId = getTableId({ windowId, viewId });
    const page = 1;
    const tableData = createTableData({
      ..._.pick(limitedViewData, [
        'windowId',
        'viewId',
        'size',
        'headerProperties',
        'result',
        'firstRow',
      ]),
      ...limitedViewLayout,
      headerElements: limitedViewData.columnsByFieldName,
      keyProperty: 'id',
    });
    const state = createStore({
      viewHandler: {
        views: {
          [windowId]: {
            layout: { ...limitedViewLayout },
            ...limitedCreateViewData,
          },
        },
      },
      tables: {
        [tableId]: createTableData({
          ...limitedCreateViewData,
          ...limitedViewLayout,
        }),
      },
    });
    const store = mockStore(state);
    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(
        `/documentView/${windowId}/${viewId}?firstRow=${pageLength *
          (page - 1)}&pageLength=${pageLength}`
      )
      .reply(200, limitedViewData);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/documentView/${windowId}/${viewId}/quickActions`)
      .reply(200, { data: { actions: [] } });

    return store
      .dispatch(
        viewActions.fetchDocument({
          windowId,
          viewId,
          pageLength,
          page,
          isModal: false,
        })
      )
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            { type: ACTION_TYPES.FETCH_DOCUMENT_PENDING, payload: {
                id: windowId,
                isModal: false,
                websocketRefresh: false,
              } },
            { type: ACTION_TYPES.FETCH_DOCUMENT_SUCCESS, payload: {
                id: windowId,
                data: limitedViewData,
                isModal: false,
              } },
            { type: ACTION_TYPES.UPDATE_TABLE, payload: {
                id: tableId,
                data: tableData,
              } },
          ])
        );
      });
  });

  it(`dispatches 'CREATE_FILTER' with properly formatted filters when fetching filtered view rows data`, () => {
    const limitedViewLayout = fixtures.viewLayout2;
    const limitedViewData = fixtures.basicViewData2;
    const limitedCreateViewData = _.omit(limitedViewData, [
      'columnsByFieldName',
      'result',
      'firstRow',
      'pageLength',
      'headerProperties',
    ]);
    const {
      windowId,
      viewId,
      pageLength,
    } = limitedViewData;
    const page = 1;
    const state = createStore({
      viewHandler: {
        views: {
          [windowId]: {
            layout: { ...limitedViewLayout },
            ...limitedCreateViewData,
          },
        },
      },
    });
    const store = mockStore(state);
    const filterId = getEntityRelatedId({ windowId, viewId });
    const activeFiltersCaptions = populateFiltersCaptions({
      filterData: limitedViewLayout.filters,
      filtersActive: limitedViewData.filters,
    });
    const filtersActive = formatFilters({
      filtersData: limitedViewLayout.filters,
      filtersActive: limitedViewData.filters,
    });

    const filtersData = {
      filterData: limitedViewLayout.filters, // set the proper layout for the filters
      filtersActive,
      activeFiltersCaptions,
    };

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(
        `/documentView/${windowId}/${viewId}?firstRow=${pageLength *
          (page - 1)}&pageLength=${pageLength}`
      )
      .reply(200, _.cloneDeep(limitedViewData));

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/documentView/${windowId}/${viewId}/quickActions`)
      .reply(200, { data: { actions: [] } });

    return store
      .dispatch(
        viewActions.fetchDocument({
          windowId,
          viewId,
          pageLength,
          page,
          isModal: false,
        })
      )
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            { type: ACTION_TYPES.CREATE_FILTER, payload: {
                id: filterId,
                data: filtersData,
              } },
          ])
        );
      });
  });

  it(`dispatches 'TOGGLE_INCLUDED_VIEW' and 'SET_INCLUDED_VIEW' actions when
   fetching view rows data for included views`, () => {
    const layoutData = gridLayoutFixtures.layout2_parent;
    const rowsData = gridRowFixtures.data2_parent;
    const { windowId, viewId, pageLength } = rowsData;
    const page = 1;
    const state = createStore({
      viewHandler: {
        modals: {
          [windowId]: {
            ...viewState,
            layout: { ...layoutData },
          },
        },
      },
    });
    const store = mockStore(state);
    const includedWindowId = rowsData.result[0].includedView.windowId;
    const includedViewId = rowsData.result[0].includedView.viewId;
    const parentId = layoutData.windowId;

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(
        `/documentView/${windowId}/${viewId}?firstRow=${pageLength *
          (page - 1)}&pageLength=${pageLength}`
      )
      .reply(200, rowsData);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/documentView/${windowId}/${viewId}/quickActions`)
      .reply(200, { data: { actions: [] } });

    return store
      .dispatch(
        viewActions.fetchDocument({
          windowId,
          viewId,
          pageLength,
          page,
          isModal: true,
        })
      )
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            { type: ACTION_TYPES.TOGGLE_INCLUDED_VIEW, payload: {
                id: windowId,
                showIncludedView: true,
                isModal: true,
              } },
            { type: ACTION_TYPES.SET_INCLUDED_VIEW, payload: {
                id: includedWindowId,
                viewId: includedViewId,
                viewProfileId: null,
                parentId,
              } },
          ])
        );
      });
  });

  it(`dispatches 'SET_INCLUDED_VIEW' action with viewId from the 'includedView' when it exists`, () => {
    const layoutData = gridLayoutFixtures.layout2_parent;
    const rowsData = gridRowFixtures.data2_parent;
    const { windowId, viewId, pageLength } = rowsData;
    const includedWindowId = 'pickingSlot';
    const includedViewId = 'pickingSlot-Ne-1001024';
    const page = 1;
    const state = createStore({
      viewHandler: {
        modals: {
          [windowId]: {
            ...viewState,
            layout: { ...layoutData },
          },
        },
        includedView: {
          viewId: includedViewId,
          windowType: includedWindowId,
          parentId: windowId,
          viewProfileId: null,
        },
      },
    });
    const store = mockStore(state);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(
        `/documentView/${windowId}/${viewId}?firstRow=${pageLength *
          (page - 1)}&pageLength=${pageLength}`
      )
      .reply(200, rowsData);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/documentView/${windowId}/${viewId}/quickActions`)
      .reply(200, { data: { actions: [] } });

    return store
      .dispatch(
        viewActions.fetchDocument({
          windowId,
          viewId,
          pageLength,
          page,
          isModal: true,
        })
      )
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            { type: ACTION_TYPES.TOGGLE_INCLUDED_VIEW, payload: {
                id: windowId,
                showIncludedView: true,
                isModal: true,
              } },
            { type: ACTION_TYPES.SET_INCLUDED_VIEW, payload: {
                id: includedWindowId,
                viewId: includedViewId,
                viewProfileId: null,
                parentId: windowId,
              } },
          ])
        );
      });
  });

  it(`dispatches 'UPDATE_VIEW_DATA' when fetching header properties for views`, () => {
    const headersData = generalData.headerProperties1;
    const viewData = _.omit(limitedViewData, ['result']);
    const { windowId, viewId, } = viewData;
    const tableId = getTableId({ windowId, viewId });
    const state = createStore({
      viewHandler: {
        views: {
          [windowId]: {
            layout: { ...limitedViewLayout },
            ...viewData,
          },
        },
      },
      tables: {
        [tableId]: createTableData({
          ...limitedViewData,
          ...limitedViewLayout,
        }),
      },
    });
    const store = mockStore(state);
    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/documentView/${windowId}/${viewId}/headerProperties`)
      .reply(200, headersData);

    // check if headerProperties are empty before running actions
    expect(
      store.getState().viewHandler.views[windowId].headerProperties.groups
        .length
    ).toEqual(0);

    return store
      .dispatch(
        viewActions.fetchHeaderProperties({ windowId, viewId, isModal: false })
      )
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            {
              type: ACTION_TYPES.UPDATE_VIEW_DATA_SUCCESS, payload: {
                id: windowId,
                data: {
                  headerProperties: headersData,
                },
                isModal: false,
              }
            },
          ])
        );
      });
  });

  it.todo(`dispatches 'FILTER_VIEW_*' when the view is filtered`);
});
