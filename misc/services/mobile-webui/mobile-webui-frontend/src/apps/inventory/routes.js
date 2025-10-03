import { getWFProcessScreenLocation } from '../../routes/workflow_locations';
import InventoryLineScreen from './activities/InventoryLineScreen';
import InventoryScanScreen from './activities/InventoryScanScreen';

export const inventoryJobLocation = ({ applicationId, wfProcessId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId });

export const inventoryLineScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) => {
  const baseUrl = inventoryJobLocation({ applicationId, wfProcessId });
  return `${baseUrl}/line/${activityId}/${lineId}`;
};
export const inventoryScanScreenLocation = ({ applicationId, wfProcessId, activityId }) => {
  const baseUrl = inventoryJobLocation({ applicationId, wfProcessId });
  return `${baseUrl}/line/${activityId}/scan`;
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
