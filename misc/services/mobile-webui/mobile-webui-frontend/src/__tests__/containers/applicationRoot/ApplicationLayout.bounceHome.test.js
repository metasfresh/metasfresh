import React from 'react';
import '@testing-library/jest-dom';
import { render, act } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import { ApplicationLayout } from '../../../containers/applicationRoot/ApplicationLayout';
import wfProcesses from '../../../reducers/wfProcesses/index';
import { updateWFProcess } from '../../../actions/WorkflowActions';

// Race B — "bounce a just-started workflow to home".
//
// On a workflow-launcher tap, WFLauncherButton dispatches updateWFProcess and then navigates to the
// job route in the same start-request `.then`. On React 17 / connected-react-router 6.9 /
// react-redux 7.2 those updates are not batched, so ApplicationLayout can mount on the job route
// one render pass BEFORE the store selector observes the just-dispatched process. The redirect-home
// guard used to call history.goHome() synchronously on that first pass, dropping the operator on the
// root menu mid-start (the flaky #WFProcessScreen timeout). The guard must tolerate the ordering:
// only redirect home if the process is STILL not loaded after a tick.
//
// This test drives that ordering deterministically with a real store: ApplicationLayout mounts while
// the process is absent (guard armed), then the just-started process arrives before the deferred tick
// fires. It must NOT bounce home and must render the workflow screen.

const WF_PROCESS_ID = 'picking-1000004';
const SCREEN_ID = 'WFProcessScreen';

const mockGoHome = jest.fn();
jest.mock('../../../hooks/useMobileNavigation', () => ({
  useMobileNavigation: () => ({
    goHome: mockGoHome,
    push: jest.fn(),
    replace: jest.fn(),
    goTo: jest.fn(),
    goBack: jest.fn(),
    go: jest.fn(),
    goToFromLocation: jest.fn(),
  }),
}));

// The job route carries a wfProcessId, so the redirect-home guard is armed.
jest.mock('../../../hooks/useMobileLocation', () => ({
  useMobileLocation: () => ({ wfProcessId: 'picking-1000004' }),
}));

jest.mock('../../../reducers/applications', () => ({
  useApplicationInfo: () => ({ caption: 'Picking', iconClassNames: 'fas fa-box' }),
}));

jest.mock('../../../reducers/headers', () => ({
  useNavigationInfoFromHeaders: () => ({
    screenId: 'WFProcessScreen',
    caption: 'Picking job',
    homeLocation: { iconClassName: 'fas fa-home', location: '/' },
  }),
  useBackLocationFromHeaders: () => null,
}));

// Non-fullscreen layout (the real WF screens render the #WFProcessScreen container the e2e gate waits on).
jest.mock('../../../apps', () => ({ isApplicationFullScreen: () => false }));

jest.mock('../../../utils/ui_trace/useUITraceLocationChange', () => ({
  useUITraceLocationChange: jest.fn(),
}));
jest.mock('../../../utils/ui_trace', () => ({
  traceFunction: (fn) => fn,
}));

// Heavy children are irrelevant to the guard; stub them to trivial nodes.
jest.mock('../../../containers/ViewHeader', () => ({ ViewHeader: () => null }));
jest.mock('../../../components/ScreenToaster', () => () => null);
jest.mock('../../../apps/picking/ShelfLifeConfirmDialogHost', () => () => null);

describe('ApplicationLayout: a just-started workflow must not be bounced to home (Race B)', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    jest.useFakeTimers();
  });
  afterEach(() => {
    jest.runOnlyPendingTimers();
    jest.useRealTimers();
  });

  it('does not call mockGoHome and renders the WF screen when the process is observed one tick later', () => {
    // Store starts empty: the just-dispatched process is not yet observable on the job route.
    const store = createStore(combineReducers({ wfProcesses }));

    const WFScreen = () => <div data-testid="wf-screen-content">workflow content</div>;

    // 1) ApplicationLayout mounts on the job route BEFORE the store observes the process.
    render(
      <Provider store={store}>
        <ApplicationLayout applicationId="picking" Component={WFScreen} />
      </Provider>
    );

    // 2) The just-started process becomes visible in the store (the start `.then` dispatch lands),
    //    still within the same tick — before any deferred redirect could fire.
    act(() => {
      store.dispatch(updateWFProcess({ wfProcess: { id: WF_PROCESS_ID, activities: [] }, parent: null }));
    });

    // 3) Flush any pending timers (a deferred-but-cancelled mockGoHome must not fire).
    act(() => {
      jest.runAllTimers();
    });

    // The operator stays on the job screen — no bounce home, screen rendered (not null).
    expect(mockGoHome).not.toHaveBeenCalled();
    expect(document.getElementById(SCREEN_ID)).not.toBeNull();
    expect(document.querySelector('[data-testid="wf-screen-content"]')).not.toBeNull();
  });

  it('still redirects home for a genuinely absent process (dead deep-link / reload)', () => {
    // The process never arrives — the guard must still bounce home once the deferred tick fires.
    const store = createStore(combineReducers({ wfProcesses }));

    const WFScreen = () => <div data-testid="wf-screen-content">workflow content</div>;

    render(
      <Provider store={store}>
        <ApplicationLayout applicationId="picking" Component={WFScreen} />
      </Provider>
    );

    act(() => {
      jest.runAllTimers();
    });

    expect(mockGoHome).toHaveBeenCalledTimes(1);
  });
});
