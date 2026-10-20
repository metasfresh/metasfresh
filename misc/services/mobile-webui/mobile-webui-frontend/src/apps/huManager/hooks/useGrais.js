import { useCallback, useEffect, useRef, useState } from 'react';

import { toastError } from '../../../utils/toast';
import { getAssignedGrais, getExtraGrais, mergeGraiArrays, mergeGraisAfterSend } from '../../../utils/grai';
import * as api from '../api';

export const useGrais = ({ huId }) => {
  const [graiCodes, setGraiCodes] = useState([]);
  const [tuCount, setTuCount] = useState(0);
  const [loading, setLoading] = useState(true);
  const [dirty, setDirty] = useState(false);
  const [sending, setSending] = useState(false);
  // Count the user's edits, so that the answer to a Send can tell whether the list was changed while
  // the request was in flight (the user does not have to wait for it).
  const editCountRef = useRef(0);
  // The list shown by the latest successful reload (Undo), and the edit count at that moment.
  const lastReloadRef = useRef(null);

  const markEdited = useCallback(() => {
    editCountRef.current += 1;
    setDirty(true);
  }, []);

  // Load initial state from backend
  useEffect(() => {
    if (!huId) return;
    api
      .getGRAIs(huId)
      .then((response) => {
        setGraiCodes(response.graiCodes || []);
        setTuCount(response.tuCount || 0);
        setDirty(false);
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setLoading(false));
  }, [huId]);

  const addGrais = useCallback(
    (newGrais) => {
      setGraiCodes((prev) => mergeGraiArrays(prev, newGrais).merged);
      markEdited();
    },
    [markEdited]
  );

  const removeGrai = useCallback(
    (graiToRemove) => {
      setGraiCodes((prev) => prev.filter((g) => g !== graiToRemove));
      markEdited();
    },
    [markEdited]
  );

  const clearAllGrais = useCallback(() => {
    setGraiCodes([]);
    markEdited();
  }, [markEdited]);

  /**
   * Send the assigned GRAIs to the backend. The user may keep scanning, removing or tapping Undo
   * while the request is in flight; the answer is merged into the list on screen, and the screen
   * stays unsaved (`dirty`) when the user changed anything after the send (or after an Undo made
   * meanwhile).
   */
  const sendToBackend = useCallback(() => {
    if (!huId) return;
    setSending(true);
    const sendBaseline = { grais: graiCodes, editCount: editCountRef.current };
    const reloadAtSend = lastReloadRef.current;
    api
      .setGRAIs(huId, getAssignedGrais(graiCodes, tuCount))
      .then((response) => {
        const backendGrais = response.graiCodes || [];
        const newTuCount = response.tuCount || 0;
        // An Undo while sending discarded the edits made before it: count only the edits made after it.
        const baseline = lastReloadRef.current !== reloadAtSend ? lastReloadRef.current : sendBaseline;
        const editedSinceBaseline = editCountRef.current !== baseline.editCount;
        setTuCount(newTuCount);
        setGraiCodes((currentGrais) =>
          mergeGraisAfterSend({
            baselineGrais: baseline.grais,
            currentGrais,
            backendGrais,
            tuCount: newTuCount,
          })
        );
        // Changes made while the request was in flight are not saved yet, so Send and Undo stay enabled.
        setDirty(editedSinceBaseline);
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setSending(false));
  }, [huId, graiCodes, tuCount]);

  const loadFromBackend = useCallback(() => {
    if (!huId) return;
    api
      .getGRAIs(huId)
      .then((response) => {
        const grais = response.graiCodes || [];
        lastReloadRef.current = { grais, editCount: editCountRef.current };
        setGraiCodes(grais);
        setTuCount(response.tuCount || 0);
        setDirty(false);
      })
      .catch((axiosError) => toastError({ axiosError }));
  }, [huId]);

  const assignedGrais = getAssignedGrais(graiCodes, tuCount);
  const extraGrais = getExtraGrais(graiCodes, tuCount);

  return {
    graiCodes,
    assignedGrais,
    extraGrais,
    tuCount,
    loading,
    dirty,
    sending,
    addGrais,
    removeGrai,
    clearAllGrais,
    sendToBackend,
    loadFromBackend,
  };
};
