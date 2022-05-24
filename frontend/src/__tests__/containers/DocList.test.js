import React from 'react';
import { act } from 'react-dom/test-utils';
import { mount } from 'enzyme';
import nock from 'nock';
import { Provider } from 'react-redux';
import { applyMiddleware, createStore, combineReducers } from 'redux';
import { Router } from 'react-router-dom';
import { createMemoryHistory } from 'history';
import waitForExpect from 'wait-for-expect';
import { waitFor } from '@testing-library/dom';
import { merge } from 'merge-anything';
import thunk from 'redux-thunk';
import promiseMiddleware from 'redux-promise';
import http from 'http';
import StompServer from 'stomp-broker-js';

import { ShortcutProvider } from '../../components/keyshortcuts/ShortcutProvider';
import { ProvideAuth } from '../../hooks/useAuth';
import { Routes } from '../../routes';
import { serverTestPort } from '../../../test_setup/jestSetup';

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
import filters, {
  initialState as filtersHandlerState,
} from '../../reducers/tables';
import actionsHandler, {
  getQuickActionsId,
  initialState as actionsHandlerState,
} from '../../reducers/actionsHandler';

import hotkeys from '../../../test_setup/fixtures/hotkeys.json';
import keymap from '../../../test_setup/fixtures/keymap.json';
import propsFixtures from '../../../test_setup/fixtures/doclist.json';
import dataFixtures from '../../../test_setup/fixtures/grid/doclist_data.json';
import layoutFixtures from '../../../test_setup/fixtures/grid/doclist_layout.json';
import rowFixtures from '../../../test_setup/fixtures/grid/doclist_row_data.json';
import userSessionData from '../../../test_setup/fixtures/user_session.json';
import notificationsData from '../../../test_setup/fixtures/notifications.json';
import quickActionsData from '../../../test_setup/fixtures/grid/doclist_quickactions.json';
import attributesData from '../../../test_setup/fixtures/attributes.json';


jest.mock(`../../components/app/QuickActions`);

// so that we don't have collision with other tests running in parallel
const serverPort = serverTestPort + 2;
global.config.WS_URL = `ws://localhost:${serverPort}/ws`;

// jest.useFakeTimers();

const middleware = [thunk];

localStorage.setItem('isLogged', true);

const rootReducer = combineReducers({
  appHandler,
  listHandler,
  viewHandler,
  menuHandler,
  windowHandler,
  pluginsHandler,
  tables,
  filters,
  actionsHandler,
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
      filters: { ...filtersHandlerState },
      actionsHandler: actionsHandlerState,
    },
    state
  );

  return res;
};

describe.skip('DocList', () => {
  const menuResponse = propsFixtures.menu1;

  let mockServer;
  let server;
  let history;

  beforeAll(() => {
    server = http.createServer();

    mockServer = new StompServer({
      server: server,
      path: '/ws',
    });

    server.listen(serverPort); // this is defined in the jestSetup file
  });

  // afterEach stop server
  afterAll(async () => {
    await server.close();
  });

  describe('included views grid', () => {
    const props = propsFixtures.props1;
    const history = createMemoryHistory({ initialEntries: [`/window/${props.windowId}`]} );

    it('renders without errors and loads quick actions', async () => {
      const initialState = createInitialState();
      const store = createStore(
        rootReducer,
        initialState,
        applyMiddleware(...middleware)
      );
      const windowId = props.windowId;
      const viewId = props.query.viewId;
      const attributes = attributesData.data2;
      const rowId = attributes.data.id;
      const data = rowFixtures.rowData1;
      const includedData = rowFixtures.includedViewData1;
      const includedWindowId = includedData.windowId;
      const includedViewId = includedData.viewId;

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/login/availableLanguages`)
        .reply(200, {});

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
        .get(`/documentView/${windowId}/layout?viewType=grid`)
        .reply(200, layoutFixtures.layout1);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get('/geolocation/config')
        .reply(200, []);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(
          `/menu/elementPath?type=window&elementId=${windowId}&inclusive=true`
        )
        .reply(200, menuResponse);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/documentView/${windowId}/${viewId}?firstRow=0&pageLength=20`)
        .reply(200, rowFixtures.rowData1);

      // included view
      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/documentView/${includedWindowId}/layout?viewType=includedView`)
        .reply(200, layoutFixtures.includedViewLayout1);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get('/geolocation/config')
        .reply(200, []);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .post(`/documentView/${windowId}`)
        .reply(200, dataFixtures.data1);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(
          `/documentView/${includedWindowId}/${includedViewId}?firstRow=0&pageLength=20`
        )
        .reply(200, rowFixtures.includedViewData1);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(
          `/documentView/${includedWindowId}/${includedViewId}/${rowId}/attributes/layout`
        )
        .reply(200, attributes.layout);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(
          `/documentView/${includedWindowId}/${includedViewId}/${rowId}/attributes`
        )
        .reply(200, attributes.data);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(
          `/documentView/${windowId}/${viewId}/quickActions?childViewId=${includedViewId}&childViewSelectedIds=${
            includedData.result[0].id}&selectedIds=${data.result[0].id
          }`
        )
        .reply(200, quickActionsData.parent_quickactions2);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(
          `/documentView/${includedWindowId}/${includedViewId}/quickActions?parentViewId=${viewId}&parentViewSelectedIds=${
            data.result[0].id}&selectedIds=${includedData.result[0].id
          }`
        )
        .reply(200, quickActionsData.included_quickactions);

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

      await act(async() => {
        wrapper.update();

        await waitFor(async () => {
          expect(store.getState().appHandler.me.loggedIn).toEqual(true);
          expect(Object.keys(store.getState().viewHandler.views).length).toBe(2);
        });
      });      

      await act(async() => {
        wrapper.update();

        await waitFor(async () => {
          expect(
            store.getState().viewHandler.views[includedWindowId].layoutPending
          ).toBeFalsy();
        });
      });

      waitForExpect(() => {
        const html = wrapper.html();
        expect(html).toContain('document-list-has-included');
        expect(html).toContain('document-list-is-included');
      }, 4000);

      const quickActionsId = getQuickActionsId({ windowId: includedWindowId, viewId: includedViewId });

      await act(async() => {
        waitFor(() => {
          expect(
            store.getState().actionsHandler[quickActionsId]
          ).toBeTruthy();
          expect(store.getState().windowHandler.indicator).toEqual('saved');
        });
      });   

    }, 20000);
  });
});
