import nock from 'nock';
import * as ACTION_TYPES from '../../constants/ActionTypes';
import { merge } from 'merge-anything';
import thunk from 'redux-thunk';
import configureStore from 'redux-mock-store';
import { initialState } from '../../reducers/viewHandler';
import { initialState as appInitialState } from '../../reducers/appHandler';
import {
  createProcess,
  handleProcessResponse
} from '../../actions/ProcessActions';
import { setProcessPending, setProcessSaved } from '../../actions/AppActions';

const createState = (state = {}) => merge(
  {
    viewHandler: initialState,
    appHandler: appInitialState,
  },
  state
);

describe('WindowActions thunks', () => {
  const mockStore = configureStore([thunk]);

  it(`dispatches 'SET_PROCESS_STATE_PENDING' action when setting process state pending`, () => {
    const store = mockStore();
    store.dispatch(setProcessPending());
    expect(store.getActions()).toEqual(
      expect.arrayContaining([{ type: ACTION_TYPES.SET_PROCESS_STATE_PENDING }])
    );
  });

  it(`dispatches 'SET_PROCESS_STATE_SAVED' action when setting process state pending`, () => {
    const store = mockStore();
    store.dispatch(setProcessSaved());
    expect(store.getActions()).toEqual(
      expect.arrayContaining([{ type: ACTION_TYPES.SET_PROCESS_STATE_SAVED }])
    );
  });

  it('create a process opening an included view in a raw modal', () => {
    const store = mockStore(createState({
      viewHandler: {
        modals: {
          "540328": {
            "viewId": "540328-g",
            "windowId": "540328",
          }
        }
      }
    }));

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/process/ADP_540871`)
      .reply(200, {
        "pinstanceId": "1195364",
        "fieldsByName": {},
        "startProcessDirectly": true
      });

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/process/ADP_540871/1195364/start`)
      .reply(200, {
        "pinstanceId": "1195364",
        "error": false,
        "action": {
          "type": "openIncludedView",
          "viewType": "includedView",
          "windowId": "540189",
          "viewId": "540189-h",
        }
      });

    return store.dispatch(createProcess({
      "ids": ["PP_Order_BOMLine-null-1000003"],
      "processType": "ADP_540871",
      "documentType": "53009",
      "viewId": "540328-g",
      "selectedTab": {
        "tabId": "AD_Tab-53040",
        "rowIds": ["PP_Order_BOMLine-null-1000003"]
      }
    }))
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            { type: ACTION_TYPES.SET_PROCESS_STATE_PENDING },
            {
              type: ACTION_TYPES.SET_INCLUDED_VIEW,
              payload: {
                id: "540189", // windowId
                viewId: "540189-h",
                viewProfileId: null,
                parentId: "540328",
              }
            },
            { type: ACTION_TYPES.SET_PROCESS_STATE_SAVED },
            { type: ACTION_TYPES.CLOSE_PROCESS_MODAL },
          ])
        );
      });
  });

  it('create a process opening an included view in a raw modal with included view', () => {
    const store = mockStore(createState({
      "viewHandler": {
        "modals": {
          "540350": {
            "viewId": "540350-q",
            "windowId": "540350",
          }
        },
      }
    }));

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .post(`/process/ADP_540809`)
      .reply(200, {
        "pinstanceId": "1195720",
        "fieldsByName": {},
        "startProcessDirectly": true
      });

    nock(config.API_URL)
      .defaultReplyHeaders({ 'access-control-allow-origin': '*' })
      .get(`/process/ADP_540809/1195720/start`)
      .reply(200, {
        "pinstanceId": "1195720",
        "error": false,
        "action": {
          "windowId": "husToPick",
          "viewId": "husToPick-s",
          "viewType": "includedView",
          "profileId": "husToPick",
          "type": "openIncludedView"
        }
      });

    return store.dispatch(
      createProcess({
        "ids": ["1000126"],
        "processType": "ADP_540809",
        "rowId": "1003027",
        "documentType": "540345",
        "viewId": "pickingSlot-q-1003027",
        "parentViewId": "540350-q",
        "parentViewSelectedIds": ["1003027"]
      }))
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            { type: ACTION_TYPES.SET_PROCESS_STATE_PENDING },
            {
              type: ACTION_TYPES.SET_INCLUDED_VIEW,
              payload: {
                id: "husToPick", // windowId
                viewId: "husToPick-s",
                viewProfileId: "husToPick",
                parentId: "540350",
              }
            },
            { type: ACTION_TYPES.SET_PROCESS_STATE_SAVED },
            { type: ACTION_TYPES.CLOSE_PROCESS_MODAL },
          ])
        );
      });
  });

  it('opens view in the modal from a process', () => {
    const store = mockStore(createState());

    return store.dispatch(
      handleProcessResponse(
        {
          data: {
            action: {
              "windowId": "540759",
              "viewId": "540759-M",
              "targetTab": "SAME_TAB_OVERLAY",
              "type": "openView"
            }
          }
        },
        "ADP_541214",
        "1135474",
        "195"
      ))
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            { type: ACTION_TYPES.CLOSE_MODAL },
            {
              type: ACTION_TYPES.OPEN_RAW_MODAL,
              windowId: "540759",
              viewId: "540759-M"
            },
            { type: ACTION_TYPES.SET_PROCESS_STATE_SAVED },
            { type: ACTION_TYPES.CLOSE_PROCESS_MODAL },
          ])
        );
      });
  });

  it('sets included view in the store from a process', () => {
    const store = mockStore(createState());

    return store.dispatch(
      handleProcessResponse(
        {
          data: {
            action: {
              "windowId": "husToPick",
              "viewId": "husToPick-4",
              "viewType": "includedView",
              "profileId": "husToPick",
              "type": "openIncludedView"
            }
          }
        },
        "ADP_540801",
        "1135949",
        "540345"
      ))
      .then(() => {
        expect(store.getActions()).toEqual(
          expect.arrayContaining([
            {
              type: ACTION_TYPES.SET_INCLUDED_VIEW,
              payload: {
                id: "husToPick",
                viewId: "husToPick-4",
                viewProfileId: "husToPick",
                parentId: "540345",
              }
            },
            { type: ACTION_TYPES.SET_PROCESS_STATE_SAVED },
            { type: ACTION_TYPES.CLOSE_PROCESS_MODAL },
          ])
        );
      });
  });
});
