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

/**
 * Whether the Scan (GRAI) button was shown at any time in this job.
 * Latched: once the backend sent graiScanEnabled=true it stays true, so the screen can keep the button's place
 * (an invisible slot) after the last GRAI crate is gone and the button vanishes; the buttons below must not move under the worker's finger.
 */
export const wasGRAIScanButtonShown = ({ activity }) => {
  return activity?.dataStored?.graiScanButtonShown ?? false;
};

/** Stores the job sent by the backend into the activity's data and keeps the "button was shown" latch (see {@link wasGRAIScanButtonShown}). */
export const mergeJobIntoActivityDataStored = ({ draftActivityDataStored, job }) => {
  draftActivityDataStored.job = job;
  draftActivityDataStored.graiScanButtonShown = !!draftActivityDataStored.graiScanButtonShown || !!job?.graiScanEnabled;
};
