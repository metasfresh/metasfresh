import { getStepsArrayFromLine } from '../index';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';

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

const addCatchWeightToMap = (catchWeightsMap, catchWeight) => {
  if (!catchWeight) {
    return;
  }

  const existingCatchWeightForUOM = catchWeightsMap[catchWeight.uom];
  if (!existingCatchWeightForUOM) {
    catchWeightsMap[catchWeight.uom] = catchWeight;
  } else {
    existingCatchWeightForUOM.qty += catchWeight.qty;
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
