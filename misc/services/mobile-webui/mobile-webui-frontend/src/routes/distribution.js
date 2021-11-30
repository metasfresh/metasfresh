import DistributionLineScreen from '../containers/activities/distribution/DistributionLineScreen';
import DistributionStepScreen from '../containers/activities/distribution/DistributionStepScreen';
import DistributionStepPickFromScreen from '../containers/activities/distribution/DistributionStepPickFromScreen';
import DistributionStepDropToScreen from '../containers/activities/distribution/DistributionStepDropToScreen';

export const distributionLineScreenLocation = ({ wfProcessId, activityId, lineId }) =>
  `/app/distribution/${wfProcessId}/activityId/${activityId}/lineId/${lineId}`;

export const distributionStepScreenLocation = ({ wfProcessId, activityId, lineId, stepId }) =>
  `/app/distribution/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}`;

export const distributionStepPickFromScreenLocation = ({ wfProcessId, activityId, lineId, stepId }) =>
  `/app/distribution/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}/pickFrom`;

export const distributionStepDropToScreenLocation = ({ wfProcessId, activityId, lineId, stepId }) =>
  `/app/distribution/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/stepId/${stepId}/dropTo`;

export const distributionRoutes = [
  {
    path: distributionLineScreenLocation({ wfProcessId: ':workflowId', activityId: ':activityId', lineId: ':lineId' }),
    Component: DistributionLineScreen,
  },
  {
    path: '/app/distribution/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId',
    Component: DistributionStepScreen,
  },
  {
    path: '/app/distribution/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId/pickFrom',
    Component: DistributionStepPickFromScreen,
  },
  {
    path: '/app/distribution/:workflowId/activityId/:activityId/lineId/:lineId/stepId/:stepId/dropTo',
    Component: DistributionStepDropToScreen,
  },
];
