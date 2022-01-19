import { getActivityById } from '../../../../reducers/wfProcesses_status';

export const getOptions = ({ state, wfProcessId, activityId }) => {
  return getActivityById(state, wfProcessId, activityId)?.componentProps?.options ?? [];
};

export const getOptionByIndex = ({ state, wfProcessId, activityId, optionIndex }) => {
  return getOptions({ state, wfProcessId, activityId })[optionIndex];
};
