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

export const useCurrentPickingTargetInfo = ({ wfProcessId, activityId, lineId }) => {
  return useSelector((state) => getCurrentPickingTargetInfo({ state, wfProcessId, activityId, lineId }), shallowEqual);
};

const computeIsAllowReopeningLU = ({ allowedPickToStructures }) =>
  allowedPickToStructures.some(isLUBasedPickToStructure);

export const getCurrentPickingTargetInfo = ({ state, wfProcessId, activityId, lineId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);

  let luPickingTarget = null;
  let tuPickingTarget = null;

  let allowedPickToStructures = [];
  let isAllowReopeningLU = false;

  //
  // Picking Job Line level
  if (lineId) {
    const line = getLineByIdFromActivity(activity, lineId);

    if (isLineLevelPickTarget({ activity })) {
      luPickingTarget = line?.luPickingTarget;
      tuPickingTarget = line?.tuPickingTarget;
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
  };
};
