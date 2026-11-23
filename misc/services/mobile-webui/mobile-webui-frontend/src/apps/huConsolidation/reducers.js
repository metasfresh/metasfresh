export const isUserEditable = ({ activity }) => {
  return activity?.dataStored?.isUserEditable ?? false;
};

export const getHUConsolidationJob = ({ activity }) => {
  return activity?.dataStored?.job ?? {};
};

/** Whether the job was already loaded into the activity (it is not, e.g., right after a reload of a screen). */
export const isHUConsolidationJobLoaded = ({ activity }) => {
  return activity?.dataStored?.job != null;
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

/** Whether the backend enables the GRAI scan for the job. */
export const isGRAIScanEnabled = ({ activity }) => {
  return getHUConsolidationJob({ activity }).graiScanEnabled ?? false;
};
