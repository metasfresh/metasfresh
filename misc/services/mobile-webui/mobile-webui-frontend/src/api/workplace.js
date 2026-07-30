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
import { useCallback, useEffect, useState } from 'react';
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
  const [loadErrorMessage, setLoadErrorMessage] = useState(null);

  const reloadWorkplace = useCallback(() => {
    setIsLoading(true);
    setLoadErrorMessage(null);
    getCurrentWorkplaceInfo()
      .then(({ workplaceRequired, assignedWorkplace }) => {
        setIsWorkplaceRequired(workplaceRequired);
        setWorkplace(assignedWorkplace);
      })
      .catch((axiosError) => {
        // A server response means the backend answered and retrying the same read won't help, so keep the
        // toast (existing automation asserts on it). No response at all is the routine warehouse-WiFi blip:
        // leaving the workplace unset blanks the header row for good — the screen never re-reads it — so
        // surface it for retry instead.
        const isNetworkFailure = !axiosError?.response;
        const message = extractUserFriendlyErrorMessageFromAxiosError({ axiosError });
        uiTrace.trace({
          eventName: 'currentWorkplaceLoadFailed',
          httpStatus: axiosError?.response?.status ?? null,
          axiosCode: axiosError?.code ?? null,
          isNetworkFailure,
          message,
        });
        if (isNetworkFailure) {
          setLoadErrorMessage(message);
        } else {
          toastError({ axiosError });
        }
      })
      .finally(() => setIsLoading(false));
  }, []);

  useEffect(() => {
    reloadWorkplace();
  }, [reloadWorkplace]);

  const setWorkplaceByQRCode = (qrCode) => {
    const { workplaceId } = parseWorkplaceQRCodeString(qrCode);
    assignWorkplace(workplaceId)
      .then((workplace) => setWorkplace(workplace))
      .catch((axiosError) => toastError({ axiosError }));
  };

  return {
    isWorkplaceLoading: isLoading,
    isWorkplaceRequired: requiresWorkplaceIfAvailable && isWorkplaceRequired,
    workplace,
    workplaceLoadErrorMessage: loadErrorMessage,
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
