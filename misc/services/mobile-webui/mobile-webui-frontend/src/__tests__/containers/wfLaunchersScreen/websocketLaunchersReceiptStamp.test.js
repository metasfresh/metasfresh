import React from 'react';
import { render, act } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import { useLaunchers } from '../../../containers/wfLaunchersScreen/useLaunchers';
import wfProcesses from '../../../reducers/wfProcesses/index';
import launchers from '../../../reducers/launchers';
import { isWfProcessLoaded } from '../../../reducers/wfProcesses';
import { updateWFProcess } from '../../../actions/WorkflowActions';

// Companion to __tests__/reducers/wfProcesses/websocketLaunchersReceiptStampDeletesStartedProcess.test.js.
// That one pins the reducer contract; THIS one drives the REAL production code path: it captures the
// `onWebsocketMessage` callback that useLaunchers passes to useLaunchersWebsocket
// (containers/wfLaunchersScreen/useLaunchers.js) and invokes it, so whatever the websocket route
// dispatches is produced by production code, not mirrored by the test.
//
// Stamping a pushed snapshot with the browser's RECEIPT time makes one the server built BEFORE the job
// existed look newer than the job, so the wfProcesses garbage collector deletes it. Pushed snapshots
// therefore carry their own action type, which that reducer has no case for.
//
// Comparing the payload's server-side `computedTime` is not a usable alternative: the same DTO serialises
// it as an ISO-8601 string on the websocket and as an epoch-seconds float on REST, and on a handheld it
// would compare a SERVER instant against a BROWSER `Date.now()`. Do not introduce that comparison.
//
// Interleaving: the server computes the snapshot at T1 (job absent from it), the operator's start is stored
// at T2, the push is received at T3. The just-started wfProcess must survive.

const WF_PROCESS_ID = 'picking-1000004';
const T1_SERVER_COMPUTED_SNAPSHOT = 1000;
const T2_START_DISPATCHED = 2000;
const T3_WEBSOCKET_RECEIVED = 3000;

let capturedOnWebsocketMessage = null;

// The REST route must not interfere: getLaunchers never resolves, so the only launchers action
// dispatched in this test is the websocket one.
// NOTE: plain functions, not jest.fn(impl) -- create-react-app's jest preset sets
// `resetMocks: true`, which strips implementations passed to jest.fn().
jest.mock('../../../api/launchers', () => ({
  getLaunchers: () => new Promise(() => {}),
  useLaunchersWebsocket: ({ onWebsocketMessage }) => {
    capturedOnWebsocketMessage = onWebsocketMessage;
  },
}));

// Irrelevant to the race; keep them out of the store shape.
jest.mock('../../../reducers/applications', () => ({
  useApplicationInfo: () => ({ maxStartedLaunchers: 0, allowStartNextJobOnly: false }),
}));
jest.mock('../../../reducers/appHandler', () => ({
  getTokenFromState: () => 'test-user-token',
}));

const LaunchersScreenProbe = () => {
  useLaunchers({
    applicationId: 'picking',
    showFilterByQRCode: false,
    facets: null,
    filters: {},
    isEnabled: true,
  });
  return null;
};

describe('useLaunchers: a websocket push must not prune a process started after the snapshot was computed', () => {
  afterEach(() => {
    jest.restoreAllMocks();
    capturedOnWebsocketMessage = null;
  });

  it('keeps the just-started wfProcess when the pre-start snapshot is pushed after the start', () => {
    const store = createStore(combineReducers({ wfProcesses, launchers }));

    render(
      <Provider store={store}>
        <LaunchersScreenProbe />
      </Provider>
    );
    expect(typeof capturedOnWebsocketMessage).toBe('function');

    // 1) The operator taps the launcher; the start response is stored at T2.
    jest.spyOn(Date, 'now').mockReturnValue(T2_START_DISPATCHED);
    act(() => {
      store.dispatch(updateWFProcess({ wfProcess: { id: WF_PROCESS_ID, activities: [] }, parent: null }));
    });
    expect(isWfProcessLoaded(store.getState(), WF_PROCESS_ID)).toBe(true);

    // 2) The websocket push arrives at T3. Its payload was computed by the server at T1 -- before
    //    the start -- so it does not list the just-started job.
    Date.now.mockReturnValue(T3_WEBSOCKET_RECEIVED);
    act(() => {
      capturedOnWebsocketMessage({
        applicationId: 'picking',
        applicationLaunchers: {
          computedTime: new Date(T1_SERVER_COMPUTED_SNAPSHOT).toISOString(),
          launchers: [{ startedWFProcessId: null }, { startedWFProcessId: 'picking-9999999' }],
        },
      });
    });

    // The process must still be there -- otherwise ApplicationLayout renders null and bounces the
    // operator home mid-start (containers/applicationRoot/ApplicationLayout.jsx:34-55).
    expect(isWfProcessLoaded(store.getState(), WF_PROCESS_ID)).toBe(true);
  });

  // CONTROL: identical delivery, but the pushed snapshot DOES list the started job. It must survive
  // -- proving the captured callback really reaches the wfProcesses reducer and that the payload
  // shape (`launchers[].startedWFProcessId`) is modelled correctly, so the failure above is about
  // the timestamp source and nothing else.
  it('keeps the just-started wfProcess when the pushed snapshot does list it', () => {
    const store = createStore(combineReducers({ wfProcesses, launchers }));

    render(
      <Provider store={store}>
        <LaunchersScreenProbe />
      </Provider>
    );

    jest.spyOn(Date, 'now').mockReturnValue(T2_START_DISPATCHED);
    act(() => {
      store.dispatch(updateWFProcess({ wfProcess: { id: WF_PROCESS_ID, activities: [] }, parent: null }));
    });

    Date.now.mockReturnValue(T3_WEBSOCKET_RECEIVED);
    act(() => {
      capturedOnWebsocketMessage({
        applicationId: 'picking',
        applicationLaunchers: {
          computedTime: new Date(T3_WEBSOCKET_RECEIVED).toISOString(),
          launchers: [{ startedWFProcessId: WF_PROCESS_ID }],
        },
      });
    });

    expect(isWfProcessLoaded(store.getState(), WF_PROCESS_ID)).toBe(true);
  });
});
