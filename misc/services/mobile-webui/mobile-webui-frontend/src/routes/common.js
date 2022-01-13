import ApplicationsScreen from '../containers/applicationScreen/ApplicationsScreen';

import ScanScreen from '../containers/activities/scan/ScanScreen';

export const commonRoutes = [
  { path: '/', Component: ApplicationsScreen },
  { path: '/workflow/:workflowId/activityId/:activityId/scanner', Component: ScanScreen },
];
