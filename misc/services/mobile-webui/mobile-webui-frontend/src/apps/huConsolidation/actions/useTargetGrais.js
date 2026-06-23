import { useCallback, useState } from 'react';
import { useDispatch } from 'react-redux';
import * as api from '../api';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { toastError } from '../../../utils/toast';
import { mergeGraiArrays } from '../../../utils/grai';

export const useTargetGrais = ({ wfProcessId }) => {
  const dispatch = useDispatch();
  const [graiCodes, setGraiCodes] = useState([]);
  const [sending, setSending] = useState(false);
  const [loading, setLoading] = useState(false);

  const loadFromBackend = useCallback(() => {
    setLoading(true);
    return api
      .getTargetGrais({ wfProcessId })
      .then((result) => {
        setGraiCodes(result?.graiCodes ?? []);
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setLoading(false));
  }, [wfProcessId]);

  const addGrais = useCallback((newGrais) => {
    setGraiCodes((prev) => mergeGraiArrays(prev, newGrais));
  }, []);

  const removeGrai = useCallback((grai) => {
    setGraiCodes((prev) => prev.filter((g) => g !== grai));
  }, []);

  const clearAllGrais = useCallback(() => {
    setGraiCodes([]);
  }, []);

  const sendToBackend = useCallback(() => {
    setSending(true);
    return api
      .setTargetGrais({ wfProcessId, graiCodes })
      .then((wfProcess) => {
        if (wfProcess) {
          dispatch(updateWFProcess({ wfProcess }));
        }
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setSending(false));
  }, [wfProcessId, graiCodes, dispatch]);

  return {
    graiCodes,
    assignedGrais: graiCodes,
    extraGrais: [],
    loading,
    sending,
    addGrais,
    removeGrai,
    clearAllGrais,
    sendToBackend,
    loadFromBackend,
  };
};
