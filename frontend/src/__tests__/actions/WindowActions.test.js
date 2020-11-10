import thunk from 'redux-thunk';
import nock from 'nock';
import configureStore from 'redux-mock-store';
import { Set } from 'immutable';
import merge from 'merge';

import { initialState } from '../../reducers/viewHandler';
import windowState from '../../reducers/windowHandler';

import {
  createWindow,
  handleProcessResponse,
} from '../../actions/WindowActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';
import { getScope, parseToDisplay } from '../../utils/documentListHelper';

import masterWindowProps from '../../../test_setup/fixtures/master_window.json';
import dataFixtures from '../../../test_setup/fixtures/master_window/data.json';
import layoutFixtures from '../../../test_setup/fixtures/master_window/layout.json';
import actionsFixtures from '../../../test_setup/fixtures/window/actions.json';
import { setProcessSaved, setProcessPending } from '../../actions/AppActions';

const createStore = function(state = {}) {
  const res = merge.recursive(
    true,
    {
      viewHandler: initialState,
      windowHandler: windowState,
    },
    state
  );

  return res;
};

describe('WindowActions thunks', () => {
  const propsData = masterWindowProps.props1;
  const middlewares = [thunk];
  const mockStore = configureStore(middlewares);

  describe('init', () => {
    it(`dispatches 'INIT_WINDOW' and 'INIT_DATA_SUCCESS' actions`, () => {
      const store = mockStore();
      const {
        params: { windowType, docId },
      } = propsData;
      const data = {
        data: {},
        docId: undefined,
        includedTabsInfo: undefined,
        scope: 'master',
        saveStatus: undefined,
        standardActions: undefined,
        validStatus: undefined,
        websocket: undefined,
      };
      const layoutResp = {
        windowId: '143',
        type: '143',
        caption: 'Sales Order',
        documentSummaryElement: { caption: '' },
        docActionElement: { caption: '' },
        sections: [{ columns: [{}], closableMode: 'ALWAYS_OPEN' }],
        tabs: [],
      };

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowType}/${docId}/`)
        .reply(200, [{ fieldsByName: {} }]);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowType}/layout`)
        .reply(200, layoutResp);

      const expectedActions = [
        { type: ACTION_TYPES.INIT_WINDOW },
        { type: ACTION_TYPES.INIT_DATA_SUCCESS, ...data },
      ];

      return store
        .dispatch(createWindow(windowType, docId, undefined, undefined, false))
        .then(() => {
          expect(store.getActions()).toEqual(
            expect.arrayContaining(expectedActions)
          );
        });
    });

    it(`dispatches 'SET_PROCESS_STATE_PENDING' action when setting process state pending`, () => {
      const expectedActions = [
        {
          type: ACTION_TYPES.SET_PROCESS_STATE_PENDING,
        },
      ];

      const store = mockStore();
      store.dispatch(setProcessPending());
      expect(store.getActions()).toEqual(
        expect.arrayContaining(expectedActions)
      );
    });

    it(`dispatches 'SET_PROCESS_STATE_SAVED' action when setting process state pending`, () => {
      const expectedActions = [
        {
          type: ACTION_TYPES.SET_PROCESS_STATE_SAVED,
        },
      ];

      const store = mockStore();
      store.dispatch(setProcessSaved());
      expect(store.getActions()).toEqual(
        expect.arrayContaining(expectedActions)
      );
    });

    it(`'handler response error in initWindow'`, () => {
      const store = mockStore();
      let {
        params: { windowType, docId },
      } = propsData;
      windowType = '123';
      const notFoundResp = {
        data: {},
        docId: 'notfound',
        includedTabsInfo: {},
        scope: 'master',
        saveStatus: { saved: true },
        standardActions: Set(),
        validStatus: {},
      };

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowType}/${docId}/`)
        .reply(404);

      const expectedActions = [
        { type: ACTION_TYPES.INIT_WINDOW },
        { type: ACTION_TYPES.INIT_DATA_SUCCESS, ...notFoundResp },
      ];

      return store
        .dispatch(createWindow(windowType, docId, undefined, undefined, false))
        .then(() => {
          expect(store.getActions()).toEqual(expectedActions);
        });
    });

    it(`properly loads request data for 'INIT_DATA_SUCCESS' actions`, () => {
      const store = mockStore();
      const dataResponse = dataFixtures.data1;
      const {
        params: { windowType, docId },
      } = propsData;
      const data = {
        data: parseToDisplay(dataResponse[0].fieldsByName),
        docId,
        saveStatus: dataResponse[0].saveStatus,
        scope: getScope(false),
        standardActions: dataResponse[0].standardActions,
        validStatus: dataResponse[0].validStatus,
        includedTabsInfo: dataResponse[0].includedTabsInfo,
        websocket: dataResponse[0].websocketEndpoint,
      };
      const layoutData = layoutFixtures.layout1;
      const tabId = layoutData.tabs[0].tabId;
      const tabsData = [
        {
          windowId: windowType,
          id: docId,
          tabId: tabId,
          tabid: tabId,
          rowId: '1',
          fieldsByName: {},
          validStatus: {},
          saveStatus: {},
          standardActions: [],
          websocketEndpoint: '',
        },
      ];

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowType}/${docId}/`)
        .reply(200, dataResponse);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowType}/layout`)
        .reply(200, layoutFixtures.layout1);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowType}/${docId}/${tabId}/`)
        .reply(200, tabsData);

      const expectedActions = [
        { type: ACTION_TYPES.INIT_WINDOW },
        { type: ACTION_TYPES.INIT_DATA_SUCCESS, ...data },
      ];

      return store
        .dispatch(createWindow(windowType, docId, undefined, undefined, false))
        .then(() => {
          expect(store.getActions()).toEqual(
            expect.arrayContaining(expectedActions)
          );
        });
    });

    it.todo(`dispatches 'UPDATE_TABLE' action when tab is fetched`);

    // @TODO: tests for NEW windows, NEW rows

    //@ TODO: loading top actions
  });

  describe('process', () => {
    it('sets included view in the store from a process', () => {
      const fixtures = actionsFixtures.processResponse;
      const { type, id, response } = fixtures;
      const state = createStore();
      const store = mockStore(state);

      const includedViewPayload = {
        id: response.action.windowId,
        viewId: response.action.viewId,
        viewProfileId: null,
      };

      const expectedActions = [
        { type: ACTION_TYPES.SET_INCLUDED_VIEW, payload: includedViewPayload },
        { type: ACTION_TYPES.SET_PROCESS_STATE_SAVED },
        { type: ACTION_TYPES.CLOSE_PROCESS_MODAL },
      ];

      return store
        .dispatch(handleProcessResponse({ data: response }, type, id))
        .then(() => {
          expect(store.getActions()).toEqual(
            expect.arrayContaining(expectedActions)
          );
        });
    });
  });
});
