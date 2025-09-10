import {
  PICKTO_STRUCTURE_CU,
  PICKTO_STRUCTURE_LU_CU,
  PICKTO_STRUCTURE_LU_TU,
  PICKTO_STRUCTURE_TU,
} from './PickToStructure';
import { getCurrentPickingTargetInfoFromActivity } from './useCurrentPickTarget';

export const isCurrentTargetEligibleForActivityAndLine = ({ activity, line }) => {
  const lineId = line?.pickingLineId;
  if (!lineId) return false;

  const { allowedPickToStructures, luPickingTarget, tuPickingTarget } = getCurrentPickingTargetInfoFromActivity({
    activity,
    lineId,
    fallbackToHeader: true,
  });
  //console.log('isCurrentTargetEligibleForActivityAndLine', { allowedPickToStructures, luPickingTarget, tuPickingTarget });

  return isCurrentTargetEligibleForLine({ line, allowedPickToStructures, luPickingTarget, tuPickingTarget });
};

export const isCurrentTargetEligibleForLine = ({ line, luPickingTarget, tuPickingTarget, allowedPickToStructures }) => {
  if (!allowedPickToStructures?.length) {
    console.log('isCurrentTargetEligibleForLine: allowedPickToStructures is empty. Returning false.', {
      line,
      luPickingTarget,
      tuPickingTarget,
    });
    return false;
  }

  const linePickingUnit = line.pickingUnit;

  return allowedPickToStructures.some((pickToStructure) => {
    return isCurrentTargetEligibleForLine_SinglePickToStructure({
      pickToStructure,
      luPickingTarget,
      tuPickingTarget,
      linePickingUnit,
    });
  });
};

const isCurrentTargetEligibleForLine_SinglePickToStructure = ({
  pickToStructure,
  luPickingTarget,
  tuPickingTarget,
  linePickingUnit,
}) => {
  if (pickToStructure === PICKTO_STRUCTURE_LU_TU) {
    return isCurrentTargetEligibleForLine_LU_TU({ luPickingTarget, tuPickingTarget, linePickingUnit });
  } else if (pickToStructure === PICKTO_STRUCTURE_TU) {
    return isCurrentTargetEligibleForLine_TU({ luPickingTarget, tuPickingTarget, linePickingUnit });
  } else if (pickToStructure === PICKTO_STRUCTURE_LU_CU) {
    return isCurrentTargetEligibleForLine_LU_CU({ luPickingTarget, tuPickingTarget, linePickingUnit });
  } else if (pickToStructure === PICKTO_STRUCTURE_CU) {
    return isCurrentTargetEligibleForLine_CU({ luPickingTarget, tuPickingTarget, linePickingUnit });
  } else {
    return false;
  }
};

const isCurrentTargetEligibleForLine_LU_TU = ({ luPickingTarget, tuPickingTarget, linePickingUnit }) => {
  // expect LU target to be set
  if (luPickingTarget == null) return false;

  return linePickingUnit === 'TU'
    ? tuPickingTarget == null // expect TU target to not be set because the line is bringing TUs directly
    : tuPickingTarget != null; // expect TU target to be set because the line is bringing CUs
};

const isCurrentTargetEligibleForLine_TU = ({ luPickingTarget, tuPickingTarget, linePickingUnit }) => {
  if (luPickingTarget != null) return false; // expect LU target to NOT be set

  return linePickingUnit === 'TU'
    ? tuPickingTarget == null // expect TU target to not be set because the line is bringing TUs directly
    : tuPickingTarget != null; // expect TU target to be set because the line is bringing CUs
};

const isCurrentTargetEligibleForLine_LU_CU = ({ luPickingTarget, tuPickingTarget, linePickingUnit }) => {
  if (luPickingTarget == null) return false; // expect LU target to be set

  return linePickingUnit === 'TU'
    ? false // LU/CU structure is not supported if the line is bringing TUs
    : tuPickingTarget == null; // expect TU target to not be set because we want to load directly to LU/CUs
};

function isCurrentTargetEligibleForLine_CU({ luPickingTarget, tuPickingTarget, linePickingUnit }) {
  if (luPickingTarget != null) return false; // expect LU target to NOT be set
  if (tuPickingTarget != null) return false; // expect CU target to NOT be set

  return linePickingUnit === 'CU';
}
