import {
  PICKTO_STRUCTURE_CU,
  PICKTO_STRUCTURE_LU_CU,
  PICKTO_STRUCTURE_LU_TU,
  PICKTO_STRUCTURE_TU,
} from './PickToStructure';

export const isCurrentTargetEligibleForLine = ({ line, luPickingTarget, tuPickingTarget, allowedPickToStructures }) => {
  const linePickingUnit = line.pickingUnit;

  return allowedPickToStructures.some((pickToStructure) => {
    if (pickToStructure === PICKTO_STRUCTURE_LU_TU) {
      if (luPickingTarget == null) return false; // expect LU target to be set

      return linePickingUnit === 'TU'
        ? tuPickingTarget == null // expect TU target to not be set because the line is bringing TUs directly
        : tuPickingTarget != null; // expect TU target to be set because the line is bringing CUs
    } else if (pickToStructure === PICKTO_STRUCTURE_TU) {
      if (luPickingTarget != null) return false; // expect LU target to NOT be set

      return linePickingUnit === 'TU'
        ? tuPickingTarget == null // expect TU target to not be set because the line is bringing TUs directly
        : tuPickingTarget != null; // expect TU target to be set because the line is bringing CUs
    } else if (pickToStructure === PICKTO_STRUCTURE_LU_CU) {
      if (luPickingTarget == null) return false; // expect LU target to be set

      return linePickingUnit === 'TU'
        ? false // LU/CU structure is not supported if the line is bringing TUs
        : tuPickingTarget == null; // expect TU target to not be set because we want to load directly to LU/CUs
    } else if (pickToStructure === PICKTO_STRUCTURE_CU) {
      if (luPickingTarget != null) return false; // expect LU target to NOT be set
      if (tuPickingTarget != null) return false; // expect CU target to NOT be set

      return linePickingUnit === 'CU';
    } else {
      return false;
    }
  });
};
