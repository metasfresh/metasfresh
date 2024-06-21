import thunk from 'redux-thunk';
import nock from 'nock';
import configureStore from 'redux-mock-store';
import { merge } from 'merge-anything';

import { initialState as appInitialState } from '../../reducers/appHandler';
import { initialState } from '../../reducers/viewHandler';
import windowState from '../../reducers/windowHandler';

import {
  createWindow,
  handleProcessResponse,
  createProcess,
} from '../../actions/WindowActions';
import * as ACTION_TYPES from '../../constants/ActionTypes';
import { getScope, parseToDisplay } from '../../utils/documentListHelper';

import masterWindowProps from '../../../test_setup/fixtures/master_window.json';
import dataFixtures from '../../../test_setup/fixtures/master_window/data.json';
import layoutFixtures from '../../../test_setup/fixtures/master_window/layout.json';
import actionsFixtures from '../../../test_setup/fixtures/process/actions.json';
import processResponseFixtures from '../../../test_setup/fixtures/process/responses.json';
import processParameterFixtures from '../../../test_setup/fixtures/process/parameters.json';
import processStateFixtures from '../../../test_setup/fixtures/process/store.json';
import printingOptions from '../../../test_setup/fixtures/window/printingOptions.json';
import { setProcessSaved, setProcessPending } from '../../actions/AppActions';
import {
  setPrintingOptions,
  resetPrintingOptions,
  togglePrintingOption,
  setSpinner,
} from '../../actions/WindowActions';

const createState = function (state = {}) {
  const res = merge(
    {
      viewHandler: initialState,
      windowHandler: windowState,
      appHandler: appInitialState,
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

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(
          `/menu/elementPath?type=window&elementId=${windowType}&inclusive=true`
        )
        .reply(200, dataFixtures.breadcrumbs1);

      const expectedActions = [
        { type: ACTION_TYPES.INIT_WINDOW },
        { type: ACTION_TYPES.INIT_DATA_SUCCESS, ...data },
      ];

      return store
        .dispatch(
          createWindow({
            windowId: windowType,
            docId,
            tabId: undefined,
            rowId: undefined,
            isModal: false,
          })
        )
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
        standardActions: [],
        validStatus: {},
      };

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowType}/${docId}/`)
        .reply(404);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(
          `/menu/elementPath?type=window&elementId=${windowType}&inclusive=true`
        )
        .reply(200, dataFixtures.breadcrumbs1);

      const expectedActions = [
        { type: ACTION_TYPES.INIT_WINDOW },
        { type: ACTION_TYPES.INIT_DATA_SUCCESS, ...notFoundResp },
      ];

      return store
        .dispatch(
          createWindow({
            windowId: windowType,
            docId,
            tabId: undefined,
            rowId: undefined,
            isModal: false,
          })
        )
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

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(
          `/menu/elementPath?type=window&elementId=${windowType}&inclusive=true`
        )
        .reply(200, dataFixtures.breadcrumbs1);

      const expectedActions = [
        { type: ACTION_TYPES.INIT_WINDOW },
        { type: ACTION_TYPES.INIT_DATA_SUCCESS, ...data },
      ];

      return store
        .dispatch(
          createWindow({
            windowId: windowType,
            docId,
            tabId: undefined,
            rowId: undefined,
            isModal: false,
          })
        )
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
    it('create a process opening an included view in a raw modal', () => {
      const { pid, processType, parentId } = actionsFixtures.processData3;
      const { viewHandler } = processStateFixtures.modal_included1;
      const processParameters = processParameterFixtures.modal_included1;
      const processStartResponse = actionsFixtures.processResponse3;
      const { windowId, viewId } = processStartResponse.action;
      const processDataResponse = processResponseFixtures.modal_included1;
      const state = createState({ viewHandler });
      const store = mockStore(state);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .post(`/process/${processType}`)
        .reply(200, processDataResponse);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/process/${processType}/${pid}/start`)
        .reply(200, processStartResponse);

      const includedViewPayload = {
        id: windowId,
        viewId: viewId,
        viewProfileId: null,
        parentId,
      };

      const expectedActions = [
        { type: ACTION_TYPES.SET_PROCESS_STATE_PENDING },
        { type: ACTION_TYPES.SET_INCLUDED_VIEW, payload: includedViewPayload },
        { type: ACTION_TYPES.SET_PROCESS_STATE_SAVED },
        { type: ACTION_TYPES.CLOSE_PROCESS_MODAL },
      ];

      return store.dispatch(createProcess(processParameters)).then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining(expectedActions)
        );
      });
    });

    it('create a process opening an included view in a raw modal with included view', () => {
      const { pid, processType, parentId } = actionsFixtures.processData4;
      const { viewHandler } = processStateFixtures.modal_included2;
      const processParameters = processParameterFixtures.modal_included2;
      const processStartResponse = actionsFixtures.processResponse4;
      const { windowId, viewId, profileId } = processStartResponse.action;
      const processDataResponse = processResponseFixtures.modal_included2;
      const state = createState({ viewHandler });
      const store = mockStore(state);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .post(`/process/${processType}`)
        .reply(200, processDataResponse);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/process/${processType}/${pid}/start`)
        .reply(200, processStartResponse);

      const includedViewPayload = {
        id: windowId,
        viewId,
        viewProfileId: profileId,
        parentId,
      };

      const expectedActions = [
        { type: ACTION_TYPES.SET_PROCESS_STATE_PENDING },
        { type: ACTION_TYPES.SET_INCLUDED_VIEW, payload: includedViewPayload },
        { type: ACTION_TYPES.SET_PROCESS_STATE_SAVED },
        { type: ACTION_TYPES.CLOSE_PROCESS_MODAL },
      ];

      return store.dispatch(createProcess(processParameters)).then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining(expectedActions)
        );
      });
    });

    it('opens view in the modal from a process', () => {
      const { pid, processType, parentId } = actionsFixtures.processData1;
      const { action } = actionsFixtures.processResponse1;
      const { windowId, viewId } = action;
      const state = createState();
      const store = mockStore(state);

      const expectedActions = [
        { type: ACTION_TYPES.CLOSE_MODAL },
        { type: ACTION_TYPES.OPEN_RAW_MODAL, windowId, viewId },
        { type: ACTION_TYPES.SET_PROCESS_STATE_SAVED },
        { type: ACTION_TYPES.CLOSE_PROCESS_MODAL },
      ];

      return store
        .dispatch(
          handleProcessResponse({ data: { action } }, processType, pid, parentId )
        )
        .then(() => {
          expect(store.getActions()).toEqual(
            expect.arrayContaining(expectedActions)
          );
        });
    });

    it('sets included view in the store from a process', () => {
      const { pid, processType, parentId } = actionsFixtures.processData2;
      const { action } = actionsFixtures.processResponse2;
      const { windowId, viewId, profileId } = action;
      const state = createState();
      const store = mockStore(state);

      const includedViewPayload = {
        id: windowId,
        viewId: viewId,
        viewProfileId: profileId,
        parentId,
      };

      const expectedActions = [
        { type: ACTION_TYPES.SET_INCLUDED_VIEW, payload: includedViewPayload },
        { type: ACTION_TYPES.SET_PROCESS_STATE_SAVED },
        { type: ACTION_TYPES.CLOSE_PROCESS_MODAL },
      ];

      return store
        .dispatch(
          handleProcessResponse({ data: { action } }, processType, pid, parentId )
        )
        .then(() => {
          expect(store.getActions()).toEqual(
            expect.arrayContaining(expectedActions)
          );
        });
    });
  });

  describe('Printing Actions', () => {
    it('setting printing options in the store', () => {
      const state = createState();
      const store = mockStore(state);
      const expectedAction = [
        { type: ACTION_TYPES.SET_PRINTING_OPTIONS, payload: printingOptions },
      ];

      store.dispatch(setPrintingOptions(printingOptions));
      expect(store.getActions()).toEqual(expectedAction);
    });

    it('reset printing options is called', () => {
      const state = createState();
      const store = mockStore(state);
      const expectedAction = [{ type: ACTION_TYPES.RESET_PRINTING_OPTIONS }];

      store.dispatch(resetPrintingOptions());
      expect(store.getActions()).toEqual(expectedAction);
    });

    it('triggers action to toggle the printing option', () => {
      const state = createState();
      const store = mockStore(state);
      const expectedAction = [
        {
          type: ACTION_TYPES.TOGGLE_PRINTING_OPTION,
          payload: 'PRINTER_OPTS_IsPrintLogo',
        },
      ];

      store.dispatch(togglePrintingOption('PRINTER_OPTS_IsPrintLogo'));
      expect(store.getActions()).toEqual(expectedAction);
    });

    it('triggers action to set the showSpinner option', () => {
      const state = createState();
      const store = mockStore(state);
      const expectedAction = [
        {
          type: ACTION_TYPES.SET_SPINNER,
          payload: true,
        },
      ];

      store.dispatch(setSpinner(true));
      expect(store.getActions()).toEqual(expectedAction);
    });
  });
});
