import thunk from "redux-thunk";
import nock from "nock";
import configureStore from "redux-mock-store";
import { merge } from "merge-anything";

import { initialState as appInitialState } from "../../reducers/appHandler";
import { initialState } from "../../reducers/viewHandler";
import windowState from "../../reducers/windowHandler";

import {
  createWindow,
  resetPrintingOptions,
  setPrintingOptions,
  setSpinner,
  togglePrintingOption,
} from "../../actions/WindowActions";
import * as ACTION_TYPES from "../../constants/ActionTypes";
import { getScope, parseToDisplay } from "../../utils/documentListHelper";

import masterWindowProps from "../../../test_setup/fixtures/master_window.json";
import dataFixtures from "../../../test_setup/fixtures/master_window/data.json";
import layoutFixtures from "../../../test_setup/fixtures/master_window/layout.json";
import printingOptions from "../../../test_setup/fixtures/window/printingOptions.json";

const createState = (state = {}) =>
  merge(
    {
      viewHandler: initialState,
      windowHandler: windowState,
      appHandler: appInitialState,
    },
    state
  );

describe('WindowActions thunks', () => {
  const mockStore = configureStore([thunk]);

  describe('init', () => {
    it(`dispatches 'INIT_WINDOW' and 'INIT_DATA_SUCCESS' actions`, () => {
      nock(config.API_URL)
          .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
          .get(`/window/143/layout`)
          .reply(200, {
            windowId: '143',
            type: '143',
            caption: 'Sales Order',
            documentSummaryElement: { caption: '' },
            docActionElement: { caption: '' },
            sections: [{ columns: [{}], closableMode: 'ALWAYS_OPEN' }],
            tabs: [],
          });

      nock(config.API_URL)
          .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
          .get(`/window/143/1000000/`)
          .reply(200, [{ fieldsByName: {} }]);

      const store = mockStore();
      return store
        .dispatch(
          createWindow({
            windowId: "143",
            docId: "1000000",
            tabId: undefined,
            rowId: undefined,
            isModal: false,
          })
        )
        .then(() => {
          expect(store.getActions()).toEqual(
            expect.arrayContaining([
                { type: ACTION_TYPES.INIT_WINDOW },
                {
                  type: ACTION_TYPES.INIT_DATA_SUCCESS,
                  windowId: "143",
                  data: {},
                  docId: undefined,
                  hasComments: undefined,
                  includedTabsInfo: undefined,
                  notFoundMessage: undefined,
                  notFoundMessageDetail: undefined,
                  saveStatus: undefined,
                  scope: 'master',
                  standardActions: undefined,
                  validStatus: undefined,
                  websocket: undefined,
                },
                {
                  type: 'INIT_LAYOUT_SUCCESS',
                  layout: {
                    windowId: '143',
                    type: '143',
                    caption: 'Sales Order',
                    documentSummaryElement: {caption: ""},
                    docActionElement: {caption: ""},
                    sections: [{closableMode: "ALWAYS_OPEN", columns: [{}]}],
                    tabs: []
                  },
                  scope: 'master'
                }
            ])
          );
        });
    });

    it(`'handler response error in initWindow when layout not found'`, () => {
      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/123/layout`)
        .reply(404);

      const store = mockStore();
      return store
        .dispatch(
          createWindow({
            windowId: '123',
            docId: '1000000',
            tabId: undefined,
            rowId: undefined,
            isModal: false,
          })
        )
        .then(() => {
          expect(store.getActions()).toEqual([
            { type: ACTION_TYPES.INIT_WINDOW },
            {
              type: ACTION_TYPES.INIT_DATA_SUCCESS,
              data: {},
              docId: 'notfound',
              includedTabsInfo: {},
              scope: 'master',
              saveStatus: { saved: true },
              standardActions: [],
              validStatus: {},
            },
          ]);
        });
    });

    it(`'handler response error in initWindow when data not found'`, () => {
      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/143/layout`)
        .reply(200, {
          windowId: '143',
          type: '143',
          caption: 'Sales Order',
          documentSummaryElement: { caption: '' },
          docActionElement: { caption: '' },
          notFoundMessage: 'Not Found Title',
          notFoundMessageDetail: 'Not Found Detail',
          sections: [{ columns: [{}], closableMode: 'ALWAYS_OPEN' }],
          tabs: [],
        });
      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/143/999/`)
        .reply(404);

      const store = mockStore();
      return store
        .dispatch(
          createWindow({
            windowId: '143',
            docId: '999',
            tabId: undefined,
            rowId: undefined,
            isModal: false,
          })
        )
        .then(() => {
          expect(store.getActions()).toEqual([
            { type: ACTION_TYPES.INIT_WINDOW },
            {
              type: ACTION_TYPES.INIT_DATA_SUCCESS,
              data: {},
              docId: 'notfound',
              hasComments: undefined,
              includedTabsInfo: {},
              notFoundMessage: 'Not Found Title',
              notFoundMessageDetail: 'Not Found Detail',
              saveStatus: { saved: true },
              scope: 'master',
              standardActions: [],
              validStatus: {},
              websocket: undefined
            },
          ]);
        });
    });

    it(`properly loads request data for 'INIT_DATA_SUCCESS' actions`, () => {
      const dataResponse = dataFixtures.data1;
      const {
        params: { windowType: windowId, docId },
      } = masterWindowProps.props1;
      const layoutResponse = layoutFixtures.layout1;
      const tabId = layoutResponse.tabs[0].tabId;
      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowId}/${docId}/`)
        .reply(200, dataResponse);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowId}/layout`)
        .reply(200, layoutResponse);

      nock(config.API_URL)
        .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
        .get(`/window/${windowId}/${docId}/${tabId}/`)
        .reply(200, [
          {
            windowId,
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
        ]);

      const store = mockStore();
      return store
        .dispatch(
          createWindow({
            windowId,
            docId,
            tabId: undefined,
            rowId: undefined,
            isModal: false,
          })
        )
        .then(() => {
          expect(store.getActions()).toEqual(
            expect.arrayContaining([
              { type: ACTION_TYPES.INIT_WINDOW },
              {
                type: ACTION_TYPES.INIT_DATA_SUCCESS,
                windowId,
                data: parseToDisplay(dataResponse[0].fieldsByName),
                docId,
                saveStatus: dataResponse[0].saveStatus,
                scope: getScope(false),
                standardActions: dataResponse[0].standardActions,
                validStatus: dataResponse[0].validStatus,
                includedTabsInfo: dataResponse[0].includedTabsInfo,
                websocket: dataResponse[0].websocketEndpoint,
              },
            ])
          );
        });
    });

    it.todo(`dispatches 'UPDATE_TABLE' action when tab is fetched`);

    // @TODO: tests for NEW windows, NEW rows

    //@ TODO: loading top actions
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
