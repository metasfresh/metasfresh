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

          // Carry the just-picked source HU's QR code forward to the next order's Pick-From screen —
          // but never unconditionally (an earlier fix did, and regressed
          // packingTable_navigateToNextOrder + navigateToJobsListAfterPickFromComplete, where the HU
          // the operator must scan next is a DIFFERENT physical one even when it shares the source
          // locator). Three cases; the case NUMBERS are referenced from the specs, so they keep the
          // established order even though isSourceHUCarriedForward decides case 3 first:
          //  1. The next order's pre-allocated move plan draws from that SAME physical HU (the
          //     "sweep" flow: one staging LU feeding several orders) -> carry forward, landing the
          //     operator directly on the product-scan step.
          //     Proven by sweep_scan_product_after_autoAdvance.spec.js.
          //  2. Its plan names a DIFFERENT source HU (a distinct HU per order, even when they share
          //     the source locator) -> omit huQRCode, landing on Scan-HU so the operator scans the
          //     HU the plan actually points at. Proven by packingTable_navigateToNextOrder.spec.js.
          //  3. allowPickingAnyHU=true, so the next order has no pre-allocated move plan at all
          //     -> carry forward. No source HU is designated for it, so the operator's own choice is
          //     the only source information that exists and nothing contradicts it; same landing as
          //     case 1. Proven by sweep_scan_HU_after_autoAdvance_anyHU.spec.js.
          //     This is the case that used to omit the HU, stranding the operator at every order
          //     boundary on the setting every customer runs.
          const carryForwardHUQRCode = (await isSourceHUCarriedForward({
            getState,
            wfProcessId: nextWfProcess.id,
            huScannedCode,
          }))
            ? huScannedCode
            : undefined;

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

// Whether the source HU the operator just scanned stays selected on the next order's Pick-From
// screen. The three cases are spelled out at the call site.
const isSourceHUCarriedForward = async ({ getState, wfProcessId, huScannedCode }) => {
  // Case 3. Nothing to compare the operator's choice against, so their choice stands. Checked FIRST
  // so the HU re-resolution round trip below is skipped entirely: it exists only to compare against
  // a move plan, and here there is none.
  if (isPickAnyHUOrder({ state: getState(), wfProcessId })) {
    return true;
  }

  // Cases 1 and 2: a move plan exists, so carry forward only when it draws from that same physical HU.
  const justPickedHUQR = await getResolvedHUQR({ scannedCode: huScannedCode });
  const nextOrderHUQRs = getNextOrderHUQRs({ state: getState(), wfProcessId });
  return justPickedHUQR != null && nextOrderHUQRs.has(justPickedHUQR);
};

// Whether the next order lets the operator serve it from ANY handling unit
// (MobileUI_UserProfile_DD.IsAllowPickingAnyHU='Y' — the setting every customer runs). The backend
// then builds NO pre-allocated move plan at all (DistributionJobCreateCommand.executeInTrx creates
// one only inside `if (!config.isAllowPickingAnyHU())`), so the job has no steps and no designated
// source HU, and getNextOrderHUQRs is necessarily empty. That emptiness is why the flag has to be
// read directly instead of inferred from it: an empty set cannot tell "no plan was ever built" from
// "the plan's steps are all picked already", and the old condition — which required a NON-empty set
// to match — therefore never fired at any customer.
// Read per line, as JsonDistributionJobLine reports it; `some` mirrors picking's
// isAllowPickingAnyHUOnHeaderLevel, and for distribution every line carries the same job-level
// config value (DistributionJobLoader).
const isPickAnyHUOrder = ({ state, wfProcessId }) => {
  const activity = getActivityById(state, wfProcessId, ACTIVITY_ID_MoveLines);
  return getLinesArrayFromActivity(activity).some((line) => !!line?.allowPickingAnyHU);
};

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
    // Only reachable when the next order HAS a move plan (case 3 returns before this runs), so the
    // fallback is well-defined: with the scanned HU's identity unknown we cannot show it matches the
    // plan, and carrying it forward anyway would drop the operator on the product scan of an HU the
    // plan may not point at. Land them on Scan-HU instead. Logged because on screen this is
    // indistinguishable from case 2, the plan naming a different HU.
    console.warn('Failed to resolve scanned HU QR for auto-advance carry-forward; defaulting to Scan-HU', e);
    return null; // unresolvable -> no match in the caller -> safe default (Scan-HU)
  }
};

// The set of source HUs the next order's pre-allocated move plan draws from (across all its lines
// and steps). Only ever called once a plan is known to exist — isPickAnyHUOrder short-circuits the
// no-plan case, where this would be empty and could decide nothing.
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
