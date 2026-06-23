export const isUserEditable = ({ activity }) => {
  return activity?.dataStored?.isUserEditable ?? false;
};

/**
 * Returns true when GRAI scanning is not required, or when all required GRAI slots
 * on the current target LU are filled.
 *
 * @param {object} job - the HU Consolidation job object from activity.dataStored.job
 * @returns {boolean}
 */
export const isGraiReady = (job) => {
  if (!job?.graiScanEnabled) {
    return true;
  }
  const target = job.currentTarget;
  if (!target) {
    // No target materialised yet — no slots to fill.
    return true;
  }
  const expected = target.graiExpectedCount ?? 0;
  if (expected === 0) {
    // LU not yet materialised as an existing HU — no slots to fill yet.
    return true;
  }
  const assigned = target.graiAssignedCount ?? 0;
  return assigned >= expected;
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
