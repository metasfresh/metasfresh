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
import { toastError } from '../utils/toast';
import { useEffect, useState } from 'react';
import { parseWorkplaceQRCodeString } from '../utils/qrCode/workplace';
import { useApplicationInfo } from '../reducers/applications';

const workplaceAPIBase = `${apiBasePath}/workplace`;

export const getCurrentWorkplaceInfo = () => {
  return axios.get(`${workplaceAPIBase}`).then(unboxAxiosResponse);
};

export const useCurrentWorkplace = ({ applicationId }) => {
  const { requiresWorkplace: requiresWorkplaceIfAvailable } = useApplicationInfo({ applicationId });
  const [isLoading, setIsLoading] = useState(true);
  const [isWorkplaceRequired, setIsWorkplaceRequired] = useState(false);
  const [workplace, setWorkplace] = useState(null);

  useEffect(() => {
    if (requiresWorkplaceIfAvailable) {
      setIsLoading(true);
      getCurrentWorkplaceInfo()
        .then(({ workplaceRequired, assignedWorkplace }) => {
          setIsWorkplaceRequired(workplaceRequired);
          setWorkplace(assignedWorkplace);
        })
        .catch((axiosError) => toastError({ axiosError }))
        .finally(() => setIsLoading(false));
    } else {
      setIsLoading(false);
    }
  }, []);

  const setWorkplaceByQRCode = (qrCode) => {
    const { workplaceId } = parseWorkplaceQRCodeString(qrCode);
    assignWorkplace(workplaceId)
      .then((workplace) => setWorkplace(workplace))
      .catch((axiosError) => toastError({ axiosError }));
  };

  return {
    isWorkplaceLoading: isLoading,
    isWorkplaceRequired,
    workplace,
    setWorkplaceByQRCode,
  };
};

export const getWorkplaceByQRCode = (qrCode) => {
  return axios.post(`${workplaceAPIBase}/byQRCode`, { qrCode }).then(unboxAxiosResponse);
};

export const assignWorkplace = (workplaceId) => {
  return axios.post(`${apiBasePath}/workplace/${workplaceId}/assign`).then(unboxAxiosResponse);
};
