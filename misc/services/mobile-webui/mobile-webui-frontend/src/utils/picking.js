import { getLinesArrayFromActivity } from '../reducers/wfProcesses';
import * as CompleteStatus from '../constants/CompleteStatus';

export const getPickFromForStep = ({ stepProps, altStepId }) => {
  return altStepId ? stepProps.pickFromAlternatives[altStepId] : stepProps.mainPickFrom;
};

export const getQtyToPickForStep = ({ stepProps, altStepId }) => {
  return altStepId ? getPickFromForStep({ stepProps, altStepId }).qtyToPick : stepProps.qtyToPick;
};

export const getQtyToPickForLine = ({ line }) => {
  return line?.qtyToPick ?? 0;
};

export const getQtyPickedOrRejectedTotalForLine = ({ line }) => {
  return line?.qtyPickedOrRejected ?? 0;
};

export const getQtyToPickRemainingForLine = ({ line }) => {
  return line?.qtyRemainingToPick ?? 0;
};

export const getNextEligibleLineToPick = ({ activity, productId, excludeLineId }) => {
  console.group('getNextEligibleLineToPick', { activity, productId, excludeLineId });
  let lines = getLinesArrayFromActivity(activity);
  console.log('all activity lines', { lines });

  lines = lines.filter((line) => isLineNotCompleted({ line }) && isAllowPickingAnyHUForLine({ line }));
  console.log('lines not completed', { lines });

  if (lines.length > 0 && productId) {
    lines = lines.filter((line) => String(line.productId) === String(productId));
    console.log('lines matching product', { lines, productId });
  }

  if (lines.length > 0 && excludeLineId) {
    lines = lines.filter((line) => String(line.pickingLineId) !== String(excludeLineId));
    console.log('lines excluding given line', { lines, excludeLineId });
  }

  console.groupEnd();

  return lines.length > 0 ? lines[0] : null;
};

export const isAllowPickingAnyHUForActivity = ({ activity }) => {
  return getLinesArrayFromActivity(activity).some((line) => isAllowPickingAnyHUForLine({ line }));
};

export const isAllowPickingAnyHUForLine = ({ line }) => {
  return !!line?.allowPickingAnyHU;
};

export const isLineNotCompleted = ({ line }) => line.completeStatus !== CompleteStatus.COMPLETED;
