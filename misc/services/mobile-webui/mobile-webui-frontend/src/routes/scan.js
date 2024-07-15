import ScanScreen from '../containers/activities/scan/ScanScreen';
import { getWFProcessScreenLocation } from './workflow_locations';
import ScanAndValidateScreen from '../containers/activities/scan/ScanAndValidateScreen';

export const scanBarcodeLocation = ({ applicationId, wfProcessId, activityId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId }) + `/scan/A/${activityId}/`;

export const scanAndValidateBarcodeLocation = ({ applicationId, wfProcessId, activityId, validOptionIndex }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId }) + `/scan/A/${activityId}/vo/${validOptionIndex}`;

export const scanRoutes = [
  {
    path: scanBarcodeLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
    }),
    Component: ScanScreen,
  },
  {
    path: scanAndValidateBarcodeLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      validOptionIndex: ':validOptionIndex',
    }),
    Component: ScanAndValidateScreen,
  },
];
