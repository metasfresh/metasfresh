import ReceiptReceiveTargetScreen from '../containers/activities/manufacturing/ReceiptReceiveTargetScreen';
import ReceiptNewHUScreen from '../containers/activities/manufacturing/ReceiptNewHUScreen';
import RawMaterialIssueStepScreen from '../containers/activities/manufacturing/RawMaterialIssueStepScreen';
import ManufacturingReceiptScanScreen from '../containers/activities/manufacturing/ManufacturingReceiptScanScreen';
import RawMaterialIssueScanScreen from '../containers/activities/manufacturing/RawMaterialIssueScanScreen';
import LineScreen from '../containers/activities/manufacturing/LineScreen';

export const manufacturingLineScreenLocation = ({ wfProcessId, activityId, lineId }) =>
  `/app/mfg/${wfProcessId}/activityId/${activityId}/lineId/${lineId}`;

export const manufacturingStepScreenLocation = ({ wfProcessId, activityId, lineId, stepId }) =>
  `/app/mfg/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}`;

export const manufacturingScanScreenLocation = ({ wfProcessId, activityId, lineId, stepId }) =>
  `/app/mfg/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}/scanner`;

export const manufacturingReceiptScanScreenLocation = ({ wfProcessId, activityId, lineId }) =>
  `/app/mfg/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/receipt/scanner`;

export const manufacturingReceiptReceiveTargetScreen = ({ wfProcessId, activityId, lineId }) =>
  `/app/mfg/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/receipt/target`;

export const manufacturingReceiptNewHUScreen = ({ wfProcessId, activityId, lineId }) =>
  `/app/mfg/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/receipt/receive/hu`;

export const manufacturingRoutes = [
  {
    path: manufacturingLineScreenLocation({
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: LineScreen,
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
    path: '/app/mfg/:workflowId/activityId/:activityId/lineId/:lineId/receipt/target',
    Component: ReceiptReceiveTargetScreen,
  },
  {
    path: '/app/mfg/:workflowId/activityId/:activityId/lineId/:lineId/receipt/receive/hu',
    Component: ReceiptNewHUScreen,
  },
  {
    path: manufacturingReceiptScanScreenLocation({
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: ManufacturingReceiptScanScreen,
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
