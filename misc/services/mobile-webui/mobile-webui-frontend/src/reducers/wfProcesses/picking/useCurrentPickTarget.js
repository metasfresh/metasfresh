import { shallowEqual, useSelector } from 'react-redux';
import { getActivityById, getLineByIdFromActivity } from '../index';
import { isLineLevelPickTarget } from '../../../utils/picking';

export const useCurrentPickingTargetInfo = ({ wfProcessId, activityId, lineId }) => {
  return useSelector((state) => getCurrentPickingTargetInfo({ state, wfProcessId, activityId, lineId }), shallowEqual);
};

const getCurrentPickingTargetInfo = ({ state, wfProcessId, activityId, lineId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);

  let isAllowReopeningLU = false;
  let isPickWithNewLU = false;
  let isAllowNewTU = false;
  let luPickingTarget = null;
  let tuPickingTarget = null;

  //
  // Picking Job Line level
  if (lineId) {
    const line = getLineByIdFromActivity(activity, lineId);

    if (isLineLevelPickTarget({ activity })) {
      luPickingTarget = line?.luPickingTarget;
      tuPickingTarget = line?.tuPickingTarget;
      isPickWithNewLU = activity.dataStored.isPickWithNewLU;
      isAllowReopeningLU = isPickWithNewLU;
      isAllowNewTU = activity.dataStored.isAllowNewTU;
    }
  }
  //
  // Picking Job header level
  else {
    luPickingTarget = activity?.dataStored?.luPickingTarget;
    tuPickingTarget = activity?.dataStored?.tuPickingTarget;

    if (isLineLevelPickTarget({ activity })) {
      isAllowReopeningLU = false;
      isPickWithNewLU = true;
      isAllowNewTU = true;
    } else {
      isPickWithNewLU = activity.dataStored.isPickWithNewLU;
      isAllowReopeningLU = isPickWithNewLU;
      isAllowNewTU = activity.dataStored.isAllowNewTU;
    }
  }

  return {
    isAllowReopeningLU,
    isPickWithNewLU,
    isLUScanRequiredAndMissing: isPickWithNewLU && !luPickingTarget,
    isAllowNewTU,
    luPickingTarget,
    tuPickingTarget,
  };
};
