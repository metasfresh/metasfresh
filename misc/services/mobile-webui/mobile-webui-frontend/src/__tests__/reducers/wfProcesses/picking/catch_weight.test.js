import {
  computeCatchWeightsArrayForLine,
  formatCatchWeightToHumanReadableStr,
} from '../../../../reducers/wfProcesses/picking/catch_weight';

const lineWithCatchWeightSteps = (...qtys) => ({
  steps: Object.fromEntries(
    qtys.map((qty, index) => [`step-${index}`, { mainPickFrom: { pickedCatchWeight: { qty, uomSymbol: 'kg' } } }])
  ),
});

describe('picking catch_weight', () => {
  describe('computeCatchWeightsArrayForLine', () => {
    it('sums the catch weights of all steps', () => {
      expect(computeCatchWeightsArrayForLine({ line: lineWithCatchWeightSteps(8.76) })).toEqual([
        { qty: 8.76, uom: 'kg' },
      ]);
    });

    // Reproduces the real picking flow: each pick of a 6-piece catch-weight batch creates six 1-CU
    // steps of 1.46 kg. Six of them sum to exactly 8.76 in IEEE-754, but after a duplicate pick the
    // twelve addends sum to 17.520000000000003. The display path formats with precision=null, i.e.
    // countDecimalPlaces(qty) => 15 decimals, so that artifact reaches the UI verbatim.
    it('does not leak a floating-point artifact when summing many one-CU catch weights', () => {
      const sixSteps = Array(6).fill(1.46);
      expect(computeCatchWeightsArrayForLine({ line: lineWithCatchWeightSteps(...sixSteps) })).toEqual([
        { qty: 8.76, uom: 'kg' },
      ]);

      const twelveSteps = Array(12).fill(1.46);
      expect(computeCatchWeightsArrayForLine({ line: lineWithCatchWeightSteps(...twelveSteps) })).toEqual([
        { qty: 17.52, uom: 'kg' },
      ]);
    });

    it('does not round away genuinely-precise values', () => {
      expect(computeCatchWeightsArrayForLine({ line: lineWithCatchWeightSteps(0.001, 0.002) })).toEqual([
        { qty: 0.003, uom: 'kg' },
      ]);
    });

    // Every addition is (accumulator, nextStepQty), so the decimals must come from BOTH operands: keying
    // only off the accumulator (1 decimal here) would round 1.501 back to 1.5 and lose every later step.
    it('keeps the more precise operand precision when the step precisions differ', () => {
      const steps = [1.5, 0.001, 0.001, 0.001, 0.001, 0.001];
      expect(computeCatchWeightsArrayForLine({ line: lineWithCatchWeightSteps(...steps) })).toEqual([
        { qty: 1.505, uom: 'kg' },
      ]);
    });

    // Below 1e-6 String(num) yields exponential notation, so countDecimalPlaces reports 0 decimals;
    // rounding to 0 there would truncate the quantity to nothing.
    it('does not truncate sub-microgram-scale quantities to zero', () => {
      expect(computeCatchWeightsArrayForLine({ line: lineWithCatchWeightSteps(1e-7, 1e-7) })).toEqual([
        { qty: 2e-7, uom: 'kg' },
      ]);
    });

    it('keeps catch weights of different UOMs apart', () => {
      const line = {
        steps: {
          a: { mainPickFrom: { pickedCatchWeight: { qty: 8.76, uomSymbol: 'kg' } } },
          b: { mainPickFrom: { pickedCatchWeight: { qty: 8.76, uomSymbol: 'kg' } } },
          c: { mainPickFrom: { pickedCatchWeight: { qty: 1.5, uomSymbol: 'lb' } } },
        },
      };
      expect(computeCatchWeightsArrayForLine({ line })).toEqual([
        { qty: 17.52, uom: 'kg' },
        { qty: 1.5, uom: 'lb' },
      ]);
    });
  });

  describe('formatCatchWeightToHumanReadableStr', () => {
    // This is the assertion the mobile E2E spec picking_catchWeight_unpack.spec.js makes on the
    // line button's data-qtycurrentcatchweight attribute after a duplicate pick.
    it('renders a summed catch weight without floating-point noise', () => {
      const catchWeights = computeCatchWeightsArrayForLine({ line: lineWithCatchWeightSteps(...Array(12).fill(1.46)) });
      expect(formatCatchWeightToHumanReadableStr(catchWeights)).toEqual('17.52 kg');
    });
  });
});
