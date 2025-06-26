import { getWFProcessScreenLocation } from '../../routes/workflow_locations';
import { SelectHUConsolidationTargetScreen } from './activities/SelectHUConsolidationTargetScreen';
import { PickingSlotScreen } from './activities/PickingSlotScreen';
import { ScanHUConsolidationTargetScreen } from './activities/ScanHUConsolidationTargetScreen';

export const huConsolidationJobLocation = ({ applicationId, wfProcessId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId });

export const selectTargetScreenLocation = ({ applicationId, wfProcessId, activityId }) => {
  const baseUrl = huConsolidationJobLocation({ applicationId, wfProcessId });
  return `${baseUrl}/selectTarget/${activityId}`;
};
export const scanTargetScreenLocation = ({ applicationId, wfProcessId, activityId }) => {
  const baseUrl = huConsolidationJobLocation({ applicationId, wfProcessId });
  return `${baseUrl}/scanTarget/${activityId}`;
};

export const pickingSlotScreenLocation = ({ applicationId, wfProcessId, activityId, pickingSlotId }) => {
  const baseUrl = huConsolidationJobLocation({ applicationId, wfProcessId });
  return `${baseUrl}/pickingSlot/${activityId}/${pickingSlotId}`;
};

export const huConsolidationRoutes = [
  {
    path: selectTargetScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
    }),
    Component: SelectHUConsolidationTargetScreen,
  },
  {
    path: scanTargetScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
    }),
    Component: ScanHUConsolidationTargetScreen,
  },
  {
    path: pickingSlotScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      pickingSlotId: ':id',
    }),
    Component: PickingSlotScreen,
  },
];
