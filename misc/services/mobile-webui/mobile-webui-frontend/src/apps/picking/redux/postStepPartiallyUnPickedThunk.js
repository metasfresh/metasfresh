import { postStepPartiallyUnPicked } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';

export const postStepPartiallyUnPickedThunk =
  ({ wfProcessId, activityId, lineId, scannedCode, unpickProductId, unpickQty, unpickToTargetQRCode }) =>
  async (dispatch) => {
    const wfProcess = await postStepPartiallyUnPicked({
      wfProcessId,
      activityId,
      lineId,
      scannedCode,
      unpickProductId,
      unpickQty,
      unpickToTargetQRCode,
    });

    // NOTE: because running in thunk, this will happen synchronously
    dispatch(updateWFProcess({ wfProcess }));

    return { wfProcess };
  };
