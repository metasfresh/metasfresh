import axios from 'axios';
import { useEffect, useState } from 'react';

export const getUserSession = () => {
  return axios.get(`${config.API_URL}/userSession`, {
    validateStatus: (status) => {
      // returning true so that we can get the error status
      return (status >= 200 && status < 300) || status === 502;
    },
  });
};

export const getUserLang = () => {
  return axios.get(`${config.API_URL}/userSession/language`);
};

export const useWorkplaces = ({ includeAvailable }) => {
  const [loading, setLoading] = useState(true);
  const [enabled, setEnabled] = useState(false);
  const [currentWorkplace, setCurrentWorkplaceInState] = useState([]);
  const [availableWorkplaces, setAvailableWorkplaces] = useState([]);

  useEffect(() => {
    setLoading(true);
    getWorkplaces({ includeAvailable })
      .then(({ workplacesEnabled, currentWorkplace, available }) => {
        setEnabled(workplacesEnabled);
        setCurrentWorkplaceInState(currentWorkplace);
        setAvailableWorkplaces(available ?? []);
      })
      .finally(() => setLoading(false));
  }, []);

  const changeCurrentWorkplace = (workplace) => {
    setWorkplaceId(workplace?.key);
    setCurrentWorkplaceInState(workplace);
  };

  return {
    isWorkplacesLoading: loading,
    isWorkplacesEnabled: enabled,
    currentWorkplace,
    availableWorkplaces,
    changeCurrentWorkplace,
  };
};

const getWorkplaces = ({ includeAvailable }) => {
  return axios
    .get(
      `${config.API_URL}/userSession/workplace?includeAvailable=${includeAvailable}`
    )
    .then((response) => response.data); // unbox
};

const setWorkplaceId = (workplaceId) => {
  return axios.put(`${config.API_URL}/userSession/workplace`, { workplaceId });
};
