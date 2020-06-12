import React from "react";
import * as Immutable from "immutable";
import { mount, shallow, render } from "enzyme";
import nock from 'nock';
import uuid from "uuid/v4";
import { Provider } from 'react-redux';
import { applyMiddleware, createStore, compose, combineReducers } from 'redux';
import configureStore from 'redux-mock-store';
import { routerReducer as routing } from 'react-router-redux';
import { createMemoryHistory } from 'react-router';
import merge from 'merge';
import thunk from 'redux-thunk';
import promiseMiddleware from 'redux-promise';
import waitForExpect from 'wait-for-expect';
import { waitFor } from '@testing-library/dom';
import { createWaitForElement } from 'enzyme-wait';

import http from 'http';
import StompServer from 'stomp-broker-js';

import { ShortcutProvider } from '../../components/keyshortcuts/ShortcutProvider';
import CustomRouter from '../../containers/CustomRouter';

import pluginsHandler, { initialState as pluginsHandlerState } from '../../reducers/pluginsHandler';
import appHandler, { initialState as appHandlerState } from '../../reducers/appHandler';
import windowHandler, { initialState as windowHandlerState } from '../../reducers/windowHandler';
import menuHandler, { initialState as menuHandlerState } from '../../reducers/menuHandler';
import listHandler, { initialState as listHandlerState } from '../../reducers/listHandler';
import viewHandler, { initialState as viewHandlerState } from '../../reducers/viewHandler';
import tables, { initialState as tablesHandlerState } from '../../reducers/tables';

import fixtures from "../../../test_setup/fixtures/master_window.json";
import dataFixtures from '../../../test_setup/fixtures/master_window/data.json';
import layoutFixtures from '../../../test_setup/fixtures/master_window/layout.json';
import rowFixtures from '../../../test_setup/fixtures/master_window/row_data.json';
import docActionFixtures from '../../../test_setup/fixtures/master_window/doc_action.json';
import topActionsFixtures from '../../../test_setup/fixtures/master_window/top_actions.json';
import userSessionData from '../../../test_setup/fixtures/user_session.json';
import notificationsData from '../../../test_setup/fixtures/notifications.json';

const mockStore = configureStore(middleware);
const middleware = [thunk, promiseMiddleware];
const FIXTURES_PROPS = fixtures.props1;
const history = createMemoryHistory('/window/143/1000000');

localStorage.setItem('isLogged', true)

const rootReducer = combineReducers({
  appHandler,
  listHandler,
  viewHandler,
  menuHandler,
  windowHandler,
  pluginsHandler,
  tables,
  routing,
});

const createInitialState = function(state = {}) {
  const res = merge.recursive(
    true,
    {
      appHandler: { ...appHandlerState },
      windowHandler: { ...windowHandlerState },
      listHandler: { ...listHandlerState },
      viewHandler: { ...viewHandlerState },
      menuHandler: { ...menuHandlerState },
      pluginsHandler: { ...pluginsHandlerState },
      tables: tablesHandlerState,
      routing: { ...fixtures.state1.routing },
    },
    state
  );

  return res;
}

describe("MasterWindowContainer", () => {
  describe("'integration' tests:", () => {
    it("renders without errors", async (done) => {
      const initialState = createInitialState();
      const store = createStore(
        rootReducer,
        initialState,
        applyMiddleware(...middleware),
      );
      const windowType = FIXTURES_PROPS.params.windowType;
      const docId = FIXTURES_PROPS.params.docId;
      const tabId = layoutFixtures.layout1.tabs[0].tabId;
      const auth = {
        initNotificationClient: jest.fn(),
        initSessionClient: jest.fn(),
      };

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
        .get(`/window/${windowType}/${docId}/${tabId}/`)
        .reply(200, rowFixtures.row_data1);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowType}/${docId}/${tabId}/?orderBy=%2BLine`)
        .reply(200, rowFixtures.row_data1);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowType}/${docId}/${tabId}/topActions`)
        .reply(200, topActionsFixtures.top_actions1);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowType}/${docId}/field/DocAction/dropdown`)
        .reply(200, docActionFixtures.data1);

      // This request doesn't happen here but in the `websockets` test. But component
      // created here still exists and thus tries to handle this XHR response.
      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowType}/${docId}/?noTabs=true`)
        .reply(200, dataFixtures.data1);

      const wrapper = mount(
        <Provider store={store}>
          <ShortcutProvider hotkeys={{}} keymap={{}} >
            <CustomRouter history={history} auth={auth} />
          </ShortcutProvider>
        </Provider>
      );

      await waitForExpect(() => {
        wrapper.update();

        const html = wrapper.html();
        expect(html).toContain('<table');
      }, 6000);

      done();
    }, 10000);
  });

  describe('websocket tests', () => {
    let mockServer;
    let server;
    
    beforeEach(() => {
      server = http.createServer();

      mockServer = new StompServer({
          server: server,
          path: '/ws',
          heartbeat: [1000,1000]
      });

      server.listen(8080);
    });

    // afterEach stop server
    afterEach(() => {
      server.close();
    });

    it("reacts to websocket events and updates the UI correctly", async done => {
      const initialState = createInitialState();
      const store = createStore(
        rootReducer,
        initialState,
        applyMiddleware(...middleware),
      );

      const windowType = FIXTURES_PROPS.params.windowType;
      const docId = FIXTURES_PROPS.params.docId;
      const tabId = layoutFixtures.layout1.tabs[0].tabId;
      const updatedRows = rowFixtures.updatedRow1;
      const auth = {
        initNotificationClient: jest.fn(),
        initSessionClient: jest.fn(),
      };

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
        .get(`/window/${windowType}/${docId}/${tabId}/`)
        .reply(200, rowFixtures.row_data1);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowType}/${docId}/${tabId}/?orderBy=%2BLine`)
        .reply(200, rowFixtures.row_data1);

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
        .get(`/window/${windowType}/${docId}/${tabId}?ids=${updatedRows[0].rowId}`)
        .reply(200, updatedRows);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowType}/${docId}/?noTabs=true`)
        .reply(200, dataFixtures.data1);

      const wrapper = mount(
        <Provider store={store}>
          <ShortcutProvider hotkeys={{}} keymap={{}} >
            <CustomRouter history={history} auth={auth} />
          </ShortcutProvider>
        </Provider>
      );

      const msg = dataFixtures.websocketMessage1;

      // connection to the server takes some time, so we're waiting for the websocket url to be saved
      // in the store and then once the connection is open - push a websocket event from
      // the server
      await waitFor(() => expect(store.getState().windowHandler.master.websocket).toBeTruthy())
        .then(() => {
          setTimeout(() => {
            mockServer.send(store.getState().windowHandler.master.websocket, {}, JSON.stringify(msg));
          }, 5000);
        });

      createWaitForElement('tbody')(wrapper)
        .then((component) => {
          expect(wrapper.find('tbody tr').length).toBe(7);
        });

      // wait for the DOM to be updated
      await waitFor(() => {
        wrapper.update();
        expect(wrapper.find('tbody tr').length).toBe(8);
      }, { timeout: 8000, interval: 500 });

      done();
    }, 20000);
  });
});
