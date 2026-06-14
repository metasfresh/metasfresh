import { useCallback, useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';

import { toastError } from '../../../utils/toast';
import { getPickingGRAIs, setPickingGRAIs } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';

/**
 * In-picking GRAI mass-capture hook, adapted from the HU-Manager `useGrais` pattern but bound to the
 * picked LU of a picking job and talking to the picking-scoped GRAI endpoints (NOT the hu-manager ones).
 *
 * - loads the current GRAIs + expected count (`tuCount` = N picked TUs) from the backend
 * - accumulates a deduped GRAI list (dedup within-list AND against the GRAIs already on the LU)
 * - on save: PUTs the captured GRAIs, dispatches the refreshed workflow process to redux, then calls `onSaved`
 *
 * The save action is only meaningful when exactly N GRAIs are captured; the caller (screen) enforces that
 * via `canSave` on the button.
 */
export const usePickingGrais = ({ wfProcessId, huId, onSaved }) => {
  const dispatch = useDispatch();

  const [graiCodes, setGraiCodes] = useState([]);
  const [tuCount, setTuCount] = useState(0);
  const [loading, setLoading] = useState(true);
  const [dirty, setDirty] = useState(false);
  const [saving, setSaving] = useState(false);

  // Load the current GRAIs + expected count from the backend
  useEffect(() => {
    if (!wfProcessId || !huId) return;
    getPickingGRAIs({ wfProcessId, huId })
      .then((response) => {
        setGraiCodes(response.graiCodes || []);
        setTuCount(response.tuCount || 0);
        setDirty(false);
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setLoading(false));
  }, [wfProcessId, huId]);

  const addGrais = useCallback((newGrais) => {
    setGraiCodes((prev) => mergeGraiArrays(prev, newGrais));
    setDirty(true);
  }, []);

  const removeGrai = useCallback((graiToRemove) => {
    setGraiCodes((prev) => prev.filter((g) => g !== graiToRemove));
    setDirty(true);
  }, []);

  const clearAllGrais = useCallback(() => {
    setGraiCodes([]);
    setDirty(true);
  }, []);

  const save = useCallback(() => {
    if (!wfProcessId || !huId) return;
    setSaving(true);
    setPickingGRAIs({ wfProcessId, huId, graiCodes: getAssignedGrais(graiCodes, tuCount) })
      .then((wfProcess) => {
        dispatch(updateWFProcess({ wfProcess }));
        setDirty(false);
        onSaved?.();
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setSaving(false));
  }, [wfProcessId, huId, graiCodes, tuCount, dispatch, onSaved]);

  const assignedGrais = getAssignedGrais(graiCodes, tuCount);
  const extraGrais = getExtraGrais(graiCodes, tuCount);
  // The save button is enabled only when the captured list has changed (dirty) AND exactly
  // N (=tuCount) GRAIs are captured. Without `dirty`, re-entering a fully-captured LU would light
  // up "Speichern" before the operator did anything and allow a no-op re-save.
  const canSave = dirty && tuCount > 0 && graiCodes.length === tuCount && !saving;

  return {
    graiCodes,
    assignedGrais,
    extraGrais,
    tuCount,
    loading,
    dirty,
    saving,
    canSave,
    addGrais,
    removeGrai,
    clearAllGrais,
    save,
  };
};

//
//
//

const getAssignedGrais = (graiCodes, tuCount) => (tuCount > 0 ? graiCodes.slice(0, tuCount) : graiCodes);
const getExtraGrais = (graiCodes, tuCount) => (tuCount > 0 ? graiCodes.slice(tuCount) : []);

const mergeGraiArrays = (prev, newGrais) => {
  const existingSet = new Set(prev);
  const toAdd = newGrais.filter((g) => !existingSet.has(g));
  if (toAdd.length === 0) return prev;
  return [...prev, ...toAdd];
};
