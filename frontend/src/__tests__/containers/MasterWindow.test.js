import React from 'react';
import { mount } from 'enzyme';
import nock from 'nock';
import { Provider } from 'react-redux';
import { applyMiddleware, createStore, combineReducers } from 'redux';
import { routerReducer as routing } from 'react-router-redux';
import { createMemoryHistory } from 'react-router';
import merge from 'merge';
import thunk from 'redux-thunk';
import promiseMiddleware from 'redux-promise';
import waitForExpect from 'wait-for-expect';
import { waitFor } from '@testing-library/dom';
import { createWaitForElement } from 'enzyme-wait';
import hotkeys from '../../../test_setup/fixtures/hotkeys.json';
import keymap from '../../../test_setup/fixtures/keymap.json';

import http from 'http';
import StompServer from 'stomp-broker-js';

import { ShortcutProvider } from '../../components/keyshortcuts/ShortcutProvider';
import CustomRouter from '../../containers/CustomRouter';

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
import userSessionData from '../../../test_setup/fixtures/user_session.json';
import notificationsData from '../../../test_setup/fixtures/notifications.json';

const middleware = [thunk, promiseMiddleware];
const FIXTURES_PROPS = fixtures;
const history = createMemoryHistory('/window/143/1000000');

localStorage.setItem('isLogged', true);

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
};

describe('MasterWindowContainer', () => {
  describe('websocket tests', () => {
    let mockServer;
    let server;

    beforeAll(() => {
      server = http.createServer();

      mockServer = new StompServer({
        server: server,
        path: '/ws',
      });

      server.listen(8080);
    });

    // afterEach stop server
    afterAll(() => {
      server.close();
    });

    it('reacts to websocket events and updates the UI correctly', async (done) => {
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
        .reply(200, { result: rowFixtures.row_data1 });

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
        .get(`/window/${windowType}/${docId}/?noTabs=true`)
        .reply(200, dataFixtures.data1);

      const wrapper = await mount(
        <Provider store={store}>
          <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
            <CustomRouter history={history} auth={auth} />
          </ShortcutProvider>
        </Provider>
      );

      const msg = dataFixtures.websocketMessage1;

      // connection to the server takes some time, so we're waiting for the websocket url to be saved
      // in the store and then once the connection is open - push a websocket event from
      // the server
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

      // createWaitForElement('tbody')(wrapper).then(() => {
      //   expect(wrapper.find('tbody tr').length).toBe(7);
      // });
      // -- Commented the above code and replaced with below one that uses await waitForExpect because it seems that
      // the `createWaitForElement` is introducing that flaky issue
      await waitForExpect(() => {
        wrapper.update();
        expect(wrapper.find('tbody tr').length).toBe(7);
      });

      // wait for the DOM to be updated
      await waitFor(
        () => {
          wrapper.update();
          expect(wrapper.find('tbody tr').length).toBe(7);
        },
        { timeout: 8000, interval: 500 }
      );

      done();
    }, 20000);

    it('removes old and includes new rows on ws event', async (done) => {
      const initialState = createInitialState({
        routing: { ...fixtures.state2.routing },
      });
      const store = createStore(
        rootReducer,
        initialState,
        applyMiddleware(...middleware)
      );
      const localHistory = createMemoryHistory('/window/53009/1000000');

      const windowType = FIXTURES_PROPS.props2.params.windowType;
      const docId = FIXTURES_PROPS.props2.params.docId;
      const tabId = layoutFixtures.layout2.tabs[0].tabId;
      const updatedRows = rowFixtures.updated_row_data2;
      const auth = {
        initNotificationClient: jest.fn(),
        initSessionClient: jest.fn(),
      };
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
        .get(`/window/${windowType}/${docId}/?noTabs=true`)
        .reply(200, dataFixtures.data2);

      const wrapper = mount(
        <Provider store={store}>
          <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
            <CustomRouter history={localHistory} auth={auth} />
          </ShortcutProvider>
        </Provider>
      );

      // connection to the server takes some time, so we're waiting for the websocket url to be saved
      // in the store and then once the connection is open - push a websocket event from
      // the server
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

      // createWaitForElement('tbody')(wrapper).then((component) => {
      //   expect(wrapper.find('tbody tr').length).toBe(4);
      //   expect(wrapper.html()).toContain('288.86');
      // }); // see above comments regarding createWaitForElement why I commented this
      await waitForExpect(() => {
        wrapper.update();
        expect(wrapper.find('tbody tr').length).toBe(4);
        expect(wrapper.html()).toContain('288.86');
      });

      // wait for the DOM to be updated
      await waitFor(
        () => {
          wrapper.update();
          // expect(wrapper.find('tbody tr').length).toBe(4);
          expect(wrapper.html()).toContain('2,888.60');
        },
        { timeout: 8000, interval: 500 }
      );

      done();
    }, 20000);
  });

  describe("'integration' tests:", () => {
    it('renders without errors', async (done) => {
      const initialState = createInitialState();
      const store = createStore(
        rootReducer,
        initialState,
        applyMiddleware(...middleware)
      );
      const windowType = FIXTURES_PROPS.props1.params.windowType;
      const docId = FIXTURES_PROPS.props1.params.docId;
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
        .reply(200, { result: rowFixtures.row_data1 });

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

      let wrapper;
      try {
        wrapper = mount(
          <Provider store={store}>
            <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
              <CustomRouter history={history} auth={auth} />
            </ShortcutProvider>
          </Provider>
        );
      } catch (e) {
          console.log('e: ', e);
      }

      await waitForExpect(() => {
        try {
          wrapper.update();
        } catch (e) {
          console.log(e);
        }

        const html = wrapper.html();
        expect(html).toContain('<table');
      }, 6000);

      done();
    }, 10000);
  });
});
