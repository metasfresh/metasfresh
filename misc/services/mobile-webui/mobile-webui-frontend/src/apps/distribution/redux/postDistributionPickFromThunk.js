import { postDistributionPickFrom } from '../../../api/distribution';
import { toQRCodeString } from '../../../utils/qrCode/hu';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { getDistributionJobPickedStatus } from '../../../reducers/wfProcesses/distribution/getDistributionJobPickedStatus';
import { distributionJobsListScreenLocation, distributionPickFromScreenLocation } from '../../../routes/distribution';
import { getLaunchers, startWorkflowRequest } from '../../../api/launchers';
import { APPLICATION_ID_Distribution, ACTIVITY_ID_MoveLines } from '../constants';
import { getApplicationLaunchersFacetIds, getApplicationLaunchersFilters } from '../../../reducers/launchers';
import { getApplicationInfoById } from '../../../reducers/applications';

export const postDistributionPickFromThunk =
  ({ history, wfProcessId, activityId, lineId, huScannedCode, qty }) =>
  async (dispatch, getState) => {
    const wfProcess = await postDistributionPickFrom({
      wfProcessId,
      activityId,
      lineId,
      pickFrom: {
        qrCode: toQRCodeString(huScannedCode),
        qtyPicked: qty,
      },
    });

    dispatch(updateWFProcess({ wfProcess })); // runs sync in thunk

    const { isNavigateToJobsListAfterPickFromComplete, isFullyPicked } = getDistributionJobPickedStatus({
      state: getState(),
      wfProcessId,
    });

    if (isNavigateToJobsListAfterPickFromComplete && isFullyPicked) {
      // Job fully picked: start the next unstarted DD Order and navigate to its Pick-From screen
      try {
        const state = getState();
        const nextLauncher = await getNextJobLauncher(state);
        if (nextLauncher) {
          const nextWfProcess = await startWorkflowRequest({ wfParameters: nextLauncher.wfParameters });
          dispatch(updateWFProcess({ wfProcess: nextWfProcess }));
          history.goTo(
            distributionPickFromScreenLocation({
              applicationId: APPLICATION_ID_Distribution,
              wfProcessId: nextWfProcess.id,
              activityId: ACTIVITY_ID_MoveLines,
            })
          );
        } else {
          history.goTo(distributionJobsListScreenLocation());
        }
      } catch (e) {
        console.warn('Failed to auto-start next DD Order after fully picked; falling back to jobs list', e);
        history.goTo(distributionJobsListScreenLocation());
      }
    } else {
      history.goBack();
    }
  };

//
//
// -----------------------------------------------------------------------------
//
//

const getNextJobLauncher = async (state) => {
  const { showFilterByQRCode } = getApplicationInfoById({ state, applicationId: APPLICATION_ID_Distribution });
  const filters = getApplicationLaunchersFilters(state, APPLICATION_ID_Distribution);
  const facetIds = getApplicationLaunchersFacetIds(state, APPLICATION_ID_Distribution);
  const { launchers } = await getLaunchers({
    applicationId: APPLICATION_ID_Distribution,
    showFilterByQRCode,
    facetIds,
    filters,
    excludeAlreadyStarted: true,
    limit: 1,
  });

  return launchers?.[0];
};
