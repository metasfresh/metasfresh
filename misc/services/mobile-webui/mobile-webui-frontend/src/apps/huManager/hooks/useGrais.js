import { useCallback, useEffect, useRef, useState } from 'react';

import { toastError } from '../../../utils/toast';
import * as api from '../api';

const SYNC_DEBOUNCE_MS = 500;

export const useGrais = ({ huId }) => {
  const [graiCodes, setGraiCodes] = useState([]);
  const [tuCount, setTuCount] = useState(0);
  const [loading, setLoading] = useState(true);

  // Ref always holds the latest graiCodes so the debounced sync reads current state
  const graiCodesRef = useRef(graiCodes);
  graiCodesRef.current = graiCodes;

  const syncTimerRef = useRef(null);

  // Apply the backend response (source of truth) to local state
  const applyBackendResponse = useCallback((response) => {
    setGraiCodes(response.graiCodes || []);
    setTuCount(response.tuCount || 0);
  }, []);

  // Debounced sync: waits for scanning to settle, then sends the latest state to backend.
  // Reads from graiCodesRef at fire time (not at schedule time) to always send the latest list.
  const scheduleSyncToBackend = useCallback(() => {
    if (!huId) return;

    clearTimeout(syncTimerRef.current);
    syncTimerRef.current = setTimeout(() => {
      api
        .setGRAIs(huId, graiCodesRef.current)
        .then(applyBackendResponse)
        .catch((axiosError) => toastError({ axiosError }));
    }, SYNC_DEBOUNCE_MS);
  }, [huId, applyBackendResponse]);

  // Cleanup debounce timer on unmount
  useEffect(() => {
    return () => clearTimeout(syncTimerRef.current);
  }, []);

  // Load initial state from backend
  useEffect(() => {
    if (!huId) return;

    api
      .getGRAIs(huId)
      .then(applyBackendResponse)
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setLoading(false));
  }, [huId, applyBackendResponse]);

  // Add multiple GRAIs at once (from RFID batch or single barcode scan).
  // Updates local state immediately (optimistic UI), then schedules one debounced backend sync.
  const addGrais = useCallback(
    (newGrais) => {
      setGraiCodes((prev) => mergeGraiArrays(prev, newGrais));
      scheduleSyncToBackend();
    },
    [scheduleSyncToBackend]
  );

  const removeGrai = useCallback(
    (graiToRemove) => {
      setGraiCodes((prev) => prev.filter((g) => g !== graiToRemove));
      scheduleSyncToBackend();
    },
    [scheduleSyncToBackend]
  );

  return { graiCodes, tuCount, loading, addGrais, removeGrai };
};

//
//
//
//
//
//

const mergeGraiArrays = (prev, newGrais) => {
  const existingSet = new Set(prev);
  const toAdd = newGrais.filter((g) => !existingSet.has(g));
  if (toAdd.length === 0) return prev;
  return [...prev, ...toAdd];
};
