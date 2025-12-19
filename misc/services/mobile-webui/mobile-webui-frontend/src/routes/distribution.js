import DistributionLineScreen from '../containers/activities/distribution/DistributionLineScreen';
import DistributionStepScreen from '../containers/activities/distribution/DistributionStepScreen';
import DistributionStepPickFromScreen from '../containers/activities/distribution/DistributionStepPickFromScreen';
import DistributionStepDropToScreen from '../containers/activities/distribution/DistributionStepDropToScreen';
import { getWFProcessScreenLocation } from './workflow_locations';
import DistributionPickFromScreen from '../containers/activities/distribution/DistributionPickFromScreen';
import DistributionDropAllToScreen from '../containers/activities/distribution/DistributionDropAllToScreen';
import { APPLICATION_ID_Distribution } from '../apps/distribution/constants';
import DistributionJobsDropAllScreen from '../apps/distribution/containers/DistributionJobsDropAllScreen';
import { toUrl } from '../utils';

export const distributionJobsListScreenLocation = () => `/${APPLICATION_ID_Distribution}/launchers`;
// appLaunchersLocation({ applicationId: APPLICATION_ID_Distribution });

export const distributionJobsDropAllScreen = () => distributionJobsListScreenLocation() + '/dropAll';

export const distributionJobScreenLocation = ({ applicationId, wfProcessId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId });

export const distributionLineScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) =>
  distributionJobScreenLocation({ applicationId, wfProcessId }) + `/dd/A/${activityId}/L/${lineId}`;

export const distributionPickFromScreenLocation = ({ applicationId, wfProcessId, activityId, lineId, huQRCode }) =>
  toUrl(distributionJobScreenLocation({ applicationId, wfProcessId }) + `/dd/A/${activityId}/pickFrom`, {
    lineId,
    huQRCode,
  });

export const distributionDropAllToScreenLocation = ({ applicationId, wfProcessId, activityId }) =>
  distributionJobScreenLocation({ applicationId, wfProcessId }) + `/dd/A/${activityId}/dropTo`;

export const distributionStepScreenLocation = ({ applicationId, wfProcessId, activityId, lineId, stepId }) =>
  distributionLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }) + `/stepId/${stepId}`;

export const distributionStepPickFromScreenLocation = ({ applicationId, wfProcessId, activityId, lineId, stepId }) =>
  distributionStepScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId }) + '/pickFrom';

export const distributionStepDropToScreenLocation = ({ applicationId, wfProcessId, activityId, lineId, stepId }) =>
  distributionStepScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId }) + '/dropTo';

export const distributionRoutes = [
  {
    path: distributionJobsDropAllScreen(),
    Component: DistributionJobsDropAllScreen,
  },
  {
    path: distributionDropAllToScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
    }),
    Component: DistributionDropAllToScreen,
  },
  {
    path: distributionLineScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: DistributionLineScreen,
  },
  {
    path: distributionPickFromScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
    }),
    Component: DistributionPickFromScreen,
  },
  {
    path: distributionStepScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
    }),
    Component: DistributionStepScreen,
  },
  {
    path: distributionStepPickFromScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
    }),
    Component: DistributionStepPickFromScreen,
  },
  {
    path: distributionStepDropToScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
    }),
    Component: DistributionStepDropToScreen,
  },
];
