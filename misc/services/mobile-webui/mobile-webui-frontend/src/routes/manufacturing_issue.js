import RawMaterialIssueLineScreen from '../containers/activities/manufacturing/issue/RawMaterialIssueLineScreen';
import RawMaterialIssueStepScreen from '../containers/activities/manufacturing/issue/RawMaterialIssueStepScreen';
import RawMaterialIssueStepScanScreen from '../containers/activities/manufacturing/issue/step_scan/RawMaterialIssueStepScanScreen';
import { getWFProcessScreenLocation } from './workflow_locations';
import RawMaterialIssueLineScanScreen from '../containers/activities/manufacturing/issue/RawMaterialIssueLineScanScreen';

export const manufacturingLineScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId }) + `/issue/A/${activityId}/L/${lineId}`;

export const manufacturingLineScanScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) =>
  manufacturingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }) + '/scanner';

export const manufacturingStepScreenLocation = ({ applicationId, wfProcessId, activityId, lineId, stepId }) =>
  manufacturingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }) + `/S/${stepId}`;

export const manufacturingStepScanScreenLocation = ({ applicationId, wfProcessId, activityId, lineId, stepId }) =>
  manufacturingStepScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId }) + '/scanner';

export const manufacturingIssueRoutes = [
  {
    path: manufacturingLineScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: RawMaterialIssueLineScreen,
  },
  {
    path: manufacturingLineScanScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: RawMaterialIssueLineScanScreen,
  },
  {
    path: manufacturingStepScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
    }),
    Component: RawMaterialIssueStepScreen,
  },
  {
    path: manufacturingStepScanScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
    }),
    Component: RawMaterialIssueStepScanScreen,
  },
];
