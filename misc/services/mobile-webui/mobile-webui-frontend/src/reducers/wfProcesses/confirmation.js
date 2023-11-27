import * as types from '../../constants/UserConfirmationTypes';
import * as CompleteStatus from '../../constants/CompleteStatus';

import { updateUserEditable } from './utils';
import { registerHandler } from './activityStateHandlers';

const COMPONENT_TYPE = 'common/confirmButton';

export const activityUserConfirmationReducer = ({ draftState, action }) => {
  switch (action.type) {
    case types.SET_ACTIVITY_USER_CONFIRMED: {
      const { wfProcessId, activityId } = action.payload;

      const draftWFProcess = draftState[wfProcessId];
      const draftActivity = draftWFProcess.activities[activityId];
      draftActivity.dataStored.confirmed = true;
      draftActivity.dataStored.completeStatus = computeActivityStatus({
        confirmed: draftActivity.dataStored.confirmed,
      });

      updateUserEditable({ draftWFProcess });

      return draftState;
    }

    default: {
      return draftState;
    }
  }
};

const computeActivityStatus = ({ confirmed }) => {
  return confirmed ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
};

registerHandler({
  componentType: COMPONENT_TYPE,
  mergeActivityDataStored: ({ draftActivityDataStored, fromActivity }) => {
    draftActivityDataStored.isAlwaysAvailableToUser = fromActivity.isAlwaysAvailableToUser ?? true;
    draftActivityDataStored.confirmed = !!fromActivity.componentProps.confirmed;
    draftActivityDataStored.completeStatus = computeActivityStatus({
      confirmed: draftActivityDataStored.confirmed,
    });

    return draftActivityDataStored;
  },
});
