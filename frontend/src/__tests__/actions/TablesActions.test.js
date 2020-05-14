import thunk from 'redux-thunk'
import configureStore from 'redux-mock-store';
import produce from 'immer';
import merge from 'merge';

import tablesHandler, { getTableId } from '../../reducers/tables';
import viewHandler from '../../reducers/viewHandler';
import { fetchTab, createWindow } from '../../actions/WindowActions';
import {
  createTableData,
  createTable,
  updateTable,
  createGridTable,
  createTabTable,
  updateGridTable,
  updateTabTable,
  deleteTable,
  setActiveSortNEW,
} from '../../actions/TableActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';

import masterWindowProps from '../../../test_setup/fixtures/master_window.json';
import masterDataFixtures from '../../../test_setup/fixtures/master_window/data.json';
import masterLayoutFixtures from '../../../test_setup/fixtures/master_window/layout.json';
import masterRowFixtures from '../../../test_setup/fixtures/master_window/row_data.json';

import gridProps from '../../../test_setup/fixtures/grid.json';
import gridDataFixtures from '../../../test_setup/fixtures/grid/data.json';
import gridLayoutFixtures from '../../../test_setup/fixtures/grid/layout.json';
import gridRowFixtures from '../../../test_setup/fixtures/grid/row_data.json';

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

  it.skip(`dispatches 'SET_ACTIVE_SORT_NEW' action when setting active sort`, () => {
  });
});

describe('TableActions grid', () => {
  const propsData = masterWindowProps.props1;
  const middlewares = [thunk];
  const mockStore = configureStore(middlewares);

  it(`dispatches 'CREATE_TABLE' action when creating a new view`, () => {
    const { windowType, viewId } = gridProps.props1;
    const layoutResponse = gridLayoutFixtures.layout1;
    const dataResponse = gridDataFixtures.data1;
    const id = getTableId({ windowType, viewId });
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
    ]

    return store.dispatch(createGridTable(id, dataResponse)).then(() => {
      expect(store.getActions()).toEqual(expect.arrayContaining(expectedActions));
    });
  });
})
