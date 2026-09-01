import reducer from '../../../reducers/wfProcesses/index';
import launchersReducer from '../../../reducers/launchers';
import { isWfProcessLoaded } from '../../../reducers/wfProcesses';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { populateLaunchersPushed } from '../../../actions/LauncherActions';

// AC1, the primary regression criterion, stated as an INVARIANT: a launchers snapshot that arrives by
// websocket PUSH never removes a workflow process from the store -- for ANY timestamp values, including
// ones that would satisfy today's `requestTimestamp >= updatedAt` guard, and including the harshest
// payload of all (an empty launchers list, which on the old path deleted every process in the store).
//
// The two companion suites each pin exactly ONE interleaving (the captured one), so neither proves the
// invariant; this one parametrises over the timestamp matrix and asserts the wfProcesses state is
// UNCHANGED, not merely that one id survived.
//
// The fix gives pushed snapshots their own action type (POPULATE_LAUNCHERS_PUSHED, dispatched by
// actions/LauncherActions.populateLaunchersPushed) which reducers/wfProcesses/workflow.js deliberately
// does NOT handle -- so a push structurally cannot prune, whatever the clocks say. Before the fix that
// action creator does not exist, so every test below fails with "populateLaunchersPushed is not a
// function" -- the RED.
//
// NOT the fix: comparing the payload's server-side `computedTime`. REQUIREMENTS.md section 7 rejects it
// (two wire formats -- ISO-8601 on the websocket, epoch-seconds float on REST -- and a server instant
// compared against a browser Date.now() on a real handheld). Do not reintroduce that comparison.
//
// AC3 (a REQUESTED snapshot must still garbage-collect) is NOT retested here: it is already covered by
// staleLaunchersDeletesStartedProcess.test.js, which must stay green after the fix.
describe('wfProcesses: a PUSHED launchers snapshot never deletes, for ANY timestamps', () => {
  const WF_PROCESS_ID = 'picking-1000004';

  // Captured epoch ms (capture/FAILING-RUN-probe-1788251568209-1.ndjson; INVESTIGATION.md).
  // The server built the snapshot at T_SNAPSHOT_BUILT, 198 ms before the job entered the store.
  const T_SNAPSHOT_BUILT = 1788251575868;
  const T_JOB_IN_STORE = 1788251576066;

  const COMPUTED_BEFORE_THE_JOB = new Date(T_SNAPSHOT_BUILT).toISOString();

  // A pre-start snapshot: it was built before the job existed, so it does not list WF_PROCESS_ID.
  const launchersWithoutTheJob = [{ startedWFProcessId: null }, { startedWFProcessId: 'picking-9999999' }];

  afterEach(() => {
    jest.restoreAllMocks();
  });

  it.each([
    [
      "frame delivered long after the job (would defeat today's guard)",
      {
        computedTime: COMPUTED_BEFORE_THE_JOB,
        frameDeliveredAt: T_JOB_IN_STORE + 5000,
        launchers: launchersWithoutTheJob,
      },
    ],
    [
      'the captured margin (+9 ms)',
      {
        computedTime: COMPUTED_BEFORE_THE_JOB,
        frameDeliveredAt: T_JOB_IN_STORE + 9.6,
        launchers: launchersWithoutTheJob,
      },
    ],
    [
      'frame and job in the same millisecond',
      { computedTime: COMPUTED_BEFORE_THE_JOB, frameDeliveredAt: T_JOB_IN_STORE, launchers: launchersWithoutTheJob },
    ],
    [
      'frame delivered before the job',
      {
        computedTime: COMPUTED_BEFORE_THE_JOB,
        frameDeliveredAt: T_JOB_IN_STORE - 200,
        launchers: launchersWithoutTheJob,
      },
    ],
    [
      'payload carries no computedTime at all',
      { computedTime: undefined, frameDeliveredAt: T_JOB_IN_STORE + 5000, launchers: launchersWithoutTheJob },
    ],
    [
      'empty launchers list (would prune everything)',
      { computedTime: COMPUTED_BEFORE_THE_JOB, frameDeliveredAt: T_JOB_IN_STORE + 5000, launchers: [] },
    ],
  ])('leaves the store untouched: %s', (_caseName, { computedTime, frameDeliveredAt, launchers }) => {
    // The operator's start is stored; `updatedAt` is stamped from Date.now() by the action creator.
    jest.spyOn(Date, 'now').mockReturnValue(T_JOB_IN_STORE);
    const stateBeforeThePush = reducer(
      undefined,
      updateWFProcess({ wfProcess: { id: WF_PROCESS_ID, activities: [] }, parent: null })
    );
    expect(isWfProcessLoaded({ wfProcesses: stateBeforeThePush }, WF_PROCESS_ID)).toBe(true);

    // A structural copy, so the comparison below cannot be satisfied by the reducer simply handing back
    // the very same object it was given.
    const snapshotOfStateBeforeThePush = JSON.parse(JSON.stringify(stateBeforeThePush));

    // The frame is delivered.
    Date.now.mockReturnValue(frameDeliveredAt);
    const applicationLaunchers = computedTime === undefined ? { launchers } : { computedTime, launchers };
    const stateAfterThePush = reducer(
      stateBeforeThePush,
      populateLaunchersPushed({ applicationId: 'picking', applicationLaunchers })
    );

    expect(stateAfterThePush).toEqual(snapshotOfStateBeforeThePush);
  });

  // The push must keep doing its job: only its ability to PRUNE is removed, not its ability to refresh
  // what the operator sees. Fails now for the same missing-action-creator reason.
  it('still refreshes the visible launcher list', () => {
    const state = {
      launchers: launchersReducer(
        undefined,
        populateLaunchersPushed({
          applicationId: 'picking',
          applicationLaunchers: { computedTime: COMPUTED_BEFORE_THE_JOB, launchers: launchersWithoutTheJob },
        })
      ),
    };

    expect(state.launchers.picking.list).toEqual(launchersWithoutTheJob);
  });
});
