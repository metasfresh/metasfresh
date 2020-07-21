import thunk from 'redux-thunk'
import configureStore from 'redux-mock-store';
import produce from 'immer';
import merge from 'merge';

import tablesHandler, { tableState, getTableId } from '../../reducers/tables';
import viewHandler from '../../reducers/viewHandler';
import {
  createTableData,
  createGridTable,
  createTabTable,
  updateGridTable,
  updateTabTable,
  deleteTable,
  setActiveSort,
} from '../../actions/TableActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';
import { flattenRows } from '../../utils/documentListHelper';

import masterWindowProps from '../../../test_setup/fixtures/master_window.json';
import masterDataFixtures from '../../../test_setup/fixtures/master_window/data.json';
import masterLayoutFixtures from '../../../test_setup/fixtures/master_window/layout.json';
import masterRowFixtures from '../../../test_setup/fixtures/master_window/row_data.json';

import gridProps from '../../../test_setup/fixtures/grid.json';
import gridDataFixtures from '../../../test_setup/fixtures/grid/data.json';
import gridLayoutFixtures from '../../../test_setup/fixtures/grid/layout.json';
import gridRowFixtures from '../../../test_setup/fixtures/grid/row_data.json';

const middlewares = [thunk];
const mockStore = configureStore(middlewares);

const createStore = function(state = {}) {
  const res = merge.recursive(
    true,
    {
      ...viewHandler,
      tables: { ...tablesHandler(undefined, {}) }
    },
    state
  );

  return res;
}

describe('TableActions general', () => {
  it('should call DELETE_TABLE action with correct payload', () => {
    const payload = { id: '143_143-F'};
    const action = deleteTable(payload.id);

    expect(action.type).toEqual(ACTION_TYPES.DELETE_TABLE);
    expect(action.payload).toHaveProperty('id', payload.id);
  });

  it(`dispatches 'SET_ACTIVE_SORT' action when setting active sort`, () => {
    const { windowType, viewId } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const id = getTableId({ windowId: windowType, viewId });
    const initialState = createStore({
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
    const expectedActions = [
      { type: ACTION_TYPES.SET_ACTIVE_SORT, payload },
    ];

    store.dispatch(setActiveSort(id, payload.active));
    expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
  });
});

describe('TableActions grid', () => {
  it(`dispatches 'CREATE_TABLE' action when creating a new view`, () => {
    const { windowType, viewId } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const dataResponse = gridDataFixtures.data1;
    const id = getTableId({ windowId: windowType, viewId });
    const initialState = createStore({
      viewHandler: {
        views: {
          [windowType]: {
            layout: { ...layoutResponse },
          },
        },
      },
    });
    const store = mockStore(initialState); 
    const tableData = createTableData({ ...dataResponse, ...layoutResponse });
    const payload = {
      id,
      data: tableData,
    };
    const expectedActions = [
      { type: ACTION_TYPES.CREATE_TABLE, payload },
    ];

    return store.dispatch(createGridTable(id, dataResponse)).then(() => {
      expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
    });
  });

  it(`dispatches 'UPDATE_TABLE' action after loading data to the view`, () => {
    const { windowType, viewId } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const dataResponse = gridDataFixtures.data1;
    const rowResponse = gridRowFixtures.data1;
    const id = getTableId({ windowId: windowType, viewId });
    const tableData_create = createTableData({ ...dataResponse, ...layoutResponse });
    const tableData_update = createTableData({
      ...rowResponse,
      headerElements: rowResponse.columnsByFieldName,
      keyProperty: 'id',
    });
    const initialState = createStore({
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
          ...tableState,
          ...tableData_create,
        }
      }
    });

    const store = mockStore(initialState); 
    const payload = {
      id,
      data: tableData_update,
    };
    const expectedActions = [
      { type: ACTION_TYPES.UPDATE_TABLE, payload },
    ]

    return store.dispatch(updateGridTable(id, rowResponse)).then(() => {
      expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
    });
  });

  it.todo(`dispatches 'UPDATE_TABLE' action after loading collapsible data to the view`);

  it(`dispatches 'CREATE_TABLE' action when browsing an existing view but table is not yet created`, () => {
    const { windowType, viewId } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const dataResponse = gridDataFixtures.data1;
    const rowResponse = gridRowFixtures.data1;
    const id = getTableId({ windowId: windowType, viewId });
    const tableData_create = createTableData({ ...dataResponse, ...layoutResponse });
    const tableData_update = createTableData({
      ...rowResponse,
      ...layoutResponse,
      headerElements: rowResponse.columnsByFieldName,
      keyProperty: 'id',
    });
    const initialState = createStore({
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
      data: tableData_update,
    };
    const expectedActions = [
      { type: ACTION_TYPES.CREATE_TABLE, payload },
    ]

    return store.dispatch(updateGridTable(id, rowResponse)).then(() => {
      expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
    });
  });
});

describe('TableActions tab', () => {
  it(`dispatches 'CREATE_TABLE' action when creating a new tab`, () => {
    const { params: { windowType, docId } } = masterWindowProps.props1;
    const masterWindowData = masterDataFixtures.data1[0];
    const store = mockStore();
    const expectedActions = [];
    const dispatchedActions = [];

    Object.values(masterWindowData.includedTabsInfo).forEach(tab => {
      const tableId = getTableId({ windowId: windowType, docId, tabId: tab.tabId });
      const dataResponse = {
        windowType,
        docId,
        tableId,
        ...tab
      };
      const tableData = createTableData(dataResponse);
      const payload = {
        id: tableId,
        data: tableData,
      };

      dispatchedActions.push(store.dispatch(createTabTable(tableId, dataResponse)));
      expectedActions.push(
        { type: ACTION_TYPES.CREATE_TABLE, payload },
      );
    });

    return Promise.all(dispatchedActions).then(() => {
      expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
    });
  });

  it(`dispatches 'UPDATE_TABLE' action when loading details view layout`, () => {
    const { params: { windowType, docId } } = masterWindowProps.props1;
    const masterWindowData = masterDataFixtures.data1[0];
    const layoutResponse = masterLayoutFixtures.layout1;
    const initialStateTables = {}

    Object.values(masterWindowData.includedTabsInfo).forEach(tab => {
      const tableId = getTableId({ windowId: windowType, docId, tabId: tab.tabId });
      const initialStateData = {
        windowType,
        docId,
        tableId,
        ...tab
      };

      initialStateTables[tableId] = initialStateData;
    });

    const initialState = createStore({
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
      }
    });

    const store = mockStore(initialState); 
    const expectedActions = [];
    const dispatchedActions = [];

    layoutResponse.tabs.forEach(tab => {
      const tableId = getTableId({ windowId: windowType, docId, tabId: tab.tabId });
      const dataResponse = {
        windowType,
        docId,
        tableId,
        ...tab
      };
      const tableData = createTableData({ ...dataResponse, keyProperty: 'rowId' });
      const payload = {
        id: tableId,
        data: tableData,
      };

      dispatchedActions.push(store.dispatch(updateTabTable(tableId, dataResponse)));
      expectedActions.push(
        { type: ACTION_TYPES.UPDATE_TABLE, payload },
      );
    });

    return Promise.all(dispatchedActions).then(() => {
      expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
    });
  });

  it(`dispatches 'UPDATE_TABLE' action when populating table with rows data`, () => {
    const { params: { windowType, docId } } = masterWindowProps.props1;
    const masterWindowResponse = masterDataFixtures.data1[0];
    const layoutResponse = masterLayoutFixtures.layout1;
    const rowDataResponse = masterRowFixtures.row_data1;

    const initialStateTables = {}

    Object.values(masterWindowResponse.includedTabsInfo).forEach(tab => {
      const tableId = getTableId({ windowId: windowType, docId, tabId: tab.tabId });
      const fullTab = {
        ...tab,
        ...layoutResponse.tabs[tab.tabId]
      };
      const initialStateData = {
        windowType,
        docId,
        tableId,
        ...fullTab
      };

      initialStateTables[tableId] = initialStateData;
    });

    const initialState = createStore({
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
      }
    });

    const store = mockStore(initialState);
    const tabId = rowDataResponse[0].tabId;
    const tableId = getTableId({ windowId: windowType, docId, tabId, });
    const tableData = createTableData({ result: rowDataResponse, keyProperty: 'rowId' });
    tableData.rows = flattenRows(tableData.rows);
    const payload = {
      id: tableId,
      data: {...tableData},
    };

    const expectedActions= [
      { type: ACTION_TYPES.UPDATE_TABLE, payload },
    ];

    return store.dispatch(updateTabTable(tableId, { result: rowDataResponse })).then(() => {
      expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
    });
  });
});