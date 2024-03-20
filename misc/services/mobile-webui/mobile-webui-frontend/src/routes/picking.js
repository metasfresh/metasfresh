import PickStepScreen from '../containers/activities/picking/PickStepScreen';
import PickStepScanScreen from '../containers/activities/picking/PickStepScanScreen';
import PickLineScreen from '../containers/activities/picking/PickLineScreen';
import { getWFProcessScreenLocation } from './workflow_locations';
import PickLineScanScreen from '../containers/activities/picking/PickLineScanScreen';
import PickProductsScanScreen from '../containers/activities/picking/PickProductsScanScreen';
import { toUrl } from '../utils';

export const pickingScanScreenLocation = ({ applicationId, wfProcessId, activityId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId }) + `/pick/A/${activityId}/scan`;

export const pickingLineScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId }) + `/pick/A/${activityId}/L/${lineId}`;

export const pickingLineScanScreenLocation = ({ applicationId, wfProcessId, activityId, lineId, qrCode, next }) =>
  toUrl(getWFProcessScreenLocation({ applicationId, wfProcessId }) + `/pick/A/${activityId}/L/${lineId}/scanner`, {
    qrCode,
    next,
  });

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
    path: pickingScanScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
    }),
    Component: PickProductsScanScreen,
  },
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
    path: pickingLineScanScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: PickLineScanScreen,
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
