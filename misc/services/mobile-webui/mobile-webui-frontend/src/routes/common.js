import WFLaunchersScreen from '../containers/wfLaunchersScreen/WFLaunchersScreen';
import WFProcessScreen from '../containers/wfProcessScreen/WFProcessScreen';
import ScanScreen from '../containers/activities/scan/ScanScreen';
import LineScreen from '../containers/activities/common/LineScreen';
import StepScreen from '../containers/activities/common/StepScreen';
import StepScanScreen from '../containers/activities/common/StepScanScreen';
import ApplicationsScreen from '../containers/applicationScreen/ApplicationsScreen';

export const commonRoutes = [
  { path: '/', Component: ApplicationsScreen },
  { path: '/workflow/:workflowId', Component: WFProcessScreen },
  { path: '/launchers/:applicationId', Component: WFLaunchersScreen },
  { path: '/workflow/:workflowId/activityId/:activityId/scanner', Component: ScanScreen },
  { path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId', Component: LineScreen },
  { path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId', Component: StepScreen },
  {
    path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId/altStepId/:altStepId',
    Component: StepScreen,
  },
  {
    path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId/scanner/:appId',
    Component: StepScanScreen,
  },
  {
    path: '/workflow/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId/altStepId/:altStepId/scanner/:appId/:locatorId?',
    Component: StepScanScreen,
  },
];
