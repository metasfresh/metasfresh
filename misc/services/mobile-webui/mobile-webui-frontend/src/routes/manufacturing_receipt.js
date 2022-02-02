import MaterialReceiptLineScreen from '../containers/activities/manufacturing/receipt/MaterialReceiptLineScreen';
import ReceiptReceiveTargetScreen from '../containers/activities/manufacturing/receipt/ReceiptReceiveTargetScreen';
import ReceiptNewHUScreen from '../containers/activities/manufacturing/receipt/ReceiptNewHUScreen';
import ManufacturingReceiptScanScreen from '../containers/activities/manufacturing/receipt/ManufacturingReceiptScanScreen';
import { getWFProcessScreenLocation } from './workflow_locations';

export const manufacturingReceiptScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId }) + `/receive/A/${activityId}/L/${lineId}`;

export const manufacturingReceiptReceiveTargetScreen = ({ applicationId, wfProcessId, activityId, lineId }) =>
  manufacturingReceiptScreenLocation({ applicationId, wfProcessId, activityId, lineId }) + `/target`;

export const manufacturingReceiptScanScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) =>
  manufacturingReceiptReceiveTargetScreen({ applicationId, wfProcessId, activityId, lineId }) + `/scan`;

export const manufacturingReceiptNewHUScreen = ({ applicationId, wfProcessId, activityId, lineId }) =>
  manufacturingReceiptReceiveTargetScreen({ applicationId, wfProcessId, activityId, lineId }) + `/newHU`;

export const manufacturingReceiptRoutes = [
  {
    path: manufacturingReceiptScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: MaterialReceiptLineScreen,
  },
  {
    path: manufacturingReceiptReceiveTargetScreen({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: ReceiptReceiveTargetScreen,
  },
  {
    path: manufacturingReceiptNewHUScreen({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: ReceiptNewHUScreen,
  },
  {
    path: manufacturingReceiptScanScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: ManufacturingReceiptScanScreen,
  },
];
