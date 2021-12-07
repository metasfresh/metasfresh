export const getPickFrom = (props) => {
  const { stepProps, altStepId } = props;
  return altStepId ? stepProps.pickFromAlternatives[altStepId] : stepProps.mainPickFrom;
};

export const getQtyToPick = (props) => {
  const { stepProps, altStepId } = props;
  return altStepId ? getPickFrom(props).qtyToPick : stepProps.qtyToPick;
};
