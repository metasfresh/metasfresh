import { completePickingJob, postStepPicked } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { getPickingJobCompleteStatus } from '../../../reducers/wfProcesses/picking/getPickingJobCompleteStatus';
import { pickingJobsListLocation } from '../../../routes/picking';

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
    isCloseTarget = false,
  }) =>
  async (dispatch, getState) => {
    const wfProcess = await postStepPicked({
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
      isCloseTarget,
    });

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
