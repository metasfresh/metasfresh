import DistributionLineScreen from '../containers/activities/distribution/DistributionLineScreen';
import DistributionStepScreen from '../containers/activities/distribution/DistributionStepScreen';
import DistributionStepPickFromScreen from '../containers/activities/distribution/DistributionStepPickFromScreen';
import DistributionStepDropToScreen from '../containers/activities/distribution/DistributionStepDropToScreen';
import { getWFProcessScreenLocation } from './workflow_locations';
import DistributionLinePickFromScreen from '../containers/activities/distribution/DistributionLinePickFromScreen';
import DistributionDropAllToScreen from '../containers/activities/distribution/DistributionDropAllToScreen';

export const distributionJobScreenLocation = ({ applicationId, wfProcessId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId });

export const distributionLineScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) =>
  distributionJobScreenLocation({ applicationId, wfProcessId }) + `/dd/A/${activityId}/L/${lineId}`;

export const distributionDropAllToScreenLocation = ({ applicationId, wfProcessId, activityId }) =>
  distributionJobScreenLocation({ applicationId, wfProcessId }) + `/dd/A/${activityId}/dropTo`;

export const distributionLinePickFromScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) =>
  distributionLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }) + `/scan`;

export const distributionStepScreenLocation = ({ applicationId, wfProcessId, activityId, lineId, stepId }) =>
  distributionLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }) + `/stepId/${stepId}`;

export const distributionStepPickFromScreenLocation = ({ applicationId, wfProcessId, activityId, lineId, stepId }) =>
  distributionStepScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId }) + '/pickFrom';

export const distributionStepDropToScreenLocation = ({ applicationId, wfProcessId, activityId, lineId, stepId }) =>
  distributionStepScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId }) + '/dropTo';

export const distributionRoutes = [
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
    path: distributionLinePickFromScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: DistributionLinePickFromScreen,
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
