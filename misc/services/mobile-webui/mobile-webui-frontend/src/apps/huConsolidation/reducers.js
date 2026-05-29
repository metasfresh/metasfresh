export const isUserEditable = ({ activity }) => {
  return activity?.dataStored?.isUserEditable ?? false;
};

export const getHUConsolidationJob = ({ activity }) => {
  return activity?.dataStored?.job ?? {};
};

export const getCurrentTarget = ({ activity }) => {
  return getHUConsolidationJob({ activity }).currentTarget;
};
export const getPickingSlots = ({ activity }) => {
  return getHUConsolidationJob({ activity }).pickingSlots ?? [];
};
export const getPickingSlotById = ({ activity, pickingSlotId }) => {
  return getPickingSlots({ activity }).find(
    (pickingSlot) => String(pickingSlot.pickingSlotId) === String(pickingSlotId)
  );
};
