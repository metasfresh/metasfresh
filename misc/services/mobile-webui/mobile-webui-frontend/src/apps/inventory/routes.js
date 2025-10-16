import { getWFProcessScreenLocation } from '../../routes/workflow_locations';
import InventoryLineScreen from './activities/InventoryLineScreen';
import InventoryScanScreen from './activities/InventoryScanScreen';
import { toUrl } from '../../utils';

export const inventoryJobLocation = ({ applicationId, wfProcessId }) => {
  return getWFProcessScreenLocation({ applicationId, wfProcessId });
};
export const inventoryLineScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) => {
  const baseUrl = inventoryJobLocation({ applicationId, wfProcessId });
  return `${baseUrl}/line/${activityId}/${lineId}`;
};
export const inventoryJobOrLineLocation = ({ applicationId, wfProcessId, activityId, lineId }) => {
  return lineId
    ? inventoryLineScreenLocation({ applicationId, wfProcessId, activityId, lineId })
    : inventoryJobLocation({ applicationId, wfProcessId });
};
export const inventoryScanScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) => {
  const baseUrl = inventoryJobLocation({ applicationId, wfProcessId });
  return toUrl(`${baseUrl}/scan/${activityId}`, { lineId });
};

export const inventoryRoutes = [
  {
    path: inventoryScanScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
    }),
    Component: InventoryScanScreen,
  },
  {
    path: inventoryLineScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: InventoryLineScreen,
  },
];
