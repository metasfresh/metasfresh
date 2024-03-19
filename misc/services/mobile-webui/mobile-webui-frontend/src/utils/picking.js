import { getLinesArrayFromActivity } from '../reducers/wfProcesses';
import * as CompleteStatus from '../constants/CompleteStatus';

export const getPickFromForStep = ({ stepProps, altStepId }) => {
  return altStepId ? stepProps.pickFromAlternatives[altStepId] : stepProps.mainPickFrom;
};

export const getQtyToPickForStep = ({ stepProps, altStepId }) => {
  return altStepId ? getPickFromForStep({ stepProps, altStepId }).qtyToPick : stepProps.qtyToPick;
};

export const getQtyPickedOrRejectedTotalForLine = ({ line }) => {
  return Object.values(line.steps)
    .map((step) => step.mainPickFrom.qtyPicked + step.mainPickFrom.qtyRejected)
    .reduce((acc, qtyPickedOrRejected) => acc + qtyPickedOrRejected, 0);
};

export const getQtyToPickRemainingForLine = ({ line }) => {
  const qtyToPickRemaining = line.qtyToPick - getQtyPickedOrRejectedTotalForLine({ line });
  return qtyToPickRemaining > 0 ? qtyToPickRemaining : 0;
};

export const getNextEligibleLineToPick = ({ activity, productId, excludeLineId }) => {
  let lines = getLinesArrayFromActivity(activity).filter(
    (line) => isLineNotCompleted({ line }) && isAllowPickingAnyHUForLine({ line })
  );

  if (lines.length > 0 && productId) {
    lines = lines.filter((line) => String(line.productId) === String(productId));
  }
  if (lines.length > 0 && excludeLineId) {
    lines = lines.filter((line) => String(line.pickingLineId) !== String(excludeLineId));
  }

  return lines.length > 0 ? lines[0] : null;
};

export const isAllowPickingAnyHUForActivity = ({ activity }) => {
  return getLinesArrayFromActivity(activity).some((line) => isAllowPickingAnyHUForLine({ line }));
};

export const isAllowPickingAnyHUForLine = ({ line }) => {
  return !!line?.allowPickingAnyHU;
};

export const isLineNotCompleted = ({ line }) => line.completeStatus !== CompleteStatus.COMPLETED;
