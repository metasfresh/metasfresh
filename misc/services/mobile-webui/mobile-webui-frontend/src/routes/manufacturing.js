import ReceiptReceiveTargetScreen from '../containers/activities/manufacturing/ReceiptReceiveTargetScreen';
import ReceiptNewHUScreen from '../containers/activities/manufacturing/ReceiptNewHUScreen';

export const manufacturingRoutes = [
  {
    path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/receipt/target',
    Component: ReceiptReceiveTargetScreen,
  },
  {
    path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/receipt/receive/hu',
    Component: ReceiptNewHUScreen,
  },
];
