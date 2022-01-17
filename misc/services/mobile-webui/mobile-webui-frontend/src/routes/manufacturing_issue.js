import RawMaterialIssueLineScreen from '../containers/activities/manufacturing/issue/RawMaterialIssueLineScreen';
import RawMaterialIssueStepScreen from '../containers/activities/manufacturing/issue/RawMaterialIssueStepScreen';
import RawMaterialIssueScanScreen from '../containers/activities/manufacturing/issue/RawMaterialIssueScanScreen';

export const manufacturingLineScreenLocation = ({ wfProcessId, activityId, lineId }) =>
  `/app/mfg/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/issue`;

export const manufacturingStepScreenLocation = ({ wfProcessId, activityId, lineId, stepId }) =>
  `/app/mfg/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/issue/stepId/${stepId}`;

export const manufacturingScanScreenLocation = ({ wfProcessId, activityId, lineId, stepId }) =>
  `/app/mfg/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/issue/stepId/${stepId}/scanner`;

export const manufacturingIssueRoutes = [
  {
    path: manufacturingLineScreenLocation({
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: RawMaterialIssueLineScreen,
  },
  {
    path: manufacturingStepScreenLocation({
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
    }),
    Component: RawMaterialIssueStepScreen,
  },
  {
    path: manufacturingScanScreenLocation({
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
    }),
    Component: RawMaterialIssueScanScreen,
  },
];
