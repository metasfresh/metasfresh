import { getActivityById } from '../index';
import { ACTIVITY_ID_MoveLines } from '../../../apps/distribution/constants';

export const getDistributionJobPickedStatus = ({ state, wfProcessId }) => {
  const activity = getActivityById(state, wfProcessId, ACTIVITY_ID_MoveLines);
  return {
    isNavigateToJobsListAfterPickFromComplete: activity.dataStored.isNavigateToJobsListAfterPickFromComplete,
    isFullyPicked: activity.dataStored.isFullyPicked,
  };
};
