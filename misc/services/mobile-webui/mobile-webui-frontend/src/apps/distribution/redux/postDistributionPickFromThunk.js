import { getDistributionScannedHUQRCodeInfo, postDistributionPickFrom } from '../../../api/distribution';
import { parseQRCodeString, toQRCodeString } from '../../../utils/qrCode/hu';
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
          // never unconditionally: the HU the next order must be served from can be a different
          // physical one, or one that cannot serve that order at all, even when it stands at the same
          // source locator. Three cases; the case NUMBERS are referenced from the specs, so they keep
          // the established order:
          //  1. The next order's pre-allocated move plan draws from that SAME physical HU (the
          //     "sweep" flow: one staging LU feeding several orders) -> carry forward, landing the
          //     operator directly on the product-scan step.
          //     Proven by sweep_scan_product_after_autoAdvance.spec.js.
          //  2. Its plan names a DIFFERENT source HU (a distinct HU per order, even when they share
          //     the source locator) -> omit huQRCode, landing on Scan-HU so the operator scans the
          //     HU the plan actually points at. Proven by packingTable_navigateToNextOrder.spec.js.
          //  3. allowPickingAnyHU=true, so the next order has no pre-allocated move plan at all
          //     -> carry forward, but ONLY while the scanned HU can actually serve that order (see
          //     isHUServingWholeOrder). Nothing designates a source HU, so the operator's own choice
          //     is the only source information that exists — yet it stands only as long as it still
          //     works for what they are about to pick: with an HU applied the Pick-From screen
          //     renders no HU input at all (ScanHUAndGetQtyComponent goes straight to the product
          //     scan), so an HU that cannot serve the order leaves the operator unable to identify
          //     one that can.
          //     Proven by sweep_scan_HU_after_autoAdvance_anyHU.spec.js (the carry-forward) and
          //     navigateToJobsListAfterPickFromComplete.spec.js (the gate on it — that spec's next
          //     order asks for a product the just-picked HU does not hold).
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
  const justPickedHU = await resolveScannedHU({ scannedCode: huScannedCode });
  if (justPickedHU == null) {
    return false;
  }

  // Case 3: no move plan designates a source HU, so the operator's own choice stands — as long as
  // that HU can serve the order they were just advanced to.
  if (isPickAnyHUOrder({ state: getState(), wfProcessId })) {
    return isHUServingWholeOrder({ state: getState(), wfProcessId, huProductId: justPickedHU.productId });
  }

  // Cases 1 and 2: a move plan exists, so carry forward only when it draws from that same physical HU.
  return getNextOrderHUQRs({ state: getState(), wfProcessId }).has(justPickedHU.qrCodeString);
};

// Whether the just-picked HU can serve EVERY line of the next order. This is the proviso the
// carry-forward carries in case 3, where no move plan can vouch for the operator's choice: the
// handling unit stays selected only while it still holds the product that order asks for.
//
// EVERY line, not merely one of them, because the auto-advanced Pick-From screen carries no lineId:
// the app cannot know which line the operator will pick next — they choose it by scanning a product
// GTIN and the backend resolves the matching line (nextEligiblePickFromLine). So the HU must hold the
// product of whatever they may scan; one that serves only part of the order would trap them the
// moment they scan the rest. The next order was just started (getNextJobLauncher passes
// excludeAlreadyStarted), so none of it is picked yet and every line is still to pick.
//
// The HU's product comes from its own QR code, the same source — and the same strict string compare
// against the line's productId — that DistributionPickFromScreen.resolveHUScannedCode already uses to
// reject a scan of the wrong product. A mixed-product HU has no product in its QR code
// (HUQRCodeGenerateForExistingHUsCommand fills it from getSingleProductIdOrNull), so it can never
// prove the proviso and is not carried forward.
const isHUServingWholeOrder = ({ state, wfProcessId, huProductId }) => {
  if (huProductId == null) {
    return false;
  }

  const activity = getActivityById(state, wfProcessId, ACTIVITY_ID_MoveLines);
  const lines = getLinesArrayFromActivity(activity);

  // The emptiness check is load-bearing, not a formality: getLinesArrayFromActivity returns [] for an
  // activity that is not in the store, and `every` over [] is true — so an order whose lines could
  // not be read would otherwise pass as "the HU serves all of it" and be carried forward blindly.
  return lines.length > 0 && lines.every((line) => line.productId === huProductId);
};

// Whether the next order lets the operator serve it from ANY handling unit
// (MobileUI_UserProfile_DD.IsAllowPickingAnyHU='Y' — the setting every customer runs). The backend
// then builds NO pre-allocated move plan at all (DistributionJobCreateCommand.executeInTrx creates
// one only inside `if (!config.isAllowPickingAnyHU())`), so the job has no steps and no designated
// source HU, and getNextOrderHUQRs is necessarily empty. That emptiness is why the flag has to be
// read directly instead of inferred from it: an empty set cannot tell "no plan was ever built" from
// "the plan's steps are all picked already", so a condition keyed on that set alone could never
// decide this case.
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
//
// Returns the HU's normalized QR code string plus the product it holds (null for a mixed-product HU),
// which are what cases 1/2 and case 3 respectively decide on. Both come out of the one round trip:
// the backend renders the HU's own QR code, whose payload carries the product.
const resolveScannedHU = async ({ scannedCode }) => {
  try {
    const { qrCode } = await getDistributionScannedHUQRCodeInfo({ qrCode: toQRCodeString(scannedCode) });
    const qrCodeString = toQRCodeString(qrCode);
    const parsedQRCode = parseQRCodeString({ string: qrCodeString, returnFalseOnError: true });
    return { qrCodeString, productId: parsedQRCode?.productId ?? null };
  } catch (e) {
    // With the scanned HU's identity unknown, no case can be decided in favour of keeping it: we can
    // neither show it matches the next order's move plan (cases 1/2) nor that it holds what that
    // order asks for (case 3). Land the operator on Scan-HU instead. Logged because on screen this is
    // indistinguishable from case 2, the plan naming a different HU.
    console.warn('Failed to resolve scanned HU QR for auto-advance carry-forward; defaulting to Scan-HU', e);
    return null; // unresolvable -> no carry-forward in the caller -> safe default (Scan-HU)
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
