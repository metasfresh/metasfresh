import { postDistributionPickFrom } from '../../../api/distribution';
import { toQRCodeString } from '../../../utils/qrCode/hu';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { getDistributionJobPickedStatus } from '../../../reducers/wfProcesses/distribution/getDistributionJobPickedStatus';
import { distributionJobsListScreenLocation } from '../../../routes/distribution';

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
      history.goTo(distributionJobsListScreenLocation());
    } else {
      history.goBack();
    }
  };
