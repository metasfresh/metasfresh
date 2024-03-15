export const getPickFromForStep = ({ stepProps, altStepId }) => {
  return altStepId ? stepProps.pickFromAlternatives[altStepId] : stepProps.mainPickFrom;
};

export const getQtyToPickForStep = ({ stepProps, altStepId }) => {
  return altStepId ? getPickFromForStep({ stepProps, altStepId }).qtyToPick : stepProps.qtyToPick;
};
