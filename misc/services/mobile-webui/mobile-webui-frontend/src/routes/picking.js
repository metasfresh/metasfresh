import PickStepScreen from '../containers/activities/picking/PickStepScreen';
import PickStepScanScreen from '../containers/activities/picking/PickStepScanScreen';
import PickLineScreen from '../containers/activities/picking/PickLineScreen';
import { getWFProcessScreenLocation } from './workflow_locations';

export const pickingLineScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId }) + `/pick/A/${activityId}/L/${lineId}`;

export const pickingStepScreenLocation = ({ applicationId, wfProcessId, activityId, lineId, stepId, altStepId }) =>
  pickingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }) +
  `/S/${stepId}${altStepId ? `/ALT/${altStepId}` : ''}`;

export const pickingStepScanScreenLocation = ({
  applicationId,
  wfProcessId,
  activityId,
  lineId,
  stepId,
  altStepId,
}) => {
  return pickingStepScreenLocation({ applicationId, wfProcessId, activityId, lineId, stepId, altStepId }) + `/scanner`;
};

export const pickingRoutes = [
  {
    path: pickingLineScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: PickLineScreen,
  },
  {
    path: pickingStepScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
    }),
    Component: PickStepScreen,
  },
  {
    path: pickingStepScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
      altStepId: ':altStepId',
    }),
    Component: PickStepScreen,
  },
  {
    path: pickingStepScanScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
    }),
    Component: PickStepScanScreen,
  },
  {
    path: pickingStepScanScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      stepId: ':stepId',
      altStepId: ':altStepId',
    }),
    Component: PickStepScanScreen,
  },
];
