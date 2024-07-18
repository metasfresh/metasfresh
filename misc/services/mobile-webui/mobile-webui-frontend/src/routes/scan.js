import ScanScreen from '../containers/activities/scan/ScanScreen';
import { getWFProcessScreenLocation } from './workflow_locations';

export const scanBarcodeLocation = ({ applicationId, wfProcessId, activityId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId }) + `/scan/A/${activityId}/`;

export const scanAndValidateBarcodeLocation = ({ applicationId, wfProcessId, activityId, validOptionIndex }) =>
  `${scanBarcodeLocation({ applicationId, wfProcessId, activityId })}?validOptionIndex=${validOptionIndex}`;

export const scanRoutes = [
  {
    path: scanBarcodeLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
    }),
    Component: ScanScreen,
  },
];
