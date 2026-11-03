import reducer from '../../../reducers/wfProcesses/index';
import { isWfProcessLoaded } from '../../../reducers/wfProcesses';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { POPULATE_LAUNCHERS_COMPLETE } from '../../../constants/LaunchersActionTypes';

// A launchers refresh that was computed BEFORE a workflow is started can resolve AFTER the start's
// UPDATE_WORKFLOW_PROCESS. The POPULATE_LAUNCHERS_COMPLETE reducer
// (reducers/wfProcesses/workflow.js -> removeWFProcessesFromState) prunes wfProcesses that are not
// in the snapshot. It must prune ONLY when the launchers snapshot was fetched strictly AFTER a
// process was last updated locally; a snapshot that predates the start cannot be authoritative
// about it. Otherwise ApplicationLayout sees isWfProcessLoaded===false and redirects the operator
// to the home menu mid-flow (the observed job-start timeout landing on the root menu).
//
// updateWFProcess stamps `timestamp: Date.now()` in its action creator and populateLaunchersComplete
// threads the launchers fetch-start time through as `requestTimestamp`. We drive Date.now() so the
// two tests model the real ordering deterministically (no wall-clock flakiness).
describe('wfProcesses: POPULATE_LAUNCHERS_COMPLETE prunes only genuinely stale processes', () => {
  afterEach(() => {
    jest.restoreAllMocks();
  });

  it('keeps the just-started wfProcess when a pre-start launchers snapshot resolves after the start', () => {
    const wfProcessId = 'picking-1000004';

    // The launchers refresh was ISSUED before the operator tapped the launcher.
    const launchersFetchStartedAt = 1000;

    // 1) Operator taps the launcher; startWorkflowRequest resolves and the app stores the process.
    //    Its local update time (2000) is AFTER the launchers fetch was issued.
    jest.spyOn(Date, 'now').mockReturnValue(2000);
    let state = reducer(undefined, updateWFProcess({ wfProcess: { id: wfProcessId, activities: [] }, parent: null }));
    expect(isWfProcessLoaded({ wfProcesses: state }, wfProcessId)).toBe(true);

    // 2) The pre-start launchers refresh now completes. Its snapshot predates the start, so it does
    //    NOT list wfProcessId as a startedWFProcessId — but it also predates the local update.
    state = reducer(state, {
      type: POPULATE_LAUNCHERS_COMPLETE,
      payload: {
        requestTimestamp: launchersFetchStartedAt,
        applicationLaunchers: {
          launchers: [
            { startedWFProcessId: null },
            { startedWFProcessId: 'picking-9999999' }, // some other, unrelated started job
          ],
        },
      },
    });

    // The started process must survive — otherwise ApplicationLayout redirects the operator home
    // mid-flow (the observed job-start timeout landing on the root menu).
    expect(isWfProcessLoaded({ wfProcesses: state }, wfProcessId)).toBe(true);
  });

  it('still removes a process that is older than the launchers snapshot and absent from it (GC)', () => {
    const wfProcessId = 'picking-1000004';

    // 1) A process was stored at t=1000.
    jest.spyOn(Date, 'now').mockReturnValue(1000);
    let state = reducer(undefined, updateWFProcess({ wfProcess: { id: wfProcessId, activities: [] }, parent: null }));
    expect(isWfProcessLoaded({ wfProcesses: state }, wfProcessId)).toBe(true);

    // 2) A FRESH launchers refresh, issued at t=2000 (strictly after the process update), no longer
    //    lists the process (it was finished/aborted server-side). It IS authoritative here.
    state = reducer(state, {
      type: POPULATE_LAUNCHERS_COMPLETE,
      payload: {
        requestTimestamp: 2000,
        applicationLaunchers: {
          launchers: [{ startedWFProcessId: 'picking-9999999' }],
        },
      },
    });

    // The legitimate garbage-collection must still happen: the fix must not just disable pruning.
    expect(isWfProcessLoaded({ wfProcesses: state }, wfProcessId)).toBe(false);
  });

  it('keeps the process when its update time EQUALS the launchers fetch-start time (>= tie-break)', () => {
    const wfProcessId = 'picking-1000004';

    // Process update time and launchers fetch-start time are the same instant. The snapshot cannot
    // be proven to postdate the start, so the tie must resolve to KEEP (workflow.js uses `>=`).
    jest.spyOn(Date, 'now').mockReturnValue(1500);
    let state = reducer(undefined, updateWFProcess({ wfProcess: { id: wfProcessId, activities: [] }, parent: null }));

    state = reducer(state, {
      type: POPULATE_LAUNCHERS_COMPLETE,
      payload: {
        requestTimestamp: 1500,
        applicationLaunchers: { launchers: [{ startedWFProcessId: 'picking-9999999' }] },
      },
    });

    expect(isWfProcessLoaded({ wfProcesses: state }, wfProcessId)).toBe(true);
  });

  it('keeps the process when the launchers snapshot carries no fetch-start time (conservative default)', () => {
    const wfProcessId = 'picking-1000004';

    // An older caller (or a cached snapshot) may complete with no requestTimestamp. With no way to
    // prove the snapshot is authoritative, the reducer must default to KEEP rather than delete a
    // possibly-live process.
    jest.spyOn(Date, 'now').mockReturnValue(1000);
    let state = reducer(undefined, updateWFProcess({ wfProcess: { id: wfProcessId, activities: [] }, parent: null }));

    state = reducer(state, {
      type: POPULATE_LAUNCHERS_COMPLETE,
      payload: {
        // requestTimestamp intentionally omitted (undefined)
        applicationLaunchers: { launchers: [{ startedWFProcessId: 'picking-9999999' }] },
      },
    });

    expect(isWfProcessLoaded({ wfProcesses: state }, wfProcessId)).toBe(true);
  });
});
