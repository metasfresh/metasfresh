import MaterialReceiptLineScreen from '../containers/activities/manufacturing/receipt/MaterialReceiptLineScreen';
import ReceiptReceiveTargetScreen from '../containers/activities/manufacturing/receipt/ReceiptReceiveTargetScreen';
import ReceiptNewHUScreen from '../containers/activities/manufacturing/receipt/ReceiptNewHUScreen';
import ManufacturingReceiptScanScreen from '../containers/activities/manufacturing/receipt/ManufacturingReceiptScanScreen';

export const manufacturingReceiptScreenLocation = ({ wfProcessId, activityId, lineId }) =>
  `/app/mfg/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/receipt`;

export const manufacturingReceiptScanScreenLocation = ({ wfProcessId, activityId, lineId }) =>
  `/app/mfg/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/receipt/scanner`;

export const manufacturingReceiptReceiveTargetScreen = ({ wfProcessId, activityId, lineId }) =>
  `/app/mfg/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/receipt/target`;

export const manufacturingReceiptNewHUScreen = ({ wfProcessId, activityId, lineId }) =>
  `/app/mfg/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/receipt/receive/hu`;

export const manufacturingReceiptRoutes = [
  {
    path: '/app/mfg/:workflowId/activityId/:activityId/lineId/:lineId/receipt/target',
    Component: ReceiptReceiveTargetScreen,
  },
  {
    path: manufacturingReceiptScreenLocation({
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: MaterialReceiptLineScreen,
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
];
