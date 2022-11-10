import RawMaterialIssueLineScreen from '../containers/activities/manufacturing/issue/RawMaterialIssueLineScreen';
import RawMaterialIssueStepScreen from '../containers/activities/manufacturing/issue/RawMaterialIssueStepScreen';
import RawMaterialIssueScanScreen from '../containers/activities/manufacturing/issue/RawMaterialIssueScanScreen';
import { getWFProcessScreenLocation } from './workflow_locations';

export const manufacturingLineScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId }) + `/issue/A/${activityId}/L/${lineId}`;

export const manufacturingStepScreenLocation = ({ applicationId, wfProcessId, activityId, lineId, stepId }) =>
  manufacturingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }) + `/S/${stepId}`;

export const manufacturingScanScreenLocation = ({ applicationId, wfProcessId, activityId, lineId, stepId }) =>
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
    path: manufacturingScanScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
    }),
    Component: RawMaterialIssueScanScreen,
  },
];
