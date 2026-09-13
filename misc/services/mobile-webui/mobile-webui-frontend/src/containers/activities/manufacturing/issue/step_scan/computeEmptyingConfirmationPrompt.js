import { QTY_REJECTED_REASON_EMPTIED_KEY } from '../../../../../reducers/wfProcesses';
import { trl } from '../../../../../utils/translations';
import { formatQtyToHumanReadableStr } from '../../../../../utils/qtys';

/**
 * Computes the "empty (auto. inventory)" confirmation prompt shown before the write-off is posted
 * (only when the activity's `isConfirmEmptyingHU` flag is on). Returns `undefined` when nothing shall
 * be confirmed.
 *
 * Pure on purpose (like computeIssueRequest.js): the quantity this prompt names is NOT the one the
 * dialog computed, and that difference is the whole point of the function.
 *
 * The prompt must name what `PPOrderIssueScheduleService#bookEmptiedHUToZero` will actually write off:
 * the HU's REMAINDER, `qtyHUCapacity - qty`. The dialog's own `qtyRejected` is the ORDER-side shortfall
 * (`qtyTarget - qty`), which is a different number as soon as the HU holds more than the step needs —
 * e.g. an HU of 0.502 on a step targeting 0.5: typing 0.49 rejects 0.01 but empties 0.012. The two are
 * equal only on a step whose target reaches the HU's capacity, which is why they could not diverge
 * before the reasons started being offered on every step (computeStepScanPropsFromActivity.js).
 *
 * @param qty quantity the operator entered (already validated by ScanHUAndGetQtyComponent)
 * @param qtyRejected the order-side shortfall the dialog computed
 * @param rejectedReason the reason key the operator selected, or null
 * @param resolvedBarcodeData props computed by computeStepScanPropsFromActivity for the scanned step
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

  const uom = resolvedBarcodeData?.uom;
  // qtyHUCapacity is `@NonNull` on the wire (JsonRawMaterialsIssueLineStep), so it is always a number
  // here; Math.max only keeps the prompt sane if the operator was allowed to exceed the HU's capacity
  // (qtyToIssueMax is deliberately not capped by it — computeStepScanPropsFromActivity.js).
  const qtyToWriteOff = Math.max(resolvedBarcodeData.qtyHUCapacity - qty, 0);
  if (!(qtyToWriteOff > 0)) {
    return undefined;
  }

  return trl('activities.manufacturing.confirmEmptyHUPrompt', {
    qty: formatQtyToHumanReadableStr({ qty: qtyToWriteOff, uom }),
  });
};
