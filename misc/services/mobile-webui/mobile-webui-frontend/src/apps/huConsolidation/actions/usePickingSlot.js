import { useState } from 'react';
import { useDispatch } from 'react-redux';
import * as api from '../api';
import { useWFActivity } from '../../../reducers/wfProcesses';
import { getPickingSlotById } from '../reducers';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { toQRCodeString } from '../../../utils/qrCode/hu';
import { toastErrorFromObj } from '../../../utils/toast';

export const usePickingSlot = ({ wfProcessId, activityId, pickingSlotId }) => {
  const dispatch = useDispatch();
  const activity = useWFActivity({ wfProcessId, activityId });
  const [processing, setProcessing] = useState(false);
  // console.log('usePickingSlot', { pickingSlotId, pickingSlots: getPickingSlots({ activity }) });
  const { pickingSlotQRCode } = getPickingSlotById({ activity, pickingSlotId }) ?? {};

  const consolidateAll = () => {
    setProcessing(true);
    return api
      .consolidate({ wfProcessId, fromPickingSlotQRCode: toQRCodeString(pickingSlotQRCode) })
      .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })))
      .catch((error) => toastErrorFromObj(error))
      .finally(() => setProcessing(false));
  };

  return {
    processing,
    pickingSlotQRCode,
    //
    consolidateAll,
  };
};
