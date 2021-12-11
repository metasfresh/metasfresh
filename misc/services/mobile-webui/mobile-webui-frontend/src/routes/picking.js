import PickStepScreen from '../containers/activities/picking/PickStepScreen';
import PickStepScanScreen from '../containers/activities/picking/PickStepScanScreen';
import PickLineScreen from '../containers/activities/picking/PickLineScreen';

export const pickingLineScreenLocation = ({ wfProcessId, activityId, lineId }) =>
  `/app/picking/${wfProcessId}/activityId/${activityId}/lineId/${lineId}`;

export const pickingStepScreenLocation = ({ wfProcessId, activityId, lineId, stepId, altStepId }) =>
  `/app/picking/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}${
    altStepId ? `/altStepId/${altStepId}` : ''
  }`;

export const pickingStepScanScreenLocation = ({ wfProcessId, activityId, lineId, stepId, altStepId }) => {
  return `/app/picking/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}${
    altStepId ? `/altStepId/${altStepId}` : ''
  }/scanner`;
};

export const pickingRoutes = [
  {
    path: pickingLineScreenLocation({
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: PickLineScreen,
  },
  {
    path: pickingStepScreenLocation({
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
    }),
    Component: PickStepScreen,
  },
  {
    path: pickingStepScreenLocation({
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
      altStepId: ':altStepId',
    }),
    Component: PickStepScreen,
  },
  {
    path: pickingStepScanScreenLocation({
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
    }),
    Component: PickStepScanScreen,
  },
  {
    path: pickingStepScanScreenLocation({
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
      altStepId: ':altStepId',
    }),
    Component: PickStepScanScreen,
  },
];
