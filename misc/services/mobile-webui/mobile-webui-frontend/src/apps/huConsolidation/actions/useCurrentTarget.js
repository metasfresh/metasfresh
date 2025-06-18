import { useWFActivity } from '../../../reducers/wfProcesses';
import { getCurrentTarget } from '../reducers';
import * as api from '../api';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useDispatch } from 'react-redux';
import { toastErrorFromObj } from '../../../utils/toast';
import { useState } from 'react';

export const useCurrentTarget = ({ wfProcessId, activityId }) => {
  const dispatch = useDispatch();
  const activity = useWFActivity({ wfProcessId, activityId });
  const [isProcessing, setProcessing] = useState(false);

  const currentTarget = getCurrentTarget({ activity });
  const isPrintable = currentTarget?.printable ?? false;

  const printLabel = () => {
    if (!isPrintable) return;
    setProcessing(true);
    return api
      .printTargetLabel({ wfProcessId })
      .catch((error) => toastErrorFromObj(error))
      .finally(() => setProcessing(false));
  };

  const closeTarget = () => {
    setProcessing(true);
    return api
      .closeTarget({ wfProcessId })
      .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })))
      .catch((error) => toastErrorFromObj(error))
      .finally(() => setProcessing(false));
  };

  return {
    currentTarget,
    isProcessing,
    closeTarget,
    printLabel: isPrintable ? printLabel : null,
  };
};
