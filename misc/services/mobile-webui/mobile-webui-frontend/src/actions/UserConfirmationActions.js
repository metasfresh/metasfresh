import { SET_ACTIVITY_USER_CONFIRMED } from '../constants/UserConfirmationTypes';

export function setActivityUserConfirmed({ wfProcessId, activityId }) {
  return {
    type: SET_ACTIVITY_USER_CONFIRMED,
    payload: { wfProcessId, activityId },
  };
}
