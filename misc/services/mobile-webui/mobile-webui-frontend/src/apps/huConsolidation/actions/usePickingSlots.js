import { useWFActivity } from '../../../reducers/wfProcesses';
import { getPickingSlots } from '../reducers';
import * as api from '../api';
import { useDispatch } from 'react-redux';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { toQRCodeString } from '../../../utils/qrCode/hu';

export const usePickingSlots = ({ wfProcessId, activityId }) => {
  const dispatch = useDispatch();
  const activity = useWFActivity({ wfProcessId, activityId });
  const pickingSlots = getPickingSlots({ activity });

  const consolidateFromPickingSlot = ({ fromPickingSlotQRCode }) => {
    return api
      .consolidate({ wfProcessId, fromPickingSlotQRCode: toQRCodeString(fromPickingSlotQRCode) })
      .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })));
  };

  return {
    pickingSlots,
    consolidateFromPickingSlot,
  };
};
