import { useState } from 'react';
import { useDispatch } from 'react-redux';
import * as api from '../api';
import { getPickingSlotContent } from '../api';
import { useWFActivity } from '../../../reducers/wfProcesses';
import { getPickingSlotById } from '../reducers';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { toQRCodeString } from '../../../utils/qrCode/hu';
import { useQuery } from '../../../hooks/useQuery';

export const usePickingSlot = ({ wfProcessId, activityId, pickingSlotId }) => {
  const dispatch = useDispatch();
  const {
    isPending: isLoading,
    data: pickingSlotContent,
    setData: setPickingSlotContent,
  } = useQuery({
    queryKey: [wfProcessId, pickingSlotId],
    queryFn: () => getPickingSlotContent({ wfProcessId, pickingSlotId }),
  });
  const activity = useWFActivity({ wfProcessId, activityId });
  const [isProcessing, setProcessing] = useState(false);
  const { pickingSlotQRCode } = getPickingSlotById({ activity, pickingSlotId }) ?? {};

  // console.log('usePickingSlot', { pickingSlotId, pickingSlots: getPickingSlots({ activity }) });

  const consolidate = ({ huId = null } = {}) => {
    setProcessing(true);
    return api
      .consolidate({ wfProcessId, fromPickingSlotQRCode: toQRCodeString(pickingSlotQRCode), huId })
      .then(({ wfProcess, pickingSlotContent }) => {
        dispatch(updateWFProcess({ wfProcess }));
        setPickingSlotContent(pickingSlotContent);
      })
      .finally(() => setProcessing(false));
  };

  return {
    isLoading,
    pickingSlotContent,
    pickingSlotQRCode,
    isProcessing,
    //
    // Actions:
    consolidate,
  };
};
