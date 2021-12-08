import PickStepScreen from '../containers/activities/picking/PickStepScreen';
import PickStepScanScreen from '../containers/activities/picking/PickStepScanScreen';
import LineScreen from '../containers/activities/common/LineScreen';

export const pickingLineScreenLocation = ({ wfProcessId, activityId, lineId }) =>
  `/app/picking/${wfProcessId}/activityId/${activityId}/lineId/${lineId}`;

export const pickingStepScreenLocation = ({ wfProcessId, activityId, lineId, stepId }) =>
  `/app/picking/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}`;

export const pickingAltStepScreenLocation = ({ wfProcessId, activityId, lineId, stepId, altStepId }) =>
  `/app/picking/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}/altStepId/${altStepId}`;

export const pickingStepScanScreenLocation = ({ wfProcessId, activityId, lineId, stepId }) =>
  `/app/picking/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}/scanner/picking`;

export const pickingRoutes = [
  {
    path: pickingLineScreenLocation({ wfProcessId: ':workflowId', activityId: ':activityId', lineId: ':lineId' }),
    Component: LineScreen,
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
    path: pickingAltStepScreenLocation({
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
      altStepId: ':altStepId',
    }),
    Component: PickStepScreen,
  },
  {
    path: '/app/picking/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId/scanner',
    Component: PickStepScanScreen,
  },
  {
    path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId/altStepId/:altStepId/scanner/',
    Component: PickStepScanScreen,
  },
];
