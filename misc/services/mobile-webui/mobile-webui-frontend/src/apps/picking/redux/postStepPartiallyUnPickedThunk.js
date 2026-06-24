import { postStepPartiallyUnPicked } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';

export const postStepPartiallyUnPickedThunk =
  ({ wfProcessId, activityId, lineId, stepId, huQRCode, unpickProductId, unpickQty, unpickToTargetQRCode }) =>
  async (dispatch) => {
    const wfProcess = await postStepPartiallyUnPicked({
      wfProcessId,
      activityId,
      lineId,
      stepId,
      huQRCode,
      unpickProductId,
      unpickQty,
      unpickToTargetQRCode,
    });

    // NOTE: because running in thunk, this will happen synchronously
    dispatch(updateWFProcess({ wfProcess }));

    return { wfProcess };
  };
