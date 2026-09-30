import reducer from '../../../reducers/wfProcesses/index';
import { isWfProcessLoaded } from '../../../reducers/wfProcesses';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { populateLaunchersComplete, populateLaunchersPushedByServer } from '../../../actions/LauncherActions';

// The captured failure, replayed at the reducer level.
//
// A launchers snapshot that arrives by websocket PUSH may refresh the visible job list, but must never
// remove a workflow process from the store: a push carries no request-issued time, so nothing about it can
// prove a job is gone. Deleting therefore requires an explicitly REQUESTED snapshot, which stamps at
// request-issue time and so can never over-state its own freshness.
//
// Pushed snapshots carry their own action type, which reducers/wfProcesses/workflow.js has no case for,
// so a push cannot reach the pruning path at all.
//
// Comparing the payload's server-side `computedTime` is not a usable alternative: the same DTO serialises
// it as an ISO-8601 string on the websocket and as an epoch-seconds float on REST, and on a handheld it
// would compare a SERVER instant against a BROWSER `Date.now()`. Do not introduce that comparison.
//
// The numbers are the CAPTURED ones (capture/FAILING-RUN-probe-1788251568209-1.ndjson; INVESTIGATION.md),
// epoch ms, not round placeholders.
describe('wfProcesses: a pushed launchers snapshot must not prune a just-started process', () => {
  const WF_PROCESS_ID = 'picking-1000004';

  // The server computed the snapshot (payload computedTime 2026-09-01T08:32:55.868Z).
  const T_SNAPSHOT_BUILT = 1788251575868;
  // The job entered the store: 198 ms AFTER the snapshot was built, so the snapshot cannot know about it.
  const T_JOB_IN_STORE = 1788251576066;
  // The push was delivered: 9 ms after the job entered the store (the captured margin, +9 ms).
  const T_FRAME_RECEIVED = 1788251576075.6;

  // The snapshot predates the start, so it does not list WF_PROCESS_ID.
  const snapshotBuiltBeforeTheStart = {
    computedTime: new Date(T_SNAPSHOT_BUILT).toISOString(),
    launchers: [{ startedWFProcessId: null }, { startedWFProcessId: 'picking-9999999' }],
  };

  afterEach(() => {
    jest.restoreAllMocks();
  });

  const startTheJob = () => {
    jest.spyOn(Date, 'now').mockReturnValue(T_JOB_IN_STORE);
    const state = reducer(
      undefined,
      updateWFProcess({ wfProcess: { id: WF_PROCESS_ID, activities: [] }, parent: null })
    );
    expect(isWfProcessLoaded({ wfProcesses: state }, WF_PROCESS_ID)).toBe(true);
    return state;
  };

  // Control: the same snapshot on the REQUESTED route, stamped with the request-issue time, where the
  // guard in removeWFProcessesFromState keeps the job. It proves the harness models the reducer, the
  // action shape and the interleaving correctly, so a failure below is about the pushed route alone.
  it('keeps the just-started wfProcess when the same pre-start snapshot arrives as a REQUESTED snapshot', () => {
    let state = startTheJob();

    state = reducer(
      state,
      populateLaunchersComplete({
        applicationId: 'picking',
        applicationLaunchers: snapshotBuiltBeforeTheStart,
        requestTimestamp: T_SNAPSHOT_BUILT,
      })
    );

    expect(isWfProcessLoaded({ wfProcesses: state }, WF_PROCESS_ID)).toBe(true);
  });

  // The websocket route dispatches a PUSHED snapshot, which this reducer does not handle at all, so the
  // just-started process survives no matter when the frame was delivered.
  it('keeps the just-started wfProcess when the pre-start snapshot arrives as a PUSHED snapshot', () => {
    let state = startTheJob();

    jest.spyOn(Date, 'now').mockReturnValue(T_FRAME_RECEIVED);
    state = reducer(
      state,
      populateLaunchersPushedByServer({
        applicationId: 'picking',
        applicationLaunchers: snapshotBuiltBeforeTheStart,
      })
    );

    expect(isWfProcessLoaded({ wfProcesses: state }, WF_PROCESS_ID)).toBe(true);
  });
});
