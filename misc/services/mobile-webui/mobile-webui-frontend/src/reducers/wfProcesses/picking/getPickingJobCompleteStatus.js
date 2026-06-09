import { getActivityById } from '../index';
import { ACTIVITY_ID_PickLines } from '../../../apps/picking';
import { COMPLETED } from '../../../constants/CompleteStatus';

export const getPickingJobCompleteStatus = ({ state, wfProcessId }) => {
  const activity = getActivityById(state, wfProcessId, ACTIVITY_ID_PickLines);
  return {
    isCompleteJobAutomatically: activity.dataStored.isCompleteJobAutomatically,
    isFullyPicked: activity.dataStored.completeStatus === COMPLETED,
  };
};
