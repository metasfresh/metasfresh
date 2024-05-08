export const getPickFrom = ({ stepProps, altStepId }) => {
  return altStepId ? stepProps.pickFromAlternatives[altStepId] : stepProps.mainPickFrom;
};

export const getQtyToPick = ({ stepProps, altStepId }) => {
  return altStepId ? getPickFrom({ stepProps, altStepId }).qtyToPick : stepProps.qtyToPick;
};
