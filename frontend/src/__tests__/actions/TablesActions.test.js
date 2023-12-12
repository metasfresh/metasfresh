// noinspection JSUnresolvedFunction

import thunk from 'redux-thunk';
import configureStore from 'redux-mock-store';
import { merge } from 'merge-anything';
import { combineReducers } from 'redux';
import nock from 'nock';

import tablesHandler, {
  getTableId,
  initialTableState
} from '../../reducers/tables';
import viewHandler, {
  initialState as initialViewsState
} from '../../reducers/viewHandler';

import {
  createGridTable,
  createTableData,
  createTabTable,
  deleteTable,
  deselectTableRows,
  setActiveSort,
  updateGridTable,
  updateTableSelection,
  updateTabTable,
} from '../../actions/TableActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';
import { flattenRows } from '../../utils/documentListHelper';

import masterWindowProps from '../../../test_setup/fixtures/master_window.json';
import masterDataFixtures
  from '../../../test_setup/fixtures/master_window/data.json';
import masterLayoutFixtures
  from '../../../test_setup/fixtures/master_window/layout.json';
import masterRowFixtures
  from '../../../test_setup/fixtures/master_window/row_data.json';

import gridProps from '../../../test_setup/fixtures/grid.json';
import gridDataFixtures from '../../../test_setup/fixtures/grid/data.json';
import gridLayoutFixtures from '../../../test_setup/fixtures/grid/layout.json';
import gridRowFixtures from '../../../test_setup/fixtures/grid/row_data.json';

const middlewares = [thunk];
const mockStore = configureStore(middlewares);

const createState = function(state = {}) {
  return merge(
    {
      viewHandler: initialViewsState,
      tables: { ...tablesHandler(undefined, {}) },
    },
    state
  );
};

describe('TableActions general', () => {
  it('should call DELETE_TABLE action with correct payload', async () => {
    const { windowType, viewId } = gridProps.props1;
    const id = getTableId({ windowId: windowType, viewId });
    const payload = { id };

    const initialState = createState({
      tables: {
        length: 1,
        [id]: { id },
      },
    });
    const store = mockStore(initialState);

    await store.dispatch(deleteTable(id));
    expect(store.getActions()).toEqual([
      { type: ACTION_TYPES.DELETE_ATTRIBUTES },
      { type: ACTION_TYPES.DELETE_TABLE, payload }
    ]);
  });

  it(`dispatches 'SET_ACTIVE_SORT' action when setting active sort`, async () => {
    const { windowType, viewId } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const id = getTableId({ windowId: windowType, viewId });
    const initialState = createState({
      viewHandler: {
        views: {
          [windowType]: {
            layout: { ...layoutResponse },
          },
        },
      },
    });
    const store = mockStore(initialState);
    const payload = {
      id,
      active: true,
    };

    await store.dispatch(setActiveSort(id, payload.active));
    expect(store.getActions()).toEqual([{ type: ACTION_TYPES.SET_ACTIVE_SORT, payload }]);
  });

  it('should call UPDATE_TABLE_SELECTION action with correct payload', async () => {
    const { windowId, tabId, id, rowId } = masterRowFixtures.row_data1[0];
    const tableId = getTableId({ windowId, tabId, docId: id });
    const keyProperty = 'rowId';
    const payload = {
      id: tableId,
      selection: [rowId],
      keyProperty,
    }
    const store = mockStore();

    await store.dispatch(updateTableSelection({
      id: tableId,
      selection: [rowId],
      keyProperty }));
    expect(store.getActions()).toEqual([{ type: ACTION_TYPES.UPDATE_TABLE_SELECTION, payload }]);
  });

  it('should call DESELECT_TABLE_ROWS action with correct payload', async () => {
    const { windowId, tabId, id } = masterRowFixtures.row_data1[0];
    const tableId = getTableId({ windowId, tabId, docId: id });
    const payload = {
      id: tableId,
      selection: [],
    }
    const store = mockStore();

    await store.dispatch(deselectTableRows({ id: tableId, selection: [] }));
    expect(store.getActions()).toEqual([{ type: ACTION_TYPES.DESELECT_TABLE_ROWS, payload }]);
  });
});

describe('TableActions grid', () => {
  beforeAll(() => {
    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .persist()
      .post(uri => uri.includes('quickActions'))
      .reply(200, { data: { actions: [] } });
  });

  afterAll(() => {
    nock.cleanAll();
  });

  it(`dispatches 'CREATE_TABLE' action when creating a new view`, async () => {
    const { windowType, viewId } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const dataResponse = gridDataFixtures.data1;
    const id = getTableId({ windowId: windowType, viewId });
    const initialState = createState({
      viewHandler: {
        views: {
          [windowType]: {
            layout: { ...layoutResponse },
          },
        },
      },
    });
    const store = mockStore(initialState);

    await store.dispatch(createGridTable(id, dataResponse));
    expect(store.getActions()).toEqual([
      {
        type: ACTION_TYPES.CREATE_TABLE,
        payload: {
          id,
          data: createTableData({ ...dataResponse, ...layoutResponse }),
        }
      }
    ]);
  });

  it(`dispatches 'UPDATE_TABLE' action after loading data to the view`, async () => {
    const { windowType, viewId } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const dataResponse = gridDataFixtures.data1;
    const rowResponse = gridRowFixtures.data1;
    const id = getTableId({ windowId: windowType, viewId });
    const initialState = createState({
      viewHandler: {
        views: {
          [windowType]: {
            layout: { ...layoutResponse },
          },
        },
      },
      tables: {
        length: 1,
        [id]: {
          ...initialTableState,
          ...createTableData({ ...dataResponse, ...layoutResponse }),
        },
      },
    });

    const store = mockStore(initialState);

    await store.dispatch(updateGridTable(id, rowResponse));
    expect(store.getActions()).toContainEqual(
      {
        type: ACTION_TYPES.UPDATE_TABLE,
        payload: {
          id,
          data: createTableData({
            ...rowResponse,
            ...layoutResponse,
            headerElements: rowResponse.columnsByFieldName,
            keyProperty: 'id',
          }),
        }
      }
    );
  });

  it.todo(
    `dispatches 'UPDATE_TABLE' action after loading collapsible data to the view`
  );

  it(`dispatches 'CREATE_TABLE' action when browsing an existing view but table is not yet created`, async () => {
    const { windowType, viewId } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const rowResponse = gridRowFixtures.data1;
    const id = getTableId({ windowId: windowType, viewId });
    const initialState = createState({
      viewHandler: {
        views: {
          [windowType]: {
            layout: { ...layoutResponse },
          },
        },
      },
    });

    const store = mockStore(initialState);

    await store.dispatch(updateGridTable(id, rowResponse));
    expect(store.getActions()).toContainEqual(
      {
        type: ACTION_TYPES.CREATE_TABLE,
        payload: {
          id,
          data: createTableData({
            ...rowResponse,
            ...layoutResponse,
            headerElements: rowResponse.columnsByFieldName,
            keyProperty: 'id',
          }),
        }
      }
    );
  });

  it(`should call UPDATE_TABLE_SELECTION and TOGGLE/SET_INCLUDED_VIEW actions on selection change`, async () => {
    const parentLayoutResponse = gridLayoutFixtures.layout2_parent;
    const rowResponse = gridRowFixtures.data3_parent;
    const { windowId, viewId , result} = rowResponse;
    const rowId = result[0].id;
    const { includedView } = result[0];
    const tableId = getTableId({ windowId, viewId });
    const tableData_create = createTableData({
      ...parentLayoutResponse,
      ...rowResponse,
      keyProperty: 'id',
    });
    const initialState = createState({
      viewHandler: {
        modals: {
          [windowId]: {
            layout: { ...parentLayoutResponse },
          },
        },
      },
      tables: {
        length: 1,
        [tableId]: {
          ...initialTableState,
          ...tableData_create,
        },
      },
    });
    const store = mockStore(initialState);

    await store.dispatch(updateTableSelection({
      id: tableId,
      selection: [rowId],
      windowId,
      viewId,
      isModal: true,
    }));

    // noinspection JSVoidFunctionReturnValueUsed
    expect(store.getActions()).toEqual(expect.arrayContaining([
      {
        type: ACTION_TYPES.UPDATE_TABLE_SELECTION,
        payload: { id: tableId, selection: [rowId], keyProperty: 'id' }
      },
      {
        type: ACTION_TYPES.TOGGLE_INCLUDED_VIEW,
        payload: { id: windowId, showIncludedView: true, isModal: true, }
      },
      {
        type: ACTION_TYPES.SET_INCLUDED_VIEW,
        payload: { id: includedView.windowId, viewId: includedView.viewId, viewProfileId: null, parentId: windowId, }
      },
    ]));
  });

  it(`should call DESELECT_TABLE_ROWS and TOGGLE/SET_INCLUDED_VIEW actions on deselecting rows`, async () => {
    const parentLayoutResponse = gridLayoutFixtures.layout2_parent;
    const childLayoutResponse = gridLayoutFixtures.layout2_child;
    const parentRowResponse = gridRowFixtures.data3_parent;
    const childRowResponse = gridRowFixtures.data3_child;
    const { windowId, viewId, parentWindowId, parentViewId } = childRowResponse;
    const parentRowId = parentRowResponse.result[0].id;
    const childRowId = childRowResponse.result[0].id;
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
          },
          [parentWindowId]: {
            layout: { ...parentLayoutResponse },
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
    });
    const appReducer = combineReducers({
      viewHandler,
      tables: tablesHandler,
    });
    // this way we can update the mocked state according to changes in actions
    const reduceState = state => actions => actions.reduce(appReducer, state);
    const initialState = reduceState(initialStateData);
    const store = mockStore(initialState);

    await store.dispatch(deselectTableRows({
      id: parentTableId,
      selection: [parentRowId],
      windowId: parentWindowId,
      viewId: parentViewId,
      isModal: true,
    }));

    // noinspection JSVoidFunctionReturnValueUsed
    expect(store.getActions()).toEqual(expect.arrayContaining([
      {
        type: ACTION_TYPES.DESELECT_TABLE_ROWS,
        payload: { id: parentTableId, selection: [parentRowId] }
      },
      {
        type: ACTION_TYPES.TOGGLE_INCLUDED_VIEW,
        payload: { id: parentWindowId, showIncludedView: false, isModal: true, }
      },
      {
        type: ACTION_TYPES.UNSET_INCLUDED_VIEW,
        payload: { id: windowId, viewId, forceClose: true, }
      },
    ]));
  });
});

describe('TableActions tab', () => {
  it(`dispatches 'CREATE_TABLE' action when creating a new tab`, async () => {
    const {
      params: { windowType, docId },
    } = masterWindowProps.props1;
    const masterWindowData = masterDataFixtures.data1[0];
    const store = mockStore();
    const expectedActions = [];
    const dispatchedActions = [];

    Object.values(masterWindowData.includedTabsInfo).forEach((tab) => {
      const tableId = getTableId({
        windowId: windowType,
        docId,
        tabId: tab.tabId,
      });
      const dataResponse = {
        windowType,
        docId,
        tableId,
        ...tab,
      };

      dispatchedActions.push(store.dispatch(createTabTable(tableId, dataResponse)));
      expectedActions.push({
        type: ACTION_TYPES.CREATE_TABLE,
        payload: {
          id: tableId,
          data: createTableData(dataResponse),
        }
      });
    });

    await Promise.all(dispatchedActions);
    // noinspection JSVoidFunctionReturnValueUsed
    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
  });

  it(`dispatches 'UPDATE_TABLE' action when loading details view layout`, async () => {
    const {
      params: { windowType, docId },
    } = masterWindowProps.props1;
    const masterWindowData = masterDataFixtures.data1[0];
    const layoutResponse = masterLayoutFixtures.layout1;
    const initialStateTables = {};

    Object.values(masterWindowData.includedTabsInfo).forEach((tab) => {
      const tableId = getTableId({
        windowId: windowType,
        docId,
        tabId: tab.tabId,
      });
      initialStateTables[tableId] = {
        windowType,
        docId,
        tableId,
        ...tab,
      };
    });

    const initialState = createState({
      viewHandler: {
        views: {
          [windowType]: {
            layout: { ...layoutResponse },
          },
        },
      },
      tables: {
        length: Object.values(masterWindowData.includedTabsInfo).length,
        ...initialStateTables,
      },
    });

    const store = mockStore(initialState);
    const expectedActions = [];
    const dispatchedActions = [];

    layoutResponse.tabs.forEach((tab) => {
      const tableId = getTableId({
        windowId: windowType,
        docId,
        tabId: tab.tabId,
      });
      const dataResponse = {
        windowType,
        docId,
        tableId,
        ...tab,
      };

      dispatchedActions.push(store.dispatch(updateTabTable({ tableId, tableResponse: dataResponse, pending: true })));
      expectedActions.push({
        type: ACTION_TYPES.UPDATE_TABLE,
        payload: {
          id: tableId,
          data: createTableData({ ...dataResponse, keyProperty: 'rowId', pending: true }),
        }
      });
    });

    await Promise.all(dispatchedActions);
    // noinspection JSVoidFunctionReturnValueUsed
    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
  });

  it(`dispatches 'UPDATE_TABLE' action when populating table with rows data`, async () => {
    const {
      params: { windowType, docId },
    } = masterWindowProps.props1;
    const masterWindowResponse = masterDataFixtures.data1[0];
    const layoutResponse = masterLayoutFixtures.layout1;
    const rowDataResponse = masterRowFixtures.row_data1;

    const initialStateTables = {};

    Object.values(masterWindowResponse.includedTabsInfo).forEach((tab) => {
      const tableId = getTableId({
        windowId: windowType,
        docId,
        tabId: tab.tabId,
      });
      const fullTab = {
        ...tab,
        ...layoutResponse.tabs[tab.tabId],
      };
      initialStateTables[tableId] = {
        windowType,
        docId,
        tableId,
        ...fullTab,
      };
    });

    const initialState = createState({
      viewHandler: {
        views: {
          [windowType]: {
            layout: { ...layoutResponse },
          },
        },
      },
      tables: {
        length: Object.values(masterWindowResponse.includedTabsInfo).length,
        ...initialStateTables,
      },
    });

    const store = mockStore(initialState);
    const tabId = rowDataResponse[0].tabId;
    const tableId = getTableId({ windowId: windowType, docId, tabId });
    const tableData = createTableData({
      result: rowDataResponse,
      keyProperty: 'rowId',
      pending: true,
    });
    tableData.rows = flattenRows(tableData.rows);

    await store.dispatch(updateTabTable({ tableId, tableResponse: { result: rowDataResponse }, pending: true }));
    expect(store.getActions()).toContainEqual(
      {
        type: ACTION_TYPES.UPDATE_TABLE,
        payload: {
          id: tableId,
          data: { ...tableData },
        }
      }
    );
  });
});
