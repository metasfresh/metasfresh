import { getActivityById } from '../../../../reducers/wfProcesses';

export const getOptionsFromActivity = (activity) => {
  return activity?.componentProps?.options ?? [];
};

export const getOptions = ({ state, wfProcessId, activityId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  return activity?.componentProps?.options ?? [];
};

export const getOptionByIndex = ({ state, wfProcessId, activityId, optionIndex }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const options = getOptionsFromActivity(activity);
  return options[optionIndex];
};
