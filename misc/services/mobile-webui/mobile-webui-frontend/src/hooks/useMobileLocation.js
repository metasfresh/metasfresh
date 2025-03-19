import { useRouteMatch } from 'react-router-dom';

export const useMobileLocation = () => {
  const {
    url,
    params: { applicationId, wfProcessId, workflowId, activityId, lineId, stepId, altStepId, ...otherParams },
  } = useRouteMatch();

  return {
    ...otherParams,
    url,
    applicationId,
    wfProcessId: wfProcessId ? wfProcessId : workflowId,
    activityId,
    lineId,
    stepId,
    altStepId,
  };
};
