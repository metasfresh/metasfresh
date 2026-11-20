import { useWFActivity } from '../../../reducers/wfProcesses';
import { getCurrentTarget } from '../reducers';
import * as api from '../api';
import { getWFProcess } from '../../../api/workflow';
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

  /** @returns {Promise<boolean>} true if the target was closed without error, false if an error was shown (the target might still have been closed, the job is reloaded) */
  const closeTarget = () => {
    setProcessing(true);
    return api
      .closeTarget({ wfProcessId })
      .then((wfProcess) => {
        dispatch(updateWFProcess({ wfProcess }));
        return true;
      })
      .catch((error) => {
        toastErrorFromObj(error);
        // The pallet is closed even when printing its label failed, so show the actual job state, not the stale open pallet.
        return getWFProcess({ wfProcessId })
          .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })))
          .catch((reloadError) => console.error('Failed reloading the job after a close error', reloadError))
          .then(() => false);
      })
      .finally(() => setProcessing(false));
  };

  return {
    currentTarget,
    isProcessing,
    closeTarget,
    printLabel: isPrintable ? printLabel : null,
  };
};
