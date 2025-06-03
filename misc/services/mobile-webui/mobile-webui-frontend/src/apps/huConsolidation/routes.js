import { SelectHUConsolidationTargetScreen } from './activities/SelectHUConsolidationTargetScreen';
import { getWFProcessScreenLocation } from '../../routes/workflow_locations';

export const huConsolidationJobLocation = ({ applicationId, wfProcessId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId });

export const selectTargetScreenLocation = ({ applicationId, wfProcessId, activityId }) => {
  const baseUrl = huConsolidationJobLocation({ applicationId, wfProcessId });
  return `${baseUrl}/selectTarget/${activityId}`;
};

export const huConsolidationRoutes = [
  {
    path: selectTargetScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      type: ':type',
    }),
    Component: SelectHUConsolidationTargetScreen,
  },
];
