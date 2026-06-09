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

const getCurrentPickingTargetInfo = ({ state, wfProcessId, activityId, lineId, fallbackToHeader = false }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  return getCurrentPickingTargetInfoFromActivity({ activity, lineId, fallbackToHeader });
};

export const getCurrentPickingTargetInfoFromActivity = ({ activity, lineId, fallbackToHeader = false }) => {
  let luPickingTarget;
  let tuPickingTarget;

  let allowedPickToStructures;
  let isAllowReopeningLU;

  //
  // Picking Job Line level
  if (lineId) {
    if (isLineLevelPickTarget({ activity })) {
      const line = getLineByIdFromActivity(activity, lineId);
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
  };
};
