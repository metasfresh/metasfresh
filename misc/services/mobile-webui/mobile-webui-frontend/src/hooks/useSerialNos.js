import { useCallback, useState } from 'react';

/**
 * Local state for capturing one serial per picked unit (no backend round-trip — the serials
 * ride the PICK event payload). Mirrors the GRAI multi-scan model (`apps/huManager/hooks/useGrais`):
 * dedup-by-Set on add, and a `requiredCount` split into `assigned` (first N) vs `extra` (beyond N)
 * so the dialog can show "X of N" and block extras. `requiredCount` is the pick quantity.
 */
export const useSerialNos = ({ requiredCount = 0, initialSerialNos = [] } = {}) => {
  const [serialNos, setSerialNos] = useState(initialSerialNos);

  const addSerialNos = useCallback((newSerialNos) => {
    setSerialNos((prev) => mergeSerialNoArrays(prev, newSerialNos));
  }, []);

  const removeSerialNo = useCallback((serialNoToRemove) => {
    setSerialNos((prev) => prev.filter((sn) => sn !== serialNoToRemove));
  }, []);

  const clearAllSerialNos = useCallback(() => setSerialNos([]), []);

  const assignedSerialNos = getAssigned(serialNos, requiredCount);
  const extraSerialNos = getExtra(serialNos, requiredCount);
  const isComplete = requiredCount > 0 && serialNos.length === requiredCount;

  return {
    serialNos,
    assignedSerialNos,
    extraSerialNos,
    isComplete,
    addSerialNos,
    removeSerialNo,
    clearAllSerialNos,
  };
};

//
//
//

const getAssigned = (serialNos, requiredCount) => (requiredCount > 0 ? serialNos.slice(0, requiredCount) : serialNos);

const getExtra = (serialNos, requiredCount) => (requiredCount > 0 ? serialNos.slice(requiredCount) : []);

const mergeSerialNoArrays = (prev, newSerialNos) => {
  const existing = new Set(prev);
  const toAdd = newSerialNos.filter((sn) => sn && !existing.has(sn));
  if (toAdd.length === 0) return prev; // silent dedup — duplicate scans don't grow the count
  return [...prev, ...toAdd];
};
