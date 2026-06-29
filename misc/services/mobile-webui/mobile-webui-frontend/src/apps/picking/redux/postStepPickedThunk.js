import React from 'react';
import ReactDOM from 'react-dom';
import { completePickingJob, postStepPicked } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { getPickingJobCompleteStatus } from '../../../reducers/wfProcesses/picking/getPickingJobCompleteStatus';
import { pickingJobsListLocation } from '../../../routes/picking';
import YesNoDialog from '../../../components/dialogs/YesNoDialog';
import { extractErrorCodeFromAxiosError, extractUserFriendlyErrorMessageFromAxiosError } from '../../../utils/toast';

const RLZ_TOO_SHORT_ERROR_CODE = 'RLZ_TooShort';

/**
 * Shows a YesNoDialog using ReactDOM.render into a temporary DOM node.
 * Resolves to true if the user clicks Yes, false if No.
 */
const confirmShelfLifeWarning = (message) => {
  return new Promise((resolve) => {
    const container = document.createElement('div');
    document.body.appendChild(container);

    const cleanup = (result) => {
      ReactDOM.unmountComponentAtNode(container);
      document.body.removeChild(container);
      resolve(result);
    };

    ReactDOM.render(
      <YesNoDialog promptQuestion={message} onYes={() => cleanup(true)} onNo={() => cleanup(false)} />,
      container
    );
  });
};

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
    isShelfLifeConfirmed = false,
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
      isCloseTarget,
      isShelfLifeConfirmed,
    };

    let wfProcess;
    try {
      wfProcess = await postStepPicked(pickParams);
    } catch (axiosError) {
      const errorCode = extractErrorCodeFromAxiosError(axiosError);
      if (errorCode === RLZ_TOO_SHORT_ERROR_CODE) {
        const warningMessage = extractUserFriendlyErrorMessageFromAxiosError({ axiosError });
        const confirmed = await confirmShelfLifeWarning(warningMessage);
        if (!confirmed) {
          // User chose not to confirm shelf-life warning — abort quietly, no pick
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
