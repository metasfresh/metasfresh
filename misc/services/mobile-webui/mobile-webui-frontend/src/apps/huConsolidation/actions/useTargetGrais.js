import { useCallback, useState } from 'react';
import { useDispatch } from 'react-redux';
import * as api from '../api';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { toastError } from '../../../utils/toast';
import { mergeGraiArrays } from '../../../utils/grai';

export const useTargetGrais = ({ wfProcessId }) => {
  const dispatch = useDispatch();
  const [graiCodes, setGraiCodes] = useState([]);
  const [isSending, setIsSending] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const loadFromBackend = useCallback(() => {
    setIsLoading(true);
    return api
      .getTargetGrais({ wfProcessId })
      .then((result) => {
        setGraiCodes(result?.graiCodes ?? []);
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setIsLoading(false));
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
    setIsSending(true);
    return api
      .setTargetGrais({ wfProcessId, graiCodes })
      .then((wfProcess) => {
        if (wfProcess) {
          dispatch(updateWFProcess({ wfProcess }));
        }
      })
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => setIsSending(false));
  }, [wfProcessId, graiCodes, dispatch]);

  return {
    graiCodes,
    assignedGrais: graiCodes,
    extraGrais: [],
    isLoading,
    isSending,
    addGrais,
    removeGrai,
    clearAllGrais,
    sendToBackend,
    loadFromBackend,
  };
};
