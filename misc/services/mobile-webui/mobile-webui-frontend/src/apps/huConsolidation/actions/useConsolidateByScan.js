import * as api from '../api';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useDispatch } from 'react-redux';

/**
 * Moves the crate that the scanned code resolves to (searched in all slots of the job) onto the open pallet.
 * Rejects on error.
 */
export const useConsolidateByScan = ({ wfProcessId }) => {
  const dispatch = useDispatch();

  return ({ scannedCode }) => {
    return api
      .consolidateByScan({ wfProcessId, scannedCode })
      .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })));
  };
};
