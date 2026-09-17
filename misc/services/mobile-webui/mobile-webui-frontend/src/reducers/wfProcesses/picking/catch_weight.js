import { getStepsArrayFromLine } from '../index';
import { countDecimalPlaces, formatQtyToHumanReadableStr } from '../../../utils/qtys';

export const computeCatchWeightForStep = ({ pickFrom: pickFromParam, step: stepParam }) => {
  let pickFrom;
  if (pickFromParam) {
    pickFrom = pickFromParam;
  } else if (stepParam) {
    pickFrom = stepParam.mainPickFrom;
  }

  const uom = pickFrom?.pickedCatchWeight?.uomSymbol;
  if (!uom) {
    // console.debug('computeCatchWeightForStep: No uom found for catch weight. Returning null.', { pickFrom, stepParam });
    return null;
  }

  const qty = pickFrom?.pickedCatchWeight?.qty ?? 0;

  // console.debug('computeCatchWeightForStep: Returning', { qty, uom, pickFrom, stepParam });
  return { qty, uom };
};

export const computeCatchWeightsArrayForLine = ({ line }) => {
  const catchWeightsMap = {};

  getStepsArrayFromLine(line).forEach((step) => {
    const catchWeight = computeCatchWeightForStep({ step });
    addCatchWeightToMap(catchWeightsMap, catchWeight);
  });

  let catchWeightsArray = Object.values(catchWeightsMap);
  if (catchWeightsArray.length <= 0 && line.catchWeightUOM) {
    catchWeightsArray = [{ qty: 0, uom: line.catchWeightUOM }];
  }

  return catchWeightsArray;
};

/**
 * Adds two quantities and rounds the result back to the number of decimals the operands actually carry.
 *
 * A plain `+=` accumulates binary floating-point error: one pick of a 6-piece catch-weight batch is six
 * 1-CU steps of 1.46 kg, which sum to exactly 8.76, but twelve of them (after a duplicate pick) sum to
 * 17.520000000000003. The display path formats with no explicit precision, so it derives the precision
 * from the value itself (`countDecimalPlaces`) and renders all 15 decimals verbatim into the UI.
 */
const sumQtys = (qty1, qty2) => {
  const sum = qty1 + qty2;
  const decimals = Math.max(countDecimalPlaces(qty1), countDecimalPlaces(qty2));

  // `countDecimalPlaces` inspects `String(num)`, and JS switches to exponential notation below 1e-6
  // ("1e-7" has no decimal point), so it reports 0 decimals for such a value. Rounding to 0 decimals
  // there would truncate the quantity away entirely, so leave a sum of non-integer operands unrounded.
  if (decimals === 0 && !(Number.isInteger(qty1) && Number.isInteger(qty2))) {
    return sum;
  }

  return parseFloat(sum.toFixed(decimals));
};

const addCatchWeightToMap = (catchWeightsMap, catchWeight) => {
  if (!catchWeight) {
    return;
  }

  const existingCatchWeightForUOM = catchWeightsMap[catchWeight.uom];
  if (!existingCatchWeightForUOM) {
    catchWeightsMap[catchWeight.uom] = catchWeight;
  } else {
    existingCatchWeightForUOM.qty = sumQtys(existingCatchWeightForUOM.qty, catchWeight.qty);
  }
};

export const formatCatchWeightToHumanReadableStr = (catchWeightParam) => {
  if (!catchWeightParam) {
    return '';
  }

  if (Array.isArray(catchWeightParam)) {
    if (!catchWeightParam?.length) {
      return '';
    } else {
      return catchWeightParam.map((catchWeight) => formatQtyToHumanReadableStr(catchWeight)).join(', ');
    }
  } else {
    return formatQtyToHumanReadableStr(catchWeightParam);
  }
};
