import { useWFActivity } from '../../../reducers/wfProcesses';
import { getPickingSlots } from '../reducers';

export const usePickingSlots = ({ wfProcessId, activityId }) => {
  const activity = useWFActivity({ wfProcessId, activityId });
  const pickingSlots = getPickingSlots({ activity });

  return {
    pickingSlots,
  };
};
