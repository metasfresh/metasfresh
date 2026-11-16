import { completePickingJob, postStepPicked } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { getPickingJobCompleteStatus } from '../../../reducers/wfProcesses/picking/getPickingJobCompleteStatus';
import { pickingJobsListLocation } from '../../../routes/picking';
import {
  extractErrorCodeFromAxiosError,
  extractUserFriendlyErrorMessageFromAxiosError,
  toastNotification,
} from '../../../utils/toast';
import {
  clearPendingShelfLifeConfirmation,
  setPendingShelfLifeConfirmation,
  storePendingShelfLifeResolver,
} from './pickingUiSlice';

const RLZ_TOO_SHORT_ERROR_CODE = 'RLZ_TooShort';

export const postStepPickedThunk =
  ({
    history,
    wfProcessId,
    activityId,
    lineId,
    stepId,
    huQRCode,
    qtyPicked,
    qtyRejected,
    qtyRejectedReasonCode,
    catchWeight,
    pickWholeTU,
    checkIfAlreadyPacked,
    setBestBeforeDate,
    bestBeforeDate,
    productionDate,
    setLotNo,
    lotNo,
    setGrais,
    graiCodes,
    setSerialNos,
    serialNos,
    isCloseTarget = false,
  }) =>
  async (dispatch, getState) => {
    const pickParams = {
      wfProcessId,
      activityId,
      lineId,
      stepId,
      huQRCode,
      qtyPicked,
      qtyRejected,
      qtyRejectedReasonCode,
      catchWeight,
      pickWholeTU,
      checkIfAlreadyPacked,
      setBestBeforeDate,
      bestBeforeDate,
      productionDate,
      setLotNo,
      lotNo,
      setGrais,
      graiCodes,
      setSerialNos,
      serialNos,
      isCloseTarget,
    };

    let wfProcess;
    try {
      wfProcess = await postStepPicked({ ...pickParams, isShelfLifeConfirmed: false });
    } catch (axiosError) {
      const errorCode = extractErrorCodeFromAxiosError(axiosError);
      if (errorCode === RLZ_TOO_SHORT_ERROR_CODE) {
        const warningMessage = extractUserFriendlyErrorMessageFromAxiosError({ axiosError });

        // Ask the user via a declarative, state-driven dialog rendered in the React tree.
        const confirmed = await new Promise((resolve) => {
          storePendingShelfLifeResolver(resolve);
          dispatch(setPendingShelfLifeConfirmation({ message: warningMessage }));
        });

        dispatch(clearPendingShelfLifeConfirmation());

        if (!confirmed) {
          // User declined — notify the operator and abort the pick.
          toastNotification({ messageKey: 'activities.picking.rlzConfirmDeclined' });
          return { isPickingJobCompleted: false };
        }
        // User confirmed — retry with isShelfLifeConfirmed=true
        wfProcess = await postStepPicked({ ...pickParams, isShelfLifeConfirmed: true });
      } else {
        throw axiosError;
      }
    }

    // NOTE: because running in thunk, this will happen synchronously
    dispatch(updateWFProcess({ wfProcess }));

    //
    // Automatically complete the job if required
    let isPickingJobCompleted = false;
    const { isCompleteJobAutomatically, isFullyPicked } = getPickingJobCompleteStatus({
      state: getState(),
      wfProcessId,
    });
    if (isCompleteJobAutomatically && isFullyPicked) {
      try {
        await completePickingJob({ wfProcessId });
        history?.goTo(pickingJobsListLocation());
        isPickingJobCompleted = true;
      } catch (error) {
        console.warn('Auto-complete failed', error);
      }
    }

    return { isPickingJobCompleted };
  };
