import { useWFActivity } from '../index';
import { ACTIVITY_ID_PickLines } from '../../../apps/picking';
import { COMPLETED } from '../../../constants/CompleteStatus';

export const usePickingJobCompleteStatus = ({ wfProcessId }) => {
  const activity = useWFActivity({ wfProcessId, activityId: ACTIVITY_ID_PickLines });

  return {
    isCompleteJobAutomatically: activity.dataStored.isCompleteJobAutomatically,
    isFullyPicked: activity.dataStored.completeStatus === COMPLETED,
  };
};
