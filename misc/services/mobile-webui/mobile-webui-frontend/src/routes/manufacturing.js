import ReceiptReceiveTargetScreen from '../containers/activities/manufacturing/ReceiptReceiveTargetScreen';
import ReceiptNewHUScreen from '../containers/activities/manufacturing/ReceiptNewHUScreen';
import RawMaterialIssueStepScreen from '../containers/activities/manufacturing/RawMaterialIssueStepScreen';

export const manufacturingLineScreenLocation = ({ wfProcessId, activityId, lineId }) =>
  `/${wfProcessId}/activityId/${activityId}/lineId/${lineId}`;

export const manufacturingStepScreenLocation = ({ wfProcessId, activityId, lineId, stepId }) =>
  `/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}`;

export const manufacturingScanScreenLocation = ({ wfProcessId, activityId, lineId, stepId }) =>
  `/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}/scanner/mfg`;

export const manufacturingRoutes = [
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
    path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/receipt/target',
    Component: ReceiptReceiveTargetScreen,
  },
  {
    path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/receipt/receive/hu',
    Component: ReceiptNewHUScreen,
  },
];
