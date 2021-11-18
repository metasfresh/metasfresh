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

      console.log('**** BUG **** ===----->', draftWFProcess);
      const resultOfMerge = mergeWFProcessToState({
        draftWFProcess: draftWFProcess,
        fromWFProcess,
      });

      draftState[fromWFProcess.id] = resultOfMerge;

      return draftState;
    }

    case launcherTypes.POPULATE_LAUNCHERS: {
      const { launchers } = action.payload;

      removeWFProcessesFromState({
        draftState,
        wfProcessIdsToKeep: extractStartedWFProcessIdsFromLaunchers(launchers),
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
