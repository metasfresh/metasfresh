import { useSelector } from 'react-redux';
import { getActivityById } from '../index';

export const useCurrentPickTarget = ({ wfProcessId, activityId }) => {
  return useSelector((state) => getCurrentPickTarget({ state, wfProcessId, activityId }));
};

const getCurrentPickTarget = ({ state, wfProcessId, activityId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  return activity?.dataStored?.pickTarget;
};
