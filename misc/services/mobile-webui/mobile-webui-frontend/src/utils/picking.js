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
