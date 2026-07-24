import React from 'react';
import { act } from 'react-dom/test-utils';
import { mount, shallow } from 'enzyme';
import nock from 'nock';
import { Provider } from 'react-redux';
import { applyMiddleware, combineReducers, createStore } from 'redux';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import { merge } from 'merge-anything';
import thunk from 'redux-thunk';
import promiseMiddleware from 'redux-promise';
import { waitFor } from '@testing-library/dom';
import http from 'http';
import StompServer from 'stomp-broker-js';

import { serverTestPort } from '../../../test_setup/jestSetup';

import {
  ShortcutProvider
} from '../../components/keyshortcuts/ShortcutProvider';
import { ProvideAuth } from '../../hooks/useAuth';
import { Routes } from '../../routes';

import pluginsHandler, {
  initialState as pluginsHandlerState,
} from '../../reducers/pluginsHandler';
import appHandler, {
  initialState as appHandlerState,
} from '../../reducers/appHandler';
import windowHandler, {
  initialState as windowHandlerState,
} from '../../reducers/windowHandler';
import menuHandler, {
  initialState as menuHandlerState,
} from '../../reducers/menuHandler';
import listHandler, {
  initialState as listHandlerState,
} from '../../reducers/listHandler';
import viewHandler, {
  initialState as viewHandlerState,
} from '../../reducers/viewHandler';
import tables, {
  initialState as tablesHandlerState,
  getTableId,
} from '../../reducers/tables';

import fixtures from '../../../test_setup/fixtures/master_window.json';
import dataFixtures from '../../../test_setup/fixtures/master_window/data.json';
import layoutFixtures
  from '../../../test_setup/fixtures/master_window/layout.json';
import rowFixtures
  from '../../../test_setup/fixtures/master_window/row_data.json';
import docActionFixtures
  from '../../../test_setup/fixtures/master_window/doc_action.json';
import topActionsFixtures
  from '../../../test_setup/fixtures/master_window/top_actions.json';
import menuFixtures from '../../../test_setup/fixtures/master_window/menu.json';
import userSessionData from '../../../test_setup/fixtures/user_session.json';
import notificationsData from '../../../test_setup/fixtures/notifications.json';

import MasterWindow from '../../components/app/MasterWindow';
import { discardNewRequest, getRowsData } from '../../api';

// closeModalCallback calls discardNewRequest (from ../../api) and awaits its
// promise, then — for an already-persisted (numeric-id) abandoned row — re-fetches
// that row via getRowsData and applies its reverted DB value to the parent grid.
jest.mock('../../api', () => ({
  __esModule: true,
  discardNewRequest: jest.fn(() => Promise.resolve()),
  getRowsData: jest.fn(() => Promise.resolve({ data: { result: [] } })),
}));

const middleware = [thunk, promiseMiddleware];
const FIXTURES_PROPS = fixtures;
const path = '/window/143/1000000';

localStorage.setItem('isLogged', true);

const rootReducer = combineReducers({
  appHandler,
  listHandler,
  viewHandler,
  menuHandler,
  windowHandler,
  pluginsHandler,
  tables,
});

const createInitialState = function(state = {}) {
  return merge(
    {
      appHandler: { ...appHandlerState },
      windowHandler: { ...windowHandlerState },
      listHandler: { ...listHandlerState },
      viewHandler: { ...viewHandlerState },
      menuHandler: { ...menuHandlerState },
      pluginsHandler: { ...pluginsHandlerState },
      tables: tablesHandlerState,
    },
    state
  );
};

describe.skip('MasterWindowContainer', () => {
  const menuResponse = menuFixtures.menu1;

  let mockServer;
  let server;
  let history;

  beforeAll(() => {
    server = http.createServer();

    mockServer = new StompServer({
      server: server,
      path: '/ws',
    });

    server.listen(serverTestPort); // this is defined in the jestSetup file
  });

  // afterEach stop server
  afterAll(async () => {
    await server.close();
  });

  beforeEach(() => {
    history = createMemoryHistory({ initialEntries: [path] });
  });

  it('renders without errors', async () => {
    const initialState = createInitialState();
    const store = createStore(
      rootReducer,
      initialState,
      applyMiddleware(...middleware)
    );
    const windowType = FIXTURES_PROPS.props1.params.windowType;
    const docId = FIXTURES_PROPS.props1.params.docId;
    const tabId = layoutFixtures.layout1.tabs[0].tabId;

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/`)
      .reply(200, dataFixtures.data1);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/layout`)
      .reply(200, layoutFixtures.layout1);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get('/userSession')
      .reply(200, userSessionData);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get('/login/isLoggedIn')
      .reply(200, true);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/notifications/websocketEndpoint`)
      .reply(200, `/notifications/${userSessionData.userProfileId}`);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get('/notifications/all?limit=20')
      .reply(200, notificationsData.data1);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/menu/elementPath?type=window&elementId=${windowType}&inclusive=true`)
      .reply(200, menuResponse);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/${tabId}/`)
      .reply(200, { result: rowFixtures.row_data1 });

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/${tabId}/?orderBy=%2BLine,%2BC_OrderLine_ID`)
      .reply(200, { result: rowFixtures.row_data1 });

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/menu/elementPath?type=window&elementId=${windowType}&inclusive=true`)
      .reply(200, menuResponse);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/${tabId}/topActions`)
      .reply(200, topActionsFixtures.top_actions1);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/field/DocAction/dropdown`)
      .reply(200, docActionFixtures.data1);

    let wrapper;

    await act(async () => {
      wrapper = await mount(
        <Provider store={store}>
          <ProvideAuth>
            <ShortcutProvider>
              <Router history={history}>
                <Routes />
              </Router>
            </ShortcutProvider>
          </ProvideAuth>
        </Provider>
      );
    });

    await act( async() => {
      await waitFor(async() => {
        wrapper.update();
        // I have no idea why, but if I try to search for `body` or pretty much anything else, it
        // starts throwing some weird jsdom errors
        expect(wrapper.find('tbody tr').length).not.toEqual(0);
      }, { timeout: 8000, interval: 500 });  
    });

  }, 10000);

  it('reacts to websocket events and updates the UI correctly when discount is applied', async () => {
    const initialState = createInitialState();
    const store = createStore(
      rootReducer,
      initialState,
      applyMiddleware(...middleware)
    );
    const windowType = FIXTURES_PROPS.props1.params.windowType;
    const docId = FIXTURES_PROPS.props1.params.docId;
    const tabId = layoutFixtures.layout1.tabs[0].tabId;
    const updatedRows = rowFixtures.updatedRow1;

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/`)
      .reply(200, dataFixtures.data1);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/layout`)
      .reply(200, layoutFixtures.layout1);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get('/userSession')
      .reply(200, userSessionData);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/notifications/websocketEndpoint`)
      .reply(200, `/notifications/${userSessionData.userProfileId}`);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get('/notifications/all?limit=20')
      .reply(200, notificationsData.data1);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/menu/elementPath?type=window&elementId=${windowType}&inclusive=true`)
      .reply(200, menuResponse);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/${tabId}/?orderBy=%2BLine`)
      .reply(200, { result: rowFixtures.row_data1 });

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/${tabId}/topActions`)
      .reply(200, topActionsFixtures.top_actions1);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/field/DocAction/dropdown`)
      .reply(200, docActionFixtures.data1);

    // after update

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(
        `/window/${windowType}/${docId}/${tabId}?ids=${updatedRows[0].rowId}`
      )
      .reply(200, updatedRows);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/`)
      .reply(200, dataFixtures.data1);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(
        `/window/${windowType}/${docId}/${tabId}?ids=${updatedRows[0].rowId}`
      )
      .reply(200, updatedRows);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/`)
      .reply(200, dataFixtures.data1);

    let wrapper;

    await act(async () => {
      wrapper = await mount(
        <Provider store={store}>
          <ProvideAuth>
            <ShortcutProvider>
              <Router history={history}>
                <Routes />
              </Router>
            </ShortcutProvider>
          </ProvideAuth>
        </Provider>
      );
    });

    const msg = dataFixtures.websocketMessage1;

    // connection to the server takes some time, so we're waiting for the websocket url to be saved
    // in the store and then once the connection is open - push a websocket event from
    // the server
    await act(async() => {
      await waitFor(() =>
        expect(store.getState().windowHandler.master.websocket).toBeTruthy()
      ).then(() => {
        setTimeout(() => {
          mockServer.send(
            store.getState().windowHandler.master.websocket,
            {},
            JSON.stringify(msg)
          );
        }, 5000);
      });
    });

    await act( async() => {
      await waitFor(async() => {
        wrapper.update();
        expect(wrapper.find('tbody tr').length).toBe(7);
      });  
    });

  }, 20000);

  it('removes old and includes new rows on ws event', async () => {
    const initialState = createInitialState();
    const store = createStore(
      rootReducer,
      initialState,
      applyMiddleware(...middleware)
    );
    const localHistory = createMemoryHistory({ initialEntries: ['/window/53009/1000000'] });

    const windowType = FIXTURES_PROPS.props2.params.windowType;
    const docId = FIXTURES_PROPS.props2.params.docId;
    const tabId = layoutFixtures.layout2.tabs[0].tabId;
    const updatedRows = rowFixtures.updated_row_data2;
    const msg = dataFixtures.websocketMessage2;

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/`)
      .reply(200, dataFixtures.data2);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/layout`)
      .reply(200, layoutFixtures.layout2);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get('/userSession')
      .reply(200, userSessionData);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/notifications/websocketEndpoint`)
      .reply(200, `/notifications/${userSessionData.userProfileId}`);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get('/notifications/all?limit=20')
      .reply(200, notificationsData.data1);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/menu/elementPath?type=window&elementId=${windowType}&inclusive=true`)
      .reply(200, menuResponse);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/${tabId}/`)
      .reply(200, rowFixtures.row_data2);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/${tabId}/?orderBy=%2BLine`)
      .reply(200, rowFixtures.row_data2);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/${tabId}/topActions`)
      .reply(200, topActionsFixtures.top_actions1);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/field/DocAction/dropdown`)
      .reply(200, docActionFixtures.data1);

    // after update
    const rows = msg.includedTabsInfo[tabId].staleRowIds.join(',');

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/${tabId}?ids=${rows}`)
      .reply(200, updatedRows);

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/window/${windowType}/${docId}/`)
      .reply(200, dataFixtures.data2);

    let wrapper;

    await act(async () => {
      wrapper = await mount(
        <Provider store={store}>
          <ProvideAuth>
            <ShortcutProvider>
              <Router history={localHistory}>
                <Routes />
              </Router>
            </ShortcutProvider>
          </ProvideAuth>
        </Provider>
      );
    });

    await act( async() => {
      await waitFor(async() => {
        wrapper.update();
      expect(wrapper.find('tbody tr').length).toBe(4);
      expect(wrapper.html()).toContain('288.86');
      }, { timeout: 8000, interval: 500 });  
    }); 

    await act(async() => {
      await waitFor(() =>
        expect(store.getState().windowHandler.master.websocket).toBeTruthy()
      ).then(() => {
        setTimeout(() => {
          mockServer.send(
            store.getState().windowHandler.master.websocket,
            {},
            JSON.stringify(msg)
          );
        }, 5000);
      });
    });   

    await act( async() => {
      await waitFor(async() => {
        wrapper.update();
        expect(wrapper.html()).toContain('2,888.60');
      }, { timeout: 8000, interval: 500 });
    });

  }, 20000);
});

describe('MasterWindow.closeModalCallback - abandon must not drop a persisted new-record row', () => {
  const WINDOW_TYPE = '540321';
  const DOCUMENT_ID = 'd1';
  const TAB_ID = 't1';

  // Minimal props so shallow() can construct the instance without render throwing.
  const buildProps = (overrides = {}) => ({
    modal: { visible: false },
    master: {
      docId: DOCUMENT_ID,
      data: { DocumentNo: undefined },
      layout: {},
      includedTabsInfo: {},
      hasComments: false,
    },
    breadcrumb: [],
    rawModal: {},
    pluginModal: {},
    me: {},
    overlay: { data: {}, visible: false },
    params: { windowId: WINDOW_TYPE, docId: DOCUMENT_ID },
    updateTabRowsData: jest.fn(),
    ...overrides,
  });

  const getInstance = (props) => shallow(<MasterWindow {...props} />).instance();

  // A realistic ?ids= re-fetch response: the persisted row, reverted server-side
  // to its DB ValidFrom (a far-future year) after discardChanges — the abandoned
  // in-memory colliding value (01/01/2015) is gone.
  const PERSISTED_ROW_ID = '1234567';
  const revertedRowsResponse = {
    data: {
      result: [
        {
          rowId: PERSISTED_ROW_ID,
          fieldsByName: {
            ValidFrom: { field: 'ValidFrom', value: '2053-11-30', widgetType: 'Date' },
          },
        },
      ],
    },
  };

  beforeEach(() => {
    discardNewRequest.mockClear();
    discardNewRequest.mockResolvedValue();
    getRowsData.mockClear();
    getRowsData.mockResolvedValue(revertedRowsResponse);
  });

  // Case A - the bug: a modal auto-saved (rowId became a real numeric id) but
  // saveStatus stayed falsy. Abandon must NOT remove the now-persisted row.
  it('does not remove a persisted (numeric rowId) row when saveStatus is falsy', async () => {
    const props = buildProps();
    const instance = getInstance(props);

    await instance.closeModalCallback({
      isNew: true,
      windowType: WINDOW_TYPE,
      documentId: DOCUMENT_ID,
      tabId: TAB_ID,
      rowId: PERSISTED_ROW_ID,
      saveStatus: false,
    });

    const removedPersistedRow = props.updateTabRowsData.mock.calls.some(
      ([, payload]) =>
        payload && payload.removed && payload.removed[PERSISTED_ROW_ID]
    );
    expect(removedPersistedRow).toBe(false);
  });

  // Case B - regression guard: a genuinely unsaved new row (rowId === 'NEW')
  // must still be discarded from the parent grid.
  it('removes an unsaved new row (rowId === NEW) when saveStatus is falsy', async () => {
    const props = buildProps();
    const instance = getInstance(props);

    await instance.closeModalCallback({
      isNew: true,
      windowType: WINDOW_TYPE,
      documentId: DOCUMENT_ID,
      tabId: TAB_ID,
      rowId: 'NEW',
      saveStatus: false,
    });

    const expectedTableId = getTableId({
      windowId: WINDOW_TYPE,
      docId: DOCUMENT_ID,
      tabId: TAB_ID,
    });
    expect(props.updateTabRowsData).toHaveBeenCalledWith(expectedTableId, {
      removed: { NEW: true },
    });
  });

  // Case C - the fix: abandoning an already-persisted (numeric rowId) row with a
  // falsy saveStatus must re-fetch the row and UPDATE (not remove) it in the grid,
  // so the retained row shows its reverted DB value without a browser reload.
  it('re-fetches and UPDATES a persisted row with its reverted DB value on abandon', async () => {
    const props = buildProps();
    const instance = getInstance(props);

    await instance.closeModalCallback({
      isNew: true,
      windowType: WINDOW_TYPE,
      documentId: DOCUMENT_ID,
      tabId: TAB_ID,
      rowId: PERSISTED_ROW_ID,
      saveStatus: false,
    });

    const expectedTableId = getTableId({
      windowId: WINDOW_TYPE,
      docId: DOCUMENT_ID,
      tabId: TAB_ID,
    });

    // the reverted DB row was re-fetched for exactly this persisted row
    expect(getRowsData).toHaveBeenCalledWith(
      expect.objectContaining({
        entity: 'window',
        docType: WINDOW_TYPE,
        docId: DOCUMENT_ID,
        tabId: TAB_ID,
        rows: [PERSISTED_ROW_ID],
      })
    );

    // it was applied as an UPDATE (changed), never a removal
    const updateCall = props.updateTabRowsData.mock.calls.find(
      ([, payload]) => payload && payload.changed && payload.changed[PERSISTED_ROW_ID]
    );
    expect(updateCall).toBeDefined();
    expect(updateCall[0]).toBe(expectedTableId);
    expect(updateCall[1].changed[PERSISTED_ROW_ID].fieldsByName.ValidFrom.value).toBe(
      '2053-11-30'
    );

    const removedAny = props.updateTabRowsData.mock.calls.some(
      ([, payload]) => payload && payload.removed
    );
    expect(removedAny).toBe(false);
  });

  // Case D - reconcile the deleted-row edge: if the persisted row was removed
  // server-side between the abandon and the re-fetch, the ?ids= GET reports it in
  // missingIds → the grid row must be removed (not left stranded).
  it('removes a persisted row from the grid when the re-fetch reports it missing', async () => {
    getRowsData.mockResolvedValue({
      data: { result: [], missingIds: [PERSISTED_ROW_ID] },
    });

    const props = buildProps();
    const instance = getInstance(props);

    await instance.closeModalCallback({
      isNew: true,
      windowType: WINDOW_TYPE,
      documentId: DOCUMENT_ID,
      tabId: TAB_ID,
      rowId: PERSISTED_ROW_ID,
      saveStatus: false,
    });

    const expectedTableId = getTableId({
      windowId: WINDOW_TYPE,
      docId: DOCUMENT_ID,
      tabId: TAB_ID,
    });
    expect(props.updateTabRowsData).toHaveBeenCalledWith(expectedTableId, {
      removed: { [PERSISTED_ROW_ID]: true },
    });
  });
});
