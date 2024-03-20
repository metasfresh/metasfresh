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

export const getLinesByProductId = (activity, productId) => {
  const lines = getLinesArrayFromActivity(activity);
  return lines.filter((line) => String(line.productId) === String(productId));
};

export const isAllowPickingAnyHUForActivity = ({ activity }) => {
  return getLinesArrayFromActivity(activity).some((line) => isAllowPickingAnyHUForLine({ line }));
};

export const isAllowPickingAnyHUForLine = ({ line }) => {
  return !!line?.allowPickingAnyHU;
};

export const isLineNotCompleted = ({ line }) => line.completeStatus !== CompleteStatus.COMPLETED;
