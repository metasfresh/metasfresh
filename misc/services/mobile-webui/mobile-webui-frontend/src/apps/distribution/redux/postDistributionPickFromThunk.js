import { getDistributionScannedHUQRCodeInfo, postDistributionPickFrom } from '../../../api/distribution';
import { toQRCodeString } from '../../../utils/qrCode/hu';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { getDistributionJobPickedStatus } from '../../../reducers/wfProcesses/distribution/getDistributionJobPickedStatus';
import { distributionJobsListScreenLocation, distributionPickFromScreenLocation } from '../../../routes/distribution';
import { getLaunchers, startWorkflowRequest } from '../../../api/launchers';
import { APPLICATION_ID_Distribution, ACTIVITY_ID_MoveLines } from '../constants';
import { getApplicationLaunchersFacetIds, getApplicationLaunchersFilters } from '../../../reducers/launchers';
import { getApplicationInfoById } from '../../../reducers/applications';
import { getActivityById, getLinesArrayFromActivity, getStepsArrayFromLine } from '../../../reducers/wfProcesses';

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

          // Carry the just-picked source HU's QR code forward to the next order's Pick-From screen
          // ONLY when the next order's pre-allocated move plan draws from that SAME physical HU —
          // never unconditionally (an earlier fix carried it forward unconditionally and regressed
          // packingTable_navigateToNextOrder + navigateToJobsListAfterPickFromComplete, where the
          // next order's source HU is a DIFFERENT one even when it shares the source locator). Three
          // cases, decided by HU identity (not locator):
          //  1. Same source HU (the "sweep" flow: one staging LU feeding several orders) -> carry
          //     forward, landing the operator directly on the product-scan step.
          //  2. A different source HU (distinct HU per order, even sharing a locator) -> omit
          //     huQRCode, landing on Scan-HU so the operator (re-)scans the correct source HU.
          //  3. allowPickingAnyHU=true, so the next order has no pre-allocated move plan
          //     (nextOrderHUQRs empty) -> omit huQRCode. Safe default: never assume it's the same HU.
          const justPickedHUQR = await getResolvedHUQR({ scannedCode: huScannedCode });
          const nextOrderHUQRs = getNextOrderHUQRs({ state: getState(), wfProcessId: nextWfProcess.id });
          const carryForwardHUQRCode =
            justPickedHUQR != null && nextOrderHUQRs.has(justPickedHUQR) ? huScannedCode : undefined;

          history.goTo(
            distributionPickFromScreenLocation({
              applicationId: APPLICATION_ID_Distribution,
              wfProcessId: nextWfProcess.id,
              activityId: ACTIVITY_ID_MoveLines,
              huQRCode: carryForwardHUQRCode,
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

// Re-resolve the operator's scan to its CURRENT backend-normalized HU identity — a fresh lookup, not
// a cached/plan-snapshot value. This matters because on a PARTIAL pick the just-completed step's own
// pickFromHU is overwritten to report the ephemeral split HU that the picked/moved portion is packed
// into (see DistributionJobPickFromCommand.splitQty), NOT the stable source LU that stays at the
// source locator. So reading that step's pickFromHU.qrCode after the pick would read the moved piece
// and never match nextOrderHUQRs — even for the genuinely-same source LU. Re-resolving the scanned
// barcode (an external label in the sweep case) reflects what was actually scanned, the same way the
// backend's resolveHuIdToPick does, so it compares ground truth against the next order's freshly-built plan.
const getResolvedHUQR = async ({ scannedCode }) => {
  try {
    const { qrCode } = await getDistributionScannedHUQRCodeInfo({ qrCode: toQRCodeString(scannedCode) });
    return toQRCodeString(qrCode);
  } catch (e) {
    console.warn('Failed to resolve scanned HU QR for auto-advance carry-forward; defaulting to Scan-HU', e);
    return null; // unresolvable -> no match below -> safe default (Scan-HU)
  }
};

// The set of source HUs the next order's pre-allocated move plan draws from (across all its lines
// and steps). Empty when allowPickingAnyHU=true, i.e. no move plan was built.
const getNextOrderHUQRs = ({ state, wfProcessId }) => {
  const activity = getActivityById(state, wfProcessId, ACTIVITY_ID_MoveLines);
  const huQRCodes = getLinesArrayFromActivity(activity).flatMap((line) =>
    getStepsArrayFromLine(line).map((step) => toQRCodeString(step.pickFromHU?.qrCode))
  );
  return new Set(huQRCodes.filter(Boolean));
};

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
