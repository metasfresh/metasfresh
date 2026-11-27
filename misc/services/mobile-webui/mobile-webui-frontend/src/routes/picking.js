import PickStepScreen from '../containers/activities/picking/PickStepScreen';
import PickStepScanScreen from '../containers/activities/picking/PickStepScanScreen';
import PickLineScreen from '../containers/activities/picking/PickLineScreen';
import { getWFProcessScreenLocation } from './workflow_locations';
import PickLineScanScreen from '../containers/activities/picking/PickLineScanScreen';
import PickProductsScanScreen from '../containers/activities/picking/PickProductsScanScreen';
import { toUrl } from '../utils';
import { SelectPickTargetScreen } from '../containers/activities/picking/SelectPickTargetScreen';
import { ReopenLUScreen } from '../containers/activities/picking/ReopenLUScreen';
import { appLaunchersLocation } from './launchers';
import { APPLICATION_ID_Picking } from '../apps/picking';

export const pickingJobsListLocation = ({ applicationId = APPLICATION_ID_Picking } = {}) =>
  appLaunchersLocation({ applicationId });

const pickingJobLocation = ({ applicationId, wfProcessId }) =>
  getWFProcessScreenLocation({ applicationId, wfProcessId });

export const pickingJobOrLineLocation = ({ applicationId, wfProcessId, activityId, lineId }) =>
  lineId
    ? pickingLineScreenLocation({ applicationId, wfProcessId, activityId, lineId })
    : pickingJobLocation({ applicationId, wfProcessId });

export const reopenClosedLUScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) => {
  const baseUrl = pickingJobLocation({ applicationId, wfProcessId });
  return `${baseUrl}/reopen-lu/${activityId}${lineId ? `/${lineId}` : ''}`;
};

/** @param returnTo where the choice leads to, instead of the back location of the screen (e.g. the scan screen "Weiteres TU" was tapped on) */
export const selectPickingTargetScreenLocation = ({
  applicationId,
  wfProcessId,
  activityId,
  lineId,
  type,
  returnTo,
}) => {
  const baseUrl = pickingJobLocation({ applicationId, wfProcessId });
  return toUrl(`${baseUrl}/selectPickTarget/${activityId}${lineId ? `/${lineId}` : ''}/${type}`, { returnTo });
};

export const pickingScanScreenLocation = ({ applicationId, wfProcessId, activityId }) =>
  pickingJobLocation({ applicationId, wfProcessId }) + `/pick/A/${activityId}/scan`;

export const pickingLineScreenLocation = ({ applicationId, wfProcessId, activityId, lineId }) =>
  pickingJobLocation({ applicationId, wfProcessId }) + `/pick/A/${activityId}/L/${lineId}`;

export const pickingLineScanScreenLocation = ({ applicationId, wfProcessId, activityId, lineId, qrCode, next }) =>
  toUrl(pickingJobLocation({ applicationId, wfProcessId }) + `/pick/A/${activityId}/L/${lineId}/scanner`, {
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
    path: reopenClosedLUScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
    }),
    Component: ReopenLUScreen,
  },
  {
    path: selectPickingTargetScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      type: ':type',
    }),
    Component: SelectPickTargetScreen,
  },
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
    path: reopenClosedLUScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
    }),
    Component: ReopenLUScreen,
  },
  {
    // Exactly one route may match a given URL: routes render as siblings without a <Switch>,
    // so a duplicate (e.g. a literal-'tu' route beside this :type one) would double-mount the screen.
    path: selectPickingTargetScreenLocation({
      applicationId: ':applicationId',
      wfProcessId: ':workflowId',
      activityId: ':activityId',
      lineId: ':lineId',
      type: ':type',
    }),
    Component: SelectPickTargetScreen,
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
