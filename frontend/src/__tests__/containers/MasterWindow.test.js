import React from 'react';
import { act } from 'react-dom/test-utils';
import { mount } from 'enzyme';
import nock from 'nock';
import { Provider } from 'react-redux';
import { applyMiddleware, createStore, combineReducers } from 'redux';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import { merge } from 'merge-anything';
import thunk from 'redux-thunk';
import promiseMiddleware from 'redux-promise';
import waitForExpect from 'wait-for-expect';
import { waitFor } from '@testing-library/dom';
import { createWaitForElement } from 'enzyme-wait';
import http from 'http';
import StompServer from 'stomp-broker-js';

import hotkeys from '../../../test_setup/fixtures/hotkeys.json';
import keymap from '../../../test_setup/fixtures/keymap.json';
import { serverTestPort } from '../../../test_setup/jestSetup';
import { wrapInRouter } from '../../../test_setup/helpers';

import { ShortcutProvider } from '../../components/keyshortcuts/ShortcutProvider';
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
} from '../../reducers/tables';

import fixtures from '../../../test_setup/fixtures/master_window.json';
import dataFixtures from '../../../test_setup/fixtures/master_window/data.json';
import layoutFixtures from '../../../test_setup/fixtures/master_window/layout.json';
import rowFixtures from '../../../test_setup/fixtures/master_window/row_data.json';
import docActionFixtures from '../../../test_setup/fixtures/master_window/doc_action.json';
import topActionsFixtures from '../../../test_setup/fixtures/master_window/top_actions.json';
import menuFixtures from '../../../test_setup/fixtures/master_window/menu.json';
import userSessionData from '../../../test_setup/fixtures/user_session.json';
import notificationsData from '../../../test_setup/fixtures/notifications.json';

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
  const res = merge(
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

  return res;
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
      .get(`/window/${windowType}/${docId}/${tabId}/?orderBy=%2BLine`)
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
            <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
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
            <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
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
            <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
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
