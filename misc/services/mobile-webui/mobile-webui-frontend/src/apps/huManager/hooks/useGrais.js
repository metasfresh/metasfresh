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
  // Count the user's edits and the reloads from the backend, so that the answer to a Send can tell
  // whether the list was changed while the request was in flight (the user does not have to wait).
  const editCountRef = useRef(0);
  const reloadCountRef = useRef(0);

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

  const sendToBackend = useCallback(() => {
    if (!huId) return;
    setSending(true);
    const sentGrais = graiCodes;
    const editCountAtSend = editCountRef.current;
    const reloadCountAtSend = reloadCountRef.current;
    api
      .setGRAIs(huId, getAssignedGrais(graiCodes, tuCount))
      .then((response) => {
        const backendGrais = response.graiCodes || [];
        const newTuCount = response.tuCount || 0;
        // Undo (reload) while sending discarded the local edits: show what the backend saved.
        const reloadedSinceSend = reloadCountRef.current !== reloadCountAtSend;
        const editedSinceSend = !reloadedSinceSend && editCountRef.current !== editCountAtSend;
        setTuCount(newTuCount);
        setGraiCodes((currentGrais) =>
          mergeGraisAfterSend({
            sentGrais: reloadedSinceSend ? currentGrais : sentGrais,
            currentGrais,
            backendGrais,
            tuCount: newTuCount,
          })
        );
        // Changes made while the request was in flight are not saved yet, so Send and Undo stay enabled.
        setDirty(editedSinceSend);
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setSending(false));
  }, [huId, graiCodes, tuCount]);

  const loadFromBackend = useCallback(() => {
    if (!huId) return;
    reloadCountRef.current += 1;
    api
      .getGRAIs(huId)
      .then((response) => {
        setGraiCodes(response.graiCodes || []);
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
