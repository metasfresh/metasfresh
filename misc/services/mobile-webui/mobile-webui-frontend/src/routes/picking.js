import PickStepScreen from '../containers/activities/picking/PickStepScreen';

export const pickingLineScreenLocation = ({ wfProcessId, activityId, lineId }) =>
  `/${wfProcessId}/activityId/${activityId}/lineId/${lineId}`;

export const pickingStepScreenLocation = ({ wfProcessId, activityId, lineId, stepId }) =>
  `/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}`;

export const pickingStepScanScreenLocation = ({ wfProcessId, activityId, lineId, stepId }) =>
  `/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}/scanner/picking`;

export const pickingRoutes = [
  {
    path: pickingStepScreenLocation({
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
    }),
    Component: PickStepScreen,
  },
];
