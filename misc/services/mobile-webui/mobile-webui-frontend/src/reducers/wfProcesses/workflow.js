import { original } from 'immer';

import * as workflowTypes from '../../constants/WorkflowActionTypes';
import * as launcherTypes from '../../constants/LaunchersActionTypes';
import { mergeWFProcessToState } from './utils';

export const workflowReducer = ({ draftState, action }) => {
  switch (action.type) {
    case workflowTypes.UPDATE_WORKFLOW_PROCESS: {
      const { wfProcess: fromWFProcess } = action.payload;

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
      });

      return draftState;
    }

    case launcherTypes.POPULATE_LAUNCHERS_COMPLETE: {
      const { applicationLaunchers } = action.payload;

      removeWFProcessesFromState({
        draftState,
        wfProcessIdsToKeep: extractStartedWFProcessIdsFromLaunchers(applicationLaunchers.launchers),
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

const removeWFProcessesFromState = ({ draftState, wfProcessIdsToKeep }) => {
  const wfProcessIdsInState = Object.keys(original(draftState));

  wfProcessIdsInState.forEach((wfProcessIdInState) => {
    if (!wfProcessIdsToKeep.includes(wfProcessIdInState)) {
      delete draftState[wfProcessIdInState];
    }
  });
};
