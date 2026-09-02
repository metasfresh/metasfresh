import { toUrl } from '../../utils';
import { getWFProcessScreenLocation } from '../../routes/workflow_locations';
import InventoryLineScreen from './activities/InventoryLineScreen';
import InventoryScanScreen from './activities/InventoryScanScreen';
import InventoryLineHUScreen from './activities/InventoryLineHUScreen';

export const inventoryJobLocation = ({ applicationId, wfProcessId }) => {
  return getWFProcessScreenLocation({ applicationId, wfProcessId });
};
export const inventoryScanScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) => {
  const baseUrl = inventoryJobLocation({ applicationId, wfProcessId });
  return toUrl(`${baseUrl}/scan/${activityId}`, { lineId });
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
export const inventoryLineHUScreenLocation = ({ applicationId, wfProcessId, activityId, lineId, lineHUId }) => {
  const baseUrl = inventoryJobOrLineLocation({ applicationId, wfProcessId, activityId, lineId });
  return `${baseUrl}/lineHU/${lineHUId}`;
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
  {
    path: inventoryLineHUScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      lineHUId: ':lineHUId',
    }),
    Component: InventoryLineHUScreen,
  },
];
