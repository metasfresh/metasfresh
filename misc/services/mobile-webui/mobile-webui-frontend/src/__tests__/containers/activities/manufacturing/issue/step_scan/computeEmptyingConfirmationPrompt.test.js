import counterpart from 'counterpart';

import { computeEmptyingConfirmationPrompt } from '../../../../../../containers/activities/manufacturing/issue/step_scan/computeEmptyingConfirmationPrompt';
import translations_en from '../../../../../../utils/translations_en';

const REASON_EMPTIED = 'E';
const REASON_NOT_FOUND = 'N';

// The prompt text is a translated message, so the test registers the very translations the app ships
// (`setupCounterpart` does the same at runtime) instead of asserting against a missing-entry fallback.
beforeAll(() => {
  counterpart.registerTranslations('en', translations_en);
  counterpart.setLocale('en');
});

const call_computeEmptyingConfirmationPrompt = ({ qty, qtyRejected, rejectedReason, resolvedBarcodeData }) => {
  return computeEmptyingConfirmationPrompt({
    qty,
    qtyRejected,
    rejectedReason,
    resolvedBarcodeData: {
      uom: 'kg',
      qtyHUCapacity: 0.5,
      ...resolvedBarcodeData,
    },
  });
};

describe('computeEmptyingConfirmationPrompt', () => {
  it('names the HU remainder, NOT the order-side shortfall, when the HU holds more than the step needs', () => {
    // Step target 0.5 (capped by the order's need), HU capacity 0.502, typed 0.49:
    // the dialog's own qtyRejected is 0.01 -- but `bookEmptiedHUToZero` writes off 0.012.
    const prompt = call_computeEmptyingConfirmationPrompt({
      qty: 0.49,
      qtyRejected: 0.01,
      rejectedReason: REASON_EMPTIED,
      resolvedBarcodeData: { qtyHUCapacity: 0.502 },
    });

    // Exact string, no regex tolerance: what the operator READS is the point of this function, and
    // 0.502 - 0.49 is 0.012000000000000011 in IEEE-754 -- unrounded it renders as "12.00000000000001 g".
    expect(prompt).toEqual('This will write off the remaining 12 g and empty the HU. Continue?');
  });

  it('still names the leftover on a step that takes the whole HU', () => {
    // HU capacity 0.5 == target, typed 0.498: shortfall and remainder are the same 0.002 here, which
    // is exactly why the two could not diverge before the reasons were offered on every step.
    const prompt = call_computeEmptyingConfirmationPrompt({
      qty: 0.498,
      qtyRejected: 0.002,
      rejectedReason: REASON_EMPTIED,
    });

    expect(prompt).toEqual('This will write off the remaining 2 g and empty the HU. Continue?');
  });

  it('asks nothing for any other rejection reason', () => {
    const prompt = call_computeEmptyingConfirmationPrompt({
      qty: 0.49,
      qtyRejected: 0.01,
      rejectedReason: REASON_NOT_FOUND,
      resolvedBarcodeData: { qtyHUCapacity: 0.502 },
    });

    expect(prompt).toBeUndefined();
  });

  it('asks nothing when the operator corrected the qty back up to the full target', () => {
    // The dialog keeps the selected radio button; it drops the reason from the payload itself once
    // nothing is short (`qtyRejectedReason: qtyRejected > 0 ? rejectedReason : null`).
    const prompt = call_computeEmptyingConfirmationPrompt({
      qty: 0.5,
      qtyRejected: 0,
      rejectedReason: REASON_EMPTIED,
      resolvedBarcodeData: { qtyHUCapacity: 0.502 },
    });

    expect(prompt).toBeUndefined();
  });

  it('asks nothing when the entered qty already takes the whole HU', () => {
    // qtyToIssueMax is deliberately not capped by the HU capacity, so an over-entry is possible;
    // there is then no remainder to write off.
    const prompt = call_computeEmptyingConfirmationPrompt({
      qty: 0.6,
      qtyRejected: 0.01,
      rejectedReason: REASON_EMPTIED,
      resolvedBarcodeData: { qtyHUCapacity: 0.5 },
    });

    expect(prompt).toBeUndefined();
  });
});
