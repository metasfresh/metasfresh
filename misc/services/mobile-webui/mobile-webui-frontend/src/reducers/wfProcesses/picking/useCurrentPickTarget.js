import { shallowEqual, useSelector } from 'react-redux';
import { getActivityById, getLineByIdFromActivity } from '../index';
import { isLineLevelPickTarget } from '../../../utils/picking';
import {
  addPickToStructure,
  isLUBasedPickToStructure,
  PICKTO_STRUCTURE_CU,
  PICKTO_STRUCTURE_LU_CU,
  PICKTO_STRUCTURE_TU,
  removePickToStructure,
} from './PickToStructure';

export const useCurrentPickingTargetInfo = ({ wfProcessId, activityId, lineId, fallbackToHeader = false }) => {
  return useSelector(
    (state) => getCurrentPickingTargetInfo({ state, wfProcessId, activityId, lineId, fallbackToHeader }),
    shallowEqual
  );
};

const computeIsAllowReopeningLU = ({ allowedPickToStructures }) =>
  allowedPickToStructures.some(isLUBasedPickToStructure);

const getCurrentPickingTargetInfo = ({ state, wfProcessId, activityId, lineId, fallbackToHeader = false }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  return getCurrentPickingTargetInfoFromActivity({ activity, lineId, fallbackToHeader });
};

export const getCurrentPickingTargetInfoFromActivity = ({ activity, lineId, fallbackToHeader = false }) => {
  let luPickingTarget;
  let tuPickingTarget;
  let line;

  let allowedPickToStructures;
  let isAllowReopeningLU;

  //
  // Picking Job Line level
  if (lineId) {
    // Resolve the line whenever a lineId is in scope, so the line's own carrier fields
    // (carrierProductCaption / carrierAdviseAvailable / carrierAdviseReadOnly) are read in the line
    // view regardless of the aggregation type. For a header-level job (SALES_ORDER) the lines can
    // carry different carriers while the job header carrier is null (divergent) — the worker must
    // still see the line's own carrier here. The LU/TU pick target still follows the pick-target
    // level below (only line-level for PRODUCT).
    line = getLineByIdFromActivity(activity, lineId);

    if (isLineLevelPickTarget({ activity })) {
      luPickingTarget = line?.luPickingTarget;
      tuPickingTarget = line?.tuPickingTarget;
      allowedPickToStructures = activity.dataStored.allowedPickToStructures;
      isAllowReopeningLU = computeIsAllowReopeningLU({ allowedPickToStructures });
    } else {
      luPickingTarget = null;
      tuPickingTarget = null;
      allowedPickToStructures = [];
      isAllowReopeningLU = false;
    }

    if (fallbackToHeader && luPickingTarget == null && tuPickingTarget == null) {
      luPickingTarget = activity?.dataStored?.luPickingTarget;
      tuPickingTarget = activity?.dataStored?.tuPickingTarget;
      allowedPickToStructures = activity.dataStored.allowedPickToStructures;
      isAllowReopeningLU = computeIsAllowReopeningLU({ allowedPickToStructures });
    }
  }
  //
  // Picking Job header level
  else {
    luPickingTarget = activity?.dataStored?.luPickingTarget;
    tuPickingTarget = activity?.dataStored?.tuPickingTarget;

    if (isLineLevelPickTarget({ activity })) {
      allowedPickToStructures = activity.dataStored.allowedPickToStructures;
      allowedPickToStructures = addPickToStructure(allowedPickToStructures, PICKTO_STRUCTURE_TU);
      allowedPickToStructures = removePickToStructure(allowedPickToStructures, PICKTO_STRUCTURE_LU_CU);
      allowedPickToStructures = removePickToStructure(allowedPickToStructures, PICKTO_STRUCTURE_CU);
      // console.log('getCurrentPickingTargetInfoFromActivity: line level allowedPickToStructures', {
      //   allowedPickToStructures_headerLevel: activity.dataStored.allowedPickToStructures,
      //   allwoedPickToStructures_lineLevel: allowedPickToStructures,
      // });
      isAllowReopeningLU = false;
    } else {
      allowedPickToStructures = activity.dataStored.allowedPickToStructures;
      isAllowReopeningLU = computeIsAllowReopeningLU({ allowedPickToStructures });
    }
  }

  return {
    allowedPickToStructures,
    isAllowReopeningLU,
    luPickingTarget,
    tuPickingTarget,
    lineCarrierAdviseAvailable: line?.carrierAdviseAvailable ?? false,
    lineCarrierAdviseReadOnly: line?.carrierAdviseReadOnly ?? false,
    lineCarrierProductCaption: line?.carrierProductCaption ?? null,
    lineCarrierAdviseDisabledReason: line?.carrierAdviseDisabledReason ?? null,
    // Job-level fallback (header-level CU-direct: no LU/TU target and no line in scope).
    jobCarrierAdviseAvailable: activity?.dataStored?.carrierAdviseAvailable ?? false,
    jobCarrierAdviseReadOnly: activity?.dataStored?.carrierAdviseReadOnly ?? false,
    jobCarrierProductCaption: activity?.dataStored?.carrierProductCaption ?? null,
    jobCarrierAdviseDisabledReason: activity?.dataStored?.carrierAdviseDisabledReason ?? null,
  };
};
