/*
 * #%L
 * ic114
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import axios from 'axios';
import { unboxAxiosResponse } from '../utils';
import { apiBasePath } from '../constants';
import { extractUserFriendlyErrorMessageFromAxiosError, toastError } from '../utils/toast';
import { useCallback, useEffect, useRef, useState } from 'react';
import { parseWorkplaceQRCodeString } from '../utils/qrCode/workplace';
import { useApplicationInfo } from '../reducers/applications';
import * as uiTrace from '../utils/ui_trace';

const workplaceAPIBase = `${apiBasePath}/workplace`;

export const getCurrentWorkplaceInfo = () => {
  return axios.get(`${workplaceAPIBase}`).then(unboxAxiosResponse);
};

export const useCurrentWorkplace = ({ applicationId }) => {
  const { requiresWorkplace: requiresWorkplaceIfAvailable } = useApplicationInfo({ applicationId });
  const [isLoading, setIsLoading] = useState(true);
  const [isWorkplaceRequired, setIsWorkplaceRequired] = useState(false);
  const [workplace, setWorkplace] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);
  // What "Retry" has to re-run. Re-READING after a failed SCAN would drop the scan instead of
  // retrying it, putting the operator back in front of the scan prompt with no clue why, so the
  // operation that actually failed is remembered here.
  const retryFailedOperationRef = useRef(null);

  // A server response means the backend answered and retrying the same request won't help, so keep the
  // toast (existing automation asserts on it). No response at all is the routine warehouse-WiFi blip —
  // retrying may well succeed — so surface it inline with a retry instead.
  // Shape per module CLAUDE.md § "API Error Surfacing"; reference impl ConfirmActivity.jsx.
  const onRequestFailed = ({ axiosError, eventName, retry }) => {
    const isNetworkFailure = !axiosError?.response;
    const message = extractUserFriendlyErrorMessageFromAxiosError({ axiosError });
    uiTrace.trace({
      eventName,
      httpStatus: axiosError?.response?.status ?? null,
      axiosCode: axiosError?.code ?? null,
      isNetworkFailure,
      message,
    });
    if (isNetworkFailure) {
      retryFailedOperationRef.current = retry;
      setErrorMessage(message);
    } else {
      toastError({ axiosError });
    }
  };

  const reloadWorkplace = useCallback(() => {
    setIsLoading(true);
    setErrorMessage(null);
    getCurrentWorkplaceInfo()
      .then(({ workplaceRequired, assignedWorkplace }) => {
        setIsWorkplaceRequired(workplaceRequired);
        setWorkplace(assignedWorkplace);
      })
      .catch((axiosError) =>
        // Leaving the workplace unset blanks the header row for good — the screen never re-reads it.
        onRequestFailed({ axiosError, eventName: 'currentWorkplaceLoadFailed', retry: reloadWorkplace })
      )
      .finally(() => setIsLoading(false));
  }, []);

  useEffect(() => {
    reloadWorkplace();
  }, [reloadWorkplace]);

  const setWorkplaceByQRCode = (qrCode) => {
    const { workplaceId } = parseWorkplaceQRCodeString(qrCode);
    setErrorMessage(null);
    assignWorkplace(workplaceId)
      .then((workplace) => setWorkplace(workplace))
      .catch((axiosError) =>
        onRequestFailed({
          axiosError,
          eventName: 'assignWorkplaceFailed',
          retry: () => setWorkplaceByQRCode(qrCode),
        })
      );
  };

  // Re-runs whatever failed last, then forgets it so a gloved double-tap on Retry cannot fire the same
  // assign twice; that second tap falls back to a plain re-read rather than crashing on a null op.
  const retryWorkplace = () => {
    const retry = retryFailedOperationRef.current ?? reloadWorkplace;
    retryFailedOperationRef.current = null;
    retry();
  };

  return {
    isWorkplaceLoading: isLoading,
    isWorkplaceRequired: requiresWorkplaceIfAvailable && isWorkplaceRequired,
    workplace,
    workplaceErrorMessage: errorMessage,
    retryWorkplace,
    reloadWorkplace,
    setWorkplaceByQRCode,
  };
};

export const getWorkplaceByQRCode = (qrCode) => {
  return axios.post(`${workplaceAPIBase}/byQRCode`, { qrCode }).then(unboxAxiosResponse);
};

export const assignWorkplace = (workplaceId) => {
  return axios.post(`${apiBasePath}/workplace/${workplaceId}/assign`).then(unboxAxiosResponse);
};
