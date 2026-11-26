import { QTY_REJECTED_REASON_EMPTIED_KEY } from '../../../../../reducers/wfProcesses';
import { trl } from '../../../../../utils/translations';
import { countDecimalPlaces, formatQtyToHumanReadableStr } from '../../../../../utils/qtys';
import { round } from '../../../../../utils/numbers';

const MAX_PRECISION = 6;

/**
 * Computes the "empty (auto. inventory)" confirmation prompt shown before the write-off is posted
 * (only when the activity's `isConfirmEmptyingHU` flag is on). Returns `undefined` when nothing shall
 * be confirmed.
 *
 * Pure on purpose (like computeIssueRequest.js): the quantity this prompt names is NOT the one the
 * dialog computed, and that difference is the whole point of the function.
 *
 * The prompt names the HU's remainder AS LAST KNOWN TO THIS STEP, `qtyHUCapacity - qty`. The backend
 * does not compute that: `PPOrderIssueScheduleService#bookEmptiedHUToZero`
 * (PPOrderIssueScheduleService.java:164-175) reads the HU's LIVE storage after the issue and books
 * whatever it finds to zero (and writes nothing off at all if the HU is no longer active or is already
 * empty, :151-162). So this is the operator-facing PREDICTION of that booking, exact as long as the
 * HU's content has not changed since the activity was loaded.
 *
 * What it must NOT name is the dialog's own `qtyRejected`, the ORDER-side shortfall
 * (`qtyTarget - qty`): that is a different number as soon as the HU holds more than the step needs —
 * e.g. an HU of 0.502 on a step targeting 0.5: typing 0.49 rejects 0.01 but empties 0.012. The two are
 * equal only on a step whose target reaches the HU's capacity, which is why they could not diverge
 * before the reasons started being offered on every step (computeStepScanPropsFromActivity.js).
 *
 * @param qty quantity the operator entered (already validated by ScanHUAndGetQtyComponent)
 * @param qtyRejected the order-side shortfall the dialog computed
 * @param rejectedReason the reason key the operator selected, or null
 * @param resolvedBarcodeData props computed by computeStepScanPropsFromActivity for the scanned step;
 *        mandatory (ScanHUAndGetQtyComponent always has it by the time the qty dialog can be submitted)
 */
export const computeEmptyingConfirmationPrompt = ({
  qty = 0,
  qtyRejected = 0,
  rejectedReason = null,
  resolvedBarcodeData,
}) => {
  if (rejectedReason !== QTY_REJECTED_REASON_EMPTIED_KEY) {
    return undefined;
  }

  // `rejectedReason` is the dialog's raw radio-button state: it survives the operator correcting the
  // qty back up to the full target, and the dialog only drops it from the payload afterwards
  // (`qtyRejectedReason: qtyRejected > 0 ? rejectedReason : null` in GetQuantityDialog.jsx). Without
  // this check we would ask to confirm an emptying that is no longer being requested.
  if (!(qtyRejected > 0)) {
    return undefined;
  }

  const { uom, qtyHUCapacity } = resolvedBarcodeData;

  // `qty` is what is taken FROM THIS HU. That holds while `qtyInput.ProcessedQtyIsStillOnScale` is off,
  // which is the only state this prompt is written for; with that setting on, the typed qty is the
  // scale's total for the line (computeStepScanPropsFromActivity.js:22-27 keeps `lineQtyIssued` in
  // `qtyToIssueMax` and exposes `qtyAlreadyOnScale`), so the subtraction below would understate the
  // remainder. GetQuantityDialog subtracts `qtyAlreadyOnScale` before calling us, so it is the line's
  // total-minus-own-issues, still not necessarily an HU-relative qty.
  //
  // Round before formatting: `0.502 - 0.49` is `0.012000000000000011` in IEEE-754, and
  // formatQtyToHumanReadableStr derives its precision from the value's own decimals, so unrounded the
  // operator would read "remaining 12.00000000000001 g" on the dialog that destroys an HU. Neither
  // operand carries more decimals than the UOM allows (both come from the same backend qty), so their
  // decimal count is the precision to round the difference to.
  // ...capped, because `qty` is not always the clean decimal the operator typed: on the still-on-scale
  // path the dialog hands over `Math.max(typed - qtyAlreadyOnScale, 0)` (GetQuantityDialog.jsx), whose
  // own 17-decimal tail would otherwise become the precision and make this rounding a no-op. 6 decimals
  // is past any UOM precision in play (KGM, the finest here, is configured with 5).
  // qtyHUCapacity is `@NonNull` on the wire (JsonRawMaterialsIssueLineStep), so no null guard here.
  const precision = Math.min(Math.max(countDecimalPlaces(qtyHUCapacity), countDecimalPlaces(qty)), MAX_PRECISION);
  // Math.max() only keeps the prompt sane if the operator was allowed to exceed the HU's capacity
  // (qtyToIssueMax is deliberately not capped by it — computeStepScanPropsFromActivity.js).
  const qtyToWriteOff = Math.max(round(qtyHUCapacity - qty, precision), 0);
  if (!(qtyToWriteOff > 0)) {
    return undefined;
  }

  return trl('activities.manufacturing.confirmEmptyHUPrompt', {
    qty: formatQtyToHumanReadableStr({ qty: qtyToWriteOff, uom }),
  });
};
