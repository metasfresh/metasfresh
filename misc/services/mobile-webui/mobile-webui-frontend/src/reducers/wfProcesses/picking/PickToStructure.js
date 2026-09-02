// NOTE to dev: keep in sync with de.metas.handlingunits.picking.config.mobileui.PickToStructure
export const PICKTO_STRUCTURE_LU_TU = 'LU_TU';
export const PICKTO_STRUCTURE_TU = 'TU';
export const PICKTO_STRUCTURE_LU_CU = 'LU_CU';
export const PICKTO_STRUCTURE_CU = 'CU';

export const isLUBasedPickToStructure = (pickToStructure) =>
  pickToStructure === PICKTO_STRUCTURE_LU_TU || pickToStructure === PICKTO_STRUCTURE_LU_CU;
export const isTUBasedPickToStructure = (pickToStructure) =>
  pickToStructure === PICKTO_STRUCTURE_LU_TU || pickToStructure === PICKTO_STRUCTURE_TU;
export const isCUBasedPickToStructure = (pickToStructure) =>
  pickToStructure === PICKTO_STRUCTURE_LU_CU || pickToStructure === PICKTO_STRUCTURE_CU;

export const addPickToStructure = (pickToStructures, pickToStructure) => {
  if (pickToStructures.includes(pickToStructure)) {
    return pickToStructures;
  } else {
    return [...pickToStructures, pickToStructure];
  }
};

export const removePickToStructure = (pickToStructures, pickToStructure) => {
  return pickToStructures.filter((pickToStructureFromList) => pickToStructureFromList !== pickToStructure);
};
