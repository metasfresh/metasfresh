import { useCallback, useEffect, useState } from 'react';

import { toastError } from '../../../utils/toast';
import * as api from '../api';

export const useGrais = ({ huId }) => {
  const [graiCodes, setGraiCodes] = useState([]);
  const [tuCount, setTuCount] = useState(0);
  const [loading, setLoading] = useState(true);
  const [dirty, setDirty] = useState(false);
  const [sending, setSending] = useState(false);

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

  const sendToBackend = useCallback(() => {
    if (!huId) return;
    setSending(true);
    api
      .setGRAIs(huId, getAssignedGrais(graiCodes, tuCount))
      .then((response) => {
        const backendGrais = response.graiCodes || [];
        const newTuCount = response.tuCount || 0;
        setTuCount(newTuCount);
        setGraiCodes((prev) => {
          const currentExtras = newTuCount > 0 ? prev.slice(newTuCount) : [];
          return [...backendGrais, ...currentExtras];
        });
        setDirty(false);
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setSending(false));
  }, [huId, graiCodes, tuCount]);

  const loadFromBackend = useCallback(() => {
    if (!huId) return;
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

//
//
//
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
