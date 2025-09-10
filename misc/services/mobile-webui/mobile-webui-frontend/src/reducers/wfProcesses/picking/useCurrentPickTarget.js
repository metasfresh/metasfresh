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

export const getCurrentPickingTargetInfo = ({ state, wfProcessId, activityId, lineId, fallbackToHeader = false }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  return getCurrentPickingTargetInfoFromActivity({ activity, lineId, fallbackToHeader });
};

export const getCurrentPickingTargetInfoFromActivity = ({ activity, lineId, fallbackToHeader = false }) => {
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
    } else if (fallbackToHeader) {
      luPickingTarget = activity?.dataStored?.luPickingTarget;
      tuPickingTarget = activity?.dataStored?.tuPickingTarget;
      allowedPickToStructures = activity.dataStored.allowedPickToStructures;
      isAllowReopeningLU = computeIsAllowReopeningLU({ allowedPickToStructures });
    } else {
      // console.log('getCurrentPickingTargetInfoFromActivity: considering empty allowedPickToStructures because was asked about line level but this line is not a line level picking', { line });
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
      console.log('getCurrentPickingTargetInfoFromActivity: line level allowedPickToStructures', {
        allowedPickToStructures_headerLevel: activity.dataStored.allowedPickToStructures,
        allwoedPickToStructures_lineLevel: allowedPickToStructures,
      });
      isAllowReopeningLU = false;
    } else {
      allowedPickToStructures = activity.dataStored.allowedPickToStructures;
      isAllowReopeningLU = computeIsAllowReopeningLU({ allowedPickToStructures });
    }
  }

  // // WARN if empty allowed Pick To structures
  // if (!allowedPickToStructures.length) {
  //   console.warn('getCurrentPickingTargetInfoFromActivity: returning empty allowedPickToStructures', {
  //     activity,
  //     lineId,
  //     luPickingTarget,
  //     tuPickingTarget,
  //     isAllowReopeningLU,
  //     allowedPickToStructures,
  //   });
  // }

  return {
    allowedPickToStructures,
    isAllowReopeningLU,
    luPickingTarget,
    tuPickingTarget,
  };
};
