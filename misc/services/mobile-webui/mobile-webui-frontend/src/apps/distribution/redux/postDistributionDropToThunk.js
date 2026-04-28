import { completeDistributionJob, postDistributionDropTo } from '../../../api/distribution';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { getDistributionJobCompleteStatus } from '../../../reducers/wfProcesses/distribution/getDistributionJobCompleteStatus';
import { distributionJobsListScreenLocation } from '../../../routes/distribution';

export const postDistributionDropToThunk =
  ({ history, wfProcessId, activityId, stepId, dropToLocatorQRCode }) =>
  async (dispatch, getState) => {
    const wfProcess = await postDistributionDropTo({
      wfProcessId,
      activityId,
      stepId,
      dropToLocatorQRCode,
    });

    // NOTE: because running in thunk, this will happen synchronously
    dispatch(updateWFProcess({ wfProcess }));

    //
    // Automatically complete the job if required
    let isDistributionJobCompleted = false;
    const { isCompleteJobAutomatically, isFullyMoved } = getDistributionJobCompleteStatus({
      state: getState(),
      wfProcessId,
    });
    if (isCompleteJobAutomatically && isFullyMoved) {
      try {
        await completeDistributionJob({ wfProcessId });
        history.goTo(distributionJobsListScreenLocation());
        isDistributionJobCompleted = true;
      } catch (error) {
        console.warn('Auto-complete failed', error);
      }
    }

    return { isDistributionJobCompleted };
  };
