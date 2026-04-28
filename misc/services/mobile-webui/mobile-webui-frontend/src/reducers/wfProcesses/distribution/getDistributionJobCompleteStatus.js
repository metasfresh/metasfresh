import { getActivityById } from '../index';
import { COMPLETED } from '../../../constants/CompleteStatus';
import { ACTIVITY_ID_MoveLines } from '../../../apps/distribution/constants';

export const getDistributionJobCompleteStatus = ({ state, wfProcessId }) => {
  const activity = getActivityById(state, wfProcessId, ACTIVITY_ID_MoveLines);
  return {
    isCompleteJobAutomatically: activity.dataStored.isCompleteJobAutomatically,
    isFullyMoved: activity.dataStored.completeStatus === COMPLETED,
  };
};

export const isRequireScanningProductCode = ({ activity }) => !!activity.dataStored.requireScanningProductCode;
