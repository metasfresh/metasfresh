import { original } from 'immer';

import * as workflowTypes from '../../constants/WorkflowActionTypes';
import * as launcherTypes from '../../constants/LaunchersActionTypes';
import { mergeWFProcessToState, updateUserEditable } from './utils';

export const workflowReducer = ({ draftState, action }) => {
  switch (action.type) {
    case workflowTypes.UPDATE_WORKFLOW_PROCESS: {
      const { wfProcess: fromWFProcess, parent, timestamp } = action.payload;

      let draftWFProcess = draftState[fromWFProcess.id];

      if (!draftWFProcess) {
        draftWFProcess = {
          id: fromWFProcess.id,
          activities: {},
        };
      }

      draftState[fromWFProcess.id] = mergeWFProcessToState({
        draftWFProcess: draftWFProcess,
        fromWFProcess,
        parent,
      });

      // Remember when this process was last updated locally so a launchers snapshot that predates
      // the update cannot prune it (see POPULATE_LAUNCHERS_COMPLETE below). `timestamp` is stamped
      // by the updateWFProcess action creator, keeping this reducer pure.
      if (timestamp != null) {
        draftState[fromWFProcess.id].updatedAt = timestamp;
      }

      return draftState;
    }

    case workflowTypes.SET_ACTIVITY_PROCESSING: {
      const { wfProcessId, activityId, processing } = action.payload;
      const draftWFProcess = draftState[wfProcessId];
      const draftActivity = draftWFProcess.activities[activityId];

      draftActivity.dataStored.processing = !!processing;

      updateUserEditable({ draftWFProcess });
      return draftState;
    }

    // Only a REQUESTED launchers snapshot may prune. Pushed snapshots have their own action type
    // (launcherTypes.POPULATE_LAUNCHERS_PUSHED) which is deliberately NOT handled in this reducer:
    //  - a push carries no request-issued time, so its stamp cannot bound what it knows about; stamping it
    //    with the browser's receipt time made a snapshot built BEFORE a job look newer than the job and
    //    deleted a live job off the operator's screen;
    //  - the requested route stamps at request-issue time, so a snapshot asked for before a job started is
    //    provably not authoritative about it and the guard below keeps the job.
    // Two premises this rests on, stated here because the fix rests on them:
    //  - requested snapshots are never served from cache: WorkflowRestController leaves maxStaleAccepted
    //    unset, whose default Duration.ZERO forces a recompute;
    //  - the requested fetch is dispatched only from the launchers screen the operator has left --
    //    containers/wfLaunchersScreen/useLaunchers.js is the only non-test dispatcher of this action.
    // Because the delete power is attached to the ACTION TYPE, a new consumer cannot silently acquire it:
    // it has to pick this deleting type on purpose.
    case launcherTypes.POPULATE_LAUNCHERS_COMPLETE: {
      const { applicationLaunchers, requestTimestamp } = action.payload;

      removeWFProcessesFromState({
        draftState,
        wfProcessIdsToKeep: extractStartedWFProcessIdsFromLaunchers(applicationLaunchers.launchers),
        launchersFetchStartedAt: requestTimestamp,
      });

      return draftState;
    }

    default: {
      return draftState;
    }
  }
};

const extractStartedWFProcessIdsFromLaunchers = (launchers) => {
  return launchers.reduce((accum, launcher) => {
    if (launcher.startedWFProcessId) {
      accum.push(launcher.startedWFProcessId);
    }
    return accum;
  }, []);
};

const removeWFProcessesFromState = ({ draftState, wfProcessIdsToKeep, launchersFetchStartedAt }) => {
  const originalState = original(draftState);
  const wfProcessIdsInState = Object.keys(originalState);

  wfProcessIdsInState.forEach((wfProcessIdInState) => {
    // Present in the snapshot => a live started job => keep.
    if (wfProcessIdsToKeep.includes(wfProcessIdInState)) {
      return;
    }

    // Absent from the snapshot: prune ONLY when we can prove the snapshot is authoritative about
    // this process, i.e. the launchers fetch was issued strictly AFTER the process was last updated
    // locally. A launchers response cannot be authoritative about a process started after its
    // request was issued, so a snapshot that predates (or is concurrent with) the local update must
    // not delete the process — otherwise a pre-start refresh resolving after a start would bounce
    // the operator home mid-flow. Conservative default: if either timestamp is unknown, KEEP.
    // Residual risk (accepted): both stamps are wall-clock Date.now(), not monotonic, so a backward
    // clock adjustment between them could invert this comparison — orders of magnitude rarer than
    // the race being fixed.
    const wfProcessUpdatedAt = originalState[wfProcessIdInState]?.updatedAt;
    if (wfProcessUpdatedAt == null || launchersFetchStartedAt == null) {
      return;
    }
    if (wfProcessUpdatedAt >= launchersFetchStartedAt) {
      return;
    }

    // Older than the snapshot and absent from it => genuinely gone => prune (GC).
    delete draftState[wfProcessIdInState];
  });
};
